/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.Mascotas;
import java.awt.Color;
import java.math.BigInteger;
import java.security.MessageDigest;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import modelo.Conexion_DB;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;

/**
 *
 * @author Cathecita
 */
public class FrmProductosRegistrar extends javax.swing.JFrame {

    /**
     * Creates new form FrmUsuariosRegistrar
     */
    //creamos objeto frmregistro para llamarlo desde login
   private int idProveedor;
    
    DefaultTableModel modeloMascotas;
    
    public FrmProductosRegistrar() {
        initComponents();
        String[] columnas = {"Categoría", "Código", "Nombre", "Cantidad", "PVP"};
        DefaultTableModel model = new DefaultTableModel(null, columnas);
        jTable_PRODUCTOSadd.setModel(model);
        
        
        //crear color
        //Color verdeOscuro = new Color(52,78,65);
        jTable_PRODUCTOSadd.setShowGrid(true);
        
        //Personalizar FRONT tabla colores según identidad corporativa webpet
        jTable_PRODUCTOSadd.setGridColor(Color.decode("#A3B18A"));
        jTable_PRODUCTOSadd.setSelectionBackground(Color.decode("#3A5A40"));
        jTable_PRODUCTOSadd.setSelectionForeground(Color.decode("#FFFFFF"));
        JTableHeader th = jTable_PRODUCTOSadd.getTableHeader();
        th.setFont(new Font("Montserrat", Font.BOLD, 12));
    }
    
    
    public FrmProductosRegistrar(int idProveedor) {
    initComponents();
    String[] columnas = {"Categoría", "Código", "Nombre", "Cantidad", "PVP"};
        DefaultTableModel model = new DefaultTableModel(null, columnas);
        jTable_PRODUCTOSadd.setModel(model);
        //crear color
        //Color verdeOscuro = new Color(52,78,65);
        jTable_PRODUCTOSadd.setShowGrid(true);
        
        //Personalizar FRONT tabla colores según identidad corporativa webpet
        jTable_PRODUCTOSadd.setGridColor(Color.decode("#A3B18A"));
        jTable_PRODUCTOSadd.setSelectionBackground(Color.decode("#3A5A40"));
        jTable_PRODUCTOSadd.setSelectionForeground(Color.decode("#FFFFFF"));
        JTableHeader th = jTable_PRODUCTOSadd.getTableHeader();
        th.setFont(new Font("Montserrat", Font.BOLD, 12));
    this.idProveedor = idProveedor;

    // Puedes mostrarlo en pantalla si deseas verificar
    System.out.println("ID del proveedor recibido: " + idProveedor);

    cargarCategorias(); //  categorías al iniciar
}
    
    // Método para llenar el combo jComboBox_categoriaPRODUCTOS con las categorías desde la base de datos
public void cargarCategorias() {
    // Limpiamos el combo
    jComboBox_categoriaPRODUCTOS.removeAllItems();

    //  opción por defecto
    jComboBox_categoriaPRODUCTOS.addItem("Seleccione:");

    // Consulta SQL para obtener las categorías
    String sql = "SELECT categoria FROM categorias ORDER BY categoria ASC";

    try (Connection conn = Conexion_DB.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        // resultados agregar al combo
        while (rs.next()) {
            jComboBox_categoriaPRODUCTOS.addItem(rs.getString("categoria"));
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error al cargar categorías: " + e.getMessage());
    }
}

private void limpiarCamposProducto() {
    jComboBox_categoriaPRODUCTOS.setSelectedIndex(0); // Valor por defecto: "Seleccione"
    jTextField_codigoPRODUCTO.setText("");
    jTextField_nombrePRODUCTO.setText("");
    jSpinner_cantidadPRODUCTO.setValue(1);
    jTextField_preciocompraPRODUCTO.setText("");
    jTextField_precioventaPRODUCTO.setText("");
    jTextField_ivacompraPRODUCTO.setText("");
    jTextField_ivaventaPRODUCTO.setText("");
}

    private void agregarProductoATabla() {
    String categoriaSeleccionada = (String) jComboBox_categoriaPRODUCTOS.getSelectedItem();
    String codigo = jTextField_codigoPRODUCTO.getText().trim();
    String nombre = jTextField_nombrePRODUCTO.getText().trim();
    int cantidad = (int) jSpinner_cantidadPRODUCTO.getValue();
    String pvpStr = jTextField_precioventaPRODUCTO.getText().trim();

    // Validación
    if (categoriaSeleccionada.equals("Seleccione:") || codigo.isEmpty() || nombre.isEmpty() || pvpStr.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor completa todos los campos correctamente.");
        return;
    }

    BigDecimal precioVenta;
    try {
        precioVenta = new BigDecimal(pvpStr);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "El precio de venta debe ser un número válido.");
        return;
    }

    DefaultTableModel model = (DefaultTableModel) jTable_PRODUCTOSadd.getModel();
    model.addRow(new Object[]{categoriaSeleccionada, codigo, nombre, cantidad, precioVenta});
    limpiarCamposProducto();
}


