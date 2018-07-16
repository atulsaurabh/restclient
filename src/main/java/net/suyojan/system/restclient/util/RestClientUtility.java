/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.suyojan.system.restclient.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

/**
 *
 * @author Suyojan
 */
public class RestClientUtility 
{
    @Autowired
    private Environment environment;
    
    
    public String getValue(String key)
    {
        return environment.getProperty(key);
    }
}
