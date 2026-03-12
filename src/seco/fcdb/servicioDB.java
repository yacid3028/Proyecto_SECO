package seco.fcdb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import seco.fcdb.conexionbd;

public class servicioDB {

    public String[] buscarProducto(String idProducto, JTextField producto) {
        Connection con = conexionbd.conect();
        String sql = "SELECT * FROM Productos ";
        ArrayList<String> datos = new ArrayList<>();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                String id = rs.getString("id_producto");
                if (id.contains(idProducto)) {
                    datos.add(id);
                }

                if (id.equals(idProducto)) {
                    String nombre = rs.getString("Nombre");
                    producto.setText(nombre);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return datos.toArray(new String[0]);
    }

    public void agregarArticulo(String id, String nombre, String cantidad, String fecha,
            DefaultTableModel modelo) {
        Connection con = conexionbd.conect();
        String sql = "SELECT * FROM Productos ";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                String idProd = rs.getString("id_producto");
                if (idProd.equals(id)) {
                    double precio = rs.getDouble("Precio_venta");
                    modelo.addRow(new Object[] {
                            id,
                            nombre,
                            cantidad,
                            "$" + precio,
                            "$" + precio * Integer.parseInt(cantidad),
                            fecha
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
