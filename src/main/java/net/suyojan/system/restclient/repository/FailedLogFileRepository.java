/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.suyojan.system.restclient.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.suyojan.system.restclient.entity.Testrecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Suyojan
 */

@Repository
public class FailedLogFileRepository 
{
     @Value("${failed.operation.log.filename}")
     private String failedLogFile;
     
   public boolean insertFailedItems(List<Testrecord> testrecords)
   {
       try {
             if (isLogFileAvailable()) {
               if (deleteLogFile()) {
                   if (createLogFile()) {
                       ObjectMapper mapper=new ObjectMapper();
                       mapper.enable(SerializationFeature.INDENT_OUTPUT);
                       mapper.writeValue(getLogFile(), testrecords);
                       return true;
                   } else {
                       Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Log File Not Created ");
                       return false;
                   }
               } else {
                   return false;
               }

           } 
             else
             {
                if(createLogFile())
                {
                    ObjectMapper mapper=new ObjectMapper();
                       mapper.enable(SerializationFeature.INDENT_OUTPUT);
                       mapper.writeValue(getLogFile(), testrecords);
                       return true;
                }
                else
                {
                    return false;
                }
             }
       } catch (IOException e) 
       {
           Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Unable To Create Log File "+e.getMessage(),e);
           return false;
       }
   }
   
   
   private File getLogFile()
   {
       String rootPath = getClass().getResource("/").getPath();
       File file = new File(rootPath+"/"+failedLogFile);
       return file;
   }
   
   public boolean isLogFileAvailable()
   {
       return getLogFile().exists();
   }
   
   public boolean deleteLogFile()
   {
    return getLogFile().delete();
   }
   
   private boolean createLogFile() throws IOException
   {
       
       return getLogFile().createNewFile();
   }
   
   
   public List<Testrecord> getFailedRecords() throws IOException
   {
      File logFile = getLogFile();
      ObjectMapper mapper=new ObjectMapper();
      List<Testrecord> testrecords=mapper.readValue(logFile, List.class);
      return testrecords;
   }
   
   public void createMarker() throws IOException
   {
       insertFailedItems(new ArrayList<>());
   }
}
