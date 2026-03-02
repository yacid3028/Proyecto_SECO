package seco;

import javax.swing.JFrame;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;

public class home extends JFrame {

    public home() {

        setTitle("Sistema de Inventario");
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // HEADER 
        JPanel header = new JPanel();
        header.setPreferredSize(new Dimension(0, 60));
        header.add(new JLabel("Productos"));
        add(header, BorderLayout.NORTH);

        // MENU LATERAL 
        JPanel menu = new JPanel();
        menu.setPreferredSize(new Dimension(200, 0));
        menu.setBackground(new Color(10,20,100));
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        

        JButton btn1 = new JButton("Dashboard");
        btn1.setBackground(new Color(10,20,100));
        btn1.setForeground(Color.WHITE);
        btn1.setFocusPainted(false);
        btn1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btn1.setBorder(BorderFactory.createEmptyBorder(8,16,8,16));
        menu.add(btn1);
        JButton btn2 = new JButton("Productos");
        btn2.setBackground(new Color(33,150,243));
        btn2.setForeground(Color.WHITE);
        btn2.setFocusPainted(false);
        btn2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btn2.setBorder(BorderFactory.createEmptyBorder(8,16,8,16));
        menu.add(btn2);
        JButton btn3 = new JButton("Entradas");
        btn3.setBackground(new Color(10,20,100));
        btn3.setForeground(Color.WHITE);
        btn3.setFocusPainted(false);
        btn3.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btn3.setBorder(BorderFactory.createEmptyBorder(8,16,8,16));
        menu.add(btn3);
        JButton btn4 = new JButton("Salidas");
        btn4.setBackground(new Color(10,20,100));
        btn4.setForeground(Color.WHITE);
        btn4.setFocusPainted(false);
        btn4.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btn4.setBorder(BorderFactory.createEmptyBorder(8,16,8,16));
        menu.add(btn4);
        JButton btn5 = new JButton("Reportes");
        btn5.setBackground(new Color(10,20,100));
        btn5.setForeground(Color.WHITE);
        btn5.setFocusPainted(false);
        btn5.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btn5.setBorder(BorderFactory.createEmptyBorder(8,16,8,16));
        menu.add(btn5);

        add(menu, BorderLayout.WEST);
        
        // SUPERIOR CENTRAL
        JPanel superior = new JPanel();
        superior.setLayout(new GridLayout(1, 4, 10, 10));
        superior.add(crearBloques("Stock total","1298","Productos Totales"));
        superior.add(crearBloques("Stock total","1298","Productos Totales"));
        superior.add(crearBloques("Stock total","1298","Productos Totales"));
        superior.add(crearBloques("Stock total","1298","Productos Totales"));
        add(superior,BorderLayout.CENTER);
        

        // CONTENIDO CENTRAL 
        JPanel content = new JPanel();
        content.setLayout(new GridLayout(2, 4, 10, 10));
        content.add(crearBloques("Stock Total", "12450","doble"));
        content.add(crearBloques("Bajo Stock", "28","doble"));
        content.add(crearBloques("Órdenes", "5","doble"));
        content.add(crearBloques("Ventas Hoy", "$12,500","doble"));

        add(content, BorderLayout.CENTER); 
    }

    private JPanel crearBloques(String titulo, String valor, String funcion) {
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JLabel t = new JLabel(titulo);
        JLabel v = new JLabel(valor, SwingConstants.CENTER);

        v.setFont(new Font("Arial", Font.BOLD, 24));
        if(funcion!="doble") {
        	JLabel c = new JLabel(funcion);
        	c.setFont(new Font("Arial",Font.BOLD,12));
        	c.setForeground(new Color(100,100,100));
        	p.add(c, BorderLayout.SOUTH);
        }

        p.add(t, BorderLayout.NORTH);
        p.add(v, BorderLayout.CENTER);

        return p;
    }

    public static void main(String[] args) {
    	try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        new home().setVisible(true);
    }
}
