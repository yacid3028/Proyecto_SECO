package seco;

import java.awt.*;
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

    public entradas(executable frame) throws Exception {
        this.executable = frame;
        this.setLayout(new BorderLayout());
        this.setBackground(COLOR_FONDO);

        Menu_lateral();
        crearPanelCentral();
        cargarEntradas();
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

    private void cargarEntradas() throws Exception {
        db.consultarEntradas(modelo);
        refrescarTarjetas();
    }

    private void refrescarTarjetas() throws Exception {
        int entradasHoy = db.contarEntradasHoy();
        int totalProductos = db.sumarCantidadesTotales();
        actualizarTarjeta(tarjetaEntradasHoy, "Entradas Hoy", String.valueOf(entradasHoy), Color.BLACK);
        actualizarTarjeta(tarjetaProductos, "Productos", String.valueOf(totalProductos), Color.BLACK);
    }

    private void actualizarTarjeta(JPanel tarjeta, String titulo, String valor, Color colorValor) {
        tarjeta.removeAll();
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

    // ==========================================
    //            AQUÍ EDITAR
    // ==========================================
    private void mostrarDialogoEntrada(boolean esEdicion) {
        String titulo = esEdicion ? "Editar Entrada" : "Nueva Entrada";
        String botonTexto = esEdicion ? "Guardar" : "Agregar";
        final String[] idSeleccionado = new String[] { "" };
        String producto = "", proveedor = "";
        int cantidad = 1;

        if (esEdicion) {
            int fila = tabla.getSelectedRow();
            
            // Verificamos que se haya seleccionado una fila (Evita el error de índice -1)
            if (fila < 0) {
                JOptionPane.showMessageDialog(this, "Seleccione una fila para editar.");
                return;
            }

            // Verificamos que la celda no sea nula antes de usar toString()
            Object objId = tabla.getValueAt(fila, 0);
            if (objId == null) {
                JOptionPane.showMessageDialog(this, "La fila seleccionada no contiene un ID válido.");
                return;
            }

            idSeleccionado[0] = objId.toString();
            producto = String.valueOf(tabla.getValueAt(fila, 2));
            proveedor = String.valueOf(tabla.getValueAt(fila, 3));
            
            Object objCant = tabla.getValueAt(fila, 4);
            cantidad = (objCant != null) ? Integer.parseInt(objCant.toString()) : 1;
        }

        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(this), titulo, Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setSize(350, 250);
        dialog.setLocationRelativeTo(this);
        JPanel form = new JPanel(new GridLayout(0, 2, 10, 10));
        form.setBorder(new EmptyBorder(15, 15, 15, 15));

        JTextField tfProducto = new JTextField(producto);
        JTextField tfProveedor = new JTextField(proveedor);
        JSpinner spCantidad = new JSpinner(new SpinnerNumberModel(cantidad, 1, 9999, 1));

        form.add(new JLabel("Producto:")); form.add(tfProducto);
        form.add(new JLabel("Proveedor:")); form.add(tfProveedor);
        form.add(new JLabel("Cantidad:")); form.add(spCantidad);

        JButton btnAccion = new JButton(botonTexto);
        btnAccion.addActionListener(e -> {
            try {
                if (esEdicion) {
                    db.actualizarEntrada(idSeleccionado[0], tfProducto.getText(), tfProveedor.getText(), (int) spCantidad.getValue());
                } else {
                    db.agregarEntrada(tfProducto.getText(), tfProveedor.getText(), (int) spCantidad.getValue());
                }
                cargarEntradas();
                dialog.dispose();
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        dialog.add(form, BorderLayout.CENTER);
        dialog.add(btnAccion, BorderLayout.SOUTH);
        dialog.setVisible(true);
        
    }

    // ==========================================
    //            AQUÍ ELIMINAR
    // ==========================================
    private void eliminarEntradaSeleccionada() {
        int fila = tabla.getSelectedRow();
        
        // Validación para evitar error si no hay fila seleccionada
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una fila para eliminar.");
            return;
        }
        
        // Validación para evitar NullPointerException al invocar toString()
        Object objId = tabla.getValueAt(fila, 0);
        if (objId == null) {
            JOptionPane.showMessageDialog(this, "No se puede obtener el ID de la fila seleccionada.");
            return;
        }

        String id = objId.toString();
        
        if (JOptionPane.showConfirmDialog(this, "¿Eliminar entrada con ID: " + id + "?") == JOptionPane.YES_OPTION) {
            try {
                db.eliminarEntrada(id);
                cargarEntradas();
                JOptionPane.showMessageDialog(this, "Entrada eliminada con éxito.");
            } catch (Exception e) { 
                e.printStackTrace(); 
                JOptionPane.showMessageDialog(this, "Error al eliminar: " + e.getMessage());
            }
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