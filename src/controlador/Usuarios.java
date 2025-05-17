/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.Conexion_DB;


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
    public Usuarios(Integer ID,String PRINOM, String SEGNOM, String PRIAPE, String SEGAPE, 
            String TIPODOC, String NUMDOC, String EMAIL, String TEL, String DIR, 
            String USER, String PASSWD, String ROL){
    
        
        this.id= ID;
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

    
    
    //Getters
    
    
    public Integer getId() {
        return id;
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
        
        //INSERT INTO `usuarios`(`primer_nombre_usuario`, `segundo_nombre_usuario`, `primer_apellido_usuario`, `segundo_apellido_usuario`, `tipo_documento_usuario`, `numero_documento_usuario`, `email_usuario`, `telefono_usuario`, `direccion_usuario`, `usuario`, `passwd`, `rol_usuario`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)"
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
     
     // get users list
    public ArrayList<Usuarios> ListaUsuarios(){
        
        ArrayList<Usuarios> lista_usuario = new ArrayList<>();
        connection = modelo.Conexion_DB.getConnection();

        ResultSet rs;
        PreparedStatement ps;
        
                
                //`primer_nombre_usuario`, `segundo_nombre_usuario`, `primer_apellido_usuario`, `segundo_apellido_usuario`, `tipo_documento_usuario`, 
                //`numero_documento_usuario`, `email_usuario`, `telefono_usuario`, `direccion_usuario`, `usuario`, `passwd`, `rol_usuario`
                

                String query = "SELECT `id`, `primer_nombre_usuario`,`segundo_nombre_usuario`,`primer_apellido_usuario`,`segundo_apellido_usuario`,`tipo_documento_usuario`,`numero_documento_usuario`,`email_usuario`, `telefono_usuario`, `direccion_usuario`, `usuario`, `passwd`, `rol_usuario`FROM `usuarios`";
        
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
           //el órden del select y del while debe ser como el del constructor
            Usuarios usu;
            while(rs.next()){
                usu = new Usuarios(rs.getInt("id"),
                                 rs.getString("primer_nombre_usuario"),
                                 rs.getString("segundo_nombre_usuario"),
                                 rs.getString("primer_apellido_usuario"),
                                 rs.getString("segundo_apellido_usuario"),
                                 rs.getString("tipo_documento_usuario"),
                                 rs.getString("numero_documento_usuario"),
                                 rs.getString("email_usuario"),
                                 rs.getString("telefono_usuario"),
                                 rs.getString("direccion_usuario"),
                                 rs.getString("usuario"),
                                 rs.getString("passwd"),
                                 rs.getString("rol_usuario")
                                  );
                lista_usuario.add(usu);
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista_usuario;
        
    }
    
    
    //PRUEBA LISTAR CON STRING VAL
    /*
    public ArrayList<Usuarios> ListarUsuariosParam(String val){
        
        ArrayList<Usuarios> lista_usuarios = new ArrayList<>();
        connection =  Conexion_DB.getConnection();
        ResultSet rs;
        PreparedStatement ps;
        
               //String query = "SELECT product.id, product.name, category_id, quantity, price, picture,description,category.name as 'category' FROM product INNER JOIN category ON category.id = product.category_id";
        ////`primer_nombre_usuario`, `segundo_nombre_usuario`, `primer_nombre_usuario`, `segundo_apellido_usuario`, `tipo_documento_usuario`, 
                //`numero_documento_usuario`, `email_usuario`, `telefono_usuario`, `direccion_usuario`, `usuario`, `passwd`, `rol_usuario`
                // SELECT `primer_nombre_usuario`,`primer_nombre_usuario`,`numero_documento_usuario`,`email_usuario`,`usuario`,`rol_usuario` FROM usuarios 
                
               String query = "SELECT primer_nombre_usuario, primer_apellido_usuario, numero_documento_usuario, email_usuario, usuario, rol_usuario\n" +
"  FROM usuarios\n";
        
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, "%" + val + "%");
            rs = ps.executeQuery();
           
            Usuarios usu;
            // Integer ID, String NAME, Integer CATEGORY_ID, String PRICE, byte[] PICTURE, Integer QUANTITY, String DESCRIPTION
            while(rs.next()){
                usu = new Usuarios(rs.getInt("id"),
                                 rs.getString("primer_nombre_usuario"),
                                 rs.getString("segundo_nombre_usuario"),
                                 rs.getString("primer_apellido_usuario"),
                                 rs.getString("segundo_apellido_usuario"),
                                 rs.getString("tipo_documento_usuario"),
                                 rs.getString("numero_documento_usuario"),
                                 rs.getString("email_usuario"),
                                 rs.getString("telefono_usuario"),
                                 rs.getString("direccion_usuario"),
                                 rs.getString("usuario"),
                                 rs.getString("passwd"),
                                 rs.getString("rol_usuario")
                                  );
                
                lista_usuarios.add(usu);
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(Usuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista_usuarios;
        
    }*/
    
    
    
    
    
}
