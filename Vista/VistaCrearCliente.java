package vista;

import modelo.*;
import persistencia.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VistaCrearCliente extends JFrame {

    private Sistema sistema;
    private Entrenador entrenadorLogueado; // null si es admin

    private JTextField txtNombre, txtApellido, txtDNI, txtClases, txtEntrenador;
    private JPasswordField txtContra;
    private JButton btnCrear, btnCancelar;

    // ==================================================
    // 1) Constructor para ADMIN
    // ==================================================
    public VistaCrearCliente(Sistema sistema) {
        this.sistema = sistema;
        this.entrenadorLogueado = null;  // admin crea libremente
        setTitle("Crear Cliente");
        init();
    }

    // ==================================================
    // 2) Constructor para ENTRENADOR
    // ==================================================
    public VistaCrearCliente(Sistema sistema, Entrenador ent) {
        this.sistema = sistema;
        this.entrenadorLogueado = ent;
        setTitle("Crear Cliente");
        init();

        // autocompletar el DNI del entrenador
        txtEntrenador.setText(String.valueOf(ent.getDNI()));
        txtEntrenador.setEditable(false);

        setVisible(true);
    }

    // ==================================================
    // INICIALIZAR COMPONENTES
    // ==================================================
    private void init() {
        setSize(450, 410);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(6, 2, 8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panel.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panel.add(txtNombre);

        panel.add(new JLabel("Apellido:"));
        txtApellido = new JTextField();
        panel.add(txtApellido);

        panel.add(new JLabel("DNI:"));
        txtDNI = new JTextField();
        panel.add(txtDNI);

        panel.add(new JLabel("Contraseña:"));
        txtContra = new JPasswordField();
        panel.add(txtContra);

        panel.add(new JLabel("Clases iniciales:"));
        txtClases = new JTextField();
        panel.add(txtClases);

        panel.add(new JLabel("DNI Entrenador asignado:"));
        txtEntrenador = new JTextField();
        panel.add(txtEntrenador);

        add(panel, BorderLayout.CENTER);

        // BOTONES
        JPanel pie = new JPanel(new FlowLayout());
        btnCrear = new JButton("Crear");
        btnCancelar = new JButton("Cancelar");

        btnCrear.addActionListener(e -> crearCliente());
        btnCancelar.addActionListener(e -> dispose());

        pie.add(btnCrear);
        pie.add(btnCancelar);
        add(pie, BorderLayout.SOUTH);

        setVisible(true);
    }

    // ==================================================
    // CREAR CLIENTE
    // ==================================================
    private void crearCliente() {
        try {
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            int dni = Integer.parseInt(txtDNI.getText().trim());
            String contra = new String(txtContra.getPassword());
            int clases = Integer.parseInt(txtClases.getText().trim());

            int idEntrenador = Integer.parseInt(txtEntrenador.getText().trim());

            // Crear objeto
            Cliente nuevo = new Cliente(
                    nombre,
                    apellido,
                    dni,
                    contra,
                    clases,
                    idEntrenador
            );

            // 1) Insertar en BD
        boolean ok = sistema.getClienteBD().insertarCliente(nuevo);

        if (!ok) {
            JOptionPane.showMessageDialog(this, "Error al crear cliente.");
            return;
        }

        // 2) Si lo creó un entrenador, asignarlo también en BD
        if (entrenadorLogueado != null) {
            sistema.getClienteBD().asignarEntrenador(dni, entrenadorLogueado.getDNI());

            // actualizar en memoria
            nuevo.setIdEntrenador(entrenadorLogueado.getDNI());
            entrenadorLogueado.getCliente().add(nuevo);
        }

        JOptionPane.showMessageDialog(this, "Cliente creado correctamente.");

        // 3) Cerrar ventana AL FINAL
        dispose();  // ahora sí VistaEntrenador refresca correctamente etrenador recibe el evento y hace cargarClientes()

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: ingresar datos válidos.\n" + ex.getMessage());
        }
    }
}
