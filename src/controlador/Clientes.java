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
import modelo.Conexion_DB;




/**
 *
 * @author Cathecita
 */
public class Clientes {
    
    Connection connection;
    private Integer id;
    private String pri_nom_cliente;
    private String seg_nom_cliente;
    private String pri_apell_cliente;
    private String seg_apell_cliente;
    private String tipo_doc_cliente;
    private String num_doc_cliente;
    private String email_cliente;
    private String tel_cliente;
    private String dir_cliente;
    
    
    
    //constructor
    
    public Clientes(){
    
    }
    
    //datos desde el formulario
    public Clientes(Integer ID,String PRINOM, String SEGNOM, String PRIAPE, String SEGAPE, 
            String TIPODOC, String NUMDOC, String EMAIL, String TEL, String DIR)
    {
    
        this.id = ID;
        this.pri_nom_cliente = PRINOM;
        this.seg_nom_cliente = SEGNOM;
        this.pri_apell_cliente = PRIAPE;
        this.seg_apell_cliente = SEGAPE;
        this.tipo_doc_cliente = TIPODOC;
        this.num_doc_cliente = NUMDOC;
        this.email_cliente = EMAIL;
        this.tel_cliente = TEL;
        this.dir_cliente = DIR;
    }

   
    public Integer getId() {
        return id;
    }

    public String getPri_nom_cliente() {
        return pri_nom_cliente;
    }

    public String getSeg_nom_cliente() {
        return seg_nom_cliente;
    }

    public String getPri_apell_cliente() {
        return pri_apell_cliente;
    }

    public String getSeg_apell_cliente() {
        return seg_apell_cliente;
    }

    public String getTipo_doc_cliente() {
        return tipo_doc_cliente;
    }

    public String getNum_doc_cliente() {
        return num_doc_cliente;
    }

    public String getEmail_cliente() {
        return email_cliente;
    }

    public String getTel_cliente() {
        return tel_cliente;
    }

    public String getDir_cliente() {
        return dir_cliente;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPri_nom_cliente(String pri_nom_cliente) {
        this.pri_nom_cliente = pri_nom_cliente;
    }

    public void setSeg_nom_cliente(String seg_nom_cliente) {
        this.seg_nom_cliente = seg_nom_cliente;
    }

    public void setPri_apell_cliente(String pri_apell_cliente) {
        this.pri_apell_cliente = pri_apell_cliente;
    }

    public void setSeg_apell_cliente(String seg_apell_cliente) {
        this.seg_apell_cliente = seg_apell_cliente;
    }

    public void setTipo_doc_cliente(String tipo_doc_cliente) {
        this.tipo_doc_cliente = tipo_doc_cliente;
    }

    public void setNum_doc_cliente(String num_doc_cliente) {
        this.num_doc_cliente = num_doc_cliente;
    }

    public void setEmail_cliente(String email_cliente) {
        this.email_cliente = email_cliente;
    }

    public void setTel_cliente(String tel_cliente) {
        this.tel_cliente = tel_cliente;
    }

    public void setDir_cliente(String dir_cliente) {
        this.dir_cliente = dir_cliente;
    }
    
 
    public static int insertarClienteN(Clientes cliente) {
    int idGenerado = -1;
    Connection con = Conexion_DB.getConnection();
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        String sql = "INSERT INTO clientes (primer_nombre_cliente, segundo_nombre_cliente, primer_apellido_cliente, segundo_apellido_cliente, tipo_documento_cliente, numero_documento_cliente, email_cliente, telefono_cliente, direccion_cliente) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            
            ps.setString(1, cliente.getPri_nom_cliente()); 
            ps.setString(2, cliente.getSeg_nom_cliente());
            ps.setString(3, cliente.getPri_apell_cliente());
            ps.setString(4, cliente.getSeg_apell_cliente());
            ps.setString(5, cliente.getTipo_doc_cliente());
            ps.setString(6, cliente.getNum_doc_cliente());
            ps.setString(7, cliente.getEmail_cliente());
            ps.setString(8, cliente.getTel_cliente());
            ps.setString(9, cliente.getDir_cliente());

        ps.executeUpdate();

        rs = ps.getGeneratedKeys();
        if (rs.next()) {
            idGenerado = rs.getInt(1);  // Recupera el ID generado
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al insertar cliente: " + e.getMessage());
    } finally {
        try { if (rs != null) rs.close(); } catch (Exception e) {}
        try { if (ps != null) ps.close(); } catch (Exception e) {}
        try { if (con != null) con.close(); } catch (Exception e) {}
    }

    return idGenerado;
}

    
    
    // insert a new user
     public static void insertarCliente(Clientes cliente)
    {
        
        //crear el objeto de la clase  Conexion_DB
        Connection con = modelo.Conexion_DB.getConnection();
        PreparedStatement ps; //kt permite ejecutar la sentencia sql no expone los datos
        
        //INSERT INTO `users`(`full_name`, `username`, `password`, `phone`) VALUES ('pedro','pedro','1234','245789')
        try {
            ps = con.prepareStatement("INSERT INTO `clientes`(`primer_nombre_cliente`, `segundo_nombre_cliente`, `primer_apellido_cliente`, `segundo_apellido_cliente`, `tipo_documento_cliente`, `numero_documento_cliente`, `email_cliente`, `telefono_cliente`, `direccion_cliente`) VALUES (?,?,?,?,?,?,?,?,?)");
                
            //kt método del ps para agregar lo que llega del get
            ps.setString(1, cliente.getPri_nom_cliente()); 
            ps.setString(2, cliente.getSeg_nom_cliente());
            ps.setString(3, cliente.getPri_apell_cliente());
            ps.setString(4, cliente.getSeg_apell_cliente());
            ps.setString(5, cliente.getTipo_doc_cliente());
            ps.setString(6, cliente.getNum_doc_cliente());
            ps.setString(7, cliente.getEmail_cliente());
            ps.setString(8, cliente.getTel_cliente());
            ps.setString(9, cliente.getDir_cliente());
           
            
            if(ps.executeUpdate() != 0){
                JOptionPane.showMessageDialog(null, "Cliente agregado correctamente");
                
                }
                else{
                    JOptionPane.showMessageDialog(null, "No se pudo registrar en bd");
                    
                }
            
        } catch (SQLException ex) {
            Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error al insertar usuario: " + ex.getMessage());//test
        }
    }
    
    
    
    
    
}
