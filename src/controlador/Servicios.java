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
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Cathecita
 */
public class Servicios {

    Connection cn;

    public Servicios() {
        cn = Conexion_DB.getConnection();  
    }

    // agregar servicio
    public void insertarServicio(String nombre, double precio, String descripcion) {
        try {
            PreparedStatement ps = cn.prepareStatement(
                "INSERT INTO servicio (nombre, precio, descripcion) VALUES (?, ?, ?)"
            );
            ps.setString(1, nombre);
            ps.setDouble(2, precio);
            ps.setString(3, descripcion);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Servicio registrado correctamente.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar servicio: " + e.getMessage());
        }
    }

    // consultar servicios
    public DefaultTableModel consultarServicios() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Precio");
        modelo.addColumn("Descripción");

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM servicio");

            while (rs.next()) {
                Object[] fila = new Object[4];
                fila[0] = rs.getInt("id");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getDouble("precio");
                fila[3] = rs.getString("descripcion");
                modelo.addRow(fila);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al consultar servicios: " + e.getMessage());
        }

        return modelo;
    }

    //actualziar servicio
    public void actualizarServicio(int id, String nombre, double precio, String descripcion) {
        try {
            PreparedStatement ps = cn.prepareStatement(
                "UPDATE servicio SET nombre = ?, precio = ?, descripcion = ? WHERE id = ?"
            );
            ps.setString(1, nombre);
            ps.setDouble(2, precio);
            ps.setString(3, descripcion);
            ps.setInt(4, id);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Servicio actualizado correctamente.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar servicio: " + e.getMessage());
        }
    }

    // eliminar servicio
    public void eliminarServicio(int id) {
        try {
            PreparedStatement ps = cn.prepareStatement("DELETE FROM servicio WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Servicio eliminado correctamente.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar servicio: " + e.getMessage());
        }
    }

    // buscar servicio por nombre
    public DefaultTableModel buscarServicioPorNombre(String nombreBusqueda) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Precio");
        modelo.addColumn("Descripción");

        try {
            PreparedStatement ps = cn.prepareStatement(
                "SELECT * FROM servicio WHERE nombre LIKE ?"
            );
            ps.setString(1, "%" + nombreBusqueda + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] fila = new Object[4];
                fila[0] = rs.getInt("id");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getDouble("precio");
                fila[3] = rs.getString("descripcion");
                modelo.addRow(fila);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar servicio: " + e.getMessage());
        }

        return modelo;
    }
}