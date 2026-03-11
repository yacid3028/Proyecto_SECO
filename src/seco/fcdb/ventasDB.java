package seco.fcdb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

import seco.fcdb.conexionbd;

public class ventasDB {

    public void consultarVentas(DefaultTableModel modelo) {

        Connection con = conexionbd.conect();
        String sql = "SELECT * FROM ventas";

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                int id = rs.getInt("id");
                String fecha = rs.getString("fecha");
                String producto = rs.getString("producto");
                int cantidad = rs.getInt("cantidad");
                double subtotal = rs.getDouble("subtotal");

                modelo.addRow(new Object[] {
                        "#V-" + id,
                        fecha,
                        producto,
                        cantidad,
                        "$" + subtotal
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
