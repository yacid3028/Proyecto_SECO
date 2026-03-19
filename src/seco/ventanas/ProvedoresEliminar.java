package seco.ventanas;

import javax.swing.*;
import java.awt.*;
import seco.fcdb.provedoresDB;
import seco.provedores;

public class ProvedoresEliminar extends JFrame {

    JTextField campoID;
    private provedoresDB db;
    private provedores panel; // Referencia al panel principal para refrescar tabla

    public ProvedoresEliminar(provedores panel) {

        this.panel = panel; // Guardamos referencia para actualizar la tabla
        db = new provedoresDB();

        setTitle("Eliminar Proveedor");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel titulo = new JLabel("Eliminar Proveedor");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setBounds(90, 20, 200, 30);
        add(titulo);

        JLabel id = new JLabel("ID de Proveedor:");
        id.setBounds(40, 80, 120, 25);
        add(id);

        campoID = new JTextField();
        campoID.setBounds(160, 80, 150, 25);
        add(campoID);

        // BOTÓN ELIMINAR
        JButton eliminar = new JButton("Eliminar");
        eliminar.setBounds(180, 120, 100, 30);
        add(eliminar);

        eliminar.addActionListener(e -> {
            String idProvedor = campoID.getText().trim();

            if (idProvedor.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese un ID válido.");
                return;
            }

            // Confirmar antes de eliminar
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "¿Seguro que deseas eliminar el proveedor con ID " + idProvedor + "?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    if (db.existeID(idProvedor)) {
                        db.eliminarProveedor(idProvedor);
                        JOptionPane.showMessageDialog(this, "Proveedor con ID " + idProvedor + " eliminado.");
                        campoID.setText(""); // Limpiar campo

                        // 🔹 REFRESCAR TABLA en el panel principal
                        if (panel != null) {
                            panel.actualizarTabla();
                        }

                    } else {
                        JOptionPane.showMessageDialog(this, "No existe un proveedor con ese ID.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error al eliminar el proveedor.");
                }
            }
        });

        // BOTÓN CANCELAR
        JButton cancelar = new JButton("Cancelar");
        cancelar.setBounds(60, 120, 100, 30);
        add(cancelar);
        cancelar.addActionListener(e -> dispose());
    }
}