/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.suyojan.system.restclient.background;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Suyojan
 */
@Controller
public class BackgroundTransmitionJob 
{
    @Autowired
    private TestRecordService testRecordService;
    
    @Value("${fixed.repeat}")
    private long repeatition_time;
    
    @Value("${rest.endpoint.update}")
    private String batchUpdateURL;
    
    @Value("${current.dbName}")
    private String dbName;
    
    @Autowired
    private XMLReadWriteService xMLReadWriteService;
    
    @Autowired
    private RestTemplate restTemplate;
    
    public void submitTask()
    {
        
        try {
           
         ScheduledExecutorService executorService=Executors.newSingleThreadScheduledExecutor();
        Runnable task = () -> {
             List<Testrecord> testrecords=testRecordService.fetchRecordNotSent(); 
             testrecords.forEach(testrecord -> {
               testrecord.setMigratedFrom(dbName);
             });
                     
             restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
             HttpHeaders headers=new HttpHeaders();
             headers.setContentType(MediaType.APPLICATION_JSON);
             HttpEntity<List<Testrecord>> entity = new HttpEntity<>(testrecords,headers);
             RestConfiguration restConfiguration=xMLReadWriteService.readRestConfiguration();
             String url = "https://"+restConfiguration.getIpaddress()+":"+restConfiguration.getPort()+"/"+batchUpdateURL;
             try {
                Boolean answer = restTemplate.postForObject(url, entity, Boolean.class);
               if(answer)
               {
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