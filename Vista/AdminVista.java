package vista;

import modelo.*;
import javax.swing.*;
import java.awt.*;

public class AdminVista extends JFrame {

    private final Administrador admin;
    private final Sistema sistema;

    public AdminVista(Administrador admin, Sistema sistema) {
        this.admin = admin;
        this.sistema = sistema;
        initUI();
    }

    private void initUI() {

        setTitle("Panel del Administrador");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ===== TÍTULO =====
        JLabel lblTitulo = new JLabel(
                "Administrador: " + admin.getNombre(),
                SwingConstants.CENTER
        );
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        add(lblTitulo, BorderLayout.NORTH);

        // ===== PANEL CENTRAL =====
        JPanel panel = new JPanel(new GridLayout(4, 2, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JButton btnClientes = crearBoton("Clientes");
        JButton btnEntrenadores = crearBoton("Entrenadores");
        JButton btnRutinas = crearBoton("Rutinas");
        JButton btnCrearCliente = crearBoton("Crear cliente");
        JButton btnCrearEntrenador = crearBoton("Crear entrenador");
        JButton btnAcreditarPago = crearBoton("Acreditar pago");
        JButton btnAsignarEntrenador = crearBoton("Asignar entrenador");
        JButton btnCerrarSesion = crearBotonPeligro("Cerrar sesión");

        panel.add(btnClientes);
        panel.add(btnEntrenadores);
        panel.add(btnRutinas);
        panel.add(btnCrearCliente);
        panel.add(btnCrearEntrenador);
        panel.add(btnAcreditarPago);
        panel.add(btnAsignarEntrenador);
        panel.add(btnCerrarSesion);

        add(panel, BorderLayout.CENTER);

        // ===== ACCIONES =====
        btnClientes.addActionListener(e ->
                new ClientePanelJFrame(sistema).setVisible(true)
        );

        btnEntrenadores.addActionListener(e ->
                new EntrenadorPanelJFrame(sistema).setVisible(true)
        );

        btnRutinas.addActionListener(e ->
                new RutinaPanelJFrame(this, sistema).setVisible(true)
        );

        btnCrearCliente.addActionListener(e ->
                new VistaCrearCliente(sistema).setVisible(true)
        );

        btnCrearEntrenador.addActionListener(e ->
                new CrearEntrenadorDialog(this, sistema).setVisible(true)
        );

        btnAcreditarPago.addActionListener(e ->
                new AcreditarPagoDialog(this, sistema).setVisible(true)
        );

        btnAsignarEntrenador.addActionListener(e ->
                new AsignarEntrenadorDialog(this, sistema).setVisible(true)
        );

        btnCerrarSesion.addActionListener(e -> cerrarSesion());
    }

    // ===== HELPERS UX =====
    private JButton crearBoton(String texto) {
        JButton b = new JButton(texto);
        b.setFont(new Font("Arial", Font.PLAIN, 14));
        return b;
    }

    private JButton crearBotonPeligro(String texto) {
        JButton b = crearBoton(texto);
        b.setBackground(Color.RED);
        b.setForeground(Color.WHITE);
        return b;
    }

    private void cerrarSesion() {
        dispose();
        new VistaLogin(sistema).setVisible(true);
    }
}
