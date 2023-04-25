/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;
import util.MyDB;
import entities.Tournois;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 *
 * @author nassim
 */
public class TournoisService {
    
    
    Connection cnx;

    public TournoisService() {
        cnx = MyDB.getInstance().getCnx();
    }

 
public void ajouterTournois(Tournois t) {
    String req = "INSERT INTO Tournois (title, image, type, description, localisation, nb_place, date_tour, date_fin) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
 
    try {
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, t.getTitle());
        ps.setString(2, t.getImage());
        ps.setString(3, t.getType());
        ps.setString(4, t.getDescription());
        ps.setString(5, t.getLocalisation());
        ps.setInt(6, t.getNb_place());
        System.out.println(Timestamp.valueOf(t.getDate_tour()));
        ps.setTimestamp(7,Timestamp.valueOf(t.getDate_tour()));
        ps.setTimestamp(8, Timestamp.valueOf(t.getDate_fin()));
        ps.executeUpdate();
        System.out.println("Tournois ajouté avec succès !");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}


public List<Tournois> afficherLesTournois() {
    List<Tournois> tournois = new ArrayList<>();
    String query = "SELECT * FROM `Tournois`";
    try {
        Statement statement = cnx.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        // Parcourir les résultats de la requête et créer des objets Tournois
        while (resultSet.next()) {
            Tournois tournoisObj = new Tournois();
            tournoisObj.setId(resultSet.getInt("id"));
            tournoisObj.setTitle(resultSet.getString("title"));
            tournoisObj.setImage(resultSet.getString("image"));
            tournoisObj.setType(resultSet.getString("type"));
            tournoisObj.setDescription(resultSet.getString("description"));
            tournoisObj.setLocalisation(resultSet.getString("localisation"));
            tournoisObj.setNb_place(resultSet.getInt("nb_place"));
            tournoisObj.setDate_tour(resultSet.getTimestamp("date_tour").toLocalDateTime());
            tournoisObj.setDate_fin(resultSet.getTimestamp("date_fin").toLocalDateTime());
            tournois.add(tournoisObj);
        }
    } catch (SQLException ex) {
        System.out.println("Erreur lors de la récupération des tournois : " + ex.getMessage());
    }

    return tournois;
}

public ObservableList<Tournois> afficherLesTournois2() {
    ObservableList<Tournois> tournois =  FXCollections.observableArrayList();
    String query = "SELECT * FROM `Tournois`";
    try {
        Statement statement = cnx.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        // Parcourir les résultats de la requête et créer des objets Tournois
        while (resultSet.next()) {
            Tournois tournoisObj = new Tournois();
            tournoisObj.setId(resultSet.getInt("id"));
            tournoisObj.setTitle(resultSet.getString("title"));
            tournoisObj.setImage(resultSet.getString("image"));
            tournoisObj.setType(resultSet.getString("type"));
            tournoisObj.setDescription(resultSet.getString("description"));
            tournoisObj.setLocalisation(resultSet.getString("localisation"));
            tournoisObj.setNb_place(resultSet.getInt("nb_place"));
            tournoisObj.setDate_tour(resultSet.getTimestamp("date_tour").toLocalDateTime());
            tournoisObj.setDate_fin(resultSet.getTimestamp("date_fin").toLocalDateTime());
            tournois.add(tournoisObj);
        }
    } catch (SQLException ex) {
        System.out.println("Erreur lors de la récupération des tournois : " + ex.getMessage());
    }

    return tournois;
}

public void modifierTournoi(Tournois t) {
    String req = "UPDATE Tournois SET title=?, image=?, type=?, description=?, localisation=?, nb_place=?, date_tour=?, date_fin=? WHERE id=?";
 
    try {
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, t.getTitle());
        ps.setString(2, t.getImage());
        ps.setString(3, t.getType());
        ps.setString(4, t.getDescription());
        ps.setString(5, t.getLocalisation());
        ps.setInt(6, t.getNb_place());
        ps.setTimestamp(7,Timestamp.valueOf(t.getDate_tour()));
        ps.setTimestamp(8, Timestamp.valueOf(t.getDate_fin()));
        ps.setInt(9, t.getId());
        ps.executeUpdate();
        System.out.println("Tournoi modifié avec succès !");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}

public void supprimerTournoi(int id) {
    try {
        PreparedStatement ps = cnx.prepareStatement("DELETE FROM Participant WHERE tournois_id = ?");
        ps.setInt(1, id);
        ps.executeUpdate();

        ps = cnx.prepareStatement("DELETE FROM Tournois WHERE id = ?");
        ps.setInt(1, id);
        int nb = ps.executeUpdate();

        if (nb > 0) {
            System.out.println("Le tournoi est supprimé avec succès...");
        } else {
            System.out.println("Le tournoi avec l'ID " + id + " n'existe pas.");
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}

public List<Tournois> getCalendarActivitiesMonth(ZonedDateTime mth) throws SQLException {
        String req = "select * from Tournois where Month(date_tour) = ? and year(date_tour)=?";
         System.out.println(mth.getMonthValue());
        PreparedStatement st = cnx.prepareStatement(req);
        st.setObject(1, mth.getMonthValue());
        st.setObject(2, mth.getYear());
        ResultSet rs = st.executeQuery();
         List<Tournois> tournoiss = new ArrayList<>();
          while (rs.next()) {
            Tournois p = new Tournois();
            p.setId(rs.getInt("id"));
            p.setTitle(rs.getString("title"));
            p.setType(rs.getString("type"));
              p.setDate_tour(rs.getTimestamp("date_tour").toLocalDateTime());
          

            tournoiss.add(p);
        }
          
        return tournoiss;
    }
    
}
