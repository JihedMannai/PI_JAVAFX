package service;

import entity.Reclamation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import util.MyConnection;
import java.sql.Date;
import java.util.Comparator;
import util.FilterBadWord;

public class ServiceReclamation implements IService<Reclamation> {

    Connection conn;

    public ServiceReclamation() {
        conn = MyConnection.getInstance().getConn();
    }

    @Override
    public void ajouter(Reclamation r) {
        try {
            String query = "INSERT INTO `reclamation` (`nom`, `prenom`, `email`,  `numero_mobile`, `date_creation`,  `description`, `status`, `image`) VALUES ('" + r.getNom() + "','" + r.getPrenom() + "','" + r.getEmail() + "','" + r.getNumero_mobile() + "','" + r.getDate_creation() + "','" + FilterBadWord.filter(r.getDescription()) + "','" + r.getStatus() + "', ?)";
            PreparedStatement st = conn.prepareStatement(query);
            st.setBlob(1, r.getImage());
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ServiceReclamation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void supprimer(int id) {
        try {
            String query = "DELETE FROM reclamation WHERE idReclamation=" + id;
            Statement st = conn.createStatement();
            st.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(ServiceReclamation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void modifier(int id, Reclamation r) {
    try {
        String query = "UPDATE reclamation SET `nom`=?, `prenom`=?, `email`=?, `screenshot`=?, `numero_mobile`=?, `date_creation`=?, `date_traitement`=?, `description`=?, `status`=?, `nomService`=?, `image`=? WHERE idReclamation=?";
        PreparedStatement st = conn.prepareStatement(query);
        st.setString(1, r.getNom());
        st.setString(2, r.getPrenom());
        st.setString(3, r.getEmail());
        st.setString(4, r.getScreenshot());
        st.setString(5, r.getNumero_mobile());
        st.setDate(6, new java.sql.Date(r.getDate_creation().getTime()));
        if (r.getDate_traitement() != null) {
            st.setDate(7, new java.sql.Date(r.getDate_traitement().getTime()));
        } else {
            st.setNull(7, java.sql.Types.DATE);
        }
        st.setString(8, FilterBadWord.filter(r.getDescription()));
        st.setString(9, r.getStatus());
        st.setString(10, r.getNomService());
        st.setBlob(11, r.getImage());
        st.setInt(12, id);
        st.executeUpdate();
    } catch (SQLException ex) {
        Logger.getLogger(ServiceReclamation.class.getName()).log(Level.SEVERE, null, ex);
    }
}


    @Override
    public List<Reclamation> afficher() {
        List<Reclamation> lr = new ArrayList<>();
        try {
            String query = "SELECT * FROM reclamation";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Reclamation r = new Reclamation();
                r.setIdReclamation(rs.getInt("idReclamation"));
                r.setNom(rs.getString("nom"));
                r.setPrenom(rs.getString("prenom"));
                r.setEmail(rs.getString("email"));
                r.setScreenshot(rs.getString("screenshot"));
                r.setNumero_mobile(rs.getString("numero_mobile"));
                r.setDate_creation(rs.getDate("date_creation"));
                r.setDate_traitement(rs.getDate("date_traitement"));
                r.setDescription(rs.getString("description"));
                r.setStatus(rs.getString("status"));
                r.setNomService(rs.getString("nomService"));
                r.setImage(rs.getBlob("image"));
                lr.add(r);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceReclamation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lr;
    }

    @Override
    public Reclamation getById(int id) {
        return afficher().stream().filter(r -> r.getIdReclamation() == id).findFirst().orElse(null);
    }

    
    public List<Reclamation> triparNom() {
        return afficher().stream()
                .sorted(Comparator.comparing(Reclamation::getNom)).collect(Collectors.toList());
    }
}
