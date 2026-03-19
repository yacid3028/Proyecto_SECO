package seco;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import seco.fcdb.productosDB;
import seco.fcdb.ventasDB;
import seco.ventanas.ventanaFactura;

public class salidas extends JPanel {

    private DefaultTableModel modeloTabla;
    private executable executable;
    private final Color COLOR_FONDO = new Color(240, 242, 245); // Fondo gris
    private final Color COLOR_AZUL_LATERAL = new Color(10, 20, 100); // Azul menú
    private final Color COLOR_AZUL_ACTIVO = new Color(33, 150, 243); // Azul selección
    private final Color COLOR_NARANJA = new Color(255, 133, 27); // Color de acento
    private final Color COLOR_DINERO_VERDE = new Color(40, 167, 69); // Verde para ingresos
    private final Color COLOR_BORDE_GRIS = new Color(225, 230, 235); // Marcos de cuadros
    private final Color COLOR_TEXTO_SEC = new Color(110, 115, 130); // Texto secundario

    public salidas(executable frame) {
        this.executable = frame;
        this.setLayout(new BorderLayout());
        this.setBackground(COLOR_FONDO);

        Menu_lateral();
        crearPanelCentral();
    }

    private void Menu_lateral() {
        JPanel p = new JPanel();
        p.setPreferredSize(new Dimension(200, 0));
        p.setBackground(COLOR_AZUL_LATERAL);
        p.setLayout(new GridLayout(10, 1, 0, 1));
        add(p, BorderLayout.WEST);

        ImageIcon lg = new ImageIcon("img/logo.png");
        Icon in = new ImageIcon(lg.getImage().getScaledInstance(70, 60, Image.SCALE_SMOOTH));
        JButton titulo = new JButton("Stock System", in);
        titulo.setHorizontalTextPosition(SwingConstants.RIGHT);
        titulo.setBackground(new Color(0, 0, 0, 0));
        titulo.setFocusPainted(false);
        titulo.setBorder(null);
        titulo.setForeground(Color.white);
        titulo.setFont(new Font("Arial", Font.ITALIC, 15));
        p.add(titulo);

        ImageIcon dashi = new ImageIcon("img/casa_icono.jpg");
        Icon dsh = new ImageIcon(dashi.getImage().getScaledInstance(30, 25, Image.SCALE_SMOOTH));

        p.add(crearBotonNavegacion("Dashboard", "dashboard", dsh));
        p.add(crearBotonNavegacion("Productos", "productos", dsh));
        p.add(crearBotonNavegacion("Entradas", "entradas", dsh));
        p.add(crearBotonNavegacion("Salidas", "salidas", dsh));
        p.add(crearBotonNavegacion("Provedores", "provedores", dsh));
        p.add(crearBotonNavegacion("Ordenes", "ordenes", dsh));
        p.add(crearBotonNavegacion("Reportes", "reportes", dsh));
        p.add(crearBotonNavegacion("Servicio", "ventas", dsh));
    }

    private JButton crearBotonNavegacion(String texto, String vista, Icon icono) {
        JButton boton = new JButton(texto, icono);
        if (texto.equals("Salidas")) {
            boton.setBackground(COLOR_AZUL_ACTIVO);
        } else {
            boton.setBackground(COLOR_AZUL_LATERAL);
        }
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setHorizontalAlignment(SwingConstants.LEFT);
        boton.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        boton.addActionListener(e -> executable.mostrarVista(vista));
        return boton;
    }

