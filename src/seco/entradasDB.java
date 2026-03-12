package seco;

import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class entradasDB {

    public static DefaultTableModel modeloEntradas() {
        String[] cols = { "ID", "FECHA", "PRODUCTO", "PROVEEDOR", "CANTIDAD", "ESTADO" };
        DefaultTableModel model = new DefaultTableModel(cols, 0);

        try (Connection c = conexionbd.conect();
             PreparedStatement ps = c.prepareStatement(
                     "SELECT id, fecha, producto, proveedor, cantidad, estado FROM entradas");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("fecha"),
                    rs.getString("producto"),
                    rs.getString("proveedor"),
                    rs.getInt("cantidad"),
                    rs.getString("estado")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return model;
    }

    // podrías añadir aquí otros métodos:
    // public static boolean insertar(Entrada e) { … }
    // public static boolean eliminar(int id) { … }
    // public static boolean actualizar(Entrada e) { … }
}