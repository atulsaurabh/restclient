/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.suyojan.system.restclient.util;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;


import org.apache.log4j.PatternLayout;
import org.apache.log4j.Priority;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author Suyojan
 */
public class SuyojanLogger 
{
    @Value("${log.dir.name}")
    private String logDirPath;
    
    private String logfilepath;

    
     public void log(Class clas,Level level,String message,Throwable th)
     {
        
         try {
            URI logDIRFullName = this.getClass().getResource("/").toURI();
            String normalizePath = Paths.get(logDIRFullName).toString();
            
            String fileName = "abblog.log";
            logfilepath = normalizePath + "/" + logDirPath+"/"+fileName;
            PatternLayout layout = new PatternLayout();
        String conversionPattern = "[%p] %d %c %M - %m%n";
        layout.setConversionPattern(conversionPattern);
 
        // creates daily rolling file appender
         DailyRollingFileAppender rollingAppender = new DailyRollingFileAppender();
        rollingAppender.setFile(logfilepath);
        rollingAppender.setDatePattern("'.'yyyy-MM-dd");
        rollingAppender.setLayout(layout);
        rollingAppender.activateOptions();
 
        // configures the root logger
        /*Logger rootLogger = Logger.getRootLogger();
        rootLogger.setLevel(Level.DEBUG);
        rootLogger.addAppender(rollingAppender);*/
 
        // creates a custom logger and log messages
         Logger logger = Logger.getLogger(clas);
         logger.addAppender(rollingAppender);
         logger.setLevel(level);
         logger.fatal(message,th);
        } catch (Exception e) {
            e.printStackTrace();
        }
         
                 
     }
     
     public String getLogFilePath()
     {
         return logfilepath;
     }
}
