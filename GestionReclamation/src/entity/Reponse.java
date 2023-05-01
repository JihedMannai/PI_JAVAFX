/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author Yasmine
 */
import java.util.Date;

public class Reponse {
    private int idResponse;
    private int idReclamation;
    private String Text;
    private String status;
    private Date date;

    public Reponse() {
    }

    public Reponse(int idReclamation, String Text, String status, Date date) {
        this.idReclamation = idReclamation;
        this.Text = Text;
        this.status = status;
        this.date = date;
    }

    public Reponse(int idResponse, int idReclamation, String Text, String status, Date date) {
        this.idResponse = idResponse;
        this.idReclamation = idReclamation;
        this.Text = Text;
        this.status = status;
        this.date = date;
    }

    public int getIdResponse() {
        return idResponse;
    }

    public void setIdResponse(int idResponse) {
        this.idResponse = idResponse;
    }

    public int getIdReclamation() {
        return idReclamation;
    }

    public void setIdReclamation(int idReclamation) {
        this.idReclamation = idReclamation;
    }

    public String getText() {
        return Text;
    }

    public void setText(String Text) {
        this.Text = Text;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Reponse{" + "idResponse=" + idResponse + ", idReclamation=" + idReclamation + ", Text=" + Text + ", status=" + status + ", date=" + date + '}'+"\n";
    }

    
    
}
