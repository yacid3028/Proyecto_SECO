package seco.fcdb;

import java.sql.*;
import java.util.ArrayList;
import seco.fcdb.conexionbd;

public class dashboardDB {
    
    private int contadorV = 0;
    private int contadorAB = 0;
    private int contadorCar = 0;
    private int contadorLac = 0;
    private int contadorLim = 0;
    private int contadorBeb = 0;
    private int contadorPan = 0;
    private int contadorMas = 0;
    private int contadorHig = 0;

    public int[] obtenerTop9ProductosVendidos() throws Exception {
        ArrayList<String> nombres = new ArrayList<>();
        ArrayList<Integer> totales = new ArrayList<>();

        Connection con = conexionbd.conect();
        String sql = "SELECT Producto, Cantidad FROM Salidas";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            String prod = rs.getString("Producto");
            int cant = rs.getInt("Cantidad");

            int index = nombres.indexOf(prod);
            if (index != -1) {
                totales.set(index, totales.get(index) + cant);
            } else {
                nombres.add(prod);
                totales.add(cant);
            }
        }
        
        con.close();

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
        // Reiniciar todos los contadores
        contadorV = 0; contadorAB = 0; contadorCar = 0;
        contadorLac = 0; contadorLim = 0; contadorBeb = 0;
        contadorPan = 0; contadorMas = 0; contadorHig = 0;

        Connection con = conexionbd.conect();
        String sqlSalidas = "SELECT Producto, Cantidad FROM Salidas";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sqlSalidas);

        while (rs.next()) {
            String nombreProd = rs.getString("Producto");
            int cantidad = rs.getInt("Cantidad");
            procesarCategoria(con, nombreProd, cantidad);
        }
        
        con.close();

        // Retorna el array con los 9 contadores para la gráfica de barras
        return new int[]{ 
            contadorV, contadorAB, contadorCar, 
            contadorLac, contadorLim, contadorBeb, 
            contadorPan, contadorMas, contadorHig 
        };
    }

    private void procesarCategoria(Connection con, String producto, int cantidad) throws Exception {
        String sqlProd = "SELECT Categoria FROM Productos WHERE Nombre = ?";
        PreparedStatement pst = con.prepareStatement(sqlProd);
        pst.setString(1, producto);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            String cat = rs.getString("Categoria");
            if (cat != null) {
                String c = cat.toLowerCase();
                
                if (c.equals("v&l")) {
                    contadorV += cantidad;
                } else if (c.equals("abarrotes")) {
                    contadorAB += cantidad;
                } else if (c.equals("carnes")) {
                    contadorCar += cantidad;
                } else if (c.equals("lacteos") || c.equals("lácteos")) {
                    contadorLac += cantidad;
                } else if (c.equals("limpieza")) {
                    contadorLim += cantidad;
                } else if (c.equals("bebidas")) {
                    contadorBeb += cantidad;
                } else if (c.equals("panaderia") || c.equals("panadería")) {
                    contadorPan += cantidad;
                } else if (c.equals("mascotas")) {
                    contadorMas += cantidad;
                } else if (c.equals("higiene")) {
                    contadorHig += cantidad;
                }
            }
        }
    }
}