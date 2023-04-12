/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gestionuser;

import Entities.user;
import Service.ServiceUser;
import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author jihed zavou
 */
public class User_Sign_upController implements Initializable {

    @FXML
    private TextField nom_text;
    @FXML
    private TextField prenom_text;
    @FXML
    private TextField adresse_text;
    @FXML
    private DatePicker date_text;
    @FXML
    private ComboBox<String> role_text;
    @FXML
    private TextField email_text;
    @FXML
    private PasswordField password_text;
    @FXML
    private Button signup;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> list = FXCollections.observableArrayList("Visiteur","Participant");
         role_text.setItems(list);
    }    

    @FXML
    private void ajouterc(ActionEvent event) {
        
        String roles = "[\"ROLE_USER\"]" ;       
        ServiceUser su =new ServiceUser();
        
       // List<Client> li =sc.ListClasse(); 
 List<user> li =su.ListClasse(); 
       
        int i = 0 ;  
    java.util.Date date = 
    java.util.Date.from(date_text.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
    java.sql.Date sqlDate = new java.sql.Date(date.getTime());              
    if(verifUserChamps() ){ 
    if ( controlSaisie()){
        if (verifyEmailStructure(email_text) ) {
            
            
         
        
        for ( i=0 ; i<li.size();i++){
        if (li.get(i).getEmail().equals(email_text.getText()) )
        
        { Alert alert = new Alert(AlertType.ERROR, "email is used.");
        alert.showAndWait();} 
        else {
        
      
    user u = new user( nom_text.getText(),roles ,email_text.getText() , password_text.getText() , prenom_text.getText() ,adresse_text.getText() ,role_text.getSelectionModel().getSelectedItem().toString(), sqlDate );
    
        su.ajouteru(u);
        nom_text.clear();
        adresse_text.clear();
        prenom_text.clear();
        email_text.clear();
        password_text.clear(); 
        role_text.getSelectionModel().clearSelection();
        date_text.getEditor().clear();
           
          
        }
        }}       }}                
    }


public boolean controlSaisie(){
         Alert alert = new Alert(AlertType.ERROR);
         alert.setTitle("Erreur");
         alert.setHeaderText("Erreur de saisie");
         

        if(checkIfStringContainsNumber(nom_text.getText())){
            alert.setContentText("Le nom  ne doit pas contenir des chiffres");
            alert.showAndWait();
            return false;
        }
         if(checkIfStringContainsNumber(prenom_text.getText())){
            alert.setContentText("Le prenom  ne doit pas contenir des chiffres");
            alert.showAndWait();
            return false;
        }
          
        return true;
    }
    
    public boolean checkIfNumber(){
        Alert alert = new Alert(AlertType.ERROR);
         alert.setTitle("Erreur");
         alert.setHeaderText("Erreur de saisie");
         

       return true;
    }
    
    public boolean checkIfStringContainsNumber(String str){
        for (int i=0; i<str.length();i++){
            if(str.contains("0") || str.contains("1") || str.contains("2") || str.contains("3") || str.contains("4") || str.contains("5") || str.contains("6") || str.contains("7") || str.contains("8") || str.contains("9")){
                return true;
            }
        }
        return false;
    }


public boolean verifUserChamps() {
        int verif = 0;
        String style = " -fx-border-color: red;";

        String styledefault = "-fx-border-color: green;";

   
       
        nom_text.setStyle(styledefault);
        prenom_text.setStyle(styledefault);
        adresse_text.setStyle(styledefault);
        date_text.setStyle(styledefault);
                role_text.setStyle(styledefault);
                        email_text.setStyle(styledefault);
                        password_text.setStyle(styledefault);



     
       
 

        if (nom_text.getText().equals("")) {
            nom_text.setStyle(style);
            verif = 1;
        }
       
        if ( prenom_text.getText().equals("")) {
             prenom_text.setStyle(style);
            verif = 1;
        }
         
        if (adresse_text.getText().equals("")) {
            adresse_text.setStyle(style);
            verif = 1;
        }
       
        if (email_text.getText().equals("")) {
            email_text.setStyle(style);
            verif = 1;
        }
       if (password_text.getText().equals("")) {
            password_text.setStyle(style);
            verif = 1;
        }
        if (verif == 0) {
            return true;
        }
        Alert al = new Alert(Alert.AlertType.ERROR);
        al.setTitle("Alert");
        al.setContentText("Verifier les champs");
        al.setHeaderText(null);
        al.show() ; 
        
        return false;
    }
    public static boolean verifyEmailStructure(TextField emailField) {
    String emailPattern = "\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b";
    String email = emailField.getText().trim();

    if (email.isEmpty()) {
        Alert alert = new Alert(AlertType.ERROR, "Email field is empty.");
        alert.showAndWait();
        emailField.requestFocus();
        return false ; 
    } else if (!email.matches(emailPattern)) {
        Alert alert = new Alert(AlertType.ERROR, "Invalid email structure.");
        alert.showAndWait();
        emailField.requestFocus();
                return false ; 

    }
    return true ; 
}

    @FXML
    private void back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        
        loader.setLocation(getClass().getResource("authentification.fxml"));
        Parent root = loader.load();
        nom_text.getScene().setRoot(root);
    }
}



    
    

