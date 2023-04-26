/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entities.SMSSender;
import entities.club;
import entities.train;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import services.Clubservice;
import services.Trainservice;

/**
 * FXML Controller class
 *
 * @author amina
 */
public class InterfacebackController implements Initializable {
    public static final String ACCOUNT_SID = "AC26099e79db56fd793e742b38716ad6ad";
    public static final String AUTH_TOKEN = "2a306aca57d75531a19a3a7326789407";

    @FXML
    private Pane pn_listclub;
    @FXML
    private Pane pn_listtterain;
    @FXML
    private Pane pn_ajouterclub;
    @FXML
    private TextField tf_name_club;
    @FXML
    private TextField tf_location_club;
    ///////////////////////////////////////////////////////////
    Clubservice cs = new Clubservice();
    Trainservice ts = new Trainservice();
    club tmpp;
    train tmpt;
    ///////////////////////////////////////////////////////////
    @FXML
    private TableView<club> tableclub;
    @FXML
    private TableColumn<club,String> col_id_club;
    @FXML
    private TableColumn<club,String> col_name_club;
    @FXML
    private TableColumn<club,String> col_location_club;
    @FXML
    private TableColumn<club,String> col_option_club;
    @FXML
    private Pane pn_modifierclub;
    @FXML
    private TextField tf_name_club1;
    @FXML
    private TextField tf_location_club1;
    @FXML
    private TextField tf_name_club2;
    @FXML
    private TextField tf_location_club2;
    @FXML
    private TextField tf_type_terrain;
    @FXML
    private TableView<train> tabletrain;
    @FXML
    private TableColumn<train,String> col_id_train;
    @FXML
    private TableColumn<train,String> col_type_train;
    @FXML
    private TableColumn<train,String> col_option_train;
    @FXML
    private Pane pn_ajouterterrain1;
    @FXML
    private Pane pn_modifiertrain;
    @FXML
    private TextField tf_type_terrain1;
    @FXML
    private Pane pn_ajouterterrain;
    @FXML
    private TextField tf_idterrain_club;
    @FXML
    private TextField tf_photo_terrain;
    @FXML
    private TextField tfrecherche;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pn_listclub.toFront();
        display();
        display2();
    }    


    @FXML
    private void btn_ajouterclub(ActionEvent event) {
        pn_ajouterclub.toFront();
    }

    @FXML
    private void btn_listclub(ActionEvent event) {
        pn_listclub.toFront();
    }

    @FXML
    private void btn_listterain(ActionEvent event) {
        pn_listtterain.toFront();
    }

    @FXML
    private void btn_ajouter_club(ActionEvent event) {
        if (tf_location_club.getText().isEmpty() || tf_name_club.getText().isEmpty() ) {
        // Afficher un message d'alerte
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Champs manquants");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez remplir tous les champs !");
        alert.showAndWait();
        return;
        }
        String emailRegex = "[a-zA-Z]+";
        if (!tf_location_club.getText().matches(emailRegex)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Format location incorrect");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir une adresse valide !");
            alert.showAndWait();
            return;
        }
        if (!tf_name_club.getText().matches(emailRegex)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Format name incorrect");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir un nom valide !");
            alert.showAndWait();
            return;
        }
        String name = tf_name_club.getText();
        String location = tf_location_club.getText();
        int idt = Integer.parseInt(tf_idterrain_club.getText());
        club p = new club(name,location,idt);
        cs.Ajouter(p);
        String message1 = "Un nouveau club a été ajouté.";
        cs.notifyUser(message1);
        
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
               new com.twilio.type.PhoneNumber("whatsapp:+21658049501"),
               new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                "un nouveau club a été ajouté")
            .create();

        System.out.println(message.getSid());




             
              


        display();
        tf_name_club.clear();
        tf_location_club.clear();
        pn_listclub.toFront();
    }
    
    private void display () {
        tableclub.getItems().clear();
        ObservableList<club>  l = FXCollections.observableArrayList();
        ResultSet resultSet = cs.Selectionner();
        l.clear();   
        try {
            while (resultSet.next()){
                l.add(new  club(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("location")));
                tableclub.setItems(l);  
            }
        } catch (SQLException ex) {
            Logger.getLogger(InterfacebackController.class.getName()).log(Level.SEVERE, null, ex);
        }
        col_id_club.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name_club.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_location_club.setCellValueFactory(new PropertyValueFactory<>("location"));        
        /////////////////////////////////////////////////////////////////////////
        //add cell of button edit 
         Callback<TableColumn<club, String>, TableCell<club, String>> cellFoctory = (TableColumn<club, String> param) -> {
            // make cell containing buttons
            final TableCell<club, String> cell = new TableCell<club, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {
                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView deditIcon = new FontAwesomeIconView(FontAwesomeIcon.PLUS);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.EDIT);

                        deleteIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:20px;"
                                + "-fx-fill:black;"
                        );
                        editIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:20px;"
                                + "-fx-fill:black;"
                        );
                        deditIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:20px;"
                                + "-fx-fill:black;"
                        );
                        deleteIcon.setOnMouseClicked((event) -> {
                            tmpp = tableclub.getSelectionModel().getSelectedItem();
                            cs.Supprimer( tmpp.getId());
                            display ();
                        });
                        
                        
                        editIcon.setOnMouseClicked((event) -> {
                            tmpp = tableclub.getSelectionModel().getSelectedItem();
                            int id= tmpp.getId();
                            ResultSet resultSet1 =cs.SelectionnerSingle(id);
                            try {
                                while (resultSet.next()){
                                    tmpp =new  club(
                                            resultSet.getInt("id"),
                                            resultSet.getString("name"),
                                            resultSet.getString("location"));  
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(InterfacebackController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            pn_modifierclub.toFront();
                            tf_name_club1.setText(tmpp.getName());
                            tf_location_club1.setText(tmpp.getLocation());
                        });
                        deditIcon.setOnMouseClicked((event) -> {
                            
                        });
                        
                        HBox managebtn = new HBox(deditIcon,deleteIcon,editIcon);
                        managebtn.setStyle("-fx-alignment:center");                
                        HBox.setMargin(deleteIcon, new Insets(6, 6, 0, 7));
                        HBox.setMargin(editIcon, new Insets(6, 6, 0, 7));
                        HBox.setMargin(deditIcon, new Insets(6, 6, 0, 7));
                        setGraphic(managebtn);
                        setText(null);

                    }
                }

            };

            return cell;
        };
        col_option_club.setCellFactory(cellFoctory);
    }

    @FXML
    private void btn_modifier_club(ActionEvent event) {
        if (tf_name_club1.getText().isEmpty() || tf_location_club1.getText().isEmpty() ) {
        // Afficher un message d'alerte
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Champs manquants");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez remplir tous les champs !");
        alert.showAndWait();
        return;
        }
        String emailRegex = "[a-zA-Z]+";
        if (!tf_name_club1.getText().matches(emailRegex)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Format nom incorrect");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir un nom correct !");
            alert.showAndWait();
            return;
        }
        if (!tf_location_club1.getText().matches(emailRegex)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Format location incorrect");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir une adresse correct !");
            alert.showAndWait();
            return;
        }
        String titre = tf_name_club1.getText();
        String description = tf_location_club1.getText();
        cs.Modifier(tmpp.getId(),titre,description);
        tf_name_club1.clear();
        tf_location_club1.clear();
        pn_listclub.toFront();
        display();
    }

    @FXML
    private void btn_ajouterterrain(ActionEvent event) {
        pn_ajouterterrain1.toFront();
    }

    @FXML
    private void btn_ajouter_terrain(ActionEvent event) {
        if (tf_type_terrain.getText().isEmpty() ) {
        // Afficher un message d'alerte
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Champs manquants");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez remplir tous les champs !");
        alert.showAndWait();
        return;
        }
        String emailRegex = "[a-zA-Z]+";
        if (!tf_type_terrain.getText().matches(emailRegex)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Format type incorrect");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir un type valide !");
            alert.showAndWait();
            return;
        }
        String type = tf_type_terrain.getText();
        String photo = tf_photo_terrain.getText();
        train t = new train(type,photo);
        ts.Ajouter(t);
        display2();
        tf_type_terrain.clear();
        pn_listtterain.toFront();
    }
        private void display2 () {
        tabletrain.getItems().clear();
        ObservableList<train>  l = FXCollections.observableArrayList();
        ResultSet resultSet = ts.Selectionner();
        l.clear();   
        try {
            while (resultSet.next()){
                l.add(new  train(
                        resultSet.getInt("id"),
                        resultSet.getString("type")));
                tabletrain.setItems(l);  
            }
        } catch (SQLException ex) {
            Logger.getLogger(InterfacebackController.class.getName()).log(Level.SEVERE, null, ex);
        }
        col_id_train.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_type_train.setCellValueFactory(new PropertyValueFactory<>("type"));
        
        /////////////////////////////////////////////////////////////////////////
        //add cell of button edit 
         Callback<TableColumn<train, String>, TableCell<train, String>> cellFoctory = (TableColumn<train, String> param) -> {
            // make cell containing buttons
            final TableCell<train, String> cell = new TableCell<train, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {
                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.EDIT);

                        deleteIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:20px;"
                                + "-fx-fill:black;"
                        );
                        editIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:20px;"
                                + "-fx-fill:black;"
                        );
                        
                        deleteIcon.setOnMouseClicked((event) -> {
                            tmpt= tabletrain.getSelectionModel().getSelectedItem();
                            ts.Supprimer( tmpt.getId());
                            display2 ();
                        });
                        
                        
                        editIcon.setOnMouseClicked((event) -> {
                            tmpt = tabletrain.getSelectionModel().getSelectedItem();
                            int id= tmpt.getId();
                            ResultSet resultSet1 =ts.SelectionnerSingle(id);
                            try {
                                while (resultSet.next()){
                                    tmpt =new  train(
                                            resultSet.getInt("id"),
                                            resultSet.getString("type"));  
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(InterfacebackController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            pn_modifiertrain.toFront();
                            tf_type_terrain1.setText(tmpt.getType());
                        });
                        
                        
                        HBox managebtn = new HBox(deleteIcon,editIcon);
                        managebtn.setStyle("-fx-alignment:center");                
                        HBox.setMargin(deleteIcon, new Insets(6, 6, 0, 7));
                        HBox.setMargin(editIcon, new Insets(6, 6, 0, 7));
                        setGraphic(managebtn);
                        setText(null);

                    }
                }

            };

            return cell;
        };
        col_option_train.setCellFactory(cellFoctory);
    }

    @FXML
    private void btn_modifier_terrain(ActionEvent event) {
        if (tf_type_terrain1.getText().isEmpty() ) {
        // Afficher un message d'alerte
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Champs manquants");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez remplir tous les champs !");
        alert.showAndWait();
        return;
        }
        String emailRegex = "[a-zA-Z]+";
        if (!tf_type_terrain1.getText().matches(emailRegex)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Format type incorrect");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir un type correct !");
            alert.showAndWait();
            return;
        }
        String type = tf_type_terrain1.getText();
        ts.Modifier(tmpt.getId(),type);
        tf_type_terrain1.clear();
        pn_listtterain.toFront();
        display2();
    }
   @FXML
public void recherche() {
    // Ajouter un listener sur le champ de recherche pour effectuer la recherche à chaque modification du texte
    tfrecherche.textProperty().addListener((observable, oldValue, newValue) -> {
        // Filtrer les clubs en utilisant le nouveau texte de recherche
        Clubservice sp = new Clubservice(); // instancier le service ClubService
        List<club> clubrecherche = new ArrayList<>(); // créer une liste pour stocker les clubs filtrés
        try {
            ResultSet rs = sp.Selectionner(); // obtenir tous les clubs depuis la base de données
            while (rs.next()) { // boucler sur tous les clubs
                club club = new club(
                   rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("location")
                );
                if (club.getName().toLowerCase().contains(newValue.toLowerCase())||club.getLocation().toLowerCase().contains(newValue.toLowerCase())) {
                    clubrecherche.add(club); // ajouter le club à la liste s'il correspond à la recherche
                }
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        // Mettre à jour la TableView avec les clubs filtrés
        tableclub.setItems(FXCollections.observableArrayList(clubrecherche));
    });
}

}
