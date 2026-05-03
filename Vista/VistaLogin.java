package vista;

import modelo.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VistaLogin extends JFrame {

    private Sistema sistema;
    private ClienteService clienteService;
    private EntrenadorService entrenadorService;
    private AdminService adminService;

    private JTextField txtDNI;
    private JPasswordField txtPass;
    private JComboBox<String> comboTipo;
    private JButton btnLogin, btnRegistro;

    public VistaLogin(Sistema sistema) {
        this.sistema = sistema;
        

        setTitle("Login - Gimnasio");
        setSize(350, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1));

        // ------------------ DNI -------------------
        JPanel p1 = new JPanel();
        p1.add(new JLabel("DNI: "));
        txtDNI = new JTextField(15);
        p1.add(txtDNI);
        add(p1);

        // ------------------ CONTRASEÑA -------------------
        JPanel p2 = new JPanel();
        p2.add(new JLabel("Contraseña: "));
        txtPass = new JPasswordField(15);
        p2.add(txtPass);
        add(p2);

        // ------------------ TIPO -------------------
        JPanel p3 = new JPanel();
        comboTipo = new JComboBox<>(new String[]{
            "Cliente", "Entrenador", "Administrador"
        });
        p3.add(new JLabel("Tipo: "));
        p3.add(comboTipo);
        add(p3);

        // ------------------ BOTÓN LOGIN -------------------
        btnLogin = new JButton("Iniciar Sesión");
        btnLogin.addActionListener(e -> login());
        add(btnLogin);

        // ------------------ REGISTRO -------------------
        btnRegistro = new JButton("Registrarse");
        btnRegistro.addActionListener(e -> abrirRegistro());
        add(btnRegistro);

        setVisible(true);
    }

    private void login() {

        try {
            int dni = Integer.parseInt(txtDNI.getText());
            String pass = new String(txtPass.getPassword());
            String tipo = comboTipo.getSelectedItem().toString();

            Usuario u = sistema.login(dni, pass, tipo);

            if (u == null) {
                JOptionPane.showMessageDialog(this,
                        "Datos incorrectos",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this,
                    "Bienvenido: " + u.getNombre());

            if (u instanceof Administrador) {
                AdminVista f = new AdminVista((Administrador) u, sistema);
                f.setVisible(true);
            }
            dispose();
            
            if (u instanceof Entrenador) {
                VistaEntrenador f = new VistaEntrenador((Entrenador) u, sistema);
                f.setVisible(true);
            }
            dispose();
            
            if (u instanceof Cliente) {
                VistaCliente f = new VistaCliente((Cliente) u, sistema);
                f.setVisible(true);
            }
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "El DNI debe ser numérico.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    private void abrirRegistro() {
        new VistaRegistro(sistema);
    }
}
