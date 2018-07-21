package net.suyojan.system.restclient.configuration;

import java.util.Properties;
import javax.sql.DataSource;
import net.suyojan.system.restclient.condition.SecondaryDatabaseEnablingCondition;
import net.suyojan.system.restclient.service.XMLReadWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "secondaryEntityManagerFactory",
        transactionManagerRef = "secondaryTransactionManager",
        basePackages = "net.suyojan.system.restclient.repository.secondaryrepository"
)
@Conditional(SecondaryDatabaseEnablingCondition.class)
public class SecondaryDatabaseConfiguration {
   @Autowired
    private XMLReadWriteService xMLReadWriteService;
  
    
    @Bean(name = "secobdaryDataSource")
    public DataSource secondaryDataSource()
    {
        RestConfiguration configuration=xMLReadWriteService.readRestConfiguration();
        DriverManagerDataSource driverManagerDataSource=new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://"+configuration.getRepository().getDestinationdatabase().getIpaddress()+
                                 ":"+configuration.getRepository().getDestinationdatabase().getPort()+
                                "/"+configuration.getRepository().getDestinationdatabase().getDbname();
        driverManagerDataSource.setUrl(url);
        driverManagerDataSource.setUsername(configuration.getRepository().getDestinationdatabase().getUser());
        driverManagerDataSource.setPassword(configuration.getRepository().getDestinationdatabase().getPassword());    
        return driverManagerDataSource;
    }
    
    @Bean(name = "secondaryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactory()
    {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = 
                new LocalContainerEntityManagerFactoryBean();
        
        entityManagerFactory.setDataSource(secondaryDataSource());
        HibernateJpaVendorAdapter vendorAdapter=new HibernateJpaVendorAdapter();
        entityManagerFactory.setJpaVendorAdapter(vendorAdapter);
        
        Properties properties = new Properties();
        properties.put("hibernate.dialect","org.hibernate.dialect.MySQL5Dialect");
       // properties.put("hibernate.show_sql","true");
        //properties.put("hibernate.format_sql","true");
        properties.put("hibernate.hbm2ddl.auto","update");
        entityManagerFactory.setJpaProperties(properties);
        
        return entityManagerFactory;
    }
    
    @Bean(name = "secondaryTransactionManager")
    public JpaTransactionManager transactionManager()
    {
        JpaTransactionManager transactionManager=new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(secondaryEntityManagerFactory().getObject());
        return transactionManager;
    }
    
    /*@Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation()
    {
        return new PersistenceExceptionTranslationPostProcessor();
    } */
}
