package seco;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class entradas extends JPanel {

    // VARIABLES Y CONSTANTES
    private executable executable;
    private final Color COLOR_FONDO = new Color(240, 242, 245); // Color grisáceo para el fondo general
    private final Color COLOR_AZUL_LATERAL = new Color(10, 20, 100); // Azul oscuro para el menú
    private final Color COLOR_AZUL_ACTIVO = new Color(33, 150, 243); // Resalta el botón de la vista actual
    private final Color COLOR_NARANJA = new Color(255, 133, 27); // Color de marca para botones principales
    private final Color COLOR_DINERO_VERDE = new Color(40, 167, 69); // Color estándar para valores monetarios
    private final Color COLOR_BORDE_GRIS = new Color(225, 230, 235); // Color sutil para marcos y divisiones
    private final Color COLOR_TEXTO_SEC = new Color(110, 115, 130); // Gris para etiquetas menos importantes

    public entradas(executable frame) {
        this.executable = frame;
        this.setLayout(new BorderLayout()); // Divide el panel en secciones (Norte, Sur, Centro, etc.)
        this.setBackground(COLOR_FONDO);

        Menu_lateral();
        crearPanelCentral();
    }

    private void Menu_lateral() {
        JPanel p = new JPanel();
        p.setPreferredSize(new Dimension(200, 0)); // Define el ancho fijo del menú izquierdo
        p.setBackground(COLOR_AZUL_LATERAL);
        p.setLayout(new GridLayout(10, 1, 0, 1)); // Crea filas iguales para los botones
        add(p, BorderLayout.WEST);

        // LOGO Y TÍTULO
        ImageIcon lg = new ImageIcon("img/logo.png");
        Icon in = new ImageIcon(lg.getImage().getScaledInstance(70, 60, Image.SCALE_SMOOTH)); // Suaviza la imagen al
                                                                                              // reducirla
        JButton titulo = new JButton("Stock System", in);
        titulo.setHorizontalTextPosition(SwingConstants.RIGHT); // Pone el texto a la derecha del icono
        titulo.setBackground(new Color(0, 0, 0, 0)); // Hace el fondo del botón totalmente transparente
        titulo.setFocusPainted(false);
        titulo.setBorder(null);
        titulo.setForeground(Color.white);
        titulo.setFont(new Font("Arial", Font.ITALIC, 15));
        p.add(titulo);

        // ICONO DE NAVEGACIÓN
        ImageIcon dashi = new ImageIcon("img/casa_icono.jpg");
        Icon dsh = new ImageIcon(dashi.getImage().getScaledInstance(30, 25, Image.SCALE_SMOOTH));

        // BOTONES DEL MENÚ
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
        if (texto.equals("Entradas")) {
            boton.setBackground(COLOR_AZUL_ACTIVO); // Marca visual de "estás aquí"
        } else {
            boton.setBackground(COLOR_AZUL_LATERAL);
        }
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false); // Elimina el recuadro de puntos al hacer click
        boton.setHorizontalAlignment(SwingConstants.LEFT); // Alinea contenido a la izquierda
        boton.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16)); // Margen interno del botón
        boton.addActionListener(e -> executable.mostrarVista(vista)); // Cambia la pantalla mediante la interfaz
        return boton;
    }

    private void crearPanelCentral() {
        JPanel main = new JPanel(new BorderLayout(0, 25)); // Espacio vertical de 25px entre componentes
        main.setBackground(COLOR_FONDO);
        main.setBorder(new EmptyBorder(30, 40, 30, 40)); // Margen de respiro para todo el contenido central

        // CABECERA
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false); // Permite que se vea el fondo gris del panel padre
        JLabel lblTitle = new JLabel("Historial de Entradas");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));

        JPanel pBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pBotones.setOpaque(false);

        pBotones.add(crearBotonBlanco("Editar"));
        pBotones.add(crearBotonBlanco("Eliminar"));

        JButton btnNu = new JButton("Nueva Entrada");
        btnNu.setBackground(COLOR_NARANJA);
        btnNu.setForeground(Color.WHITE);
        btnNu.setFocusPainted(false);
        btnNu.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE_GRIS), // Borde exterior
                BorderFactory.createEmptyBorder(8, 15, 8, 15) // Margen interno (padding)
        ));
        pBotones.add(btnNu);

        header.add(lblTitle, BorderLayout.WEST);
        header.add(pBotones, BorderLayout.EAST);

        // TARJETAS INFORMATIVAS
        JPanel tarjetas = new JPanel(new GridLayout(1, 3, 20, 0)); // Rejilla de 1 fila y 3 columnas iguales
        tarjetas.setOpaque(false);
        tarjetas.add(crearTarjeta("Entradas Hoy", "12", Color.BLACK));
        tarjetas.add(crearTarjeta("Productos", "145", Color.BLACK));
        tarjetas.add(crearTarjeta("Valor Ingreso", "$4,520", COLOR_DINERO_VERDE));

        // CUADRO DE LA TABLA (EL "BOX")
        JPanel contenedorTabla = new JPanel(new BorderLayout());
        contenedorTabla.setBackground(Color.WHITE);
        contenedorTabla.setBorder(BorderFactory.createLineBorder(COLOR_BORDE_GRIS)); // Dibuja el contorno del cuadro

        String[] columnas = { "ID", "FECHA", "PRODUCTO", "PROVEEDOR", "CANTIDAD", "ESTADO" };
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0); // Estructura de datos de la tabla
        for (int i = 1; i <= 10; i++)
            modelo.addRow(new Object[] { "#00" + i, "09/03/26", "Producto " + i, "Proveedor", "50", "OK" });

        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(40); // Espaciado cómodo para las filas

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createEmptyBorder()); // Quita el borde por defecto del scroll para no duplicar
                                                             // marcos
        scroll.getViewport().setBackground(Color.WHITE); // Fondo blanco para el área donde no hay filas

        // PAGINACIÓN
        JPanel paginacion = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 10)); // Botones alineados a la derecha
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
        contenedorTabla.add(paginacion, BorderLayout.SOUTH); // Pone los botones debajo de la tabla dentro del cuadro

        // ENSAMBLADO
        JPanel areaSuperior = new JPanel(new BorderLayout(0, 20));
        areaSuperior.setOpaque(false);
        areaSuperior.add(header, BorderLayout.NORTH);
        areaSuperior.add(tarjetas, BorderLayout.CENTER);

        main.add(areaSuperior, BorderLayout.NORTH);
        main.add(contenedorTabla, BorderLayout.CENTER); // La tabla ocupa el espacio restante
        add(main, BorderLayout.CENTER);
    }

    private JButton crearBotonBlanco(String t) {
        JButton b = new JButton(t);
        b.setBackground(Color.WHITE);
        b.setForeground(Color.BLACK);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE_GRIS), // Borde exterior
                BorderFactory.createEmptyBorder(8, 15, 8, 15) // Margen interno (padding)
        ));
        return b;
    }

    private JPanel crearTarjeta(String titulo, String valor, Color colorValor) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE_GRIS), // Marco de la tarjeta
                new EmptyBorder(15, 15, 15, 15) // Espacio para que el texto no toque el marco
        ));

        JLabel t = new JLabel(titulo);
        t.setForeground(COLOR_TEXTO_SEC); // Título en gris
        JLabel v = new JLabel(valor);
        v.setFont(new Font("Segoe UI", Font.BOLD, 24));
        v.setForeground(colorValor);

        p.add(t, BorderLayout.NORTH);
        p.add(v, BorderLayout.CENTER);
        return p;
    }
}