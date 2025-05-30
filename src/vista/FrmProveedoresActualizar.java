/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;


import java.awt.Color;
import modelo.Conexion_DB;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import controlador.ItemProducto;
import controlador.ItemCategoria;
import java.math.BigDecimal;



/**
 *
 * @author Cathecita
 */
public class FrmProveedoresActualizar extends javax.swing.JFrame {

    /**
     * Creates new form FrmRegistro
     */
    //creamos objeto frmregistro para llamarlo desde login
    private int idProveedor;
    
    public static FrmLogin fl;
    private int idProveedorRecienInsertado = -1;
   
    
    
    public FrmProveedoresActualizar() {
        initComponents();
         cargarDatosProveedor();
        cargarProductosDelProveedor(idProveedor);
        cargarCategoriasEnCombo();
    
    }
    
    public FrmProveedoresActualizar(int idProveedor) {
    initComponents(); // cargarn los componentes delform
    this.idProveedor = idProveedor; // guardar el id del proveedor que llegó desde FrmProveedoresAdministrar
    
    cargarDatosProveedor();
    cargarProductosDelProveedor(idProveedor);
    cargarCategoriasEnCombo();
    cargarCategoriaVacia();
    
}
    
    private void cargarDatosProveedor() {
    String sql = "SELECT tipodocumento_proveedor, documento_proveedor, nombre_proveedor, email_proveedor, telefono_proveedor, direccion_proveedor, ciudad_proveedor FROM proveedores WHERE id_proveedor = ?";

    try (Connection con = Conexion_DB.getConnection();
         PreparedStatement pst = con.prepareStatement(sql)) {

        pst.setInt(1, idProveedor);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            jComboBox_tipodocPROVEEORactualizar.setSelectedItem(rs.getString("tipodocumento_proveedor"));
            jTextField_numdocPROVEEDORactualizar.setText(rs.getString("documento_proveedor"));
            jTextField_nombrePROVEEDORactualizar.setText(rs.getString("nombre_proveedor"));
            jTextField_emailPROVEEDORactualizar.setText(rs.getString("email_proveedor"));
            jTextField_telefonoPROVEEDORactualizar.setText(rs.getString("telefono_proveedor"));
            jTextField_direccionPROVEEDORactualizar.setText(rs.getString("direccion_proveedor"));
            jTextField_ciudadPROVEEDORactualizar.setText(rs.getString("ciudad_proveedor"));
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al cargar los datos del proveedor: " + e.getMessage());
    }
}

    
    private void cargarProductosDelProveedor(int idProveedor) {
    jComboBox_PRODUCTOproveed.removeAllItems(); // limpiar antes

    try {
        Connection conn = Conexion_DB.getConnection();
        String sql = "SELECT id, nombre_producto FROM productos WHERE fk_id_proveedor = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, idProveedor);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int idProducto = rs.getInt("id");
            String nombre = rs.getString("nombre_producto");

            jComboBox_PRODUCTOproveed.addItem(new ItemProducto(idProducto, nombre));
        }

        rs.close();
        stmt.close();
        conn.close();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error al cargar productos del proveedor: " + e.getMessage());
    }
}
    
    private void cargarDatosProducto(int idProducto) {
    String sql = "SELECT codigo_producto, nombre_producto, cantidad_producto, precio_compra, precio_venta, fk_id_categoria FROM productos WHERE id = ?";

    try (Connection con = Conexion_DB.getConnection();
         PreparedStatement pst = con.prepareStatement(sql)) {

        pst.setInt(1, idProducto);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            jTextField_codigoPRODUCTOactualizar.setText(rs.getString("codigo_producto"));
            jTextField_nombrePRODUCTOactualizar.setText(rs.getString("nombre_producto"));
            jSpinner_cantidadPRODUCTOactualizar.setValue(rs.getInt("cantidad_producto"));
            jTextField_preciocompraACTUALIZAR.setText(rs.getString("precio_compra"));
            jTextField_precioventaprodACTUALIZAR.setText(rs.getString("precio_venta"));

            int idCategoria = rs.getInt("fk_id_categoria");

            // ahora seleccionamos la categoría en el combo
            seleccionarCategoriaEnCombo(idCategoria);
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error al cargar datos del producto: " + e.getMessage());
    }
}
    
    private void seleccionarCategoriaEnCombo(int idCategoria) {
    for (int i = 0; i < jComboBox_categoriaPRODUCTOactualizar.getItemCount(); i++) {
        ItemCategoria item = (ItemCategoria) jComboBox_categoriaPRODUCTOactualizar.getItemAt(i);
        if (item.getId() == idCategoria) {
            jComboBox_categoriaPRODUCTOactualizar.setSelectedIndex(i);
            break;
        }
    }
}

  private void actualizarProducto() {
    ItemProducto productoSeleccionado = (ItemProducto) jComboBox_PRODUCTOproveed.getSelectedItem();
    if (productoSeleccionado == null) {
        JOptionPane.showMessageDialog(this, "Seleccione un producto.");
        return;
    }

    int idProducto = productoSeleccionado.getId();
    String codigo = jTextField_codigoPRODUCTOactualizar.getText();
    String nombre = jTextField_nombrePRODUCTOactualizar.getText();
    int cantidad = (int) jSpinner_cantidadPRODUCTOactualizar.getValue();
    String precioCompraStr = jTextField_preciocompraACTUALIZAR.getText();
    String precioVentaStr = jTextField_precioventaprodACTUALIZAR.getText();
    ItemCategoria categoriaSeleccionada = (ItemCategoria) jComboBox_categoriaPRODUCTOactualizar.getSelectedItem();

    if (codigo.isEmpty() || nombre.isEmpty() || precioCompraStr.isEmpty() || precioVentaStr.isEmpty() || categoriaSeleccionada == null) {
        JOptionPane.showMessageDialog(this, "Complete todos los campos.");
        return;
    }

    try {
        BigDecimal precioCompra = new BigDecimal(precioCompraStr);
        BigDecimal precioVenta = new BigDecimal(precioVentaStr);
        int idCategoria = categoriaSeleccionada.getId();

        String sql = "UPDATE productos SET codigo_producto = ?, nombre_producto = ?, cantidad_producto = ?, precio_compra = ?, precio_venta = ?, fk_id_categoria = ? WHERE id = ?";

        try (Connection con = Conexion_DB.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, codigo);
            pst.setString(2, nombre);
            pst.setInt(3, cantidad);
            pst.setBigDecimal(4, precioCompra);
            pst.setBigDecimal(5, precioVenta);
            pst.setInt(6, idCategoria);
            pst.setInt(7, idProducto);

            int filasActualizadas = pst.executeUpdate();

            if (filasActualizadas > 0) {
                JOptionPane.showMessageDialog(this, "Producto actualizado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar el producto.");
            }
        }

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Error en el formato de precios.");
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error al actualizar el producto: " + e.getMessage());
    }
}


