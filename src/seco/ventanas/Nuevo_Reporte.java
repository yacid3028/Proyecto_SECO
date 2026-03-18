package seco.ventanas;

import javax.swing.*;
import java.awt.*;
import seco.reportes;

public class Nuevo_Reporte extends JFrame {

    JTextField campoID, campoDescripcion, campoUsuario, campoFecha;
    JComboBox<String> campoCategoria;
    private reportes panelReportes;

    public Nuevo_Reporte(reportes panelReportes) {

        this.panelReportes = panelReportes;

        setTitle("Nuevo Reporte");
        setSize(400,300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6,2,10,10));

        // ID
        add(new JLabel("ID:"));
        campoID = new JTextField();
        add(campoID);

        // CATEGORIA (DESPLEGABLE)
        add(new JLabel("Categoría:"));
        campoCategoria = new JComboBox<>(new String[] {
                "S.A.C",
                "Sucursal",
                "S.R.S"
        });
        add(campoCategoria);

        // DESCRIPCION
        add(new JLabel("Descripción:"));
        campoDescripcion = new JTextField();
        add(campoDescripcion);

        // USUARIO
        add(new JLabel("Usuario:"));
        campoUsuario = new JTextField();
        add(campoUsuario);

        // FECHA
        add(new JLabel("Fecha:"));
        campoFecha = new JTextField();
        add(campoFecha);

        // BOTONES
        JButton guardar = new JButton("Guardar");
        JButton cancelar = new JButton("Cancelar");

        add(cancelar);
        add(guardar);

        // 🔥 AUTOLLENADO DE DESCRIPCIÓN
        campoCategoria.addActionListener(e -> {

            String seleccion = campoCategoria.getSelectedItem().toString();

            if(seleccion.equals("S.A.C")){
                campoDescripcion.setText("El cliente se quejó");
            }
            else if(seleccion.equals("Sucursal")){
                campoDescripcion.setText("Salió producto dañado");
            }
            else if(seleccion.equals("S.R.S")){
                campoDescripcion.setText("Inmobiliario dañado");
            }

        });

        // 💾 GUARDAR
        guardar.addActionListener(e -> {

            if(campoID.getText().isEmpty()){
                JOptionPane.showMessageDialog(this,"Ingrese ID");
                return;
            }

            seco.fcdb.reportesDB.agregarReporte(
                    campoID.getText(),
                    campoCategoria.getSelectedItem().toString(),
                    campoDescripcion.getText(),
                    campoUsuario.getText(),
                    campoFecha.getText()
            );

            panelReportes.actualizarTabla();

            JOptionPane.showMessageDialog(this,"Reporte agregado correctamente");

            dispose();
        });

        // CANCELAR
        cancelar.addActionListener(e -> dispose());
    }
}