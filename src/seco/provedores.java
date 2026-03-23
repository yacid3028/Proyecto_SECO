package seco;

import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import seco.ventanas.ProvedoresAgregar;
import seco.ventanas.ProvedoresEditar;
import seco.ventanas.ProvedoresEliminar;

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
	String[] columnas = { "ID Proveedor", "Empresa", "Teléfono", "Email", "Producto" };

	public void actualizarTabla() {
		modelo.setRowCount(0);
		db.consultarProvedores(modelo);
	}

	private void Cont_central() {

		JPanel contenedor = new JPanel(new BorderLayout());
		contenedor.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		add(contenedor, BorderLayout.CENTER);

		JTextField buscar = new JTextField("Buscar proveedor...");
		buscar.setPreferredSize(new Dimension(360, 30));

		JButton editar = new JButton("Editar");
		editar.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(241, 241, 241)),
				BorderFactory.createEmptyBorder(8, 15, 8, 15)));

		JButton eliminar = new JButton("Eliminar");
		eliminar.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(241, 241, 241)), // Borde exterior
				BorderFactory.createEmptyBorder(8, 15, 8, 15) // Margen interno (padding)

		));

		JButton nuevoProveedor = new JButton("Nuevo Proveedor");
		nuevoProveedor.setBackground(new Color(255, 140, 0));
		nuevoProveedor.setForeground(Color.WHITE);

		nuevoProveedor.addActionListener(e -> {
			new ProvedoresAgregar(this).setVisible(true);
		});

		editar.addActionListener(e -> {
			int fila = tabla.getSelectedRow();
			if (fila >= 0) {
				new ProvedoresEditar(this).setVisible(true);
			} else {
				JOptionPane.showMessageDialog(this, "Selecciona un proveedor.");
			}
		});

		eliminar.addActionListener(e -> {
			int fila = tabla.getSelectedRow();
			if (fila >= 0) {
				new ProvedoresEliminar(this).setVisible(true);
			} else {
				JOptionPane.showMessageDialog(this, "Selecciona un proveedor.");
			}
		});

		JPanel cabecera = new JPanel(new BorderLayout());
		JLabel titulo = new JLabel("Proveedores", SwingConstants.CENTER);
		titulo.setFont(new Font("Arial", Font.BOLD, 20));
		cabecera.add(titulo, BorderLayout.WEST);

		JPanel buscaCentro = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buscaCentro.add(buscar);
		cabecera.add(buscaCentro, BorderLayout.CENTER);

		JPanel nuevoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		nuevoPanel.add(editar);
		nuevoPanel.add(eliminar);
		nuevoPanel.add(nuevoProveedor);
		cabecera.add(nuevoPanel, BorderLayout.EAST);

		contenedor.add(cabecera, BorderLayout.NORTH);

		JPanel contenido = new JPanel(new BorderLayout());
		contenedor.add(contenido, BorderLayout.CENTER);

		JPanel tarjetas = new JPanel(new GridLayout(1, 3, 20, 20));
		tarjetas.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
		tarjetas.add(crearTarjeta("Proveedores", "15"));
		tarjetas.add(crearTarjeta("Activos", "13"));
		tarjetas.add(crearTarjeta("Órdenes", "5"));
		contenido.add(tarjetas, BorderLayout.NORTH);

		modelo = new DefaultTableModel(columnas, 0) {
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};

		DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Hace que todas las celdas no sean editables
			}
		};

		tabla = new JTable(modelo);
		db.consultarProvedores(modelo);

		JScrollPane scroll = new JScrollPane(tabla);
		contenido.add(scroll, BorderLayout.CENTER);

		// ================= FOOTER =================
		JPanel botonesBottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton exportar = new JButton("Exportar");
		botonesBottom.add(exportar);
		contenedor.add(botonesBottom, BorderLayout.SOUTH);

	}

}
