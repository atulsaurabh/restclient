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
import net.suyojan.system.restclient.configuration.Repository;
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
public class DatabasePropertyController {

    @FXML
    private TextField databaseName;
    @FXML
    private TextField ipAddress;
    @FXML
    private TextField port;
    @FXML
    private TextField userName;
    @FXML
    private TextField userPassword;
    
    @FXML
    private TextField hostName;
    
    @FXML
    private TextField portNumber;

    @FXML
    private TextField clientName;

    @FXML
    private TextField repeatdelay;

    @Autowired
    private XMLReadWriteService xMLReadWriteService;

    @Autowired
    private RestClientUtility utility;
    
    @FXML
    public void initialize()
    {
       RestConfiguration restConfiguration = xMLReadWriteService.readRestConfiguration();
       Database database = restConfiguration.getRepository().getDatabase();
       hostName.setText(restConfiguration.getIpaddress());
       portNumber.setText(String.valueOf(restConfiguration.getPort()));
       databaseName.setText(database.getDbname());
       ipAddress.setText(database.getIpaddress());
       port.setText(String.valueOf(database.getPort()));
       userName.setText(database.getUser());
       userPassword.setText(database.getPassword());
       clientName.setText(restConfiguration.getDbName());
       repeatdelay.setText(String.valueOf(restConfiguration.getDelay()));
       
    }

    @FXML
    public void changeDatabaseProperty(ActionEvent actionEvent) {
        
        RestConfiguration restConfiguration = xMLReadWriteService.readRestConfiguration();
        restConfiguration.setMode(Modes.MODE_DATABASE_REST);
        restConfiguration.setIpaddress(hostName.getText());
        restConfiguration.setPort(Integer.parseInt(portNumber.getText()));
        restConfiguration.setDelay(Long.parseLong(repeatdelay.getText()));
        restConfiguration.setDbName(clientName.getText());
        Repository resRepository = restConfiguration.getRepository();

        resRepository.setDestinationdatabase(null);
        Database database = new Database();
        database.setDbname(databaseName.getText());
        database.setIpaddress(ipAddress.getText());
        database.setPort(Integer.parseInt(port.getText()));
        database.setUser(userName.getText());
        database.setPassword(userPassword.getText());
        resRepository.setDatabase(database);

        if (xMLReadWriteService.updateRestConfiguration(restConfiguration)) {
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

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Database Configuration Not Done Succesfully");
            alert.setTitle("Failure");
            alert.show();
        }
    }
}
