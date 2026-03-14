package seco.fcdb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

import seco.conexionbd;

public class provedoresDB {
    public void consultarProvedores(DefaultTableModel modelo) {
        Connection con=conexionbd.conect();
        String sql = "SELECT * FROM Provedores";
        try{
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){

                int id_Provedores = rs.getInt("id_Provedores");
                String nombre = rs.getString("nombre");
                String direccion = rs.getString("direccion");
                int telefono = rs.getInt("telefono");

                modelo.addRow(new Object[]{nombre, direccion, telefono});
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    } 

    public void editarProveedor(int id, String nombre, String telefono, String email) {
        Connection con = conexionbd.conect();
        String sql = "UPDATE Provedores SET nombre=?, telefono=?, email=? WHERE id=?";
        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, nombre);
            pst.setString(2, telefono);
            pst.setString(3, email);
            pst.setInt(4, id);
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void insertarProveedor(String nombre, String email, String telefono, String id) {

    Connection con = conexionbd.conect();
    String sql = "INSERT INTO Provedores(nombre,email,telefono,id) VALUES(?,?,?,?)";

    try {

        PreparedStatement pst = con.prepareStatement(sql);

        pst.setString(1, nombre);
        pst.setString(2, email);
        pst.setString(3, telefono);
        pst.setString(4, id);

        pst.executeUpdate();

        System.out.println("Proveedor guardado");

    } catch (Exception e) {
        e.printStackTrace();
    }
}

public void eliminarProveedor(int id) {
    Connection con = conexionbd.conect();
    String sql = "DELETE FROM Provedores WHERE id=?";
    try {
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, id);
        pst.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

}