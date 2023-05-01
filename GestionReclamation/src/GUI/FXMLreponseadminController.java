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
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import service.ServiceReclamation;
import service.ServiceReponse;

/**
 * FXML Controller class
 *
 * @author Yasmine
 */
public class FXMLreponseadminController implements Initializable {

    @FXML
    private TableView<Reponse> tvreponse;
    @FXML
    private TableColumn<Reponse, Integer> tcidrec;
    @FXML
    private TableColumn<Reponse, String> tctext;
    @FXML
    private TableColumn<Reponse, Date> tcdate;
    ObservableList<Reponse> data=FXCollections.observableArrayList();
    ServiceReponse sr=new ServiceReponse();
    ServiceReclamation srec=new ServiceReclamation();
    @FXML
    private TextArea tfreponse;
    @FXML
    private TableColumn<Reponse, Integer> tcidres;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        refresh();
    }    


     @FXML
    private void modifier(ActionEvent event) {
        Reponse r = tvreponse.getSelectionModel().getSelectedItem();
        if (r != null) {
            r.setText(tfreponse.getText());
            r.setDate(new Date(System.currentTimeMillis()));
            sr.modifier(r.getIdResponse(), r);
            refresh();
        }
    }

    @FXML
    private void supprimer(ActionEvent event) {
        Reponse rep=tvreponse.getSelectionModel().getSelectedItem();
        if(rep != null){
            
            sr.supprimer(rep.getIdResponse());
            Reclamation rec=srec.getById(rep.getIdReclamation());
            rec.setDate_traitement(null);
            rec.setStatus("Non traiter");
            srec.modifier(rec.getIdReclamation(), rec);
            refresh();
        }
    }

    @FXML
    private void retour(ActionEvent event) {
        Stage stageclose=(Stage)((Node)event.getSource()).getScene().getWindow();
        stageclose.close();
        try {
            Parent root=FXMLLoader.load(getClass().getResource("/GUI/FXMLreclamationadmin.fxml"));

            Scene scene = new Scene(root);
            Stage primaryStage=new Stage();
            primaryStage.setTitle("Gestion recalamation");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLreclamationadminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void refresh(){
        data.clear();
        data = FXCollections.observableArrayList(sr.afficher());
        tcidrec.setCellValueFactory(new PropertyValueFactory<>("idReclamation"));
        tctext.setCellValueFactory(new PropertyValueFactory<>("text"));
        tcdate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tcidres.setCellValueFactory(new PropertyValueFactory<>("idResponse"));
        tvreponse.setItems(data);
    }
    @FXML
    private void fillforum(MouseEvent event) {
        Reponse r = tvreponse.getSelectionModel().getSelectedItem();
        if (r != null) {
            tfreponse.setText(r.getText());
        }
    }
}
