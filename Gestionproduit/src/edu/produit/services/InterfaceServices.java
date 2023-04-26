/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.produit.services;

import edu.produit.entites.Produit;
import java.util.List;
import java.util.Map;

/**
 *
 * @author USER
 */
public interface InterfaceServices {
    
    public void ajouterproduit (Produit p);
    public void modifierproduit(Produit p);
    public void supprimerproduit(int id_produit);
    public List<Produit> afficherproduit();
    public List<Produit> ListClasse1();
    public float totalPrixProduits(List<Produit> produits);
    public int nombreproduits();
    public Map<Integer, String> chercherUser();
}
