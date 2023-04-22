/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.club;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author amina
 */
public interface CService <T>{
    public void Ajouter(T t);
    public void Supprimer(int id);
    public void Modifier(int id,String nom,String location);
    public ResultSet Selectionner();
    public ResultSet SelectionnerSingle(int id);
    public List<club> afficher(int id);
}
