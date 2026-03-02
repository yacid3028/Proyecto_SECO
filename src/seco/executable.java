package seco;



import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.formdev.flatlaf.FlatLightLaf;

public class executable extends JFrame {
	 	private CardLayout cardlayout;
	    private JPanel container;

	    public executable() {

	        setTitle("SECO Stock Control");
	        setSize(1000, 700);
	        setLocationRelativeTo(null);
	        setDefaultCloseOperation(EXIT_ON_CLOSE);

	        ImageIcon ico = new ImageIcon("img/logo_app.png");
	        setIconImage(ico.getImage());

	        
	        //CREA EL CARDLAYOUT 
	        cardlayout= new CardLayout();
	        container = new JPanel(cardlayout);

	        //AGREGA LAS CLASES PANELES 
	        container.add(new dashboard(this), "dashboard");
	        container.add(new productos(this), "productos");
	        container.add(new entradas(this), "entradas");
	        container.add(new salidas(this), "salidas");
	        container.add(new provedores(this), "provedores");
	        container.add(new ordenes(this), "ordenes");
	        container.add(new reportes(this), "reportes");
	        container.add(new ventas(this), "ventas");

	        add(container);
	        
	    }

	   
	    public void mostrarVista(String nombre) {
	        cardlayout.show(container, nombre);
	    }
	    
	    

	    public static void main(String[] args) {

	        try {
	            FlatLightLaf.setup();
	        } catch (Exception ex) {
	            System.err.println("Error cargando FlatLaf");
	        }

	        SwingUtilities.invokeLater(() -> {
	            new executable().setVisible(true);
	            
	        });
	    }

}
