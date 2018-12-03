/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.suyojan.system.restclient.service;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.suyojan.system.restclient.configuration.HomeConfiguration;
import net.suyojan.system.restclient.configuration.RestClientXMLConfiguration;
import net.suyojan.system.restclient.configuration.RestConfiguration;
import net.suyojan.system.restclient.configuration.RestPropertyConfiguration;
import org.springframework.stereotype.Service;

/**
 *
 * @author Suyojan
 * 
 */

@Service
public class XMLReadWriteService 
{
   
    public RestConfiguration readRestConfiguration()
    {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            File file = new File(HomeConfiguration.SUYOJAN_HOME+"/"+RestPropertyConfiguration.REST_XML_CONFIGURATION_FILE);
            RestConfiguration restConfiguration = xmlMapper.readValue(new FileInputStream(file), RestConfiguration.class);
            return restConfiguration;
        } catch (FileNotFoundException e) {
            Logger.getLogger(RestClientXMLConfiguration.class.getName()).log(Level.SEVERE, "Configuration XML File Does Not Exist",e);
        } catch (IOException ioe) {
            Logger.getLogger(RestClientXMLConfiguration.class.getName()).log(Level.SEVERE, "Unable to Read Configuration XML File",ioe);
        }
        return null;
    }
    
    public boolean updateRestConfiguration(RestConfiguration restConfiguration)
    {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
            File file = new File(HomeConfiguration.SUYOJAN_HOME+"/"+RestPropertyConfiguration.REST_XML_CONFIGURATION_FILE);

            xmlMapper.writeValue(new FileOutputStream(file), restConfiguration);
            return true;
        } catch (IOException e) {
            Logger.getLogger(XMLReadWriteService.class.getName()).log(Level.SEVERE, "Unable To Update Configuration", e);
        }
   
     return false; 
    }
}
