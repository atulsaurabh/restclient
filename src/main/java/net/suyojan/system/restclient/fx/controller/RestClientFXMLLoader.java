/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.suyojan.system.restclient.fx.controller;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.suyojan.system.restclient.fx.GUIInitializer;
import net.suyojan.system.restclient.util.RestClientUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author Suyojan
 */
public class RestClientFXMLLoader
{
    @Autowired
    private ApplicationContext context;
    
    @Autowired
    private RestClientUtility restClientUtility;
    
    public Parent load(String guiName)
    {
        FXMLLoader loader=new FXMLLoader();
        loader.setControllerFactory(context::getBean);
        loader.setLocation(getClass().getResource(GUIInitializer.GUI_HOME+guiName));
        try {
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    
    public void createStage(String uiname,String titleKey)
    {
        Stage stage=new Stage();
        stage.setTitle(restClientUtility.getValue(titleKey));
        stage.setScene(new Scene(load(uiname)));
        stage.setResizable(false);
        stage.show();
    }
    
}
