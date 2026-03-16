package seco.fcdb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.ResultSetMetaData;
import javax.swing.table.DefaultTableModel;

import seco.conexionbd;

public class productosDB {

    public static void productos(DefaultTableModel model) {

    try {

        Connection con = conexionbd.conectar();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM Productos");

        ResultSetMetaData meta = rs.getMetaData();

        for (int i = 1; i <= meta.getColumnCount(); i++) {
            System.out.println(meta.getColumnName(i));
        }

        while (rs.next()) {

            model.addRow(new Object[] {
                    rs.getInt("id_Producto"),
                    rs.getString("Nombre"),
                    rs.getString("Categoria"),
                    rs.getDouble("Precio de venta"),
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

    public static void agregarProducto(String id, String nombre, String descripcion, double precio, int stock) {

        try {

            Connection con = conexionbd.conectar();

            String sql = "INSERT INTO Productos (id_producto, Nombre, Categoria, [Precio de venta], Stock) VALUES ("
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
// Modificar insersion, falta categoria y precio de compra y sobra descripcion
// VE LA BASE DE DATOS NO DEJES Q TODO LO HAGA CHAT GPT, HAZLO TU TAMBIEN, ES
// PARA APRENDER, NO PARA QUE TE LO HAGA TODO LA IA, SI QUIERES Q TE AYUDE EN
// ALGO ESPECIFICO PREGUNTA, PERO NO LE DIGAS Q TE HAGA TODO EL CODIGO, ESO NO
// ESTA BIEN, APRENDE A PROGRAMAR, NO A USAR CHAT GPT PARA PROGRAMAR.
