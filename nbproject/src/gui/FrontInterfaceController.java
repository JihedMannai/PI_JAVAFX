/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.Tournois;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.TournoisService;

/**
 * FXML Controller class
 *
 * @author nassim
 */
public class FrontInterfaceController implements Initializable {

    @FXML
    private Button Calendar;
    @FXML
    private VBox Vbox;

    private TournoisService ts = new TournoisService();
    private List<Tournois> tournois;
    @FXML
    private TextField Search;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       try {
            // TODO
            tournois = new ArrayList<>(ts.afficherLesTournois());

            for (Tournois tournoi : tournois) {
                System.out.println("Processing tournoi: " + tournoi.getId());

              
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("listEvent.fxml"));
                    VBox vBox = fxmlLoader.load();
                    ListEventController ListEventController = fxmlLoader.getController();

                    ListEventController.setData(tournoi);

                    Vbox.getChildren().add(vBox);
               

            }
        } catch (SQLException ex) {
            Logger.getLogger(FrontInterfaceController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FrontInterfaceController.class.getName()).log(Level.SEVERE, null, ex);
        }
      
  
    }

   
    
    @FXML
    private void Calendar(ActionEvent event) throws IOException {
       FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Calendar.fxml"));
       
        Parent root = loader.load();
        Calendar.getScene().setRoot(root);

    }

    @FXML

private void search(ActionEvent event) throws SQLException {
    String searchText = Search.getText().toLowerCase();
    List<Tournois> filteredTournois = new ArrayList<>();

    for (Tournois tournoi : tournois) {
        if (tournoi.getTitle().toLowerCase().contains(searchText) || tournoi.getDescription().toLowerCase().contains(searchText)) {
            filteredTournois.add(tournoi);
        }
    }

    Vbox.getChildren().clear();
    try {
        for (Tournois tournoi : filteredTournois) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("listEvent.fxml"));
            VBox vBox = fxmlLoader.load();
            ListEventController ListEventController = fxmlLoader.getController();

            ListEventController.setData(tournoi);

            Vbox.getChildren().add(vBox);
        }
    } catch (IOException ex) {
        Logger.getLogger(FrontInterfaceController.class.getName()).log(Level.SEVERE, null, ex);
    }
}


}
