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



/**
 *
 * @author Cathecita
 */
public class FrmClientesAdministrar extends javax.swing.JFrame {

    controlador.Usuarios user;
    
    public FrmClientesAdministrar() {
        initComponents();
        
        //llenar la tabla para usar el query de búsqueda
        llenarJtableParam("");
        
        //crear color
        //Color verdeOscuro = new Color(52,78,65);
        jTable_CLIENTES.setShowGrid(true);
        
        //Personalizar FRONT tabla colores según identidad corporativa webpet
        jTable_CLIENTES.setGridColor(Color.decode("#A3B18A"));
        jTable_CLIENTES.setSelectionBackground(Color.decode("#3A5A40"));
        jTable_CLIENTES.setSelectionForeground(Color.decode("#FFFFFF"));
        JTableHeader th = jTable_CLIENTES.getTableHeader();
        th.setFont(new Font("Montserrat", Font.BOLD, 12));
        
        cargarClientesConMascotas();
        
    }
    
    
    public void cargarClientesConMascotas() {
    DefaultTableModel modelo = new DefaultTableModel();
    jTable_CLIENTES.setModel(modelo);

    modelo.addColumn("ID");        // Se agrega pero se ocultará
    modelo.addColumn("Nombre");
    modelo.addColumn("Apellido");
    modelo.addColumn("Documento");
    modelo.addColumn("Teléfono");
    modelo.addColumn("Mascotas");

    String sql = "SELECT c.id_cliente, c.primer_nombre_cliente, c.primer_apellido_cliente, c.numero_documento_cliente, c.telefono_cliente, " +
                 "GROUP_CONCAT(m.nombre_mascota SEPARATOR ', ') AS mascotas " +
                 "FROM clientes c " +
                 "LEFT JOIN mascotas m ON c.id_cliente = m.id_cliente_mascota " +
                 "GROUP BY c.id_cliente";

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        con = Conexion_DB.getConnection();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();

        while (rs.next()) {
            Object[] fila = new Object[6];
            fila[0] = rs.getInt("id_cliente"); // ID oculto
            fila[1] = rs.getString("primer_nombre_cliente");
            fila[2] = rs.getString("primer_apellido_cliente");
            fila[3] = rs.getString("numero_documento_cliente");
            fila[4] = rs.getString("telefono_cliente");
            fila[5] = rs.getString("mascotas") != null ? rs.getString("mascotas") : "Sin mascotas";

            modelo.addRow(fila);
        }

        // Ocultar la columna de ID (columna 0)
        jTable_CLIENTES.getColumnModel().getColumn(0).setMinWidth(0);
        jTable_CLIENTES.getColumnModel().getColumn(0).setMaxWidth(0);
        jTable_CLIENTES.getColumnModel().getColumn(0).setWidth(0);

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al cargar clientes: " + e.getMessage());
        e.printStackTrace();
    } finally {
        try { if (rs != null) rs.close(); } catch (SQLException ex) {}
        try { if (ps != null) ps.close(); } catch (SQLException ex) {}
        try { if (con != null) con.close(); } catch (SQLException ex) {}
    }
}


    
    
    
    public void llenarJtableParam(String val){
        
        controlador.Usuarios usu = new controlador.Usuarios();
        ArrayList<controlador.Usuarios> listaUsuario = usu.usuariosList(val);
        
        String[] colNames = {"ID","Nombre","Apellido","Documento","Email","Usuario","Rol"};
        Object[][] rows = new Object[listaUsuario.size()][7];
        
        for(int i = 0; i < listaUsuario.size(); i++){
            rows[i][0] = listaUsuario.get(i).getId();
            rows[i][1] = listaUsuario.get(i).getPri_nom_usuario();
            rows[i][2] = listaUsuario.get(i).getPri_apell_usuario();
            rows[i][3] = listaUsuario.get(i).getNum_doc_usuario();
            rows[i][4] = listaUsuario.get(i).getEmail_usuario();
            rows[i][5] = listaUsuario.get(i).getUsuario();
            rows[i][6] = listaUsuario.get(i).getRol_usuario();
            

        }
        
        modelo.MyTableModelUsuarios mmd = new modelo.MyTableModelUsuarios(rows, colNames);
        jTable_CLIENTES.setModel(mmd);
        jTable_CLIENTES.setRowHeight(30);
        jTable_CLIENTES.getColumnModel().getColumn(6).setPreferredWidth(120);
        
        //Ocultar la columna ID pero sigue disponible para luego llamar la info para actualizar
        
        jTable_CLIENTES.getColumnModel().getColumn(0).setMinWidth(0);
        jTable_CLIENTES.getColumnModel().getColumn(0).setMaxWidth(0);
        jTable_CLIENTES.getColumnModel().getColumn(0).setWidth(0);
    
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
        jButton_BUSCAR_CLIENTES = new javax.swing.JButton();
        jButton_ACTUALIZAR_CLIENTES = new javax.swing.JButton();
        jTextField_VALOR_BUSQUEDA_CLIENTES = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_CLIENTES = new javax.swing.JTable();
        jButton_registrarCLIENTE = new javax.swing.JButton();
        jButton_editarCLIENTESeleccionado = new javax.swing.JButton();
        jButton_volverInicio = new javax.swing.JButton();
        jButton_eliminarCLIENTESeleccionado = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(900, 600));
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

        jButton_BUSCAR_CLIENTES.setBackground(new java.awt.Color(218, 215, 205));
        jButton_BUSCAR_CLIENTES.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton_BUSCAR_CLIENTES.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/images/buscar-icono-webpet.png"))); // NOI18N
        jButton_BUSCAR_CLIENTES.setText("Buscar");
        jButton_BUSCAR_CLIENTES.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_BUSCAR_CLIENTESActionPerformed(evt);
            }
        });

        jButton_ACTUALIZAR_CLIENTES.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton_ACTUALIZAR_CLIENTES.setForeground(new java.awt.Color(52, 78, 65));
        jButton_ACTUALIZAR_CLIENTES.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/images/icono-actualizar-webpet-app.png"))); // NOI18N
        jButton_ACTUALIZAR_CLIENTES.setText("Actualizar");
        jButton_ACTUALIZAR_CLIENTES.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ACTUALIZAR_CLIENTESActionPerformed(evt);
            }
        });

        jTextField_VALOR_BUSQUEDA_CLIENTES.setBackground(new java.awt.Color(218, 215, 205));
        jTextField_VALOR_BUSQUEDA_CLIENTES.setForeground(new java.awt.Color(52, 78, 65));

        jTable_CLIENTES.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable_CLIENTES);

        jButton_registrarCLIENTE.setFont(new java.awt.Font("Montserrat", 0, 11)); // NOI18N
        jButton_registrarCLIENTE.setForeground(new java.awt.Color(52, 78, 65));
        jButton_registrarCLIENTE.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/images/icono-agregar-webpet-app.png"))); // NOI18N
        jButton_registrarCLIENTE.setText("Registrar Cliente");
        jButton_registrarCLIENTE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_registrarCLIENTEActionPerformed(evt);
            }
        });

        jButton_editarCLIENTESeleccionado.setFont(new java.awt.Font("Montserrat", 0, 11)); // NOI18N
        jButton_editarCLIENTESeleccionado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/images/icono-editar-webpet-app.png"))); // NOI18N
        jButton_editarCLIENTESeleccionado.setText("Editar Cliente Seleccionado");
        jButton_editarCLIENTESeleccionado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_editarCLIENTESeleccionadoActionPerformed(evt);
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

        jButton_eliminarCLIENTESeleccionado.setFont(new java.awt.Font("Montserrat", 0, 11)); // NOI18N
        jButton_eliminarCLIENTESeleccionado.setForeground(new java.awt.Color(52, 78, 65));
        jButton_eliminarCLIENTESeleccionado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/images/icono-eliminar-webpet-app.png"))); // NOI18N
        jButton_eliminarCLIENTESeleccionado.setText("Eliminar Cliente Seleccionado");
        jButton_eliminarCLIENTESeleccionado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_eliminarCLIENTESeleccionadoActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(218, 215, 205));
        jLabel3.setText("ADMINISTRAR CLIENTES Y MASCOTAS");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jButton_editarCLIENTESeleccionado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton_registrarCLIENTE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton_volverInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton_eliminarCLIENTESeleccionado))
                    .addComponent(jLabel1))
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextField_VALOR_BUSQUEDA_CLIENTES, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton_BUSCAR_CLIENTES, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton_ACTUALIZAR_CLIENTES, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(308, 308, 308))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jLabel1)
                        .addGap(61, 61, 61)
                        .addComponent(jButton_editarCLIENTESeleccionado, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton_eliminarCLIENTESeleccionado, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton_registrarCLIENTE, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton_volverInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addComponent(jButton_ACTUALIZAR_CLIENTES, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton_BUSCAR_CLIENTES, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField_VALOR_BUSQUEDA_CLIENTES, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
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

    private void jButton_BUSCAR_CLIENTESActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_BUSCAR_CLIENTESActionPerformed
        // TODO add your handling code here:
          String valorBusqueda = jTextField_VALOR_BUSQUEDA_CLIENTES.getText().trim();
        ArrayList<String[]> resultados = controlador.Clientes.buscarClientesConMascotas(valorBusqueda);

        DefaultTableModel modelo = new DefaultTableModel();
        jTable_CLIENTES.setModel(modelo);

        // agregar columna ID para ocultarla luego
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Documento");
        modelo.addColumn("Teléfono");
        modelo.addColumn("Mascotas");

        for (String[] fila : resultados) {
            modelo.addRow(fila);
        }

        if (resultados.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se encontraron resultados.");
        }

        // Ocultar columna del ID (columna 0)
        jTable_CLIENTES.getColumnModel().getColumn(0).setMinWidth(0);
        jTable_CLIENTES.getColumnModel().getColumn(0).setMaxWidth(0);
        jTable_CLIENTES.getColumnModel().getColumn(0).setWidth(0);
    }//GEN-LAST:event_jButton_BUSCAR_CLIENTESActionPerformed

    private void jButton_registrarCLIENTEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_registrarCLIENTEActionPerformed
        // TODO add your handling code here:
        FrmClientesRegistrar registrarClienteForm = new FrmClientesRegistrar();
            registrarClienteForm.pack();
            registrarClienteForm.setVisible(true);
            registrarClienteForm.setLocationRelativeTo(null);
            registrarClienteForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }//GEN-LAST:event_jButton_registrarCLIENTEActionPerformed

    private void jButton_editarCLIENTESeleccionadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_editarCLIENTESeleccionadoActionPerformed
        // TODO add your handling code here:
        int filaSeleccionada = jTable_CLIENTES.getSelectedRow();
            if (filaSeleccionada != -1) {
            int idCliente = Integer.parseInt(jTable_CLIENTES.getValueAt(filaSeleccionada, 0).toString());
            FrmClientesActualizar actualizar = new FrmClientesActualizar(idCliente);
            actualizar.setVisible(true);
            this.dispose(); // Opcional: cierra FrmClientesAdministrar si no quieres que queden ambos abiertos
            } else {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona un cliente para editar.");
}

    }//GEN-LAST:event_jButton_editarCLIENTESeleccionadoActionPerformed

    private void jButton_volverInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_volverInicioActionPerformed
        // TODO add your handling code here:
        vista.FrmInicio inicioForm = new vista.FrmInicio();
        inicioForm.setVisible(true);
        this.dispose(); 
        
    }//GEN-LAST:event_jButton_volverInicioActionPerformed

    private void jButton_eliminarCLIENTESeleccionadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_eliminarCLIENTESeleccionadoActionPerformed
        // TODO add your handling code here:
       int filaSeleccionada = jTable_CLIENTES.getSelectedRow();

    if (filaSeleccionada != -1) {
        try {
            // Obtener el ID de la fila seleccionada (columna 0 oculta)
            int idCliente = Integer.parseInt(jTable_CLIENTES.getValueAt(filaSeleccionada, 0).toString());

            int opcion = JOptionPane.showConfirmDialog(this, "¿Estás segura de que deseas eliminar este cliente y sus mascotas?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

            if (opcion == JOptionPane.YES_OPTION) {
                boolean eliminado = controlador.Clientes.eliminarClientePorId(idCliente);

                if (eliminado) {
                    JOptionPane.showMessageDialog(this, "Cliente eliminado correctamente.");
                    cargarClientesConMascotas(); // O vuelve a ejecutar la búsqueda actual si prefieres
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo eliminar el cliente.");
                }
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error al obtener el ID del cliente: " + e.getMessage());
        }

    } else {
        JOptionPane.showMessageDialog(this, "Por favor selecciona un cliente para eliminar.");
    }
    }//GEN-LAST:event_jButton_eliminarCLIENTESeleccionadoActionPerformed

    private void jButton_ACTUALIZAR_CLIENTESActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ACTUALIZAR_CLIENTESActionPerformed
        // TODO add your handling code here:
        cargarClientesConMascotas();
    }//GEN-LAST:event_jButton_ACTUALIZAR_CLIENTESActionPerformed

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
            java.util.logging.Logger.getLogger(FrmClientesAdministrar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmClientesAdministrar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmClientesAdministrar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmClientesAdministrar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmClientesAdministrar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_ACTUALIZAR_CLIENTES;
    private javax.swing.JButton jButton_BUSCAR_CLIENTES;
    private javax.swing.JButton jButton_editarCLIENTESeleccionado;
    private javax.swing.JButton jButton_eliminarCLIENTESeleccionado;
    private javax.swing.JButton jButton_registrarCLIENTE;
    private javax.swing.JButton jButton_volverInicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_CLIENTES;
    private javax.swing.JTextField jTextField_VALOR_BUSQUEDA_CLIENTES;
    // End of variables declaration//GEN-END:variables
}
