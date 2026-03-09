package seco;

import java.awt.*;
import javax.swing.*;

public class reportes extends JPanel {
    private executable executable;

    public reportes(executable frame) {
        this.executable = frame;
        setLayout(new BorderLayout());

        Menu_lateral();
        

    }

    private void Menu_lateral() {
        JPanel p = new JPanel();
        p.setPreferredSize(new Dimension(200, 0));
        p.setBackground(new Color(10, 20, 100));
        p.setLayout(new GridLayout(10, 1, 0, 1));
        add(p, BorderLayout.WEST);

        // Logo original
        ImageIcon lg = new ImageIcon("img/logo.png");
        Icon in = new ImageIcon(lg.getImage().getScaledInstance(70, 60, Image.SCALE_SMOOTH));
        JButton titulo = new JButton("Stock System", in);
        titulo.setHorizontalTextPosition(SwingConstants.RIGHT);
        titulo.setVerticalTextPosition(SwingConstants.CENTER);
        titulo.setBackground(new Color(0, 0, 0, 0));
        titulo.setFocusPainted(false);
        titulo.setBorder(null);
        titulo.setForeground(Color.white);
        titulo.setFont(new Font("Arial", Font.ITALIC, 15));
        p.add(titulo);

        // Icono casa original
        ImageIcon dashi = new ImageIcon("img/casa_icono.jpg");
        Icon dsh = new ImageIcon(dashi.getImage().getScaledInstance(30, 25, Image.SCALE_SMOOTH));
        
        p.add(crearBoton("Dashboard", "dashboard", dsh));
        p.add(crearBoton("Productos", "productos", dsh));
        p.add(crearBoton("Entradas", "entradas", dsh));
        p.add(crearBoton("Salidas", "salidas", dsh));
        p.add(crearBoton("Provedores", "provedores", dsh));
        p.add(crearBoton("Ordenes", "ordenes", dsh));
        p.add(crearBoton("Reportes", "reportes", dsh)); // Este se pintará azul ahora
        p.add(crearBoton("Servicio", "ventas", dsh));
    }

    private JButton crearBoton(String texto, String vista, Icon icono) {
        JButton boton = new JButton(texto, icono);
        
        // ERROR CORREGIDO: Usar .equals() y marcar "Reportes" como activo
        if (texto.equals("Reportes")) { 
            boton.setBackground(new Color(33, 150, 243)); // Azul activo
        } else {
            boton.setBackground(new Color(10, 20, 100));
        }
        
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setHorizontalAlignment(SwingConstants.LEFT);
        boton.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        boton.addActionListener(e -> executable.mostrarVista(vista));
        return boton;
    }

}

