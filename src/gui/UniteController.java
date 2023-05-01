/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import entities.train;
import entities.club;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.Rating;
import services.Trainservice;
import utils.MyConnection;


/**
 * FXML Controller class
 *
 * @author amina
 */
public class UniteController implements Initializable {

    @FXML
    private Label tf_nomclub;
    @FXML
    private Label tf_locationclub;
    @FXML
    private Label tf_typeterrain;
    
    ////////////////////////
    private MyListener myListener;
    private club c ;
    @FXML
    private ImageView imagev;
    @FXML
    private Rating rating;
    @FXML
    private Label lspid;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void setData(club c, MyListener myListener) {
        this.c = c;
        this.myListener = myListener;
        tf_nomclub.setText("Nom: "+c.getName());
        lspid.setText(String.valueOf(c.getId()));
        tf_locationclub.setText("Localisation: "+c.getLocation());
        Trainservice ts = new Trainservice();
        ObservableList<train>  l = FXCollections.observableArrayList();
        ResultSet resultSet = ts.SelectionnerSingle(c.getTerain_id());
        l.clear();   
        try {
            while (resultSet.next()){
                l.add(new  train(
                        resultSet.getInt("id"),
                        resultSet.getString("type"),
                        resultSet.getString("image")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(InterfacebackController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Image image = new Image(getClass().getResourceAsStream(l.get(0).getImage()));
        imagev.setImage(image);
        tf_typeterrain.setText("Type: "+l.get(0).getType());
        System.out.println(lspid.getText());
        rating.setRating(c.getRating());
    }

    @FXML
    private void rate(ActionEvent event) {
        Float Somme =  ((float) rating.getRating());
        int id = Integer.parseInt(lspid.getText());
        Connection cn = MyConnection.getTest().getCnx();
        try {
            String requete1 = "UPDATE `club` SET `rating`='"+Somme+"' WHERE `id` ="+id;
            PreparedStatement pst = cn.prepareStatement(requete1);
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    private void lire(ActionEvent event) {
        VoiceManager vm = VoiceManager.getInstance();
        Voice voice = vm.getVoice("kevin16");
        voice.allocate();
        voice.speak(tf_nomclub.getText());  
        voice.speak(tf_locationclub.getText());  
        voice.speak(tf_typeterrain.getText());


        
    }
    
    @FXML
    private void qr(ActionEvent event) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        String myWeb = this.c.toString();
        int width = 300;
        int height = 300;
        String fileType = "png";
        
        BufferedImage bufferedImage = null;
        try {
            BitMatrix byteMatrix = qrCodeWriter.encode(myWeb, BarcodeFormat.QR_CODE, width, height);
            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            bufferedImage.createGraphics();
            
            Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, width, height);
            graphics.setColor(Color.BLACK);
            
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (byteMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }
            
            System.out.println("Success...");
            
        } catch (WriterException ex) {
            Logger.getLogger(UniteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        imagev.setImage(SwingFXUtils.toFXImage(bufferedImage, null));


    }

}
