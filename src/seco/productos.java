package seco;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.*;

public class productos extends JPanel {
	private executable executable;

	public productos(executable frame) {
		this.executable = frame;
		setLayout(new BorderLayout());
		Menu_lateral();
		C_central();
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
		if (texto == "Productos") {
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

	private void C_central() {
		JPanel pam = new JPanel(new BorderLayout());
		add(pam, BorderLayout.CENTER);

		JPanel superior = new JPanel();
		superior.setLayout(new BoxLayout(superior, BoxLayout.Y_AXIS));
		superior.setPreferredSize(new Dimension(Integer.MAX_VALUE, 120));

		// Fila del título
		JPanel tituloPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		tituloPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		JLabel titulo = new JLabel("Inventario de Productos");
		titulo.setFont(new Font("Arial", Font.BOLD, 24));
		tituloPanel.add(titulo);
		superior.add(tituloPanel);

		// Fila de los botones
		JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		botonesPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));
		JButton agregar = new JButton("Agregar Producto");
		agregar.setPreferredSize(new Dimension(140, 40));
		agregar.setBackground(new Color(255,140,0));
		agregar.setForeground(Color.WHITE);

		JButton editar = new JButton("Editar");
		editar.setPreferredSize(new Dimension(140, 40));

		JButton eliminar = new JButton("Eliminar");
		eliminar.setPreferredSize(new Dimension(140, 40));
		botonesPanel.add(agregar);
		botonesPanel.add(editar);
		botonesPanel.add(eliminar);
		superior.add(botonesPanel);

		// Panel central que contiene superior, búsqueda, tabla
		JPanel central = new JPanel();
		central.setLayout(new BoxLayout(central, BoxLayout.Y_AXIS));
		central.add(superior);

		// Barra de búsqueda abajo del título
		JPanel busquedaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		busquedaPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		JPanel buscador = new JPanel(new BorderLayout(5, 5));
		JTextField buscar = new JTextField("Buscar producto...");
		buscar.setPreferredSize(new Dimension(300, 25));
		buscador.add(buscar, BorderLayout.CENTER);
		JButton filtro = new JButton("Filtrar");
		buscador.add(filtro, BorderLayout.EAST);
		busquedaPanel.add(buscador);
		central.add(busquedaPanel);

		central.add(panelTabla());
		pam.add(central, BorderLayout.CENTER);

		// Paginación inferior
		JPanel inferior = new JPanel(new BorderLayout());
		inferior.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
		inferior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JLabel info = new JLabel("Mostrando 6 de 250 productos");
		inferior.add(info, BorderLayout.WEST);

		JPanel paginas = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
		paginas.add(new JButton("<"));
		paginas.add(new JButton("1"));
		paginas.add(new JButton("2"));
		paginas.add(new JButton("3"));
		paginas.add(new JButton("4"));
		paginas.add(new JButton(">"));
		inferior.add(paginas, BorderLayout.CENTER);

		JButton cerrar = new JButton("Cerrar");
		inferior.add(cerrar, BorderLayout.EAST);

		pam.add(inferior, BorderLayout.SOUTH);

	}

	private JPanel panelTabla() {

		JPanel panel = new JPanel(new BorderLayout());

		String[] columnas = {"ID","Producto","Categoria","Stock","Precio"};

		Object[][] datos = {
			{"#10106","Teclado Inalámbrico","Electrónica","80","Disponible"},
			{"#10105","Cafetera Eléctrica","Electrónica","12","Disponible"},
			{"#10109","Galletas Chocolate","Alimentos","6","Disponible"},
			{"#10110","Mouse Gamer","Electrónica","15","Disponible"},
			{"#10111","Monitor 24","Electrónica","9","Disponible"},
			{"#10112","USB 32GB","Electrónica","30","Disponible"}
		};

		JTable tabla = new JTable(datos, columnas);
		tabla.setRowHeight(30);
		tabla.setShowGrid(false);
		tabla.setSelectionBackground(new Color(184,207,229));
		tabla.setFillsViewportHeight(true);

		JScrollPane scroll = new JScrollPane(tabla);

		if (tabla.getColumnModel().getColumnCount() > 0) {
			tabla.getColumnModel().getColumn(0).setPreferredWidth(60);
			tabla.getColumnModel().getColumn(1).setPreferredWidth(150);
			tabla.getColumnModel().getColumn(2).setPreferredWidth(80);
			tabla.getColumnModel().getColumn(3).setPreferredWidth(50);
			tabla.getColumnModel().getColumn(4).setPreferredWidth(70);
		}

		panel.add(scroll, BorderLayout.CENTER);

		return panel;

	}
}