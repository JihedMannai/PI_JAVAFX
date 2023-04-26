/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import static com.mysql.jdbc.Messages.getString;
import entities.club;
import java.awt.AWTException;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import utils.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;


public class Clubservice implements CService <club>  {
    
    Connection cn = MyConnection.getTest().getCnx();
    int idsession =     1;

    @Override
    public void Ajouter(club t) {
        try {
            System.out.println(t);
            String req = "INSERT INTO `club`(`name`, `location`,`terain_id`) VALUES (?,?,?)";
            PreparedStatement pst = cn.prepareStatement(req);
            pst.setString(1, t.getName());
            pst.setString(2, t.getLocation());
            pst.setInt(3,t.getTerain_id());
            pst.executeUpdate();
            System.out.println("Club ajouté !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void Supprimer(int id) {
        System.out.println(id);
        try {
            String req = "DELETE FROM `club` WHERE `id` ="+id+"";
            PreparedStatement st = cn.prepareStatement(req);
            st.executeUpdate(req);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } 
    }


    @Override
    public ResultSet Selectionner() {
        ResultSet rs = null;
        try {
            String req = "SELECT * FROM `club`";
            PreparedStatement st = cn.prepareStatement(req);
            rs = st.executeQuery(req);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return rs;    
    } 
    
    public void save(club t) {
        try {
            System.out.println(t);
            
            if (issaved(idsession,t.getId())){
            String req = "INSERT INTO `fvt_club`(`id_user`, `id_club`) VALUES (?,?,?)";
            PreparedStatement pst = cn.prepareStatement(req);
            
            pst.setInt(1, t.getId());
            pst.setInt(2, t.getId());
            pst.executeUpdate();
            }else{
                String req ="DELETE FROM `fvt_club` WHERE `id_user` ="+idsession+" AND id_club="+t.getId();
                PreparedStatement pst = cn.prepareStatement(req);
                pst.executeUpdate();
                
            
            }
            
          
           
            
            System.out.println("saved");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public boolean issaved(int id,int id_exp){
        ResultSet rs = null;
        try {
            String req =  "SELECT * FROM fvt_club WHERE id_club ="+id_exp;
            
            PreparedStatement st = cn.prepareStatement(req);
            rs = st.executeQuery(req);
            while (rs.next()){
                
                if (rs.getInt("id_user") == id){
                    //System.out.println(resultSet.getString("nom"));
                    
                   return true;
                    
                }
                   
            }
        }catch(SQLException ex){
            System.err.println(ex.getMessage());
        }
        
        //System.out.println(resultSet);
        return false;
        
        
        
    }
    
    public ResultSet clubfav() {
        ResultSet rs = null;
        try {
            String req = "SELECT * FROM `fvt_club`";
            PreparedStatement st = cn.prepareStatement(req);
            rs = st.executeQuery(req);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return rs;    
    }

    @Override
    public void Modifier(int id,String nom,String location) {
        try {
            String req = "UPDATE `club` SET `name`='"+nom+"',`location`='"+location+"' WHERE `id` ="+id;
            PreparedStatement st = cn.prepareStatement(req);
            st.executeUpdate(req);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } 
    }

    @Override
    public ResultSet SelectionnerSingle(int id) {
        ResultSet rs = null;
        try {
            String req = "SELECT * FROM `club` WHERE `id` ="+id+"";
            PreparedStatement st = cn.prepareStatement(req);
            rs = st.executeQuery(req);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return rs;    
    }
    

    public club SelectionnerSingle1(int id) {
        ResultSet rs = null;
        club ppp = null;
        try {
            String req = "SELECT * FROM `club` WHERE `id` ="+id+"";
            PreparedStatement st = cn.prepareStatement(req);
            rs = st.executeQuery(req);
            ppp = new club ( rs.getInt("agent_id"), rs.getInt("terain_id"), rs.getString("name"),rs.getString("location")); 
            return ppp;
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return ppp;
            
    }
    
    public List<club> afficher(int id) {
       List<club> list = new ArrayList<>();

        return list;
    }
    
       public void notifyUser(String message) {
        if (SystemTray.isSupported()) {
            try {
                // Initialiser SystemTray
                SystemTray tray = SystemTray.getSystemTray();

                // Créer une icône pour la notification
                java.awt.Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
                TrayIcon trayIcon = new TrayIcon(image, "Notification");

                // Ajouter l'icône au SystemTray
                tray.add(trayIcon);

                // Afficher la notification
                trayIcon.displayMessage("Notification", message, TrayIcon.MessageType.INFO);
            } catch (AWTException e) {
                System.err.println("Erreur lors de l'initialisation du SystemTray: " + e);
            }
        } else {
            System.out.println("SystemTray n'est pas pris en charge");
        }
    }
    
    
}
