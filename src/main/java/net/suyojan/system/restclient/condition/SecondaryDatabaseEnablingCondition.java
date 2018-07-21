/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.suyojan.system.restclient.condition;

import net.suyojan.system.restclient.configuration.Database;
import net.suyojan.system.restclient.service.XMLReadWriteService;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 *
 * @author Suyojan
 */
public class SecondaryDatabaseEnablingCondition extends SpringBootCondition{
  
    private XMLReadWriteService xMLReadWriteService;

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) 
    {
        
        xMLReadWriteService = context.getBeanFactory().createBean(XMLReadWriteService.class);
        Database seconDatabase = xMLReadWriteService.readRestConfiguration().getRepository().getDestinationdatabase();        
        ConditionMessage.Builder message = ConditionMessage.forCondition("secondarydatabasenotfound");
        if(seconDatabase == null)
        return ConditionOutcome.noMatch(message.didNotFind("Secondary Database Not Available").atAll());
        else
            return ConditionOutcome.match();
    }
    
}
