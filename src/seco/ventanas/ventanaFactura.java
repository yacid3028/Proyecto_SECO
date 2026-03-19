package seco.ventanas;

import javax.swing.*;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.graphics.image.*;

import seco.conexionbd;

import org.apache.pdfbox.pdmodel.common.PDRectangle;

import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.*;
import seco.conexionbd;

import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
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

    public ventanaFactura(String factura, String fecha, String pedido) {

        setTitle("Factura - " + factura);
        setSize(800, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String rfc = JOptionPane.showInputDialog(null, "RFC del cliente:", "Datos del cliente",
                JOptionPane.PLAIN_MESSAGE);
        String nombre = JOptionPane.showInputDialog(null, "Nombre / Razón social:", "Datos del cliente",
                JOptionPane.PLAIN_MESSAGE);
        String direccion = JOptionPane.showInputDialog(null, "Dirección fiscal:", "Datos del cliente",
                JOptionPane.PLAIN_MESSAGE);

        ImageIcon iconFondo = new ImageIcon("img/fondo.png");
        Image imagenFondo = iconFondo.getImageLoadStatus() == MediaTracker.COMPLETE
                ? iconFondo.getImage()
                : null;

        JPanel panelPrincipal = new JPanel(new BorderLayout(0, 15)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagenFondo != null) {
                    g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        panelPrincipal.setOpaque(false);
        panelPrincipal.setBorder(new EmptyBorder(20, 30, 20, 30));
        setContentPane(panelPrincipal);

        JPanel panelHeader = new JPanel(new BorderLayout(10, 5));
        panelHeader.setOpaque(false);
        panelHeader.setBorder(new EmptyBorder(0, 0, 15, 0));

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

        ImageIcon iconLogo = new ImageIcon("ruta/a/logo.png");
        JLabel lblLogo = new JLabel(iconLogo);
        lblLogo.setPreferredSize(new Dimension(120, 80));
        lblLogo.setPreferredSize(new Dimension(120, 80));
        panelHeader.add(lblLogo, BorderLayout.EAST);

        panelPrincipal.add(panelHeader, BorderLayout.NORTH);

        // ================ DATOS CLIENTE ================

        JPanel panelDatos = new JPanel(new GridLayout(1, 3, 20, 0));
        panelDatos.setOpaque(false);
        panelDatos.setBorder(new EmptyBorder(0, 0, 15, 0)); // espacio debajo

        // FACTURAR A
        JPanel facturar = new JPanel(new GridLayout(4, 1, 0, 5));
        facturar.setOpaque(false);
        facturar.setBorder(new EmptyBorder(5, 8, 5, 8));

        JLabel tituloFacturar = new JLabel("FACTURAR A");
        tituloFacturar.setFont(new Font("Arial", Font.BOLD, 11));
        tituloFacturar.setForeground(new Color(40, 70, 120));
        facturar.add(tituloFacturar);

        lblCliente = new JLabel(nombre != null ? nombre : "Sin nombre");

        lblDireccionCliente = new JLabel(direccion != null ? direccion : "Sin dirección");

        JLabel lblRFC = new JLabel(rfc != null ? rfc : "Sin RFC");
        facturar.add(lblRFC);
        facturar.add(lblCliente);
        facturar.add(lblDireccionCliente);

        // ENVIAR A
        JPanel enviar = new JPanel(new GridLayout(3, 1, 0, 5));
        enviar.setOpaque(false);
        enviar.setBorder(new EmptyBorder(5, 8, 5, 8));

        JLabel tituloEnviar = new JLabel("ESTABLECIMIENTO");
        tituloEnviar.setFont(new Font("Arial", Font.BOLD, 11));
        tituloEnviar.setForeground(new Color(40, 70, 120));
        enviar.add(tituloEnviar);

        lblEnviarA = new JLabel("Tienda Don Julion ");
        lblDireccionEnvio = new JLabel("Calle de las Crudas 54832");
        enviar.add(lblEnviarA);
        enviar.add(lblDireccionEnvio);

        // DATOS FACTURA
        JPanel datosFactura = new JPanel(new GridLayout(4, 2, 10, 6));
        datosFactura.setOpaque(false);
        datosFactura.setBorder(new EmptyBorder(5, 8, 5, 8));

        JLabel lblTituloNumFactura = new JLabel("N° FACTURA");
        lblTituloNumFactura.setFont(new Font("Arial", Font.BOLD, 11));
        lblTituloNumFactura.setForeground(new Color(40, 70, 120));

        JLabel lblTituloFecha = new JLabel("FECHA");
        lblTituloFecha.setFont(new Font("Arial", Font.BOLD, 11));
        lblTituloFecha.setForeground(new Color(40, 70, 120));

        JLabel lblTituloPedido = new JLabel("N° PEDIDO");
        lblTituloPedido.setFont(new Font("Arial", Font.BOLD, 11));
        lblTituloPedido.setForeground(new Color(40, 70, 120));

        JLabel lblTituloVencimiento = new JLabel("VENCIMIENTO");
        lblTituloVencimiento.setFont(new Font("Arial", Font.BOLD, 11));
        lblTituloVencimiento.setForeground(new Color(40, 70, 120));

        lblNumFactura = new JLabel(factura);
        datosFactura.add(lblNumFactura);
        lblFecha = new JLabel(fecha);
        datosFactura.add(lblFecha);
        lblPedido = new JLabel(pedido);
        datosFactura.add(lblPedido);
        String venc = fecha.substring(3, 5);
        String tt = "" + (Integer.parseInt(venc) + 1);
        String rep = fecha.replace(venc, tt);
        lblVencimiento = new JLabel(rep);
        datosFactura.add(lblVencimiento);

        panelDatos.add(facturar);
        panelDatos.add(enviar);
        panelDatos.add(datosFactura);

        panelPrincipal.add(panelDatos, BorderLayout.CENTER);

        JPanel panelCentro = new JPanel(new BorderLayout(0, 10));
        panelCentro.setOpaque(false);

        // TABLA
        String[] columnas = { "Cant", "Descripción", "Precio Unitario", "Importe" };

        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        double sub = 0;
        Connection con = conexionbd.conect();
        String sql = "SELECT  id_Venta,Producto, Cantidad,Total FROM Salidas";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                if (rs.getString("id_Venta").equals(pedido)) {
                    String producto = rs.getString("Producto");
                    int cantidad = rs.getInt("Cantidad");
                    double total = rs.getDouble("Total");
                    Statement stt = con.createStatement();
                    ResultSet rss = stt
                            .executeQuery(
                                    "SELECT Nombre , Precio_de_venta FROM Productos WHERE Nombre='" + producto + "'");
                    while (rss.next()) {

                        modelo.addRow(new Object[] {
                                cantidad,
                                producto,
                                "$ " + Math.round((rss.getInt("Precio_de_venta") / 1.16) * 100) / 100,
                                (Math.round((rss.getInt("Precio_de_venta") / 1.16) * 100) / 100) * cantidad
                        });
                        sub += total;
                    }
                }

            }
        } catch (Exception e) {
            System.out.print("erorr de tabla");
        }

        tablaProductos = new JTable(modelo);
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
        lblSubtotal = new JLabel("$ " + sub);
        gridTotales.add(lblSubtotal);

        gridTotales.add(new JLabel("IVA (16%)"));
        lblIVA = new JLabel("$ " + Math.round((sub * 0.16) * 100.0) / 100.0);
        gridTotales.add(lblIVA);

        JLabel lblTituloTotal = new JLabel("TOTAL");
        lblTituloTotal.setFont(new Font("Arial", Font.BOLD, 14));
        gridTotales.add(lblTituloTotal);

        lblTotal = new JLabel("$ " + (sub + (Math.round((sub * 0.16) * 100.0) / 100.0)));
        lblTotal.setFont(new Font("Arial", Font.BOLD, 16));
        lblTotal.setForeground(new Color(40, 70, 120));
        gridTotales.add(lblTotal);

        JButton btnExportar = new JButton("Exportar PDF");
        btnExportar.setPreferredSize(new Dimension(150, 35));
        btnExportar.setBackground(new Color(121, 203, 241));
        btnExportar.addActionListener(e -> {
            try {
                // 1. Capturar el panel como imagen
                BufferedImage imagen = new BufferedImage(
                        panelPrincipal.getWidth(),
                        panelPrincipal.getHeight(),
                        BufferedImage.TYPE_INT_RGB);
                Graphics2D g2d = imagen.createGraphics();
                panelPrincipal.printAll(g2d);
                g2d.dispose();

                // 2. Crear el PDF con PDFBox
                PDDocument documento = new PDDocument();
                PDPage pagina = new PDPage(new PDRectangle(
                        panelPrincipal.getWidth(),
                        panelPrincipal.getHeight()));
                documento.addPage(pagina);

                // 3. Insertar la imagen en la página
                PDImageXObject pdImage = LosslessFactory.createFromImage(documento, imagen);
                PDPageContentStream contenido = new PDPageContentStream(documento, pagina);
                contenido.drawImage(pdImage, 0, 0, pagina.getMediaBox().getWidth(), pagina.getMediaBox().getHeight());
                contenido.close();

                // 4. Guardar con JFileChooser para que el usuario elija dónde
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Guardar Factura PDF");
                fileChooser.setSelectedFile(new File("Factura_" + factura + ".pdf"));
                int opcion = fileChooser.showSaveDialog(null);

                if (opcion == JFileChooser.APPROVE_OPTION) {
                    File archivo = fileChooser.getSelectedFile();
                    // Asegura que tenga extensión .pdf
                    if (!archivo.getName().endsWith(".pdf")) {
                        archivo = new File(archivo.getAbsolutePath() + ".pdf");
                    }
                    documento.save(archivo);
                    documento.close();
                    JOptionPane.showMessageDialog(null, "✅ PDF guardado correctamente.");
                    dispose();
                } else {
                    documento.close();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "❌ Error al exportar: " + ex.getMessage());
            }
        });

        panelTotales.add(btnExportar, BorderLayout.WEST);
        panelTotales.add(gridTotales, BorderLayout.EAST);
        panelCentro.add(panelTotales, BorderLayout.SOUTH);

        panelPrincipal.add(panelCentro, BorderLayout.SOUTH);
    }

}