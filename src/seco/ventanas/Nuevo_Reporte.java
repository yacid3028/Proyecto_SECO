package seco.ventanas;

import javax.swing.*;
import java.awt.*;
import seco.reportes;

public class Nuevo_Reporte extends JFrame {

    JTextField campoID, campoCategoria, campoDescripcion, campoUsuario, campoFecha;
    private reportes panelReportes;

    public Nuevo_Reporte(reportes panelReportes) {

        this.panelReportes = panelReportes;

        setTitle("Nuevo Reporte");
        setSize(400,300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6,2,10,10));

        add(new JLabel("ID:"));
        campoID = new JTextField();
        add(campoID);

        add(new JLabel("Categoría:"));
        campoCategoria = new JTextField();
        add(campoCategoria);

        add(new JLabel("Descripción:"));
        campoDescripcion = new JTextField();
        add(campoDescripcion);

        add(new JLabel("Usuario:"));
        campoUsuario = new JTextField();
        add(campoUsuario);

        add(new JLabel("Fecha:"));
        campoFecha = new JTextField();
        add(campoFecha);

        JButton guardar = new JButton("Guardar");
        JButton cancelar = new JButton("Cancelar");

        add(cancelar);
        add(guardar);

        guardar.addActionListener(e -> {

            if(campoID.getText().isEmpty()){
                JOptionPane.showMessageDialog(this,"Ingrese ID");
                return;
            }

            seco.fcdb.reportesDB.agregarReporte(
                    campoID.getText(),
                    campoCategoria.getText(),
                    campoDescripcion.getText(),
                    campoUsuario.getText(),
                    campoFecha.getText()
            );

            panelReportes.actualizarTabla();

            JOptionPane.showMessageDialog(this,"Reporte agregado");
            dispose();
        });

        cancelar.addActionListener(e -> dispose());
    }
}