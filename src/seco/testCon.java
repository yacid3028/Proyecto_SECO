package seco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class testCon {
    public static void main(String[] args) {
        Connection con = conexionbd.conect();
        String sql = "INSERT INTO Productos(id_Producto,Nombre,Descripcion,Precio_venta,Stock) values(?,?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, 1);
            ps.setString(2, "paleta");
            ps.setString(3, "paleta de paleta");
            ps.setDouble(4, 3.50);
            ps.setInt(5, 15);

            ps.executeUpdate();

        } catch (Exception e) {
            // TODO: handle exception
        }

        try {

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Productos");

            while (rs.next()) {
                System.out
                        .println(rs.getString("nombre") + " " + rs.getInt("stock") + " " + rs.getString("descripcion"));
            }
        } catch (Exception e) {
            System.out.println("error de consulta");
        }

    }
}
