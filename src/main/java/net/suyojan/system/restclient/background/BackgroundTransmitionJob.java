/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.suyojan.system.restclient.background;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import net.suyojan.system.restclient.configuration.RestConfiguration;
import net.suyojan.system.restclient.entity.Testrecord;
import net.suyojan.system.restclient.exception.RecoveryRetriesLimitExceedException;
import net.suyojan.system.restclient.service.LogFileService;
import net.suyojan.system.restclient.service.TestRecordService;
import net.suyojan.system.restclient.service.XMLReadWriteService;
import net.suyojan.system.restclient.util.SuyojanLogger;
import org.apache.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author Suyojan
 * 
 */
@Controller
public class BackgroundTransmitionJob 
{
    @Autowired
    private TestRecordService testRecordService;
    
    /*
       Time of repeatition fetched from  properties file.
    */
    
    @Value("${fixed.repeat}")
    private long repeatition_time;
   
    
    /*
      Recovery retries
    */
    
    @Value("${failed.operation.recover.tries}")
    private int retries;
    
    /*
       Rest end point URL fetched properties file.
    */
    @Value("${rest.endpoint.update}")
    private String batchUpdateURL;
    
    @Value("${rest.endpoint.query}")
    private String retrieveURL;
    
    @Value("${current.dbName}")
    private String dbName;
    
    @Autowired
    private XMLReadWriteService xMLReadWriteService;
    
    @Autowired
    private LogFileService logFileService;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private SuyojanLogger suyojanLogger;
    
    @Value("${network.fail.message}")
    private String networkFailMessage;
    
    /**
     * The batch of 25 records is fetched. 
     * Each batch is sent to the remote server.
     * The task is scheduled every pre-specified time.
     */
    
    public void submitTask()
    {
        
        try {
           
         ScheduledExecutorService executorService=Executors.newSingleThreadScheduledExecutor();
         //executorService.shutdown();
         /*
            Creating a background thread.
            This threads performs the following tasks
            1. Fetch a batch of 25 records
            2. Calling the REST end point and transmitting the batch
            3. If server response with true then updating the flag sent=true in the batch and update it
         */
        Runnable task = () -> 
             {
                 List<Testrecord> testrecords = null;
                    /*
                        Try for the recovery, if there is any failed record
                    */
                    
                    try 
                    {    
                        
                        doRestore(0); 
                    } 
                    catch (RecoveryRetriesLimitExceedException recoveryRetriesLimitExceedException) 
                    {
                     //Logger.getLogger(getClass().getName()).log(Level.SEVERE,"Unable To Update Failed Records",recoveryRetriesLimitExceedException);
                     executorService.shutdown();
                     System.exit(-1);
                     return;
                    }
                    
             
                  testrecords=testRecordService.fetchRecordNotSent();
            
              /*
                In each record from a batch insert the current database name.
             */
             testrecords.forEach(testrecord -> {
               testrecord.setMigratedFrom(dbName);
             });
             
             /*
                Use the RestTemplate to call REST END point.
             */
             restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
             /*
                 Prepare the http header as the transporting protocol is HTTPS 
              */
             HttpHeaders headers=new HttpHeaders();
             /*
                The batch will be sent in JSON format. 
                So the content-type header is set as application/json
             */
             headers.setContentType(MediaType.APPLICATION_JSON);
             String auth = "REST_CLIENT:INDIA";
             String password=new String(Base64.encode(auth.getBytes(Charset.forName("US-ASCII"))));
            //String password = new BCryptPasswordEncoder().encode(auth);
             String token = "Basic "+password;
              headers.set("Authorization",token);
             HttpEntity<List<Testrecord>> entity = new HttpEntity<>(testrecords,headers);
            
             RestConfiguration restConfiguration=xMLReadWriteService.readRestConfiguration();
             /*
               Prepare the REST end point URL.
             */
             String url = "https://"+restConfiguration.getIpaddress()+":"+restConfiguration.getPort()+"/"+batchUpdateURL;
             try {
                 /*
                   Call the REST end point and get the boolean answer for database migration.
                 */
                Boolean answer = restTemplate.postForObject(url, entity, Boolean.class);
               if(answer)
               {
                   /*
                      if the updation on server is successfull then update the sent=true 
                    */
                   if(testRecordService.batch25Update(testrecords))
                   {
                       System.out.println("Updation done on both side");
                   }
                   else
                   {
                       /*
                          If the server already updated and client is not able to update
                       */   
                      logFileService.createAndStore(testrecords);
                   }
                   
               }
               else
               {
                   System.out.println("Not Updated");
               }
            } catch (Exception e) 
            {
                logFileService.marker();
                try {
                    doRetrieveFromServerAndUpdate(0);
                    logFileService.removeFailedLogFile();
                } catch (Exception e1) 
                {
                    
                    suyojanLogger.log(this.getClass(), Level.FATAL, networkFailMessage,e1);
                    System.out.println("Kindly refer the "+suyojanLogger.getLogFilePath());
                    executorService.shutdown();
                    System.exit(0);
                }
            }
             
            
        };
          executorService.scheduleAtFixedRate(task, 0, repeatition_time, TimeUnit.MINUTES);
        } catch (Exception e) 
          {
              e.printStackTrace();
          }
        
        
    }
    
    
    private void doRestore(int again) throws RecoveryRetriesLimitExceedException
    {
        /*
        Check if there is any failed record available or not
         */
        if (logFileService.isLogAvailable()) {
            /*
              Recover all failed records
            */
            List<Testrecord> testrecords = logFileService.recoverFailedRecord();
            /*
               If the logfile is empty or accedently empty
            */
            
                if(testrecords.isEmpty())
                {
                    /*
                        Start Communication with server to get lastly sent items
                        and update the record.
                    */
                    doRetrieveFromServerAndUpdate(0);    
                }
                else
                {
                    /*
                       Try to update it
                     */
                    if (testRecordService.batch25Update(testrecords)) {
                        logFileService.removeFailedLogFile();
                    } else {
                        /*
                            if updation failed Try Again
                         */

                        if (again < retries) {
                            again++;
                            try {
                                Thread.sleep(1000 * 15);
                            } catch (Exception e1) {
                            }
                            doRestore(again);
                        } else {
                            throw new RecoveryRetriesLimitExceedException(retries);
                        }
                    }
                }
            
        }
        else
        {
            /*
            No Need to restore
            */
            return;
        }
    }
    
