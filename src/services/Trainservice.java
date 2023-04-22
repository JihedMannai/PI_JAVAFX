/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.train;
import utils.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class Trainservice implements TService <train>  {
    
    Connection cn = MyConnection.getTest().getCnx();

    @Override
    public void Ajouter(train t) {
        try {
            System.out.println(t);
            String req = "INSERT INTO `terrain`(`type`,`image`) VALUES (?,?)";
            PreparedStatement pst = cn.prepareStatement(req);
            pst.setString(1, t.getType());
            pst.setString(2, t.getImage());
            pst.executeUpdate();
            System.out.println("terrain ajouté !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void Supprimer(int id) {
        System.out.println(id);
        try {
            String req = "DELETE FROM `terrain` WHERE `id` ="+id+"";
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
            String req = "SELECT * FROM `terrain`";
            PreparedStatement st = cn.prepareStatement(req);
            rs = st.executeQuery(req);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return rs;    
    }

    @Override
    public void Modifier(int id,String type) {
        try {
            String req = "UPDATE `terrain` SET `type`='"+type+"' WHERE `id` ="+id;
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
            String req = "SELECT * FROM `terrain` WHERE `id` ="+id+"";
            PreparedStatement st = cn.prepareStatement(req);
            rs = st.executeQuery(req);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return rs;     
    }
    
    public List<train> afficher(int id) {
       List<train> list = new ArrayList<>();

        return list;
    }
    
    
}
