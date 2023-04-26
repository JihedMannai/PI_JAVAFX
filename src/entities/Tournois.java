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
public class Tournois {
     private int id;
     private String title ,image,type,description,localisation;
     private int nb_place;
     private LocalDateTime date_tour ,date_fin;

    public Tournois() {
    }

    public Tournois(String title, String image, String type, String description, String localisation, int nb_place, LocalDateTime date_tour, LocalDateTime date_fin) {
        this.title = title;
        this.image = image;
        this.type = type;
        this.description = description;
        this.localisation = localisation;
        this.nb_place = nb_place;
        this.date_tour = date_tour;
        this.date_fin = date_fin;
    }

     
     
     
     
    public Tournois(int id, String title, String image, String type, String description, String localisation, int nb_place, LocalDateTime date_tour, LocalDateTime date_fin) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.type = type;
        this.description = description;
        this.localisation = localisation;
        this.nb_place = nb_place;
        this.date_tour = date_tour;
        this.date_fin = date_fin;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getLocalisation() {
        return localisation;
    }

    public int getNb_place() {
        return nb_place;
    }

    public LocalDateTime getDate_tour() {
        return date_tour;
    }

    public LocalDateTime getDate_fin() {
        return date_fin;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public void setNb_place(int nb_place) {
        this.nb_place = nb_place;
    }

    public void setDate_tour(LocalDateTime date_tour) {
        this.date_tour = date_tour;
    }

    public void setDate_fin(LocalDateTime date_fin) {
        this.date_fin = date_fin;
    }

    @Override
    public String toString() {
        return "Tournois{" + "id=" + id + ", title=" + title + ", image=" + image + ", type=" + type + ", description=" + description + ", localisation=" + localisation + ", nb_place=" + nb_place + ", date_tour=" + date_tour + ", date_fin=" + date_fin + '}';
    }
     
     
}
