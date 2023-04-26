/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.produit.services;

import edu.produit.entites.Produit;
import edu.produit.utils.MyConnection;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USER
 */
public class CRUDProduit implements InterfaceServices{
    Statement ste;
    Connection conn = MyConnection.getInstance().getConnection();
    
    
    /*public void ajouterproduit(Produit p) {
        try {
        ste = conn.createStatement();
        String req = "Insert into produit values(0,'"+p.getNom_produit()+"','"+p.getPrix()+"','"+p.getDescription()+"')";
        ste.executeUpdate(req);
        System.out.println("Produit ajouté");
    } catch (SQLException ex) {
            System.out.println("Produit non ajouté!!!!");    }
    }*/

    
    public void ajouterproduit(Produit p) {
       try {
            String req = "INSERT INTO `produit` (`nom_produit`, `prix`,`description`,`id_user`,`image`) VALUES (?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(req);
            ps.setBytes(5, p.getImage());
            ps.setInt(4, p.getId_user());
            ps.setString(3, p.getDescription());
            ps.setFloat(2, p.getPrix());
            ps.setString(1, p.getNom_produit());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    
    public void modifierproduit(Produit p) {
       try {
            String req = "UPDATE `produit` SET `nom_produit` = '"+ p.getNom_produit()+ "',`prix` = '" +p.getPrix()+ "',`description`='"+p.getDescription()+"',`id_user`='"+p.getId_user()+"' WHERE `produit`.`id_produit` = " + p.getId_produit();
            Statement st = conn.createStatement();
            st.executeUpdate(req);
            System.out.println("Produit updated !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    
    public void supprimerproduit(int id_produit) {
        try {
            String req = "DELETE FROM `produit` WHERE id_produit = " + id_produit;
            Statement st = conn.createStatement();
            st.executeUpdate(req);
            System.out.println("Produit deleted !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    
    public List<Produit> afficherproduit() {
       List<Produit> prod = new ArrayList<Produit>();
        try {
            ste =conn.createStatement();
            String req = "SELECT * FROM `produit`";
        ResultSet result = ste.executeQuery(req);
        
        while (result.next()) {
            Produit resultProduit = new Produit( result.getString("nom_produit"),result.getFloat("prix"),result.getString("description"),result.getInt("id_user"),result.getBytes("image"));
            prod.add(resultProduit);
        }
        System.out.println(prod);
      
    } catch (SQLException ex) {
         System.out.println(ex);   
    }
   return prod;
    }
    
    
    public int getid (int n ){ 
    int t=0 ; 
    try {
            String requete = "select * from produit where ?= id_produit";
            PreparedStatement pst = conn.prepareStatement(requete);
            pst.setInt(1, n);
            ResultSet e = pst.executeQuery();
            while(e.next()){
            Produit pre = new Produit(); 
            
            pre.setId_produit(e.getInt("id_produit"));
            t=pre.getId_produit();   }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
   
return t ; 
    }
    
    
    public List<Produit> ListClasse1() {
    List<Produit> Mylist = new ArrayList<>();
    try {
        String requete = "select * from produit ORDER BY prix ";
        PreparedStatement pst = conn.prepareStatement(requete);

        ResultSet e = pst.executeQuery();
        while (e.next()) {
            Produit pr = new Produit();

            pr.setId_produit(e.getInt("id_produit"));
            pr.setNom_produit(e.getString("nom_produit"));
            pr.setPrix(e.getInt("prix"));

            pr.setDescription(e.getString("description"));
            pr.setId_user(e.getInt("id_user"));

            // Load the image from the database
            Blob blob = e.getBlob("image");
            if (blob != null) {
                InputStream in = blob.getBinaryStream();
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buffer = new byte[4096];
                int bytesRead = -1;

                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }

                byte[] imageBytes = out.toByteArray();
                pr.setImage(imageBytes);
            }

            Mylist.add(pr);
        }

    } catch (SQLException | IOException ex) {
        System.out.println(ex.getMessage());
    }
    return Mylist;
}

        public float totalPrixProduits(List<Produit> produits) {
         float total = 0;
    for (Produit p : produits) {
        total += p.getPrix();
    }
    return total;
}
    public int nombreproduits() {
    int count = 0;
    String query = "SELECT COUNT(*) FROM produit";
    try {
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        rs.next();
        count = rs.getInt(1);
    } catch (SQLException ex) {
        System.out.println("Error: " + ex.getMessage());
    }
    return count;
}
    public Map<Integer, String> chercherUser() {
    Map<Integer, String> users = new HashMap<>();
    try {
        String sql = "SELECT id_user, nom FROM utilisateur";
        Statement st = conn.createStatement();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id_user = rs.getInt("id_user");
            String nom = rs.getString("nom");
            users.put(id_user, nom);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return users;
}
public List<Produit> searchProduit(String searchTerm) {
    List<Produit> produitList = new ArrayList<>();
    try {
        String requete = "SELECT * FROM produit WHERE nom_produit LIKE ?";
        PreparedStatement pst = conn.prepareStatement(requete);
        pst.setString(1, "%" + searchTerm + "%");
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            Produit produit = new Produit();
            produit.setId_produit(rs.getInt("id_produit"));
            produit.setNom_produit(rs.getString("nom_produit"));
            produit.setPrix(rs.getFloat("prix"));
            produit.setDescription(rs.getString("description"));
            produit.setId_user(rs.getInt("id_user"));
            produit.setImage(rs.getBytes("image"));
            produitList.add(produit);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return produitList;
}

}
    