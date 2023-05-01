/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import entity.Reclamation;
import entity.Reponse;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.ServiceReclamation;
import service.ServiceReponse;
import util.MailAPI;

/**
 * FXML Controller class
 *
 * @author Yasmine
 */
public class FXMLreclamationadminController implements Initializable {

    @FXML
    private TableView<Reclamation> tvreclamation;
    @FXML
    private TextField tfrecherche;
    ObservableList<Reclamation> data = FXCollections.observableArrayList();
    ServiceReclamation sr = new ServiceReclamation();
    ServiceReponse srep = new ServiceReponse();
    @FXML
    private TableColumn<Reclamation, Integer> tcIdReclamation;
    @FXML
    private TableColumn<Reclamation, String> tcNom;
    @FXML
    private TableColumn<Reclamation, String> tcPrenom;
    @FXML
    private TableColumn<Reclamation, String> tcEmail;
    @FXML
    private TableColumn<Reclamation, String> tcNumeroMobile;
    @FXML
    private TableColumn<Reclamation, Date> tcDateCreation;
    @FXML
    private TableColumn<Reclamation, Date> tcDateTraitement;
    @FXML
    private TableColumn<Reclamation, String> tcDescription;
    @FXML
    private TableColumn<Reclamation, String> tcStatus;
    @FXML
    private TextArea tfreponse;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        recherche_avance();
        // TODO
    }

    @FXML
    private void tri(ActionEvent event) {
        refresh(sr.triparNom());
    }

    @FXML
    private void stat(ActionEvent event) {
        Stage stageclose=(Stage)((Node)event.getSource()).getScene().getWindow();
        stageclose.close();
        try {
            Parent root=FXMLLoader.load(getClass().getResource("/GUI/FXMLstatreclamation.fxml"));

            Scene scene = new Scene(root);
            Stage primaryStage=new Stage();
            primaryStage.setTitle("Gestion recalamation");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLreclamationadminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void gotogstreponse(ActionEvent event) {
        Stage stageclose=(Stage)((Node)event.getSource()).getScene().getWindow();
        stageclose.close();
        try {
            Parent root=FXMLLoader.load(getClass().getResource("/GUI/FXMLreponseadmin.fxml"));

            Scene scene = new Scene(root);
            Stage primaryStage=new Stage();
            primaryStage.setTitle("Gestion recalamation");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLreclamationadminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void refresh(List<Reclamation> reclamations) {
        data.clear();
        data = FXCollections.observableArrayList(reclamations);
        tcIdReclamation.setCellValueFactory(new PropertyValueFactory<>("idReclamation"));
        tcNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        tcPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tcNumeroMobile.setCellValueFactory(new PropertyValueFactory<>("numero_mobile"));
        tcDateCreation.setCellValueFactory(new PropertyValueFactory<>("date_creation"));
        tcDateTraitement.setCellValueFactory(new PropertyValueFactory<>("date_traitement"));
        tcDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tcStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tvreclamation.setItems(data);
    }

    @FXML
    private void envoyer(ActionEvent event) {
        Reclamation r=tvreclamation.getSelectionModel().getSelectedItem();
        if(r!=null){
            if(r.getStatus().equals("Traiter")){
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Erreur de reponse");
                alert.setContentText("Vouz avez deja repondre a cette reclamation!");
                alert.showAndWait();
            }
            else{
                Reponse rep=new Reponse();
                rep.setDate(new java.sql.Date(System.currentTimeMillis()));
                rep.setText(tfreponse.getText());
                rep.setIdReclamation(r.getIdReclamation());
                rep.setStatus("Traiter");
                srep.ajouter(rep);
                r.setStatus("Traiter");
                r.setDate_traitement(new java.sql.Date(System.currentTimeMillis()));
                sr.modifier(r.getIdReclamation(), r);
                refresh(sr.afficher());
                MailAPI.sendEmail(r.getEmail(), "Repondre a votre reclamation", rep.getText());
            }
            
            
        }
    }
    public void recherche_avance(){
        
        refresh(sr.afficher());
        ObservableList<Reclamation> reclamations=FXCollections.observableArrayList(sr.afficher());
        FilteredList<Reclamation> filtredlist=new FilteredList<>(reclamations,r->true);
        tfrecherche.textProperty().addListener((observable,oldValue,newValue)->{
            filtredlist.setPredicate(r->{
                String date;
                if(r.getDate_traitement()==null){
                    date="";
                }
                else{
                    date=r.getDate_traitement().toString();
                }
                if(newValue==null || newValue.isEmpty()){
                    return true;
                }
                if(r.getDescription().toLowerCase().indexOf(newValue.toLowerCase())!=-1){
                    return true;
                }
                else if(r.getNom().toLowerCase().indexOf(newValue.toLowerCase())!=-1){
                    return true;
                }
                else if(r.getStatus().toLowerCase().indexOf(newValue.toLowerCase())!=-1){
                    return true;
                }
                else if(r.getPrenom().toLowerCase().indexOf(newValue.toLowerCase())!=-1){
                    return true;
                }
                else if(r.getNumero_mobile().toLowerCase().indexOf(newValue.toLowerCase())!=-1){
                    return true;
                }
                else if(r.getEmail().toLowerCase().indexOf(newValue.toLowerCase())!=-1){
                    return true;
                }
                else if(r.getDate_creation().toString().indexOf(newValue)!=-1){
                    return true;
                }
                else if(date.indexOf(newValue)!=-1){
                    return true;
                }
                else{
                    return false;
                }
                
            });
            refresh(filtredlist);
        });
    }

}
