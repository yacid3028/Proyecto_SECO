package seco;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import seco.ventanas.Eliminar_Reportes;
import seco.ventanas.Editar_Reportes;
import seco.ventanas.Nuevo_Reporte;

public class reportes extends JPanel {

    private executable executable;
    private JTable tabla;
    private DefaultTableModel modelo;

    public reportes(executable frame) {
        this.executable = frame;
        setLayout(new BorderLayout());
        Menu_lateral();
        contenidoReportes();
    }

    private void Menu_lateral() {

        JPanel p = new JPanel();
        p.setPreferredSize(new Dimension(200, 0));
        p.setBackground(new Color(10, 20, 100));
        p.setLayout(new GridLayout(10, 1, 0, 1));
        add(p, BorderLayout.WEST);

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

        ImageIcon dashi = new ImageIcon("img/casa_icono.jpg");
        Icon dsh = new ImageIcon(dashi.getImage().getScaledInstance(30, 25, Image.SCALE_SMOOTH));

        ImageIcon repos = new ImageIcon("img/reporte_icon.png");
        Icon rps = new ImageIcon(repos.getImage().getScaledInstance(30, 25, Image.SCALE_SMOOTH));

        ImageIcon entrad = new ImageIcon("img/entradas_icon.png");
        Icon etd = new ImageIcon(entrad.getImage().getScaledInstance(30, 25, Image.SCALE_SMOOTH));

        ImageIcon salids = new ImageIcon("img/guayaba.png");
        Icon slds = new ImageIcon(salids.getImage().getScaledInstance(30, 25, Image.SCALE_SMOOTH));

        ImageIcon ordns = new ImageIcon("img/camioncitorunrun.png");
        Icon ord = new ImageIcon(ordns.getImage().getScaledInstance(30, 25, Image.SCALE_SMOOTH));

        ImageIcon vents = new ImageIcon("img/ventas_icon.png");
        Icon vts = new ImageIcon(vents.getImage().getScaledInstance(30, 25, Image.SCALE_SMOOTH));

        ImageIcon provs = new ImageIcon("img/sexo.png");
        Icon prvs = new ImageIcon(provs.getImage().getScaledInstance(30, 25, Image.SCALE_SMOOTH));

        ImageIcon prod = new ImageIcon("img/papaya.png");
        Icon prd = new ImageIcon(prod.getImage().getScaledInstance(30, 25, Image.SCALE_SMOOTH));

        p.add(crearBoton("Dashboard", "dashboard", dsh));
        p.add(crearBoton("Productos", "productos", prd));
        p.add(crearBoton("Entradas", "entradas", etd));
        p.add(crearBoton("Salidas", "salidas", slds));
        p.add(crearBoton("Provedores", "provedores", prvs));
        p.add(crearBoton("Ordenes", "ordenes", ord));
        p.add(crearBoton("Reportes", "reportes", rps));
        p.add(crearBoton("Servicio", "ventas", vts));
    }

    private JButton crearBoton(String texto, String vista, Icon icono) {

        JButton boton = new JButton(texto, icono);

        if (texto.equals("Reportes")) {
            boton.setBackground(new Color(33, 150, 243));
        } else {
            boton.setBackground(new Color(10, 20, 100));
        }

        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setHorizontalAlignment(SwingConstants.LEFT);
        boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        boton.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));

        boton.addActionListener(e -> executable.mostrarVista(vista));

        return boton;
    }

    private void contenidoReportes() {

        JPanel content = new JPanel(new BorderLayout());
        add(content, BorderLayout.CENTER);

        JPanel panelSuperior = new JPanel(new BorderLayout());

        JLabel titulo = new JLabel("Reportes");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        panelSuperior.add(titulo, BorderLayout.WEST);

        JPanel botones = new JPanel();

        JButton nuevo = new JButton("Nuevo Reporte");
        JButton editar = new JButton("Editar Reporte");
        JButton eliminar = new JButton("Eliminar Reporte");

        nuevo.addActionListener(e -> {
            new Nuevo_Reporte(this).setVisible(true);
        });

        editar.addActionListener(e -> {
            new Editar_Reportes().setVisible(true);
        });

        eliminar.addActionListener(e -> {
            new Eliminar_Reportes(this).setVisible(true);
        });

        botones.add(nuevo);
        botones.add(editar);
        botones.add(eliminar);

        panelSuperior.add(botones, BorderLayout.EAST);

        content.add(panelSuperior, BorderLayout.NORTH);

        String columnas[] = { "ID", "Categoria", "Descripción", "Usuario", "Fecha" };

        modelo = new DefaultTableModel(null, columnas);

        seco.fcdb.reportesDB.reportes(modelo);

        tabla = new JTable(modelo);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        content.add(scroll, BorderLayout.CENTER);
    }

    public void actualizarTabla() {
        modelo.setRowCount(0);
        seco.fcdb.reportesDB.reportes(modelo);
    }
}