package seco.fcdb;

import java.sql.Connection;
import java.sql.ResultSet;
import seco.conexionbd;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class salidasDB {
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

    public void agregarArticulo(String id, JTextField nombre, JTextField cantidad, String fecha,
            DefaultTableModel modelo, JLabel subtotal, JLabel impuesto, JLabel total) {
        double sub = Double.parseDouble(subtotal.getText().replace("$ ", ""));
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
                            "$ " + precio,
                            "$ " + precio * Integer.parseInt(cantidad.getText()),
                            fecha
                    });
                    sub += precio * Integer.parseInt(cantidad.getText());
                    subtotal.setText("$ " + String.valueOf(sub));
                    impuesto.setText(
                            "$ " + String.valueOf(Integer.parseInt(subtotal.getText().replace("$ ", "")) * 0.15));
                    total.setText("$ " + String.valueOf(Integer.parseInt(subtotal.getText().replace("$ ", ""))
                            + Integer.parseInt(impuesto.getText().replace("$ ", ""))));

                    cantidad.setText("");
                    nombre.setText("");

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void eliminarArticulo(DefaultTableModel modelo, JLabel subtotal, JLabel impuesto, JLabel total) {
        int selectedRow = modelo.getRowCount() - 1;
        if (selectedRow >= 0) {
            double precio = Double.parseDouble(modelo.getValueAt(selectedRow, 4).toString().replace("$ ", ""));
            modelo.removeRow(selectedRow);
            double sub = Double.parseDouble(subtotal.getText().replace("$ ", ""));
            sub -= precio;
            subtotal.setText("$ " + String.valueOf(sub));
            impuesto.setText(
                    "$ " + String.valueOf(Integer.parseInt(subtotal.getText().replace("$ ", "")) * 0.15));
            total.setText("$ " + String.valueOf(Integer.parseInt(subtotal.getText().replace("$ ", ""))
                    + Integer.parseInt(impuesto.getText().replace("$ ", ""))));
        }
    }

    public void registrarVenta(String idVenta, String fecha, String producto, int cantidad, double subtotal,
            double total) {
        Connection con = conexionbd.conect();
        String sql = "INSERT INTO Ventas (id_venta, fecha, producto, cantidad, subtotal, total) VALUES ('"
                + idVenta + "', '" + fecha + "', '" + producto + "', " + cantidad + ", " + subtotal + ", " + total
                + ")";
        try {
            Statement st = con.createStatement();
            st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Venta registrada exitosamente ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
