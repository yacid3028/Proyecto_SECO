package seco.fcdb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import seco.conexionbd;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

public class ventasDB {
    ResultSet rs;

    public void consultarVentas(DefaultTableModel modelo) {

        Connection con = conexionbd.conect();
        String sql = "SELECT * FROM Salidas";

        try {
            Statement st = con.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {

                String idVenta = rs.getString("id_Venta");
                String producto = rs.getString("Producto");
                String cantidad = rs.getString("Cantidad");
                int total = rs.getInt("Total");
                String factura = rs.getString("Factura");
                String fecha = rs.getString("Fecha");

                modelo.addRow(new Object[] {
                        idVenta,
                        producto,
                        cantidad,
                        "$" + total,
                        factura,
                        fecha
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void consultarVentasPorFecha(DefaultTableModel modelo, String fecha) {
        Connection con = conexionbd.conect();
        String sql = "SELECT * FROM Salidas WHERE Fecha = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, fecha);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[] {
                        rs.getString("id_Venta"),
                        rs.getString("Producto"),
                        rs.getInt("Cantidad"),
                        String.format("$ %.2f", rs.getDouble("Total")),
                        rs.getString("Factura"),
                        rs.getString("Fecha")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Filtra por semana actual usando Semana de Config
    public void consultarVentasPorSemana(DefaultTableModel modelo) {
        Connection con = conexionbd.conect();
        // Primero obtiene la semana actual de Config
        String sqlSemana = "SELECT Semana_act FROM Config";
        int semanaActual = 0;
        try (Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sqlSemana)) {
            if (rs.next())
                semanaActual = rs.getInt("Semana_act");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Luego filtra las ventas por esa semana
        String sql = "SELECT * FROM Salidas WHERE Semana = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, semanaActual);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[] {
                        rs.getString("id_Venta"),
                        rs.getString("Producto"),
                        rs.getInt("Cantidad"),
                        String.format("$ %.2f", rs.getDouble("Total")),
                        rs.getString("Factura"),
                        rs.getString("Fecha")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Ordena por precio ASC o DESC
    public void consultarVentasOrdenPrecio(DefaultTableModel modelo, String orden) {
        Connection con = conexionbd.conect();
        String sql = "SELECT * FROM Salidas ORDER BY Total " + orden;
        try (Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                modelo.addRow(new Object[] {
                        rs.getString("id_Venta"),
                        rs.getString("Producto"),
                        rs.getInt("Cantidad"),
                        String.format("$ %.2f", rs.getDouble("Total")),
                        rs.getString("Factura"),
                        rs.getString("Fecha")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buscarVentas(String idProducto, DefaultTableModel modelo) {
        try {
            Connection con = conexionbd.conect();
            String sql = "SELECT * FROM Salidas ";

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            modelo.setRowCount(0);

            while (rs.next()) {
                if (rs.getString("id_Venta").contains(idProducto)) {
                    modelo.addRow(new Object[] {
                            rs.getString("id_Venta"),
                            rs.getString("Producto"),
                            rs.getString("Cantidad"),
                            rs.getDouble("Total"),
                            rs.getString("Factura"),
                            rs.getString("Fecha")
                    });
                }
            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            System.out.println("Error en búsqueda");
            e.printStackTrace();
        }
    }
}
