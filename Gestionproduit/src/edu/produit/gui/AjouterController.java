/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.produit.gui;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.rowset.internal.Row;
import edu.produit.entites.Produit;
import edu.produit.services.CRUDProduit;
import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


//import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class AjouterController implements Initializable {

    @FXML
    private TextField fxnomproduit;
    @FXML
    private TextField fxprix;
    @FXML
    private TextField fxdescription;
    @FXML
    private Button fxajout;
    @FXML
    private TextField idproduit;
    
    
    @FXML
    private TableView<Produit> table_produit;
    @FXML
    private TableColumn<Produit, String> fxxnomproduit;
    @FXML
    private TableColumn<Produit, Float> fxxprix;
    @FXML
    private TableColumn<Produit, String> fxxdescription;
    @FXML
    private Button modifier;
    private CRUDProduit cr=new CRUDProduit();
    Produit P;
    @FXML
    private Button refreshlisteproduit;
    @FXML
    private TableColumn<Produit, Integer> fxxiduser;
    @FXML
    private TextField fxiduser;
    @FXML
    private TableColumn<Produit, byte[]> fxximage1;
    @FXML
    private Button statist;
    @FXML
    private Button fxsearch;
    @FXML
    private TextField filterfield;
    @FXML
    private Button front;
    
    
    /*public void setID_produit(String message)
       {
       this.fxxidproduit.setText(message);
       }
    public void setNom_produit(String message)
       {
       this.fxxnomproduit.setText(message);
       }
       public void setPrix(String message)
       {
       this.fxxprix.setText(message);
       }
       public void setDescription(String message)
       {
       this.fxxdescription.setText(message);
       }*/

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       // fxxidproduit.setCellValueFactory(new PropertyValueFactory<>("id_produit"));
        fxxnomproduit.setCellValueFactory(new PropertyValueFactory<>("nom_produit"));
        fxxprix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        fxxdescription.setCellValueFactory(new PropertyValueFactory<>("description"));
         fxxiduser.setCellValueFactory(new PropertyValueFactory<>("id_user"));
       fxximage1.setCellFactory(column -> {
    TableCell<Produit, byte[]> cell = new TableCell<Produit, byte[]>() {
        private final ImageView imageView = new ImageView();

        @Override
        protected void updateItem(byte[] item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                imageView.setImage(null);
            } else {
                Image image = new Image(new ByteArrayInputStream(item));
                imageView.setImage(image);
            }

            setGraphic(imageView);
        }
    };

    return cell;
});
       populateTable();
         
         
         for (Produit Pe : cr.afficherproduit()) {
            table_produit.getItems().add(Pe);
        }
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
        
    
    public void start (){
        try {
        FXMLLoader loader=FXMLLoader.load(getClass().getResource("Ajouter.fxml"));
        Parent root = loader.load();
        //root=FXMLLoader.load(getClass().getResource("Ajouter.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(AjouterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void supprimer(ActionEvent event) {
        CRUDProduit cr=new CRUDProduit();
        Produit P =new Produit();
        int id_produit = cr.getid(Integer.parseInt(idproduit.getText())) ; 
        P.setId_produit(id_produit);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirmation de Suppression !");
        alert.setContentText("Voulez-Vous Vraiment Supprimer");
        
        Optional<ButtonType> btn = alert.showAndWait();
        if(btn.get() == ButtonType.OK){
            cr.supprimerproduit(id_produit);
            
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
        private void redirectToPage2(){
            Parent root;
            try {
            
            
           
            root = FXMLLoader.load(getClass().getResource("Ajouter.fxml"));
            Scene c=new Scene(root);
            Stage stage=(Stage)refreshlisteproduit.getScene().getWindow();
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
    private void refresh(MouseEvent event) {
        this.redirectToPage2();
    }

    

    
    public void populateTable(){
        CRUDProduit cr= new CRUDProduit();
        
        List<Produit> li =cr.afficherproduit();
        ObservableList<Produit> data = FXCollections.observableArrayList(li);
                  

        
          
       // fxxidproduit.setCellValueFactory(
             //   new PropertyValueFactory<>("id_produit"));
        fxxnomproduit.setCellValueFactory(
                new PropertyValueFactory<>("nom_produit"));
 
       
        fxxprix.setCellValueFactory(
                new PropertyValueFactory<>("prix"));
       fxxdescription.setCellValueFactory(  
                new PropertyValueFactory<>("description")) ; 
        
        fxxiduser.setCellValueFactory(
                new PropertyValueFactory<>("id_user"));
        fxximage1.setCellValueFactory(
                new PropertyValueFactory<>("image"));
        table_produit.setItems(data);
    }

    @FXML
private void tri(ActionEvent event) {
    CRUDProduit cr = new CRUDProduit();
    List<Produit> li = cr.ListClasse1();
    ObservableList<Produit> data = FXCollections.observableArrayList(li);
    
    // Initialize the image column cell factory
    fxximage1.setCellFactory(column -> {
        TableCell<Produit, byte[]> cell = new TableCell<Produit, byte[]>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(byte[] item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    imageView.setImage(null);
                } else {
                    Image image = new Image(new ByteArrayInputStream(item));
                    imageView.setImage(image);
                }

                setGraphic(imageView);
            }
        };

        return cell;
    });
    
    // Set the cell value factories for the other columns
    //fxxidproduit.setCellValueFactory(new PropertyValueFactory<>("id_produit"));
    fxxnomproduit.setCellValueFactory(new PropertyValueFactory<>("nom_produit"));
    fxxprix.setCellValueFactory(new PropertyValueFactory<>("prix"));
    fxxdescription.setCellValueFactory(new PropertyValueFactory<>("description"));
    fxxiduser.setCellValueFactory(new PropertyValueFactory<>("id_user"));

    // Set the sorting for the table
    table_produit.getSortOrder().add(fxxprix);
    fxxprix.setSortType(TableColumn.SortType.ASCENDING);
    
    // Set the items to the table and update the view
    table_produit.setItems(data);
    table_produit.sort();
    
    // Add a listener to handle changes in the table columns
    table_produit.getColumns().addListener((ListChangeListener<TableColumn<Produit, ?>>) c -> {
        c.next();
        if (c.wasReplaced()) {
            TableColumn<Produit, ?> column = c.getAddedSubList().get(0);
            if (column.equals(fxximage1)) {
                fxximage1.setCellFactory(column2 -> {
                    TableCell<Produit, byte[]> cell = new TableCell<Produit, byte[]>() {
                        private final ImageView imageView = new ImageView();

                        @Override
                        protected void updateItem(byte[] item, boolean empty) {
                            super.updateItem(item, empty);

                            if (empty || item == null) {
                                imageView.setImage(null);
                            } else {
                                Image image = new Image(new ByteArrayInputStream(item));
                                imageView.setImage(image);
                            }

                            setGraphic(imageView);
                        }
                    };

                    return cell;
                });
            }
        }
    });
}




    private void redirectToPage3(){
            Parent root;
            try {
            
            
           
            root = FXMLLoader.load(getClass().getResource("Produitstat.fxml"));
            Scene c=new Scene(root);
            Stage stage=(Stage)statist.getScene().getWindow();
            stage.setScene(c);
        } catch (IOException ex) {
            Logger.getLogger(AjouterController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
          
    }
    @FXML
    private void stat(ActionEvent event) {
        this.redirectToPage3();
    }
@FXML
private void search(ActionEvent event) {
  //  fxxidproduit.setCellValueFactory(new PropertyValueFactory<>("id_produit"));
    fxxnomproduit.setCellValueFactory(new PropertyValueFactory<>("nom_produit"));
    fxxprix.setCellValueFactory(new PropertyValueFactory<>("prix"));
    fxxdescription.setCellValueFactory(new PropertyValueFactory<>("description"));
    fxxiduser.setCellValueFactory(new PropertyValueFactory<>("id_user"));
    fxximage1.setCellValueFactory(new PropertyValueFactory<>("image"));

    CRUDProduit cr = new CRUDProduit();
    List<Produit> li = cr.ListClasse1();
    ObservableList<Produit> dataList = FXCollections.observableArrayList(li);

    FilteredList<Produit> filteredData = new FilteredList<>(dataList, b -> true);
    filterfield.textProperty().addListener((observable, oldValue, newValue) -> {
        filteredData.setPredicate(prd -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();
            if (prd.getNom_produit().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                return true;
            } else if (prd.getDescription().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                return true;
            } else {
                return false;
            }
        });
    });

    SortedList<Produit> sortedData = new SortedList<>(filteredData);
    sortedData.comparatorProperty().bind(table_produit.comparatorProperty());

    // Initialize the image column cell factory
    fxximage1.setCellFactory(column -> {
        TableCell<Produit, byte[]> cell = new TableCell<Produit, byte[]>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(byte[] item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    imageView.setImage(null);
                } else {
                    Image image = new Image(new ByteArrayInputStream(item));
                    imageView.setImage(image);
                }

                setGraphic(imageView);
            }
        };

        return cell;
    });

    String searchTerm = filterfield.getText().trim();
    if (!searchTerm.isEmpty()) {
        // Filter the dataList instead of querying the database again
        filteredData.setPredicate(prd -> {
            String lowerCaseFilter = searchTerm.toLowerCase();
            if (prd.getNom_produit().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                return true;
            } else if (prd.getDescription().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                return true;
            } else {
                return false;
            }
        });

        // Set the table items with the filtered data
        table_produit.setItems(filteredData);
    } else {
        // If the search field is empty, show all products
        table_produit.setItems(sortedData);
    }
}
 private void redirectToPage4(){
            Parent root;
            try {
            
            
           
            root = FXMLLoader.load(getClass().getResource("FrontAjouter.fxml"));
            Scene c=new Scene(root);
            Stage stage=(Stage)statist.getScene().getWindow();
            stage.setScene(c);
        } catch (IOException ex) {
            Logger.getLogger(AjouterController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
          
    }
    @FXML
    private void front(ActionEvent event) {
        this.redirectToPage4();
    }

    
   
    @FXML
    private void pdf(ActionEvent event) throws FileNotFoundException, DocumentException, IOException {
    Document document = new Document();
    PdfWriter.getInstance(document, new FileOutputStream("table_produit.pdf"));
    document.open();

    PdfPTable pdfTable = new PdfPTable(table_produit.getColumns().size());

    // Add table headers
    for (int i = 0; i < table_produit.getColumns().size(); i++) {
        pdfTable.addCell(new Phrase(table_produit.getColumns().get(i).getText()));
    }

    // Add table rows
    for (int i = 0; i < table_produit.getItems().size(); i++) {
        for (int j = 0; j < table_produit.getColumns().size(); j++) {
            Object cellValue = table_produit.getColumns().get(j).getCellData(i);

            if (cellValue instanceof String) {
                pdfTable.addCell(new Phrase((String) cellValue));
            } else if (cellValue instanceof Float) {
                pdfTable.addCell(new Phrase(Float.toString((Float) cellValue)));
            } else if (cellValue instanceof byte[]) {
                com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance((byte[]) cellValue);

                pdfTable.addCell(image);
            } else {
                // Check if id_user column is empty
                if (j == table_produit.getColumns().indexOf(fxxiduser)) {
                    if (cellValue != null && !cellValue.toString().isEmpty()) {
                        pdfTable.addCell(new Phrase(cellValue.toString()));
                    }
                } else {
                    pdfTable.addCell(new Phrase(""));
                }
            }
        }
    }

    document.add(pdfTable);
    document.close();
    Process pro = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler table_produit.pdf");
}







        
    

}
 
    
    

