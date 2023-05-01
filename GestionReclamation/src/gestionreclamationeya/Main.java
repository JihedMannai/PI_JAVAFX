/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionreclamationeya;

import service.ServiceReclamation;

/**
 *
 * @author Yasmine
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ServiceReclamation sr=new ServiceReclamation();
        System.out.println(sr.afficher());
    }
    
}
