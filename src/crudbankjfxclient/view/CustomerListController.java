/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crudbankjfxclient.view;

import clientside.model.Account;
import clientside.model.Customer;
import clientside.controller.CustomerManager;
import clientside.model.Movement;
import java.net.URL;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author lorea
 */
public class CustomerListController  {

    @FXML
    private Button btExit;
    @FXML
    private TextField tfBalance;
    @FXML
    private TableView<?> tbCustomer;
    @FXML
    private TableColumn<?, ?> tcId;
    @FXML
    private TableColumn<?, ?> tcFirstName;
    @FXML
    private TableColumn<?, ?> tcLastName;
    @FXML
    private TableColumn<?, ?> tcPhone;
    @FXML
    private TableColumn<?, ?> tcTotalBalance;
    @FXML
    private Label lbCustomrt;

    private CustomerManager manager;
    
    private Long customerId;
    
    private Stage stage;
    
    private  List<Customer> customerList;

    private final Logger LOGGER=Logger.getLogger("crudbankjfxclient.view");

    public void setManager(CustomerManager manager) {
        this.manager=manager;
    }
    /**
     * @param customerId the customerId to set
     */
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    /**
     * @param stage the stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
     
    public void initStage(Parent root){
        try{
            LOGGER.info("Initializing Clients Positions view.");
            //set scene and view DOM root
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Customer's positions");
            stage.setResizable(false);
            
            
            //set cell factory values for table columns
            tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
            tcFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            tcLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            tcPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
            //tcBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
         
            customerList= manager.getAllCustomer();
            int  CantidadClientes= customerList.size();
            if(customerList!=null)
                for(int i=0;i<=CantidadClientes;i+=1){
                    Customer customer=manager.getCustomerAccountsFullInfo(customerList.get(i).getId());
                    List<Account> accounts=customer.getAccounts();
                    //if the client has no accounts
                    if(accounts==null)
                        throw new NoSuchElementException("Client has no Accounts");
                    //set total balance as the sum of each account balance
                    Double totalBalance= accounts.stream()
                    .mapToDouble(Account::getBalance)
                    .sum();
                    //tfBalance.setText(totalBalance.toString());
                   }
           
          
            
            // if(customerList==null)
            //     throw new NoSuchElementException("No exist Clients");
            //if(customerList!=null)
                //tbCustomer.setItems(FXCollections.observableList(customerList));
            
            
            //align amounts
            tcId.setStyle("-fx-alignment: center-right;");
            tcPhone.setStyle("-fx-alignment: center-right;");
            tcTotalBalance.setStyle("-fx-alignment: center-right;");
            
            //disable balance
            tfBalance.setDisable(true);
            //focus on table
            tbCustomer.requestFocus();
            //set event handlers
          
            btExit.setOnAction(this::handleExitAction);
            stage.setOnCloseRequest(this::handleExitAction);
            //show window
            stage.show();
            LOGGER.info("Clients Positions view initialized.");
            }
        catch(Exception e){
            String errorMsg="Error opening window:\n" +e.getMessage();    
            this.showErrorAlert(errorMsg);
            LOGGER.log(Level.SEVERE,errorMsg);            
        }    
        }
    
    /**
     * Initializes the controller class.
     */
  
    

    public void handleExitAction(Event event){
        try{
            //Ask user for confirmation on exit
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION,
                                        "¿Are you sure you want to exit?",
                                        ButtonType.OK,ButtonType.CANCEL);
            Optional<ButtonType> result = alert.showAndWait();
            //If OK to exit
            if (result.isPresent() && result.get() == ButtonType.OK)
                Platform.exit();
            else event.consume();
        }catch(Exception e){
                    String errorMsg="Error exiting application:" +e.getMessage();    
                    this.showErrorAlert(errorMsg);
                    LOGGER.log(Level.SEVERE,errorMsg);            
        }
    }
    
    protected void showErrorAlert(String errorMsg){
        //Shows error dialog.
        Alert alert=new Alert(Alert.AlertType.ERROR,
                              errorMsg,
                              ButtonType.OK);
        alert.showAndWait();
        
    }
}

