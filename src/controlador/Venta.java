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
import java.math.BigDecimal;

/**
 *
 * @author Cathecita
 */


import java.time.LocalDateTime;

public class Venta {
    private int idVenta;
    private LocalDateTime fechaVenta;
    private int cantidadVenta;
    private double precioVenta;
    private double valorTotal;

    private int idProducto;
    private int idUsuario;
    private int idCliente;
    private int idServicio;

    
    public Venta() {
    }

   
    public Venta(int idVenta, LocalDateTime fechaVenta, int cantidadVenta, double precioVenta,
                 int idProducto, int idUsuario, int idCliente, int idServicio) {
        this.idVenta = idVenta;
        this.fechaVenta = fechaVenta;
        this.cantidadVenta = cantidadVenta;
        this.precioVenta = precioVenta;
        this.valorTotal = cantidadVenta * precioVenta; // cálculo automático
        this.idProducto = idProducto;
        this.idUsuario = idUsuario;
        this.idCliente = idCliente;
        this.idServicio = idServicio;
    }

  
    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public LocalDateTime getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDateTime fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public int getCantidadVenta() {
        return cantidadVenta;
    }

    public void setCantidadVenta(int cantidadVenta) {
        this.cantidadVenta = cantidadVenta;
        calcularValorTotal();
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
        calcularValorTotal();
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    // Método para actualizar valor total
    private void calcularValorTotal() {
        this.valorTotal = this.cantidadVenta * this.precioVenta;
    }
}

