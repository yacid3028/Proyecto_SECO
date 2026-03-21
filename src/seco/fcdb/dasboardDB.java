package seco.fcdb;

import java.sql.*;
import java.util.ArrayList;

public class dasboardDB {
    
    private int contadorV = 0;
    private int contadorAB = 0;

    // Método para obtener los 9 valores de la gráfica (Lógica original sin try-catch)
    public int[] obtenerTop9ProductosVendidos() throws Exception {
        ArrayList<String> nombres = new ArrayList<>();
        ArrayList<Integer> totales = new ArrayList<>();

        Connection con = conexionbd.conect();
        // Se mantiene la consulta a la tabla Salidas
        String sql = "SELECT Producto, Cantidad FROM Salidas";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            String prod = rs.getString("Producto");
            int cant = rs.getInt("Cantidad");

            // Lógica para agrupar productos y sumar sus cantidades
            int index = nombres.indexOf(prod);
            if (index != -1) {
                totales.set(index, totales.get(index) + cant);
            } else {
                nombres.add(prod);
                totales.add(cant);
            }
        }
        
        // Cerramos la conexión manualmente como en entradasDB
        con.close();

        // Conversión al array de 9 espacios para la gráfica
        int[] resultado = new int[9];
        
        for (int i = 0; i < 9; i++) {
            if (totales.isEmpty()) {
                resultado[i] = 0; 
            } else {
                int maxVal = -1;
                int maxIdx = -1;
                
                for (int j = 0; j < totales.size(); j++) {
                    if (totales.get(j) > maxVal) {
                        maxVal = totales.get(j);
                        maxIdx = j;
                    }
                }
                
                if (maxIdx != -1) {
                    resultado[i] = maxVal;
                    totales.remove(maxIdx);
                    nombres.remove(maxIdx);
                }
            }
        }

        return resultado;
    }

    public int[] contarCategorias() throws Exception {
        contadorV = 0;
        contadorAB = 0;

        Connection con = conexionbd.conect();
        String sqlSalidas = "SELECT Producto, Cantidad FROM Salidas";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sqlSalidas);

        while (rs.next()) {
            String nombreProd = rs.getString("Producto");
            int cantidad = rs.getInt("Cantidad");
            // Pasamos la conexión abierta para procesar la categoría
            procesarCategoria(con, nombreProd, cantidad);
        }
        
        con.close();

        // Retorna los contadores y rellena con 1 el resto como en tu diseño original
        return new int[]{ contadorV, contadorAB, 1, 1, 1, 1, 1, 1, 1 };
    }

    private void procesarCategoria(Connection con, String producto, int cantidad) throws Exception {
        String sqlProd = "SELECT Categoria FROM Productos WHERE Nombre = ?";
        PreparedStatement pst = con.prepareStatement(sqlProd);
        pst.setString(1, producto);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            String cat = rs.getString("Categoria");
            if (cat != null) {
                if (cat.equalsIgnoreCase("V&L")) {
                    contadorV += cantidad;
                } else if (cat.equalsIgnoreCase("abarrotes")) {
                    contadorAB += cantidad;
                }
            }
        }
        // No cerramos 'con' aquí porque se cierra en contarCategorias()
    }
}