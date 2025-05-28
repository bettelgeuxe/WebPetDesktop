/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.awt.Color;
import java.math.BigInteger;
import java.security.MessageDigest;
import javax.swing.JOptionPane;
import modelo.Conexion_DB;
import controlador.Clientes;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 *
 * @author Cathecita
 */
public class FrmClientesActualizar extends javax.swing.JFrame {

    /**
     * Creates new form FrmRegistro
     */
    //creamos objeto frmregistro para llamarlo desde login
    private int idCliente;
    public static FrmLogin fl;
    private int idClienteRecienInsertado = -1;
   
    
    
    public FrmClientesActualizar() {
        initComponents();
    }
    
    public FrmClientesActualizar(int idCliente) {
    initComponents(); // se cargan los componentes de la ventana
    this.idCliente = idCliente; // se guarda el id del cliente que llegó desde FrmClientesAdministrar
    cargarDatosCliente(); // se cargan los datos de ese cliente
    cargarMascotasCliente(); // se cargan las mascotas de ese cliente

    //PARA EJECUTAR ESTE BLOQUE DE CÓDIGO PERSONALIZADO Y NO EL QUE VIENE POR DEFECTO en el botón de insertar la mascota
    jButton_agregarMASCOTAcliact.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            agregarMascotaParaCliente();
        }
    });
}

    
    private void cargarDatosCliente() {
    Connection con = Conexion_DB.getConnection();
    PreparedStatement ps;
    ResultSet rs;

    try {
        ps = con.prepareStatement("SELECT * FROM clientes WHERE id_cliente = ?");
        ps.setInt(1, idCliente); 
        rs = ps.executeQuery();

        if (rs.next()) {
            jTextField_pnombreCLIENTEactualizar.setText(rs.getString("primer_nombre_cliente"));
            jTextField_snombreCLIENTEactualizar.setText(rs.getString("segundo_nombre_cliente"));
            jTextField_papellidoCLIENTEactualizar.setText(rs.getString("primer_apellido_cliente"));
            jTextField_sapellidoCLIENTEactualizar.setText(rs.getString("segundo_apellido_cliente"));
            jComboBox_tipodocCLIENTEactualizar.setSelectedItem(rs.getString("tipo_documento_cliente"));
            jTextField_numdocCLIENTEactualizar.setText(rs.getString("numero_documento_cliente"));
            jTextField_emailCLIENTEactualizar.setText(rs.getString("email_cliente"));
            jTextField_telCLIENTEactualizar.setText(rs.getString("telefono_cliente"));
            jTextField_dirCLIENTEactualizar.setText(rs.getString("direccion_cliente"));
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al cargar datos del cliente: " + e.getMessage());
    }
}
    private void cargarMascotasCliente() {
    Connection con = Conexion_DB.getConnection();
    PreparedStatement ps;
    ResultSet rs;

    try {
        ps = con.prepareStatement("SELECT id_mascota, nombre_mascota FROM mascotas WHERE id_cliente_mascota = ?");
        ps.setInt(1, idCliente); // usa tu variable ya existente
        rs = ps.executeQuery();

        jComboBox_NOMBREMASCOTAS.removeAllItems(); // limpiar combo

        boolean hayMascotas = false;
        while (rs.next()) {
            int idMascota = rs.getInt("id_mascota");
            String nombreMascota = rs.getString("nombre_mascota");
            jComboBox_NOMBREMASCOTAS.addItem(idMascota + " - " + nombreMascota);
            hayMascotas = true;
        }

        if (!hayMascotas) {
            jComboBox_NOMBREMASCOTAS.addItem("No hay mascotas registradas");
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al cargar mascotas: " + e.getMessage());
    }
}
    
   private void cargarDatosMascotaSeleccionada() {
    String itemSeleccionado = (String) jComboBox_NOMBREMASCOTAS.getSelectedItem();

    if (itemSeleccionado == null || itemSeleccionado.equals("No hay mascotas registradas")) {
        JOptionPane.showMessageDialog(this, "No hay mascotas registradas");
        return;
    }

    // Extraer solo el ID de la mascota desde el combo (formato "ID - NOMBRE")
    int idMascota = Integer.parseInt(itemSeleccionado.split(" - ")[0]);

    String sql = "SELECT * FROM mascotas WHERE id_mascota = ? AND id_cliente_mascota = ?";

    try (Connection con = Conexion_DB.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, idMascota);
        ps.setInt(2, idCliente); 

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            jTextField_nombreMASCOTAactualizar.setText(rs.getString("nombre_mascota"));
            jComboBox_especieMASCOTAactualizar.setSelectedItem(rs.getString("especie_mascota"));
            jComboBox_generoMASCOTAactualizar.setSelectedItem(rs.getString("genero_mascota"));
            jTextField_razaMASCOTAactualizar.setText(rs.getString("raza_mascota"));
            jTextField_colorMASCOTAactualizar.setText(rs.getString("color_mascota"));
            jTextField_edadMASCOTASactualizar.setText(rs.getString("edad_mascota"));
        } else {
            JOptionPane.showMessageDialog(this, "No se encontraron datos de la mascota.");
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al cargar datos de la mascota: " + e.getMessage());
        e.printStackTrace();
    }
}
 
   private void actualizarClienteYmascota() {
    // Validar si hay mascota seleccionada
    String itemSeleccionado = (String) jComboBox_NOMBREMASCOTAS.getSelectedItem();
    if (itemSeleccionado == null || itemSeleccionado.equals("No hay mascotas registradas")) {
        JOptionPane.showMessageDialog(this, "No hay mascotas registradas.");
        return;
    }

    // Extraer ID de mascota
    int idMascota = Integer.parseInt(itemSeleccionado.split(" - ")[0]);

    Connection con = Conexion_DB.getConnection();

    try {
        // Desactivar autocommit para manejo de transacción
        con.setAutoCommit(false);

        //  ACTUALIZAR CLIENTE 
        String sqlCliente = "UPDATE clientes SET primer_nombre_cliente=?, segundo_nombre_cliente=?, primer_apellido_cliente=?, segundo_apellido_cliente=?, tipo_documento_cliente=?, numero_documento_cliente=?, email_cliente=?, telefono_cliente=?, direccion_cliente=? WHERE id_cliente=?";

        try (PreparedStatement psCliente = con.prepareStatement(sqlCliente)) {
            psCliente.setString(1, jTextField_pnombreCLIENTEactualizar.getText());
            psCliente.setString(2, jTextField_snombreCLIENTEactualizar.getText());
            psCliente.setString(3, jTextField_papellidoCLIENTEactualizar.getText());
            psCliente.setString(4, jTextField_sapellidoCLIENTEactualizar.getText());
            psCliente.setString(5, jComboBox_tipodocCLIENTEactualizar.getSelectedItem().toString());
            psCliente.setString(6, jTextField_numdocCLIENTEactualizar.getText());
            psCliente.setString(7, jTextField_emailCLIENTEactualizar.getText());
            psCliente.setString(8, jTextField_telCLIENTEactualizar.getText());
            psCliente.setString(9, jTextField_dirCLIENTEactualizar.getText());
            psCliente.setInt(10, idCliente);

            psCliente.executeUpdate();
        }

        //  ACTUALIZAR MASCOTA 
        String sqlMascota = "UPDATE mascotas SET nombre_mascota=?, especie_mascota=?, genero_mascota=?, raza_mascota=?, color_mascota=?, edad_mascota=? WHERE id_mascota=? AND id_cliente_mascota=?";

        try (PreparedStatement psMascota = con.prepareStatement(sqlMascota)) {
            psMascota.setString(1, jTextField_nombreMASCOTAactualizar.getText());
            psMascota.setString(2, jComboBox_especieMASCOTAactualizar.getSelectedItem().toString());
            psMascota.setString(3, jComboBox_generoMASCOTAactualizar.getSelectedItem().toString());
            psMascota.setString(4, jTextField_razaMASCOTAactualizar.getText());
            psMascota.setString(5, jTextField_colorMASCOTAactualizar.getText());
            psMascota.setString(6, jTextField_edadMASCOTASactualizar.getText());
            psMascota.setInt(7, idMascota);
            psMascota.setInt(8, idCliente);

            psMascota.executeUpdate();
        }

        // Confirmar transacción
        con.commit();
        JOptionPane.showMessageDialog(this, "Cliente y mascota actualizados correctamente.");

    } catch (SQLException e) {
        try {
            con.rollback(); // revertir si hay error
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        JOptionPane.showMessageDialog(this, "Error al actualizar: " + e.getMessage());
    } finally {
        try {
            con.setAutoCommit(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
   
   private void agregarMascotaParaCliente() {
    String nombre = jTextField_nombreMASCOTAactualizar.getText().trim();
    String especie = (String) jComboBox_especieMASCOTAactualizar.getSelectedItem();
    String genero = (String) jComboBox_generoMASCOTAactualizar.getSelectedItem();
    String raza = jTextField_razaMASCOTAactualizar.getText().trim();
    String color = jTextField_colorMASCOTAactualizar.getText().trim();
    String edad = jTextField_edadMASCOTASactualizar.getText().trim();

    if (nombre.isEmpty() || especie == null || genero == null || raza.isEmpty() || color.isEmpty() || edad.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor completa todos los campos de la mascota.", "Datos incompletos", JOptionPane.WARNING_MESSAGE);
        return;
    }

    Connection con = Conexion_DB.getConnection();
    PreparedStatement ps;

    try {
        ps = con.prepareStatement("INSERT INTO mascotas (nombre_mascota, especie_mascota, genero_mascota, raza_mascota, color_mascota, edad_mascota, id_cliente_mascota) VALUES (?, ?, ?, ?, ?, ?, ?)");
        ps.setString(1, nombre);
        ps.setString(2, especie);
        ps.setString(3, genero);
        ps.setString(4, raza);
        ps.setString(5, color);
        ps.setString(6, edad);
        ps.setInt(7, idCliente); // id del cliente cargado actualmente

        int filasInsertadas = ps.executeUpdate();

        if (filasInsertadas > 0) {
            JOptionPane.showMessageDialog(this, "Mascota agregada exitosamente.");
            cargarMascotasCliente(); // actualizar el combo con la nueva mascota
            limpiarCamposMascota();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo agregar la mascota.");
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error al agregar la mascota: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

   
private void limpiarCamposMascota() {
    jTextField_nombreMASCOTAactualizar.setText("");
    jComboBox_especieMASCOTAactualizar.setSelectedIndex(0);
    jComboBox_generoMASCOTAactualizar.setSelectedIndex(0);
    jTextField_razaMASCOTAactualizar.setText("");
    jTextField_colorMASCOTAactualizar.setText("");
    jTextField_edadMASCOTASactualizar.setText("");
}



    

    /*
    private void cargarDatosCliente() {
    Connection con = Conexion_DB.getConnection();
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        String sql = "SELECT * FROM clientes WHERE id_cliente = ?";
        ps = con.prepareStatement(sql);
        ps.setInt(1, idCliente);
        rs = ps.executeQuery();

        if (rs.next()) {
            jTextField_pnombreCLIENTEactualizar.setText(rs.getString("primer_nombre_cliente"));
            jTextField_snombreCLIENTEactualizar.setText(rs.getString("segundo_nombre_cliente"));
            jTextField_papellidoCLIENTEactualizar.setText(rs.getString("primer_apellido_cliente"));
            jTextField_sapellidoCLIENTEactualizar.setText(rs.getString("segundo_apellido_cliente"));
            jComboBox_tipodocCLIENTEactualizar.setSelectedItem(rs.getString("tipo_documento_cliente"));
            jTextField_numdocCLIENTEactualizar.setText(rs.getString("numero_documento_cliente"));
            jTextField_emailCLIENTEactualizar.setText(rs.getString("email_cliente"));
            jTextField_telCLIENTEactualizar.setText(rs.getString("telefono_cliente"));
            jTextField_dirCLIENTEactualizar.setText(rs.getString("direccion_cliente"));
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error al cargar datos del cliente: " + e.getMessage());
    } finally {
        try { if (rs != null) rs.close(); } catch (Exception e) {}
        try { if (ps != null) ps.close(); } catch (Exception e) {}
        try { if (con != null) con.close(); } catch (Exception e) {}
    }
}
    
    private void cargarMascotasCliente() {
    Connection con = Conexion_DB.getConnection();
    PreparedStatement ps = null;
    ResultSet rs = null;

    jComboBox_NOMBREMASCOTAS.removeAllItems();

    try {
        String sql = "SELECT id_mascota, nombre_mascota FROM mascotas WHERE id_cliente_mascota = ?";
        ps = con.prepareStatement(sql);
        ps.setInt(1, idCliente);
        rs = ps.executeQuery();

        boolean hayMascotas = false;

        while (rs.next()) {
            int idMascota = rs.getInt("id_mascota");
            String nombreMascota = rs.getString("nombre_mascota");
            jComboBox_NOMBREMASCOTAS.addItem(idMascota + " - " + nombreMascota);
            hayMascotas = true;
        }

        if (!hayMascotas) {
            jComboBox_NOMBREMASCOTAS.addItem("No hay mascotas registradas");
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error al cargar mascotas: " + e.getMessage());
    } finally {
        try { if (rs != null) rs.close(); } catch (Exception e) {}
        try { if (ps != null) ps.close(); } catch (Exception e) {}
        try { if (con != null) con.close(); } catch (Exception e) {}
    }
}*/



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jTextField_pnombreCLIENTEactualizar = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField_papellidoCLIENTEactualizar = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jComboBox_tipodocCLIENTEactualizar = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jTextField_numdocCLIENTEactualizar = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField_snombreCLIENTEactualizar = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField_sapellidoCLIENTEactualizar = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTextField_emailCLIENTEactualizar = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextField_telCLIENTEactualizar = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTextField_dirCLIENTEactualizar = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jComboBox_NOMBREMASCOTAS = new javax.swing.JComboBox<>();
        jButton_seleccionarMASCOTActualizarcli = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jTextField_nombreMASCOTAactualizar = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jTextField_razaMASCOTAactualizar = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jComboBox_especieMASCOTAactualizar = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jTextField_edadMASCOTASactualizar = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jTextField_colorMASCOTAactualizar = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jComboBox_generoMASCOTAactualizar = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        jButton_agregarMASCOTAcliact = new javax.swing.JButton();
        jButton_ACTUALIZARclimasc = new javax.swing.JButton();
        jButton_VOLVERclientesactualizar = new javax.swing.JButton();
        jButton_INICIOclimasc = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(52, 78, 65));

        jPanel3.setBackground(new java.awt.Color(218, 215, 205));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Hecho con ♥ por Catherine Escobar SENA ADSO CBA");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(331, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(275, 275, 275))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(59, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(40, 40, 40))
        );

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/images/logo webpet beige.png"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(218, 215, 205));
        jLabel3.setText("Somos tu mejor aliado en la gestión veterinaria, WebPet");

        jPanel2.setBackground(new java.awt.Color(218, 215, 205));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 129, 87)));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Primer Nombre");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Primer Apellido");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Tipo documento");

        jComboBox_tipodocCLIENTEactualizar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "CC", "CE", "RUT", "NIT" }));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("Número documento");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("Segundo Nombre");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("Segundo Apellido");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setText("Email");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setText("Teléfono");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setText("Dirección");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(52, 78, 65));
        jLabel18.setText("INFORMACIÓN CLIENTE");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(52, 78, 65));
        jLabel4.setText("SELECCIONE LA MASCOTA");

        jButton_seleccionarMASCOTActualizarcli.setFont(new java.awt.Font("Montserrat", 1, 11)); // NOI18N
        jButton_seleccionarMASCOTActualizarcli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/images/icono-seleccionar-webpet-app.png"))); // NOI18N
        jButton_seleccionarMASCOTActualizarcli.setText("Seleccionar");
        jButton_seleccionarMASCOTActualizarcli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton_seleccionarMASCOTActualizarcliMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton_seleccionarMASCOTActualizarcliMouseExited(evt);
            }
        });
        jButton_seleccionarMASCOTActualizarcli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_seleccionarMASCOTActualizarcliActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTextField_pnombreCLIENTEactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel9))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTextField_papellidoCLIENTEactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                                        .addComponent(jLabel10))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox_tipodocCLIENTEactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel8))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel11))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField_emailCLIENTEactualizar)
                                    .addComponent(jTextField_dirCLIENTEactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel12)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextField_snombreCLIENTEactualizar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addComponent(jTextField_sapellidoCLIENTEactualizar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addComponent(jTextField_numdocCLIENTEactualizar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addComponent(jTextField_telCLIENTEactualizar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox_NOMBREMASCOTAS, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jButton_seleccionarMASCOTActualizarcli, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel18)
                .addGap(170, 170, 170))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18)
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField_pnombreCLIENTEactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jTextField_snombreCLIENTEactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField_papellidoCLIENTEactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_sapellidoCLIENTEactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jComboBox_tipodocCLIENTEactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jTextField_numdocCLIENTEactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jTextField_emailCLIENTEactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jTextField_telCLIENTEactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jTextField_dirCLIENTEactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_seleccionarMASCOTActualizarcli, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox_NOMBREMASCOTAS, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );

        jPanel4.setBackground(new java.awt.Color(218, 215, 205));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 129, 87)));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setText("Nombre");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setText("Raza");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel16.setText("Especie");

        jComboBox_especieMASCOTAactualizar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "CANINA", "FELINA", "EQUINA", "AVES", "BOVINOS", "PORCINA", " ", " ", " " }));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel17.setText("Edad");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel19.setText("Color");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel20.setText("Género");

        jComboBox_generoMASCOTAactualizar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "HEMBRA", "MACHO", " ", " ", " " }));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(52, 78, 65));
        jLabel21.setText("INFORMACIÓN MASCOTA");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel15)
                            .addComponent(jLabel14)
                            .addComponent(jLabel19)
                            .addComponent(jLabel17)
                            .addComponent(jLabel20))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox_generoMASCOTAactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_nombreMASCOTAactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_razaMASCOTAactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox_especieMASCOTAactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_colorMASCOTAactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_edadMASCOTASactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel21)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jTextField_nombreMASCOTAactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jComboBox_especieMASCOTAactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jComboBox_generoMASCOTAactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jTextField_razaMASCOTAactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jTextField_colorMASCOTAactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_edadMASCOTASactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton_agregarMASCOTAcliact.setFont(new java.awt.Font("Montserrat", 0, 11)); // NOI18N
        jButton_agregarMASCOTAcliact.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/images/icono-agregar-webpet-app.png"))); // NOI18N
        jButton_agregarMASCOTAcliact.setText("Registrar");

        jButton_ACTUALIZARclimasc.setFont(new java.awt.Font("Montserrat", 0, 11)); // NOI18N
        jButton_ACTUALIZARclimasc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/images/icono-actualizar-webpet-app.png"))); // NOI18N
        jButton_ACTUALIZARclimasc.setText("Actualizar");
        jButton_ACTUALIZARclimasc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ACTUALIZARclimascActionPerformed(evt);
            }
        });

        jButton_VOLVERclientesactualizar.setFont(new java.awt.Font("Montserrat", 0, 11)); // NOI18N
        jButton_VOLVERclientesactualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/images/icono-atras-webpet-app.png"))); // NOI18N
        jButton_VOLVERclientesactualizar.setText("Administrar  Clientes");
        jButton_VOLVERclientesactualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_VOLVERclientesactualizarActionPerformed(evt);
            }
        });

        jButton_INICIOclimasc.setFont(new java.awt.Font("Montserrat", 0, 11)); // NOI18N
        jButton_INICIOclimasc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/images/icono-home-webpet-app.png"))); // NOI18N
        jButton_INICIOclimasc.setText("Inicio");
        jButton_INICIOclimasc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_INICIOclimascActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton_INICIOclimasc)
                                .addGap(18, 18, 18)
                                .addComponent(jButton_VOLVERclientesactualizar)))
                        .addGap(40, 40, 40)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton_ACTUALIZARclimasc)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton_agregarMASCOTAcliact, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton_INICIOclimasc, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton_VOLVERclientesactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton_ACTUALIZARclimasc, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton_agregarMASCOTAcliact, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(76, 76, 76)))
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(916, 639));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_seleccionarMASCOTActualizarcliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_seleccionarMASCOTActualizarcliActionPerformed
        // TODO add your handling code here:
        cargarDatosMascotaSeleccionada();                                                 
    
        
    }//GEN-LAST:event_jButton_seleccionarMASCOTActualizarcliActionPerformed

    private void jButton_seleccionarMASCOTActualizarcliMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_seleccionarMASCOTActualizarcliMouseEntered
        // TODO add your handling code here:
      
    }//GEN-LAST:event_jButton_seleccionarMASCOTActualizarcliMouseEntered

    private void jButton_seleccionarMASCOTActualizarcliMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_seleccionarMASCOTActualizarcliMouseExited
        // TODO add your handling code here:
       
    }//GEN-LAST:event_jButton_seleccionarMASCOTActualizarcliMouseExited

    private void jButton_ACTUALIZARclimascActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ACTUALIZARclimascActionPerformed
        // TODO add your handling code here:
        actualizarClienteYmascota();
    }//GEN-LAST:event_jButton_ACTUALIZARclimascActionPerformed

    private void jButton_VOLVERclientesactualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_VOLVERclientesactualizarActionPerformed
        // TODO add your handling code here:
        // Cerrar este formulario
        this.dispose();

        // Abrir el formulario de administración
        FrmClientesAdministrar frmAdministrar = new FrmClientesAdministrar();
        frmAdministrar.setVisible(true);
    }//GEN-LAST:event_jButton_VOLVERclientesactualizarActionPerformed

    private void jButton_INICIOclimascActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_INICIOclimascActionPerformed
        // TODO add your handling code here:
        vista.FrmInicio inicioForm = new vista.FrmInicio();
        inicioForm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton_INICIOclimascActionPerformed

    
    // create a function to verify the empty fields  
    public boolean verificarCampos()
    {
        String pnombre = jTextField_pnombreCLIENTEactualizar.getText();
        String snombre = jTextField_snombreCLIENTEactualizar.getText();
        String papellido = jTextField_papellidoCLIENTEactualizar.getText();
        String sapellido = jTextField_sapellidoCLIENTEactualizar.getText();
        String tipodoc= jComboBox_tipodocCLIENTEactualizar.getSelectedItem().toString();
        String numdoc = jTextField_numdocCLIENTEactualizar.getText();
        String email = jTextField_emailCLIENTEactualizar.getText();
        String tel = jTextField_telCLIENTEactualizar.getText();
        String dir = jTextField_dirCLIENTEactualizar.getText();
                
        // check empty fields
        if(pnombre.trim().equals("") || snombre.trim().equals("") || papellido.trim().equals("")
           || sapellido.trim().equals("") || tipodoc.trim().equals("Seleccione") || numdoc.trim().equals("")
           || email.trim().equals("") || tel.trim().equals("") || dir.trim().equals("") )
        {
            JOptionPane.showMessageDialog(null, "Uno o más campos están vacíos o sin selección","Campos vacíos",2);
            return false;
        }
        
               
        // if everything is ok
        else{
            return true;
        }
    }
    
    
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
            java.util.logging.Logger.getLogger(FrmClientesActualizar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmClientesActualizar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmClientesActualizar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmClientesActualizar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new FrmClientesActualizar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_ACTUALIZARclimasc;
    private javax.swing.JButton jButton_INICIOclimasc;
    private javax.swing.JButton jButton_VOLVERclientesactualizar;
    private javax.swing.JButton jButton_agregarMASCOTAcliact;
    private javax.swing.JButton jButton_seleccionarMASCOTActualizarcli;
    private javax.swing.JComboBox<String> jComboBox_NOMBREMASCOTAS;
    private javax.swing.JComboBox<String> jComboBox_especieMASCOTAactualizar;
    private javax.swing.JComboBox<String> jComboBox_generoMASCOTAactualizar;
    private javax.swing.JComboBox<String> jComboBox_tipodocCLIENTEactualizar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTextField jTextField_colorMASCOTAactualizar;
    private javax.swing.JTextField jTextField_dirCLIENTEactualizar;
    private javax.swing.JTextField jTextField_edadMASCOTASactualizar;
    private javax.swing.JTextField jTextField_emailCLIENTEactualizar;
    private javax.swing.JTextField jTextField_nombreMASCOTAactualizar;
    private javax.swing.JTextField jTextField_numdocCLIENTEactualizar;
    private javax.swing.JTextField jTextField_papellidoCLIENTEactualizar;
    private javax.swing.JTextField jTextField_pnombreCLIENTEactualizar;
    private javax.swing.JTextField jTextField_razaMASCOTAactualizar;
    private javax.swing.JTextField jTextField_sapellidoCLIENTEactualizar;
    private javax.swing.JTextField jTextField_snombreCLIENTEactualizar;
    private javax.swing.JTextField jTextField_telCLIENTEactualizar;
    // End of variables declaration//GEN-END:variables
}
