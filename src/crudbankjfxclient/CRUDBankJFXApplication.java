/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crudbankjfxclient;

import clientside.controller.CustomerManager;
import clientside.controller.CustomerManagerFactory;
import crudbankjfxclient.view.AccountMovementsController;
import crudbankjfxclient.view.ClientAccountsController;
import crudbankjfxclient.view.CustomerDataController;
import crudbankjfxclient.view.CustomerListController;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 *
 * @author javi
 */
public class CRUDBankJFXApplication extends Application {
    //Get URI from properties' values file.
    private static String CUSTOMER_ID = 
            ResourceBundle.getBundle("crudbankjfxclient.config.parameters")
                          .getString("CUSTOMER_ID");
    //Get URI from properties' values file.
    private static String SERVER_NAME = 
            ResourceBundle.getBundle("crudbankjfxclient.config.parameters")
                          .getString("SERVER_NAME");   
   
    @Override
    public void start(Stage stage) throws Exception {    
        //Get CustomerManager
        CustomerManager manager=CustomerManagerFactory.getCustomerManager();
        //Load view
        FXMLLoader loader=
                //new FXMLLoader(getClass().getResource("view/AccountMovementsView.fxml"));
                //new FXMLLoader(getClass().getResource("view/ClientAccountsView.fxml"));
                //new FXMLLoader(getClass().getResource("view/CustomerDataView.fxml"));
                new FXMLLoader(getClass().getResource("view/CustomerListView.fxml"));
        Parent root = loader.load();
        //Set manager for UI controller
        //AccountMovementsController controller=
                //(AccountMovementsController)loader.getController();
        //ClientAccountsController controller=
                //(ClientAccountsController)loader.getController();
        //CustomerDataController controller=
                //(CustomerDataController)loader.getController();
        CustomerListController controller=
                (CustomerListController)loader.getController();
        controller.setManager(manager);
        //set server name
        manager.setServerName(SERVER_NAME);
        controller.setStage(stage);
        //set customer 
        //Long customerId=new Long(CUSTOMER_ID);
        //controller.setCustomerId(customerId);
        controller.initStage(root);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
