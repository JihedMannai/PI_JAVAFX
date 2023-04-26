
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Skand
 */
public class MyDB {

    private String url = "jdbc:mysql://localhost:3306/tirxfinal123";
    private String username = "root";
    private String password = "";
    private Connection cnx;
    private static MyDB instance;

    private MyDB() {
        try {
            cnx = DriverManager.getConnection(url, username, password);
            System.out.println("Connexion établie");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    public static MyDB getInstance(){
        if(instance == null)
            instance = new MyDB();
        return instance;
    }

    public Connection getCnx() {
        return cnx;
    }

}