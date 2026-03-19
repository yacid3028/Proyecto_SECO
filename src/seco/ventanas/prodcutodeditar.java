package seco.ventanas;

import java.awt.*;
import javax.swing.*;

public class prodcutodeditar extends JFrame {

    private JComboBox<String> comboCategoria;
    private JTextField txtPrecioVenta;
    private JTextField txtPrecioCompra;
    private JTextField txtStock;

    private String idProducto; 

    public prodcutodeditar(String idProducto) {

        this.idProducto = idProducto;

        setTitle("Editar Producto");
        setSize(420, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Editar Producto");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        add(titulo, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(4, 2, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblCategoria = new JLabel("Categoría:");

        String[] categorias = {
            "Seleccione una categoría",
            "V&L",
            "Higiene",
            "Abarrotes",
            "Bebidas",
            "Limpieza",
            "Papelería",
            "Ferretería",
            "Accesorios",
            "Juguetes",
            "Refrigerados"
        };

        comboCategoria = new JComboBox<>(categorias);

        JLabel lblPrecioVenta = new JLabel("Precio Venta:");
        txtPrecioVenta = new JTextField();
        txtPrecioVenta.setEditable(false); 

        JLabel lblPrecioCompra = new JLabel("Precio Compra:");
        txtPrecioCompra = new JTextField();

        JLabel lblStock = new JLabel("Stock:");
        txtStock = new JTextField();

        panel.add(lblCategoria);
        panel.add(comboCategoria);

        panel.add(lblPrecioVenta);
        panel.add(txtPrecioVenta);

        panel.add(lblPrecioCompra);
        panel.add(txtPrecioCompra);

        panel.add(lblStock);
        panel.add(txtStock);

        add(panel, BorderLayout.CENTER);

        
        txtPrecioCompra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                try {
                    double precioCompra = Double.parseDouble(txtPrecioCompra.getText());
                    double precioVenta = precioCompra * 1.40;

                    txtPrecioVenta.setText(String.format("%.2f", precioVenta));

                } catch (Exception e) {
                    txtPrecioVenta.setText("");
                }
            }
        });

        JPanel botones = new JPanel();

        JButton cancelar = new JButton("Cancelar");
        JButton guardar = new JButton("Guardar");

        guardar.setBackground(new Color(33, 150, 243));
        guardar.setForeground(Color.WHITE);
        guardar.setFocusPainted(false);

        cancelar.addActionListener(e -> dispose());

        guardar.addActionListener(e -> {

            if (comboCategoria.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(null, "Seleccione una categoría válida");
                return;
            }

            try {
                String categoria = (String) comboCategoria.getSelectedItem();

                double precioCompra = Double.parseDouble(txtPrecioCompra.getText());

                
                double precioVenta = precioCompra * 1.40;

                int stock = Integer.parseInt(txtStock.getText());

                seco.fcdb.productosDB.actualizarProducto(
                        idProducto,
                        categoria,
                        precioVenta,
                        precioCompra,
                        stock
                );

                JOptionPane.showMessageDialog(null, "Producto actualizado correctamente");
                dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Ingrese valores numéricos válidos");
            }
        });

        botones.add(cancelar);
        botones.add(guardar);

        add(botones, BorderLayout.SOUTH);
    }
}