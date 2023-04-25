/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.Tournois;
import java.time.LocalDateTime;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author nassim
 */
public class ModifierTournoisController {

    
    @FXML
    private TextField titleTextField;

    @FXML
    private TextField typeTextField;

    @FXML
    private TextArea descriptionTextField;

    @FXML
    private TextField localisationTextField;

    @FXML
    private Spinner<Integer> heureTourSpinner;

    @FXML
    private DatePicker dateTourPicker;

    @FXML
    private Spinner<Integer> minuteTourSpinner;

    @FXML
    private Spinner<Integer> heureFinSpinner;

    @FXML
    private DatePicker dateFinPicker;

    @FXML
    private Spinner<Integer> minuteFinSpinner;


    @FXML
    private Button crud_close;

    @FXML
    private Spinner<Integer> nbPlaceSpinner;

    private TextField imageTextField;   
    @FXML
    private Button crud_ajouter;
    @FXML
    private Button crud_image;
    
    
    
    void initData(Tournois myObject) {
       String title = myObject.getTitle();
       String image = myObject.getImage();
       String type = myObject.getType();
       String description = myObject.getDescription();
       String localisation = myObject.getLocalisation();
 
       // Remplir les champs de l'interface graphique avec les informations récupérées
    titleTextField.setText(title);
    
    typeTextField.setText(type);
    descriptionTextField.setText(description);
    localisationTextField.setText(localisation);
  
   
    }
    
}
