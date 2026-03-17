package seco;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import seco.fcdb.productosDB;
import seco.fcdb.salidasDB;
import seco.ventanas.agregarProducto;
import seco.ventanas.prodcutodeditar;

public class productos extends JPanel {

    private executable executable;
    private final Color COLOR_BORDE_GRIS = new Color(225, 230, 235);
     private JTable tabla;
    private DefaultTableModel modelo;

    public productos(executable frame) {
        this.executable = frame;
        setLayout(new BorderLayout());
        Menu_lateral();
        C_central();
    }

    // MENU LATERAL
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
        titulo.setBackground(new Color(0,0,0,0));
        titulo.setFocusPainted(false);
        titulo.setBorder(null);
        titulo.setForeground(Color.white);
        titulo.setFont(new Font("Arial", Font.ITALIC, 15));

        p.add(titulo);

        ImageIcon dashi = new ImageIcon("img/casa_icono.jpg");
        Icon dsh = new ImageIcon(dashi.getImage().getScaledInstance(30,25,Image.SCALE_SMOOTH));

        p.add(crearBoton("Dashboard","dashboard",dsh));
        p.add(crearBoton("Productos","productos",dsh));
        p.add(crearBoton("Entradas","entradas",dsh));
        p.add(crearBoton("Salidas","salidas",dsh));
        p.add(crearBoton("Provedores","provedores",dsh));
        p.add(crearBoton("Ordenes","ordenes",dsh));
        p.add(crearBoton("Reportes","reportes",dsh));
        p.add(crearBoton("Servicio","ventas",dsh));
    }

    private JButton crearBoton(String texto, String vista, Icon icono) {

        JButton boton = new JButton(texto, icono);

        if(texto.equals("Productos")){
            boton.setBackground(new Color(33,150,243));
        }else{
            boton.setBackground(new Color(10,20,100));
        }

        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setHorizontalAlignment(SwingConstants.LEFT);
        boton.setMaximumSize(new Dimension(Integer.MAX_VALUE,40));
        boton.setBorder(BorderFactory.createEmptyBorder(8,16,8,16));

        boton.addActionListener(e -> executable.mostrarVista(vista));

        return boton;
    }

    private void C_central(){

        JPanel pam = new JPanel(new BorderLayout());
        add(pam, BorderLayout.CENTER);

        JPanel superior = new JPanel(new BorderLayout());
        superior.setPreferredSize(new Dimension(Integer.MAX_VALUE,80));

        JLabel titulo = new JLabel("Inventario de Productos");
        titulo.setFont(new Font("Arial",Font.BOLD,24));
        titulo.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));

        superior.add(titulo,BorderLayout.WEST);

        JPanel botonesPanel = new JPanel();

        JButton agregar = new JButton("Agregar Producto");
		agregar.addActionListener(e -> {

    	agregarProducto ventana = new agregarProducto();
    	ventana.setVisible(true);

});
        agregar.setBackground(new Color(255,140,0));
        agregar.setForeground(Color.WHITE);
        agregar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE_GRIS),
                BorderFactory.createEmptyBorder(8,15,8,15)
        ));

        JButton editar = new JButton("Editar");
        editar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE_GRIS),
                BorderFactory.createEmptyBorder(8,15,8,15)
				
        ));
		editar.addActionListener(e -> {

    int fila = tabla.getSelectedRow();

    if(fila == -1){

        JOptionPane.showMessageDialog(null,"Seleccione un producto de la tabla");

        return;
    }else{

    String idProducto = (String) tabla.getValueAt(fila,0);
    
    prodcutodeditar ventana = new prodcutodeditar(idProducto);
    ventana.setVisible(true);
    }

});

        JButton eliminar = new JButton("Eliminar");
eliminar.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(COLOR_BORDE_GRIS),
        BorderFactory.createEmptyBorder(8,15,8,15)
));

eliminar.addActionListener(e -> {

    String id = JOptionPane.showInputDialog(
            null,
            "Coloque el ID del producto que desea eliminar",
            "Eliminar Producto",
            JOptionPane.QUESTION_MESSAGE
    );

    if(id != null){

        int idProducto = Integer.parseInt(id);

        productosDB.eliminarProducto(idProducto);

        actualizarTabla();

        JOptionPane.showMessageDialog(null,"Producto eliminado");

    }

});

        botonesPanel.add(editar);
        botonesPanel.add(eliminar);
        botonesPanel.add(agregar);

        superior.add(botonesPanel,BorderLayout.EAST);

        JPanel central = new JPanel();
        central.setLayout(new BoxLayout(central,BoxLayout.Y_AXIS));

        central.add(superior);

        // BUSCADOR
        JPanel busquedaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        busquedaPanel.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));

        JPanel buscador = new JPanel(new BorderLayout(5,5));

        JTextField buscar_producto = new JTextField();
        buscar_producto.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
               String texto = buscar_producto.getText().toUpperCase();
				if (!texto.isEmpty()) {
                    productosDB salidasDB = new productosDB();
                    salidasDB.buscarProducto(texto, modelo);
					
				} 
            }
        });

        JButton filtro = new JButton("Filtrar");


        buscador.add(buscar_producto,BorderLayout.CENTER);
        buscador.add(filtro,BorderLayout.EAST);

        busquedaPanel.add(buscador);

        central.add(busquedaPanel);

        central.add(panelTabla());

        pam.add(central,BorderLayout.CENTER);

        // PAGINACION
        JPanel inferior = new JPanel(new BorderLayout());
        inferior.setPreferredSize(new Dimension(Integer.MAX_VALUE,50));
        inferior.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel info = new JLabel("Mostrando productos");

        inferior.add(info,BorderLayout.WEST);

        JPanel paginas = new JPanel(new FlowLayout(FlowLayout.CENTER,5,0));

        paginas.add(new JButton("<"));
        paginas.add(new JButton("1"));
        paginas.add(new JButton("2"));
        paginas.add(new JButton("3"));
        paginas.add(new JButton(">"));

        inferior.add(paginas,BorderLayout.CENTER);

        JButton exportar = new JButton("Exportar");

        inferior.add(exportar,BorderLayout.EAST);

        pam.add(inferior,BorderLayout.SOUTH);
    }

    private JPanel panelTabla(){

        JPanel panel = new JPanel(new BorderLayout());

        String[] cols = {"id_Producto" ,"Nombre",  "Categoria", "Precio de Venta","Precio de Compra", "Stock"};
        modelo = new DefaultTableModel(cols,0){
            @Override
            public boolean isCellEditable(int row,int column){
                return false;
            }
        };

        
        productosDB.productos(modelo);

        tabla = new JTable(modelo);

        tabla.setRowHeight(30);
        tabla.setShowGrid(false);
        tabla.setSelectionBackground(new Color(184,207,229));
        tabla.setFillsViewportHeight(true);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));

        if(tabla.getColumnModel().getColumnCount()>0){

            tabla.getColumnModel().getColumn(0).setPreferredWidth(60);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(150);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(50);
            tabla.getColumnModel().getColumn(4).setPreferredWidth(70);

        }

       panel.add(scroll,BorderLayout.CENTER);

return panel;
}

private void actualizarTabla(){

    modelo.setRowCount(0);

    productosDB.productos(modelo);

}

}