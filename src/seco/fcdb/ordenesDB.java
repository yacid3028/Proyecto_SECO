package seco.fcdb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

import seco.conexionbd;

public class ordenesDB {

    public void consultarOrdenes(DefaultTableModel modelo) {

        try {

            Connection con = conexionbd.conectar();

            String sql = "SELECT * FROM Ordenes";

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                modelo.addRow(new Object[]{
                        rs.getInt("id_Orden"),
                        rs.getString("Proveedor"),
                        rs.getString("Producto"),
                        rs.getInt("Cantidad"),
                        rs.getString("Fecha")
                });

            }

            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {

            System.out.println("Error al cargar ordenes");
            e.printStackTrace();

        }

    }

}