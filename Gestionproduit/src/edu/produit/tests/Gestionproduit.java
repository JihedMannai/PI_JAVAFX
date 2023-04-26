/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.produit.tests;

import edu.produit.entites.Produit;
import edu.produit.services.CRUDProduit;
import edu.produit.utils.MyConnection;

/**
 *
 * @author USER
 */
public class Gestionproduit {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MyConnection connexion = MyConnection.getInstance();
        Produit p = new Produit("makrouna", 500,"bzeyedthon",1);
        CRUDProduit crud = new CRUDProduit();
       crud.ajouterproduit(p);
       crud.supprimerproduit(15);
        
        crud.afficherproduit();
    }
    
}
