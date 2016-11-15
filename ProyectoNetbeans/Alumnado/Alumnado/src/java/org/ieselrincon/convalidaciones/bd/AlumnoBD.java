/**
 * @author Tiburcio
 *
 */
package org.ieselrincon.convalidaciones.bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.ieselrincon.convalidaciones.pojos.Alumno;

public class AlumnoBD {

    static public List<Alumno> getAllAlumno() throws Exception {

        List<Alumno> registros = new ArrayList<Alumno>();
        
        Connection conexion = ConexionBD.getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = conexion.prepareStatement("SELECT * FROM alumno ORDER BY apellidos ASC, nombre ASC");
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Alumno registro = new Alumno();

                registro.setPK_ID(rs.getInt("PK_ID"));
                registro.setNombre(rs.getString("Nombre"));
                registro.setApellidos(rs.getString("Apellidos"));

                registros.add(registro);
            }
            return registros;
        } catch (Exception e) {
            throw e;
        } finally {
            if(rs != null) rs.close();
            if(ps != null) ps.close();
            if(conexion != null) conexion.close();
        }
    }

    static public Alumno getAlumno(int id) throws SQLException {
       
        Connection conexion = ConexionBD.getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = conexion.prepareStatement("SELECT * FROM Alumno WHERE PK_ID = " + id);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Alumno registro = new Alumno();

                registro.setPK_ID(rs.getInt("PK_ID"));
                registro.setNombre(rs.getString("Nombre"));
                registro.setApellidos(rs.getString("Apellidos"));

                return registro;
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if(rs != null) rs.close();
            if(ps != null) ps.close();
            if(conexion != null) conexion.close();
        }
        return null;
    }

    static public void addAlumno(Alumno registro) throws SQLException {

        Connection conexion = ConexionBD.getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String str;
            if (registro.getPK_ID() != -1) {
                str = "INSERT INTO Alumno (PK_ID, Nombre, Apellidos) VALUES ("
                        + registro.getPK_ID() + ",'" + registro.getNombre() + "','" + registro.getApellidos() + "')";
            } else {
                str = "INSERT INTO Alumno (Nombre, Apellidos) VALUES ("
                        + "'" + registro.getNombre() + "','" + registro.getApellidos() + "')";
            }

            ps = conexion.prepareStatement(str);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            if(rs != null) rs.close();
            if(ps != null) ps.close();
            if(conexion != null) conexion.close();
        }
    }

    static public void delAlumno(int id) throws SQLException {
        Connection conexion = ConexionBD.getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conexion.prepareStatement("DELETE FROM Alumno WHERE PK_ID = " + id + "");

            ps.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            if(rs != null) rs.close();
            if(ps != null) ps.close();
            if(conexion != null) conexion.close();
        }
    }

    static public void updateAlumno(Alumno registro) throws SQLException {
        Connection conexion = ConexionBD.getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String str = "UPDATE Alumno SET Nombre = '" + registro.getNombre() + "'"
                    + ", Apellidos = '" + registro.getApellidos() + "'"
                    + " WHERE PK_ID = " + registro.getPK_ID();
            System.out.println(str);
            ps = conexion.prepareStatement(str);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            if(rs != null) rs.close();
            if(ps != null) ps.close();
            if(conexion != null) conexion.close();
        }
    }
}
