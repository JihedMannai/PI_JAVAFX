/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.train;
import entities.club;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import services.Trainservice;

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
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void setData(club c, MyListener myListener) {
        System.out.println("welco");
        this.c = c;
        this.myListener = myListener;
        tf_nomclub.setText(c.getName());
        tf_locationclub.setText(c.getLocation());
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
    }

}
