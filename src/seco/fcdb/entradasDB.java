package seco.fcdb;

import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class entradasDB {
    ResultSet rs;

    public void consultarEntradas(DefaultTableModel modelo) {

        Connection con = conexionbd.conect();
        String sql = "SELECT id_entrada  , Fecha, Productos, Provedor, Cantidad, FROM entradas";

        try {
            Statement st = con.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                String id = rs.getString("id");
                String fecha = rs.getString("fecha");
                String producto = rs.getString("producto");
                String proveedor = rs.getString("proveedor");
                int cantidad = rs.getInt("cantidad");

                modelo.addRow(new Object[] {
                        id,
                        fecha,
                        producto,
                        proveedor,
                        cantidad,

                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}