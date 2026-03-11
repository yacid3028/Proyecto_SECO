package seco;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.FlowLayout;

public class provedores extends JPanel {
	private executable executable;

	public provedores(executable frame) {
		this.executable = frame;
		setLayout(new BorderLayout());
		Menu_lateral();
		Cont_central();
	}

	// MENU LATERAL ELEMENTOS, BOTONES, ACCIONES
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
		titulo.setPreferredSize(new Dimension(100, 0));
		titulo.setForeground(Color.white);
		titulo.setFont(new Font("Arial", Font.ITALIC, 15));
		p.add(titulo);

		ImageIcon dashi = new ImageIcon("img/casa_icono.jpg");
		Icon dsh = new ImageIcon(dashi.getImage().getScaledInstance(30, 25, Image.SCALE_SMOOTH));
		p.add(crearBoton("Dashboard", "dashboard", dsh));
		p.add(crearBoton("Productos", "productos", dsh));
		p.add(crearBoton("Entradas", "entradas", dsh));
		p.add(crearBoton("Salidas", "salidas", dsh));
		p.add(crearBoton("Provedores", "provedores", dsh));
		p.add(crearBoton("Ordenes", "ordenes", dsh));
		p.add(crearBoton("Reportes", "reportes", dsh));
		p.add(crearBoton("Servicio", "ventas", dsh));

	}

	private JButton crearBoton(String texto, String vista, Icon icono) {
		// AGREGA IMAGEN AL BOTON Y TEXTO
		JButton boton = new JButton(texto, icono);
		if (texto == "Provedores") {
			boton.setBackground(new Color(33, 150, 243));
		} else {
			boton.setBackground(new Color(10, 20, 100));
		}
		boton.setForeground(Color.WHITE);
		boton.setFocusPainted(false);
		boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
		boton.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));

		boton.addActionListener(e -> executable.mostrarVista(vista));

		return boton;
	}

	private JPanel crearTarjeta(String titulo){

    JPanel tarjeta = new JPanel(new BorderLayout());
    tarjeta.setPreferredSize(new Dimension(200,70));
    tarjeta.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

    JLabel t = new JLabel(titulo);
    t.setBorder(BorderFactory.createEmptyBorder(5,10,0,0));

    JLabel numero = new JLabel("0", SwingConstants.CENTER);
    numero.setFont(new Font("Arial", Font.BOLD, 22));

    tarjeta.add(t, BorderLayout.NORTH);
    tarjeta.add(numero, BorderLayout.CENTER);

    return tarjeta;
}

	private JPanel crearTarjeta(String titulo, String numero){

	JPanel tarjeta = new JPanel(new BorderLayout());
	tarjeta.setPreferredSize(new Dimension(200,70));
	tarjeta.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

	JLabel t = new JLabel(titulo);
	t.setBorder(BorderFactory.createEmptyBorder(5,10,0,0));

	JLabel num = new JLabel(numero, SwingConstants.CENTER);
	num.setFont(new Font("Arial", Font.BOLD, 22));

	tarjeta.add(t, BorderLayout.NORTH);
	tarjeta.add(num, BorderLayout.CENTER);

	return tarjeta;}

	private void Cont_central() {
		JPanel p = new JPanel();
		p.setBackground(new Color(240, 240, 240));
		add(p, BorderLayout.CENTER);

		JLabel jl = new JLabel("");
		p.add(jl);{

		

    JPanel contenedor = new JPanel(new BorderLayout());
    contenedor.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    add(contenedor, BorderLayout.CENTER);

    // BARRA SUPERIOR
    JPanel barraSuperior = new JPanel(new BorderLayout());

    JTextField buscar = new JTextField("Buscar proveedor...");
    buscar.setPreferredSize(new Dimension(200,30));

    JButton nuevoProveedor = new JButton("Nuevo Proveedor");

    barraSuperior.add(buscar, BorderLayout.WEST);
    barraSuperior.add(nuevoProveedor, BorderLayout.EAST);

    contenedor.add(barraSuperior, BorderLayout.NORTH);


    // CONTENIDO CENTRAL
    JPanel contenido = new JPanel();
    contenido.setLayout(new BorderLayout());
    contenido.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));

    contenedor.add(contenido, BorderLayout.CENTER);


    // TITULO
    JLabel titulo = new JLabel("Proveedores");
    titulo.setFont(new Font("Arial", Font.BOLD, 20));

    contenido.add(titulo, BorderLayout.NORTH);


    // PANEL DE TARJETAS
    JPanel tarjetas = new JPanel(new GridLayout(1,3,20,20));
    tarjetas.setBorder(BorderFactory.createEmptyBorder(20,0,20,0));

    tarjetas.add(crearTarjeta("Proveedores", "15"));
    tarjetas.add(crearTarjeta("Activos", "13"));
    tarjetas.add(crearTarjeta("Órdenes", "5"));

    contenido.add(tarjetas, BorderLayout.CENTER);


    // TABLA
    String[] columnas = {"ID","Nombre","Teléfono","Email"};

    DefaultTableModel modelo = new DefaultTableModel(columnas,0);

    modelo.addRow(new Object[]{"#001","Juan Pérez","TecnoCom","563231"});
    modelo.addRow(new Object[]{"#002","Laura Díaz","Moda Express","563777"});
    modelo.addRow(new Object[]{"#003","Ricardo Sosa","Distribuidor Alfa","552221"});
    modelo.addRow(new Object[]{"#004","Marta Garcia","Electrohogar","637897"});

    JTable tabla = new JTable(modelo);

    JScrollPane scroll = new JScrollPane(tabla);

    contenido.add(scroll, BorderLayout.SOUTH);


    // BOTONES INFERIORES
    JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));

    JButton ver = new JButton("Ver Orden");
    JButton editar = new JButton("Editar");
    JButton eliminar = new JButton("Eliminar");
    JButton exportar = new JButton("Exportar");

    botones.add(ver);
    botones.add(editar);
    botones.add(eliminar);
    botones.add(exportar);

    contenedor.add(botones, BorderLayout.SOUTH);

}




		}

	
	
}