private void cargarCategoriasEnCombo() {
    jComboBox_categoriaPRODUCTOactualizar.removeAllItems();

    String sql = "SELECT id_categoria, categoria FROM categorias";

    try (Connection con = Conexion_DB.getConnection();
         PreparedStatement pst = con.prepareStatement(sql);
         ResultSet rs = pst.executeQuery()) {

        while (rs.next()) {
            int id = rs.getInt("id_categoria");
            String nombre = rs.getString("categoria");
            jComboBox_categoriaPRODUCTOactualizar.addItem(new ItemCategoria(id, nombre));
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error al cargar categorías: " + e.getMessage());
    }
}

private void cargarCategoriaVacia() {
    jComboBox_categoriaPRODUCTOactualizar.removeAllItems(); // Limpiar combo primero

    // Agregamos un ítem vacío como primer elemento
    jComboBox_categoriaPRODUCTOactualizar.addItem(new ItemCategoria(-1, "Seleccione:"));

    try (Connection con = Conexion_DB.getConnection();
         PreparedStatement pst = con.prepareStatement("SELECT id_categoria, categoria FROM categorias");
         ResultSet rs = pst.executeQuery()) {

        while (rs.next()) {
            int id = rs.getInt("id_categoria");
            String nombre = rs.getString("categoria");
            jComboBox_categoriaPRODUCTOactualizar.addItem(new ItemCategoria(id, nombre));
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error al cargar categorías: " + e.getMessage());
    }
}

private void actualizarProveedor() {
    String tipoDocumento = (String) jComboBox_tipodocPROVEEORactualizar.getSelectedItem();
    String documento = jTextField_numdocPROVEEDORactualizar.getText();
    String nombre = jTextField_nombrePROVEEDORactualizar.getText();
    String email = jTextField_emailPROVEEDORactualizar.getText();
    String telefono = jTextField_telefonoPROVEEDORactualizar.getText();
    String direccion = jTextField_direccionPROVEEDORactualizar.getText();
    String ciudad = jTextField_ciudadPROVEEDORactualizar.getText();

    if (tipoDocumento == null || documento.isEmpty() || nombre.isEmpty() || email.isEmpty() || telefono.isEmpty() || direccion.isEmpty() || ciudad.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Complete todos los campos del proveedor.");
        return;
    }

    String sql = "UPDATE proveedores SET tipodocumento_proveedor = ?, documento_proveedor = ?, nombre_proveedor = ?, email_proveedor = ?, telefono_proveedor = ?, direccion_proveedor = ?, ciudad_proveedor = ? WHERE id_proveedor = ?";

    try (Connection con = Conexion_DB.getConnection();
         PreparedStatement pst = con.prepareStatement(sql)) {

        pst.setString(1, tipoDocumento);
        pst.setString(2, documento);
        pst.setString(3, nombre);
        pst.setString(4, email);
        pst.setString(5, telefono);
        pst.setString(6, direccion);
        pst.setString(7, ciudad);
        pst.setInt(8, idProveedor); // Usa el idProveedor actual

        int filas = pst.executeUpdate();

        if (filas > 0) {
            JOptionPane.showMessageDialog(this, "Proveedor actualizado correctamente.");
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar el proveedor.");
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error al actualizar proveedor: " + e.getMessage());
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
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jTextField_nombrePROVEEDORactualizar = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jComboBox_tipodocPROVEEORactualizar = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jTextField_direccionPROVEEDORactualizar = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField_numdocPROVEEDORactualizar = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField_emailPROVEEDORactualizar = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTextField_telefonoPROVEEDORactualizar = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jComboBox_PRODUCTOproveed = new javax.swing.JComboBox();
        jButton_seleccionaACTUALIZARproov = new javax.swing.JButton();
        jTextField_ciudadPROVEEDORactualizar = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jTextField_codigoPRODUCTOactualizar = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jTextField_nombrePRODUCTOactualizar = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jComboBox_categoriaPRODUCTOactualizar = new javax.swing.JComboBox();
        jLabel17 = new javax.swing.JLabel();
        jTextField_precioventaprodACTUALIZAR = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jTextField_preciocompraACTUALIZAR = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jSpinner_cantidadPRODUCTOactualizar = new javax.swing.JSpinner();
        jLabel20 = new javax.swing.JLabel();
        jButton_ACTUALIZARprovprod = new javax.swing.JButton();
        jButton_VOLVERPROVEEDORactualizar = new javax.swing.JButton();
        jButton_INICIOprovprod1 = new javax.swing.JButton();

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
                .addContainerGap(49, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(40, 40, 40))
        );

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/images/logo webpet beige.png"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(218, 215, 205));
        jLabel3.setText("Somos tu mejor aliado en la gestión veterinaria, WebPet");

        jPanel2.setBackground(new java.awt.Color(218, 215, 205));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 129, 87)));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Nombre");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Tipo documento");

        jComboBox_tipodocPROVEEORactualizar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "CC", "CE", "RUT", "NIT" }));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("Dirección");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("Número de documento");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("Email");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setText("Teléfono");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setText("Ciudad");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(52, 78, 65));
        jLabel18.setText("INFORMACIÓN PROVEEDOR");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(52, 78, 65));
        jLabel4.setText("SELECCIONE EL PRODUCTO");

        jButton_seleccionaACTUALIZARproov.setFont(new java.awt.Font("Montserrat", 1, 11)); // NOI18N
        jButton_seleccionaACTUALIZARproov.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/images/icono-seleccionar-webpet-app.png"))); // NOI18N
        jButton_seleccionaACTUALIZARproov.setText("Seleccionar");
        jButton_seleccionaACTUALIZARproov.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton_seleccionaACTUALIZARproovMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton_seleccionaACTUALIZARproovMouseExited(evt);
            }
        });
        jButton_seleccionaACTUALIZARproov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_seleccionaACTUALIZARproovActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(340, 340, 340)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField_emailPROVEEDORactualizar))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField_numdocPROVEEDORactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel11)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField_nombrePROVEEDORactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox_tipodocPROVEEORactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField_telefonoPROVEEDORactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField_ciudadPROVEEDORactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(75, 75, 75)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField_direccionPROVEEDORactualizar))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox_PRODUCTOproveed, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jButton_seleccionaACTUALIZARproov, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(154, 154, 154)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(163, Short.MAX_VALUE)
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
                    .addComponent(jLabel9)
                    .addComponent(jTextField_numdocPROVEEDORactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox_tipodocPROVEEORactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField_nombrePROVEEDORactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_emailPROVEEDORactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jTextField_telefonoPROVEEDORactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField_ciudadPROVEEDORactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13)))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(jTextField_direccionPROVEEDORactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(38, 38, 38)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_seleccionaACTUALIZARproov, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox_PRODUCTOproveed, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(75, 75, 75))
        );

        jPanel4.setBackground(new java.awt.Color(218, 215, 205));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 129, 87)));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel14.setText("Categoría");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setText("Nombre");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel16.setText("Código");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel17.setText("P. Venta");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel19.setText("Cantidad");

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(52, 78, 65));
        jLabel21.setText("INFORMACIÓN PRODUCTO");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel20.setText("P. Compra");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel15)
                            .addComponent(jLabel14)
                            .addComponent(jLabel19)
                            .addComponent(jLabel17)
                            .addComponent(jLabel20))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField_codigoPRODUCTOactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_nombrePRODUCTOactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox_categoriaPRODUCTOactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_preciocompraACTUALIZAR, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_precioventaprodACTUALIZAR, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSpinner_cantidadPRODUCTOactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jComboBox_categoriaPRODUCTOactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jTextField_codigoPRODUCTOactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jTextField_nombrePRODUCTOactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel19)
                    .addComponent(jSpinner_cantidadPRODUCTOactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_preciocompraACTUALIZAR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_precioventaprodACTUALIZAR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addContainerGap(65, Short.MAX_VALUE))
        );

        jButton_ACTUALIZARprovprod.setFont(new java.awt.Font("Montserrat", 0, 11)); // NOI18N
        jButton_ACTUALIZARprovprod.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/images/icono-actualizar-webpet-app.png"))); // NOI18N
        jButton_ACTUALIZARprovprod.setText("Actualizar Proveedor y Producto");
        jButton_ACTUALIZARprovprod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ACTUALIZARprovprodActionPerformed(evt);
            }
        });

        jButton_VOLVERPROVEEDORactualizar.setFont(new java.awt.Font("Montserrat", 0, 11)); // NOI18N
        jButton_VOLVERPROVEEDORactualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/images/icono-atras-webpet-app.png"))); // NOI18N
        jButton_VOLVERPROVEEDORactualizar.setText("Administrar  Proveedores");
        jButton_VOLVERPROVEEDORactualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_VOLVERPROVEEDORactualizarActionPerformed(evt);
            }
        });

        jButton_INICIOprovprod1.setFont(new java.awt.Font("Montserrat", 0, 11)); // NOI18N
        jButton_INICIOprovprod1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vista/images/icono-home-webpet-app.png"))); // NOI18N
        jButton_INICIOprovprod1.setText("Inicio");
        jButton_INICIOprovprod1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_INICIOprovprod1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton_VOLVERPROVEEDORactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton_INICIOprovprod1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(40, 40, 40)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_ACTUALIZARprovprod, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addGap(52, 52, 52)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_ACTUALIZARprovprod, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_INICIOprovprod1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_VOLVERPROVEEDORactualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
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

    private void jButton_seleccionaACTUALIZARproovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_seleccionaACTUALIZARproovActionPerformed
        // TODO add your handling code here:
        ItemProducto productoSeleccionado = (ItemProducto) jComboBox_PRODUCTOproveed.getSelectedItem();

    if (productoSeleccionado != null) {
        int idProducto = productoSeleccionado.getId();
        cargarDatosProducto(idProducto);
    } else {
        JOptionPane.showMessageDialog(this, "Por favor seleccione un producto.");
    }
                                                       
    
        
    }//GEN-LAST:event_jButton_seleccionaACTUALIZARproovActionPerformed

    private void jButton_seleccionaACTUALIZARproovMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_seleccionaACTUALIZARproovMouseEntered
        // TODO add your handling code here:
        //jButton_seleccionaACTUALIZARproov.setBackground(new Color(88,129,87));
        //jButton_seleccionaACTUALIZARproov.setForeground(new Color(0,0,0));
    }//GEN-LAST:event_jButton_seleccionaACTUALIZARproovMouseEntered

    private void jButton_seleccionaACTUALIZARproovMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton_seleccionaACTUALIZARproovMouseExited
        // TODO add your handling code here:
        //jButton_seleccionaACTUALIZARproov.setBackground(new Color(58,90,64));
        //jButton_seleccionaACTUALIZARproov.setForeground(new Color(218,215,205));
    }//GEN-LAST:event_jButton_seleccionaACTUALIZARproovMouseExited

    private void jButton_ACTUALIZARprovprodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ACTUALIZARprovprodActionPerformed
        // TODO add your handling code here:
        actualizarProducto();
        actualizarProveedor();
      
    }//GEN-LAST:event_jButton_ACTUALIZARprovprodActionPerformed

    private void jButton_VOLVERPROVEEDORactualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_VOLVERPROVEEDORactualizarActionPerformed
        // TODO add your handling code here:
        // Cerrar este formulario
        this.dispose();

        // Abrir el formulario de administración
        FrmProveedoresAdministrar frmAdministrar = new FrmProveedoresAdministrar();
        frmAdministrar.setVisible(true);
    }//GEN-LAST:event_jButton_VOLVERPROVEEDORactualizarActionPerformed

    private void jButton_INICIOprovprod1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_INICIOprovprod1ActionPerformed
        // TODO add your handling code here:
        vista.FrmInicio inicioForm = new vista.FrmInicio();
        inicioForm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton_INICIOprovprod1ActionPerformed

    
    // create a function to verify the empty fields  
    
    
    
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
            java.util.logging.Logger.getLogger(FrmProveedoresActualizar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmProveedoresActualizar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmProveedoresActualizar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmProveedoresActualizar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
       

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmProveedoresActualizar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_ACTUALIZARprovprod;
    private javax.swing.JButton jButton_INICIOprovprod1;
    private javax.swing.JButton jButton_VOLVERPROVEEDORactualizar;
    private javax.swing.JButton jButton_seleccionaACTUALIZARproov;
    private javax.swing.JComboBox jComboBox_PRODUCTOproveed;
    private javax.swing.JComboBox jComboBox_categoriaPRODUCTOactualizar;
    private javax.swing.JComboBox<String> jComboBox_tipodocPROVEEORactualizar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JSpinner jSpinner_cantidadPRODUCTOactualizar;
    private javax.swing.JTextField jTextField_ciudadPROVEEDORactualizar;
    private javax.swing.JTextField jTextField_codigoPRODUCTOactualizar;
    private javax.swing.JTextField jTextField_direccionPROVEEDORactualizar;
    private javax.swing.JTextField jTextField_emailPROVEEDORactualizar;
    private javax.swing.JTextField jTextField_nombrePRODUCTOactualizar;
    private javax.swing.JTextField jTextField_nombrePROVEEDORactualizar;
    private javax.swing.JTextField jTextField_numdocPROVEEDORactualizar;
    private javax.swing.JTextField jTextField_preciocompraACTUALIZAR;
    private javax.swing.JTextField jTextField_precioventaprodACTUALIZAR;
    private javax.swing.JTextField jTextField_telefonoPROVEEDORactualizar;
    // End of variables declaration//GEN-END:variables
}
