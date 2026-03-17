package seco.fcdb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.healthmarketscience.jackcess.complex.ComplexValue.Id;

import seco.conexionbd;

public class productosDB {

    public static void productos(DefaultTableModel model) {

        try {

            Connection con = conexionbd.conectar();
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

    public  void agregarProducto(String id, String nombre, String categoria, double precioCompra, double precioVenta, int stock) {

        try {

            Connection con = conexionbd.conectar();



            String sql = "INSERT INTO Productos (Id,id_Productos, Nombre,Precio_de_compra, Precio_de_venta,Categoria ,Stock) VALUES (0,'"
                    + id + "','" + nombre + "'," + precioCompra + ",'" + precioVenta + "','" + categoria + "'," + stock + ")";


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
        Connection con = conexionbd.conectar();
        String sql = "SELECT * FROM Productos ";
       
        modelo.setRowCount(0);
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                String id = rs.getString("id_Productos");
                if (id.contains(idProducto)) {
                    String nombre = rs.getString("Nombre");
                    String categoria = rs.getString("Categoria");
                    double precioVenta = rs.getDouble("Precio_de_venta");
                    double precioCompra = rs.getDouble("Precio_de_compra");
                    int stock = rs.getInt("Stock");

                    modelo.addRow(new Object[] { id, nombre, categoria, precioVenta, precioCompra, stock });
                    
                }

                

            }

        } catch (Exception e) {
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
