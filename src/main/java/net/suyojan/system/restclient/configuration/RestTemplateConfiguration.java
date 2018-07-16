/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.suyojan.system.restclient.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author atul_saurabh
 */

@Configuration
public class RestTemplateConfiguration 
{
    
    @Value("${ssl.keystore.password}")
    private String keyStorePassword;
    
   @Bean 
   public RestTemplate restTemplate() throws KeyStoreException, 
                                             NoSuchAlgorithmException, 
                                             KeyManagementException,
                                             FileNotFoundException,
                                             IOException,
                                             CertificateException,
                                             UnrecoverableKeyException
   {
       KeyStore keyStore = KeyStore.getInstance("PKCS12");
       FileInputStream fis = new FileInputStream(new File("keystore.p12"));
       keyStore.load(fis, keyStorePassword.toCharArray());
       //TrustStrategy trustStrategy = (chain,authType) -> true;
       SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null,new TrustSelfSignedStrategy())
                                                                       .loadKeyMaterial(keyStore, keyStorePassword.toCharArray())
                                                                       .build();
       SSLConnectionSocketFactory scf = new SSLConnectionSocketFactory(sslContext,new HostnameVerifier() {
           @Override
           public boolean verify(String string, SSLSession ssls) {
               return true;
           }
       });
       CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(scf).build();
       HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
       requestFactory.setHttpClient(httpClient);
       RestTemplate restTemplate = new RestTemplate(requestFactory);
       return restTemplate;
   }
}
