/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.club;
import utils.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class Clubservice implements CService <club>  {
    
    Connection cn = MyConnection.getTest().getCnx();

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
    
    public List<club> afficher(int id) {
       List<club> list = new ArrayList<>();

        return list;
    }
    
    
}
