/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.suyojan.system.restclient.service;

import java.util.List;
import net.suyojan.system.restclient.entity.Testrecord;
import net.suyojan.system.restclient.repository.TestRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Suyojan
 */

@Service
@Transactional
public class TestRecordService 
{
    @Autowired
    private TestRecordRepository testRecordRepository;

   @Transactional 
    public List<Testrecord> fetchRecordNotSent() {
        return testRecordRepository.findTop25BySent(false);
    }
    
    @Transactional
    public boolean batch25Update(List<Testrecord> testrecords)
    {
        for(Testrecord ts : testrecords)
        {
            ts.setSent(true);
        }
        try {
        testRecordRepository.saveAll(testrecords);
        return true;    
        } catch (Exception e) {
            return false;
        }
        
    }
    
}
