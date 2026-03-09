package seco;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class reportes extends JPanel {
	private executable executable;

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
		// AGREGA IMAGEN AL BOTON Y TEXTO
		JButton boton = new JButton(texto, icono);
		if (texto == "Ordenes") {
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

	// CONTENIDO PRINCIPAL DE REPORTES
	private void contenidoReportes() {
		JPanel content = new JPanel(new BorderLayout());
		add(content, BorderLayout.CENTER);

		// PANEL SUPERIOR
		JPanel panelSuperior = new JPanel(new BorderLayout());

		JLabel titulo = new JLabel("Reportes");
		titulo.setFont(new Font("Arial", Font.BOLD, 22));
		panelSuperior.add(titulo, BorderLayout.WEST);

		// Botones en esquina superior derecha
		JPanel botonesSuperiorDerecha = new JPanel();
		JButton agregar = new JButton("Agregar Reporte");
		agregar.setPreferredSize(new Dimension(140, 40));
		agregar.setBackground(new Color(255, 140, 0));
		agregar.setForeground(Color.WHITE);

		JButton editar = new JButton("Editar Reporte");
		editar.setPreferredSize(new Dimension(140, 40));

		JButton eliminar = new JButton("Eliminar Reporte");
		eliminar.setPreferredSize(new Dimension(140, 40));

		botonesSuperiorDerecha.add(editar);
		botonesSuperiorDerecha.add(eliminar);
		botonesSuperiorDerecha.add(agregar);

		panelSuperior.add(botonesSuperiorDerecha, BorderLayout.EAST);
		content.add(panelSuperior, BorderLayout.NORTH);

		// TARJETAS
		JPanel tarjetas = new JPanel(new GridLayout(1, 4, 10, 10));
		tarjetas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		tarjetas.add(crearTarjeta("Reportes Totales", "2"));
		tarjetas.add(crearTarjeta("Reporte Servicio", "2"));
		tarjetas.add(crearTarjeta("Reporte Ventas", "1"));
		tarjetas.add(crearTarjeta("Reporte Sofware", "7"));

		content.add(tarjetas, BorderLayout.CENTER);

		// PANEL INFERIOR (TABLA + BOTONES)
		JPanel panelInferior = new JPanel(new BorderLayout());

		// TABLA
		String columnas[] = { "Categoria", "Descripción", "Usuario", "Fecha" };

		DefaultTableModel modelo = new DefaultTableModel(null, columnas);

		modelo.addRow(new Object[] { "S.A.C", "El quiente se quejo", "Yacid", "date" });
		modelo.addRow(new Object[] { "Productos", "Salio producto dañado", "Esteban", "date" });
		modelo.addRow(new Object[] { "Sucursal", "Inmobiliario dañado", "Caleb", "date" });
		modelo.addRow(new Object[] { "S.R.S", "El sistema tuvo problemas", "Santi", "date" });

		JTable tabla = new JTable(modelo);

		JScrollPane scroll = new JScrollPane(tabla);
		scroll.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

		panelInferior.add(scroll, BorderLayout.CENTER);

		// PANEL DE BOTONES
		JPanel panelBotones = new JPanel(new BorderLayout());
		panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Botón derecha (Exportar)
		JPanel botonesDerecha = new JPanel();
		JButton exportar = new JButton("Exportar");
		exportar.setPreferredSize(new Dimension(140, 40));
		exportar.setBackground(new Color(255, 140, 0));
		exportar.setForeground(Color.WHITE);

		botonesDerecha.add(exportar);

		panelBotones.add(botonesDerecha, BorderLayout.EAST);

		panelInferior.add(panelBotones, BorderLayout.SOUTH);

		content.add(panelInferior, BorderLayout.SOUTH);
	}

	// Crear tarjetas
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
}
