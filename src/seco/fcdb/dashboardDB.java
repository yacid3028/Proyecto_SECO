package seco.fcdb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class dashboardDB {
    public int[] obtenerTop9ProductosVendidos() {
        int[] valores = new int[9];

        try {
            Connection conexion = conexionbd.conect();

            if (conexion != null) {
                // Consulta que obtiene los 9 productos más vendidos
                String consulta = "SELECT TOP 9 p.ID_PRODUCTO, SUM(v.CANTIDAD) AS TOTAL_VENDIDO " +
                        "FROM Salidas v " +
                        "INNER JOIN PRODUCTOS p ON v.ID_PRODUCTO = p.ID_PRODUCTO " +
                        "GROUP BY p.ID_PRODUCTO " +
                        "ORDER BY TOTAL_VENDIDO DESC";

                Statement stmt = conexion.createStatement();
                ResultSet rs = stmt.executeQuery(consulta);

                int index = 0;
                while (rs.next() && index < 9) {
                    valores[index] = rs.getInt("TOTAL_VENDIDO");
                    index++;
                }

                conexion.close();
            }
        } catch (Exception e) {
            System.out.println("Error al obtener top 9 productos: " + e.getMessage());
            e.printStackTrace();
        }

        return valores;
    }
}
