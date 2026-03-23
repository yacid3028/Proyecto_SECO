package seco;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import seco.fcdb.dashboardDB;

public class dashboard extends JPanel {
	public executable executable;
	Color fontColor = new Color(9, 8, 48);
	// Variable para guardar los datos de la gráfica
	private int[] valoresGrafica;

	public dashboard(executable frame) {
		this.executable = frame;
		this.setLayout(new BorderLayout());
		this.setBackground(new Color(245, 247, 250));

		// Cargamos los datos de la base de datos al iniciar
		dashboardDB db = new dashboardDB();
		this.valoresGrafica = db.obtenerTop9ProductosVendidos();

		Cont_central();
		Menu_lateral();
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

		ImageIcon vents = new ImageIcon("img/ventas_icon.png");
		Icon vts = new ImageIcon(vents.getImage().getScaledInstance(30, 25, Image.SCALE_SMOOTH));

		p.add(crearBoton("Dashboard", "dashboard", dsh));
		p.add(crearBoton("Productos", "productos", dsh));
		p.add(crearBoton("Entradas", "entradas", etd));
		p.add(crearBoton("Salidas", "salidas", dsh));
		p.add(crearBoton("Provedores", "provedores", dsh));
		p.add(crearBoton("Ordenes", "ordenes", dsh));
		p.add(crearBoton("Reportes", "reportes", rps));
		p.add(crearBoton("Servicio", "ventas", vts));
	}

	private JButton crearBoton(String texto, String vista, Icon icono) {
		JButton boton = new JButton(texto, icono);
		boton.setBackground(texto.equals("Dashboard") ? new Color(33, 150, 243) : new Color(10, 20, 100));
		boton.setForeground(Color.WHITE);
		boton.setFocusPainted(false);
		boton.setHorizontalAlignment(SwingConstants.LEFT);
		boton.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
		boton.addActionListener(e -> executable.mostrarVista(vista));
		return boton;
	}

	private void Cont_superior(JPanel p) {
		JPanel cont = new JPanel(new BorderLayout());
		cont.setBackground(new Color(240, 240, 240));

		JPanel logoPanel = new JPanel(new BorderLayout());
		logoPanel.setBackground(new Color(240, 240, 240));
		logoPanel.add(new JLabel(" "), BorderLayout.NORTH);

		JLabel titulo = new JLabel("Dashboard");
		titulo.setFont(new Font("Arial", Font.BOLD, 20));
		titulo.setForeground(fontColor);
		logoPanel.add(titulo, BorderLayout.WEST);

		JPanel btPanel = new JPanel(new GridLayout(1, 3, 20, 15));
		btPanel.setOpaque(false);

		JButton btNuevo = new JButton("Iniciar Dia");
		btNuevo.setBackground(new Color(243, 145, 32));
		btNuevo.setPreferredSize(new Dimension(100, 30));
		btNuevo.setForeground(Color.WHITE);
		btNuevo.setFont(new Font("Arial", Font.BOLD, 12));
		btNuevo.setFocusPainted(false);
		dashboardDB dr = new dashboardDB();
		btNuevo.addActionListener(e -> dr.iniciarDia());

		JButton btCerrar = new JButton("Cerrar Dia");
		btCerrar.setBackground(new Color(250, 250, 249));
		btCerrar.setPreferredSize(new Dimension(100, 30));
		btCerrar.setFont(new Font("Arial", Font.BOLD, 12));
		btCerrar.setForeground(fontColor);
		btCerrar.setFocusPainted(false);
		btCerrar.addActionListener(e -> dr.cerrarDia());

		btPanel.add(btNuevo);
		btPanel.add(btCerrar);
		btPanel.add(new JLabel(" "));

		logoPanel.add(btPanel, BorderLayout.EAST);

		JPanel cards = new JPanel(new GridLayout(1, 4, 15, 0));
		cards.setBackground(new Color(240, 240, 240));
		cards.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

		// Aquí podrías agregar las cards si lo necesitas más adelante
		// cards.add(card("Ventas", "$0.00", "+0% vs ayer"));

		cont.add(logoPanel, BorderLayout.NORTH);
		cont.add(cards, BorderLayout.CENTER);
		p.add(cont, BorderLayout.NORTH);
	}

	private void Cont_central() {
		JPanel p = new JPanel(new BorderLayout());
		p.setBackground(new Color(240, 240, 240));
		add(p, BorderLayout.CENTER);

		Cont_superior(p);

		JPanel centro = new JPanel(new GridLayout(2, 2, 20, 20));
		centro.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		centro.setBackground(new Color(240, 240, 240));

		centro.add(panelGrafica());
		centro.add(tablaBajoStock());
		centro.add(tablaMovimientos());
		centro.add(tablaOrdenes());

		p.add(centro, BorderLayout.CENTER);
		Cont_inferior(p);
	}

