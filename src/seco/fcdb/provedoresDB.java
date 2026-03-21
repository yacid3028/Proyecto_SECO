package seco.fcdb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import seco.conexionbd;

public class provedoresDB {

    public void consultarProvedores(DefaultTableModel modelo) {
        Connection con = conexionbd.conect();
        String sql = "SELECT * FROM Provedores";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                String id_Provedor = rs.getString("id_Provedor");
                String empresa = rs.getString("empresa");
                String telefono = rs.getString("telefono");
                String email = rs.getString("email");
                String producto = rs.getString("producto");


                modelo.addRow(new Object[] {id_Provedor, empresa, telefono, email, producto });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertarProveedor(String id_ProvTxt, String empresa, String telefono, String email, String producto) {

    Connection con = conexionbd.conect();
    String sql = "INSERT INTO Provedores(id_Provedor, empresa, telefono, email, producto) VALUES(?,?,?,?,?)";

    try {
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setString(1, id_ProvTxt);
        pst.setString(2, empresa);
        pst.setString(3, telefono);
        pst.setString(4, email);
        pst.setString(5, producto);

        pst.executeUpdate();

        System.out.println("Provedor guardado");

    } catch (Exception e) {
        e.printStackTrace();
    }
}

    public void editarProveedor(String id_Provedor, String empresa, String telefono, String email, String producto) {

    Connection con = conexionbd.conect();
    String sql = "UPDATE Provedores SET empresa=?, telefono=?, email=?, producto=? WHERE id_Provedor=?";

    try {
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setString(1, empresa);
        pst.setString(2, telefono);
        pst.setString(3, email);
        pst.setString(4, producto);
        pst.setString(5, id_Provedor);

        pst.executeUpdate();

    } catch (Exception e) {
        e.printStackTrace();
    }
}

    public void eliminarProveedor(String id_Provedor) {

        Connection con = conexionbd.conect();
        String sql = "DELETE FROM Provedores WHERE id_Provedor=?";

        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id_Provedor);
            pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean existeID(String id_Provedor) {
        try {
            Connection con = conexionbd.conect();
            String sql = "SELECT id_Provedor FROM Provedores WHERE id_Provedor=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id_Provedor);
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ResultSet buscarProveedor(String id) {
    Connection con = conexionbd.conect();
    String sql = "SELECT * FROM Provedores WHERE id_Provedor = ?";

    try {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, id);
        return ps.executeQuery();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}

public void actualizarProveedor(String id, String empresa, String telefono, String email, String producto) {
    Connection con = conexionbd.conect();
    String sql = "UPDATE Provedores SET empresa=?, telefono=?, email=?, producto=? WHERE id_Provedor=?";
    try {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, empresa);
        ps.setString(2, telefono);
        ps.setString(3, email);
        ps.setString(4, producto);
        ps.setString(5, id);
        ps.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

public ResultSet obtenerProvedores() {
        Connection con = conexionbd.conect();
        String sql = "SELECT Empresa FROM Provedores";

        try {
            Statement st = con.createStatement();
            return st.executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}