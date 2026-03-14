package seco.ventanas;

import java.awt.*;
import javax.swing.*;

public class agregarProducto extends JFrame {

    private JTextField txtID;
    private JTextField txtNombre;
    private JTextField txtDescripcion;
    private JTextField txtPrecio;
    private JTextField txtStock;

    public agregarProducto() {

        setTitle("Agregar Producto");
        setSize(450, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Agregar un nuevo producto");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 10));

        add(titulo, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblID = new JLabel("Ingrese ID:");
        txtID = new JTextField();

        JLabel lblNombre = new JLabel("Nombre:");
        txtNombre = new JTextField();

        JLabel lblDescripcion = new JLabel("Descripción:");
        txtDescripcion = new JTextField();

        JLabel lblPrecio = new JLabel("Precio de venta:");
        txtPrecio = new JTextField();

        JLabel lblStock = new JLabel("Stock:");
        txtStock = new JTextField();

        panel.add(lblID);
        panel.add(txtID);

        panel.add(lblNombre);
        panel.add(txtNombre);

        panel.add(lblDescripcion);
        panel.add(txtDescripcion);

        panel.add(lblPrecio);
        panel.add(txtPrecio);

        panel.add(lblStock);
        panel.add(txtStock);

        add(panel, BorderLayout.CENTER);

        // BOTONES
        JPanel botones = new JPanel();

        JButton cancelar = new JButton("Cancelar");
        JButton guardar = new JButton("Guardar");
        guardar.addActionListener(e -> {

            String id = txtID.getText();
            String nombre = txtNombre.getText();
            String descripcion = txtDescripcion.getText();
            double precio = Double.parseDouble(txtPrecio.getText());
            int stock = Integer.parseInt(txtStock.getText());

            seco.fcdb.productosDB.agregarProducto(id, nombre, descripcion, precio, stock);

            JOptionPane.showMessageDialog(null, "Producto agregado correctamente");

            dispose();

        });

        guardar.setBackground(new Color(255, 140, 0));
        guardar.setForeground(Color.WHITE);
        guardar.setFocusPainted(false);

        cancelar.addActionListener(e -> dispose());

        botones.add(cancelar);
        botones.add(guardar);

        add(botones, BorderLayout.SOUTH);
    }

}