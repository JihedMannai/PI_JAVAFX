/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.club;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import services.Clubservice;

/**
 * FXML Controller class
 *
 * @author amina
 */
public class InterfacefrontController implements Initializable {
    
    private MyListener myListener;
    private Clubservice cs = new Clubservice();
    @FXML
    private Label tf_titrepage;
    @FXML
    private GridPane grid;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    private void setChosenprod(club c) {
        
    }
    
    private void display2(){
        ///////////////////////////////////////////////////////////////
                ObservableList<club>  l2 = FXCollections.observableArrayList();
                ResultSet resultSet2 = cs.Selectionner();
                l2.clear(); 
                club pppp = new club("name","location",1);
                l2.add(pppp);
                int column =0;
                int row =2;
                if (l2.size() > 0) {
                setChosenprod(l2.get(0));
                myListener = new MyListener() {
                    @Override
                    public void onClickListener(club c) {
                        setChosenprod(c);
                    }
                };
                }
                
                
                try {
                    while (resultSet2.next()){
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/gui/unite.fxml"));
                        try {
                            AnchorPane anchorPane = fxmlLoader.load();
                            UniteController itemController = fxmlLoader.getController();
                            String terain_id  =resultSet2.getString("terain_id");
                            String name =resultSet2.getString("name");
                            String location =resultSet2.getString("location");  
                            int newidt=Integer.parseInt(terain_id);
                            club ppppp = new club(name,location,newidt);
                            itemController.setData(ppppp,myListener);
                            if (column == 1) {
                                column = 0;
                                row++;
                            }
                            grid.add(anchorPane, column++, row); //(child,column,row)
                            //set grid width
                            grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                            grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                            grid.setMaxWidth(Region.USE_PREF_SIZE);
                            //set grid height
                            grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                            grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                            grid.setMaxHeight(Region.USE_PREF_SIZE);
                            GridPane.setMargin(anchorPane, new Insets(10));
                        } catch (IOException ex) {
                            Logger.getLogger(InterfacefrontController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        }   
                } catch (SQLException ex) {
                    Logger.getLogger(InterfacefrontController.class.getName()).log(Level.SEVERE, null, ex);
                }
               
    }

    @FXML
    private void actualiser(ActionEvent event) {
        display2();
    }
    
}
