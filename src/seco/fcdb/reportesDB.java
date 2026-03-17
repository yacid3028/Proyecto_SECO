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

            System.out.println("Error al cargar reportes");
            e.printStackTrace();

        }

    }
}