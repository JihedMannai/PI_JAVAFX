/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author amina
 */
public class train {
    private int id;
    private String type;
    private String image;

    public train(int id, String type, String image) {
        this.id = id;
        this.type = type;
        this.image = image;
    }

    public train(String type, String image) {
        this.type = type;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "train{" + "id=" + id + ", type=" + type + ", image=" + image + '}';
    }

    public train(String type) {
        this.type = type;
    }
    
    public train(int id, String type) {
        this.id = id;
        this.type = type;
    }

}
