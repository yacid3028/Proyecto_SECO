package seco;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class entradas extends JPanel {

    private executable executable;
    private final Color COLOR_FONDO = new Color(240, 242, 245);
    private final Color COLOR_AZUL_LATERAL = new Color(10, 20, 100);
    private final Color COLOR_AZUL_ACTIVO = new Color(33, 150, 243);
    private final Color COLOR_NARANJA = new Color(255, 133, 27);
    private final Color COLOR_DINERO_VERDE = new Color(40, 167, 69);
    private final Color COLOR_BORDE_GRIS = new Color(225, 230, 235);
    private final Color COLOR_TEXTO_SEC = new Color(110, 115, 130);

    private DefaultTableModel modelo;
    private JTable tabla;
    private final seco.fcdb.entradasDB db = new seco.fcdb.entradasDB();

    private JPanel tarjetaEntradasHoy;
    private JPanel tarjetaProductos;
    private JPanel tarjetaValor;

    public entradas(executable frame) {
        this.executable = frame;
        this.setLayout(new BorderLayout());
        this.setBackground(COLOR_FONDO);

        Menu_lateral();
        crearPanelCentral();
        
        try {
            cargarEntradas();
        } catch (Exception e) {
            System.err.println("Error al cargar datos: " + e.getMessage());
        }
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
        boton.setBackground(texto.equals("Entradas") ? COLOR_AZUL_ACTIVO : COLOR_AZUL_LATERAL);
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

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JLabel lblTitle = new JLabel("Historial de Entradas");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));

        JPanel pBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pBotones.setOpaque(false);

        JButton btnEditar = crearBotonBlanco("Editar");
        JButton btnEliminar = crearBotonBlanco("Eliminar");
        JButton btnNueva = new JButton("Nueva Entrada");

        btnEditar.addActionListener(e -> mostrarDialogoEntrada(true));
        btnEliminar.addActionListener(e -> eliminarEntradaSeleccionada());
        btnNueva.addActionListener(e -> mostrarDialogoEntrada(false));

        btnNueva.setBackground(COLOR_NARANJA);
        btnNueva.setForeground(Color.WHITE);
        btnNueva.setFocusPainted(false);
        btnNueva.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE_GRIS),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));

        pBotones.add(btnEditar);
        pBotones.add(btnEliminar);
        pBotones.add(btnNueva);

        header.add(lblTitle, BorderLayout.WEST);
        header.add(pBotones, BorderLayout.EAST);

        JPanel tarjetas = new JPanel(new GridLayout(1, 3, 20, 0));
        tarjetas.setOpaque(false);
        tarjetaEntradasHoy = crearTarjeta("Entradas Hoy", "0", Color.BLACK);
        tarjetaProductos = crearTarjeta("Productos", "0", Color.BLACK);
        tarjetaValor = crearTarjeta("Valor Ingreso", "$0", COLOR_DINERO_VERDE);
        tarjetas.add(tarjetaEntradasHoy);
        tarjetas.add(tarjetaProductos);
        tarjetas.add(tarjetaValor);

        JPanel contenedorTabla = new JPanel(new BorderLayout());
        contenedorTabla.setBackground(Color.WHITE);
        contenedorTabla.setBorder(BorderFactory.createLineBorder(COLOR_BORDE_GRIS));

        String[] columnas = { "ID", "FECHA", "PRODUCTO", "PROVEDOR", "CANTIDAD", "ESTADO" };
        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tabla = new JTable(modelo);
        tabla.setRowHeight(40);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);

        JPanel paginacion = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 10));
        paginacion.setBackground(Color.WHITE);
        paginacion.add(new JLabel("Páginas: "));
        paginacion.add(new JButton("<"));
        JButton b1 = new JButton("1");
        b1.setBackground(COLOR_NARANJA);
        b1.setForeground(Color.WHITE);
        paginacion.add(b1);
        paginacion.add(new JButton("2"));
        paginacion.add(new JButton(">"));

        contenedorTabla.add(scroll, BorderLayout.CENTER);
        contenedorTabla.add(paginacion, BorderLayout.SOUTH);

        JPanel areaSuperior = new JPanel(new BorderLayout(0, 20));
        areaSuperior.setOpaque(false);
        areaSuperior.add(header, BorderLayout.NORTH);
        areaSuperior.add(tarjetas, BorderLayout.CENTER);

        main.add(areaSuperior, BorderLayout.NORTH);
        main.add(contenedorTabla, BorderLayout.CENTER);
        add(main, BorderLayout.CENTER);
    }

    private void cargarEntradas() {
        if (modelo == null) return;
        try {
            modelo.setRowCount(0);
            db.consultarEntradas(modelo);
            refrescarTarjetas();
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void refrescarTarjetas() {
        try {
            int entradasHoy = db.contarEntradasHoy();
            int totalProductos = db.sumarCantidadesTotales();
            
            String textoEntradas = entradasHoy == 1 ? "1 entrada" : entradasHoy + " entradas";
            String textoProductos = totalProductos == 1 ? "1 producto" : totalProductos + " productos";

            actualizarTarjeta(tarjetaEntradasHoy, "Entradas Hoy", textoEntradas, Color.BLACK);
            actualizarTarjeta(tarjetaProductos, "Productos", textoProductos, Color.BLACK);
            actualizarTarjeta(tarjetaValor, "Valor Ingreso", "$0", COLOR_DINERO_VERDE);
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void actualizarTarjeta(JPanel tarjeta, String titulo, String valor, Color colorValor) {
        tarjeta.removeAll();
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE_GRIS),
                new EmptyBorder(15, 15, 15, 15)
        ));
        JLabel t = new JLabel(titulo);
        t.setForeground(COLOR_TEXTO_SEC);
        JLabel v = new JLabel(valor);
        v.setFont(new Font("Segoe UI", Font.BOLD, 24));
        v.setForeground(colorValor);
        tarjeta.add(t, BorderLayout.NORTH);
        tarjeta.add(v, BorderLayout.CENTER);
        tarjeta.revalidate();
        tarjeta.repaint();
    }

    private void mostrarDialogoEntrada(boolean esEdicion) {
        String titulo = esEdicion ? "Editar Entrada" : "Nueva Entrada";
        String botonTexto = esEdicion ? "Guardar" : "Agregar";
        final int[] idSeleccionado = new int[] { -1 };
        String producto = "", proveedor = "";
        int cantidad = 1;

        if (esEdicion) {
            int fila = tabla.getSelectedRow();
            if (fila < 0) {
                JOptionPane.showMessageDialog(this, "Seleccione una fila.");
                return;
            }
            idSeleccionado[0] = (int) tabla.getValueAt(fila, 0);
            producto = String.valueOf(tabla.getValueAt(fila, 2));
            proveedor = String.valueOf(tabla.getValueAt(fila, 3));
            cantidad = Integer.parseInt(String.valueOf(tabla.getValueAt(fila, 4)));
        }

        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(this), titulo, Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setSize(400, 260);
        dialog.setLocationRelativeTo(this);
        JPanel form = new JPanel(new GridLayout(0, 2, 10, 10));
        form.setBorder(new EmptyBorder(15, 15, 15, 15));

        JTextField tfProducto = new JTextField(producto);
        JTextField tfProveedor = new JTextField(proveedor);
        JSpinner spCantidad = new JSpinner(new SpinnerNumberModel(cantidad, 1, Integer.MAX_VALUE, 1));

        form.add(new JLabel("Producto:")); form.add(tfProducto);
        form.add(new JLabel("Proveedor:")); form.add(tfProveedor);
        form.add(new JLabel("Cantidad:")); form.add(spCantidad);

        JButton btnGuardar = new JButton(botonTexto);
        btnGuardar.addActionListener(e -> {
            try {
                boolean ok;
                if (esEdicion) ok = db.actualizarEntrada(idSeleccionado[0], tfProducto.getText(), tfProveedor.getText(), (int) spCantidad.getValue());
                else ok = db.agregarEntrada(tfProducto.getText(), tfProveedor.getText(), (int) spCantidad.getValue());
                
                if (ok) { cargarEntradas(); dialog.dispose(); }
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        dialog.add(form, BorderLayout.CENTER);
        dialog.add(btnGuardar, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void eliminarEntradaSeleccionada() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) return;
        int id = (int) tabla.getValueAt(fila, 0);
        if (JOptionPane.showConfirmDialog(this, "¿Eliminar?") == JOptionPane.YES_OPTION) {
            try {
                if (db.eliminarEntrada(id)) cargarEntradas();
            } catch (Exception e) { e.printStackTrace(); }
        }
    }

    private JButton crearBotonBlanco(String t) {
        JButton b = new JButton(t);
        b.setBackground(Color.WHITE);
        b.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(COLOR_BORDE_GRIS), BorderFactory.createEmptyBorder(8, 15, 8, 15)));
        return b;
    }

    private JPanel crearTarjeta(String titulo, String valor, Color colorValor) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(COLOR_BORDE_GRIS), new EmptyBorder(15, 15, 15, 15)));
        JLabel t = new JLabel(titulo);
        t.setForeground(COLOR_TEXTO_SEC);
        JLabel v = new JLabel(valor);
        v.setFont(new Font("Segoe UI", Font.BOLD, 24));
        v.setForeground(colorValor);
        p.add(t, BorderLayout.NORTH);
        p.add(v, BorderLayout.CENTER);
        return p;
    }
}