/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


/**
 *
 * @author Cathecita
 */
public class Mascotas {
    
    Connection connection;
    private Integer id_mascota;
    private String nombre_mascota;
    private String especie_mascota;
    private String genero_mascota;
    private String raza_mascota;
    private String color_mascota;
    private String edad_mascota;
    private Integer id_cliente_mascota;
    
    
    
    
    //constructor
    
    public Mascotas(){
    
    }
    
    //datos desde el formulario
    public Mascotas(Integer ID, String NOMBRE, String ESPECIE, String GENERO, String RAZA, 
            String COLOR, String EDAD, Integer ID_CLI_MASCOTA)
    {
    
        
        this.id_mascota = ID;
        this.nombre_mascota = NOMBRE;
        this.especie_mascota = ESPECIE;
        this.genero_mascota = GENERO;
        this.raza_mascota = RAZA;
        this.color_mascota = COLOR;
        this.edad_mascota = EDAD;
        this.id_cliente_mascota = ID_CLI_MASCOTA;
        
    }

    public Integer getId_mascota() {
        return id_mascota;
    }

    public String getNombre_mascota() {
        return nombre_mascota;
    }

    public String getEspecie_mascota() {
        return especie_mascota;
    }

    public String getRaza_mascota() {
        return raza_mascota;
    }

    public String getColor_mascota() {
        return color_mascota;
    }

    public String getEdad_mascota() {
        return edad_mascota;
    }

    public Integer getId_cliente_mascota() {
        return id_cliente_mascota;
    }

    public void setId_mascota(Integer id_mascota) {
        this.id_mascota = id_mascota;
    }

    public void setNombre_mascota(String nombre_mascota) {
        this.nombre_mascota = nombre_mascota;
    }

    public void setEspecie_mascota(String especie_mascota) {
        this.especie_mascota = especie_mascota;
    }

    public void setRaza_mascota(String raza_mascota) {
        this.raza_mascota = raza_mascota;
    }

    public void setColor_mascota(String color_mascota) {
        this.color_mascota = color_mascota;
    }

    public void setEdad_mascota(String edad_mascota) {
        this.edad_mascota = edad_mascota;
    }

    public void setId_cliente_mascota(Integer id_cliente_mascota) {
        this.id_cliente_mascota = id_cliente_mascota;
    }

    public String getGenero_mascota() {
        return genero_mascota;
    }

    public void setGenero_mascota(String genero_mascota) {
        this.genero_mascota = genero_mascota;
    }
    
    
    
    public static void insertarMascota(Mascotas mascota)
    {
        
        //crear el objeto de la clase  Conexion_DB
        Connection con = modelo.Conexion_DB.getConnection();
        PreparedStatement ps; //kt permite ejecutar la sentencia sql no expone los datos
        
        //INSERT INTO `mascotas`(`nombre_mascota`, `especie_mascota`, `raza_mascota`, `color_mascota`,`edad_mascota`, `id_cliente_mascota`) VALUES ('pepita','CANINA','CRIOLLO','CAFÉ','7 AÑOS', 1)
        try {
            ps = con.prepareStatement("INSERT INTO `mascotas`(`nombre_mascota`, `especie_mascota`, `genero_mascota`, `raza_mascota`, `color_mascota`, `edad_mascota`, `id_cliente_mascota`) VALUES (?,?,?,?,?,?,?)");
                
            //kt método del ps para agregar lo que llega del get
            ps.setString(1, mascota.getNombre_mascota()); 
            ps.setString(2, mascota.getEspecie_mascota());
            ps.setString(3, mascota.getGenero_mascota());
            ps.setString(4, mascota.getRaza_mascota());
            ps.setString(5, mascota.getColor_mascota());
            ps.setString(6, mascota.getEdad_mascota());
            ps.setInt(7, mascota.getId_cliente_mascota());
           
           
            
            int resultado = ps.executeUpdate();
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Mascota agregada correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo registrar en BD");
            }
            
            } catch (SQLException ex) {
            Logger.getLogger(Mascotas.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error al insertar usuario: " + ex.getMessage());//test
            }
    }

   
    
 
   
    
    
    
    
    
    
}
