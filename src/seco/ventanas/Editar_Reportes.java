package seco.ventanas;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class Editar_Reportes extends JFrame {

    JTextField campoIDReporte, campoCategoria, campoDescripcion, campoUsuario, campoFecha;
    JButton buscar, guardar, cancelar;

    public Editar_Reportes() {

        setTitle("Editar Reporte");
        setSize(450,320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout(10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
        add(panel);

        JLabel titulo = new JLabel("Editar Reporte", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial",Font.BOLD,20));
        panel.add(titulo, BorderLayout.NORTH);

        JPanel campos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        campoIDReporte = new JTextField();
        campoCategoria = new JTextField();
        campoDescripcion = new JTextField();
        campoUsuario = new JTextField();
        campoFecha = new JTextField();

        buscar = new JButton("Buscar");

        gbc.gridx=0; gbc.gridy=0;
        campos.add(new JLabel("ID:"), gbc);
        gbc.gridx=1;
        campos.add(campoIDReporte, gbc);
        gbc.gridx=2;
        campos.add(buscar, gbc);

        gbc.gridx=0; gbc.gridy=1;
        campos.add(new JLabel("Categoría:"), gbc);
        gbc.gridx=1; gbc.gridwidth=2;
        campos.add(campoCategoria, gbc);

        gbc.gridx=0; gbc.gridy=2;
        campos.add(new JLabel("Descripción:"), gbc);
        gbc.gridx=1;
        campos.add(campoDescripcion, gbc);

        gbc.gridx=0; gbc.gridy=3;
        campos.add(new JLabel("Usuario:"), gbc);
        gbc.gridx=1;
        campos.add(campoUsuario, gbc);

        gbc.gridx=0; gbc.gridy=4;
        campos.add(new JLabel("Fecha:"), gbc);
        gbc.gridx=1;
        campos.add(campoFecha, gbc);

        panel.add(campos, BorderLayout.CENTER);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        guardar = new JButton("Guardar");
        cancelar = new JButton("Cancelar");

        botones.add(cancelar);
        botones.add(guardar);

        panel.add(botones, BorderLayout.SOUTH);

        // Bloquear campos
        campoCategoria.setEnabled(false);
        campoDescripcion.setEnabled(false);
        campoUsuario.setEnabled(false);
        campoFecha.setEnabled(false);

        // 🔍 BUSCAR
        buscar.addActionListener(e -> {

            String id = campoIDReporte.getText();

            if(id.isEmpty()){
                JOptionPane.showMessageDialog(this,"Ingrese ID");
                return;
            }

            try {

                ResultSet rs = seco.fcdb.reportesDB.buscarReporte(id);

                if(rs != null && rs.next()){

                    campoCategoria.setText(rs.getString("Categoria"));
                    campoDescripcion.setText(rs.getString("Descripcion"));
                    campoUsuario.setText(rs.getString("Usuario"));
                    campoFecha.setText(rs.getString("Fecha"));

                    campoCategoria.setEnabled(true);
                    campoDescripcion.setEnabled(true);
                    campoUsuario.setEnabled(true);
                    campoFecha.setEnabled(true);

                } else {
                    JOptionPane.showMessageDialog(this,"No encontrado");
                }

            } catch(Exception ex){
                ex.printStackTrace();
            }
        });

        // 💾 GUARDAR
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

        cancelar.addActionListener(e -> dispose());
    }
}