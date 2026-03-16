package seco;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import seco.ventanas.ProvedoresAgregar;
import seco.ventanas.ProvedoresEditrar;

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

import seco.fcdb.provedoresDB;

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
		boton.setHorizontalAlignment(SwingConstants.LEFT);
		boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
		boton.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));

		boton.addActionListener(e -> executable.mostrarVista(vista));

		return boton;
	}

	private JPanel crearTarjeta(String titulo, String valor) {

		JPanel panel = new JPanel(new GridLayout(2, 1));
		panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

		JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Arial", Font.PLAIN, 14));

		JLabel lblValor = new JLabel(valor, SwingConstants.CENTER);
		lblValor.setFont(new Font("Arial", Font.BOLD, 20));

		panel.add(lblTitulo);
		panel.add(lblValor);

		return panel;
	}

	DefaultTableModel modelo;
	JTable tabla;
	provedoresDB db = new provedoresDB();
	String[] columnas = { "ID", "Nombre", "Teléfono", "Email" };

	private void Cont_central() {
		JPanel p = new JPanel();
		modelo = new DefaultTableModel(columnas, 0);
		tabla = new JTable(modelo);
		p.setBackground(new Color(240, 240, 240));
		add(p, BorderLayout.CENTER);

		JLabel jl = new JLabel("");
		p.add(jl);

		JPanel contenedor = new JPanel(new BorderLayout());
		contenedor.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		add(contenedor, BorderLayout.CENTER);

		JTextField buscar = new JTextField("Buscar proveedor...");
		buscar.setPreferredSize(new Dimension(360, 30));

		JButton nuevoProveedor = new JButton("Nuevo Proveedor");
		nuevoProveedor.setBackground(new Color(255, 140, 0));
		nuevoProveedor.setForeground(Color.WHITE);
		nuevoProveedor.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(241, 241, 241)),
				BorderFactory.createEmptyBorder(8, 15, 8, 15)));

		nuevoProveedor.addActionListener(e -> {

			ProvedoresAgregar agregarProveedor = new ProvedoresAgregar();
			agregarProveedor.setVisible(true);

	});
				
		JButton editar = new JButton("Editar");
		editar.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(241, 241, 241)),
				BorderFactory.createEmptyBorder(8, 15, 8, 15)));

			editar.addActionListener(e -> {

				ProvedoresEditrar editarProveedor = new ProvedoresEditrar();
				editarProveedor.setVisible(true);

			}

		);

		JButton eliminar = new JButton("Eliminar");
		eliminar.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(241, 241, 241)), // Borde exterior
				BorderFactory.createEmptyBorder(8, 15, 8, 15) // Margen interno (padding)


		));

		JPanel cabecera = new JPanel(new BorderLayout());
		cabecera.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

		JLabel titulo = new JLabel("Proveedores", SwingConstants.CENTER);
		titulo.setFont(new Font("Arial", Font.BOLD, 20));
		cabecera.add(titulo, BorderLayout.WEST);

		JPanel buscaCentro = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		buscaCentro.add(buscar);
		cabecera.add(buscaCentro, BorderLayout.CENTER);

		JPanel nuevoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
		nuevoPanel.add(editar);
		nuevoPanel.add(eliminar);
		nuevoPanel.add(nuevoProveedor);
		cabecera.add(nuevoPanel, BorderLayout.EAST);

		contenedor.add(cabecera, BorderLayout.NORTH);

		JPanel contenido = new JPanel();
		contenido.setLayout(new BorderLayout());
		contenido.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

		contenedor.add(contenido, BorderLayout.CENTER);

		JPanel tarjetas = new JPanel(new GridLayout(1, 3, 20, 20));
		tarjetas.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
		tarjetas.setPreferredSize(new Dimension(0, 150));

		tarjetas.add(crearTarjeta("Proveedores", "15"));
		tarjetas.add(crearTarjeta("Activos", "13"));
		tarjetas.add(crearTarjeta("Órdenes", "5"));

		contenido.add(tarjetas, BorderLayout.NORTH);

		// TABLA
		String[] columnas = { "ID", "Nombre", "Teléfono", "Email" };

		DefaultTableModel modelo = new DefaultTableModel(columnas, 0){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Hace que todas las celdas no sean editables
			}
		};

		 db.consultarProvedores(modelo);

		JTable tabla = new JTable(modelo);
		JScrollPane scroll = new JScrollPane(tabla);

		JPanel tablaPanel = new JPanel(new BorderLayout());
		tablaPanel.add(scroll, BorderLayout.CENTER);

		contenido.add(tablaPanel, BorderLayout.CENTER);

		JPanel botonesBottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		JButton exportar = new JButton("Exportar");
		exportar.setMaximumSize(new Dimension(120, 40));
		botonesBottom.add(exportar);
		contenedor.add(botonesBottom, BorderLayout.SOUTH);

	}

}