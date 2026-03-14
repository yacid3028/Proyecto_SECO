package seco.ventanas;

import java.awt.*;
import javax.swing.*;

public class prodcutodeditar extends JFrame {

    private JTextField txtDescripcion;
    private JTextField txtPrecio;
    private JTextField txtStock;

    public prodcutodeditar() {

        setTitle("Editar Producto");
        setSize(420,320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        
        JLabel titulo = new JLabel("Editar Producto");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setBorder(BorderFactory.createEmptyBorder(10,20,10,10));

        add(titulo, BorderLayout.NORTH);

        
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        panel.setLayout(new GridLayout(3,2,15,15));

        JLabel lblDescripcion = new JLabel("Descripción:");
        txtDescripcion = new JTextField();

        JLabel lblPrecio = new JLabel("Precio Venta:");
        txtPrecio = new JTextField();

        JLabel lblStock = new JLabel("Stock:");
        txtStock = new JTextField();

        panel.add(lblDescripcion);
        panel.add(txtDescripcion);

        panel.add(lblPrecio);
        panel.add(txtPrecio);

        panel.add(lblStock);
        panel.add(txtStock);

        add(panel, BorderLayout.CENTER);

        
        JPanel botones = new JPanel();

        JButton cancelar = new JButton("Cancelar");
        JButton guardar = new JButton("Guardar");

        guardar.setBackground(new Color(33,150,243));
        guardar.setForeground(Color.WHITE);

        cancelar.addActionListener(e -> dispose());

        botones.add(cancelar);
        botones.add(guardar);

        add(botones, BorderLayout.SOUTH);
    }
}