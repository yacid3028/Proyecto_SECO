package seco.ventanas;

import java.awt.*;
import javax.swing.*;

import seco.productos;
import seco.fcdb.productosDB;

public class agregarProducto extends JFrame {

    private JTextField txtID;
    private JTextField txtNombre;
    private JTextField txtPrecioCompra;
    private JComboBox<String> txtCategoria;
    private JTextField txtPrecio;
    private JTextField txtStock;

    public agregarProducto() {

        setTitle("Agregar Producto");
        setSize(450, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Agregar un nuevo producto");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 10));
        add(titulo, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblID = new JLabel("Ingrese ID:");
        txtID = new JTextField();
        txtID.setText(crearRandom()); //
        txtID.setEditable(false); //

        JLabel lblNombre = new JLabel("Nombre:");
        txtNombre = new JTextField();

        JLabel lblPrecioCompra = new JLabel("Precio de compra:");
        txtPrecioCompra = new JTextField();

        JLabel lblCategoria = new JLabel("Categoría:");
        txtCategoria = new JComboBox<>(new String[] {
                "V&L", "Higiene", "Abarrotes", "Bebidas",
                "Limpieza", "Papelería", "Ferretería",
                "Accesorios", "Juguetes", "Refrigerados"
        });

        JLabel lblPrecio = new JLabel("Precio de venta:");
        txtPrecio = new JTextField();
        txtPrecio.setEditable(false);

        JLabel lblStock = new JLabel("Stock:");
        txtStock = new JTextField();

        panel.add(lblID);
        panel.add(txtID);

        panel.add(lblNombre);
        panel.add(txtNombre);

        panel.add(lblPrecioCompra);
        panel.add(txtPrecioCompra);

        panel.add(lblCategoria);
        panel.add(txtCategoria);

        panel.add(lblPrecio);
        panel.add(txtPrecio);

        panel.add(lblStock);
        panel.add(txtStock);

        add(panel, BorderLayout.CENTER);

        txtPrecioCompra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                try {
                    double precioCompra = Double.parseDouble(txtPrecioCompra.getText());
                    double precioVenta = precioCompra * 1.40;

                    txtPrecio.setText(String.format("%.2f", precioVenta));

                } catch (Exception e) {
                    txtPrecio.setText("");
                }
            }
        });

        JPanel botones = new JPanel();

        JButton cancelar = new JButton("Cancelar");
        JButton guardar = new JButton("Guardar");

        guardar.addActionListener(e -> {
            try {
                String id = txtID.getText();
                String nombre = txtNombre.getText();
                String categoria = txtCategoria.getSelectedItem().toString();

                double precioCompra = Double.parseDouble(txtPrecioCompra.getText());

                double precioVenta = precioCompra * 1.40;

                int stock = Integer.parseInt(txtStock.getText());

                productosDB db = new productosDB();
                db.agregarProducto(id, nombre, categoria, precioCompra, precioVenta, stock);

                JOptionPane.showMessageDialog(null, "Producto agregado correctamente");

                dispose();
                productos dr = new productos(null);
                dr.actualizarTabla();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese valores numéricos válidos.");
            }
        });

        guardar.setBackground(new Color(255, 140, 0));
        guardar.setForeground(Color.WHITE);
        guardar.setFocusPainted(false);

        cancelar.addActionListener(e -> dispose());

        botones.add(cancelar);
        botones.add(guardar);

        add(botones, BorderLayout.SOUTH);
    }

    public String crearRandom() {
        return "P" + (int) (Math.random() * 900000 + 100000);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new agregarProducto().setVisible(true);
        });
    }
}