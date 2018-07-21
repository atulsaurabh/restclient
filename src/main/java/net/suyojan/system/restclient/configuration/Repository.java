/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.suyojan.system.restclient.configuration;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 *
 * @author Suyojan
 */

@JacksonXmlRootElement
public class Repository {
    
    @JacksonXmlProperty
    private Database database;
    @JacksonXmlProperty(localName = "destination")
    private Database destinationdatabase;
    
    
    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public Database getDestinationdatabase() {
        return destinationdatabase;
    }

    public void setDestinationdatabase(Database destinationdatabase) {
        this.destinationdatabase = destinationdatabase;
    }
    
    
}
