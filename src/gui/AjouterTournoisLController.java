package gui;

import entities.Tournois;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import services.TournoisService;

public class AjouterTournoisLController implements Initializable {

    @FXML
    private TextField titleTextField;
    @FXML
    private TextField typeTextField;
    @FXML
    private TextArea descriptionTextField;
    @FXML
    private TextField localisationTextField;
    @FXML
    private DatePicker dateTourPicker;
    @FXML
    private DatePicker dateFinPicker;
    @FXML
    private Button crud_ajouter;
    @FXML
    private Button crud_close;
    @FXML
    private Button crud_image;
    @FXML
    private TextField nbplacetextfield;

    private String lien;
    private File file;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void save(ActionEvent event) throws IOException {
        TournoisService sp = new TournoisService();
        Tournois u = new Tournois();

        int nbPlace = Integer.parseInt(nbplacetextfield.getText());
        String localisation = localisationTextField.getText().trim();
        String description = descriptionTextField.getText().trim();
        String type = typeTextField.getText().trim();
        String title = titleTextField.getText().trim();
        Timestamp timestamp = Timestamp.valueOf(dateTourPicker.getValue().atStartOfDay());
        Timestamp timestamp1 = Timestamp.valueOf(dateFinPicker.getValue().atStartOfDay());

        LocalDateTime dateTime = timestamp.toLocalDateTime();

        LocalDateTime dateTime1 = timestamp1.toLocalDateTime();

        if (title.isEmpty()) {
            showErrorDialog("Title is required.");
            return;
        } else if (type.isEmpty()) {
            showErrorDialog("Type is required.");
            return;
        }

        if (description.isEmpty()) {
            showErrorDialog("Description is required.");
            return;
        }

        if (localisation.isEmpty()) {
            showErrorDialog("Localisation is required.");
            return;
        }

        if (nbPlace < 0) {
            showErrorDialog("nb place is required.");
            return;
        } else {

            try {

            } catch (DateTimeException e) {
                showErrorDialog("Invalid date format. Use format: dd/MM/yyyy HH:mm:ss");
                return;
            }
            u.setDate_tour(dateTime);
            u.setDate_fin(dateTime1);
            u.setTitle(title);
            u.setType(type);
            u.setDescription(description);
            u.setLocalisation(localisation);
            u.setNb_place(nbPlace);
            u.setImage(lien + ".png");
            sp.ajouterTournois(u);
        }
       Parent root = FXMLLoader.load(getClass().getResource("HomeFXML.fxml"));
       Scene scene = new Scene(root);
       Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
       stage.setScene(scene);
       stage.show();
    }

    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void close(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeFXML.fxml"));

        Parent root = loader.load();
        crud_close.getScene().setRoot(root);

    }

    @FXML
    private void addPicture(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilterJPG
                = new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
        FileChooser.ExtensionFilter extFilterjpg
                = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterPNG
                = new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
        FileChooser.ExtensionFilter extFilterpng
                = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.getExtensionFilters()
                .addAll(extFilterJPG, extFilterjpg, extFilterPNG, extFilterpng);
        //Show open file dialog
        file = fileChooser.showOpenDialog(null);

        try {
            BufferedImage image = ImageIO.read(file);
            WritableImage imagee = SwingFXUtils.toFXImage(image, null);

            try {
                // save image to PNG file
                this.lien = UUID.randomUUID().toString();
                File f = new File("src\\img\\" + this.lien + ".png");
                System.out.println(f.toURI().toString());
                ImageIO.write(image, "PNG", f);
                crud_image.setText(file.getName());
                System.out.println(lien);

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
