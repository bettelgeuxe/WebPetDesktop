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
import java.sql.*;
import javax.swing.JOptionPane;
import modelo.Conexion_DB;
import controlador.Proveedor;

/**
 *
 * @author Cathecita
 */
 
    public class Proveedor {
        
        Connection connection;
        private Integer id_proveedor;
        private String tipodocumento_proveedor;
        private String documento_proveedor;
        private String nombre_proveedor;
        private String email_proveedor;
        private String telefono_proveedor;
        private String direccion_proveedor;
        private String ciudad_proveedor;

    public Proveedor() {}

    public Proveedor(Integer id, String tipoDoc, String doc, String nombre, String email, String tel, String dir, String ciudad) {
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
    
    public int insertarProveedor() {
    int idGenerado = -1;
    String sql = "INSERT INTO proveedores (tipodocumento_proveedor, documento_proveedor, nombre_proveedor, email_proveedor, telefono_proveedor, direccion_proveedor, ciudad_proveedor) VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (Connection conn = Conexion_DB.getConnection(); 
         PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

        ps.setString(1, tipodocumento_proveedor);
        ps.setString(2, documento_proveedor);
        ps.setString(3, nombre_proveedor);
        ps.setString(4, email_proveedor);
        ps.setString(5, telefono_proveedor);
        ps.setString(6, direccion_proveedor);
        ps.setString(7, ciudad_proveedor);

        int filasAfectadas = ps.executeUpdate();
        if (filasAfectadas > 0) {
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idGenerado = rs.getInt(1);
            }
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al insertar proveedor: " + e.getMessage());
    }

    return idGenerado;
}



    
    
}