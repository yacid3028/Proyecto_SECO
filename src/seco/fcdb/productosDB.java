package seco.fcdb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

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
            insertarPromedio(nombre);

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

    public void ActualizaAlert() {
        try (Connection con = conexionbd.conect();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(
                        "SELECT id_Productos, Nombre, Stock FROM Productos")) {

            while (rs.next()) {
                if (rs.getInt("Stock") < 8) {
                    String nombre = rs.getString("Nombre");
                    updateAlert(nombre);
                }

            }
        } catch (Exception e) {
            System.out.println("Error al cargar alertas de bajo stock");
            e.printStackTrace();
        }
    }

    private void updateAlert(String nombre) {
        try (Connection con = conexionbd.conect();
                Statement st = con.createStatement()) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String fechaActual = sdf.format(new Date());
            int cantidad = ConsultaPromedio(nombre);

            if (!validacionAlert(nombre, fechaActual)) {
                String id = idAleratoria();
                String sql = "INSERT INTO Alertas (id_Alertas,Producto, Nombre, Cantidad, Fecha, Estatus) VALUES ('"
                        + id
                        + "', '"
                        + nombre
                        + "', " + cantidad + ", '" + fechaActual + "', false) ";
                st.executeUpdate(sql); // Si ya existe una alerta para este producto, no hacemos nada
            }

        } catch (Exception e) {
            System.out.println("Error al actualizar alertas de bajo stock");
            e.printStackTrace();
        }
    }

    private boolean validacionAlert(String nombre, String fecha) {
        try (Connection con = conexionbd.conect();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT id_Alertas FROM Alertas WHERE Nombre = '" + nombre
                        + "' AND Fecha = '" + fecha + "'")) {

            return rs.next(); // Retorna true si existe una alerta con el mismo ID, false si no existe

        } catch (Exception e) {
            System.out.println("Error al validar alerta de bajo stock");
            e.printStackTrace();
            return false; // En caso de error, asumimos que no existe la alerta para evitar inserciones
                          // duplicadas
        }
    }

    private String idAleratoria() {
        String id = "A";
        for (int i = 0; i < 6; i++) {
            id += (int) (Math.random() * 10);
        }
        return id;
    }

    private int ConsultaPromedio(String nombre) {
        try (Connection con = conexionbd.conect();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT Promedio FROM Promedio WHERE Producto = '" + nombre + "'")) {

            if (rs.next()) {
                return rs.getInt("Promedio") + 5;
            } else {
                System.out.println("No se pudo obtener el promedio para el producto: " + nombre);
                return 20; // En caso de no encontrar el producto, retornamos 20 como valor predeterminado
            }
        } catch (Exception e) {
            System.out.println("Error al consultar el promedio para el producto: " + nombre);
            e.printStackTrace();
            return 20; // En caso de error, retornamos 20 como valor predeterminado
        }
    }

    private void insertarPromedio(String nombre) {
        try (Connection con = conexionbd.conect();
                Statement st = con.createStatement()) {
            int semanaActual = conaultaSem();

            String sql = "INSERT INTO Promedio (Producto, Semana) VALUES ('" + nombre + "', " + semanaActual + ")";
            st.executeUpdate(sql);

        } catch (Exception e) {
            System.out.println("Error al insertar producto en tabla de promedio");
            e.printStackTrace();
        }
    }

    private int conaultaSem() {
        try (Connection con = conexionbd.conect();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT Semana_act FROM Config")) {

            if (rs.next()) {
                return rs.getInt("Semana_act");
            } else {
                System.out.println("No se pudo obtener la semana actual para el promedio");
                return 1; // En caso de no encontrar la semana, retornamos 1 como valor predeterminado
            }
        } catch (Exception e) {
            System.out.println("Error al consultar la semana actual para el promedio");
            e.printStackTrace();
            return 1; // En caso de error, retornamos 1 como valor predeterminado
        }
    }
}