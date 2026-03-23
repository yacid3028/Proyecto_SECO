package seco.fcdb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import seco.conexionbd;

public class ordenesDB {

    public void consultarOrdenes(DefaultTableModel modelo) {
        String sql = "SELECT * FROM Ordenes";
        modelo.setRowCount(0);

        try (Connection con = conexionbd.conect();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                modelo.addRow(new Object[] {
                        rs.getString("id_Odenes"),
                        rs.getString("Provedor"),
                        rs.getString("Producto"),
                        rs.getDouble("Costo"),
                        rs.getInt("Cantidad"),
                        rs.getString("Fecha"),
                        rs.getString("Estado")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertarOrden(String id_Odenes, String proveedor, String producto,
            double costo, int cantidad, String fecha, String estado) {

        String sql = "INSERT INTO Ordenes (id_Odenes, Provedor, Producto, Costo, Cantidad, Fecha, Estado) VALUES (?,?,?,?,?,?,?)";

        try (Connection con = conexionbd.conect();
                PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, id_Odenes);
            pst.setString(2, proveedor);
            pst.setString(3, producto);
            pst.setDouble(4, costo);
            pst.setInt(5, cantidad);
            pst.setString(6, fecha);
            pst.setString(7, estado);

            pst.executeUpdate();
            System.out.println("Orden guardada correctamente");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean eliminarOrden(String id_Odenes) {

        String sql = "DELETE FROM Ordenes WHERE id_Odenes=?";

        try (Connection con = conexionbd.conect();
                PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, id_Odenes);

            int filas = pst.executeUpdate();
            return filas > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarEstado(String id_Odenes, String estado) {

        String sql = "UPDATE Ordenes SET Estado=? WHERE id_Odenes=?";

        try (Connection con = conexionbd.conect();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, estado);
            ps.setString(2, id_Odenes);

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet buscarOrden(String id_Odenes) {
        String sql = "SELECT * FROM Ordenes WHERE id_Odenes=?";

        try {
            Connection con = conexionbd.conect();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, id_Odenes);
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean existeID(String id_Odenes) {
        String sql = "SELECT id_Odenes FROM Ordenes WHERE id_Odenes=?";

        try (Connection con = conexionbd.conect();
                PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, id_Odenes);
            ResultSet rs = pst.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String[] cargarProductos() {
        Connection con = conexionbd.conect();
        String sql = "SELECT Nombre FROM Productos";
        java.util.ArrayList<String> Productos = new java.util.ArrayList<>();

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Productos.add(rs.getString("Nombre"));
            }

            return Productos.toArray(new String[0]);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar productos");
            return new String[0];
        }
    }

}