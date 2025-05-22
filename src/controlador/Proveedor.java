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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import modelo.Conexion_DB;

/**
 *
 * @author Cathecita
 */
 
    public class Proveedor {
        
        Connection connection;
        private int id_proveedor;
        private String tipodocumento_proveedor;
        private String documento_proveedor;
        private String nombre_proveedor;
        private String email_proveedor;
        private String telefono_proveedor;
        private String direccion_proveedor;
        private String ciudad_proveedor;

    public Proveedor() {}

    public Proveedor(int id, String tipoDoc, String doc, String nombre, String email, String tel, String dir, String ciudad) {
        this.id_proveedor = id;
        this.tipodocumento_proveedor = tipoDoc;
        this.documento_proveedor = doc;
        this.nombre_proveedor = nombre;
        this.email_proveedor = email;
        this.telefono_proveedor = tel;
        this.direccion_proveedor = dir;
        this.ciudad_proveedor = ciudad;
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    public String getTipodocumento_proveedor() {
        return tipodocumento_proveedor;
    }

    public void setTipodocumento_proveedor(String tipodocumento_proveedor) {
        this.tipodocumento_proveedor = tipodocumento_proveedor;
    }

    public String getDocumento_proveedor() {
        return documento_proveedor;
    }

    public void setDocumento_proveedor(String documento_proveedor) {
        this.documento_proveedor = documento_proveedor;
    }

    public String getNombre_proveedor() {
        return nombre_proveedor;
    }

    public void setNombre_proveedor(String nombre_proveedor) {
        this.nombre_proveedor = nombre_proveedor;
    }

    public String getEmail_proveedor() {
        return email_proveedor;
    }

    public void setEmail_proveedor(String email_proveedor) {
        this.email_proveedor = email_proveedor;
    }

    public String getTelefono_proveedor() {
        return telefono_proveedor;
    }

    public void setTelefono_proveedor(String telefono_proveedor) {
        this.telefono_proveedor = telefono_proveedor;
    }

    public String getDireccion_proveedor() {
        return direccion_proveedor;
    }

    public void setDireccion_proveedor(String direccion_proveedor) {
        this.direccion_proveedor = direccion_proveedor;
    }

    public String getCiudad_proveedor() {
        return ciudad_proveedor;
    }

    public void setCiudad_proveedor(String ciudad_proveedor) {
        this.ciudad_proveedor = ciudad_proveedor;
    }
    
    
    
}
