package seco.fcdb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import seco.conexionbd;

public class ordenesDB {

    public void consultarOrdenes(DefaultTableModel modelo) {
        Connection con = conexionbd.conect();
        String sql = "SELECT * FROM Ordenes";

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                String id_Ordenes = rs.getString("id_Ordenes");
                String proveedor = rs.getString("Provedor");
                String producto = rs.getString("Producto");
                Double costo = rs.getDouble("Costo");
                Integer cantidad = rs.getInt("Cantidad");
                String fecha = rs.getString("Fecha");
                String estado = rs.getString("Estado");

                modelo.addRow(new Object[]{
                    id_Ordenes, proveedor, producto, costo, cantidad, fecha, estado
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertarOrden(String id_Ordenes, String proveedor, String producto,
                          double costo, int cantidad, String fecha, String estado) {

    Connection con = conexionbd.conect();

    String sql = "INSERT INTO Ordenes (id_Odenes, Provedor, Producto, Costo, Cantidad, Fecha, Estado) VALUES (?,?,?,?,?,?,?)";

    try {
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setString(1, id_Ordenes);
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

    public void eliminarOrden(String id_Ordenes) {

        Connection con = conexionbd.conect();
        String sql = "DELETE FROM Ordenes WHERE id_Odenes=?";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id_Ordenes);
            pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actualizarOrden(String id_Ordenes, String proveedor, String producto,
                                 double costo, int cantidad, String fecha, String estado) {

        Connection con = conexionbd.conect();

        String sql = "UPDATE Ordenes SET [Provedor]=?, [Producto]=?, [Costo]=?, "
                   + "[Cantidad]=?, [Fecha]=?, [Estado]=? WHERE [id_Odenes]=?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, proveedor);
            ps.setString(2, producto);
            ps.setDouble(3, costo);
            ps.setInt(4, cantidad);
            ps.setString(5, fecha);
            ps.setString(6, estado);
            ps.setString(7, id_Ordenes);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet buscarOrden(String id_Ordenes) {
        Connection con = conexionbd.conect();
        String sql = "SELECT * FROM Ordenes WHERE id_Odenes=?";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, id_Ordenes);
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    public boolean existeID(String id_Ordenes) {
        try {
            Connection con = conexionbd.conect();
            String sql = "SELECT id_Odenes FROM Ordenes WHERE id_Odenes=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id_Ordenes);
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}