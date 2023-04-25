/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author nassim
 */


public class FXMain extends Application {

@Override
public void start(Stage primaryStage) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/FrontInterface.fxml"));
        Parent root = loader.load();
        loader.setRoot(root); // set the root before loading the FXML file
        Scene scene = new Scene(root);
        primaryStage.setTitle("Dashbord Admin");
        primaryStage.setScene(scene);
        primaryStage.show();
    } catch (IOException ex) {
        System.out.println(ex.getMessage());
    }
}

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}