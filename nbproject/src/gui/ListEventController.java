/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import entities.Participant;
import entities.Tournois;
import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import services.ParticipantService;

/**
 * FXML Controller class
 *
 * @author nassim
 */
public class ListEventController implements Initializable {

    @FXML
    private Label title;
    @FXML
    private Label time;
    @FXML
    private Label localisation;
    @FXML
    private Label description;
    @FXML
    private Button participer;
    @FXML
    private ImageView imageView;

    private int id_tournoi;

    public int getId_tournoi() {
        return id_tournoi;
    }

    public void setId_tournoi(int id_tournoi) {
        this.id_tournoi = id_tournoi;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    public void setData(Tournois tournoi) throws SQLException {

        File file = new File("C:\\Users\\nassim\\Desktop\\PIDEVTOURNOIS\\src\\img\\" + tournoi.getImage());
        System.out.println(file);
        Image image = new Image(file.toURI().toString());
        System.out.println(image);
        imageView.setImage(image);
        System.out.println("Setting data for tournoi: " + tournoi.getId());
        title.setText(String.valueOf(tournoi.getTitle()));
        time.setText(tournoi.getDate_tour().toString());
        localisation.setText(tournoi.getLocalisation());
        description.setText(tournoi.getDescription());
        setId_tournoi(tournoi.getId());

    }

    @FXML

private void participer(ActionEvent event) throws SQLException {
    ParticipantService ps = new ParticipantService();
    int id_user = 1;
    LocalDateTime now = LocalDateTime.now();
    java.sql.Date date = java.sql.Date.valueOf(now.toLocalDate());

    Participant p = new Participant(id_tournoi, id_user, "En Cours", now);
    ps.ajouter(p);

    // Show participation successful message
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Participation");
    alert.setHeaderText(null);
    alert.setContentText("Participation successful");
    alert.showAndWait();
    //sms ffff
    sendSms("+21693337077", "votre participation est effectué");
}

 public static final String ACCOUNT_SID = "ACbb39a824cc9a0059529ebdbcf3b13cdf";

  public static final String AUTH_TOKEN = "ae41cb37340c90f10445c6137a6f2013";

      public static void sendSms(String recipient, String messageBody) {
    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

    Message message = Message.creator(
            new PhoneNumber("+21693337077"), // To number
            new PhoneNumber("+16203122545"), // From number
            messageBody) // SMS body
        .create();

    System.out.println("Message sent: " + message.getSid());
  }

}
