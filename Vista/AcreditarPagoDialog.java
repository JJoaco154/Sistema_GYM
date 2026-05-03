package vista;

import modelo.*;
import javax.swing.*;
import java.awt.*;

public class AcreditarPagoDialog extends JDialog {

    public AcreditarPagoDialog(JFrame parent, Sistema sistema) {
        super(parent, "Acreditar Pago", true);
        setSize(350, 220);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(4, 1, 10, 10));

        JTextField txtDni = new JTextField();
        JButton btnBuscar = new JButton("Buscar cliente");
        JButton btnCerrar = new JButton("Cerrar");

        add(new JLabel("DNI del cliente:", SwingConstants.CENTER));
        add(txtDni);
        add(btnBuscar);
        add(btnCerrar);

        btnBuscar.addActionListener(e -> {
            try {
                int dni = Integer.parseInt(txtDni.getText());
                Cliente c = sistema.buscarClientePorDNI(dni);

                if (c == null) {
                    JOptionPane.showMessageDialog(this, "Cliente no encontrado.");
                    return;
                }

                int ok = JOptionPane.showConfirmDialog(
                        this,
                        "Cliente: " + c.getNombre() + " " + c.getApellido()
                        + "\nClases actuales: " + c.getClases()
                        + "\n\n¿Acreditar pago?",
                        "Confirmar pago",
                        JOptionPane.YES_NO_OPTION
                );

                if (ok == JOptionPane.YES_OPTION) {
                    c.setClases(c.getClases() + 30);
                    sistema.getClienteBD().actualizarClases(c.getDNI(), c.getClases());
                    JOptionPane.showMessageDialog(this, "Pago acreditado.");
                    dispose();
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "DNI inválido.");
            }
        });

        btnCerrar.addActionListener(e -> dispose());
    }
}

