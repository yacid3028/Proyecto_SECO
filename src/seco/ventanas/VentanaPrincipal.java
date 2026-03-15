package seco.ventanas;

import javax.swing.*;
import java.awt.*;

// Ventana Principal
public class VentanaPrincipal extends JFrame {

    public VentanaPrincipal() {
        setTitle("Menú Principal");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 50));

        JButton btnEstado = new JButton("Cambiar Estado");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnNuevaOrden = new JButton("Nueva Orden");

        btnEstado.addActionListener(e -> {
            CambiarEstado ce = new CambiarEstado();
            ce.setVisible(true);
        });

        btnEliminar.addActionListener(e -> {
            EliminarRegistro er = new EliminarRegistro();
            er.setVisible(true);
        });

        btnNuevaOrden.addActionListener(e -> {
            NuevaOrden no = new NuevaOrden();
            no.setVisible(true);
        });

        add(btnEstado);
        add(btnEliminar);
        add(btnNuevaOrden);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaPrincipal().setVisible(true);
        });
    }
}

// Ventana Cambiar Estado
class CambiarEstado extends JFrame {
    public CambiarEstado() {
        setTitle("Cambiar Estado");
        setSize(420, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Cambiar Estado de Pedido");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        add(titulo, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(2,2,15,15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("ID Pedido:"));
        JTextField txtId = new JTextField();
        panel.add(txtId);

        panel.add(new JLabel("Nuevo Estado:"));
        JTextField txtEstado = new JTextField();
        panel.add(txtEstado);

        add(panel, BorderLayout.CENTER);

        JPanel botones = new JPanel();
        JButton cancelar = new JButton("Cancelar");
        JButton guardar = new JButton("Actualizar");
        cancelar.addActionListener(e -> dispose());
        botones.add(cancelar);
        botones.add(guardar);

        add(botones, BorderLayout.SOUTH);
    }
}

// Ventana Eliminar
class EliminarRegistro extends JFrame {
    public EliminarRegistro() {
        setTitle("Eliminar Registro");
        setSize(420, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Eliminar Registro");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        add(titulo, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(1,2,15,15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(new JLabel("ID a Eliminar:"));
        JTextField txtId = new JTextField();
        panel.add(txtId);

        add(panel, BorderLayout.CENTER);

        JPanel botones = new JPanel();
        JButton cancelar = new JButton("Cancelar");
        JButton eliminar = new JButton("Eliminar");
        cancelar.addActionListener(e -> dispose());
        botones.add(cancelar);
        botones.add(eliminar);

        add(botones, BorderLayout.SOUTH);
    }
}

// Ventana Nueva Orden
class NuevaOrden extends JFrame {
    public NuevaOrden() {
        setTitle("Nueva Orden");
        setSize(420, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Crear Nueva Orden");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        add(titulo, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(3,2,15,15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Producto:"));
        JTextField txtProducto = new JTextField();
        panel.add(txtProducto);

        panel.add(new JLabel("Cantidad:"));
        JTextField txtCantidad = new JTextField();
        panel.add(txtCantidad);

        panel.add(new JLabel("Precio:"));
        JTextField txtPrecio = new JTextField();
        panel.add(txtPrecio);

        add(panel, BorderLayout.CENTER);

        JPanel botones = new JPanel();
        JButton cancelar = new JButton("Cancelar");
        JButton guardar = new JButton("Guardar");
        cancelar.addActionListener(e -> dispose());
        botones.add(cancelar);
        botones.add(guardar);

        add(botones, BorderLayout.SOUTH);
    }
}
