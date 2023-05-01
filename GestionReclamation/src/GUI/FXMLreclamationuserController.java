package GUI;

import entity.Reclamation;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.sql.rowset.serial.SerialBlob;
import service.ServiceReclamation;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

public class FXMLreclamationuserController implements Initializable {

    @FXML
    private TableView<Reclamation> tvreclamation;
    @FXML
    private TableColumn<Reclamation, Integer> tcIdReclamation;
    @FXML
    private TableColumn<Reclamation, String> tcNom;
    @FXML
    private TableColumn<Reclamation, String> tcPrenom;
    @FXML
    private TableColumn<Reclamation, String> tcEmail;
    private TableColumn<Reclamation, String> tcScreenshot;
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
    private TableColumn<Reclamation, String> tcNomService;
    @FXML
    private TextField tfNom;
    @FXML
    private TextField tfPrenom;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextArea tfDescription;

    ObservableList<Reclamation> data = FXCollections.observableArrayList();
    ServiceReclamation sr = new ServiceReclamation();
    @FXML
    private AnchorPane anchore;
    @FXML
    private ImageView imageview;
    @FXML
    private TextField tfnum;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refresh();
    }

    @FXML
    private void fillforum(MouseEvent event) {
        Reclamation r = tvreclamation.getSelectionModel().getSelectedItem();
        if (r != null) {
            tfNom.setText(r.getNom());
            tfPrenom.setText(r.getPrenom());
            tfEmail.setText(r.getEmail());
            tfDescription.setText(r.getDescription());
            img=r.getImage();
            tfnum.setText(r.getNumero_mobile());
            
            Image image;
            try {
                image = new Image(img.getBinaryStream());
                imageview.setImage(image);
            } catch (SQLException ex) {
                Logger.getLogger(FXMLreclamationuserController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
    }

    @FXML
    private void envoyer(ActionEvent event) {
        Reclamation r = new Reclamation();
        r.setDate_creation(new Date(System.currentTimeMillis()));
        //r.setDate_traitement(new Date(System.currentTimeMillis()));
        r.setDescription(tfDescription.getText());
        r.setEmail(tfEmail.getText());
        r.setNom(tfNom.getText());
        r.setPrenom(tfPrenom.getText());
        r.setStatus("Non traitée");
        r.setImage(img);
        r.setNumero_mobile(tfnum.getText());
        if (controleDeSaisie().length() > 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning adding claim");
            alert.setContentText(controleDeSaisie());
            alert.showAndWait();
        } else {
            sr.ajouter(r);
            refresh();
            //NOTIFICATION
            TrayNotification tray=new TrayNotification();
            tray.setAnimationType(AnimationType.FADE);
            tray.setTitle("Ajout avec success");
            tray.setMessage("Vous avez bien ajouter une reclamation");
            tray.setNotificationType(NotificationType.SUCCESS);
            tray.showAndDismiss(Duration.millis(2000));
        }
    }

    @FXML
    private void modifier(ActionEvent event) {
        Reclamation r = tvreclamation.getSelectionModel().getSelectedItem();
        if (r != null) {
            r.setDate_creation(new Date(System.currentTimeMillis()));
            r.setDescription(tfDescription.getText());
            r.setEmail(tfEmail.getText());
            r.setNom(tfNom.getText());
            r.setPrenom(tfPrenom.getText());
            r.setStatus("Non traiter");
            r.setNumero_mobile(tfnum.getText());
            r.setImage(img);
            if (controleDeSaisie().length() > 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning modifying claim");
                alert.setContentText(controleDeSaisie());
                alert.showAndWait();
            } else {
                sr.modifier(r.getIdReclamation(), r);
                refresh();
                //NOTIFICATION
                TrayNotification tray=new TrayNotification();
                tray.setAnimationType(AnimationType.FADE);
                tray.setTitle("Modification avec success");
                tray.setMessage("Vous avez bien modifier une reclamation");
                tray.setNotificationType(NotificationType.SUCCESS);
                tray.showAndDismiss(Duration.millis(2000));
            }
        }
    }

    @FXML
    private void supprimer(ActionEvent event) {
        Reclamation r = tvreclamation.getSelectionModel().getSelectedItem();
        if (r != null) {
            sr.supprimer(r.getIdReclamation());
            refresh();
        }
    }

    public void refresh() {
        data.clear();
        data = FXCollections.observableArrayList(sr.afficher());
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

    public String controleDeSaisie() {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$");
	Matcher matcher = pattern.matcher(tfEmail.getText());
               System.out.println(matcher.matches());
        String erreurs = "";
        if (tfNom.getText().trim().isEmpty()) {
            erreurs += "-Le champs nom est vide!\n";
        }
        if (tfPrenom.getText().trim().isEmpty()) {
            erreurs += "-Le champs prenom est vide!\n";
        }
        if (tfEmail.getText().trim().isEmpty()) {
            erreurs += "-Le champs email est vide!\n";
        }
        if (tfDescription.getText().trim().isEmpty()) {
            erreurs += "-Le champs description est vide!\n";
        }
        if (tfDescription.getText().trim().length() < 30) {
            erreurs += "-Le champs description doit etre > 30 charactere!\n";
        }
        if (tfnum.getText().trim().isEmpty()) {
            erreurs += "-Le champs numero est vide!\n";
        }
        if(!matcher.matches()){
            erreurs += "-Le champs email est invalide!\n";
        }
        if (img == null) {
            erreurs += "-Il faut ajouter une image!\n";
        }
        return erreurs;
    }

    @FXML
    private void gotoreponse(ActionEvent event) {
        Stage stageclose = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stageclose.close();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/GUI/FXMLreponseuser.fxml"));

            Scene scene = new Scene(root);
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Gestion recalamation");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLreclamationuserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    Blob img = null;

    @FXML
    private void uploadimg(ActionEvent event) throws FileNotFoundException, IOException, SQLException {
        FileChooser open = new FileChooser();
        Stage stage = (Stage) anchore.getScene().getWindow();
        File file = open.showOpenDialog(stage);
        if (file != null) {
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            byte[] fileBytes = baos.toByteArray();
            img = new SerialBlob(fileBytes);

            // Convert byte array to Image and set it to ImageView
            Image image = new Image(new ByteArrayInputStream(fileBytes));
            imageview.setImage(image);
        }

        
    }
}
