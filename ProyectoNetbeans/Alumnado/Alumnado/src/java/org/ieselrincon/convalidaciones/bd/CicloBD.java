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
import org.ieselrincon.convalidaciones.pojos.Ciclo;

public class CicloBD {

    static public List<Ciclo> getAllCiclo() throws Exception {

        List<Ciclo> registros = new ArrayList<Ciclo>();
        
        Connection conexion = ConexionBD.getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = conexion.prepareStatement("SELECT * FROM ciclo ORDER BY nombre ASC, abreviatura ASC");
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Ciclo registro = new Ciclo();

                registro.setPK_ID(rs.getInt("PK_ID"));
                registro.setNombre(rs.getString("Nombre"));
                registro.setAbreviatura(rs.getString("Abreviatura"));

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

    static public Ciclo getCiclo(int id) throws SQLException {
       
        Connection conexion = ConexionBD.getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            ps = conexion.prepareStatement("SELECT * FROM Ciclo WHERE PK_ID = " + id);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Ciclo registro = new Ciclo();

                registro.setPK_ID(rs.getInt("PK_ID"));
                registro.setNombre(rs.getString("Nombre"));
                registro.setAbreviatura(rs.getString("Abreviatura"));

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

    static public void addCiclo(Ciclo registro) throws SQLException {

        Connection conexion = ConexionBD.getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String str;
            if (registro.getPK_ID() != -1) {
                str = "INSERT INTO Ciclo (PK_ID, Nombre, Abreviatura) VALUES ("
                        + registro.getPK_ID() + ",'" + registro.getNombre() + "','" + registro.getAbreviatura()+ "')";
            } else {
                str = "INSERT INTO Ciclo (Nombre, Abreviatura) VALUES ("
                        + "'" + registro.getNombre() + "','" + registro.getAbreviatura()+ "')";
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

    static public void delCiclo(int id) throws SQLException {
        Connection conexion = ConexionBD.getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conexion.prepareStatement("DELETE FROM Ciclo WHERE PK_ID = " + id + "");

            ps.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            if(rs != null) rs.close();
            if(ps != null) ps.close();
            if(conexion != null) conexion.close();
        }
    }

    static public void updateCiclo(Ciclo registro) throws SQLException {
        Connection conexion = ConexionBD.getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String str = "UPDATE Ciclo SET Nombre = '" + registro.getNombre() + "'"
                    + ", Abreviatura = '" + registro.getAbreviatura()+ "'"
                    + " WHERE PK_ID = " + registro.getPK_ID();
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
