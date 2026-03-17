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
    ArrayList<String> datos = new ArrayList<>();
    int contador;

    public String[] buscarProducto(String idProducto, JTextField producto) {
        Connection con = conexionbd.conect();
        String sql = "SELECT * FROM Productos ";
        datos.clear();
        producto.setText("");
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                String id = rs.getString("id_Productos");
                if (id.contains(idProducto)) {
                    datos.add(id);
                    contador++;
                }

                if (id.equals(idProducto)) {
                    String nombre = rs.getString("Nombre");
                    producto.setText(nombre);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return datos.toArray(new String[contador]);
    }

    public void agregarArticulo(String id, JTextField nombre, JTextField cantidad, String fecha,
            DefaultTableModel modelo, JLabel subtotal, JLabel impuesto, JLabel total) {
        double sub = Double.parseDouble(subtotal.getText().replace("$ ", ""));
        double imp = Double.parseDouble(impuesto.getText().replace("$ ", ""));
        double tot = Double.parseDouble(total.getText().replace("$ ", ""));
        Connection con = conexionbd.conect();
        String sql = "SELECT * FROM Productos ";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                String idProd = rs.getString("id_Productos");
                if (idProd.equals(id)) {
                    double precio = rs.getDouble("Precio_de_venta");
                    modelo.addRow(new Object[] {
                            id,
                            nombre.getText(),
                            cantidad.getText(),
                            "$ " + precio,
                            "$ " + precio * Integer.parseInt(cantidad.getText()),
                            fecha
                    });
                    sub += precio * Integer.parseInt(cantidad.getText());
                    imp += precio * Integer.parseInt(cantidad.getText()) * 0.15;
                    tot += precio * Integer.parseInt(cantidad.getText())
                            + precio * Integer.parseInt(cantidad.getText()) * 0.15;
                    subtotal.setText("$ " + String.valueOf(sub));
                    impuesto.setText(
                            "$ " + String.valueOf(imp));
                    total.setText("$ " + String.valueOf(tot));

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
            if (sub == 0) {
                subtotal.setText("$ 0.00");
                impuesto.setText("$ 0.00");
                total.setText("$ 0.00");
            } else {
                subtotal.setText("$ " + String.valueOf(sub));
                impuesto.setText(
                        "$ " + String.valueOf(sub * 0.15));
                total.setText("$ " + String.valueOf(sub + (sub * 0.15)));
            }
        }
    }

    public void registrarVenta(String idVenta, String fecha, String producto, int cantidad, double subtotal,
            double total) {
        if (idVenta.length() > 6) {
            idVenta = "V" + idVenta.substring(0, 5);
        } else if (idVenta.length() < 6) {
            String a = crearRandom(idVenta.length());
            idVenta = "V" + idVenta + a;
        }

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

    private String crearRandom(int longitud) {
        String valrey = "";
        int contador = 6 - longitud;
        for (int i = 0; i < contador; i++) {
            int random = (int) (Math.random() * 9);
            valrey += random;
        }

        return valrey;
    }
}
