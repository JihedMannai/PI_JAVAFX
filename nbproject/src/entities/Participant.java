/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.time.*;

/**
 *
 * @author nassim
 */
public class Participant {
     private int id,tournois_id,users_id;
     private String status;
     private LocalDateTime date_p;

    public Participant(int id, int tournois_id, int users_id, String status, LocalDateTime date_p) {
        this.id = id;
        this.tournois_id = tournois_id;
        this.users_id = users_id;
        this.status = status;
        this.date_p = date_p;
    }

    public Participant() {
    }

    public Participant(int tournois_id, int users_id, String status, LocalDateTime date_p) {
        this.tournois_id = tournois_id;
        this.users_id = users_id;
        this.status = status;
        this.date_p = date_p;
    }
    
    

    public int getId() {
        return id;
    }

    public int getTournois_id() {
        return tournois_id;
    }

    public int getUsers_id() {
        return users_id;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getDate_p() {
        return date_p;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTournois_id(int tournois_id) {
        this.tournois_id = tournois_id;
    }

    public void setUsers_id(int users_id) {
        this.users_id = users_id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDate_p(LocalDateTime date_p) {
        this.date_p = date_p;
    }
    
  
    @Override
    public String toString() {
        return "participant{" + "id=" + id + ", tournois_id=" + tournois_id + ", users_id=" + users_id + ", status=" + status + ", date_p=" + date_p + '}';
    }
     
     
}
