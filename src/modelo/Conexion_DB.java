/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Cathecita
 */
public class Conexion_DB {
    
      private static String dbname = "webpetdb";
      private static String username = "root";
      private static String password = "123456";
      
      static Connection con=null;
    
      public static Connection getConnection() {
        return getConnection(dbname, username, password);
      }
      
      
        private static Connection getConnection(String db_name, String user_name, String password) {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3307/" + db_name + "?useSSL=false&serverTimezone=UTC",
                user_name, password
            );
            System.out.println("Conectado ok");
        } catch (Exception e) {
            System.out.println("Error de conexión a la bd: " + e);
        }

        return con;     
    
    }
    
    
    
}
