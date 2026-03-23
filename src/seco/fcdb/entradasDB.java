package seco.fcdb;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import javax.swing.table.DefaultTableModel;
import seco.conexionbd;

public class entradasDB {

    // Genera un ID como A19398
    private String generarIDAleatorio() {
        Random r = new Random();
        int numero = 10000 + r.nextInt(90000); // Genera 5 dígitos
        return "A" + numero;
    }

    // ==========================================
    // AQUÍ ESTÁ EL MÉTODO BASADO EN TU CÓDIGO
    // ==========================================
    public ResultSet obtenerProvedores() {
        Connection con = conexionbd.conect();
        String sql = "SELECT Empresa FROM Provedores";

        try {
            Statement st = con.createStatement();
            return st.executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    // ==========================================

    public void consultarEntradas(DefaultTableModel modelo) {
        try {
            modelo.setRowCount(0);
            Connection con = conexionbd.conect();
            // Se selecciona id_Entradas como String
            String sql = "SELECT id_Entradas, Fecha, Producto, Provedor, Orden FROM Entradas";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                modelo.addRow(new Object[] {
                        rs.getString("id_Entradas"),
                        rs.getString("Fecha"),
                        rs.getString("Producto"),
                        rs.getString("Provedor"),
                        rs.getString("Orden"),
                        "Completado"
                });
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean agregarEntrada(String producto, String proveedor, int cantidad) {
        try {
            Connection con = conexionbd.conect();
            String idRandom = generarIDAleatorio();
            String fecha = new SimpleDateFormat("dd/MM/yyyy").format(new Date());

            String sql = "INSERT INTO Entradas (id_Entradas, Producto, Provedor, Orden, Fecha) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, idRandom);
            ps.setString(2, producto);
            ps.setString(3, proveedor);
            ps.setString(4, String.valueOf(cantidad));
            ps.setString(5, fecha);

            int res = ps.executeUpdate();
            con.close();
            return res > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarEntrada(String id, String producto, String proveedor, int cantidad) {
        try {
            Connection con = conexionbd.conect();
            // El ID se trata como String en el WHERE
            String sql = "UPDATE Entradas SET Producto=?, Provedor=?, Orden=? WHERE id_Entradas=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, producto);
            ps.setString(2, proveedor);
            ps.setString(3, String.valueOf(cantidad));
            ps.setString(4, id);

            int res = ps.executeUpdate();
            con.close();
            return res > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarEntrada(String id) {
        try {
            Connection con = conexionbd.conect();
            // El ID se trata como String en el WHERE
            String sql = "DELETE FROM Entradas WHERE id_Entradas = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, id);

            int res = ps.executeUpdate();
            con.close();
            return res > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int contarEntradasHoy() {
        try {
            Connection con = conexionbd.conect();
            String hoy = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
            String sql = "SELECT COUNT(*) FROM Entradas WHERE Fecha = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, hoy);
            ResultSet rs = ps.executeQuery();
            int total = rs.next() ? rs.getInt(1) : 0;
            con.close();
            return total;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int sumarCantidadesTotales() {
        try {
            Connection con = conexionbd.conect();
            String sql = "SELECT SUM(VAL(Orden)) FROM Entradas";
            ResultSet rs = con.createStatement().executeQuery(sql);
            int suma = rs.next() ? rs.getInt(1) : 0;
            con.close();
            return suma;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}