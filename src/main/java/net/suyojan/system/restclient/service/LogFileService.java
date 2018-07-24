/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.suyojan.system.restclient.service;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.suyojan.system.restclient.entity.Testrecord;
import net.suyojan.system.restclient.repository.FailedLogFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Suyojan
 */


@Service
public class LogFileService {
    @Autowired
    private FailedLogFileRepository failedLogFileRepository;
    
    
    public boolean createAndStore(List<Testrecord> testrecords)
    {
       return failedLogFileRepository.insertFailedItems(testrecords);
    }
    
    public boolean isLogAvailable()
    {
        return failedLogFileRepository.isLogFileAvailable();
    }
    
    public List<Testrecord> recoverFailedRecord()
    {
        try {
        return failedLogFileRepository.getFailedRecords();    
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Unable to Read Failed Log File ",e);
            return null;
        }
        
    }
    
    
    public boolean removeFailedLogFile()
    {
        return failedLogFileRepository.deleteLogFile();
    }
    
    public void marker()
    {
        try {
            failedLogFileRepository.createMarker();
        } catch (Exception e) {
        }
        
    }
}
