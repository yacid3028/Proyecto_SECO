package seco.fcdb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import seco.conexionbd;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JLabel;
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
        String sql = "SELECT * FROM Productos";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                String idProd = rs.getString("id_Productos");
                if (idProd.equals(id)) {
                    double precio = Math.round((rs.getDouble("Precio_de_venta") / 1.16) * 100.0) / 100.0;
                    double cant = Double.parseDouble(cantidad.getText());
                    double importe = Math.round((precio * cant) * 100.0) / 100.0;

                    modelo.addRow(new Object[] {
                            id,
                            nombre.getText(),
                            cantidad.getText(),
                            String.format("$ %.2f", precio),
                            String.format("$ %.2f", importe),
                            fecha
                    });

                    sub += importe;
                    imp = Math.round((sub * 0.16) * 100.0) / 100.0;
                    tot = Math.round((sub + imp) * 100.0) / 100.0;

                    subtotal.setText(String.format("$ %.2f", sub));
                    impuesto.setText(String.format("$ %.2f", imp));
                    total.setText(String.format("$ %.2f", tot));

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
            double importe = Double.parseDouble(
                    modelo.getValueAt(selectedRow, 4).toString().replace("$ ", ""));
            modelo.removeRow(selectedRow);

            double sub = Double.parseDouble(subtotal.getText().replace("$ ", ""));
            sub = Math.round((sub - importe) * 100.0) / 100.0;

            if (sub <= 0) {
                subtotal.setText("$ 0.00");
                impuesto.setText("$ 0.00");
                total.setText("$ 0.00");
            } else {
                double imp = Math.round((sub * 0.16) * 100.0) / 100.0;
                double tot = Math.round((sub + imp) * 100.0) / 100.0;
                subtotal.setText(String.format("$ %.2f", sub));
                impuesto.setText(String.format("$ %.2f", imp));
                total.setText(String.format("$ %.2f", tot));
            }
        }
    }

    public void registrarVenta(String idVenta, String fecha, String producto, int cantidad, double subtotal,
            double total, String factura) {

        Connection con = conexionbd.conect();
        String sw = "SELECT Semana_act FROM Config";
        descontarStock(producto, cantidad);

        int semana = 0;

        try {
            Statement st = con.createStatement();
            ResultSet sr = st.executeQuery(sw);
            if (sr.next()) {
                semana = sr.getInt("Semana_act");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String sql = "INSERT INTO Salidas (id_Venta, Producto, Cantidad, Total,Semana,Fecha,Factura) VALUES ('"
                + idVenta + "', '" + producto + "', " + cantidad + ", " + subtotal + ", " + semana + ", '" + fecha
                + "','" + factura + "')";
        try {
            Statement st = con.createStatement();
            st.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String crearRandom() {
        String valrey = "";
        int contador = 6;
        for (int i = 0; i < contador; i++) {
            int random = (int) (Math.random() * 9);
            valrey += random;
        }

        return valrey;
    }

    public String facturacount() {
        String factura = "";

        String sg = "SELECT Factura FROM Salidas";
        try {
            Connection con = conexionbd.conect();
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet sr = st.executeQuery(sg);

            if (sr.last()) {
                int sumar = Integer.parseInt(sr.getString("Factura").substring(1, 7));
                factura = String.format("F%06d", sumar + 1);
            } else {
                factura = "F000001";
            }

        } catch (Exception e) {
            System.out.print("erorr aqui en factr");
        }
        return factura;
    }

    public void descontarStock(String nombreProducto, int cantidad) {
        Connection con = conexionbd.conect();
        String sql = "UPDATE Productos SET Stock = Stock - ? WHERE Nombre = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cantidad);
            ps.setString(2, nombreProducto);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
