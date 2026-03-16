package seco.fcdb;

import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class productosDB {

    /**
     * Consulta productos con stock bajo (menos de 10 unidades).
     */
    public void consultarAlertas(DefaultTableModel modelo) {
        Connection con = conexionbd.conect();
        String sql = "SELECT nombre, stock FROM productos WHERE stock < 10";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                int stock = rs.getInt("stock");

                modelo.addRow(new Object[] {
                        nombre,
                        stock,
                        "Bajo Stock"
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
