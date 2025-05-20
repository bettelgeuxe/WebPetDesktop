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
    private String raza_mascota;
    private String color_mascota;
    private String edad_mascota;
    private Integer id_cliente_mascota;
    
    
    
    
    //constructor
    
    public Mascotas(){
    
    }
    
    //datos desde el formulario
    public Mascotas(Integer ID, String NOMBRE, String ESPECIE, String RAZA, 
            String COLOR, String EDAD, Integer ID_CLI_MASCOTA)
    {
    
        
        this.id_mascota = ID;
        this.nombre_mascota = NOMBRE;
        this.especie_mascota = ESPECIE;
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

   
    
 
    // insert a new user
    
    
    
    
    
    
}
