package seco;

import java.awt.CardLayout;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.formdev.flatlaf.FlatLightLaf;

public class executable extends JFrame {

	private CardLayout cardlayout;
	private JPanel container;

	public executable() {

		setTitle("SECO Stock Control");
		setSize(1200, 700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		ImageIcon ico = new ImageIcon("img/logo_app.png");
		setIconImage(ico.getImage());

		cardlayout = new CardLayout();
		container = new JPanel(cardlayout);

		JPanel dash = new dashboard(this);
		dash.setName("dashboard");
		container.add(dash, "dashboard");
		cardlayout.show(container, "dashboard");

		add(container);
	}

	public void mostrarVista(String nombre) {
		for (Component c : container.getComponents()) {
			if (c.getName() != null && c.getName().equals(nombre)) {
				container.remove(c);
				break;
			}
		}

		JPanel nuevaVista = null;

		if (nombre.equals("dashboard"))
			nuevaVista = new dashboard(this);
		else if (nombre.equals("productos"))
			nuevaVista = new productos(this);
		else if (nombre.equals("entradas"))
			nuevaVista = new entradas(this);
		else if (nombre.equals("salidas"))
			nuevaVista = new salidas(this);
		else if (nombre.equals("provedores"))
			nuevaVista = new provedores(this);
		else if (nombre.equals("ordenes"))
			nuevaVista = new ordenes(this);
		else if (nombre.equals("reportes"))
			nuevaVista = new reportes(this);
		else if (nombre.equals("ventas"))
			nuevaVista = new ventas(this);

		if (nuevaVista != null) {
			nuevaVista.setName(nombre);
			container.add(nuevaVista, nombre);
		}

		cardlayout.show(container, nombre);
		container.revalidate();
		container.repaint();
	}

	public static void main(String[] args) {

		try {
			FlatLightLaf.setup();
		} catch (Exception ex) {
			System.err.println("Error cargando FlatLaf");
		}

		SwingUtilities.invokeLater(() -> {
			new executable().setVisible(true);
		});
	}
}
