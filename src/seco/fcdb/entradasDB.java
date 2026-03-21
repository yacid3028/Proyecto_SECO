package seco.fcdb;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

public class entradasDB {

    // Llena la tabla con las columnas: ID, FECHA, PRODUCTO, PROVEDOR, CANTIDAD, ESTADO
    public void consultarEntradas(DefaultTableModel modelo) throws Exception {
        modelo.setRowCount(0);
        Connection con = conexionbd.conect();
        String sql = "SELECT id_Entradas, Fecha, Producto, Provedor, Orden FROM Entradas ORDER BY id_Entradas DESC";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        
        while (rs.next()) {
            modelo.addRow(new Object[]{
                rs.getInt("id_Entradas"),
                rs.getString("Fecha"),
                rs.getString("Producto"),
                rs.getString("Provedor"),
                rs.getString("Orden"),
                "Completado" // Estado fijo como en tu diseño
            });
        }
        con.close();
    }

    public boolean agregarEntrada(String producto, String proveedor, int cantidad) throws Exception {
        Connection con = conexionbd.conect();
        String fecha = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        String sql = "INSERT INTO Entradas (Producto, Provedor, Orden, Fecha) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, producto);
        ps.setString(2, proveedor);
        ps.setString(3, String.valueOf(cantidad));
        ps.setString(4, fecha);
        int res = ps.executeUpdate();
        con.close();
        return res > 0;
    }

    public boolean actualizarEntrada(int id, String producto, String proveedor, int cantidad) throws Exception {
        Connection con = conexionbd.conect();
        String sql = "UPDATE Entradas SET Producto=?, Provedor=?, Orden=? WHERE id_Entradas=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, producto);
        ps.setString(2, proveedor);
        ps.setString(3, String.valueOf(cantidad));
        ps.setInt(4, id);
        int res = ps.executeUpdate();
        con.close();
        return res > 0;
    }

    public boolean eliminarEntrada(int id) throws Exception {
        Connection con = conexionbd.conect();
        String sql = "DELETE FROM Entradas WHERE id_Entradas = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        int res = ps.executeUpdate();
        con.close();
        return res > 0;
    }

    // Métodos para las tarjetas superiores
    public int contarEntradasHoy() throws Exception {
        Connection con = conexionbd.conect();
        String hoy = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        String sql = "SELECT COUNT(*) FROM Entradas WHERE Fecha = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, hoy);
        ResultSet rs = ps.executeQuery();
        int total = rs.next() ? rs.getInt(1) : 0;
        con.close();
        return total;
    }

    public int sumarCantidadesTotales() throws Exception {
        Connection con = conexionbd.conect();
        // VAL convierte el texto de 'Orden' a número para sumar
        String sql = "SELECT SUM(VAL(Orden)) FROM Entradas";
        ResultSet rs = con.createStatement().executeQuery(sql);
        int suma = rs.next() ? rs.getInt(1) : 0;
        con.close();
        return suma;
    }
}