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
 * @author MIller
 */
public class Conexion_DB {
    
      private static String dbname = "webpetdb";
      private static String username = "root";
      private static String password = "123456";
      static Connection con=null;
    
      public static Connection getConnection()
    {
        if (con != null) return con;
        // get db, user, pass from settings file
        return getConnection(dbname, username, password);
    }

    private static Connection getConnection(String db_name,String user_name,String password)
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3307/"+db_name+"?user="+user_name+"&password="+password);
            System.out.println("Conectado ok");
        }
        catch(Exception e)
        {
            System.out.println("Error de conexión a la bd"+e);
        }

        return con;     
    
    }
    
    
    
}
