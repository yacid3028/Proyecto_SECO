package seco.ventanas;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class Editar_Reportes extends JFrame {

    JTextField campoIDReporte, campoDescripcion, campoUsuario, campoFecha;
    JComboBox<String> campoCategoria; // 

    public Editar_Reportes() {

        setTitle("Editar Reporte");
        setSize(400,300);
        setLayout(new GridLayout(6,2,10,10));

        campoIDReporte = new JTextField();

        
        campoCategoria = new JComboBox<>(new String[] {
                "S.A.C",
                "Sucursal",
                "S.R.S"
        });

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

        buscar.addActionListener(e -> {
            try {
                ResultSet rs = seco.fcdb.reportesDB.buscarReporte(campoIDReporte.getText());

                if(rs.next()){
                    
                    campoCategoria.setSelectedItem(rs.getString("Categoria"));

                    campoDescripcion.setText(rs.getString("Descripcion"));
                    campoUsuario.setText(rs.getString("Usuario"));
                    campoFecha.setText(rs.getString("Fecha"));
                } else {
                    JOptionPane.showMessageDialog(this, "No encontrado");
                }

            } catch(Exception ex){
                ex.printStackTrace();
            }
        });

        guardar.addActionListener(e -> {

            seco.fcdb.reportesDB.actualizarReporte(
                    campoIDReporte.getText(),
                    campoCategoria.getSelectedItem().toString(), 
                    campoDescripcion.getText(),
                    campoUsuario.getText(),
                    campoFecha.getText()
            );

            JOptionPane.showMessageDialog(this,"Actualizado");
            dispose();
        });
    }
}