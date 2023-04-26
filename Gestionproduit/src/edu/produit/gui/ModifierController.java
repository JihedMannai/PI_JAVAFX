/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.produit.gui;

import edu.produit.entites.Produit;
import edu.produit.services.CRUDProduit;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class ModifierController implements Initializable {

    @FXML
    private TextField fxnomproduit1;
    @FXML
    private TextField fxprix1;
    @FXML
    private TextField fxdescription1;
    @FXML
    private Button modifier;
    @FXML
    private TextField fxidproduit1;
    @FXML
    private TextField fxiduser1;
    @FXML
    private Button retour;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
private void modifier(ActionEvent event) {
    String idProduitStr = fxidproduit1.getText();
    String nomProduit = fxnomproduit1.getText();
    String prixStr = fxprix1.getText();
    String description = fxdescription1.getText();
    String idUserStr = fxiduser1.getText();

    if (idProduitStr.isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("ID du produit doit être saisi");
        alert.setTitle("Problème");
        alert.setHeaderText(null);
        alert.showAndWait();
    } else if (nomProduit.isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Nom du produit doit être saisi");
        alert.setTitle("Problème");
        alert.setHeaderText(null);
        alert.showAndWait();
    } else if (prixStr.isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Prix doit être saisi");
        alert.setTitle("Problème");
        alert.setHeaderText(null);
        alert.showAndWait();
    } else if (description.isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Description doit être saisie");
        alert.setTitle("Problème");
        alert.setHeaderText(null);
        alert.showAndWait();
    } else if (idUserStr.isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("ID utilisateur doit être saisi");
        alert.setTitle("Problème");
        alert.setHeaderText(null);
        alert.showAndWait();
    } else {
        try {
            int idProduit = Integer.parseInt(idProduitStr);
            float prix = Float.parseFloat(prixStr);
            int idUser = Integer.parseInt(idUserStr);

            CRUDProduit cr = new CRUDProduit(); 
            int idProduitTrouve = cr.getid(idProduit);

            if (idProduitTrouve != -1) {
                Produit p = new Produit(idProduit, nomProduit, prix, description, idUser);

                cr.modifierproduit(p);

                fxnomproduit1.clear(); 
                fxprix1.clear(); 
                fxdescription1.clear(); 
                fxiduser1.clear();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Produit modifié avec succès!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("ID du produit n'existe pas dans la base de données");
                alert.setTitle("Problème");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("ID du produit, prix et ID utilisateur doivent être des nombres");
            alert.setTitle("Problème");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }
}


        
    
    private void redirectToPage3(){
            Parent root;
            try {
            
            
           
            root = FXMLLoader.load(getClass().getResource("Ajouter.fxml"));
            Scene c=new Scene(root,800,600);
            Stage stage=(Stage)retour.getScene().getWindow();
            stage.setScene(c);
        } catch (IOException ex) {
            Logger.getLogger(AjouterController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
          
    }
    @FXML
    private void retour(MouseEvent event) {
        this.redirectToPage3();
    }
    
}
