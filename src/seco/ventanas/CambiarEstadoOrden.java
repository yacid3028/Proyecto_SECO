package seco.ventanas;

import javax.swing.*;

import seco.ordenes;
import seco.fcdb.ordenesDB;

import java.awt.*;

public class CambiarEstadoOrden extends JFrame {

    private JTextField campoID;
    private JComboBox<String> estado;
    private JButton guardar;
    private JButton cancelar;

    private ordenes panelPrincipal;
    private ordenesDB db;

    public CambiarEstadoOrden(ordenes panelPrincipal, String idOrden) {

        this.panelPrincipal = panelPrincipal;
        this.db = new ordenesDB();

        setTitle("Cambiar Estado de Orden");
        setSize(380, 230);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        iniciarComponentes(idOrden);
        accionesBotones();
    }

    private void iniciarComponentes(String idOrden) {

        JLabel titulo = new JLabel("Cambiar Estado de Orden");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setBounds(70, 20, 250, 30);
        add(titulo);

        JLabel id = new JLabel("ID Orden:");
        id.setBounds(40, 80, 100, 25);
        add(id);

        campoID = new JTextField(idOrden);
        campoID.setBounds(140, 80, 170, 25);
        add(campoID);

        JLabel estadoLabel = new JLabel("Nuevo Estado:");
        estadoLabel.setBounds(40, 120, 100, 25);
        add(estadoLabel);

        estado = new JComboBox<>();
        estado.addItem("Pendiente");
        estado.addItem("En camino");
        estado.addItem("Recibida");
        estado.setBounds(140, 120, 170, 25);
        add(estado);

        guardar = new JButton("Actualizar");
        guardar.setBounds(200, 160, 110, 30);
        add(guardar);

        cancelar = new JButton("Cancelar");
        cancelar.setBounds(70, 160, 110, 30);
        add(cancelar);
    }

    private void accionesBotones() {

        guardar.addActionListener(e -> actualizarEstado());

        cancelar.addActionListener(e -> dispose());
    }

    private void actualizarEstado() {

        String idOrden = campoID.getText().trim();
        String estadoSeleccionado = (String) estado.getSelectedItem();

        if (idOrden.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa un ID de orden");
            return;
        }

        boolean actualizado = db.actualizarEstado(idOrden, estadoSeleccionado);

        if (actualizado) {

            JOptionPane.showMessageDialog(this, "Estado actualizado correctamente");

            if (panelPrincipal != null) {
                panelPrincipal.actualizarTabla();
            }

            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No se encontró la orden",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}