    private void crearPanelCentral() {
        JPanel main = new JPanel(new BorderLayout(0, 25));
        main.setBackground(COLOR_FONDO);
        main.setBorder(new EmptyBorder(30, 40, 30, 40));

        // CABECERA CON FILTRO Y SELECTOR DE TIEMPO
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JLabel lblTitle = new JLabel("Resumen de Salidas");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));

        JPanel pAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        pAcciones.setOpaque(false);

        // Selector de periodo (Día, Semana, Mes, Año)

        String[] periodos = { "Hoy", "Semana Pasada", "Precio D", "Precio A" };

        JComboBox<String> cbPeriodo = new JComboBox<>(periodos);
        cbPeriodo.setPreferredSize(new Dimension(130, 35));
        cbPeriodo.setBackground(Color.WHITE);
        cbPeriodo.addActionListener(e -> {
            String seleccion = (String) cbPeriodo.getSelectedItem();
            filtrarVentas(seleccion);
        });

        // Barra de búsqueda por producto/fecha

        JTextField txtFiltro = new JTextField("");

        txtFiltro.setPreferredSize(new Dimension(200, 35));
        txtFiltro.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE_GRIS),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));

        txtFiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                String texto = txtFiltro.getText();

                if (texto.isEmpty()) {
                    refrescarTabla();
                } else {
                    modeloTabla.setRowCount(0); // limpiar tabla
                    ventasDB db = new ventasDB();
                    db.buscarVentas(texto.toUpperCase(), modeloTabla);
                }
            }
        });

        pAcciones.add(new JLabel("Categoria:"));
        pAcciones.add(cbPeriodo);
        pAcciones.add(new JLabel("Filtrar Id:"));
        pAcciones.add(txtFiltro);

        header.add(lblTitle, BorderLayout.WEST);
        header.add(pAcciones, BorderLayout.EAST);

        // TARJETAS DINÁMICAS
        JPanel tarjetas = new JPanel(new GridLayout(1, 3, 20, 0));
        tarjetas.setOpaque(false);
        tarjetas.add(crearTarjeta("Cantidad Vendida", "342 unid.", Color.BLACK));
        tarjetas.add(crearTarjeta("Ingreso Total", "$24,500.00", COLOR_DINERO_VERDE));
        tarjetas.add(crearTarjeta("Productos Restantes", "1,120 unid.", COLOR_NARANJA));

        // CUADRO DE LA TABLA
        JPanel contenedorTabla = new JPanel(new BorderLayout());
        contenedorTabla.setBackground(Color.WHITE);
        contenedorTabla.setBorder(BorderFactory.createLineBorder(COLOR_BORDE_GRIS));

        String[] columnas = { "ID VENTA", "PRODUCTO", "CANTIDAD", "TOTAL", "FACTURA", "FECHA" };
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ventasDB vdb = new ventasDB();
        vdb.consultarVentas(modeloTabla);

        JTable tabla = new JTable(modeloTabla);

        tabla.setRowHeight(40);
        tabla.getTableHeader().setReorderingAllowed(false);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);

        // PAGINACIÓN
        JPanel paginacion = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 10));
        paginacion.setBackground(Color.WHITE);

        JButton b1 = new JButton("Ver Factura");
        b1.setBackground(COLOR_NARANJA);
        b1.setForeground(Color.WHITE);
        b1.addActionListener(e -> {

            int fila = tabla.getSelectedRow();

            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Selecciona una venta");
                return;
            }

            String factura = tabla.getValueAt(fila, 4).toString();
            String fecha = tabla.getValueAt(fila, 5).toString();
            String pedido = tabla.getValueAt(fila, 0).toString();

            ventanaFactura vf = new ventanaFactura(factura, fecha, pedido);
            vf.setVisible(true);

        });

        paginacion.add(b1);

        contenedorTabla.add(scroll, BorderLayout.CENTER);
        contenedorTabla.add(paginacion, BorderLayout.SOUTH);

        // ENSAMBLADO FINAL
        JPanel areaSuperior = new JPanel(new BorderLayout(0, 20));
        areaSuperior.setOpaque(false);
        areaSuperior.add(header, BorderLayout.NORTH);
        areaSuperior.add(tarjetas, BorderLayout.CENTER);

        main.add(areaSuperior, BorderLayout.NORTH);
        main.add(contenedorTabla, BorderLayout.CENTER);
        add(main, BorderLayout.CENTER);
    }

    private JPanel crearTarjeta(String titulo, String valor, Color colorValor) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE_GRIS),
                new EmptyBorder(15, 15, 15, 15)));

        JLabel t = new JLabel(titulo);
        t.setForeground(COLOR_TEXTO_SEC);
        JLabel v = new JLabel(valor);
        v.setFont(new Font("Segoe UI", Font.BOLD, 24));
        v.setForeground(colorValor);

        p.add(t, BorderLayout.NORTH);
        p.add(v, BorderLayout.CENTER);
        return p;
    }

    public void refrescarTabla() {
        modeloTabla.setRowCount(0);
        ventasDB vdb = new ventasDB();
        vdb.consultarVentas(modeloTabla);
    }

    private void filtrarVentas(String filtro) {
        modeloTabla.setRowCount(0); // limpia la tabla
        ventasDB vdb = new ventasDB();

        switch (filtro) {
            case "Hoy":
                String hoy = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
                vdb.consultarVentasPorFecha(modeloTabla, hoy);
                break;
            case "Semana Pasada":
                vdb.consultarVentasPorSemana(modeloTabla);
                break;
            case "Precio D":
                vdb.consultarVentasOrdenPrecio(modeloTabla, "DESC");
                break;
            case "Precio A":
                vdb.consultarVentasOrdenPrecio(modeloTabla, "ASC");
                break;
        }
    }
}