    private void doRetrieveFromServerAndUpdate(int again) throws RecoveryRetriesLimitExceedException
    {
        try {
                RestConfiguration restConfiguration = xMLReadWriteService.readRestConfiguration();
                String url = "https://" + restConfiguration.getIpaddress() + ":" + restConfiguration.getPort() + "/" + retrieveURL + "/{migratedFrom}";
                HttpHeaders headers = new HttpHeaders();
                String auth = "REST_CLIENT:INDIA";
                String password = new String(Base64.encode(auth.getBytes(Charset.forName("US-ASCII"))));
                //String password = new BCryptPasswordEncoder().encode(auth);
                String token = "Basic " + password;
                headers.set("Authorization", token);
                Map<String, String> urlMap = new HashMap<>();
                urlMap.put("migratedFrom", dbName);
                UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
                URI postURI = builder.buildAndExpand(urlMap).toUri();
                HttpEntity entity = new HttpEntity(headers);
                Testrecord [] testrecords_response = restTemplate.postForObject(postURI, entity,Testrecord[].class);
                List<Testrecord> testrecords = Arrays.asList(testrecords_response);
                
                testrecords.stream().forEach(testrecord -> {
                    testrecord.setId(testrecord.getOldId());
                    testrecord.getTestresultCollection().stream().forEach(testresult -> {
                        testresult.setId(testresult.getOldid());
                    });
                });

                if (testRecordService.batch25Update(testrecords)) {
                    logFileService.removeFailedLogFile();
                } else {
                    if (again < retries) {
                       
                        again++;
                        suyojanLogger.log(this.getClass(),Level.FATAL , "Retrying ... "+again,new Throwable("Fail In Local Database Update"));
                        try {
                            Thread.sleep(1000 * 15);
                        } catch (Exception e1) {
                        }
                        doRetrieveFromServerAndUpdate(again);
                    } else {
                        throw new RecoveryRetriesLimitExceedException(retries);
                    }
                }    
        } catch (Exception e) 
        {
            System.out.println(e.getMessage());
            if(again < retries)
            {
                again++;
                
                suyojanLogger.log(this.getClass(), Level.FATAL, "Retrying ... "+again,e);
                try {
                Thread.sleep(1000*15);    
                } catch (Exception e1) {
                }
                
                doRetrieveFromServerAndUpdate(again);
            }
            else
                throw new RecoveryRetriesLimitExceedException(retries);
        }
        
        
    }
}