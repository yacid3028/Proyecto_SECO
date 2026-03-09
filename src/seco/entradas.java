package seco;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class entradas extends JPanel {
    private executable executable;

    // Colores exactos de tu diseño
    private final Color COLOR_FONDO = new Color(240, 242, 245);
    private final Color COLOR_ACCENTO = new Color(255, 133, 27); 
    private final Color COLOR_AZUL_LATERAL = new Color(10, 20, 100);
    private final Color COLOR_TEXTO_TITULO = new Color(30, 35, 50);
    private final Color COLOR_TEXTO_SEC = new Color(110, 115, 130);
    private final Color COLOR_BORDE = new Color(225, 230, 235);

    public entradas(executable frame) {
        this.executable = frame;
        this.setLayout(new BorderLayout());
        this.setBackground(COLOR_FONDO);//ok
        
        Menu_lateral();
        crearPanelCentral();
    }

    // --- PANEL LATERAL: RESPETANDO TU DISEÑO ORIGINAL AL 100% ---
    private void Menu_lateral() {
        JPanel p = new JPanel();
        p.setPreferredSize(new Dimension(200, 0)); // Tu tamaño original
        p.setBackground(COLOR_AZUL_LATERAL);
        p.setLayout(new GridLayout(10, 1, 0, 1)); // Tu distribución original
        add(p, BorderLayout.WEST);

        // Logo con la lógica exacta de tu código
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

        // Icono de casa original
        ImageIcon dashi = new ImageIcon("img/casa_icono.jpg");
        Icon dsh = new ImageIcon(dashi.getImage().getScaledInstance(30, 25, Image.SCALE_SMOOTH));

        // Botones con tus nombres y rutas originales
        p.add(crearBotonOriginal("Dashboard", "dashboard", dsh));
        p.add(crearBotonOriginal("Productos", "productos", dsh));
        p.add(crearBotonOriginal("Entradas", "entradas", dsh));
        p.add(crearBotonOriginal("Salidas", "salidas", dsh));
        p.add(crearBotonOriginal("Provedores", "provedores", dsh));
        p.add(crearBotonOriginal("Ordenes", "ordenes", dsh));
        p.add(crearBotonOriginal("Reportes", "reportes", dsh));
        p.add(crearBotonOriginal("Servicio", "ventas", dsh));
    }

    private JButton crearBotonOriginal(String texto, String vista, Icon icono) {
        JButton boton = new JButton(texto, icono);
        // Respetamos tu lógica de color para el botón activo
        if (texto.equals("Entradas")) {
            boton.setBackground(new Color(33, 150, 243));
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

    // --- PANEL CENTRAL: CON BOTONES DE ACCIÓN ARRIBA A LA DERECHA ---
    private void crearPanelCentral() {
        JPanel main = new JPanel(new BorderLayout(0, 25));
        main.setBackground(COLOR_FONDO);
        main.setBorder(new EmptyBorder(30, 40, 30, 40));

        // Header: Título a la izquierda, botones a la derecha
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        
        JLabel lblTitle = new JLabel("Historial de Entradas");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(COLOR_TEXTO_TITULO);

        // Grupo de botones superior derecho
        JPanel panelAccionesSup = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelAccionesSup.setOpaque(false);

        JButton btnEditar = crearBotonEstilizado("Editar", new Color(33, 150, 243));
        JButton btnEliminar = crearBotonEstilizado("Eliminar", new Color(230, 50, 50));
        JButton btnNuevo = crearBotonEstilizado("+ Nueva Entrada", COLOR_ACCENTO);
        
        panelAccionesSup.add(btnEditar);
        panelAccionesSup.add(btnEliminar);
        panelAccionesSup.add(btnNuevo);

        header.add(lblTitle, BorderLayout.WEST);
        header.add(panelAccionesSup, BorderLayout.EAST);

        // Tarjetas de Resumen
        JPanel panelTarjetas = new JPanel(new GridLayout(1, 3, 20, 0));
        panelTarjetas.setOpaque(false);
        panelTarjetas.add(crearCard("Entradas Hoy", "12", "ACTIVO"));
        panelTarjetas.add(crearCard("Productos", "145", "TOTAL"));
        panelTarjetas.add(crearCard("Valor Ingreso", "$4,520", "USD"));

        // Tabla
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBackground(Color.WHITE);
        tableContainer.setBorder(BorderFactory.createLineBorder(COLOR_BORDE));

        JTable tabla = crearTabla();
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);
        tableContainer.add(scroll, BorderLayout.CENTER);

        // Footer (Paginación numérica)
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(Color.WHITE);
        footer.setPreferredSize(new Dimension(0, 50));
        footer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, COLOR_BORDE));
        footer.add(crearPaginacionNumerica(), BorderLayout.EAST);
        tableContainer.add(footer, BorderLayout.SOUTH);

        // Ensamblado
        JPanel top = new JPanel(new BorderLayout(0, 20));
        top.setOpaque(false);
        top.add(header, BorderLayout.NORTH);
        top.add(panelTarjetas, BorderLayout.CENTER);

        main.add(top, BorderLayout.NORTH);
        main.add(tableContainer, BorderLayout.CENTER);
        add(main, BorderLayout.CENTER);
    }

    private JButton crearBotonEstilizado(String t, Color bg) {
        JButton b = new JButton(t);
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 12));
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        return b;
    }

    private JTable crearTabla() {
        String[] cols = {"ID", "FECHA", "PRODUCTO", "PROVEEDOR", "CANTIDAD", "ESTADO"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        for(int i=1; i<=10; i++) model.addRow(new Object[]{"#00"+i, "09/03/26", "Producto Ejemplo", "Proveedor X", "50", "DISPONIBLE"});
        
        JTable t = new JTable(model);
        t.setRowHeight(40);
        t.setShowGrid(false);
        return t;
    }

    private JPanel crearPaginacionNumerica() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 10));
        p.setOpaque(false);
        String[] pags = {"Anterior", "1", "2", "3", "Siguiente"};
        for (String pg : pags) {
            JButton b = new JButton(pg);
            b.setPreferredSize(new Dimension(pg.length() > 2 ? 80 : 35, 30));
            b.setBackground(pg.equals("1") ? COLOR_ACCENTO : Color.WHITE);
            b.setForeground(pg.equals("1") ? Color.WHITE : COLOR_TEXTO_SEC);
            p.add(b);
        }
        return p;
    }

    private JPanel crearCard(String t, String v, String s) {
        JPanel c = new JPanel(new BorderLayout());
        c.setBackground(Color.WHITE);
        c.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(COLOR_BORDE), BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        JLabel val = new JLabel(v); val.setFont(new Font("Segoe UI", Font.BOLD, 22));
        c.add(new JLabel(t), BorderLayout.NORTH);
        c.add(val, BorderLayout.CENTER);
        return c;
    }
}