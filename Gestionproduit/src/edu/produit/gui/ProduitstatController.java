/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.produit.gui;

import edu.produit.entites.Produit;
import edu.produit.services.CRUDProduit;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class ProduitstatController implements Initializable {

    @FXML
    private Label totalLabel;
    @FXML
    private Label avgLabel;
    @FXML
    private PieChart pie;

    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {
    CRUDProduit cr = new CRUDProduit();
    Double total = 0.0;
    Double avg = 0.0;
    // Total number of products
    int totalProducts = cr.nombreproduits();
    List<Produit> produits = cr.afficherproduit(); 
    // Total price of all products
    float totalPrice = cr.totalPrixProduits(produits);

    // Average price of products
    float avgPrice = (totalProducts > 0) ? totalPrice / totalProducts : 0;

    // Set the labels
    totalLabel.setText(String.valueOf(totalProducts));
    avgLabel.setText(String.valueOf(avgPrice));

    // Pie chart data
    ObservableList<PieChart.Data> data = FXCollections.observableArrayList();

    // Count number of products per user
    Map<Integer, Integer> countByUser = new HashMap<>();
    Map<Integer, String> users = cr.chercherUser();
    for (Produit produit : produits) {
        int userId = produit.getId_user();
        if (!countByUser.containsKey(userId)) {
            countByUser.put(userId, 0);
        }
        countByUser.put(userId, countByUser.get(userId) + 1);
    }

    // Add data to the pie chart
    for (Map.Entry<Integer, Integer> entry : countByUser.entrySet()) {
        int userId = entry.getKey();
        int count = entry.getValue();
        String userName = users.get(userId);
        data.add(new PieChart.Data(userName, count));
    }

    pie.setData(data);
}

    
    
}
