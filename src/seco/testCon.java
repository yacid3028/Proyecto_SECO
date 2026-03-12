package seco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import seco.fcdb.conexionbd;

public class testCon {
    public static void main(String[] args) {
        Connection con = conexionbd.conect();
        String sql = "INSERT INTO Productos(id_Producto,Nombre,Descripcion,Precio_venta,Stock) values(?,?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, 10);
            ps.setString(2, "Myonesa");
            ps.setString(3, "Crema de mayonesa");
            ps.setDouble(4, 23.50);
            ps.setInt(5, 20);{}

            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("error de consulta");
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
/*
 * INSERT INTO productos(id_Producto,Nombre,Descripcion,Precio_venta,Stock)
 * values(??????);
 * DELETE * FROM productos WHERE id_Producto = ?;
 * UPDATE * FROM productos SET Nombre = ?, Descripcion = ?, Precio_venta = ?,
 * Stock = ?
 * SELECT * FROM productos;
 */