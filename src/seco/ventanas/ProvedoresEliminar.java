package seco.ventanas;

import javax.swing.*;
import java.awt.*;

public class ProvedoresEliminar extends JFrame {

    JTextField campoID;

    public ProvedoresEliminar(){

        setTitle("Eliminar Proveedor");
        setSize(350,200);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel titulo = new JLabel("Eliminar Proveedor");
        titulo.setFont(new Font("Arial", Font.BOLD,18));
        titulo.setBounds(110,20,150,30);
        add(titulo);

        JLabel id = new JLabel("ID de Proveedor:");
        id.setBounds(40,80,120,25);
        add(id);

        campoID = new JTextField();
        campoID.setBounds(160,80,150,25);
        add(campoID);

        JButton eliminar = new JButton("Eliminar");
        eliminar.setBounds(180,120,100,30);
        add(eliminar);

        eliminar.addActionListener(e -> {
            String idProvedor = campoID.getText();
            // Aquí puedes agregar la lógica para eliminar el proveedor de tu sistema
            JOptionPane.showMessageDialog(this, "Provedor con ID " + idProvedor + " eliminado.");
        });

        JButton cancelar = new JButton("Cancelar");
        cancelar.setBounds(60,120,100,30);
        add(cancelar);

        cancelar.addActionListener(e -> dispose());
    }
    
}
