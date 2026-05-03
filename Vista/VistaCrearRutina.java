package vista;

import modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class VistaCrearRutina extends JFrame {

    private Sistema sistema;
    private Entrenador entrenador;
    private EntrenadorService service;

    private JTextField txtNombre;
    private JTable tablaEjercicios;
    private DefaultTableModel modeloEj;
    private DefaultListModel<String> listaSeleccionados;
    private ArrayList<Ejercicio> ejerciciosSeleccionados = new ArrayList<>();

    public VistaCrearRutina(Sistema sistema, Entrenador entrenador) {
        this.sistema = sistema;
        this.entrenador = entrenador;
        this.service = new EntrenadorService(sistema);

        setTitle("Crear Rutina");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        initUI();
        cargarEjercicios();
    }

    private void initUI() {

        JPanel panelTop = new JPanel(new GridLayout(2, 1));
        panelTop.add(new JLabel("Nombre de la rutina:"));
        txtNombre = new JTextField();
        panelTop.add(txtNombre);

        add(panelTop, BorderLayout.NORTH);

        // Tabla de ejercicios disponibles
        modeloEj = new DefaultTableModel(new String[]{"Nombre", "Descripción"}, 0);
        tablaEjercicios = new JTable(modeloEj);

        JButton btnAgregar = new JButton("Agregar ejercicio");
        btnAgregar.addActionListener(e -> agregarEjercicio());

        JPanel centro = new JPanel(new BorderLayout());
        centro.add(new JScrollPane(tablaEjercicios), BorderLayout.CENTER);
        centro.add(btnAgregar, BorderLayout.SOUTH);

        add(centro, BorderLayout.CENTER);

        // Lista de ejercicios seleccionados
        listaSeleccionados = new DefaultListModel<>();
        JList<String> lista = new JList<>(listaSeleccionados);

        JPanel panelDer = new JPanel(new BorderLayout());
        panelDer.add(new JLabel("Ejercicios en la rutina:"), BorderLayout.NORTH);
        panelDer.add(new JScrollPane(lista), BorderLayout.CENTER);

        add(panelDer, BorderLayout.EAST);

        JButton btnGuardar = new JButton("Guardar rutina");
        btnGuardar.addActionListener(e -> guardarRutina());

        add(btnGuardar, BorderLayout.SOUTH);
    }

    private void cargarEjercicios() {
        modeloEj.setRowCount(0);

        for (Ejercicio e : sistema.getEjercicios()) {
            modeloEj.addRow(new Object[]{
                    e.getNombre(),
                    e.getDescripcion()
            });
        }
    }

    private void agregarEjercicio() {
        int fila = tablaEjercicios.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un ejercicio.");
            return;
        }

        Ejercicio e = sistema.getEjercicios().get(fila);

        ejerciciosSeleccionados.add(e);
        listaSeleccionados.addElement(e.getNombre());
    }

    private void guardarRutina() {
        String nombre = txtNombre.getText().trim();
        String desc = txtNombre.getText().trim();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La rutina debe tener nombre.");
            return;
        }

        Rutina r = new Rutina(
                nombre,
                desc,
                false,                       // estado = sin terminar
                ""                          // fecha actual
                                          // sin cliente asignado todavía
        );

        // agregar ejercicios a la rutina
        for (Ejercicio e : ejerciciosSeleccionados) {
            r.agregarEjercicio(e);
        }

        service.crearRutina(r);

        JOptionPane.showMessageDialog(this, "Rutina creada correctamente.");
        dispose();
    }
}

