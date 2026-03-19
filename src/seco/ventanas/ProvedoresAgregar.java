package seco.ventanas;

import javax.swing.*;
import java.awt.*;

import seco.provedores;
import seco.fcdb.provedoresDB;

public class ProvedoresAgregar extends JFrame {

    private provedores panel; // referencia al panel principal

    public JTextField campoID_Provedor;
    public JTextField campoEmpresa;
    public JTextField campoTelefono;
    public JTextField campoEmail;
    public JTextField campoProducto;

    public JButton guardar;
    public JButton cancelar;

    public ProvedoresAgregar(provedores panel) {

        this.panel = panel; // guardamos la referencia

        setTitle("Nuevo Proveedor");
        setSize(420,330);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel contenedor = new JPanel();
        contenedor.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        contenedor.setLayout(new BorderLayout());

        add(contenedor);

        // TITULO
        JLabel titulo = new JLabel("Nuevo Proveedor");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        contenedor.add(titulo, BorderLayout.NORTH);

        // PANEL CENTRAL
        JPanel centro = new JPanel(new GridLayout(5,2,10,10));

        JLabel id_Provedor = new JLabel("ID Proveedor:");
        JLabel empresa = new JLabel("Empresa:");
        JLabel telefono = new JLabel("Teléfono:");
        JLabel email = new JLabel("Email:");
        JLabel producto = new JLabel("Producto:");

        campoID_Provedor = new JTextField();
        campoEmpresa = new JTextField();
        campoTelefono = new JTextField();
        campoEmail = new JTextField();
        campoProducto = new JTextField();

        campoID_Provedor.setText(generarID());
        campoID_Provedor.setEditable(false);

        centro.add(id_Provedor);
        centro.add(campoID_Provedor);

        centro.add(empresa);
        centro.add(campoEmpresa);

        centro.add(telefono);
        centro.add(campoTelefono);

        centro.add(email);
        centro.add(campoEmail);

        centro.add(producto);
        centro.add(campoProducto);

        contenedor.add(centro, BorderLayout.CENTER);

        // BOTONES
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));

        cancelar = new JButton("Cancelar");
        cancelar.setBackground(Color.LIGHT_GRAY);

        guardar = new JButton("Guardar");
        guardar.setBackground(new Color(33,150,243));
        guardar.setForeground(Color.WHITE);

        botones.add(cancelar);
        botones.add(guardar);

        contenedor.add(botones, BorderLayout.SOUTH);

        guardar.addActionListener(e -> {

            String id_ProvTxt = campoID_Provedor.getText().trim();
            String empresaTxt = campoEmpresa.getText().trim();
            String telefonoTxt = campoTelefono.getText().trim();
            String emailTxt = campoEmail.getText().trim();
            String productoTxt = campoProducto.getText().trim();

            if (empresaTxt.isEmpty() || telefonoTxt.isEmpty()
                    || emailTxt.isEmpty() || productoTxt.isEmpty()) {

                JOptionPane.showMessageDialog(this,
                        "Por favor, completa todos los campos.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            provedoresDB db = new provedoresDB();
            db.insertarProveedor(
                    id_ProvTxt,
                    empresaTxt,
                    telefonoTxt,
                    emailTxt,
                    productoTxt
            );

            JOptionPane.showMessageDialog(this, "Proveedor guardado exitosamente.");

            panel.actualizarTabla(); // refresca la tabla

            dispose();
        });

        cancelar.addActionListener(e -> dispose());
    }

    private String generarID() {
        String valrey = "Q";
        for (int i = 0; i < 6; i++) {
            int random = (int) (Math.random() * 10);
            valrey += random;
        }
        return valrey;
    }
}