	private void Cont_inferior(JPanel p) {
		JPanel bottom = new JPanel(new GridLayout(1, 2, 20, 0));
		bottom.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
		bottom.setBackground(new Color(240, 240, 240));

		JPanel reportes = new JPanel(new BorderLayout());
		reportes.setBackground(Color.WHITE);
		reportes.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

		JLabel ver = new JLabel("Ver Reportes >");
		ver.setFont(new Font("Arial", Font.BOLD, 14));
		reportes.add(ver, BorderLayout.WEST);

		reportes.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				executable.mostrarVista("reportes");
			}
		});

		JPanel alertas = new JPanel(new BorderLayout());
		alertas.setBackground(Color.WHITE);
		alertas.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

		JLabel alerta = new JLabel("\u26A0 Alertas de Inventario");
		alerta.setFont(new Font("Segoe UI Emoji", Font.BOLD, 15));
		JLabel info = new JLabel("Revisar productos en bajo stock");

		alertas.add(alerta, BorderLayout.NORTH);
		alertas.add(info, BorderLayout.CENTER);

		alertas.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				mostrarAlertas();
			}
		});

		bottom.add(reportes);
		bottom.add(alertas);
		p.add(bottom, BorderLayout.SOUTH);
	}

	private JPanel card(String titulo, String valor, String desc) {
		JPanel card = new JPanel(new BorderLayout());
		card.setBackground(Color.WHITE);
		card.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(230, 230, 230), 1, true),
				BorderFactory.createEmptyBorder(15, 20, 15, 20)));

		JLabel t = new JLabel(titulo);
		t.setForeground(new Color(120, 120, 120));
		t.setFont(new Font("Segoe UI", Font.PLAIN, 13));

		JLabel v = new JLabel(valor);
		v.setFont(new Font("Segoe UI", Font.BOLD, 28));
		v.setForeground(fontColor);

		JLabel d = new JLabel(desc);
		d.setForeground(new Color(140, 140, 140));
		d.setFont(new Font("Segoe UI", Font.PLAIN, 12));

		card.add(t, BorderLayout.NORTH);
		card.add(v, BorderLayout.CENTER);
		card.add(d, BorderLayout.SOUTH);

		return card;
	}

	private JPanel panelGrafica() {
		JPanel p = new JPanel(new BorderLayout());
		p.setBackground(Color.WHITE);
		p.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(230, 230, 230)),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		JLabel t = new JLabel("Top 9 Productos Más Vendidos");
		t.setFont(new Font("Segoe UI", Font.BOLD, 14));
		t.setForeground(fontColor);
		p.add(t, BorderLayout.NORTH);

		JPanel graf = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				Color[] colores = {
						new Color(52, 152, 219), new Color(46, 204, 113), new Color(241, 196, 15),
						new Color(155, 89, 182), new Color(52, 73, 94), new Color(211, 84, 0),
						new Color(192, 57, 43), new Color(44, 62, 80), new Color(149, 165, 166)
				};

				int x = 20;
				if (valoresGrafica != null) {
					for (int i = 0; i < valoresGrafica.length; i++) {
						g2.setColor(colores[i % colores.length]);
						// Ajustamos la altura para que no se salga del panel
						int altura = Math.min(getHeight() - 40, valoresGrafica[i] * 5);
						g2.fillRoundRect(x, getHeight() - altura - 10, 30, altura, 10, 10);
						x += 45;
					}
				}
			}
		};
		graf.setBackground(Color.WHITE);
		p.add(graf, BorderLayout.CENTER);
		return p;
	}

	private JPanel tablaBajoStock() {
		String[] cols = { "Producto", "Stock", "Estado" };
		DefaultTableModel modelo = new DefaultTableModel(cols, 0);
		JTable table = new JTable(modelo);
		configurarTabla(table);

		JPanel p = new JPanel(new BorderLayout());
		p.setBackground(Color.WHITE);
		p.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(230, 230, 230)),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		JLabel t = new JLabel("Bajo Stock");
		t.setFont(new Font("Segoe UI", Font.BOLD, 14));
		p.add(t, BorderLayout.NORTH);
		p.add(new JScrollPane(table), BorderLayout.CENTER);

		return p;
	}

	private JPanel tablaMovimientos() {
		String[] cols = { "Fecha", "Tipo", "Producto", "Cant." };
		DefaultTableModel modelo = new DefaultTableModel(cols, 0);
		JTable table = new JTable(modelo);
		configurarTabla(table);

		JPanel p = new JPanel(new BorderLayout());
		p.setBackground(Color.WHITE);
		p.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(230, 230, 230)),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		JLabel t = new JLabel("Últimos Movimientos");
		t.setFont(new Font("Segoe UI", Font.BOLD, 14));
		p.add(t, BorderLayout.NORTH);
		p.add(new JScrollPane(table), BorderLayout.CENTER);

		return p;
	}

	private JPanel tablaOrdenes() {
		String[] cols = { "Orden", "Proveedor", "Estado" };
		DefaultTableModel modelo = new DefaultTableModel(cols, 0);
		JTable table = new JTable(modelo);
		configurarTabla(table);

		JPanel p = new JPanel(new BorderLayout());
		p.setBackground(Color.WHITE);
		p.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(230, 230, 230)),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		JLabel t = new JLabel("Órdenes Pendientes");
		t.setFont(new Font("Segoe UI", Font.BOLD, 14));
		p.add(t, BorderLayout.NORTH);
		p.add(new JScrollPane(table), BorderLayout.CENTER);

		return p;
	}

	private void configurarTabla(JTable table) {
		table.setRowHeight(28);
		table.setShowVerticalLines(false);
		table.setGridColor(new Color(230, 230, 230));
		table.getTableHeader().setBackground(new Color(245, 245, 245));
		table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
	}

	private void mostrarAlertas() {
		JFrame frame = new JFrame("Alertas de Inventario");
		frame.setSize(500, 400);
		frame.setLocationRelativeTo(null);

		String[] cols = { "Producto", "Stock", "Estado" };
		DefaultTableModel modelo = new DefaultTableModel(cols, 0);
		JTable table = new JTable(modelo);
		configurarTabla(table);

		frame.add(new JScrollPane(table));
		frame.setVisible(true);
	}
}