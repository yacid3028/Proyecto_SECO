package seco.fcdb;

import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class entradasDB {
    // Variable miembro donde se almacena el conjunto de resultados de la última consulta.
    // Se declara a nivel de clase por si se quieren añadir otros métodos que lo reutilicen.
    ResultSet rs;

    public void consultarEntradas(DefaultTableModel modelo) {

        // 1. obtener conexión
        Connection con = conexionbd.conect();
        // 2. sentencia SQL (ajusta el nombre de la tabla si es distinto)
        String sql = "SELECT id_entrada  , Fecha, Productos, Provedor, Cantidad, FROM entradas";

        try {
            // 3. crear objeto Statement para ejecutar la query
            Statement st = con.createStatement();
            // 4. ejecutar la consulta y guardar el ResultSet
            rs = st.executeQuery(sql);

            // 5. recorrer las filas devueltas
            while (rs.next()) {
                String id = rs.getString("id");
                String fecha = rs.getString("fecha");
                String producto = rs.getString("producto");
                String proveedor = rs.getString("proveedor");
                int cantidad = rs.getInt("cantidad");
            

                // 6. agregar la fila al modelo de la tabla
                modelo.addRow(new Object[]{
                        id,
                        fecha,
                        producto,
                        proveedor,
                        cantidad,
                        
                });
            }

        } catch (Exception e) {
            // 7. manejo de errores: imprimir traza para localizar el problema
            e.printStackTrace();
        }
        
    }

}