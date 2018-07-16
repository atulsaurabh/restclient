/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.suyojan.system.restclient.configuration;

import java.util.Properties;
import javax.sql.DataSource;
import net.suyojan.system.restclient.background.BackgroundTransmitionJob;
import net.suyojan.system.restclient.service.XMLReadWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author Suyojan
 */

@Configuration
@EnableTransactionManagement
public class DatabaseConfiguration 
{
    @Autowired
    private XMLReadWriteService xMLReadWriteService;
  
    
    @Bean
    public DataSource dataSource()
    {
        RestConfiguration configuration=xMLReadWriteService.readRestConfiguration();
        System.out.println(configuration.getRepository().getDatabase().getDbname());
        DriverManagerDataSource driverManagerDataSource=new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://"+configuration.getRepository().getDatabase().getIpaddress()+
                                 ":"+configuration.getRepository().getDatabase().getPort()+
                                "/"+configuration.getRepository().getDatabase().getDbname();
        driverManagerDataSource.setUrl(url);
        driverManagerDataSource.setUsername(configuration.getRepository().getDatabase().getUser());
        driverManagerDataSource.setPassword(configuration.getRepository().getDatabase().getPassword());
        return driverManagerDataSource;
    }
    
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory()
    {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = 
                new LocalContainerEntityManagerFactoryBean();
        
        entityManagerFactory.setDataSource(dataSource());
        HibernateJpaVendorAdapter vendorAdapter=new HibernateJpaVendorAdapter();
        entityManagerFactory.setJpaVendorAdapter(vendorAdapter);
        
        Properties properties = new Properties();
        properties.put("hibernate.dialect","org.hibernate.dialect.MySQL5Dialect");
        properties.put("hibernate.show_sql","true");
        properties.put("hibernate.format_sql","true");
        properties.put("hibernate.hbm2ddl.auto","update");
        entityManagerFactory.setJpaProperties(properties);
        
        return entityManagerFactory;
    }
    
    @Bean
    public JpaTransactionManager transactionManager()
    {
        JpaTransactionManager transactionManager=new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }
    
    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation()
    {
        return new PersistenceExceptionTranslationPostProcessor();
    }
    
    @Bean
    public BackgroundTransmitionJob backgroundTransmitionJob()
    {
        return new BackgroundTransmitionJob();
    }
}
