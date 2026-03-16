package seco;

import java.awt.*;
import java.awt.event.*;
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

    // Componentes de la vista (DECLARACIÓN)
    // - "modelo" y "tabla" se usan en cargarEntradas()/mostrarDialogoEntrada()/eliminarEntradaSeleccionada().
    // - "db" es el objeto que llama a la base de datos (métodos CRUD en src/seco/fcdb/entradasDB.java).
    private DefaultTableModel modelo;
    private JTable tabla;
    private final seco.fcdb.entradasDB db = new seco.fcdb.entradasDB();

    // Tarjetas informativas (para refrescar con datos reales)
    private JPanel tarjetaEntradasHoy;
    private JPanel tarjetaProductos;
    private JPanel tarjetaValor;

    public entradas(executable frame) {
        this.executable = frame;
        this.setLayout(new BorderLayout()); // Divide el panel en secciones (Norte, Sur, Centro, etc.)
        this.setBackground(COLOR_FONDO);

        Menu_lateral();
        crearPanelCentral();
        // Carga los datos de la BD en la tabla al abrir esta pantalla.
        // (Llama a consultarEntradas() en src/seco/fcdb/entradasDB.java)
        cargarEntradas();
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

        // BOTONES DE ACCIÓN (Editar / Eliminar / Nueva Entrada)
        // - Editar: abre el diálogo con los datos de la fila seleccionada (carga desde tabla)
        // - Eliminar: borra la fila seleccionada (usa el ID de la tabla)
        // - Nueva Entrada: abre el diálogo vacío para crear un registro nuevo
        JButton btnEditar = crearBotonBlanco("Editar");
        JButton btnEliminar = crearBotonBlanco("Eliminar");
        JButton btnNueva = new JButton("Nueva Entrada");

        btnEditar.addActionListener(e -> mostrarDialogoEntrada(true));
        btnEliminar.addActionListener(e -> eliminarEntradaSeleccionada());

        btnNueva.setBackground(COLOR_NARANJA);
        btnNueva.setForeground(Color.WHITE);
        btnNueva.setFocusPainted(false);
        btnNueva.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE_GRIS), // Borde exterior
                BorderFactory.createEmptyBorder(8, 15, 8, 15) // Margen interno (padding)
        ));
        btnNueva.addActionListener(e -> mostrarDialogoEntrada(false));

        pBotones.add(btnEditar);
        pBotones.add(btnEliminar);
        pBotones.add(btnNueva);

        header.add(lblTitle, BorderLayout.WEST);
        header.add(pBotones, BorderLayout.EAST);

        // TARJETAS INFORMATIVAS (conectadas a BD)
        JPanel tarjetas = new JPanel(new GridLayout(1, 3, 20, 0)); // Rejilla de 1 fila y 3 columnas iguales
        tarjetas.setOpaque(false);
        tarjetaEntradasHoy = crearTarjeta("Entradas Hoy", "0", Color.BLACK);
        tarjetaProductos = crearTarjeta("Productos", "0", Color.BLACK);
        tarjetaValor = crearTarjeta("Valor Ingreso", "$0", COLOR_DINERO_VERDE);
        tarjetas.add(tarjetaEntradasHoy);
        tarjetas.add(tarjetaProductos);
        tarjetas.add(tarjetaValor);
        // posteriormente, tendrás que refrescar estos números (por ejemplo, ejecutar
        // de nuevo estos métodos y llamar a tarjetas.removeAll() + volver a agregar)


        // CUADRO DE LA TABLA (EL "BOX")
        JPanel contenedorTabla = new JPanel(new BorderLayout());
        contenedorTabla.setBackground(Color.WHITE);
        contenedorTabla.setBorder(BorderFactory.createLineBorder(COLOR_BORDE_GRIS)); // Dibuja el contorno del cuadro

        // definimos las columnas que tendrá la tabla; el orden debe coincidir con el que
        // usa el código de ejemplo original.
        String[] columnas = { "ID", "FECHA", "PRODUCTO", "PROVEDOR", "CANTIDAD", "ESTADO" };
        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Evita edición directa en la tabla
            }
        }; // Estructura de datos de la tabla

        tabla = new JTable(modelo);
        tabla.setRowHeight(40); // Espaciado cómodo para las filas
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // si la consulta tarda, podría ejecutarse en un hilo separado para no bloquear la UI

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

    private void cargarEntradas() {
        if (modelo == null) {
            return;
        }
        // Limpia los datos actuales y vuelve a cargar desde la base de datos.
        modelo.setRowCount(0);
        // Llama a consultarEntradas() en src/seco/fcdb/entradasDB.java
        db.consultarEntradas(modelo);
        // Refresca las tarjetas con datos calculados de la BD
        refrescarTarjetas();
    }

    // Método para actualizar las tarjetas con datos reales de la BD
    private void refrescarTarjetas() {
        int entradasHoy = db.contarEntradasHoy();
        int totalProductos = db.sumarCantidadesTotales();
        // Para Valor Ingreso, por ahora fijo (no hay precios en entradas), pero se puede calcular si se agrega precio
        String valorIngreso = "$0"; // TODO: calcular basado en precio * cantidad si se agrega columna precio

        actualizarTarjeta(tarjetaEntradasHoy, "Entradas Hoy", String.valueOf(entradasHoy), Color.BLACK);
        actualizarTarjeta(tarjetaProductos, "Productos", String.valueOf(totalProductos), Color.BLACK);
        actualizarTarjeta(tarjetaValor, "Valor Ingreso", valorIngreso, COLOR_DINERO_VERDE);
    }

    // Método auxiliar para actualizar el contenido de una tarjeta
    private void actualizarTarjeta(JPanel tarjeta, String titulo, String valor, Color colorValor) {
        tarjeta.removeAll(); // Quita los componentes anteriores
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE_GRIS), // Marco de la tarjeta
                new EmptyBorder(15, 15, 15, 15) // Espacio para que el texto no toque el marco
        ));

        JLabel t = new JLabel(titulo);
        t.setForeground(COLOR_TEXTO_SEC); // Título en gris
        JLabel v = new JLabel(valor);
        v.setFont(new Font("Segoe UI", Font.BOLD, 24));
        v.setForeground(colorValor);

        tarjeta.add(t, BorderLayout.NORTH);
        tarjeta.add(v, BorderLayout.CENTER);
        tarjeta.revalidate(); // Refresca la UI
        tarjeta.repaint();
    }

    private void mostrarDialogoEntrada(boolean esEdicion) {
        String titulo = esEdicion ? "Editar Entrada" : "Nueva Entrada";
        String botonGuardar = esEdicion ? "Guardar" : "Agregar";

        // Se usa array para que pueda ser modificado dentro del ActionListener (lambda)
        // (Java exige que las variables capturadas sean efectivamente finales, por eso arreglo)
        final int[] idSeleccionado = new int[] { -1 };
        String producto = "";
        String proveedor = "";
        int cantidad = 1;

        if (esEdicion) {
            int fila = tabla.getSelectedRow();
            if (fila < 0) {
                JOptionPane.showMessageDialog(this, "Seleccione una fila para editar.", "Atención",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                idSeleccionado[0] = Integer.parseInt(String.valueOf(tabla.getValueAt(fila, 0)));
            } catch (NumberFormatException ignored) {
                idSeleccionado[0] = -1;
            }
            producto = String.valueOf(tabla.getValueAt(fila, 2));
            proveedor = String.valueOf(tabla.getValueAt(fila, 3));
            try {
                cantidad = Integer.parseInt(String.valueOf(tabla.getValueAt(fila, 4)));
            } catch (NumberFormatException ignored) {
                cantidad = 1;
            }
        }

        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(this), titulo, Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setSize(400, 260);
        dialog.setLocationRelativeTo(this);

        JPanel form = new JPanel(new GridLayout(0, 2, 10, 10));
        form.setBorder(new EmptyBorder(15, 15, 15, 15));

        JTextField tfProducto = new JTextField(producto);
        JTextField tfProveedor = new JTextField(proveedor);
        JSpinner spCantidad = new JSpinner(new SpinnerNumberModel(cantidad, 1, Integer.MAX_VALUE, 1));

        form.add(new JLabel("Producto:"));
        form.add(tfProducto);
        form.add(new JLabel("Proveedor:"));
        form.add(tfProveedor);
        form.add(new JLabel("Cantidad (Lote):"));
        form.add(spCantidad);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        JButton btnCancelar = new JButton("Cancelar");
        JButton btnGuardar = new JButton(botonGuardar);
        botones.add(btnCancelar);
        botones.add(btnGuardar);

        btnCancelar.addActionListener(e -> dialog.dispose());
        btnGuardar.addActionListener(e -> {
            String prod = tfProducto.getText().trim();
            String prov = tfProveedor.getText().trim();
            int cant = (int) spCantidad.getValue();

            if (prod.isEmpty() || prov.isEmpty() || cant < 1) {
                JOptionPane.showMessageDialog(dialog, "Complete todos los campos correctamente.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean ok;
            if (esEdicion) {
                // Guardar cambios existentes: actualiza registro usando el ID cargado desde la tabla
                int id = idSeleccionado[0];
                if (id > 0) {
                    ok = db.actualizarEntrada(id, prod, prov, cant);
                } else {
                    ok = false;
                }
            } else {
                // Insertar nuevo registro en la base de datos
                ok = db.agregarEntrada(prod, prov, cant);
            }

            if (ok) {
                cargarEntradas();
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "No se pudo guardar la entrada. Revise la conexión.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.setLayout(new BorderLayout());
        dialog.add(form, BorderLayout.CENTER);
        dialog.add(botones, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void eliminarEntradaSeleccionada() {
        // Se asegura que el usuario tenga una fila seleccionada.
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una fila para eliminar.", "Atención",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // El ID viene del primer campo de la fila (columna 0).
        String idSeleccionado = String.valueOf(tabla.getValueAt(fila, 0));
        int opcion = JOptionPane.showConfirmDialog(this, "¿Desea eliminar la entrada seleccionada?", "Confirmar",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            int id = Integer.parseInt(idSeleccionado);
            // Llama al método en src/seco/fcdb/entradasDB.java que ejecuta el DELETE
            boolean ok = db.eliminarEntrada(id);
            if (ok) {
                // Refresca la tabla para que ya no aparezca la fila eliminada
                cargarEntradas();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar la entrada.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID seleccionado no es válido.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
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