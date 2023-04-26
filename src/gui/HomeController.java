/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import entities.Participant;
import entities.Tournois;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.MyDB;
import services.TournoisService;
import services.ParticipantService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * FXML Controller class
 *
 * @author nassim
 */
public class HomeController implements Initializable {
 public Participant part;
 @FXML
    private Button participantBtn;
 @FXML
    private TableView<Tournois> tabTournois;

    @FXML
    private Text lblStatus;

    @FXML
    private Text lblstatusMini;
    @FXML
    private TableColumn<Tournois, Integer> idColTournois;
    @FXML
    private TableColumn<Tournois, String> titleColTournois;
    @FXML
    private TableColumn<Tournois, LocalDateTime> debutColTournois;
    @FXML
    private TableColumn<Tournois, LocalDateTime> finColTournois;
    @FXML
    private TableColumn<Tournois, String> imageColTournois;
    @FXML
    private TableColumn<Tournois, Integer> placeColTournois;
    @FXML
    private TableColumn<Tournois, String> localiColTournois;
    @FXML
    private TableColumn<Tournois, String> typeColTournois;
    @FXML
    private TableColumn<Tournois, Void> editColTour;
    @FXML
    private Button btnajouterTour;
    @FXML
    private TextField ttitle;
    @FXML
    private TextField ttype;
    @FXML
    private TextField tdesc;
    @FXML
    private TextField tlocalisation;
    @FXML
    private TextField tnbplace;
    @FXML
    private Button btnClose;

@FXML
    private Button btnCloseP;
@FXML
    private TextField ttourid;
@FXML
    private TextField tuserid;
    
     @FXML
    private DatePicker dateModP;
    @FXML
    private Button btnmodifP;


   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        if (idColTournois != null && titleColTournois != null && debutColTournois != null && finColTournois != null && imageColTournois != null && placeColTournois != null && localiColTournois != null && typeColTournois != null && editColTour != null) {
            idColTournois.setCellValueFactory(new PropertyValueFactory<>("id"));
            titleColTournois.setCellValueFactory(new PropertyValueFactory<>("title"));
            debutColTournois.setCellValueFactory(new PropertyValueFactory<>("date_tour"));
            finColTournois.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
            imageColTournois.setCellValueFactory(new PropertyValueFactory<>("image"));
            placeColTournois.setCellValueFactory(new PropertyValueFactory<>("nb_place"));
            localiColTournois.setCellValueFactory(new PropertyValueFactory<>("localisation"));
            typeColTournois.setCellValueFactory(new PropertyValueFactory<>("type"));
            editColTour.setCellFactory(param -> new TableCell<Tournois, Void>() {
    private final Button deleteButton = new Button("Delete");
    private final Button editButton = new Button("Modifier");
    private final Button pdfButton = new Button("Télécharger pdf");

    {
        deleteButton.setOnAction(event -> {
            Tournois myObject = getTableView().getItems().get(getIndex());
            TournoisService sd = new TournoisService();
            sd.supprimerTournoi(myObject.getId());
            getTableView().getItems().remove(myObject);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Êtes-vous sûr de supprimer cette Tournois " + myObject.getId());
            alert.show();
        });
        deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white !important;");

        editButton.setOnAction(event -> {
            Tournois myObject = getTableView().getItems().get(getIndex());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("modifierTour.fxml"));
            Parent root;
            try {
                root = loader.load();
                ModifierTournoisController controller = loader.getController();
                controller.initData(myObject); // passer les données à la fenêtre de modification
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        editButton.setStyle("-fx-background-color: #FFA500; -fx-text-fill: white;");
        
        /*pdfButton.setOnAction(event->    {
        // Create a new Document
                            Document document = new Document();

                            try {
                                // Create a PdfWriter to write the Document to a file or output stream
                                PdfWriter.getInstance(document, new FileOutputStream("activite.pdf"));
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(DisplayactiviteController.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (DocumentException ex) {
                                Logger.getLogger(DisplayactiviteController.class.getName()).log(Level.SEVERE, null, ex);
                            }

// Open the Document
                            document.open();

                            try {
                                float[] columnWidths = {1, 2}; // the widths of the columns in the table
                                PdfPTable table = new PdfPTable(columnWidths);
                                table.setWidthPercentage(100); // set the width of the table to 100% of the page
                                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER); // center the text in the table cells

// Add the table headers
                               

// Add the Activite details to the table
                                table.addCell(new PdfPCell(new Phrase("Titre:", new Font(Font.FontFamily.HELVETICA, 10))));
                                table.addCell(new PdfPCell(new Phrase(getAct().getTitre(), new Font(Font.FontFamily.HELVETICA, 10))));
                                table.addCell(new PdfPCell(new Phrase("Date Debut:", new Font(Font.FontFamily.HELVETICA, 10))));
                                table.addCell(new PdfPCell(new Phrase(getAct().getDate_debut().toString(), new Font(Font.FontFamily.HELVETICA, 10))));
                                table.addCell(new PdfPCell(new Phrase("Date Fin:", new Font(Font.FontFamily.HELVETICA, 10))));
                                table.addCell(new PdfPCell(new Phrase(getAct().getDate_debut().toString(), new Font(Font.FontFamily.HELVETICA, 10))));

// Add the table to the Document
                                document.add(table);

                            
                               
                            } catch (DocumentException ex) {
                                Logger.getLogger(DisplayactiviteController.class.getName()).log(Level.SEVERE, null, ex);
                            }

// Close the Document
                            document.close();

                            if (Desktop.isDesktopSupported()) {
                                Desktop desktop = Desktop.getDesktop();
                                try {
                                    desktop.open(new File("activite.pdf"));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
    });*/

    }

    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            HBox hbox = new HBox(deleteButton, editButton);
            hbox.setSpacing(5);
            setGraphic(hbox);
        }
    }
});

            TournoisService tour = new TournoisService();
            tabTournois.setItems(tour.afficherLesTournois2());

           

        }
    }
