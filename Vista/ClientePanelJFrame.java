package vista;

import modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ClientePanelJFrame extends JFrame {

    private Sistema sistema;
    private JTable tabla;
    private DefaultTableModel modelo;

    public ClientePanelJFrame(Sistema sistema) {
        this.sistema = sistema;
        initUI();
        cargarClientes();
    }

    private void initUI() {
        setTitle("Gestión de Clientes");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Clientes registrados", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        modelo = new DefaultTableModel(
                new String[]{"DNI", "Nombre", "Apellido", "Clases", "Entrenador"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false; // UX: tabla solo lectura
            }
        };

        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Panel inferior con acciones
        JPanel acciones = new JPanel();

        JButton btnEliminar = new JButton("Eliminar cliente");
        btnEliminar.setBackground(Color.RED);
        btnEliminar.setForeground(Color.WHITE);

        JButton btnCerrar = new JButton("Cerrar");

        acciones.add(btnEliminar);
        acciones.add(btnCerrar);

        add(acciones, BorderLayout.SOUTH);

        btnEliminar.addActionListener(e -> eliminarClienteSeleccionado());
        btnCerrar.addActionListener(e -> dispose());
    }

    private void cargarClientes() {
        modelo.setRowCount(0);

        for (Cliente c : sistema.getClientes()) {
            Entrenador ent = sistema.buscarEntrenadorPorDNI(c.getIdEntrenador());
            String entrenador = (ent != null) ? ent.getNombre() : "Sin asignar";

            modelo.addRow(new Object[]{
                    c.getDNI(),
                    c.getNombre(),
                    c.getApellido(),
                    c.getClases(),
                    entrenador
            });
        }
    }

    private void eliminarClienteSeleccionado() {
        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente.");
            return;
        }

        int dni = (int) modelo.getValueAt(fila, 0);
        Cliente c = sistema.buscarClientePorDNI(dni);

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "⚠ ¿Eliminar al cliente " + c.getNombre() + " " + c.getApellido() + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            if (sistema.getClienteBD().eliminarCliente(dni)) {
                sistema.getClientes().remove(c);
                modelo.removeRow(fila);
                JOptionPane.showMessageDialog(this, "Cliente eliminado.");
            }
        }
    }
}

