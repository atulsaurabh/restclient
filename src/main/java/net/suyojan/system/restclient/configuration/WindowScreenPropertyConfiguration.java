/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.suyojan.system.restclient.configuration;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.suyojan.system.restclient.fx.controller.RestClientFXMLLoader;
import net.suyojan.system.restclient.util.RestClientUtility;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;


/**
 *
 * @author Suyojan
 */

@Configuration
@ComponentScan(basePackages = {"net.suyojan.system.restclient.fx.controller"})
@PropertySources({
   @PropertySource("classpath:window.properties")
})
public class WindowScreenPropertyConfiguration {
   
    @Bean
    public RestClientUtility restClientUtility()
    {
        return new RestClientUtility();
    }
    
    @Bean
    public RestClientFXMLLoader springFXMLLoader()
    {
        return new RestClientFXMLLoader();
    }
    
}
