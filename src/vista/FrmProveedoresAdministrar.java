/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.JTableHeader;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.Conexion_DB;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import vista.FrmClientesActualizar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;



/**
 *
 * @author Cathecita
 */
public class FrmProveedoresAdministrar extends javax.swing.JFrame {

    private List<Integer> listaIdProveedores = new ArrayList<>();

    
    public FrmProveedoresAdministrar() {
        initComponents();
        mostrarProveedoresConCategorias();
        
        
        
        //crear color
        //Color verdeOscuro = new Color(52,78,65);
        jTable_PROVEEDORES.setShowGrid(true);
        
        //Personalizar FRONT tabla colores según identidad corporativa webpet
        jTable_PROVEEDORES.setGridColor(Color.decode("#A3B18A"));
        jTable_PROVEEDORES.setSelectionBackground(Color.decode("#3A5A40"));
        jTable_PROVEEDORES.setSelectionForeground(Color.decode("#FFFFFF"));
        JTableHeader th = jTable_PROVEEDORES.getTableHeader();
        th.setFont(new Font("Montserrat", Font.BOLD, 12));
        
        
        
    }
    
    private void mostrarProveedoresConCategorias() {
    DefaultTableModel modelo = new DefaultTableModel();
    modelo.addColumn("Documento");
    modelo.addColumn("Nombre");
    modelo.addColumn("Email");
    modelo.addColumn("Teléfono");
    modelo.addColumn("Categorías");

    // Limpiar la lista antes de llenar
    listaIdProveedores.clear();

    String sql = "SELECT " +
                 "p.id_proveedor, " +
                 "p.documento_proveedor, " +
                 "p.nombre_proveedor, " +
                 "p.email_proveedor, " +
                 "p.telefono_proveedor, " +
                 "GROUP_CONCAT(c.categoria SEPARATOR ', ') AS categorias " +
                 "FROM proveedores p " +
                 "JOIN proveedor_categoria pc ON p.id_proveedor = pc.fk_id_proveedor " +
                 "JOIN categorias c ON pc.fk_id_categoria = c.id_categoria " +
                 "GROUP BY p.id_proveedor, p.documento_proveedor, p.nombre_proveedor, p.email_proveedor, p.telefono_proveedor";

    try (Connection conn = Conexion_DB.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            // Guardamos el ID del proveedor en la lista
            int idProveedor = rs.getInt("id_proveedor");
            listaIdProveedores.add(idProveedor); // se asocia con la fila actual

            String documento = rs.getString("documento_proveedor");
            String nombre = rs.getString("nombre_proveedor");
            String email = rs.getString("email_proveedor");
            String telefono = rs.getString("telefono_proveedor");
            String categorias = rs.getString("categorias");

            modelo.addRow(new Object[]{documento, nombre, email, telefono, categorias});
        }

        jTable_PROVEEDORES.setModel(modelo);

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "❌ Error al cargar proveedores: " + e.getMessage());
    }
}
    
    private void buscarProveedores(String valor) {
    DefaultTableModel modelo = new DefaultTableModel();
    modelo.addColumn("Documento");
    modelo.addColumn("Nombre");
    modelo.addColumn("Email");
    modelo.addColumn("Teléfono");
    modelo.addColumn("Categorías");

    listaIdProveedores.clear(); // limpia la lista de IDs

    String sql = "SELECT " +
                 "p.id_proveedor, " +
                 "p.documento_proveedor, " +
                 "p.nombre_proveedor, " +
                 "p.email_proveedor, " +
                 "p.telefono_proveedor, " +
                 "GROUP_CONCAT(c.categoria SEPARATOR ', ') AS categorias " +
                 "FROM proveedores p " +
                 "JOIN proveedor_categoria pc ON p.id_proveedor = pc.fk_id_proveedor " +
                 "JOIN categorias c ON pc.fk_id_categoria = c.id_categoria " +
                 "WHERE " +
                 "p.documento_proveedor LIKE ? OR " +
                 "p.nombre_proveedor LIKE ? OR " +
                 "p.email_proveedor LIKE ? OR " +
                 "p.telefono_proveedor LIKE ? OR " +
                 "p.direccion_proveedor LIKE ? OR " +
                 "p.ciudad_proveedor LIKE ? OR " +
                 "c.categoria LIKE ? " +
                 "GROUP BY p.id_proveedor, p.documento_proveedor, p.nombre_proveedor, p.email_proveedor, p.telefono_proveedor";

    try (Connection conn = Conexion_DB.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        // Asignar  valor a todos los parámetros LIKE para buscar por cualquier parte del texto 
        for (int i = 1; i <= 7; i++) {
            ps.setString(i, "%" + valor + "%");
        }

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int idProveedor = rs.getInt("id_proveedor");
            listaIdProveedores.add(idProveedor);

            String documento = rs.getString("documento_proveedor");
            String nombre = rs.getString("nombre_proveedor");
            String email = rs.getString("email_proveedor");
            String telefono = rs.getString("telefono_proveedor");
            String categorias = rs.getString("categorias");

            modelo.addRow(new Object[]{documento, nombre, email, telefono, categorias});
        }

        jTable_PROVEEDORES.setModel(modelo);

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "❌ Error al buscar proveedores: " + e.getMessage());
    }
}



    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jButton_BUSCAR_PROVEEDORES = new javax.swing.JButton();
        jButton_ACTUALIZAR_PROVEEDORES = new javax.swing.JButton();
        jTextField_VALOR_BUSQUEDA_PROVEEDORES = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_PROVEEDORES = new javax.swing.JTable();
        jButton_registrarPROVEEDORES = new javax.swing.JButton();
        jButton_editarPROVEEDORSeleccionado = new javax.swing.JButton();
        jButton_volverInicio = new javax.swing.JButton();
        jButton_eliminarPROVEEDORSeleccionado = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(52, 78, 65));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/images/logo webpet beige.png"))); // NOI18N

        jPanel2.setBackground(new java.awt.Color(218, 215, 205));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Hecho con ♥ por Catherine Escobar SENA ADSO CBA");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(277, 277, 277))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel2)
                .addContainerGap(54, Short.MAX_VALUE))
        );

        jButton_BUSCAR_PROVEEDORES.setBackground(new java.awt.Color(218, 215, 205));
        jButton_BUSCAR_PROVEEDORES.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton_BUSCAR_PROVEEDORES.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/images/buscar-icono-webpet.png"))); // NOI18N
        jButton_BUSCAR_PROVEEDORES.setText("Buscar");
        jButton_BUSCAR_PROVEEDORES.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_BUSCAR_PROVEEDORESActionPerformed(evt);
            }
        });

        jButton_ACTUALIZAR_PROVEEDORES.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton_ACTUALIZAR_PROVEEDORES.setForeground(new java.awt.Color(52, 78, 65));
        jButton_ACTUALIZAR_PROVEEDORES.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/images/icono-actualizar-webpet-app.png"))); // NOI18N
        jButton_ACTUALIZAR_PROVEEDORES.setText("Actualizar");
        jButton_ACTUALIZAR_PROVEEDORES.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ACTUALIZAR_PROVEEDORESActionPerformed(evt);
            }
        });

        jTextField_VALOR_BUSQUEDA_PROVEEDORES.setBackground(new java.awt.Color(218, 215, 205));
        jTextField_VALOR_BUSQUEDA_PROVEEDORES.setForeground(new java.awt.Color(52, 78, 65));
        jTextField_VALOR_BUSQUEDA_PROVEEDORES.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_VALOR_BUSQUEDA_PROVEEDORESActionPerformed(evt);
            }
        });

        jTable_PROVEEDORES.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable_PROVEEDORES);

        jButton_registrarPROVEEDORES.setFont(new java.awt.Font("Montserrat", 0, 11)); // NOI18N
        jButton_registrarPROVEEDORES.setForeground(new java.awt.Color(52, 78, 65));
        jButton_registrarPROVEEDORES.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/images/icono-agregar-webpet-app.png"))); // NOI18N
        jButton_registrarPROVEEDORES.setText("Registrar Proveedor y Productos");
        jButton_registrarPROVEEDORES.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_registrarPROVEEDORESActionPerformed(evt);
            }
        });

        jButton_editarPROVEEDORSeleccionado.setFont(new java.awt.Font("Montserrat", 0, 11)); // NOI18N
        jButton_editarPROVEEDORSeleccionado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/images/icono-editar-webpet-app.png"))); // NOI18N
        jButton_editarPROVEEDORSeleccionado.setText("Editar Proveedor  Seleccionado");
        jButton_editarPROVEEDORSeleccionado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_editarPROVEEDORSeleccionadoActionPerformed(evt);
            }
        });

        jButton_volverInicio.setFont(new java.awt.Font("Montserrat", 0, 11)); // NOI18N
        jButton_volverInicio.setForeground(new java.awt.Color(52, 78, 65));
        jButton_volverInicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/images/icono-home-webpet-app.png"))); // NOI18N
        jButton_volverInicio.setText("Volver a INICIO");
        jButton_volverInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_volverInicioActionPerformed(evt);
            }
        });

        jButton_eliminarPROVEEDORSeleccionado.setFont(new java.awt.Font("Montserrat", 0, 11)); // NOI18N
        jButton_eliminarPROVEEDORSeleccionado.setForeground(new java.awt.Color(52, 78, 65));
        jButton_eliminarPROVEEDORSeleccionado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/images/icono-eliminar-webpet-app.png"))); // NOI18N
        jButton_eliminarPROVEEDORSeleccionado.setText("Eliminar Proveedor Seleccionado");
        jButton_eliminarPROVEEDORSeleccionado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_eliminarPROVEEDORSeleccionadoActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(218, 215, 205));
        jLabel3.setText("ADMINISTRAR PROVEEDORES Y CATEGORÍAS");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(289, 289, 289)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButton_editarPROVEEDORSeleccionado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_registrarPROVEEDORES, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_volverInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_eliminarPROVEEDORSeleccionado))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextField_VALOR_BUSQUEDA_PROVEEDORES, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton_BUSCAR_PROVEEDORES, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton_ACTUALIZAR_PROVEEDORES, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jLabel1)
                        .addGap(66, 66, 66)
                        .addComponent(jButton_editarPROVEEDORSeleccionado, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton_eliminarPROVEEDORSeleccionado, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton_registrarPROVEEDORES, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton_volverInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton_ACTUALIZAR_PROVEEDORES, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton_BUSCAR_PROVEEDORES, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_VALOR_BUSQUEDA_PROVEEDORES, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(916, 639));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_BUSCAR_PROVEEDORESActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_BUSCAR_PROVEEDORESActionPerformed
        // TODO add your handling code here:
          String valorBusqueda = jTextField_VALOR_BUSQUEDA_PROVEEDORES.getText().trim();
    if (valorBusqueda.isEmpty()) {
        mostrarProveedoresConCategorias(); // ← Muestra todos si está vacío
    } else {
        buscarProveedores(valorBusqueda);
    }
    
    }//GEN-LAST:event_jButton_BUSCAR_PROVEEDORESActionPerformed

    private void jButton_registrarPROVEEDORESActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_registrarPROVEEDORESActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jButton_registrarPROVEEDORESActionPerformed

    private void jButton_editarPROVEEDORSeleccionadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_editarPROVEEDORSeleccionadoActionPerformed
        // TODO add your handling code here:
       int fila = jTable_PROVEEDORES.getSelectedRow();
    if (fila >= 0) {
        int idProveedor = listaIdProveedores.get(fila);
        FrmProveedoresActualizar actualizar = new FrmProveedoresActualizar(idProveedor); // pasas el id al constructor
        actualizar.setVisible(true);
        this.dispose(); // cerrar el formulario actual si quieres
    } else {
        JOptionPane.showMessageDialog(this, "Por favor selecciona un proveedor para editar.");
    }

    }//GEN-LAST:event_jButton_editarPROVEEDORSeleccionadoActionPerformed

    private void jButton_volverInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_volverInicioActionPerformed
        // TODO add your handling code here:
        vista.FrmInicio inicioForm = new vista.FrmInicio();
        inicioForm.setVisible(true);
        this.dispose(); 
        
    }//GEN-LAST:event_jButton_volverInicioActionPerformed

    private void jButton_eliminarPROVEEDORSeleccionadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_eliminarPROVEEDORSeleccionadoActionPerformed
        // TODO add your handling code here:
        int filaSeleccionada = jTable_PROVEEDORES.getSelectedRow();

    if (filaSeleccionada == -1) {
        JOptionPane.showMessageDialog(this, "❗ Por favor, selecciona un proveedor para eliminar.");
        return;
    }

    int confirmacion = JOptionPane.showConfirmDialog(this, "¿Estás segura de eliminar el proveedor seleccionado y todos sus productos?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
    if (confirmacion != JOptionPane.YES_OPTION) {
        return;
    }

    int idProveedor = listaIdProveedores.get(filaSeleccionada);

    try (Connection conn = Conexion_DB.getConnection()) {
        conn.setAutoCommit(false);

        // 1. Eliminar productos de ese proveedor
        String sqlProductos = "DELETE FROM productos WHERE fk_id_proveedor = ?";
        try (PreparedStatement psProductos = conn.prepareStatement(sqlProductos)) {
            psProductos.setInt(1, idProveedor);
            psProductos.executeUpdate();
        }

        // 2. Eliminar relaciones proveedor-categoría
        String sqlCategorias = "DELETE FROM proveedor_categoria WHERE fk_id_proveedor = ?";
        try (PreparedStatement psCategorias = conn.prepareStatement(sqlCategorias)) {
            psCategorias.setInt(1, idProveedor);
            psCategorias.executeUpdate();
        }

        // 3. Eliminar proveedor
        String sqlProveedor = "DELETE FROM proveedores WHERE id_proveedor = ?";
        try (PreparedStatement psProveedor = conn.prepareStatement(sqlProveedor)) {
            psProveedor.setInt(1, idProveedor);
            psProveedor.executeUpdate();
        }

        conn.commit();

        JOptionPane.showMessageDialog(this, "✅ Proveedor y sus productos eliminados correctamente.");
        mostrarProveedoresConCategorias();

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "❌ Error al eliminar proveedor: " + e.getMessage());
    }
       
    }//GEN-LAST:event_jButton_eliminarPROVEEDORSeleccionadoActionPerformed

    private void jButton_ACTUALIZAR_PROVEEDORESActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ACTUALIZAR_PROVEEDORESActionPerformed
        // TODO add your handling code here:
        jTextField_VALOR_BUSQUEDA_PROVEEDORES.setText(""); // Limpiar campo de búsqueda
        mostrarProveedoresConCategorias(); // Volver a cargar todos los proveedores
    }//GEN-LAST:event_jButton_ACTUALIZAR_PROVEEDORESActionPerformed

    private void jTextField_VALOR_BUSQUEDA_PROVEEDORESActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_VALOR_BUSQUEDA_PROVEEDORESActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jTextField_VALOR_BUSQUEDA_PROVEEDORESActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmProveedoresAdministrar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmProveedoresAdministrar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmProveedoresAdministrar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmProveedoresAdministrar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
     

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmProveedoresAdministrar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_ACTUALIZAR_PROVEEDORES;
    private javax.swing.JButton jButton_BUSCAR_PROVEEDORES;
    private javax.swing.JButton jButton_editarPROVEEDORSeleccionado;
    private javax.swing.JButton jButton_eliminarPROVEEDORSeleccionado;
    private javax.swing.JButton jButton_registrarPROVEEDORES;
    private javax.swing.JButton jButton_volverInicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_PROVEEDORES;
    private javax.swing.JTextField jTextField_VALOR_BUSQUEDA_PROVEEDORES;
    // End of variables declaration//GEN-END:variables
}
