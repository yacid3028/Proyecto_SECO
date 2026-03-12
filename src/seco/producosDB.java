package seco.fcdb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

public class productosDB {

    public static void productos(DefaultTableModel model) {

        try {

            // conexión a la base de datos
            Connection con = conexionbd.conectar();

            // crear objeto para ejecutar SQL
            Statement st = con.createStatement();

            // consulta SQL
            ResultSet rs = st.executeQuery("SELECT * FROM Productos");

            // recorrer resultados
            while (rs.next()) {

                model.addRow(new Object[]{
                        rs.getInt("id_Producto"),
                        rs.getString("Nombre"),
                        rs.getString("Descripcion"),
                        rs.getDouble("Precio_venta"),
                        rs.getInt("Stock")
                });

            }

            // cerrar conexiones
            rs.close();
            st.close();
            con.close();

        } catch (Exception e) {
            System.out.println("Error al cargar productos");
            e.printStackTrace();
        }

    }
}