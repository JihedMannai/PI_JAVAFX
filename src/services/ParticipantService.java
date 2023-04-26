/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;
import entities.Participant;
import entities.Tournois;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import util.MyDB;
import java.sql.Timestamp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 *
 * @author nassim
 */
public class ParticipantService  {
    
     Connection cnx;

    public ParticipantService() {
        cnx = MyDB.getInstance().getCnx();
    }

   public void ajouterParticipant(Participant p, int tournois_id, int users_id) {
    String query = "INSERT INTO Participant (tournois_id, users_id, status, date_p)   VALUES (?, ?, ?, ?)";
    try {
        PreparedStatement ps = cnx.prepareStatement(query);
        ps.setInt(1, tournois_id);
        ps.setInt(2, users_id);
        ps.setString(3, p.getStatus());
        ps.setTimestamp(4, Timestamp.valueOf(p.getDate_p()));
        ps.executeUpdate();
        System.out.println("Participant ajouté !");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage()); 
    }
}
   
   public void ajouter(Participant t) throws SQLException {
        String req = "INSERT INTO participant(tournois_id, users_id, date_p, status) VALUES ('" + t.getTournois_id()+ "', '" + t.getUsers_id()+ "', CURRENT_TIMESTAMP, '" + t.getStatus()+ "')";
        Statement st = cnx.createStatement();
        st.executeUpdate(req);
    }



  
public List<Participant> afficherParticipants() {
    List<Participant> participants = new ArrayList<>();
    String qry = "SELECT p.id, p.status, p.date_p, t.title, p.tournois_id, p.users_id " +
             "FROM Participant p JOIN Tournois t ON p.tournois_id = t.id " +
             "JOIN User u ON p.users_id = u.id";
    try {
        Statement stm = cnx.createStatement();
        ResultSet rs = stm.executeQuery(qry);

        while (rs.next()) {
            Participant p = new Participant();
            p.setId(rs.getInt("id"));
            p.setStatus(rs.getString("status"));
            p.setDate_p(rs.getTimestamp("date_p").toLocalDateTime());
            p.setTournois_id(rs.getInt("tournois_id"));
            p.setUsers_id(rs.getInt("users_id"));
            participants.add(p);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }

    return participants;
}

public ObservableList<Participant> afficherParticipants2() {
    ObservableList<Participant> participants =   FXCollections.observableArrayList();
    String qry = "SELECT p.id, p.status, p.date_p, t.title, p.tournois_id, p.users_id " +
             "FROM Participant p JOIN Tournois t ON p.tournois_id = t.id " +
             "JOIN User u ON p.users_id = u.id";
    try {
        Statement stm = cnx.createStatement();
        ResultSet rs = stm.executeQuery(qry);

        while (rs.next()) {
            Participant p = new Participant();
            p.setId(rs.getInt("id"));
            p.setStatus(rs.getString("status"));
            p.setDate_p(rs.getTimestamp("date_p").toLocalDateTime());
            p.setTournois_id(rs.getInt("tournois_id"));
            p.setUsers_id(rs.getInt("users_id"));
            participants.add(p);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }

    return participants;
}




public void modifierParticipant(Participant p) {
    String req = "UPDATE Participant SET status=?, date_p=?, tournois_id=?, users_id=? WHERE id=?";
    try {
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, p.getStatus());
        ps.setTimestamp(2, Timestamp.valueOf(p.getDate_p()));
        ps.setInt(3, p.getTournois_id());
        ps.setInt(4, p.getUsers_id());
        ps.setInt(5, p.getId());
        ps.executeUpdate();
        System.out.println("Participant modifié !");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}

public void SupprimerParticipant(int id) {
    try {
        Statement st = cnx.createStatement();
        String req = "SELECT * FROM Participant WHERE id = " + id;
        ResultSet rs = st.executeQuery(req);
        if (!rs.next()) {
            System.out.println("Le Participant avec l'ID " + id + " n'existe pas.");
        } else {
            req = "DELETE FROM Participant WHERE id = " + id;
            st.executeUpdate(req);
            System.out.println("Le Participant est supprimé avec succès...");
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}

}
