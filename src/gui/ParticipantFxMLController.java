/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.Participant;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.ParticipantService;

/**
 * FXML Controller class
 *
 * @author nassim
 */
public class ParticipantFxMLController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
     @FXML
    private Button dashboardBtn;

    @FXML
    private Button userBtn;

    @FXML
    private Button tournoisBtn;

    @FXML
    private Button participantBtn;

    @FXML
    private Button clubBtn;

    @FXML
    private Button terrainBtn;

    @FXML
    private Button produitBtn;

    @FXML
    private Button categorieBtn;

    @FXML
    private Button reclamationBtn;

    @FXML
    private GridPane pnParticipant;

    @FXML
    private Button btnajouterP;

    @FXML
    private TableView<Participant> tabParticipant;

@FXML
    private TableColumn<Participant, Integer> colidp;
@FXML
    private TableColumn<Participant, String> colnomp;
@FXML
    private TableColumn<Participant, String> colprenomp;
@FXML
    private TableColumn<Participant, String> coltournoisp;
@FXML
    private TableColumn<Participant, LocalDateTime> coldatep;
@FXML
    private TableColumn<Participant, String> colstatusp;
@FXML
    private TableColumn<Participant, Void> coleditp;

    @FXML
    private Text lblStatus;

    @FXML
    private Text lblstatusMini;
    
    
   

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
         colidp.setCellValueFactory(new PropertyValueFactory<>("id"));
            colnomp.setCellValueFactory(new PropertyValueFactory<>("users_id"));
            colprenomp.setCellValueFactory(new PropertyValueFactory<>("users_id"));
            coltournoisp.setCellValueFactory(new PropertyValueFactory<>("tournois_id"));
            coldatep.setCellValueFactory(new PropertyValueFactory<>("date_p"));
            colstatusp.setCellValueFactory(new PropertyValueFactory<>("status"));
            coleditp.setCellFactory(param -> new TableCell<Participant, Void>() {
                private final Button deleteButton = new Button("Delete");
                private final Button modifierbtn = new Button("modifier");
               
               
                
                {
        deleteButton.setOnAction(event -> {
            Participant myObject = getTableView().getItems().get(getIndex());
            ParticipantService sd = new ParticipantService();
            sd.SupprimerParticipant(myObject.getId());
            getTableView().getItems().remove(myObject);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Êtes-vous sûr de supprimer cette Participation " + myObject.getId());
            alert.show();
        });
        deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white !important;");

        modifierbtn.setOnAction(event -> {
            Participant myObject = getTableView().getItems().get(getIndex());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("modifierPart.fxml"));
            Parent root;
            try {
                root = loader.load();
                ModifierPartController controller = loader.getController();
                controller.initData(myObject); // passer les données à la fenêtre de modification
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        modifierbtn.setStyle("-fx-background-color: #FFA500; -fx-text-fill: white;");}

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        HBox hbox = new HBox(deleteButton,modifierbtn);
                        hbox.setSpacing(5);
                        setGraphic(hbox);
                    }
                }
            });
            
           
            ParticipantService part = new ParticipantService();
            tabParticipant.setItems(part.afficherParticipants2());
    }
            
        @FXML
        private void AjouterPart(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterParticipant.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) btnajouterP.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }
          @FXML
        private void handleClicks(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeFXML.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) tournoisBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }
        

    }
            
            
            
     
    

