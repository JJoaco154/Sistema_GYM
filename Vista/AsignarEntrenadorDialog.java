package vista;

import modelo.*;
import javax.swing.*;
import java.awt.*;

public class AsignarEntrenadorDialog extends JDialog {

    public AsignarEntrenadorDialog(JFrame parent, Sistema sistema) {
        super(parent, "Asignar Entrenador", true);
        setSize(600, 400);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(1, 3, 10, 10));

        DefaultListModel<Cliente> modeloC = new DefaultListModel<>();
        sistema.getClientes().forEach(modeloC::addElement);

        DefaultListModel<Entrenador> modeloE = new DefaultListModel<>();
        sistema.getEntrenadores().forEach(modeloE::addElement);

        JList<Cliente> listaClientes = new JList<>(modeloC);
        JList<Entrenador> listaEntrenadores = new JList<>(modeloE);

        JButton btnAsignar = new JButton("Asignar");

        add(new JScrollPane(listaClientes));
        add(new JScrollPane(listaEntrenadores));
        add(btnAsignar);

        btnAsignar.addActionListener(e -> {
            Cliente c = listaClientes.getSelectedValue();
            Entrenador en = listaEntrenadores.getSelectedValue();

            if (c == null || en == null) {
                JOptionPane.showMessageDialog(this, "Seleccione cliente y entrenador.");
                return;
            }

            c.setIdEntrenador(en.getDNI());
            sistema.getClienteBD().asignarEntrenador(c.getDNI(), en.getDNI());

            JOptionPane.showMessageDialog(this, "Entrenador asignado.");
            dispose();
        });
    }
}

