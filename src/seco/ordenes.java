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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class ordenes extends JPanel {
	private executable executable;

	public ordenes(executable frame) {
		this.executable = frame;
		setLayout(new BorderLayout());
		Menu_lateral();
		contenidoOrdenes();
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
		if (texto == "Ordenes") {
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

	// CONTENIDO PRINCIPAL DE ORDENES
	private void contenidoOrdenes() {
		JPanel content = new JPanel(new BorderLayout());
		add(content, BorderLayout.CENTER);

		// PANEL SUPERIOR
		JPanel panelTop = new JPanel(new BorderLayout());

		JLabel titulo = new JLabel("Órdenes");
		titulo.setFont(new Font("Arial", Font.BOLD, 22));
		panelTop.add(titulo, BorderLayout.WEST);

		JPanel botonesTop = new JPanel();

		JButton nuevaOrden = new JButton("Nueva Orden");
		nuevaOrden.setBackground(new Color(255, 140, 0));
		nuevaOrden.setForeground(Color.WHITE);
		nuevaOrden.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(241, 241, 241)), // Borde exterior
                BorderFactory.createEmptyBorder(8, 15, 8, 15) // Margen interno (padding)
        ));

		JButton cambiarEstado = new JButton("Cambiar Estado");
		cambiarEstado.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(241, 241, 241)), // Borde exterior
                BorderFactory.createEmptyBorder(8, 15, 8, 15) // Margen interno (padding)
        ));

		JButton eliminar = new JButton("Eliminar");
		eliminar.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(241, 241, 241)), // Borde exterior
				BorderFactory.createEmptyBorder(8, 15, 8, 15) // Margen interno (padding)
		));

		botonesTop.add(cambiarEstado);
		botonesTop.add(eliminar);
		botonesTop.add(nuevaOrden);

		panelTop.add(botonesTop, BorderLayout.EAST);

		content.add(panelTop, BorderLayout.NORTH);

		// PANEL TARJETAS
		JPanel panelTarjetas = new JPanel(new GridLayout(1, 3, 20, 20));
		panelTarjetas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		panelTarjetas.add(crearTarjeta("Órdenes Pendientes", "10"));
		panelTarjetas.add(crearTarjeta("Órdenes En Camino", "5"));
		panelTarjetas.add(crearTarjeta("Órdenes Recibidas", "12"));

		content.add(panelTarjetas, BorderLayout.CENTER);

		// PANEL INFERIOR (TABLA + BOTÓN EXPORTAR)
		JPanel panelInferior = new JPanel(new BorderLayout());

		// TABLA
		String columnas[] = { "Orden", "Proveedor", "Fecha", "Productos", "Total", "Estado" };

		DefaultTableModel modelo = new DefaultTableModel(null, columnas);

		modelo.addRow(new Object[] { "#1021", "TecnoCom", "20/04/2024", "3", "$3200", "Pendiente" });
		modelo.addRow(new Object[] { "#1022", "Moda Express", "19/04/2024", "5", "$1750", "En camino" });
		modelo.addRow(new Object[] { "#1023", "Distribuidor Alfa", "18/04/2024", "2", "$800", "Recibida" });

		JTable tablaOrdenes = new JTable(modelo);

		JScrollPane scroll = new JScrollPane(tablaOrdenes);
		scroll.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

		panelInferior.add(scroll, BorderLayout.CENTER);

		// BOTÓN EXPORTAR EN ESQUINA INFERIOR DERECHA
		JPanel panelBotonExportar = new JPanel(new BorderLayout());
		panelBotonExportar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JPanel panelDerecha = new JPanel();
		JButton exportar = new JButton("Exportar");
		exportar.setPreferredSize(new Dimension(140, 40));
		exportar.setBackground(new Color(255, 140, 0));
		exportar.setForeground(Color.WHITE);

		panelDerecha.add(exportar);
		panelBotonExportar.add(panelDerecha, BorderLayout.EAST);

		panelInferior.add(panelBotonExportar, BorderLayout.SOUTH);

		content.add(panelInferior, BorderLayout.SOUTH);
	}

	// Método para crear tarjetas
	private JPanel crearTarjeta(String titulo, String valor) {

		JPanel tarjeta = new JPanel();
		tarjeta.setLayout(new GridLayout(2, 1));
		tarjeta.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

		JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Arial", Font.PLAIN, 14));

		JLabel lblValor = new JLabel(valor, SwingConstants.CENTER);
		lblValor.setFont(new Font("Arial", Font.BOLD, 22));

		tarjeta.add(lblTitulo);
		tarjeta.add(lblValor);

		return tarjeta;
	}
}
