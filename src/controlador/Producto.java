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
    
    
}
