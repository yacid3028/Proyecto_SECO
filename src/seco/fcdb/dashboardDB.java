package seco.fcdb;

import java.sql.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import seco.conexionbd;

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

    public int[] obtenerTop9ProductosVendidos() {
        ArrayList<String> nombres = new ArrayList<>();
        ArrayList<Integer> totales = new ArrayList<>();
        int[] resultado = new int[9];

        try (Connection con = conexionbd.conect();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT Producto, Cantidad FROM Salidas")) {

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
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudieron cargar los productos más vendidos.");
        }

        return resultado;
    }

    public int[] contarCategorias() {
        // Reiniciar todos los contadores
        contadorV = 0;
        contadorAB = 0;
        contadorCar = 0;
        contadorLac = 0;
        contadorLim = 0;
        contadorBeb = 0;
        contadorPan = 0;
        contadorMas = 0;
        contadorHig = 0;

        try (Connection con = conexionbd.conect();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT Producto, Cantidad FROM Salidas")) {

            while (rs.next()) {
                String nombreProd = rs.getString("Producto");
                int cantidad = rs.getInt("Cantidad");
                procesarCategoria(con, nombreProd, cantidad);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudieron contar las categorías.");
        }

        // Retorna el array con los 9 contadores para la gráfica de barras
        return new int[] {
                contadorV, contadorAB, contadorCar,
                contadorLac, contadorLim, contadorBeb,
                contadorPan, contadorMas, contadorHig
        };
    }

    private void procesarCategoria(Connection con, String producto, int cantidad) {
        String sqlProd = "SELECT Categoria FROM Productos WHERE Nombre = ?";
        try (PreparedStatement pst = con.prepareStatement(sqlProd)) {
            pst.setString(1, producto);
            try (ResultSet rs = pst.executeQuery()) {
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
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo procesar la categoría del producto: " + producto);
        }
    }

    public void iniciarDia() {
        Connection con = null;
        try {
            con = conexionbd.conect();
            String selectSql = "SELECT Iniciado FROM Config";
            PreparedStatement ps = con.prepareStatement(selectSql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                boolean iniciado = rs.getBoolean("Iniciado");

                if (!iniciado) {
                    String updateSql = "UPDATE Config SET Iniciado = ?";
                    PreparedStatement ups = con.prepareStatement(updateSql);
                    ups.setBoolean(1, true);
                    ups.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Dia iniciado ");

                } else {
                    JOptionPane.showMessageDialog(null, "Dia iniciado ");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean consultarDiaIniciado() {
        Connection con = null;
        try {
            con = conexionbd.conect();
            String selectSql = "SELECT Iniciado FROM Config";
            PreparedStatement ps = con.prepareStatement(selectSql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getBoolean("Iniciado");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Retorna false si no se pudo consultar o si no está iniciado
    }

    public void cerrarDia() {
        Connection con = null;
        try {
            con = conexionbd.conect();

            String selectSql = "SELECT Iniciado, Dia_act, Semana_act FROM Config";
            PreparedStatement ps = con.prepareStatement(selectSql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                boolean iniciado = rs.getBoolean("Iniciado");
                int dia = rs.getInt("Dia_act");
                int semana = rs.getInt("Semana_act");

                if (iniciado) {
                    int nuevoDia;
                    int nuevaSemana;

                    if (dia >= 7) {
                        actualizarPromedios();
                        nuevoDia = 1;
                        nuevaSemana = semana + 1;
                    } else {
                        nuevoDia = dia + 1;
                        nuevaSemana = semana;
                    }

                    String updateSql = "UPDATE Config SET Iniciado = ?, Dia_act = ?, Semana_act = ?";
                    PreparedStatement ups = con.prepareStatement(updateSql);
                    ups.setBoolean(1, false);
                    ups.setInt(2, nuevoDia);
                    ups.setInt(3, nuevaSemana);
                    ups.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Hasta luego.");
                    System.exit(0);

                } else {
                    JOptionPane.showMessageDialog(null, "Inicie el día para cerrar.");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo cerrar el día.");
        }
    }

    public void cargarOrdenesPendientes(DefaultTableModel modelo) {
        int contador = 0;
        Connection con = conexionbd.conect();
        String sql = "SELECT id_Odenes, Provedor, Costo FROM Ordenes WHERE Estado = 'Pendiente' OR Estado = 'En Camino'";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                if (contador >= 5) {
                    break;

                } else {
                    String id = rs.getString("id_Odenes");
                    String provedor = rs.getString("Provedor");
                    double costo = rs.getDouble("Costo");
                    modelo.addRow(new Object[] { id, provedor, costo });
                    contador++;
                }

            }
        } catch (Exception e) {
            System.out.println("No se pudieron cargar las ordenes pendientes." + e.getMessage());
        }

    }

    public void cargarBajoStock(DefaultTableModel modelo) {
        int contador = 0;
        try (Connection con = conexionbd.conect();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT id_Productos, Nombre, Stock FROM Productos ")) {

            while (rs.next()) {
                if (rs.getInt("Stock") < 8 && contador < 5) {
                    String id = rs.getString("id_Productos");
                    String nombre = rs.getString("Nombre");
                    int stock = rs.getInt("Stock");
                    modelo.addRow(new Object[] { id, nombre, stock });
                    contador++;
                }
            }
        } catch (SQLException e) {
            System.out.println("No se pudieron cargar las alertas de bajo stock." + e.getMessage());
        }
    }

    public void UltimosMovimientos(DefaultTableModel modelo) {
        int contador = 0;
        try (Connection con = conexionbd.conect();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(
                        "SELECT id_Venta, Producto, Fecha, Cantidad FROM Salidas ORDER BY id_Venta DESC LIMIT 5")) {

            while (rs.next()) {
                if (contador >= 5) {
                    break;
                } else {
                    String id = rs.getString("id_Venta");
                    String producto = rs.getString("Producto");
                    String fecha = rs.getString("Fecha");
                    int cantidad = rs.getInt("Cantidad");
                    modelo.addRow(new Object[] { fecha, id, producto, cantidad });
                    contador++;
                }

            }
        } catch (SQLException e) {
            System.out.println("No se pudieron cargar las últimas ventas." + e.getMessage());
        }
    }

    public void cargarAlertasBajoStock() {
        int contador = 0;
        try (Connection con = conexionbd.conect();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM Alertas ")) {
            while (rs.next()) {
                String producto = rs.getString("Nombre");
                int cantidad = rs.getInt("Cantidad");
                boolean Estatus = rs.getBoolean("Estatus");
                if (!Estatus) {
                    JOptionPane.showMessageDialog(null,
                            "Alerta: El producto '" + producto
                                    + "' está bajo stock.\nCantidad recomendada para reordenar: " + cantidad);
                    contador++;
                }

            }
            if (contador == 0) {
                JOptionPane.showMessageDialog(null, "No hay alertas de bajo stock en este momento.");
            }

        } catch (SQLException e) {
            System.out.println("No se pudieron cargar las alertas de bajo stock." + e.getMessage());
        }
    }

    private void actualizarPromedios() {
        try (Connection con = conexionbd.conect();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT Producto,Cantidad, Semana FROM Salidas")) {

            while (rs.next()) {
                String nombre = rs.getString("Producto");
                int cantidad = rs.getInt("Cantidad");
                int semana = rs.getInt("Semana");

                insertarPromedio(nombre, cantidad, semana);
            }
        } catch (Exception e) {
            System.out.println("Error al actualizar promedios de ventas");
            e.printStackTrace();
        }
    }

    private void insertarPromedio(String nombre, int cantidad, int semana) {
        try (Connection con = conexionbd.conect();
                Statement st = con.createStatement()) {

            int semanaActual = conaultaSem();
            if (semana == semanaActual) {
                String sql = "UPDATE Promedio SET Vendidos = " + cantidad + ", Promedio = " + (cantidad / 7)
                        + " WHERE Producto = '" + nombre + "'";
                st.executeUpdate(sql);
            }

        } catch (Exception e) {
            System.out.println("Error al insertar producto en tabla de promedio");
            e.printStackTrace();
        }

    }

    private int conaultaSem() {
        try (Connection con = conexionbd.conect();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT Semana_act FROM Config")) {

            if (rs.next()) {
                return rs.getInt("Semana_act");
            }
        } catch (Exception e) {
            System.out.println("Error al consultar semana actual");
            e.printStackTrace();
        }
        return -1; // Retorna -1 si no se pudo obtener la semana actual
    }
}