package seco.ventanas;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import seco.fcdb.ordenesDB;
import seco.fcdb.provedoresDB;
import seco.ordenes; // panel principal con tabla

public class NuevaOrden extends JFrame {

    JTextField campoTotal, campoCantidad;
    JComboBox<String> estado, Provedor, Productos;
    private ordenes panelPrincipal;

    public NuevaOrden(ordenes panelPrincipal) {

        this.panelPrincipal = panelPrincipal;

        setTitle("Nueva Orden");
        setSize(450, 400);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel titulo = new JLabel("Crear Nueva Orden");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setBounds(120, 20, 250, 30);
        add(titulo);

        JLabel proveedorLabel = new JLabel("Proveedor:");
        proveedorLabel.setBounds(40, 70, 100, 25);
        add(proveedorLabel);

        Provedor = new JComboBox<>();
        Provedor.setBounds(150, 70, 200, 25);
        add(Provedor);

        JLabel productosLabel = new JLabel("Producto:");
        productosLabel.setBounds(40, 110, 100, 25);
        add(productosLabel);

        ordenesDB ds = new ordenesDB();

        Productos = new JComboBox<>();
        String[] productos = ds.cargarProductos();
        for (String producto : productos) {
            Productos.addItem(producto);
        }
        Productos.setBounds(150, 110, 200, 25);
        add(Productos);

        JLabel cantidadLabel = new JLabel("Cantidad:");
        cantidadLabel.setBounds(40, 150, 100, 25);
        add(cantidadLabel);

        campoCantidad = new JTextField();
        campoCantidad.setBounds(150, 150, 200, 25);
        add(campoCantidad);

        JLabel totalLabel = new JLabel("Costo:");
        totalLabel.setBounds(40, 190, 100, 25);
        add(totalLabel);

        campoTotal = new JTextField();
        campoTotal.setBounds(150, 190, 200, 25);
        add(campoTotal);

        JLabel estadoLabel = new JLabel("Estado:");
        estadoLabel.setBounds(40, 230, 100, 25);
        add(estadoLabel);

        estado = new JComboBox<>();
        estado.addItem("Pendiente");
        estado.addItem("En camino");
        estado.addItem("Recibida");
        estado.setBounds(150, 230, 200, 25);
        add(estado);

        JButton guardar = new JButton("Guardar");
        guardar.setBounds(230, 280, 120, 30);
        add(guardar);

        JButton cancelar = new JButton("Cancelar");
        cancelar.setBounds(80, 280, 120, 30);
        add(cancelar);

        cargarProvedores();
        cargarProvedores();
        // Acción del botón guardar
        guardar.addActionListener(e -> {
            String proveedorSeleccionado = (String) Provedor.getSelectedItem();
            String productoSeleccionado = (String) Productos.getSelectedItem();
            int cantidad = Integer.parseInt(campoCantidad.getText().trim());
            double costo = Double.parseDouble(campoTotal.getText().trim());
            String estadoSeleccionado = (String) estado.getSelectedItem();

            if (proveedorSeleccionado.isEmpty() || productoSeleccionado.isEmpty() || cantidad == 0 || costo == 0) {
                JOptionPane.showMessageDialog(this, "Por favor completa todos los campos.");
                return;
            }

            try {

                String id = generarID();

                String fecha = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

                if (proveedorSeleccionado == null || productoSeleccionado == null
                        || campoCantidad.getText().isEmpty()
                        || campoTotal.getText().isEmpty()) {

                    JOptionPane.showMessageDialog(this, "Por favor completa todos los campos.");
                    return;
                } else {
                    ordenesDB db = new ordenesDB();

                    db.insertarOrden(id, proveedorSeleccionado, productoSeleccionado, costo, cantidad, fecha,
                            estadoSeleccionado);

                    JOptionPane.showMessageDialog(this, "Orden guardada correctamente.");
                }

                dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al guardar la orden.");
            }
        });

        cancelar.addActionListener(e -> dispose());
    }

    private String generarID() {
        String valrey = "O";
        for (int i = 0; i < 6; i++) {
            int random = (int) (Math.random() * 10);
            valrey += random;
        }
        return valrey;
    }

    private void cargarProvedores() {
        try {
            ResultSet rs = new provedoresDB().obtenerProvedores();
            Provedor.removeAllItems();

            while (rs.next()) {
                Provedor.addItem(rs.getString("Empresa"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar proveedores");
        }
    }

}
