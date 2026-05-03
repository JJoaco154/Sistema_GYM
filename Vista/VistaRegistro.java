package vista;

import modelo.*;
import javax.swing.*;
import java.awt.*;

public class VistaRegistro extends JFrame {

    private Sistema sistema;

    private JTextField txtNombre, txtApellido, txtDNI, txtPass, txtEspecialidad, txtClases;
    private JComboBox<String> comboTipo;
    private JButton btnRegistrar;

    public VistaRegistro(Sistema sistema) {
        this.sistema = sistema;

        setTitle("Registro de Usuario");
        setSize(400, 450);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 1));

        // ------------------ CAMPOS -------------------
        txtNombre = new JTextField(20);
        txtApellido = new JTextField(20);
        txtDNI = new JTextField(20);
        txtPass = new JTextField(20);

        txtEspecialidad = new JTextField(20);  // Solo entrenador
        txtClases = new JTextField(20);         // Solo cliente

        comboTipo = new JComboBox<>(new String[]{
            "Cliente", "Entrenador"
        });

        // PANEL NOMBRE
        add(crearPanel("Nombre:", txtNombre));
        add(crearPanel("Apellido:", txtApellido));
        add(crearPanel("DNI:", txtDNI));
        add(crearPanel("Contraseña:", txtPass));
        add(crearPanel("Tipo:", comboTipo));
        add(crearPanel("Especialidad (entrenador):", txtEspecialidad));
        add(crearPanel("Clases (cliente):", txtClases));

        // ------------------ BOTÓN REGISTRAR -------------------
        btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(e -> registrar());
        add(btnRegistrar);

        setVisible(true);
    }

    private JPanel crearPanel(String txt, JComponent comp) {
        JPanel p = new JPanel();
        p.add(new JLabel(txt));
        p.add(comp);
        return p;
    }

    private void registrar() {
        try {
            String nombre = txtNombre.getText();
            String apellido = txtApellido.getText();
            int dni = Integer.parseInt(txtDNI.getText());
            String pass = txtPass.getText();
            String tipo = comboTipo.getSelectedItem().toString();

            if (tipo.equals("Cliente")) {
                int clases = Integer.parseInt(txtClases.getText());

                Cliente nuevo = new Cliente(
                        nombre, apellido, dni, pass, clases, 0
                );

                if (sistema.registrarCliente(nuevo)) {
                    JOptionPane.showMessageDialog(this, 
                        "Cliente registrado con éxito");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "El cliente ya existe!", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }

            } else {

                String esp = txtEspecialidad.getText();

                Entrenador e = new Entrenador(
                        nombre, apellido, dni, esp
                );
                e.setContraseña(pass);

                if (sistema.registrarEntrenador(e)) {
                    JOptionPane.showMessageDialog(this, 
                        "Entrenador registrado con éxito");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "El entrenador ya existe!", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error en formato numérico",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}


