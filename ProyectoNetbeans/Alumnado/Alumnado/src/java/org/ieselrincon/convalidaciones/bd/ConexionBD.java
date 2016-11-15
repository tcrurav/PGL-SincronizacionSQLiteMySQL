/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ieselrincon.convalidaciones.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tiburcio
 */
public class ConexionBD {
    static final String USUARIO_BD = "convalidaciones";
    static final String CONTRA_BD = "contracontrita";
    static final String URL_MYSQL = "jdbc:mysql://localhost:3306/convalidaciones";
    
    public static Connection getConexion(){
        try {
            Connection conexion;
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection(URL_MYSQL, USUARIO_BD, CONTRA_BD);

            return conexion;
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
