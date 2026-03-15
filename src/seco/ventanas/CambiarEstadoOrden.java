package seco.ventanas;

import javax.swing.*;
import java.awt.*;

public class CambiarEstadoOrden extends JFrame {

    JTextField campoID;
    JComboBox<String> estado;

    public CambiarEstadoOrden(){

        setTitle("Cambiar Estado de Orden");
        setSize(380,230);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel titulo = new JLabel("Cambiar Estado");
        titulo.setFont(new Font("Arial", Font.BOLD,18));
        titulo.setBounds(120,20,200,30);
        add(titulo);

        JLabel id = new JLabel("ID Orden:");
        id.setBounds(40,80,100,25);
        add(id);

        campoID = new JTextField();
        campoID.setBounds(140,80,170,25);
        add(campoID);

        JLabel estadoLabel = new JLabel("Nuevo Estado:");
        estadoLabel.setBounds(40,120,100,25);
        add(estadoLabel);

        estado = new JComboBox<>();
        estado.addItem("Pendiente");
        estado.addItem("En camino");
        estado.addItem("Recibida");

        estado.setBounds(140,120,170,25);
        add(estado);

        JButton guardar = new JButton("Actualizar");
        guardar.setBounds(200,160,110,30);
        add(guardar);

            guardar.addActionListener(e -> {
                String idOrden = campoID.getText();
                String estadoSeleccionado = (String) estado.getSelectedItem();
    
                // Aquí puedes agregar la lógica para actualizar el estado de la orden en tu sistema
                JOptionPane.showMessageDialog(this, "Orden ID: " + idOrden + " actualizada a estado: " + estadoSeleccionado);
            });

        JButton cancelar = new JButton("Cancelar");
        cancelar.setBounds(70,160,110,30);
        add(cancelar);

            cancelar.addActionListener(e -> {
                dispose(); 
            });
    }
}