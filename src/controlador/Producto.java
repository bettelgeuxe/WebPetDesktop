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
public class Producto {
    
    Connection connection;
    
    private int id;
    private String codigo_producto;
    private String nombre_producto;
    private int cantidad_producto;
    private BigDecimal precio_compra;
    private BigDecimal precio_venta;
    private BigDecimal iva_compra;
    private BigDecimal iva_venta;
    private int id_categoria;
    private int id_proveedor;

    public Producto() {}
    
    public Producto(int id, String codigo_producto, String nombre_producto, int cantidad_producto,
                BigDecimal precio_compra, BigDecimal precio_venta,
                BigDecimal iva_compra, BigDecimal iva_venta,
                int id_categoria, int id_proveedor) {
    this.id = id;
    this.codigo_producto = codigo_producto;
    this.nombre_producto = nombre_producto;
    this.cantidad_producto = cantidad_producto;
    this.precio_compra = precio_compra;
    this.precio_venta = precio_venta;
    this.iva_compra = iva_compra;
    this.iva_venta = iva_venta;
    this.id_categoria = id_categoria;
    this.id_proveedor = id_proveedor;
}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo_producto() {
        return codigo_producto;
    }

    public void setCodigo_producto(String codigo_producto) {
        this.codigo_producto = codigo_producto;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public int getCantidad_producto() {
        return cantidad_producto;
    }

    public void setCantidad_producto(int cantidad_producto) {
        this.cantidad_producto = cantidad_producto;
    }

    public BigDecimal getPrecio_compra() {
        return precio_compra;
    }

    public void setPrecio_compra(BigDecimal precio_compra) {
        this.precio_compra = precio_compra;
    }

    public BigDecimal getPrecio_venta() {
        return precio_venta;
    }

    public void setPrecio_venta(BigDecimal precio_venta) {
        this.precio_venta = precio_venta;
    }

    public BigDecimal getIva_compra() {
        return iva_compra;
    }

    public void setIva_compra(BigDecimal iva_compra) {
        this.iva_compra = iva_compra;
    }

    public BigDecimal getIva_venta() {
        return iva_venta;
    }

    public void setIva_venta(BigDecimal iva_venta) {
        this.iva_venta = iva_venta;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }
    
    
    
    
}
