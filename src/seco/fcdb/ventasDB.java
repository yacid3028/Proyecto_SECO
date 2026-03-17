package seco.fcdb;

import java.sql.Connection;
import java.sql.ResultSet;
import seco.conexionbd;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

public class ventasDB {
    ResultSet rs;

    public void consultarVentas(DefaultTableModel modelo) {

        Connection con = conexionbd.conect();
        String sql = "SELECT * FROM Salidas";

        try {
            Statement st = con.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {

                String idVenta = rs.getString("id_Venta");
                String producto = rs.getString("Producto");
                String cantidad = rs.getString("Cantidad");
                int total = rs.getInt("Total");
                String fecha = rs.getString("Fecha");

                modelo.addRow(new Object[] {
                        idVenta,
                        producto,
                        cantidad,
                        "$" + total,
                        fecha
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
