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

@JacksonXmlRootElement(localName = "restconfiguration")
public class RestConfiguration 
{
  @JacksonXmlProperty(localName = "ipaddress")
   private String ipaddress;
  @JacksonXmlProperty(localName = "port")
   private int port;
  @JacksonXmlProperty(localName = "testingurl")
   private String testingurl;
  @JacksonXmlProperty(localName = "mode")
  private String mode;
  @JacksonXmlProperty(localName = "dbName")
  private String dbName;
    @JacksonXmlProperty(localName = "delay")
    private long delay;
   @JacksonXmlProperty(localName = "repository")


   private Repository repository;

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getTestingurl() {
        return testingurl;
    }

    public void setTestingurl(String testingurl) {
        this.testingurl = testingurl;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }
}
