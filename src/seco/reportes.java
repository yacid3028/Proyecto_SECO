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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class reportes extends JPanel {
	private executable executable;

	public reportes(executable frame) {
		this.executable = frame;
		setLayout(new BorderLayout());
		Menu_lateral();
		contenidoReportes();
	}
	

	//MENU LATERAL ELEMENTOS, BOTONES, ACCIONES
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
		if (texto == "Reportes") {
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
   
	//CONTENIDO PRINCIPAL DE REPORTES
	private void contenidoReportes() {
		JPanel content = new JPanel(new BorderLayout());
		add(content, BorderLayout.CENTER);
		
        // PANEL SUPERIOR (TÍTULO + BOTONES EXPORTAR)
        JPanel panelSuperior = new JPanel(new BorderLayout());

        JLabel titulo = new JLabel("Reportes");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        panelSuperior.add(titulo, BorderLayout.WEST);

        JPanel botonesExportar = new JPanel();

        JButton pdf = new JButton("Exportar PDF");
        pdf.setPreferredSize(new Dimension(140, 40));

        JButton excel = new JButton("Exportar Excel");
        excel.setPreferredSize(new Dimension(140, 40));

        JButton generar = new JButton("Generar Reporte");
        generar.setPreferredSize(new Dimension(140, 40));
        generar.setBackground(new Color(255,140,0));
        generar.setForeground(Color.WHITE);

        botonesExportar.add(pdf);
        botonesExportar.add(excel);
        botonesExportar.add(generar);

        // Acción para Generar Reporte
        generar.addActionListener(e -> {
            javax.swing.JOptionPane.showMessageDialog(null, "Reporte generado (simulado)");
        });

        panelSuperior.add(botonesExportar, BorderLayout.EAST);

        content.add(panelSuperior, BorderLayout.NORTH);

        // TARJETAS
        JPanel tarjetas = new JPanel(new GridLayout(1,4,10,10));
        tarjetas.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        tarjetas.add(crearTarjeta("Ventas Totales","$12,500"));
        tarjetas.add(crearTarjeta("Productos Vendidos","320"));
        tarjetas.add(crearTarjeta("Ingresos","$9,800"));
        tarjetas.add(crearTarjeta("Productos Bajos","7"));

        content.add(tarjetas, BorderLayout.CENTER);

        // TABLA
        String columnas[] = {"Producto","Categoría","Vendidos","Ingresos"};

        DefaultTableModel modelo = new DefaultTableModel(null,columnas);

        modelo.addRow(new Object[]{"Teclado inalámbrico","Electrónica","120","$3000"});
        modelo.addRow(new Object[]{"Camiseta negra","Ropa","80","$1600"});
        modelo.addRow(new Object[]{"Café molido","Alimentos","60","$900"});
        modelo.addRow(new Object[]{"Mouse gamer","Electrónica","60","$1800"});

        JTable tabla = new JTable(modelo);

        JScrollPane scroll = new JScrollPane(tabla);

        content.add(scroll, BorderLayout.SOUTH);
	}
	
	// Crear tarjetas
    private JPanel crearTarjeta(String titulo, String valor){

        JPanel panel = new JPanel(new GridLayout(2,1));
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
