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
import javax.swing.*;

public class dashboard extends JPanel {
	private executable executable;
	Color fontColor = new Color(9, 8, 48);

	public dashboard(executable frame) {
		this.executable = frame;
		setLayout(new BorderLayout());
		setBackground(new Color(245, 247, 250));

		Cont_central();
		Menu_lateral();
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
		if (texto == "Dashboard") {
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

	// CONTENEDOR SUPERIOR
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

		JButton btCerrar = new JButton("Cerrar Dia");
		btCerrar.setBackground(new Color(250, 250, 249));
		btCerrar.setPreferredSize(new Dimension(100, 30));
		btCerrar.setFont(new Font("Arial", Font.BOLD, 12));
		btCerrar.setForeground(fontColor);
		btCerrar.setFocusPainted(false);

		btPanel.add(btNuevo);
		btPanel.add(btCerrar);
		btPanel.add(new JLabel(" "));

		logoPanel.add(btPanel, BorderLayout.EAST);

		// BLOQUES DE INFORMACION
		JPanel cards = new JPanel(new GridLayout(1, 4, 15, 0));
		cards.setBackground(new Color(240, 240, 240));
		cards.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

		cards.add(card("Stock Total", "12,450", "Productos en inventario"));
		cards.add(card("Bajo Stock", "28", "Productos bajos"));
		cards.add(card("Órdenes Pendientes", "5", "Órdenes por recibir"));
		cards.add(card("Valor Inventario", "$245,300", "Valor total"));

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

		JPanel alertas = new JPanel(new BorderLayout());
		alertas.setBackground(Color.WHITE);
		alertas.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

		JLabel alerta = new JLabel("⚠ Alertas de Inventario");
		alerta.setFont(new Font("Arial", Font.BOLD, 14));

		JLabel info = new JLabel("4 productos en bajo stock");

		alertas.add(alerta, BorderLayout.NORTH);
		alertas.add(info, BorderLayout.CENTER);

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

	// CREA LA GRIFICA
	private JPanel panelGrafica() {

		JPanel p = new JPanel(new BorderLayout());
		p.setBackground(Color.WHITE);
		p.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(230, 230, 230)),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		JLabel t = new JLabel("Resumen de Inventario");
		t.setFont(new Font("Segoe UI", Font.BOLD, 14));
		t.setForeground(fontColor);

		p.add(t, BorderLayout.NORTH);

		JPanel graf = new JPanel() {
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				// CREA LA GRAFICA PLANA
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				// DECLARA LOS VALORES DE LA TABLA
				int[] valores = { 10, 20, 40, 10, 100, 20, 49, 95, 37 };
				// DECLARA LOS COLORES OPCIONAL
				Color[] colores = {
						new Color(52, 152, 219),
						new Color(46, 204, 113),
						new Color(241, 196, 15),
						new Color(155, 89, 182),
						new Color(52, 73, 94),
						new Color(52, 73, 94),
						new Color(52, 73, 94),
						new Color(52, 73, 94),
						new Color(52, 73, 94)
				};

				int x = 10;
				// LLENA Y CREA LOS ESPACIOS DE LA TABLA CON LO VALORES
				for (int i = 0; i < valores.length; i++) {

					g2.setColor(colores[i]);

					g2.fillRoundRect(
							x, // DISTANCIA LATERAL
							getHeight() - valores[i] - 10, // DISTANCIA VERTICAL
							30, // ANCHO
							valores[i], // VALOR DE ALTO
							10, // ROUND BORDER
							10);

					x += 50;
				}
			}
		};

		graf.setBackground(Color.WHITE);

		p.add(graf, BorderLayout.CENTER);

		return p;
	}

	private JPanel tablaBajoStock() {

		String[] cols = { "Producto", "Stock", "Estado" };

		Object[][] data = {
				{ "Audifonos Bluetooth", "3", "Bajo Stock" },
				{ "Camiseta Negra", "5", "Bajo Stock" },
				{ "Cafetera Electrica", "2", "Bajo Stock" },
				{ "Galletas Chocolate", "6", "Bajo Stock" }
		};

		JTable table = new JTable(data, cols);
		table.setRowHeight(28);
		table.setShowVerticalLines(false);
		table.setGridColor(new Color(230, 230, 230));
		table.getTableHeader().setBackground(new Color(245, 245, 245));
		table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

		JPanel p = new JPanel(new BorderLayout());
		p.setBackground(Color.WHITE);

		p.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(230, 230, 230)),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		p.add(new JLabel("Productos Bajos de Stock"), BorderLayout.NORTH);
		p.add(new JScrollPane(table), BorderLayout.CENTER);

		return p;
	}

	private JPanel tablaMovimientos() {

		String[] cols = { "Fecha", "Tipo", "Producto", "Cantidad" };

		Object[][] data = {
				{ "15/04/2024", "Entrada", "Teclado Inalambrico", "20" },
				{ "14/04/2024", "Salida", "Zapatillas Deportivas", "8" },
				{ "13/04/2024", "Entrada", "Cafe Molido", "50" }
		};

		JTable table = new JTable(data, cols);
		table.setRowHeight(28);
		table.setShowVerticalLines(false);
		table.setGridColor(new Color(230, 230, 230));
		table.getTableHeader().setBackground(new Color(245, 245, 245));
		table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

		JPanel p = new JPanel(new BorderLayout());
		p.setBackground(Color.WHITE);

		p.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(230, 230, 230)),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		p.add(new JLabel("Últimos Movimientos"), BorderLayout.NORTH);
		p.add(new JScrollPane(table), BorderLayout.CENTER);

		return p;
	}

	private JPanel tablaOrdenes() {

		String[] cols = { "Orden", "Proveedor", "Estado" };

		Object[][] data = {
				{ "#1023", "TecnoCom", "En Camino" },
				{ "#1022", "Moda Express", "Pendiente" },
				{ "#1021", "Distribuidora Alfa", "Solicitado" }
		};

		JTable table = new JTable(data, cols);
		table.setRowHeight(28);
		table.setShowVerticalLines(false);
		table.setGridColor(new Color(230, 230, 230));
		table.getTableHeader().setBackground(new Color(245, 245, 245));
		table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

		JPanel p = new JPanel(new BorderLayout());
		p.setBackground(Color.WHITE);

		p.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(230, 230, 230)),
				BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		p.add(new JLabel("Órdenes Pendientes"), BorderLayout.NORTH);
		p.add(new JScrollPane(table), BorderLayout.CENTER);

		return p;
	}

}
