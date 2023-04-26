/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.produit.gui;

import edu.produit.entites.Produit;
import edu.produit.services.CRUDProduit;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class AjouterPFrontController implements Initializable {

    @FXML
    private TextField fxnomproduit;
    @FXML
    private TextField fxprix;
    @FXML
    private TextField fxdescription;
    @FXML
    private Button fxajout;
    private TextField idproduit;
    @FXML
    private Button modifier;
    @FXML
    private TextField fxiduser;
    @FXML
    private Button back;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
private void Ajouterproduit(ActionEvent event) {
    String nom_produit=fxnomproduit.getText();
    String prixStr = fxprix.getText();
    String description= fxdescription.getText();
    String id_userStr = fxiduser.getText();

    if (nom_produit.isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Nom produit doit etre saisi");
        alert.setTitle("Probleme");
        alert.setHeaderText(null);
        alert.showAndWait();
    } else if (prixStr.isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Prix doit etre saisi");
        alert.setTitle("Probleme");
        alert.setHeaderText(null);
        alert.showAndWait();
    } else if (description.isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Description doit etre saisi");
        alert.setTitle("Probleme");
        alert.setHeaderText(null);
        alert.showAndWait();
    } else if (id_userStr.isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("ID utilisateur doit etre saisi");
        alert.setTitle("Probleme");
        alert.setHeaderText(null);
        alert.showAndWait();
    } else {
        try {
            float prix = Float.parseFloat(prixStr);
            int id_user = Integer.parseInt(id_userStr);

            // Open a file chooser to let the user select an image file
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choisir une image");
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif")
            );
            File selectedFile = fileChooser.showOpenDialog(null);

            if (selectedFile != null) {
                // Read the bytes of the selected image file
                byte[] imageBytes = Files.readAllBytes(selectedFile.toPath());

                Produit p1 = new Produit(nom_produit, prix, description, id_user, imageBytes);
                CRUDProduit cr = new CRUDProduit();
                cr.ajouterproduit(p1);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Produit inséré avec succès!");
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Prix et ID utilisateur doivent etre des nombres");
            alert.setTitle("Probleme");
            alert.setHeaderText(null);
            alert.showAndWait();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Erreur lors de la lecture de l'image sélectionnée");
            alert.showAndWait();
        }
    }
}

    
    private void redirectToPage(){
            Parent root;
            try {
           
            root = FXMLLoader.load(getClass().getResource("Modifier.fxml"));
            Scene c=new Scene(root);
             Stage stage=(Stage)modifier.getScene().getWindow();
            stage.setScene(c);
        } catch (IOException ex) {
            Logger.getLogger(AjouterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void modifierproduit(MouseEvent event) {
        this.redirectToPage();
    }

    @FXML
    private void back(ActionEvent event) {
        this.redirectToPage5();
    }
    private void redirectToPage5(){
            Parent root;
            try {
            
            
           
            root = FXMLLoader.load(getClass().getResource("FrontAjouter.fxml"));
            Scene c=new Scene(root,800,600);
            Stage stage=(Stage)back.getScene().getWindow();
            stage.setScene(c);
        } catch (IOException ex) {
            Logger.getLogger(AjouterController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
          
    }
    
}
