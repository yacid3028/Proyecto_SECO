package seco.fcdb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import seco.conexionbd;

public class dashboardDB {

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

    public void cerrarDia() {
        Connection con = null;
        try {
            con = conexionbd.conect();

            String selectSql = "SELECT Iniciado, Dia, Semana FROM Config";
            PreparedStatement ps = con.prepareStatement(selectSql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                boolean iniciado = rs.getBoolean("Iniciado");
                int dia = rs.getInt("Dia");
                int semana = rs.getInt("Semana");

                if (iniciado) {
                    int nuevoDia;
                    int nuevaSemana;

                    if (dia >= 7) {
                        nuevoDia = 1;
                        nuevaSemana = semana + 1;
                    } else {
                        nuevoDia = dia + 1;
                        nuevaSemana = semana;
                    }

                    String updateSql = "UPDATE Config SET Iniciado = ?, Dia = ?, Semana = ?";
                    PreparedStatement ups = con.prepareStatement(updateSql);
                    ups.setBoolean(1, false);
                    ups.setInt(2, nuevoDia);
                    ups.setInt(3, nuevaSemana);
                    ups.executeUpdate();

                } else {
                    JOptionPane.showMessageDialog(null, "Inicie el día para cerrar.");
                }
            }
        } catch (Exception e) {

        }
    }
}