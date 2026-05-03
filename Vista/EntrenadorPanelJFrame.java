package vista;

import modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class EntrenadorPanelJFrame extends JFrame {

    private Sistema sistema;
    private JTable tabla;
    private DefaultTableModel modelo;

    public EntrenadorPanelJFrame(Sistema sistema) {
        this.sistema = sistema;
        initUI();
        cargarEntrenadores();
    }

    private void initUI() {
        setTitle("Gestión de Entrenadores");
        setSize(600, 350);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Entrenadores", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        modelo = new DefaultTableModel(
                new String[]{"DNI", "Nombre", "Apellido"}, 0
        ) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JButton btnEliminar = new JButton("Eliminar entrenador");
        btnEliminar.setBackground(Color.RED);
        btnEliminar.setForeground(Color.WHITE);

        JButton btnCerrar = new JButton("Cerrar");

        JPanel panel = new JPanel();
        panel.add(btnEliminar);
        panel.add(btnCerrar);

        add(panel, BorderLayout.SOUTH);

        btnEliminar.addActionListener(e -> eliminar());
        btnCerrar.addActionListener(e -> dispose());
    }

    private void cargarEntrenadores() {
        modelo.setRowCount(0);
        for (Entrenador e : sistema.getEntrenadores()) {
            modelo.addRow(new Object[]{
                    e.getDNI(),
                    e.getNombre(),
                    e.getApellido()
            });
        }
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) return;

        int dni = (int) modelo.getValueAt(fila, 0);
        Entrenador e = sistema.buscarEntrenadorPorDNI(dni);

        int ok = JOptionPane.showConfirmDialog(
                this,
                "⚠ Eliminar entrenador " + e.getNombre() + "?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        );

        if (ok == JOptionPane.YES_OPTION) {
            sistema.getEntrenadorBD().eliminarEntrenador(dni);
            sistema.getEntrenadores().remove(e);
            modelo.removeRow(fila);
        }
    }
}

