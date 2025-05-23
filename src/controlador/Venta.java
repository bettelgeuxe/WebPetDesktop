/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Conexion_DB;

/**
 *
 * @author Cathecita
 */

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

    Connection cn;

    public Venta() {
        cn = Conexion_DB.getConnection();
    }

    // Getters y setters (puedes mantener los tuyos)...

    private void calcularValorTotal() {
        this.valorTotal = this.cantidadVenta * this.precioVenta;
    }

    // Método para validar stock del producto antes de insertar la venta
    private boolean validarStock(int idProducto, int cantidadSolicitada) {
        boolean disponible = false;
        try {
            String sql = "SELECT stock FROM productos WHERE id = ?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, idProducto);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int stockActual = rs.getInt("stock");
                if (stockActual >= cantidadSolicitada) {
                    disponible = true;
                } else {
                    JOptionPane.showMessageDialog(null, "No hay suficiente stock. Stock actual: " + stockActual);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Producto no encontrado para validar stock.");
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error validando stock: " + e.getMessage());
        }
        return disponible;
    }

    // Insertar venta en la BD con validación de stock
    public void insertarVenta(LocalDateTime fechaVenta, int cantidadVenta, double precioVenta, int idProducto,
                              int idUsuario, int idCliente, int idServicio) {
        if (!validarStock(idProducto, cantidadVenta)) {
            return; // No hay stock suficiente, no continuar
        }

        try {
            String sql = "INSERT INTO ventas (fecha_venta, cantidad_venta, precio_venta, fk_id_producto, fk_id_usuario, fk_id_cliente, fk_id_servicio) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = cn.prepareStatement(sql);

            // Convertir LocalDateTime a Timestamp para BD
            Timestamp ts = Timestamp.valueOf(fechaVenta);

            ps.setTimestamp(1, ts);
            ps.setInt(2, cantidadVenta);
            ps.setDouble(3, precioVenta);
            ps.setInt(4, idProducto);
            ps.setInt(5, idUsuario);
            ps.setInt(6, idCliente);
            ps.setInt(7, idServicio);

            int res = ps.executeUpdate();
            if (res > 0) {
                JOptionPane.showMessageDialog(null, "Venta registrada correctamente.");

                // Actualizar stock del producto (restar la cantidad vendida)
                actualizarStockProducto(idProducto, cantidadVenta);
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo registrar la venta.");
            }

            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar venta: " + e.getMessage());
        }
    }

    // Método para actualizar el stock del producto tras una venta
    private void actualizarStockProducto(int idProducto, int cantidadVendida) {
        try {
            String sql = "UPDATE productos SET stock = stock - ? WHERE id = ?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, cantidadVendida);
            ps.setInt(2, idProducto);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error actualizando stock: " + e.getMessage());
        }
    }

    // Consultar todas las ventas y devolverlas en DefaultTableModel para JTable
    public DefaultTableModel consultarVentas() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID Venta");
        modelo.addColumn("Fecha");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Precio");
        modelo.addColumn("Valor Total");
        modelo.addColumn("ID Producto");
        modelo.addColumn("ID Usuario");
        modelo.addColumn("ID Cliente");
        modelo.addColumn("ID Servicio");

        try {
            String sql = "SELECT * FROM ventas";
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Object[] fila = new Object[9];
                fila[0] = rs.getInt("id_venta");
                fila[1] = rs.getTimestamp("fecha_venta").toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                fila[2] = rs.getInt("cantidad_venta");
                fila[3] = rs.getDouble("precio_venta");
                fila[4] = rs.getDouble("valor_total");
                fila[5] = rs.getInt("fk_id_producto");
                fila[6] = rs.getInt("fk_id_usuario");
                fila[7] = rs.getInt("fk_id_cliente");
                fila[8] = rs.getInt("fk_id_servicio");

                modelo.addRow(fila);
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al consultar ventas: " + e.getMessage());
        }

        return modelo;
    }

    // Actualizar una venta (no actualizamos stock en esta operación, ojo)
    public void actualizarVenta(int idVenta, LocalDateTime fechaVenta, int cantidadVenta, double precioVenta,
                                int idProducto, int idUsuario, int idCliente, int idServicio) {
        try {
            String sql = "UPDATE ventas SET fecha_venta = ?, cantidad_venta = ?, precio_venta = ?, fk_id_producto = ?, fk_id_usuario = ?, fk_id_cliente = ?, fk_id_servicio = ? WHERE id_venta = ?";
            PreparedStatement ps = cn.prepareStatement(sql);

            Timestamp ts = Timestamp.valueOf(fechaVenta);

            ps.setTimestamp(1, ts);
            ps.setInt(2, cantidadVenta);
            ps.setDouble(3, precioVenta);
            ps.setInt(4, idProducto);
            ps.setInt(5, idUsuario);
            ps.setInt(6, idCliente);
            ps.setInt(7, idServicio);
            ps.setInt(8, idVenta);

            int res = ps.executeUpdate();
            if (res > 0) {
                JOptionPane.showMessageDialog(null, "Venta actualizada correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo actualizar la venta.");
            }

            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar venta: " + e.getMessage());
        }
    }

    // Eliminar una venta
    public void eliminarVenta(int idVenta) {
        try {
            String sql = "DELETE FROM ventas WHERE id_venta = ?";
            PreparedStatement ps = cn.prepareStatement(sql);
            ps.setInt(1, idVenta);

            int res = ps.executeUpdate();
            if (res > 0) {
                JOptionPane.showMessageDialog(null, "Venta eliminada correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar la venta.");
            }

            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar venta: " + e.getMessage());
        }
    }
}
