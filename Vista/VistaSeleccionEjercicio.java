package vista;

import java.awt.BorderLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import modelo.Ejercicio;
import modelo.Rutina;
import modelo.Sistema;

public class VistaSeleccionEjercicio extends JDialog {

    private JList<Ejercicio> listaEjercicios;
    private DefaultListModel<Ejercicio> modelo;
    private JButton btnAgregar;

    private Rutina rutina;
    private Sistema sistema;

    public VistaSeleccionEjercicio(JFrame parent, Sistema sistema, Rutina rutina) {

        super(parent, true);

        this.sistema = sistema;
        this.rutina = rutina;

        initComponents();
        cargarEjercicios();
    }

    private void initComponents() {

        modelo = new DefaultListModel<>();
        listaEjercicios = new JList<>(modelo);

        btnAgregar = new JButton("Agregar ejercicio");
        btnAgregar.addActionListener(e -> agregarEjercicio());

        setLayout(new BorderLayout());
        add(new JScrollPane(listaEjercicios), BorderLayout.CENTER);
        add(btnAgregar, BorderLayout.SOUTH);

        setTitle("Seleccionar ejercicio");
        setSize(300, 400);
        setLocationRelativeTo(null);
    }

    private void cargarEjercicios() {

        for (Ejercicio e : sistema.getEjercicioBD().listarEjercicios()) {
            modelo.addElement(e);
        }
    }

    private void agregarEjercicio() {

        Ejercicio ej = listaEjercicios.getSelectedValue();

        if (ej == null) {
            JOptionPane.showMessageDialog(this,
                    "Debe seleccionar un ejercicio");
            return;
        }

        if (rutina.getId() <= 0) {
            JOptionPane.showMessageDialog(this,
                    "Debe guardar la rutina antes de agregar ejercicios");
            return;
        }

        boolean ok = sistema
                .getRutinaEjercicioBD()
                .asignarEjercicio(
                        rutina.getId(),
                        ej.getId()
                );

        if (ok) {

            rutina.getLista().add(ej);

            JOptionPane.showMessageDialog(this,
                    "Ejercicio agregado y guardado");

            dispose();

        } else {

            JOptionPane.showMessageDialog(this,
                    "Error guardando ejercicio");
        }
    }
}
