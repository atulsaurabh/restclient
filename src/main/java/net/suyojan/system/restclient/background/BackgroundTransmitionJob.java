/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.suyojan.system.restclient.background;

import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import net.suyojan.system.restclient.configuration.RestConfiguration;
import net.suyojan.system.restclient.entity.Testrecord;
import net.suyojan.system.restclient.service.TestRecordService;
import net.suyojan.system.restclient.service.XMLReadWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

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
       Rest end point URL fetched properties file.
    */
    @Value("${rest.endpoint.update}")
    private String batchUpdateURL;
    
    @Value("${current.dbName}")
    private String dbName;
    
    @Autowired
    private XMLReadWriteService xMLReadWriteService;
    
    @Autowired
    private RestTemplate restTemplate;
    
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
            3. If server response with true then updating the flag sent=true
         */
        Runnable task = () -> {
             List<Testrecord> testrecords=testRecordService.fetchRecordNotSent();
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
                       System.out.println("Updation done on single side");
                   }
                   
               }
               else
               {
                   System.out.println("Not Updated");
               }
            } catch (Exception e) {
                e.printStackTrace();
            }
             
            
        };
          executorService.scheduleAtFixedRate(task, 0, repeatition_time, TimeUnit.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }
}