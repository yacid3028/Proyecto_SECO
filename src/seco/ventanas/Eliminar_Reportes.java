package seco.ventanas;

import javax.swing.*;
import java.awt.*;
import seco.reportes;

public class Eliminar_Reportes extends JFrame {

    JTextField campoIDReporte;
    private reportes panelReportes;

    public Eliminar_Reportes(reportes panelReportes) {

        this.panelReportes = panelReportes;

        setTitle("Eliminar Reporte");
        setSize(350,200);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel titulo = new JLabel("Eliminar Reporte");
        titulo.setBounds(90,20,200,30);
        add(titulo);

        JLabel idReporte = new JLabel("ID:");
        idReporte.setBounds(40,80,120,25);
        add(idReporte);

        campoIDReporte = new JTextField();
        campoIDReporte.setBounds(150,80,140,25);
        add(campoIDReporte);

        JButton eliminar = new JButton("Eliminar");
        eliminar.setBounds(180,120,100,30);
        add(eliminar);

        eliminar.addActionListener(e -> {

            String id = campoIDReporte.getText();

            seco.fcdb.reportesDB.eliminarReporte(id);
            panelReportes.actualizarTabla();

            JOptionPane.showMessageDialog(this,"Eliminado");
            dispose();
        });

        JButton cancelar = new JButton("Cancelar");
        cancelar.setBounds(60,120,100,30);
        add(cancelar);

        cancelar.addActionListener(e -> dispose());
    }
}