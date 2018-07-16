/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.suyojan.system.restclient.fx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import net.suyojan.system.restclient.fx.GUIInitializer;
import net.suyojan.system.restclient.fx.UINames;
import net.suyojan.system.restclient.util.RestClientUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Suyojan
 */

@Controller
public class MainWindowController 
{
    @FXML
    private ComboBox<String> repoTypeComboBox;
    
    @Autowired
    private RestClientUtility restClientUtility;
    
    @Autowired
    private RestClientFXMLLoader restClientFXMLLoader;
    
    @FXML
    public void initialize() 
    {
      repoTypeComboBox.getItems().addAll(GUIInitializer.DATABASE,
                                 GUIInitializer.EXCEL,
                                 GUIInitializer.XML);
    }
    
    
    @FXML
    public void changeRepositoryChoice(ActionEvent actionEvent)
    {
        String repositoryType = repoTypeComboBox.getValue();
        if(repositoryType == null)
        {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(restClientUtility.getValue("window.configuration.repo.not.selected"));
            alert.setTitle(restClientUtility.getValue("window.configuration.repo.not.selected.title"));
            alert.show();
        }
        else
        {
          switch(repositoryType)
          {
              case GUIInitializer.DATABASE:
                    restClientFXMLLoader.createStage(UINames.DATABASE_CONFIGURE_WINDOW, "window.databaseconfigure.title");
                  break;
              case GUIInitializer.XML:
                  break;
              case GUIInitializer.EXCEL:
                  break;
          }
        }
    }
    
}
