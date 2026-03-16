package seco.ventanas;

import javax.swing.*;
import java.awt.*;

public class EliminarOrden extends JFrame {

    JTextField campoID;

    public EliminarOrden(){

        setTitle("Eliminar Orden");
        setSize(350,200);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel titulo = new JLabel("Eliminar Orden");
        titulo.setFont(new Font("Arial", Font.BOLD,18));
        titulo.setBounds(110,20,150,30);
        add(titulo);

        JLabel id = new JLabel("ID de Orden:");
        id.setBounds(40,80,100,25);
        add(id);

        campoID = new JTextField();
        campoID.setBounds(140,80,150,25);
        add(campoID);

        JButton eliminar = new JButton("Eliminar");
        eliminar.setBounds(180,120,100,30);
        add(eliminar);

        eliminar.addActionListener(e -> {
            String idOrden = campoID.getText();
            // Aquí puedes agregar la lógica para eliminar la orden de tu sistema
            JOptionPane.showMessageDialog(this, "Orden con ID " + idOrden + " eliminada.");
        });

        JButton cancelar = new JButton("Cancelar");
        cancelar.setBounds(60,120,100,30);
        add(cancelar);

        cancelar.addActionListener(e -> dispose());
    }
}