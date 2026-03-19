package seco.ventanas;

import javax.swing.*;
import java.awt.*;

public class NuevaOrden extends JFrame {

    JTextField campoProveedor, campoProductos, campoTotal;
    JComboBox<String> estado;
    String idProveedor, productos, total, estadoSeleccionado;

    public JButton Guardar;

    public NuevaOrden() {

        setTitle("Nueva Orden");
        setSize(400,350);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel titulo = new JLabel("Crear Nueva Orden");
        titulo.setFont(new Font("Arial", Font.BOLD,18));
        titulo.setBounds(110,20,200,30);
        add(titulo);

        JLabel proveedor = new JLabel("Proveedor:");
        proveedor.setBounds(40,80,100,25);
        add(proveedor);

        campoProveedor = new JTextField();
        campoProveedor.setBounds(150,80,180,25);
        add(campoProveedor);

        JLabel productos = new JLabel("Productos:");
        productos.setBounds(40,120,100,25);
        add(productos);

        campoProductos = new JTextField();
        campoProductos.setBounds(150,120,180,25);
        add(campoProductos);

        JLabel total = new JLabel("Total:");
        total.setBounds(40,160,100,25);
        add(total);

        campoTotal = new JTextField();
        campoTotal.setBounds(150,160,180,25);
        add(campoTotal);

        JLabel estadoLabel = new JLabel("Estado:");
        estadoLabel.setBounds(40,200,100,25);
        add(estadoLabel);

        estado = new JComboBox<>();
        estado.addItem("Pendiente");
        estado.addItem("En camino");
        estado.addItem("Recibida");
        estado.setBounds(150,200,180,25);
        add(estado);

        JButton guardar = new JButton("Guardar");
        guardar.setBounds(220,250,110,30);
        add(guardar);

        guardar.addActionListener(e -> {
            String proveedorString = campoProveedor.getText();
            String productosString = campoProductos.getText();
            String totalString = campoTotal.getText();
            String estadoSeleccionado = (String) estado.getSelectedItem();

            // Aquí puedes agregar la lógica para guardar la orden en tu sistema
            JOptionPane.showMessageDialog(this, "Orden guardada:\nProveedor: " + proveedorString + "\nProductos: " + productosString + "\nTotal: " + totalString + "\nEstado: " + estadoSeleccionado);
        });

        JButton cancelar = new JButton("Cancelar");
        cancelar.setBounds(80,250,110,30);
        add(cancelar);

        cancelar.addActionListener(e -> dispose());
    }
}