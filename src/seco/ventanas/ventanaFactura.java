package seco.ventanas;

import javax.swing.*;

import java.awt.*;

import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;

public class ventanaFactura extends JFrame {

    // DATOS CLIENTE
    JLabel lblCliente;
    JLabel lblDireccionCliente;

    JLabel lblEnviarA;
    JLabel lblDireccionEnvio;

    // DATOS FACTURA
    JLabel lblNumFactura;
    JLabel lblFecha;
    JLabel lblPedido;
    JLabel lblVencimiento;

    // TABLA
    JTable tablaProductos;

    // TOTALES
    JLabel lblSubtotal;
    JLabel lblIVA;
    JLabel lblTotal;

    public ventanaFactura(String factura) {

        setTitle("Factura - " + factura);
        setSize(800, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panel principal con imagen de fondo
        JPanel panelPrincipal = new JPanel(new BorderLayout(0, 15)) {
            private Image imagenFondo;

            {
                // Descomenta y ajusta la ruta cuando tengas la imagen:
                // imagenFondo = new ImageIcon("ruta/a/fondo.png").getImage();
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagenFondo != null) {
                    g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
                } else {
                    // Fondo por defecto mientras no hay imagen
                    g.setColor(new Color(245, 247, 250));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };

        // Margen general alrededor de todo el contenido
        panelPrincipal.setBorder(new EmptyBorder(20, 30, 20, 30));
        setContentPane(panelPrincipal);

        // ================ HEADER ================

        JPanel panelHeader = new JPanel(new BorderLayout(10, 5));
        panelHeader.setOpaque(false);
        panelHeader.setBorder(new EmptyBorder(0, 0, 15, 0)); // espacio debajo del header

        // Lado izquierdo: título + datos empresa
        JPanel panelHeaderIzq = new JPanel(new GridLayout(2, 1, 0, 8));
        panelHeaderIzq.setOpaque(false);

        JLabel titulo = new JLabel("FACTURA");
        titulo.setFont(new Font("Arial", Font.BOLD, 36));
        titulo.setForeground(new Color(40, 70, 120));
        panelHeaderIzq.add(titulo);

        JLabel empresa = new JLabel(
                "<html>Rojo Polo Paella Inc.<br>" +
                        "Carretera Muelle 38<br>" +
                        "37531 Ávila, Ávila</html>");
        empresa.setFont(new Font("Arial", Font.PLAIN, 12));
        panelHeaderIzq.add(empresa);

        panelHeader.add(panelHeaderIzq, BorderLayout.WEST);

        // Esquina superior derecha: logo de la empresa
        JLabel lblLogo = new JLabel() {
            private Image logo;

            {
                // Descomenta y ajusta la ruta cuando tengas el logo:
                // ImageIcon icon = new ImageIcon("ruta/a/logo.png");
                // logo = icon.getImage().getScaledInstance(120, 80, Image.SCALE_SMOOTH);
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (logo != null) {
                    g.drawImage(logo, 0, 0, getWidth(), getHeight(), this);
                } else {
                    // Placeholder gris mientras no hay logo
                    g.setColor(new Color(200, 200, 210));
                    g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
                    g.setColor(new Color(130, 130, 150));
                    g.setFont(new Font("Arial", Font.PLAIN, 11));
                    FontMetrics fm = g.getFontMetrics();
                    String txt = "LOGO";
                    g.drawString(txt, (getWidth() - fm.stringWidth(txt)) / 2, getHeight() / 2 + 4);
                }
            }
        };
        lblLogo.setPreferredSize(new Dimension(120, 80));
        panelHeader.add(lblLogo, BorderLayout.EAST);

        panelPrincipal.add(panelHeader, BorderLayout.NORTH);

        // ================ DATOS CLIENTE ================

        JPanel panelDatos = new JPanel(new GridLayout(1, 3, 20, 0));
        panelDatos.setOpaque(false);
        panelDatos.setBorder(new EmptyBorder(0, 0, 15, 0)); // espacio debajo

        // FACTURAR A
        JPanel facturar = new JPanel(new GridLayout(3, 1, 0, 5));
        facturar.setOpaque(false);
        facturar.setBorder(new EmptyBorder(5, 8, 5, 8));

        JLabel tituloFacturar = new JLabel("FACTURAR A");
        tituloFacturar.setFont(new Font("Arial", Font.BOLD, 11));
        tituloFacturar.setForeground(new Color(40, 70, 120));
        facturar.add(tituloFacturar);

        lblCliente = new JLabel("Nombre cliente");
        lblDireccionCliente = new JLabel("Dirección cliente");
        facturar.add(lblCliente);
        facturar.add(lblDireccionCliente);

        // ENVIAR A
        JPanel enviar = new JPanel(new GridLayout(3, 1, 0, 5));
        enviar.setOpaque(false);
        enviar.setBorder(new EmptyBorder(5, 8, 5, 8));

        JLabel tituloEnviar = new JLabel("ENVIAR A");
        tituloEnviar.setFont(new Font("Arial", Font.BOLD, 11));
        tituloEnviar.setForeground(new Color(40, 70, 120));
        enviar.add(tituloEnviar);

        lblEnviarA = new JLabel("Nombre envio");
        lblDireccionEnvio = new JLabel("Dirección envio");
        enviar.add(lblEnviarA);
        enviar.add(lblDireccionEnvio);

        // DATOS FACTURA
        JPanel datosFactura = new JPanel(new GridLayout(4, 2, 10, 6));
        datosFactura.setOpaque(false);
        datosFactura.setBorder(new EmptyBorder(5, 8, 5, 8));

        JLabel[] encabezados = {
                new JLabel("N° FACTURA"), new JLabel("FECHA"),
                new JLabel("N° PEDIDO"), new JLabel("VENCIMIENTO")
        };
        for (JLabel lbl : encabezados) {
            lbl.setFont(new Font("Arial", Font.BOLD, 11));
            lbl.setForeground(new Color(40, 70, 120));
        }

        datosFactura.add(encabezados[0]);
        lblNumFactura = new JLabel(factura);
        datosFactura.add(lblNumFactura);

        datosFactura.add(encabezados[1]);
        lblFecha = new JLabel("fecha");
        datosFactura.add(lblFecha);

        datosFactura.add(encabezados[2]);
        lblPedido = new JLabel("pedido");
        datosFactura.add(lblPedido);

        datosFactura.add(encabezados[3]);
        lblVencimiento = new JLabel("vencimiento");
        datosFactura.add(lblVencimiento);

        panelDatos.add(facturar);
        panelDatos.add(enviar);
        panelDatos.add(datosFactura);

        panelPrincipal.add(panelDatos, BorderLayout.CENTER);

        // ================ CENTRO (TABLA + TOTALES) ================

        JPanel panelCentro = new JPanel(new BorderLayout(0, 10));
        panelCentro.setOpaque(false);

        // TABLA
        String[] columnas = { "Cant", "Descripción", "Precio Unitario", "Importe" };
        Object[][] datos = {};

        tablaProductos = new JTable(datos, columnas);
        tablaProductos.setRowHeight(28);
        tablaProductos.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaProductos.setGridColor(new Color(220, 220, 230));
        tablaProductos.setIntercellSpacing(new Dimension(10, 5));

        JTableHeader header = tablaProductos.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 12));
        header.setBackground(new Color(40, 70, 120));
        header.setForeground(Color.WHITE);

        JScrollPane scrollTabla = new JScrollPane(tablaProductos);
        scrollTabla.setPreferredSize(new Dimension(0, 220));
        scrollTabla.setBorder(BorderFactory.createLineBorder(new Color(200, 205, 215)));
        panelCentro.add(scrollTabla, BorderLayout.CENTER);

        // TOTALES
        JPanel panelTotales = new JPanel(new BorderLayout());
        panelTotales.setOpaque(false);
        panelTotales.setBorder(new EmptyBorder(10, 0, 0, 0));

        JPanel gridTotales = new JPanel(new GridLayout(3, 2, 20, 8));
        gridTotales.setOpaque(false);

        gridTotales.add(new JLabel("Subtotal"));
        lblSubtotal = new JLabel("$0.00");
        gridTotales.add(lblSubtotal);

        gridTotales.add(new JLabel("IVA (16%)"));
        lblIVA = new JLabel("$0.00");
        gridTotales.add(lblIVA);

        JLabel lblTituloTotal = new JLabel("TOTAL");
        lblTituloTotal.setFont(new Font("Arial", Font.BOLD, 14));
        gridTotales.add(lblTituloTotal);

        lblTotal = new JLabel("$0.00");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 16));
        lblTotal.setForeground(new Color(40, 70, 120));
        gridTotales.add(lblTotal);

        JButton btnExportar = new JButton("Exportar PDF");
        btnExportar.setPreferredSize(new Dimension(150, 35));
        btnExportar.setBackground(new Color(23,184,23));

        panelTotales.add(btnExportar, BorderLayout.WEST);
        panelTotales.add(gridTotales, BorderLayout.EAST);
        panelCentro.add(panelTotales, BorderLayout.SOUTH);

        panelPrincipal.add(panelCentro, BorderLayout.SOUTH);
    }

}