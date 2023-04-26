/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.produit.entites;

import java.util.Objects;

/**
 *
 * @author USER
 */
public class Produit {
    int id_produit;
    String nom_produit;
    float prix;
    String description;
    int id_user;
    byte[] image;

    public void setImage(byte[] image) {
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }

    public Produit(String nom_produit, float prix, String description, int id_user, byte[] image) {
        this.nom_produit = nom_produit;
        this.prix = prix;
        this.description = description;
        this.id_user = id_user;
        this.image = image;
    }

    public Produit(int id_produit, String nom_produit, float prix, String description, int id_user, byte[] image) {
        this.id_produit = id_produit;
        this.nom_produit = nom_produit;
        this.prix = prix;
        this.description = description;
        this.id_user = id_user;
        this.image = image;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_user() {
        return id_user;
    }

    public Produit(String nom_produit, float prix, String description, int id_user) {
        this.nom_produit = nom_produit;
        this.prix = prix;
        this.description = description;
        this.id_user = id_user;
    }

    public Produit(int id_produit, String nom_produit, float prix, String description, int id_user) {
        this.id_produit = id_produit;
        this.nom_produit = nom_produit;
        this.prix = prix;
        this.description = description;
        this.id_user = id_user;
    }

    public Produit() {
    }

    public Produit(int id_produit, String nom_produit, float prix, String description) {
        this.id_produit = id_produit;
        this.nom_produit = nom_produit;
        this.prix = prix;
        this.description = description;
        
    }
    
    

    @Override
    public String toString() {
        return "Produit{" + "id_produit=" + id_produit + ", nom_produit=" + nom_produit + ", prix=" + prix + ", description=" + description + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + this.id_produit;
        hash = 47 * hash + Objects.hashCode(this.nom_produit);
        hash = 47 * hash + Float.floatToIntBits(this.prix);
        hash = 47 * hash + Objects.hashCode(this.description);
        
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Produit other = (Produit) obj;
        if (this.id_produit != other.id_produit) {
            return false;
        }
        if (Float.floatToIntBits(this.prix) != Float.floatToIntBits(other.prix)) {
            return false;
        }
        
        if (!Objects.equals(this.nom_produit, other.nom_produit)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        return true;
    }

    public Produit(String nom_produit, float prix, String description) {
        this.nom_produit = nom_produit;
        this.prix = prix;
        this.description = description;
        
    }

    public int getId_produit() {
        return id_produit;
    }

    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }

    public String getNom_produit() {
        return nom_produit;
    }

    public void setNom_produit(String nom_produit) {
        this.nom_produit = nom_produit;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    
}
