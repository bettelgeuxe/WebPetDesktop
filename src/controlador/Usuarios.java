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

/**
 *
 * @author Cathecita
 */
public class Usuarios {
    
    Connection connection;
    private Integer id;
    private String pri_nom_usuario;
    private String seg_nom_usuario;
    private String pri_apell_usuario;
    private String seg_apell_usuario;
    private String tipo_doc_usuario;
    private String num_doc_usuario;
    private String email_usuario;
    private String tel_usuario;
    private String dir_usuario;
    private String usuario;
    private String passwd_usuario;
    private String rol_usuario;
    
    
    //constructor
    
    public Usuarios(){
    
    }
    
    //datos desde el formulario
    public Usuarios(String PRINOM, String SEGNOM, String PRIAPE, String SEGAPE, 
            String TIPODOC, String NUMDOC, String EMAIL, String TEL, String DIR, String USER, String PASSWD, String ROL){
    
        
        this.pri_nom_usuario = PRINOM;
        this.seg_nom_usuario = SEGNOM;
        this.pri_apell_usuario = PRIAPE;
        this.seg_apell_usuario = SEGAPE;
        this.tipo_doc_usuario = TIPODOC;
        this.num_doc_usuario = NUMDOC;
        this.email_usuario = EMAIL;
        this.tel_usuario = TEL;
        this.dir_usuario = DIR;
        this.usuario = USER;
        this.passwd_usuario = PASSWD;
        this.rol_usuario = ROL;
       
        
    }

    
    

    public String getPri_nom_usuario() {
        return pri_nom_usuario;
    }

    public String getSeg_nom_usuario() {
        return seg_nom_usuario;
    }

    public String getPri_apell_usuario() {
        return pri_apell_usuario;
    }

    public String getSeg_apell_usuario() {
        return seg_apell_usuario;
    }

    public String getTipo_doc_usuario() {
        return tipo_doc_usuario;
    }

    public String getNum_doc_usuario() {
        return num_doc_usuario;
    }

    public String getEmail_usuario() {
        return email_usuario;
    }

    public String getTel_usuario() {
        return tel_usuario;
    }

    public String getDir_usuario() {
        return dir_usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getPasswd_usuario() {
        return passwd_usuario;
    }

    public String getRol_usuario() {
        return rol_usuario;
    }
    
    // insert a new user
     public static void insertarUsuario(Usuarios usuario)
    {
        //crear el objeto de la clase  Conexion_DB
        Connection con = modelo.Conexion_DB.getConnection();
        PreparedStatement ps; //kt permite ejecutar la sentencia sql no expone los datos
        
        //INSERT INTO `users`(`full_name`, `username`, `password`, `phone`) VALUES ('pedro','pedro','1234','245789')
        try {
            ps = con.prepareStatement("INSERT INTO `usuarios`(`primer_nombre_usuario`, `segundo_nombre_usuario`, `primer_apellido_usuario`, `segundo_apellido_usuario`, `tipo_documento_usuario`, `numero_documento_usuario`, `email_usuario`, `telefono_usuario`, `direccion_usuario`, `usuario`, `passwd`, `rol_usuario`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");

            ps.setString(1, usuario.getPri_nom_usuario()); //kt método del ps para agregar lo que llega del get
            ps.setString(2, usuario.getSeg_nom_usuario());
            ps.setString(3, usuario.getPri_apell_usuario());
            ps.setString(4, usuario.getSeg_apell_usuario());
            ps.setString(5, usuario.getTipo_doc_usuario());
            ps.setString(6, usuario.getNum_doc_usuario());
            ps.setString(7, usuario.getEmail_usuario());
            ps.setString(8, usuario.getTel_usuario());
            ps.setString(9, usuario.getDir_usuario());
            ps.setString(10, usuario.getUsuario());
            ps.setString(11, usuario.getPasswd_usuario());
            ps.setString(12, usuario.getRol_usuario());
            
          
            

            if(ps.executeUpdate() != 0){
                JOptionPane.showMessageDialog(null, "Usuario agregado correctamente");
                
                }
                else{
                    JOptionPane.showMessageDialog(null, "No se pudo registrar en bd");
                    
                }
            
        } catch (SQLException ex) {
            Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error al insertar usuario: " + ex.getMessage());//test
        }
    }
    
    
    
    
    
}
