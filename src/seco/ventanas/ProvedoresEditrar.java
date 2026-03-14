package seco.ventanas;

import javax.swing.*;
import java.awt.*;
import seco.fcdb.provedoresDB;

public class ProvedoresEditrar extends JFrame {
    public JTextField campoID;
    public JTextField campoNombre;
    public JTextField campoTelefono;
    public JTextField campoEmail;

    public JButton buscar;
    public JButton guardar;
    public JButton cancelar;

    public void ProvedoresEditar(){

        setTitle("Editar Proveedor");
        setSize(420,320);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel contenedor = new JPanel();
        contenedor.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        contenedor.setLayout(new BorderLayout());

        add(contenedor);

        // TITULO
        JLabel titulo = new JLabel("Editar Proveedor");
        titulo.setFont(new Font("Arial",Font.BOLD,20));
        contenedor.add(titulo,BorderLayout.NORTH);

        // PANEL CAMPOS
        JPanel centro = new JPanel(new GridLayout(4,3,10,10));

        JLabel id = new JLabel("ID:");
        JLabel nombre = new JLabel("Nombre:");
        JLabel telefono = new JLabel("Teléfono:");
        JLabel email = new JLabel("Email:");

        campoID = new JTextField();
        campoNombre = new JTextField();
        campoTelefono = new JTextField();
        campoEmail = new JTextField();

        buscar = new JButton("Buscar");

        centro.add(id);
        centro.add(campoID);
        centro.add(buscar);

        centro.add(nombre);
        centro.add(campoNombre);
        centro.add(new JLabel(""));

        centro.add(telefono);
        centro.add(campoTelefono);
        centro.add(new JLabel(""));

        centro.add(email);
        centro.add(campoEmail);
        centro.add(new JLabel(""));

        contenedor.add(centro,BorderLayout.CENTER);

        // BOTONES
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));

        cancelar = new JButton("Cancelar");
        cancelar.setBackground(Color.LIGHT_GRAY);

        guardar = new JButton("Guardar");
        guardar.setBackground(new Color(33,150,243));
        guardar.setForeground(Color.WHITE);

        botones.add(cancelar);
        botones.add(guardar);

        contenedor.add(botones,BorderLayout.SOUTH);

        cancelar.addActionListener(e -> dispose());
    }

    
}
