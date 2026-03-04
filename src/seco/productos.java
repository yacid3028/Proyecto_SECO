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

		JPanel superior = new JPanel(new BorderLayout());
		superior.setPreferredSize(new Dimension(Integer.MAX_VALUE, 50));
		pam.add(superior, BorderLayout.NORTH);

		JLabel titulo = new JLabel("Inventario de Productos");
		titulo.setFont(new Font("Arial", Font.BOLD, 30));
		superior.add(titulo, BorderLayout.CENTER);

		JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton agregar = new JButton("Agregar Producto");
		JButton editar = new JButton("Editar");
		JButton eliminar = new JButton("Eliminar");

		botones.add(agregar);
		botones.add(editar);
		botones.add(eliminar);

		superior.add(botones, BorderLayout.EAST);

		pam.add(panelTabla(), BorderLayout.CENTER);

		JPanel ge = new JPanel(new BorderLayout());
		ge.setPreferredSize(new Dimension(300, 50));

		JPanel as= new JPanel(new FlowLayout(FlowLayout.LEFT));
		as.setPreferredSize(new Dimension(Integer.MAX_VALUE, 200));
		pam.add(as, BorderLayout.SOUTH);

		pam.add(ge, BorderLayout.EAST);

	}

	private JPanel panelTabla() {

		JPanel panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(new Dimension(300, 300));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		String[] columnas = { "ID", "Producto", "Categoría", "Stock", "Precio" };

		Object[][] datos = {
				{ "#10106", "Teclado Inalámbrico", "Electrónica", 80, "Disponible" },
				{ "#10105", "Cafetera Eléctrica", "Electrónica", 12, "Disponible" },
				{ "#10109", "Galletas Chocolate", "Alimentos", 6, "Disponible" },
		};

		JTable tabla = new JTable(datos, columnas);
		tabla.setFillsViewportHeight(true);
		tabla.setRowHeight(15);
		tabla.setPreferredScrollableViewportSize(new Dimension(300, 250));

		JScrollPane scroll = new JScrollPane(tabla);
		scroll.setPreferredSize(new Dimension(300, 300));

		if (tabla.getColumnModel().getColumnCount() > 0) {
			tabla.getColumnModel().getColumn(0).setPreferredWidth(50);
			tabla.getColumnModel().getColumn(1).setPreferredWidth(140);
			tabla.getColumnModel().getColumn(2).setPreferredWidth(60);
			tabla.getColumnModel().getColumn(3).setPreferredWidth(30);
			tabla.getColumnModel().getColumn(4).setPreferredWidth(20);
		}

		panel.add(scroll, BorderLayout.CENTER);

		return panel;
	}
}
