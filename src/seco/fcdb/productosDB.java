package seco.fcdb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

import seco.conexionbd;

public class productosDB {

    public static void productos(DefaultTableModel model) {

        try {

            Connection con = conexionbd.conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Productos");

            while (rs.next()) {

                model.addRow(new Object[] {
                        rs.getInt("id_Producto"),
                        rs.getString("Nombre"),
                        rs.getString("Descripcion"),
                        rs.getDouble("Precio_venta"),
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

    // METODO PARA ELIMINAR PRODUCTO
    public static void eliminarProducto(int id) {

        try {

            Connection con = conexionbd.conectar();

            String sql = "DELETE FROM Productos WHERE id_Producto = " + id;

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

    public static void agregarProducto(int id, String nombre, String descripcion, double precio, int stock) {

        try {

            Connection con = conexionbd.conectar();

            String sql = "INSERT INTO Productos (id_Producto, Nombre, Descripcion, Precio_venta, Stock) VALUES ("
                    + id + ",'" + nombre + "','" + descripcion + "'," + precio + "," + stock + ")";

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

}

// Validar existencia del id antes de eliminar o editar o agregar producto
// Crear metodo para generar String Id con random
// cambiar datos de entrada por getSelectedRow de la tabla productos
// Agregar metodo para actualizar producto (UPDATE) con datos de la tabla
// productos
