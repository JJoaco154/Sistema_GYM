package vista;

import modelo.*;
import persistencia.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class VistaCliente extends JFrame {

    private Cliente cliente;
    private Sistema sistema;

    private ClienteService clienteService;
    private RutinaService rutinaService;

    // COMPONENTES
    private JLabel lblClases;
    private JLabel lblEntrenador;
    private JTextArea infoRutina;
    private DefaultListModel<Ejercicio> modeloEjercicios;
    private JList<Ejercicio> listaEjercicios;
    private DefaultListModel<Rutina> modeloHistorial;
    private JList<Rutina> listaHistorial;
    private JLabel lblNombre;
    private JLabel lblRutina;
    private JLabel lblApellido;
    private JLabel lblDni;

    public VistaCliente(Cliente cliente, Sistema sistema) {
        this.cliente = cliente;
        this.sistema = sistema;

        this.clienteService = new ClienteService(sistema);
        this.rutinaService = new RutinaService(new RutinaBdD());

        setTitle("Panel del Cliente - " + cliente.getNombre());
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();
        cargarDatos();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // PANEL IZQUIERDA: Datos personales
        JPanel panelInfo = new JPanel(new GridLayout(6, 1, 4, 4));
        panelInfo.setBorder(BorderFactory.createTitledBorder("Datos personales"));

        /*panelInfo.add(new JLabel("Nombre: " + cliente.getNombre()));
        panelInfo.add(new JLabel("Apellido: " + cliente.getApellido()));
        panelInfo.add(new JLabel("DNI: " + cliente.getDNI()));*/
        lblNombre = new JLabel("Nombre: " + cliente.getNombre());
        panelInfo.add(lblNombre);

        JLabel lblApellido = new JLabel("Apellido: " + cliente.getApellido());
        panelInfo.add(lblApellido);

        JLabel lblDni = new JLabel("DNI: " + cliente.getDNI());
        panelInfo.add(lblDni);

        lblClases = new JLabel("Clases restantes: " + cliente.getClases());
        panelInfo.add(lblClases);

        lblEntrenador = new JLabel("Entrenador asignado: " + cliente.getIdEntrenador());
        panelInfo.add(lblEntrenador);

        JButton btnActualizarClases = new JButton("Actualizar clases en BD");
        btnActualizarClases.addActionListener(e -> actualizarClases());
        panelInfo.add(btnActualizarClases);

        add(panelInfo, BorderLayout.WEST);

        // PANEL CENTRAL: Rutina actual
        JPanel panelRutina = new JPanel();
        /*panelRutina.setLayout(new BorderLayout());
        panelRutina.setBorder(BorderFactory.createTitledBorder("Rutina actual"));
        lblRutina = new JLabel("Rutina actual: ");
        panelRutina.add(lblRutina, BorderLayout.NORTH);

        // Info de la rutina arriba
        infoRutina = new JTextArea();
        infoRutina.setEditable(false);
        infoRutina.setLineWrap(true);
        infoRutina.setWrapStyleWord(true);
        infoRutina.setBackground(panelRutina.getBackground());
        infoRutina.setFont(new Font("Arial", Font.BOLD, 14));
        panelRutina.add(infoRutina, BorderLayout.NORTH);*/

        // PANEL SUPERIOR (nombre + descripción)
        JPanel panelTopRutina = new JPanel(new BorderLayout());
        panelTopRutina.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        lblRutina = new JLabel("Rutina actual: ");
        lblRutina.setFont(new Font("Arial", Font.BOLD, 16));
        panelTopRutina.add(lblRutina, BorderLayout.NORTH);

        // Cuadro con la descripción
        infoRutina = new JTextArea();
        infoRutina.setEditable(false);
        infoRutina.setLineWrap(true);
        infoRutina.setWrapStyleWord(true);
        infoRutina.setBackground(getBackground());
        panelTopRutina.add(infoRutina, BorderLayout.CENTER);

        // AGREGAMOS ESTE PANEL SUPERIOR A LA RUTINA
        panelRutina.add(panelTopRutina, BorderLayout.NORTH);

        // Lista de ejercicios abajo
        modeloEjercicios = new DefaultListModel<>();
        listaEjercicios = new JList<>(modeloEjercicios);
        listaEjercicios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        listaEjercicios.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Ejercicio ex = listaEjercicios.getSelectedValue();

                    if (ex != null && !ex.isRealizado()) {
                        ex.marcarRealizado();

                        // GUARDAR EN BD
                        sistema.getRutinaEjercicioBD().actualizarEstadoEjercicio(ex.getId(), true);

                        listaEjercicios.repaint();
                        JOptionPane.showMessageDialog(VistaCliente.this,
                                "Ejercicio '" + ex.getNombre() + "' marcado como completado.");
                    }
                }
            }
        });

        JScrollPane scrollEjercicios = new JScrollPane(listaEjercicios);
        scrollEjercicios.setPreferredSize(new Dimension(400, 300));
        panelRutina.add(scrollEjercicios, BorderLayout.CENTER);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnMarcar = new JButton("Marcar ejercicio completado");
        btnMarcar.addActionListener(e -> marcarEjercicioSeleccionado());
        JButton btnFinalizar = new JButton("Finalizar rutina");
        btnFinalizar.addActionListener(e -> finalizarRutina());
        JButton btnIniciar = new JButton("Iniciar rutina");
        btnIniciar.addActionListener(e -> iniciarRutina());
        panelBotones.add(btnIniciar);
        panelBotones.add(btnMarcar);
        panelBotones.add(btnFinalizar);
        panelRutina.add(panelBotones, BorderLayout.SOUTH);

        add(panelRutina, BorderLayout.CENTER);

        // PANEL DERECHO: Historial de rutinas
        JPanel panelHistorial = new JPanel(new BorderLayout());
        panelHistorial.setBorder(BorderFactory.createTitledBorder("Historial de rutinas"));

        modeloHistorial = new DefaultListModel<>();
        listaHistorial = new JList<>(modeloHistorial);
        JScrollPane scrollHistorial = new JScrollPane(listaHistorial);
        panelHistorial.add(scrollHistorial, BorderLayout.CENTER);
        listaHistorial.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // doble click
                    Rutina rutinaSeleccionada = listaHistorial.getSelectedValue();
                    if (rutinaSeleccionada != null) {
                        mostrarEjerciciosHistorial(rutinaSeleccionada);
                    }
                }
            }
        });

        add(panelHistorial, BorderLayout.EAST);
    }

    // ==========================================================
    // CARGAR DATOS
    // ==========================================================
    private void cargarDatos() {
        cliente.setRutinaActual(sistema.getRutinaclienteService().getRutinaActual(cliente.getDNI()));
        sistema.getRutinaclienteService().cargarEjerciciosRutina(cliente.getRutinaActual());

        //CARGAR HISTORIAL DESDE BD
        cliente.getHistorialR().clear();
        cliente.getHistorialR().addAll(sistema.getRutinaclienteService().getRutinasCliente(cliente.getDNI()));

        mostrarRutinaActual();

        modeloHistorial.clear();
        for (Rutina r : cliente.getHistorialR()) {
            modeloHistorial.addElement(r);
        }
    }

    private void mostrarRutinaActual() {
        Rutina r = cliente.getRutinaActual();
        modeloEjercicios.clear();

        if (r == null) {
            infoRutina.setText("No tenés una rutina asignada.");
            return;
        }

        // Mostrar nombre y descripción
        infoRutina.setText("Rutina: " + r.getNombre() + "\nDescripción: " + r.getDescripcion());

        // Obtener ejercicios desde BD
        List<Ejercicio> ejercicios = sistema.getRutinaEjercicioBD().obtenerEjerciciosDeRutina(r.getId());
        r.setLista(ejercicios);

        // Cargar ejercicios en la lista visible
        for (Ejercicio e : ejercicios) {
            modeloEjercicios.addElement(e);
        }

        System.out.println(">>> MOSTRANDO RUTINA ACTUAL <<<");
        System.out.println("ID Rutina actual = " + r.getId());
        System.out.println("Nombre: " + r.getNombre());

    }

    // ==========================================================
    // ACCIONES
    // ==========================================================
    private void iniciarRutina() {

        Rutina r = cliente.getRutinaActual();

        if (r == null) {
            JOptionPane.showMessageDialog(this, "No tenés una rutina asignada.");
            return;
        }

        // 🔥 Llama a ClienteService para verificar clases y aplicar reglas
        boolean ok = clienteService.iniciarRutina(cliente, r);

        if (!ok) {
            // El service ya hizo las validaciones: sin clases, descuento, etc
            JOptionPane.showMessageDialog(this,
                    "No podés iniciar la rutina. Verificá tus clases o reglas del gimnasio.");
            return;
        }

        // Si se inició correctamente:
        JOptionPane.showMessageDialog(this,
                "Rutina iniciada correctamente. ¡Dale con todo!");

        actualizarVista();
    }

    private void marcarEjercicioSeleccionado() {
        Rutina r = cliente.getRutinaActual();
        if (r == null || r.getLista().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay ejercicios para marcar.");
            return;
        }

        Ejercicio e = listaEjercicios.getSelectedValue();
        if (e == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un ejercicio de la lista.");
            return;
        }

        if (e.isRealizado()) {
            JOptionPane.showMessageDialog(this, "El ejercicio ya estaba completado.");
            return;
        }

        e.marcarRealizado();
        listaEjercicios.repaint();
        JOptionPane.showMessageDialog(this, "Ejercicio '" + e.getNombre() + "' marcado como completado.");
    }

    private void finalizarRutina() {

        Rutina r = cliente.getRutinaActual();
        if (r == null) {
            JOptionPane.showMessageDialog(this, "No tenés una rutina en curso.");
            return;
        }

        boolean ok = clienteService.finalizarRutina(cliente);

        if (!ok) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo finalizar la rutina. Revisá si está iniciada.");
            return;
        }

        JOptionPane.showMessageDialog(this,
                "Rutina finalizada. Se descontó una clase automáticamente.");

        actualizarVista();
    }

    private void actualizarClases() {
        boolean ok = sistema.getClienteBD().actualizarClases(cliente.getDNI(), cliente.getClases());
        if (ok) {
            JOptionPane.showMessageDialog(this, "Clases actualizadas correctamente.");
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar.");
        }
        lblClases.setText("Clases restantes: " + cliente.getClases());
    }

    private void actualizarVista() {

        // Actualiza etiquetas personales
        lblNombre.setText("Nombre: " + cliente.getNombre());
        lblClases.setText("Clases restantes: " + cliente.getClases());

        Rutina r = cliente.getRutinaActual();

        if (r == null) {
            lblRutina.setText("Rutina actual: (ninguna)");
            infoRutina.setText("No tenés una rutina asignada.");
            modeloEjercicios.clear();
            return;
        }

        // Mostrar nombre de la rutina
        lblRutina.setText("Rutina actual: " + r.getNombre());

        // Mostrar datos
        infoRutina.setText(
                "Descripción: " + r.getDescripcion()
        );

        // Cargar ejercicios en la lista
        modeloEjercicios.clear();
        for (Ejercicio e : r.getLista()) {
            modeloEjercicios.addElement(e);
        }

        repaint();
        revalidate();
    }

    private void cargarEjercicios(Rutina r) {
        modeloEjercicios.clear();
        if (r == null) {
            return;
        }
        for (Ejercicio e : r.getLista()) {
            modeloEjercicios.addElement(e);
        }
    }

    private void limpiarTablaEjercicios() {
        modeloEjercicios.clear();
    }

    private void mostrarEjerciciosHistorial(Rutina r) {

        // Obtener ejercicios desde BD
        List<Ejercicio> ejercicios = sistema.getRutinaEjercicioBD().obtenerEjerciciosDeRutina(r.getId());

        // Ventanita
        JDialog dialogo = new JDialog(this, "Ejercicios de " + r.getNombre(), true);
        dialogo.setSize(400, 300);
        dialogo.setLocationRelativeTo(this);

        DefaultListModel<String> modelo = new DefaultListModel<>();
        for (Ejercicio e : ejercicios) {
            modelo.addElement(e.getNombre());
        }

        JList<String> lista = new JList<>(modelo);
        JScrollPane scroll = new JScrollPane(lista);

        dialogo.add(scroll);
        dialogo.setVisible(true);
    }

}
