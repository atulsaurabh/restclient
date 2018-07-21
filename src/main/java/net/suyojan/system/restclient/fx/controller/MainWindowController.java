
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
      repoTypeComboBox.getItems().addAll(GUIInitializer.DATABASE_REST,
                                         GUIInitializer.DATABASE_DIRECT,
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
              case GUIInitializer.DATABASE_REST:
                    restClientFXMLLoader.createStage(UINames.DATABASE_REST_CONFIGURE_WINDOW, "window.databaseconfigure.title");
                  break;
              case GUIInitializer.DATABASE_DIRECT:
                  restClientFXMLLoader.createStage(UINames.DATABASE_DIRECT_CONFIGURE_WINDOW, "window.direct.databasewindow.title");
                  break;
              case GUIInitializer.EXCEL:
                  break;
          }
        }
    }
    
}
