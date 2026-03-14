package seco.ventanas;

import javax.swing.*;
import java.awt.*;

public class ProvedoresEditrar extends JFrame {

    public JTextField campoID;
    public JTextField campoNombre;
    public JTextField campoTelefono;
    public JTextField campoEmail;

    public JButton buscar;
    public JButton guardar;
    public JButton cancelar;

    public ProvedoresEditrar(){

        setTitle("Editar Proveedor");
        setSize(420,300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // PANEL PRINCIPAL
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        add(panel);

        // TITULO
        JLabel titulo = new JLabel("Editar Proveedor");
        titulo.setFont(new Font("Arial",Font.BOLD,20));
        panel.add(titulo, BorderLayout.NORTH);

        // PANEL CAMPOS
        JPanel campos = new JPanel(new GridLayout(4,3,10,10));

        campoID = new JTextField();
        campoNombre = new JTextField();
        campoTelefono = new JTextField();
        campoEmail = new JTextField();

        buscar = new JButton("Buscar");

        campos.add(new JLabel("ID"));
        campos.add(campoID);
        campos.add(buscar);

        campos.add(new JLabel("Nombre"));
        campos.add(campoNombre);
        campos.add(new JLabel(""));

        campos.add(new JLabel("Teléfono"));
        campos.add(campoTelefono);
        campos.add(new JLabel(""));

        campos.add(new JLabel("Email"));
        campos.add(campoEmail);
        campos.add(new JLabel(""));

        panel.add(campos, BorderLayout.CENTER);

        // BOTONES
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT));

        guardar = new JButton("Guardar");
        cancelar = new JButton("Cancelar");

        botones.add(cancelar);
        botones.add(guardar);

        panel.add(botones, BorderLayout.SOUTH);

        // BLOQUEAR CAMPOS AL INICIO
        campoNombre.setEnabled(false);
        campoTelefono.setEnabled(false);
        campoEmail.setEnabled(false);

        // ACCION BOTON BUSCAR
        buscar.addActionListener(e -> {

            campoNombre.setEnabled(true);
            campoTelefono.setEnabled(true);
            campoEmail.setEnabled(true);

        });

        cancelar.addActionListener(e -> dispose());
    }
}