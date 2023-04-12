/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import Entities.user;
import Utils.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jihed zavou
 */
public class ServiceUser {
    Connection cnx;
    private PreparedStatement ste ;

    public ServiceUser() {
    cnx =MyConnection.getInstance().getConnection();        
    }
    
    
   public void ajouteru(user u) {
       String req ="INSERT INTO user (email,roles,password,nom,prenom,date_naissance,adresse,role) VALUES (?,?,?,?,?,?,?,?)";
        try {
            ste = cnx.prepareStatement(req);
            
            
            ste.setString(1,u.getEmail());
            ste.setString(2,u.getRoles());
            ste.setString(3,u.getPassword());
            ste.setString(4,u.getNom());
            ste.setString(5,u.getPrenom());
            ste.setDate(6, new java.sql.Date(u.getDate_naissance().getTime()));
            ste.setString(7,u.getAdresse());
            ste.setString(8,u.getRole());



            ste.executeUpdate();
            System.out.println("User ajoutée");
            
        } catch (SQLException ex) {
            System.out.println("Probléme");
            System.out.println(ex.getMessage());
      }
    }
   
    public List<user> ListClasse() {
        List<user> Mylist = new ArrayList<>();
        try {
            String requete = "select * from user";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet e = pst.executeQuery();
            while (e.next()) {
                user pre = new user();
              
            pre.setNom(e.getString("nom"));
            pre.setPrenom(e.getString("prenom"));
            pre.setDate_naissance(e.getDate("date_naissance"));
            pre.setRole(e.getString("role"));
            pre.setRoles(e.getString("roles"));
            pre.setAdresse(e.getString("adresse"));
            pre.setEmail(e.getString("email"));
                        pre.setPassword(e.getString("password"));


            
                Mylist.add(pre);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return Mylist;
    }
    
    public void supprimer (user u ) {
    String requete = "DELETE FROM user where nom =?";
           try {
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, u.getNom());
            pst.executeUpdate();
            System.out.println("user Supprimée !!!!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
    }
   
}
