package vista;

import modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RutinaPanelJFrame extends JFrame {

    private Sistema sistema;
    private JTable tabla;
    private DefaultTableModel modelo;

    public RutinaPanelJFrame(JFrame parent, Sistema sistema) {
        this.sistema = sistema;
        initUI();
        cargarRutinas();
    }

    private void initUI() {
        setTitle("Gestión de Rutinas");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("Rutinas registradas", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        modelo = new DefaultTableModel(
                new String[]{"ID", "Nombre"}, 0
        ) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JButton btnEliminar = new JButton("Eliminar rutina");
        btnEliminar.setBackground(Color.RED);
        btnEliminar.setForeground(Color.WHITE);

        JButton btnCerrar = new JButton("Cerrar");

        JPanel panel = new JPanel();
        panel.add(btnEliminar);
        panel.add(btnCerrar);
        add(panel, BorderLayout.SOUTH);

        btnEliminar.addActionListener(e -> eliminarRutina());
        btnCerrar.addActionListener(e -> dispose());

        // Doble click → ejercicios
        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    mostrarEjercicios();
                }
            }
        });
    }

    private void cargarRutinas() {
        modelo.setRowCount(0);
        for (Rutina r : sistema.getRutinas()) {
            modelo.addRow(new Object[]{
                    r.getId(),
                    r.getNombre()
            });
        }
    }

    private void eliminarRutina() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) return;

        int id = (int) modelo.getValueAt(fila, 0);
        Rutina r = sistema.buscarRutinaPorId(id);

        int ok = JOptionPane.showConfirmDialog(
                this,
                "⚠ ¿Eliminar la rutina \"" + r.getNombre() + "\"?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        );

        if (ok == JOptionPane.YES_OPTION) {
            sistema.getRutinaBD().eliminarRutina(id);
            sistema.getRutinas().remove(r);
            modelo.removeRow(fila);
        }
    }

    private void mostrarEjercicios() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) return;

        int id = (int) modelo.getValueAt(fila, 0);
        Rutina r = sistema.buscarRutinaPorId(id);

        java.util.List<Ejercicio> ejercicios =
                sistema.getRutinaEjercicioBD().obtenerEjerciciosDeRutina(id);

        DefaultListModel<String> modeloEj = new DefaultListModel<>();

        if (ejercicios.isEmpty()) {
            modeloEj.addElement("La rutina no tiene ejercicios.");
        } else {
            for (Ejercicio e : ejercicios) {
                modeloEj.addElement(
                        e.getNombre()
                        + " | Reps: " + e.getRepeticiones()
                        + " | Peso: " + e.getPeso()
                );
            }
        }

        JList<String> lista = new JList<>(modeloEj);
        JOptionPane.showMessageDialog(
                this,
                new JScrollPane(lista),
                "Ejercicios de " + r.getNombre(),
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}

