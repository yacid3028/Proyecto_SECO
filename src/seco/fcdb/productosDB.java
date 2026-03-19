package seco.fcdb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import seco.conexionbd;

public class productosDB {

    public static void productos(DefaultTableModel model) {

        try {
            Connection con = conexionbd.conect();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Productos");

            while (rs.next()) {
                model.addRow(new Object[] {
                        rs.getString("id_Productos"),
                        rs.getString("Nombre"),
                        rs.getString("Categoria"),
                        rs.getDouble("Precio_de_venta"),
                        rs.getDouble("Precio_de_compra"),
                        rs.getInt("Stock")
                });
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            System.out.println("Error al cargar productos");
            e.printStackTrace();
        }
    }

    public static void eliminarProducto(String id) {

        try {
            Connection con = conexionbd.conect();

            String sql = "DELETE FROM Productos WHERE id_Productos = '" + id + "'";

            Statement st = con.createStatement();
            st.executeUpdate(sql);

            st.close();
            con.close();

            System.out.println("Producto eliminado correctamente");

        } catch (Exception e) {
            System.out.println("Error al eliminar producto");
            e.printStackTrace();
        }
    }

    public void agregarProducto(String id, String nombre, String categoria,
            double precioCompra, double precioVenta, int stock) {

        try {
            Connection con = conexionbd.conect();

            String sql = "INSERT INTO Productos (Id, id_Productos, Nombre, Precio_de_compra, Precio_de_venta, Categoria, Stock) VALUES (0,'"
                    + id + "','" + nombre + "'," + precioCompra + "," + precioVenta + ",'" + categoria + "'," + stock
                    + ")";

            Statement st = con.createStatement();
            st.executeUpdate(sql);

            st.close();
            con.close();

            System.out.println("Producto agregado correctamente");

        } catch (Exception e) {
            System.out.println("Error al agregar producto");
            e.printStackTrace();
        }
    }

    public void buscarProducto(String idProducto, DefaultTableModel modelo) {

        Connection con = conexionbd.conect();

        String sql = "SELECT * FROM Productos WHERE id_Productos LIKE '%" + idProducto + "%'";

        modelo.setRowCount(0);

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                modelo.addRow(new Object[] {
                        rs.getString("id_Productos"),
                        rs.getString("Nombre"),
                        rs.getString("Categoria"),
                        rs.getDouble("Precio_de_venta"),
                        rs.getDouble("Precio_de_compra"),
                        rs.getInt("Stock")
                });
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void actualizarProducto(String idProducto, String categoria,
            double precioVenta, double precioCompra, int stock) {

        try {
            Connection con = conexionbd.conect();

            String sql = "UPDATE Productos SET " +
                    "Categoria = '" + categoria + "', " +
                    "Precio_de_venta = " + precioVenta + ", " +
                    "Precio_de_compra = " + precioCompra + ", " +
                    "Stock = " + stock +
                    " WHERE id_Productos = '" + idProducto + "'";

            Statement st = con.createStatement();
            st.executeUpdate(sql);

            st.close();
            con.close();

            System.out.println("Producto actualizado correctamente");

        } catch (Exception e) {
            System.out.println("Error al actualizar producto");
            e.printStackTrace();
        }
    }
}