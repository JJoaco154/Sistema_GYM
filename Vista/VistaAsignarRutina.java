package vista;

import modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VistaAsignarRutina extends JFrame {

    private Sistema sistema;
    private Entrenador entrenador;
    private Cliente cliente;
    private EntrenadorService service;

    private JTable tablaRutinas;
    private DefaultTableModel modeloRutinas;

    public VistaAsignarRutina(Sistema sistema, Entrenador entrenador, Cliente cliente) {
        this.sistema = sistema;
        this.entrenador = entrenador;
        this.cliente = cliente;
        this.service = new EntrenadorService(sistema);

        setTitle("Asignar Rutina a " + cliente.getNombre());
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        initUI();
        cargarRutinas();
    }

    private void initUI() {

        modeloRutinas = new DefaultTableModel(new String[]{"ID", "Nombre"}, 0);
        tablaRutinas = new JTable(modeloRutinas);

        JButton btnAsignar = new JButton("Asignar");
        btnAsignar.addActionListener(e -> asignar());

        add(new JScrollPane(tablaRutinas), BorderLayout.CENTER);
        add(btnAsignar, BorderLayout.SOUTH);
    }

    private void cargarRutinas() {
        modeloRutinas.setRowCount(0);

        for (Rutina r : entrenador.getHistorialR()) {
            modeloRutinas.addRow(new Object[]{
                    r.getId(), r.getNombre()
            });
        }
    }

    private void asignar() {
        int fila = tablaRutinas.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una rutina.");
            return;
        }

        Rutina r = entrenador.getHistorialR().get(fila);

        boolean ok = service.asignarRutina(cliente, r);

        if (ok) {
            JOptionPane.showMessageDialog(this, "Rutina asignada correctamente.");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al asignar rutina.");
        }
    }
}

