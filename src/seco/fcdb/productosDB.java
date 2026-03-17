package seco.fcdb;

import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class productosDB {

    /**
     * Consulta productos con stock bajo (7 unidades o menos).
     */
    public void consultarAlertas(DefaultTableModel modelo) {
        Connection con = conexionbd.conect();
        String sql = "SELECT nombre, stock FROM productos WHERE stock <= 7 ORDER BY stock ASC";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                int stock = rs.getInt("stock");

                modelo.addRow(new Object[] {
                        nombre,
                        stock,
                        "Bajo Stock"
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene el stock total de todos los productos.
     */
    public int obtenerStockTotal() {
        Connection con = conexionbd.conect();
        String sql = "SELECT SUM(stock) AS total FROM productos";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Obtiene el conteo de productos con stock bajo (7 o menos).
     */
    public int obtenerBajoStock() {
        Connection con = conexionbd.conect();
        String sql = "SELECT COUNT(*) AS count FROM productos WHERE stock <= 7";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt("count");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Obtiene el valor de ingreso del inventario: (precio_venta - precio_compra) * stock
     */
    public double obtenerValorIngreso() {
        Connection con = conexionbd.conect();
        String sql = "SELECT SUM((precio_venta - precio_compra) * stock) AS valor FROM productos";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getDouble("valor");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    /**
     * Consulta productos para el resumen de stock, ordenados de mayor a menor stock.
     */
    public int[] consultarResumenStock() {
        Connection con = conexionbd.conect();
        String sql = "SELECT stock FROM productos ORDER BY stock DESC";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            // Asumiendo un máximo de 9 productos para la gráfica
            int[] valores = new int[9];
            int i = 0;
            while (rs.next() && i < 9) {
                valores[i] = rs.getInt("stock");
                i++;
            }
            return valores;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new int[9]; // Retorna array vacío si hay error
    }

    /**
     * Consulta productos bajos de stock para la tabla.
     */
    public void consultarBajoStock(DefaultTableModel modelo) {
        Connection con = conexionbd.conect();
        String sql = "SELECT nombre, stock FROM productos WHERE stock <= 7 ORDER BY stock ASC";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                modelo.addRow(new Object[] {
                        rs.getString("nombre"),
                        rs.getInt("stock"),
                        "Bajo Stock"
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
