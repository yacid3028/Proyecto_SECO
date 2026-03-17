package seco.fcdb;

import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class ordenesDB {

    /**
     * Obtiene el conteo de órdenes pendientes.
     */
    public int obtenerOrdenesPendientes() {
        Connection con = conexionbd.conect();
        String sql = "SELECT COUNT(*) AS count FROM ordenes WHERE estado = 'Pendiente'";

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
     * Obtiene el valor de ingreso pendiente: precio_compra * cantidad para órdenes pendientes.
     */
    public double obtenerValorIngresoPendiente() {
        Connection con = conexionbd.conect();
        String sql = "SELECT SUM(o.cantidad * p.precio_compra) AS valor " +
                     "FROM ordenes o " +
                     "JOIN productos p ON o.id_producto = p.id " +
                     "WHERE o.estado = 'Pendiente'";

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
     * Consulta órdenes pendientes para la tabla.
     */
    public void consultarOrdenesPendientes(DefaultTableModel modelo) {
        Connection con = conexionbd.conect();
        String sql = "SELECT o.id, pr.nombre AS proveedor, o.estado " +
                     "FROM ordenes o " +
                     "JOIN proveedores pr ON o.id_proveedor = pr.id " +
                     "WHERE o.estado = 'Pendiente'";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                modelo.addRow(new Object[] {
                        rs.getInt("id"),
                        rs.getString("proveedor"),
                        rs.getString("estado")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