@FXML
        private void handleClicks(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ParticipantFXML.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) participantBtn.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }


private void save(ActionEvent event) {
    TournoisService sp = new TournoisService();
    Tournois u = new Tournois();
   
    
    int nbPlace = tnbplace.getAnchor();
    String localisation = tlocalisation.getText().trim();
    String description = tdesc.getText().trim();
    String type = ttype.getText().trim();
    String title = ttitle.getText().trim();
    if (title.isEmpty()) {
        showErrorDialog("Title is required.");
        return;
    }
   
    else if (type.isEmpty()) {
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
   
      if (nbPlace<0) {
        showErrorDialog("nb place is required.");
        return;
    }
      else {
    
    try {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime dateTimee = LocalDateTime.parse("30/03/2024 10:30:45", formatter);
        u.setDate_tour(dateTimee);
        u.setDate_fin(dateTimee);
    } catch (DateTimeException e) {
        showErrorDialog("Invalid date format. Use format: dd/MM/yyyy HH:mm:ss");
        return;
    }
      u.setTitle(title);
      u.setType(type);
     u.setDescription(description);
     u.setLocalisation(localisation);
     u.setNb_place(nbPlace);
    u.setImage("hello.jpg");
    sp.ajouterTournois(u);
      }
}
private void showErrorDialog(String message) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Input Error");
    alert.setContentText(message);
    alert.showAndWait();
}

    private void close(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeFXML.fxml"));
        
        Parent root = loader.load();
        btnClose.getScene().setRoot(root);

    }

  /*  @FXML
    private void CloseP(ActionEvent event) throws IOException {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeFXML.fxml"));
         
        Parent root1 = loader.load();
        Scene scene = new Scene(root1);
        
        btnCloseP.getScene().setRoot(root1);
       


    }
*/
    @FXML
        private void AjouterTour(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterTournoisL.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) btnajouterTour.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }
        
    
    @FXML
    private void CloseP(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeFXML.fxml"));
        
        Parent root = loader.load();
        btnCloseP.getScene().setRoot(root);
    
    
}
    

    void SaveP(ActionEvent event) {

        ParticipantService sd = new ParticipantService();
        Participant j = new Participant();
        j.setUsers_id(Integer.parseInt(tuserid.getText()));
        j.setTournois_id(Integer.parseInt(ttourid.getText()));
        j.setStatus("En Cours");
               DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime dateTimee = LocalDateTime.parse("30/03/2024 10:30:45", formatter);
      
        j.setDate_p(LocalDateTime.MIN);
        sd.ajouterParticipant(j, Integer.parseInt(tuserid.getText()), Integer.parseInt(ttourid.getText()));
    }
    @FXML
    void ModifP(ActionEvent event) {
                      part.setUsers_id(Integer.parseInt(ttourid.getText()));
                      part.setTournois_id(Integer.parseInt(tuserid.getText()));
                     
       ParticipantService sd = new ParticipantService();
        
        part.setStatus("En Cours");
               DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime dateTimee = LocalDateTime.parse("30/03/2024 10:30:45", formatter);
        
     
        sd.modifierParticipant(part);
    }
    
    public void qrcode() throws SQLException {
TournoisService ps = new TournoisService();
        List<Tournois> personnes = ps.afficherLesTournois();

        // Create a button
        // Set an event handler for the button
        // Convert the data to a string
        StringBuilder stringBuilder = new StringBuilder();
        for (Tournois data : personnes) {
            stringBuilder.append(data);
            stringBuilder.append("\n");
        }
        String dataString = stringBuilder.toString().trim();

        // Generate the QR code image
        Image qrCodeImage = generateQRCode(dataString);

        // Display the QR code image
        ImageView imageView = new ImageView(qrCodeImage);
        VBox vbox = new VBox(imageView);
        Scene scene = new Scene(vbox);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

    }

    private Image generateQRCode(String data) {
        try {
            // Create a QR code writer
            QRCodeWriter writer = new QRCodeWriter();

            // Create a BitMatrix from the data and set the size
            BitMatrix bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 200, 200);

            // Create a BufferedImage from the BitMatrix
            BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);

            // Create a JavaFX Image from the BufferedImage
            return SwingFXUtils.toFXImage(image, null);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
