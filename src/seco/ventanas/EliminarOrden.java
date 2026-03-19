package seco.ventanas;

import javax.swing.*;
import java.awt.*;
import seco.fcdb.ordenesDB;
import seco.ordenes; // para refrescar tabla

public class EliminarOrden extends JFrame {

    private JTextField campoID;
    private ordenesDB db;
    private ordenes panelPrincipal; // referencia al panel principal

    public EliminarOrden(ordenes panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
        db = new ordenesDB();

        setTitle("Eliminar Orden");
        setSize(350,200);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel titulo = new JLabel("Eliminar Orden");
        titulo.setFont(new Font("Arial", Font.BOLD,18));
        titulo.setBounds(90,20,200,30);
        add(titulo);

        JLabel idLabel = new JLabel("ID de Orden:");
        idLabel.setBounds(40,80,100,25);
        add(idLabel);

        campoID = new JTextField();
        campoID.setBounds(150,80,150,25);
        add(campoID);

        JButton eliminar = new JButton("Eliminar");
        eliminar.setBounds(180,120,100,30);
        add(eliminar);

        eliminar.addActionListener(e -> {
            String idOrden = campoID.getText().trim();
            if (idOrden.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingresa un ID válido.");
                return;
            }

            db.eliminarOrden(idOrden); // eliminar de la base de datos
            JOptionPane.showMessageDialog(this, "Orden con ID " + idOrden + " eliminada.");

            if (panelPrincipal != null) {
                panelPrincipal.actualizarTabla(); // refrescar tabla
            }

            dispose(); // cerrar ventana
        });

        JButton cancelar = new JButton("Cancelar");
        cancelar.setBounds(60,120,100,30);
        add(cancelar);
        cancelar.addActionListener(e -> dispose());
    }
}