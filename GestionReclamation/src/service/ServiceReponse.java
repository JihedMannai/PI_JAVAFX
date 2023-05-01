package service;

import entity.Reclamation;
import entity.Reponse;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.MyConnection;

public class ServiceReponse implements IService<Reponse> {
    Connection conn;
    
    public ServiceReponse() {
        conn = MyConnection.getInstance().getConn();
    }

    @Override
    public void ajouter(Reponse r) {
        try {
            String query = "INSERT INTO `reponse` (`idReclamation`, `Text`, `status`, `date`) VALUES ('" + r.getIdReclamation() + "','" + r.getText() + "','" + r.getStatus() + "','" + r.getDate() + "')";
            Statement st = conn.createStatement();
            st.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(ServiceReponse.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void supprimer(int id) {
        try {
            String query = "DELETE FROM reponse WHERE idReponse=" + id;
            Statement st = conn.createStatement();
            st.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(ServiceReponse.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void modifier(int id, Reponse r) {
        try {
            String query = "UPDATE reponse SET `idReclamation`='" + r.getIdReclamation() + "', `Text`='" + r.getText() + "', `status`='" + r.getStatus() + "', `date`='" + r.getDate() + "' WHERE idReponse=" + id;
            Statement st = conn.createStatement();
            st.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(ServiceReponse.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Reponse> afficher() {
        List<Reponse> lr = new ArrayList<>();
        try {
            String query = "SELECT * FROM reponse";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Reponse r = new Reponse();
                r.setIdResponse(rs.getInt("idReponse"));
                r.setIdReclamation(rs.getInt("idReclamation"));
                r.setText(rs.getString("Text"));
                r.setStatus(rs.getString("status"));
                r.setDate(rs.getDate("date"));
                lr.add(r);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceReponse.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lr;
    }

    @Override
    public Reponse getById(int id) {
        return afficher().stream().filter(r -> r.getIdResponse() == id).findAny().orElse(null);
    }
    /*
    public List<Reponse> afficherReponseParUser(String email) {
        List<Reponse> resultat = new ArrayList<>();
        ServiceReclamation sr = new ServiceReclamation();
        for (Reclamation r : sr.afficherReclamationUser(email)) {
            for (Reponse rep : afficher()) {
                if (rep.getIdReclamation() == r.getIdReclamation()) {
                    resultat.add(rep);
                }
            }
        }
        return resultat;
    }*/
}
