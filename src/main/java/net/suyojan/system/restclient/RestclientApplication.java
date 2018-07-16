package net.suyojan.system.restclient;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.suyojan.system.restclient.background.BackgroundTransmitionJob;
import net.suyojan.system.restclient.fx.GUIInitializer;
import net.suyojan.system.restclient.fx.UINames;
import net.suyojan.system.restclient.fx.controller.RestClientFXMLLoader;
import net.suyojan.system.restclient.service.XMLReadWriteService;
import net.suyojan.system.restclient.util.RestClientUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
public class RestclientApplication extends Application implements ApplicationRunner {

    
    private static Stage primaryStage;
    

    @Autowired
    private RestClientUtility restClientUtility;

    @Autowired
    private RestClientFXMLLoader springFXMLLoader;

    @Autowired
    private BackgroundTransmitionJob backgroundTransmitionJob;
    
    @Autowired
    private XMLReadWriteService xMLReadWriteService;

    /**
     * <b>The Main Method starts the application.</b>
     * <b> The software works in two different modes</b>
     *   
     *     <ul>
     *         <li>The configuration mode</li>
     *         <li>The Background Mode</li>
     *     </ul>
     *
     *
     * @param args if --gui is passed as a command line option then the software 
     *             opens in configuration mode.
     *             if no argument is passed then the software works in background
     *             mode.
     */
    
    public static void main(String[] args) {
        Application.launch(RestclientApplication.class, args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        String[] args = getParameters().getRaw().toArray(new String[getParameters().getRaw().size()]);
        SpringApplication.run(RestclientApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (args.containsOption(GUIInitializer.GUI_OPEN)) {
            primaryStage.setScene(new Scene(springFXMLLoader.load(UINames.MAIN_WINDOW)));
            primaryStage.setMaximized(false);
            primaryStage.setResizable(false);
            primaryStage.setTitle(restClientUtility.getValue("window.mainwindow.title"));
            primaryStage.show();
        } else {
            switch(xMLReadWriteService.readRestConfiguration().getMode())
            {
                case GUIInitializer.DATABASE:
                    backgroundTransmitionJob.submitTask();
                    break;
                case GUIInitializer.XML:
                    break;
                case GUIInitializer.EXCEL:
                    break;
            }
            
        }
    }

}
