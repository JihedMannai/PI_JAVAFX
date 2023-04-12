/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gestionuser;

import Entities.user;
import Service.ServiceUser;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author jihed zavou
 */
public class UserbackController implements Initializable {

    @FXML
    private TableView<user> table_view;
    @FXML
    private TableColumn<String,user> nom_c;
    @FXML
    private TableColumn<String,user> prenom_c;
    @FXML
    private TableColumn<String,user> adresse_c;
    @FXML
    private TableColumn<Date,user> date_c;
    @FXML
    private TableColumn<String,user> role_c;
    @FXML
    private TableColumn<String,user> roles_c;
    @FXML
    private TableColumn<String,user> email_c;
    @FXML
    private TextField nom_text;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateTable() ; 
    }    
    
    public void populateTable(){
        ServiceUser ser= new ServiceUser();
        
        List<user> li =ser.ListClasse();
                 ObservableList<user> data = FXCollections.observableArrayList(li);
                  

        
          
        nom_c.setCellValueFactory(
                new PropertyValueFactory<>("nom"));
 
       
        prenom_c.setCellValueFactory(
                new PropertyValueFactory<>("prenom"));
 
        
        adresse_c.setCellValueFactory(
                new PropertyValueFactory<>("adresse"));
        
        email_c.setCellValueFactory(
                new PropertyValueFactory<>("email"));
        
        role_c.setCellValueFactory(
                new PropertyValueFactory<>("role"));
        
        roles_c.setCellValueFactory(
                new PropertyValueFactory<>("roles"));
        date_c.setCellValueFactory(
                new PropertyValueFactory<>("date_naissance"));
        
        
        
        
        table_view.setItems(data);
    }

    @FXML
    private void supprimer(ActionEvent event) {
        
        
         ServiceUser sc=new ServiceUser();
        user c =new user();
        c=table_view.getSelectionModel().getSelectedItem();  
   nom_text.setText(c.getNom());  
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirmation de Suppression !");
        alert.setContentText("Voulez-Vous Vraiment Supprimer");
        
        Optional<ButtonType> btn = alert.showAndWait();
        if(btn.get() == ButtonType.OK){
            sc.supprimer(c);
            populateTable();
            
         nom_text.clear();
      
        }
        else{
            alert.close();
        } 
    }
    
   @FXML
    private void logout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        
        loader.setLocation(getClass().getResource("authentification.fxml"));
        Parent root = loader.load();
        nom_text.getScene().setRoot(root);
    }
    
}
