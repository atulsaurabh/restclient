/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.suyojan.system.restclient.fx.controller;

import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import net.suyojan.system.restclient.configuration.Database;
import net.suyojan.system.restclient.configuration.Modes;
import net.suyojan.system.restclient.configuration.RestConfiguration;
import net.suyojan.system.restclient.service.XMLReadWriteService;
import net.suyojan.system.restclient.util.RestClientUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Suyojan
 */

@Controller
public class D2DPropertyConfiguration 
{
    @FXML
    private TextField sourceHostName;
    @FXML
    private TextField sourcePortNumber;
    @FXML
    private TextField sourceDatabaseName;
    @FXML
    private TextField sourceUserName;
    @FXML
    private TextField sourceUserPassword;
    @FXML
    private TextField destinationHostName;
    @FXML
    private TextField destinationPortNumber;
    @FXML
    private TextField destinationDatabaseName;
    @FXML
    private TextField destinationUserName;
    @FXML
    private TextField destinationUserPassword;
    
    @Autowired
    private XMLReadWriteService xMLReadWriteService;
    
    @Autowired
    private RestClientUtility utility;
    
    @FXML
    public void initialize()
    {
        RestConfiguration restConfiguration=xMLReadWriteService.readRestConfiguration();
        Database database = restConfiguration.getRepository().getDatabase();
        if(database != null)
        {
        sourceHostName.setText(database.getIpaddress());
        sourcePortNumber.setText(String.valueOf(database.getPort()));
        sourceDatabaseName.setText(database.getDbname());
        sourceUserName.setText(database.getUser());
        sourceUserPassword.setText(database.getPassword());
        }
        
        Database destinationdatabase = restConfiguration.getRepository().getDestinationdatabase();
        if(destinationdatabase != null)
        {
            destinationHostName.setText(destinationdatabase.getIpaddress());
            destinationPortNumber.setText(String.valueOf(destinationdatabase.getPort()));
            destinationDatabaseName.setText(destinationdatabase.getDbname());
            destinationUserName.setText(destinationdatabase.getUser());
            destinationUserPassword.setText(destinationdatabase.getPassword());
        }
    }
    
    @FXML
    public void d2dConfiguration(ActionEvent actionEvent)
    {
      RestConfiguration restConfiguration = xMLReadWriteService.readRestConfiguration();
      
      Database database = restConfiguration.getRepository().getDatabase();
      database.setDbname(sourceDatabaseName.getText());
      database.setIpaddress(sourceHostName.getText());
      database.setPort(Integer.parseInt(sourcePortNumber.getText()));
      database.setUser(sourceUserName.getText());
      database.setPassword(sourceUserPassword.getText());
      
      Database destinationDatabase = restConfiguration.getRepository().getDestinationdatabase();
      if(destinationDatabase == null)
      {
          destinationDatabase = new Database();
          restConfiguration.getRepository().setDestinationdatabase(destinationDatabase);
      }
      destinationDatabase.setDbname(destinationDatabaseName.getText());
      destinationDatabase.setIpaddress(destinationHostName.getText());
      destinationDatabase.setPort(Integer.parseInt(destinationPortNumber.getText()));
      destinationDatabase.setUser(destinationUserName.getText());
      destinationDatabase.setPassword(destinationUserPassword.getText());
      restConfiguration.setMode(Modes.MODE_DATABASE_DIRECT);
      if(xMLReadWriteService.updateRestConfiguration(restConfiguration))
      {
          Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(utility.getValue("window.database.alert.success.message"));
            alert.setHeaderText(utility.getValue("window.database.alert.success.headertext"));
            alert.setTitle(utility.getValue("window.database.alert.success.title"));
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
               System.exit(0);           
            }
            else
            {
                alert.close();
                Alert notShutdown = new Alert(Alert.AlertType.INFORMATION);
                notShutdown.setContentText(utility.getValue("window.database.alert.shutdownnotselected.message"));
                notShutdown.setTitle(utility.getValue("window.database.alert.shutdownnotselected.title"));
                notShutdown.setHeaderText(utility.getValue("window.database.alert.shutdownnotselected.headertext"));
                notShutdown.show();
            }
      }
      else
      {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Database Configuration Not Done Succesfully");
            alert.setTitle("Failure");
            alert.show();
      }
    }
    
    
}