    private int obtenerIdCategoria(String nombreCategoria, Connection conn) {
    String sql = "SELECT id_categoria FROM categorias WHERE categoria = ?";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, nombreCategoria);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt("id_categoria");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return -1; // No encontrada
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jTextField_codigoPRODUCTO = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField_nombrePRODUCTO = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField_preciocompraPRODUCTO = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jSpinner_cantidadPRODUCTO = new javax.swing.JSpinner();
        jLabel12 = new javax.swing.JLabel();
        jTextField_precioventaPRODUCTO = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTextField_ivacompraPRODUCTO = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jTextField_ivaventaPRODUCTO = new javax.swing.JTextField();
        jButton_agregarPRODUCTOtabla = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jComboBox_categoriaPRODUCTOS = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        Inicio = new javax.swing.JButton();
        jButton_SALIRmascotas = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_PRODUCTOSadd = new javax.swing.JTable();
        jButton_GuardarPRODUCTOS = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(52, 78, 65));
        jPanel1.setPreferredSize(new java.awt.Dimension(900, 600));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/images/webpetdef 50px.png"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(218, 215, 205));
        jLabel3.setText("Somos tu mejor aliado en la gestión veterinaria, WebPet");

        jPanel2.setBackground(new java.awt.Color(218, 215, 205));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 129, 87)));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Código");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Nombre");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("P.Compra");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setText("Cantidad");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setText("P.Venta");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setText("IVA compra");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setText("IVA Venta");

        jButton_agregarPRODUCTOtabla.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton_agregarPRODUCTOtabla.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/images/icono-derecha-webpet-app.png"))); // NOI18N
        jButton_agregarPRODUCTOtabla.setText("Enviar a lista");
        jButton_agregarPRODUCTOtabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton_agregarPRODUCTOtablaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton_agregarPRODUCTOtablaMouseExited(evt);
            }
        });
        jButton_agregarPRODUCTOtabla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_agregarPRODUCTOtablaActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Categoría");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton_agregarPRODUCTOtabla, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel11)
                    .addComponent(jLabel10)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jLabel1))
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField_codigoPRODUCTO, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(jTextField_nombrePRODUCTO, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(jTextField_preciocompraPRODUCTO, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(jSpinner_cantidadPRODUCTO, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_precioventaPRODUCTO, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(jTextField_ivacompraPRODUCTO, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(jTextField_ivaventaPRODUCTO, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                    .addComponent(jComboBox_categoriaPRODUCTOS, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox_categoriaPRODUCTOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField_codigoPRODUCTO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField_nombrePRODUCTO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jSpinner_cantidadPRODUCTO, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTextField_preciocompraPRODUCTO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_precioventaPRODUCTO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_ivacompraPRODUCTO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_ivaventaPRODUCTO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_agregarPRODUCTOtabla, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(218, 215, 205));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Hecho con ♥ por Catherine Escobar SENA ADSO CBA");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(332, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(274, 274, 274))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel4)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(218, 215, 205));
        jLabel18.setText("REGISTRO DE PRODUCTOS");

        Inicio.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        Inicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/images/icono-home-webpet-app.png"))); // NOI18N
        Inicio.setText("Inicio");

        jButton_SALIRmascotas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/images/icono-salir-webpet-app.png"))); // NOI18N
        jButton_SALIRmascotas.setText("Salir");
        jButton_SALIRmascotas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_SALIRmascotasActionPerformed(evt);
            }
        });

        jTable_PRODUCTOSadd.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable_PRODUCTOSadd);

        jButton_GuardarPRODUCTOS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/images/icono-agregar-webpet-app.png"))); // NOI18N
        jButton_GuardarPRODUCTOS.setText("Guardar Productos");
        jButton_GuardarPRODUCTOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_GuardarPRODUCTOSActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(149, 149, 149)
                                .addComponent(jLabel3))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(269, 269, 269)
                                .addComponent(jLabel18)))
                        .addContainerGap(925, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(Inicio, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton_SALIRmascotas))
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(jButton_GuardarPRODUCTOS)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(57, 57, 57))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(49, 49, 49)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Inicio, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_SALIRmascotas, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_GuardarPRODUCTOS, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(418, 418, 418))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(916, 639));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_GuardarPRODUCTOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_GuardarPRODUCTOSActionPerformed
        // TODO add your handling code here:
            DefaultTableModel model = (DefaultTableModel) jTable_PRODUCTOSadd.getModel();
    int filas = model.getRowCount();

    if (filas == 0) {
        JOptionPane.showMessageDialog(this, "No hay productos para guardar.");
        return;
    }

    try (Connection conn = Conexion_DB.getConnection()) {
        String sql = "INSERT INTO productos (codigo_producto, nombre_producto, cantidad_producto, precio_venta, fk_id_categoria, fk_id_proveedor) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);

        for (int i = 0; i < filas; i++) {
            String nombreCategoria = model.getValueAt(i, 0).toString();
            String codigo = model.getValueAt(i, 1).toString();
            String nombre = model.getValueAt(i, 2).toString();
            int cantidad = Integer.parseInt(model.getValueAt(i, 3).toString());
            BigDecimal precioVenta = new BigDecimal(model.getValueAt(i, 4).toString());

            // Obtener id_categoria desde nombre
            int idCategoria = obtenerIdCategoria(nombreCategoria, conn);
            if (idCategoria == -1) {
                JOptionPane.showMessageDialog(this, "Categoría no encontrada: " + nombreCategoria);
                return;
            }

            ps.setString(1, codigo);
            ps.setString(2, nombre);
            ps.setInt(3, cantidad);
            ps.setBigDecimal(4, precioVenta);
            ps.setInt(5, idCategoria);
            ps.setInt(6, idProveedor);

            ps.executeUpdate();
        }

        JOptionPane.showMessageDialog(this, "✅ Productos guardados correctamente.");
        model.setRowCount(0); // Limpiar la tabla después de guardar
        limpiarCamposProducto();

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al guardar productos: " + e.getMessage());
    }
                    

    }//GEN-LAST:event_jButton_GuardarPRODUCTOSActionPerformed

    private void jButton_SALIRmascotasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_SALIRmascotasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton_SALIRmascotasActionPerformed

    private void jButton_agregarPRODUCTOtablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_agregarPRODUCTOtablaActionPerformed
        // TODO add your handling code here:
         // Obtener categoría seleccionada
         agregarProductoATabla();
    

    }//GEN-LAST:event_jButton_agregarPRODUCTOtablaActionPerformed

    private void jButton_agregarPRODUCTOtablaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_agregarPRODUCTOtablaMouseExited
        // TODO add your handling code here:

    }//GEN-LAST:event_jButton_agregarPRODUCTOtablaMouseExited

    private void jButton_agregarPRODUCTOtablaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_agregarPRODUCTOtablaMouseEntered
        // TODO add your handling code here:

    }//GEN-LAST:event_jButton_agregarPRODUCTOtablaMouseEntered
   
    
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
            java.util.logging.Logger.getLogger(FrmProductosRegistrar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmProductosRegistrar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmProductosRegistrar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmProductosRegistrar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
                new FrmProductosRegistrar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Inicio;
    private javax.swing.JButton jButton_GuardarPRODUCTOS;
    private javax.swing.JButton jButton_SALIRmascotas;
    private javax.swing.JButton jButton_agregarPRODUCTOtabla;
    private javax.swing.JComboBox<String> jComboBox_categoriaPRODUCTOS;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinner_cantidadPRODUCTO;
    private javax.swing.JTable jTable_PRODUCTOSadd;
    private javax.swing.JTextField jTextField_codigoPRODUCTO;
    private javax.swing.JTextField jTextField_ivacompraPRODUCTO;
    private javax.swing.JTextField jTextField_ivaventaPRODUCTO;
    private javax.swing.JTextField jTextField_nombrePRODUCTO;
    private javax.swing.JTextField jTextField_preciocompraPRODUCTO;
    private javax.swing.JTextField jTextField_precioventaPRODUCTO;
    // End of variables declaration//GEN-END:variables
}
