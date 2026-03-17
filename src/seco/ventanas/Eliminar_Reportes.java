package seco.ventanas;

import javax.swing.*;
import java.awt.*;

public class Eliminar_Reportes extends JFrame {

    JTextField campoIDReporte;

    public Eliminar_Reportes() {

        setTitle("Eliminar Reporte");
        setSize(350,200);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel titulo = new JLabel("Eliminar Reporte");
        titulo.setFont(new Font("Arial", Font.BOLD,18));
        titulo.setBounds(90,20,200,30);
        add(titulo);

        JLabel idReporte = new JLabel("ID del reporte:");
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

            if(id.isEmpty()){
                JOptionPane.showMessageDialog(this, "Ingrese un ID");
                return;
            }

            seco.fcdb.reportesDB.eliminarReporte(id);

            JOptionPane.showMessageDialog(this, "Reporte eliminado correctamente");

            dispose();
        });

        JButton cancelar = new JButton("Cancelar");
        cancelar.setBounds(60,120,100,30);
        add(cancelar);

        cancelar.addActionListener(e -> dispose());
    }
}