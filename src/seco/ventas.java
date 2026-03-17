package seco;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import seco.fcdb.salidasDB;

public class ventas extends JPanel {
	private executable executable;

	public ventas(executable frame) {
		this.executable = frame;
		setLayout(new BorderLayout());
		Menu_lateral();
		Cont_central();
	}

	JTextField nombre, cantidad;
	String fechaActual;
	JLabel subtotalValor, impuestoValor, totalValor;
	DefaultTableModel modelo;

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
		if (texto == "Servicio") {
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

	private void Cont_central() {

		Font labelFont = new Font("Arial", Font.BOLD, 15);
		Font inputFont = new Font("Arial", Font.BOLD, 14);
		Font buttonFont = new Font("Arial", Font.BOLD, 15);
		Font totalFont = new Font("Arial", Font.BOLD, 20);

		JPanel contenedor = new JPanel();
		contenedor.setBackground(new Color(245, 245, 245));
		contenedor.setLayout(new BorderLayout(10, 10));
		contenedor.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		add(contenedor, BorderLayout.CENTER);

		// PANEL BUSCAR PRODUCTO
		JPanel buscarPanel = new JPanel(new BorderLayout(10, 10));
		buscarPanel.setBackground(Color.WHITE);
		buscarPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JLabel buscarLabel = new JLabel("Buscar producto por ID:");
		buscarLabel.setFont(labelFont);

		JComboBox<String> buscarField = new JComboBox<>();
		buscarField.setEditable(true);
		buscarField.setFont(inputFont);
		JTextField editor = (JTextField) buscarField.getEditor().getEditorComponent();
		editor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String texto = editor.getText().toUpperCase();
				if (texto.isEmpty()) {
					buscarField.removeAllItems();
				} else {
					salidasDB db = new salidasDB();
					String[] resultados = db.buscarProducto(texto, nombre);
					buscarField.removeAllItems();
					for (String resultado : resultados) {
						buscarField.addItem(resultado);
					}
					editor.setText(texto);
					buscarField.showPopup();
				}
			}
		});

		JButton agregarBtn = new JButton("Agregar");
		agregarBtn.setBackground(new Color(255, 140, 0));
		agregarBtn.setForeground(Color.WHITE);
		agregarBtn.setFocusPainted(false);
		agregarBtn.setFont(buttonFont);
		agregarBtn.addActionListener(e -> {
			String id = (String) buscarField.getSelectedItem();
			String cant = cantidad.getText();
			if (id != null && !cant.isEmpty()) {
				salidasDB db = new salidasDB();
				db.agregarArticulo(id, nombre, cantidad, fechaActual, modelo, subtotalValor,
						impuestoValor, totalValor);
			}
		});

		buscarPanel.add(buscarLabel, BorderLayout.WEST);
		buscarPanel.add(buscarField, BorderLayout.CENTER);
		buscarPanel.add(agregarBtn, BorderLayout.EAST);

		contenedor.add(buscarPanel, BorderLayout.NORTH);

		// PANEL CENTRAL
		JPanel panelCentral = new JPanel();
		panelCentral.setLayout(new BorderLayout(10, 10));
		panelCentral.setBackground(new Color(245, 245, 245));
		contenedor.add(panelCentral, BorderLayout.CENTER);

		// PANEL PRODUCTO AGREGADO
		JPanel productoPanel = new JPanel(new GridLayout(3, 2, 10, 10));
		productoPanel.setBackground(Color.WHITE);
		productoPanel.setBorder(BorderFactory.createTitledBorder("Producto agregado"));

		JLabel nombreLabel = new JLabel("Nombre:");
		nombreLabel.setFont(labelFont);
		productoPanel.add(nombreLabel);

		nombre = new JTextField();
		nombre.setFont(inputFont);
		nombre.setEditable(false);
		productoPanel.add(nombre);

		JLabel prodLabel = new JLabel("Cantidad:");
		prodLabel.setFont(labelFont);
		productoPanel.add(prodLabel);

		cantidad = new JTextField();
		cantidad.setFont(inputFont);
		productoPanel.add(cantidad);
		cantidad.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(java.awt.event.KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c) && c != '\b' && c != '\n') {
					e.consume();

				}
				if (c == '0' && cantidad.getText().isEmpty()) {
					e.consume();
					cantidad.setText("1");
				}
			}
		});

		JLabel fechaLabel = new JLabel("Fecha:");
		fechaLabel.setFont(labelFont);
		productoPanel.add(fechaLabel);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		fechaActual = sdf.format(new Date());
		JTextField fecha = new JTextField(fechaActual);
		fecha.setFont(inputFont);
		fecha.setEditable(false);
		productoPanel.add(fecha);

		panelCentral.add(productoPanel, BorderLayout.NORTH);

		// TABLA
		String columnas[] = { "ID", "Producto", "Cantidad", "Precio", "Subtotal" };

		modelo = new DefaultTableModel(columnas, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		JTable tabla = new JTable(modelo);
		tabla.setRowHeight(40);
		tabla.getTableHeader().setReorderingAllowed(false);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		for (int i = 0; i < tabla.getColumnCount(); i++) {
			tabla.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}

		JScrollPane scroll = new JScrollPane(tabla);

		panelCentral.add(scroll, BorderLayout.CENTER);

		// PANEL TOTALES
		JPanel totales = new JPanel(new GridLayout(3, 2, 10, 10));
		totales.setBackground(Color.WHITE);
		totales.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JLabel subtotalLabel = new JLabel("Subtotal:");
		subtotalLabel.setFont(labelFont);
		totales.add(subtotalLabel);

		subtotalValor = new JLabel("$ 0.00");
		subtotalValor.setFont(labelFont);
		totales.add(subtotalValor);

		JLabel impuestoLabel = new JLabel("Impuestos:");
		impuestoLabel.setFont(labelFont);
		totales.add(impuestoLabel);

		impuestoValor = new JLabel("$ 0.00");
		impuestoValor.setFont(labelFont);
		totales.add(impuestoValor);

		JLabel totalLabel = new JLabel("TOTAL:");
		totalLabel.setFont(totalFont);
		totales.add(totalLabel);

		totalValor = new JLabel("$ 0.00");
		totalValor.setFont(totalFont);
		totales.add(totalValor);

		panelCentral.add(totales, BorderLayout.SOUTH);

		// BOTONES
		JPanel botones = new JPanel(new GridLayout(1, 2, 20, 10));
		botones.setBackground(new Color(245, 245, 245));

		JButton cancelar = new JButton("Cancelar Venta");
		cancelar.setBackground(Color.WHITE);
		cancelar.setFont(buttonFont);
		cancelar.addActionListener(ActionListener -> {
			salidasDB db = new salidasDB();
			db.eliminarArticulo(modelo, subtotalValor, impuestoValor, totalValor);
		});

		JButton cobrar = new JButton("Cobrar");
		cobrar.setBackground(new Color(255, 140, 0));
		cobrar.setForeground(Color.WHITE);
		cobrar.setFont(buttonFont);
		cobrar.addActionListener(ActionListener -> {
			salidasDB db = new salidasDB();
			String idVenta = "", producto = "";
			int cantidad = 0;

			while (modelo.getRowCount() > 0) {
				idVenta = ((String) modelo.getValueAt(0, 0)).substring(2, 4);
				producto = (String) modelo.getValueAt(0, 1);
				String cant = (String) modelo.getValueAt(0, 2);
				cantidad = Integer.parseInt(cant.replace("$", "").trim());
				double subtotal = Double.parseDouble(subtotalValor.getText().replace("$ ", ""));
				double total = Double.parseDouble(totalValor.getText().replace("$ ", ""));
				modelo.removeRow(0);
				db.registrarVenta(idVenta, fechaActual, producto, cantidad, subtotal, total);

			}

			subtotalValor.setText("$ ");
			impuestoValor.setText("$ ");
			totalValor.setText("$ ");
		});

		botones.add(cancelar);
		botones.add(cobrar);

		contenedor.add(botones, BorderLayout.SOUTH);
	}

}
