/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gestionuser;

import Entities.user;
import Service.ServiceUser;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author jihed zavou
 */
public class AuthentificationController implements Initializable {

    @FXML
    private TextField email_text;
    @FXML
    private TextField password_text;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void login(ActionEvent event) throws IOException, InterruptedException {
        
        ServiceUser ser= new ServiceUser();
        
        List<user> li =ser.ListClasse(); 
       
        int i = 0 ;  
         
        
        for ( i=0 ; i<li.size();i++){
        if (li.get(i).getEmail().equals(email_text.getText()) && (li.get(i).getPassword().equals(password_text.getText())))
        
        { 
            System.out.println("success");
            FXMLLoader loader = new FXMLLoader();
        
        loader.setLocation(getClass().getResource("userback.fxml"));
        
        
        
        Parent root = loader.load();
        email_text.getScene().setRoot(root);  }
        else {            System.out.println("failed");
}
        
        
        
        }
           
        
    }
    @FXML
    private void signup(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        
        loader.setLocation(getClass().getResource("user_Sign_up.fxml"));
        Parent root = loader.load();
        email_text.getScene().setRoot(root);
    }
    
}
