package seco.fcdb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

import seco.fcdb.conexionbd;

public class ventasDB {
    ResultSet rs;

    public void consultarVentas(DefaultTableModel modelo) {

        Connection con = conexionbd.conect();
        String sql = "SELECT * FROM Ventas";

        try {
            Statement st = con.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {

                String id = rs.getString("id_Ventas");
                String fecha = rs.getString("Fecha");
                String producto = rs.getString("Producto");
                int cantidad = rs.getInt("cantidades");
                double subtotal = rs.getDouble("Subtotal");
                double total = rs.getDouble("Total");

                modelo.addRow(new Object[] {
                        id,
                        producto,
                        cantidad,
                        "$" + subtotal,
                        "$" + total,
                        fecha
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
