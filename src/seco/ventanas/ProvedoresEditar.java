package seco.ventanas;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

import seco.provedores;
import seco.fcdb.provedoresDB;

public class ProvedoresEditar extends JFrame {

    public JTextField campoID;
    public JTextField campoEmpresa;
    public JTextField campoTelefono;
    public JTextField campoEmail;
    public JTextField campoProducto;

    public JButton buscar;
    public JButton guardar;
    public JButton cancelar;
    private provedores panel;

    public ProvedoresEditar(provedores panel) {

        this.panel = panel;

        setTitle("Editar Proveedor");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel cont = new JPanel(new BorderLayout(10, 10));
        cont.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(cont);

        JLabel titulo = new JLabel("Editar Proveedor");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        cont.add(titulo, BorderLayout.NORTH);

        // PANEL DE CAMPOS
        JPanel campos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // espacio entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ID + BOTON BUSCAR
        campoID = new JTextField(15);
        buscar = new JButton("Buscar");

        gbc.gridx = 0;
        gbc.gridy = 0;
        campos.add(new JLabel("ID Proveedor:"), gbc);

        gbc.gridx = 1;
        campos.add(campoID, gbc);

        gbc.gridx = 2;
        campos.add(buscar, gbc);

        // Empresa
        campoEmpresa = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 1;
        campos.add(new JLabel("Empresa:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2; // ocupa dos columnas
        campos.add(campoEmpresa, gbc);
        gbc.gridwidth = 1; // reset

        // Teléfono
        campoTelefono = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 2;
        campos.add(new JLabel("Teléfono:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        campos.add(campoTelefono, gbc);
        gbc.gridwidth = 1;

        // Email
        campoEmail = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 3;
        campos.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        campos.add(campoEmail, gbc);
        gbc.gridwidth = 1;

        // Producto
        campoProducto = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 4;
        campos.add(new JLabel("Producto:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        campos.add(campoProducto, gbc);
        gbc.gridwidth = 1;

        cont.add(campos, BorderLayout.CENTER);

        // BOTONES
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        guardar = new JButton("Guardar");
        cancelar = new JButton("Cancelar");

        botones.add(cancelar);
        botones.add(guardar);

        cont.add(botones, BorderLayout.SOUTH);

        // DESACTIVAR CAMPOS AL INICIO
        campoEmpresa.setEnabled(false);
        campoTelefono.setEnabled(false);
        campoEmail.setEnabled(false);
        campoProducto.setEnabled(false);

        provedoresDB db = new provedoresDB();

        // BOTON BUSCAR
        buscar.addActionListener(e -> {
            try {
                ResultSet rs = db.buscarProveedor(campoID.getText());

                if (rs != null && rs.next()) {
                    campoEmpresa.setText(rs.getString("empresa"));
                    campoTelefono.setText(rs.getString("telefono"));
                    campoEmail.setText(rs.getString("email"));
                    campoProducto.setText(rs.getString("producto"));

                    campoEmpresa.setEnabled(true);
                    campoTelefono.setEnabled(true);
                    campoEmail.setEnabled(true);
                    campoProducto.setEnabled(true);

                } else {
                    JOptionPane.showMessageDialog(this, "Proveedor no encontrado");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // BOTON GUARDAR
        guardar.addActionListener(e -> {
            db.actualizarProveedor(
                    campoID.getText(),
                    campoEmpresa.getText(),
                    campoTelefono.getText(),
                    campoEmail.getText(),
                    campoProducto.getText()
            );

            JOptionPane.showMessageDialog(this, "Proveedor actualizado");
            panel.actualizarTabla();
            dispose();
        });

        cancelar.addActionListener(e -> dispose());
    }
}