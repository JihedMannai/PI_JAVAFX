
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import utils.MyConnection;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author amina
 */
public class main extends Application 
{
    public static Stage stg;
    @Override
    public void start(Stage primaryStage) throws IOException
    {
        this.stg = primaryStage;
        FXMLLoader loader= new FXMLLoader(getClass().getResource("../gui/interface.fxml"));
        Parent root= loader.load();
        Scene scene= new Scene(root);
        primaryStage.setTitle("clubterain");
        primaryStage.minWidthProperty().bind(scene.heightProperty().multiply(2));
        primaryStage.minHeightProperty().bind(scene.widthProperty().divide(2));
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        
        launch(args);
    }
    
}
