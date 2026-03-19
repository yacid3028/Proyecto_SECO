package seco.fcdb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import seco.conexionbd;

public class reportesDB {

    
    public static void reportes(DefaultTableModel model) {

        try {
            Connection con = conexionbd.conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Reportes");

            while (rs.next()) {
                model.addRow(new Object[] {
                        rs.getString("id_Reportes"),
                        rs.getString("Categoria"),
                        rs.getString("Descripcion"),
                        rs.getString("Usuario"),
                        rs.getString("Fecha")
                });
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public static ResultSet buscarReporte(String id) {

        try {
            Connection con = conexionbd.conectar();
            Statement st = con.createStatement();

            String sql = "SELECT * FROM Reportes WHERE id_Reportes = '" + id + "'";

            return st.executeQuery(sql);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    
    public static void agregarReporte(String id, String categoria, String descripcion, String usuario, String fecha) {

        try {
            Connection con = conexionbd.conectar();

            String sql = "INSERT INTO Reportes (id_Reportes, Categoria, Descripcion, Usuario, Fecha) VALUES ('"
                    + id + "','" + categoria + "','" + descripcion + "','" + usuario + "','" + fecha + "')";

            Statement st = con.createStatement();
            st.executeUpdate(sql);

            st.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public static void actualizarReporte(String id, String categoria, String descripcion, String usuario, String fecha) {

        try {
            Connection con = conexionbd.conectar();

            String sql = "UPDATE Reportes SET " +
                    "Categoria='" + categoria + "', " +
                    "Descripcion='" + descripcion + "', " +
                    "Usuario='" + usuario + "', " +
                    "Fecha='" + fecha + "' " +
                    "WHERE id_Reportes='" + id + "'";

            Statement st = con.createStatement();
            st.executeUpdate(sql);

            st.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public static void eliminarReporte(String id) {

        try {
            Connection con = conexionbd.conectar();

            String sql = "DELETE FROM Reportes WHERE id_Reportes='" + id + "'";

            Statement st = con.createStatement();
            st.executeUpdate(sql);

            st.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}