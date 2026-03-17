package seco.ventanas;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class Editar_Reportes extends JFrame {

    JTextField campoIDReporte, campoCategoria, campoDescripcion, campoUsuario, campoFecha;

    public Editar_Reportes() {

        setTitle("Editar Reporte");
        setSize(400,300);
        setLayout(new GridLayout(6,2));

        campoIDReporte = new JTextField();
        campoCategoria = new JTextField();
        campoDescripcion = new JTextField();
        campoUsuario = new JTextField();
        campoFecha = new JTextField();

        JButton buscar = new JButton("Buscar");
        JButton guardar = new JButton("Guardar");

        add(new JLabel("ID:")); add(campoIDReporte);
        add(new JLabel("Categoría:")); add(campoCategoria);
        add(new JLabel("Descripción:")); add(campoDescripcion);
        add(new JLabel("Usuario:")); add(campoUsuario);
        add(new JLabel("Fecha:")); add(campoFecha);
        add(buscar); add(guardar);

        buscar.addActionListener(e -> {
            try {
                ResultSet rs = seco.fcdb.reportesDB.buscarReporte(campoIDReporte.getText());
                if(rs.next()){
                    campoCategoria.setText(rs.getString("Categoria"));
                    campoDescripcion.setText(rs.getString("Descripcion"));
                    campoUsuario.setText(rs.getString("Usuario"));
                    campoFecha.setText(rs.getString("Fecha"));
                }
            } catch(Exception ex){
                ex.printStackTrace();
            }
        });

        guardar.addActionListener(e -> {
            seco.fcdb.reportesDB.actualizarReporte(
                    campoIDReporte.getText(),
                    campoCategoria.getText(),
                    campoDescripcion.getText(),
                    campoUsuario.getText(),
                    campoFecha.getText()
            );

            JOptionPane.showMessageDialog(this,"Actualizado");
            dispose();
        });
    }
}