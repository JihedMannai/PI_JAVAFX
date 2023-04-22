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
public class club {
    private int id;
    private int agent_id;
    private int terain_id;
    private String name;
    private String location;

    public club(int id, int agent_id, int terain_id, String name, String location) {
        this.id = id;
        this.agent_id = agent_id;
        this.terain_id = terain_id;
        this.name = name;
        this.location = location;
    }

    public club(int agent_id, int terain_id, String name, String location) {
        this.agent_id = agent_id;
        this.terain_id = terain_id;
        this.name = name;
        this.location = location;
    }

    public club(int id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public club(String name, String location) {
        this.name = name;
        this.location = location;
    }
    
    public club(String name, String location,int idt) {
        this.name = name;
        this.location = location;
        this.terain_id=idt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(int agent_id) {
        this.agent_id = agent_id;
    }

    public int getTerain_id() {
        return terain_id;
    }

    public void setTerain_id(int terain_id) {
        this.terain_id = terain_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "club{" + "id=" + id + ", agent_id=" + agent_id + ", terain_id=" + terain_id + ", name=" + name + ", location=" + location + '}';
    }
    
    
}
