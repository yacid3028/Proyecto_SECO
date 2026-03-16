package seco.fcdb;

import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class entradasDB {

    // Clase encargada de CRUD de la tabla "entradas" en la base de datos.
    // Los métodos son llamados desde src/seco/entradas.java (UI) para:
    // - consultarEntradas() -> leer datos y mostrarlos en la JTable
    // - agregarEntrada()  -> insertar nuevo registro
    // - actualizarEntrada() -> modificar registro existente
    // - eliminarEntrada() -> borrar registro

    /**
     * Lee todas las filas de la tabla "entradas" y las carga en el modelo de la JTable.
     * Este método lo llama entradas.cargarEntradas().
     */
    public void consultarEntradas(DefaultTableModel modelo) {
        Connection con = conexionbd.conect();
        String sql = "SELECT id_entrada, Fecha, Productos, Provedor, Cantidad FROM entradas";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                String id = rs.getString("id_entrada");
                String fecha = rs.getString("Fecha");
                String producto = rs.getString("Productos");
                String proveedor = rs.getString("Provedor");
                int cantidad = rs.getInt("Cantidad");

                String estado = "";
                try {
                    estado = rs.getString("Estado");
                    if (estado == null) {
                        estado = "";
                    }
                } catch (SQLException ignored) {
                    // Si la tabla no tiene la columna Estado, se ignora.
                }

                modelo.addRow(new Object[] {
                        id,
                        fecha,
                        producto,
                        proveedor,
                        cantidad,
                        estado,
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserta una nueva fila en la tabla "entradas".
     * Se usa desde entradas.mostrarDialogoEntrada(false).
     */
    public boolean agregarEntrada(String producto, String proveedor, int cantidad) {
        String sql = "INSERT INTO entradas (Fecha, Productos, Provedor, Cantidad) VALUES (?, ?, ?, ?)";

        try (Connection con = conexionbd.conect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(System.currentTimeMillis()));
            ps.setString(2, producto);
            ps.setString(3, proveedor);
            ps.setInt(4, cantidad);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Actualiza un registro existente (usado desde entradas.mostrarDialogoEntrada(true)).
     */
    public boolean actualizarEntrada(int id, String producto, String proveedor, int cantidad) {
        String sql = "UPDATE entradas SET Productos = ?, Provedor = ?, Cantidad = ? WHERE id_entrada = ?";

        try (Connection con = conexionbd.conect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, producto);
            ps.setString(2, proveedor);
            ps.setInt(3, cantidad);
            ps.setInt(4, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Borra un registro de la tabla "entradas".
     * Se llama desde entradas.eliminarEntradaSeleccionada().
     */
    public boolean eliminarEntrada(int id) {
        String sql = "DELETE FROM entradas WHERE id_entrada = ?";

        try (Connection con = conexionbd.conect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para contar entradas del día actual (Entradas Hoy)
    public int contarEntradasHoy() {
        String sql = "SELECT COUNT(*) FROM entradas WHERE Fecha = ?";

        try (Connection con = conexionbd.conect();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(System.currentTimeMillis()));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Método para sumar todas las cantidades de entradas (Productos totales)
    public int sumarCantidadesTotales() {
        String sql = "SELECT SUM(Cantidad) FROM entradas";

        try (Connection con = conexionbd.conect();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}