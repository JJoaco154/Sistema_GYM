package vista;

import modelo.*;
import javax.swing.*;
import java.awt.*;

public class CrearEntrenadorDialog extends JDialog {

    public CrearEntrenadorDialog(JFrame parent, Sistema sistema) {
        super(parent, "Crear Entrenador", true);
        setSize(350, 250);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(5, 2, 10, 10));

        JTextField txtNombre = new JTextField();
        JTextField txtApellido = new JTextField();
        JTextField txtDni = new JTextField();
        JPasswordField txtPass = new JPasswordField();

        add(new JLabel("Nombre:"));
        add(txtNombre);
        add(new JLabel("Apellido:"));
        add(txtApellido);
        add(new JLabel("DNI:"));
        add(txtDni);
        add(new JLabel("Contraseña:"));
        add(txtPass);

        JButton btnGuardar = new JButton("Crear");
        JButton btnCancelar = new JButton("Cancelar");

        add(btnGuardar);
        add(btnCancelar);

        btnGuardar.addActionListener(e -> {
            try {
                int dni = Integer.parseInt(txtDni.getText());
                Entrenador ent = new Entrenador(
                        txtNombre.getText(),
                        txtApellido.getText(),
                        dni,
                        new String(txtPass.getPassword())
                );

                if (sistema.registrarEntrenador(ent)) {
                    JOptionPane.showMessageDialog(this, "Entrenador creado.");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Ya existe ese DNI.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Datos inválidos.");
            }
        });

        btnCancelar.addActionListener(e -> dispose());
    }
}

