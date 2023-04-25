/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.Participant;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author nassim
 */
public class ModifierPartController implements Initializable {

    @FXML
    private DatePicker dateModP;
    @FXML
    private Button btnmodifP;
    @FXML
    private Button btnCloseP;
    @FXML
    private TextField tuserid;
    @FXML
    private TextField ttourid;
    @FXML
    private Text lblStatus;
    @FXML
    private Text lblstatusMini;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void ModifP(ActionEvent event) {
    }

    @FXML
    private void CloseP(ActionEvent event) {
    }
    void initData(Participant myObject) {

   
    }
    
}
