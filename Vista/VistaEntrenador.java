/*package vista;

import modelo.*;
import modelo.Rutina;
import modelo.Ejercicio;
import modelo.Cliente;
import modelo.Entrenador;
import modelo.EntrenadorService;
import modelo.ClienteService;
import modelo.RutinaService;
import vista.VistaCrearCliente;
import vista.VistaCrearCliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VistaEntrenador extends JFrame {

    private final Entrenador entrenador;
    private final Sistema sistema;
    private final EntrenadorService entrenadorService;
    private final ClienteService clienteService;
    private final RutinaService rutinaService;
    private Rutina rutina;
    private Rutina rutinaActual;

    // Modelos UI
    private DefaultListModel<Cliente> modelClientes = new DefaultListModel<>();
    private DefaultListModel<Rutina> modelRutinas = new DefaultListModel<>();
    private DefaultListModel<Ejercicio> modelEjercicios = new DefaultListModel<>();

    private JList<Cliente> listClientes;
    private JList<Rutina> listRutinas;
    private JList<Ejercicio> listEjercicios;
    private DefaultListModel<Ejercicio> modelo;
    private JButton btnAgregar;

    private JButton btnCrearEjercicio;
    private JButton btnCrearRutina;
    private JButton btnEliminarRutina;
    private JButton btnAsignarRutina;
    //private JButton btnCargarCliente;        // "Agregar cliente" -> elegir desde BD
    private JButton btnCrearClienteNuevo;   // "Crear cliente nuevo"
    private JButton btnVerHistorial;
    private JButton btnRefrescarClientes;

    public VistaEntrenador(Entrenador entrenador, Sistema sistema) {
        this.entrenador = entrenador;
        this.sistema = sistema;

        // Obtener los services desde el sistema (asegurate que Sistema tenga getters)
        this.entrenadorService = sistema.getEntrenadorService();
        this.clienteService = sistema.getClienteService();
        this.rutinaService = sistema.getRutinaService();

        setTitle("Panel Entrenador - " + entrenador.getNombre());
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();
        cargarDatosEnListas();
    }

    private void initComponents() {
        // Layout principal
        JPanel main = new JPanel(new BorderLayout(10, 10));
        getContentPane().add(main);

        // Top
        JLabel lblTitulo = new JLabel("Entrenador: " + entrenador.getNombre() + " " + entrenador.getApellido(), SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        main.add(lblTitulo, BorderLayout.NORTH);

        // Center: 3 columnas
        JPanel center = new JPanel(new GridLayout(1, 3, 10, 10));
        main.add(center, BorderLayout.CENTER);

        // ---------------- Clientes ----------------
        JPanel pnlClientes = new JPanel(new BorderLayout(5, 5));
        pnlClientes.setBorder(BorderFactory.createTitledBorder("Clientes a cargo"));
        listClientes = new JList<>(modelClientes);
        listClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        pnlClientes.add(new JScrollPane(listClientes), BorderLayout.CENTER);

        JPanel pnlClientesBtns = new JPanel(new GridLayout(4, 1, 5, 5));
        //btnCargarCliente = new JButton("Agregar cliente (desde BDD)");
        btnCrearClienteNuevo = new JButton("Crear cliente nuevo");
        btnVerHistorial = new JButton("Ver historial cliente");
        btnRefrescarClientes = new JButton("Refrescar lista");
        //pnlClientesBtns.add(btnCargarCliente);
        pnlClientesBtns.add(btnCrearClienteNuevo);
        pnlClientesBtns.add(btnVerHistorial);
        pnlClientesBtns.add(btnRefrescarClientes);
        pnlClientes.add(pnlClientesBtns, BorderLayout.SOUTH);
        center.add(pnlClientes);

        // ---------------- Rutinas ----------------
        JPanel pnlRutinas = new JPanel(new BorderLayout(5, 5));
        pnlRutinas.setBorder(BorderFactory.createTitledBorder("Rutinas (creadas por el entrenador)"));
        listRutinas = new JList<>(modelRutinas);
        listRutinas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listRutinas.addListSelectionListener(e -> {
            rutinaActual = listRutinas.getSelectedValue();
        });
        listRutinas.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Rutina r = listRutinas.getSelectedValue();
                mostrarEjerciciosDeRutina(r);
            }
        });

        pnlRutinas.add(new JScrollPane(listRutinas), BorderLayout.CENTER);

        JPanel pnlRutinasBtns = new JPanel(new GridLayout(3, 1, 5, 5));
        btnCrearRutina = new JButton("Crear rutina");
        btnAsignarRutina = new JButton("Asignar rutina a cliente");
        btnEliminarRutina = new JButton("Eliminar rutina");
        JButton btnAgregarEjercicio = new JButton("Agregar ejercicios");
        pnlRutinasBtns.add(btnAgregarEjercicio);
        pnlRutinasBtns.add(btnCrearRutina);
        pnlRutinasBtns.add(btnAsignarRutina);
        pnlRutinasBtns.add(btnEliminarRutina);
        pnlRutinas.add(pnlRutinasBtns, BorderLayout.SOUTH);
        center.add(pnlRutinas);

        // ---------------- Ejercicios ----------------
        JPanel pnlEjercicios = new JPanel(new BorderLayout(5, 5));
        pnlEjercicios.setBorder(BorderFactory.createTitledBorder("Ejercicios"));
        listEjercicios = new JList<>(modelEjercicios);
        listEjercicios.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        pnlEjercicios.add(new JScrollPane(listEjercicios), BorderLayout.CENTER);

        JPanel pnlEjBtns = new JPanel(new GridLayout(2, 1, 5, 5));
        btnCrearEjercicio = new JButton("Crear ejercicio");
        JButton btnRefrescarEj = new JButton("Refrescar ejercicios");
        pnlEjBtns.add(btnCrearEjercicio);
        pnlEjBtns.add(btnRefrescarEj);
        pnlEjercicios.add(pnlEjBtns, BorderLayout.SOUTH);
        center.add(pnlEjercicios);

        // Bottom: cerrar
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnCerrar = new JButton("Cerrar");
        bottom.add(btnCerrar);
        main.add(bottom, BorderLayout.SOUTH);

        // ---------- Listeners ----------
        btnCerrar.addActionListener(e -> dispose());

        btnRefrescarClientes.addActionListener(e -> cargarClientes());
        btnRefrescarEj.addActionListener(e -> cargarEjercicios());

        btnCrearEjercicio.addActionListener(e -> crearEjercicioDialog());
        btnCrearRutina.addActionListener(e -> crearRutinaDialog());
        btnEliminarRutina.addActionListener(e -> eliminarRutina());
        btnAsignarRutina.addActionListener(e -> asignarRutinaACliente());
        //btnCargarCliente.addActionListener(e -> agregarClienteAlEntrenador()); // elegir desde BDD
        btnCrearClienteNuevo.addActionListener(e -> {
            VistaCrearCliente v = new VistaCrearCliente(sistema, entrenador);
            v.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    cargarClientes(); // <-- refrescar al cerrar
                }
            });
        });

        btnVerHistorial.addActionListener(e -> mostrarHistorialCliente());

        // doble click en cliente -> historial
        listClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    mostrarHistorialCliente();
                }
            }
        });

        // listener para agregar ejercicios
        btnAgregarEjercicio.addActionListener(e -> {

            if (rutinaActual == null) {
                JOptionPane.showMessageDialog(this,
                        "Debe seleccionar una rutina primero.");
                return;
            }

            // Abrir la ventana para elegir ejercicios
            VistaSeleccionEjercicio v
                    = new VistaSeleccionEjercicio(this, sistema, rutinaActual);

            v.setVisible(true);

            // Actualizar ejercicios dentro de esa rutina
            cargarRutinas();
        });

    }

    private void cargarDatosEnListas() {
        cargarClientes();
        cargarRutinas();
        cargarEjercicios();
    }

    // Carga los clientes que el entrenador tiene en memoria
    private void cargarClientes() {
        modelClientes.clear();

        // Obtener clientes del entrenador desde la BD
        ArrayList<Cliente> clientesBD
                = sistema.getClienteBD().listarClientesPorEntrenador(entrenador.getDNI());

        // Actualizar lista del entrenador en memoria
        entrenador.getCliente().clear();
        entrenador.getCliente().addAll(clientesBD);

        // Mostrar en la JList
        for (Cliente c : clientesBD) {
            modelClientes.addElement(c);
        }
    }

    private void cargarRutinas() {
        modelRutinas.clear();
        for (Rutina r : entrenador.getHistorialR()) {
            modelRutinas.addElement(r);
        }
    }

    private void cargarEjercicios() {
        modelEjercicios.clear();
        // ejercicios globales del sistema (si existieran)
        if (sistema.getEjercicios() != null) {
            for (Ejercicio e : sistema.getEjercicios()) {
                modelEjercicios.addElement(e);
            }
        }
        // ejercicios propios del entrenador (evitar duplicados)
        for (Ejercicio e : entrenador.getEjercicio()) {
            if (!containsEjercicio(modelEjercicios, e)) {
                modelEjercicios.addElement(e);
            }
        }
    }

    private boolean containsEjercicio(DefaultListModel<Ejercicio> model, Ejercicio e) {
        for (int i = 0; i < model.size(); i++) {
            if (model.get(i).equals(e)) {
                return true;
            }
        }
        return false;
    }

    private void mostrarEjerciciosDeRutina(Rutina r) {
        modelEjercicios.clear();

        if (r == null) {
            return;
        }

        // Cargar desde BD
        List<Ejercicio> ejercicios
                = sistema.getRutinaEjercicioBD().obtenerEjerciciosDeRutina(r.getId());

        for (Ejercicio e : ejercicios) {
            modelEjercicios.addElement(e);
        }
    }

    // ------------------ ACCIONES ---------------------
    private void crearEjercicioDialog() {
        JTextField txtNombre = new JTextField();
        JTextField txtDesc = new JTextField();
        JTextField txtReps = new JTextField();
        JTextField txtPeso = new JTextField();
        JTextField txtGrupoM = new JTextField();

        Object[] campos = {
            "Nombre:", txtNombre,
            "Descripción:", txtDesc,
            "Repeticiones (int):", txtReps,
            "Peso (double):", txtPeso,
            "Grupo Muscular:", txtGrupoM
        };

        int opcion = JOptionPane.showConfirmDialog(this, campos, "Crear ejercicio", JOptionPane.OK_CANCEL_OPTION);
        if (opcion != JOptionPane.OK_OPTION) {
            return;
        }

        //int id = getId();
        String nombre = txtNombre.getText().trim();
        String desc = txtDesc.getText().trim();
        String grupoM = txtGrupoM.getText().trim();
        int reps = 0;
        double peso = 0.0;

        try {
            reps = Integer.parseInt(txtReps.getText().trim());
        } catch (Exception ex) {
            reps = 0;
        }
        try {
            peso = Double.parseDouble(txtPeso.getText().trim());
        } catch (Exception ex) {
            peso = 0.0;
        }

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Ejercicio nuevo = new Ejercicio(nombre, desc, reps, peso, grupoM);
        entrenador.agregarEjercicio(nuevo);

        // persistir si tienes servicio/DAO
        try {
            entrenadorService.agregarEjercicio(nuevo);
        } catch (Exception ex) {
            // ignorar si no existe persistencia
        }

        cargarEjercicios();
        JOptionPane.showMessageDialog(this, "Ejercicio creado correctamente.");
    }

    private void crearRutinaDialog() {
        JTextField txtNombre = new JTextField();
        JTextArea txtDesc = new JTextArea(4, 20);
        JScrollPane scrollDesc = new JScrollPane(txtDesc);

        Object[] campos = {
            "Nombre:", txtNombre,
            "Descripción:", scrollDesc,
            "Seleccione ejercicios de la lista:"
        };

        int opcion = JOptionPane.showConfirmDialog(this, campos, "Crear rutina", JOptionPane.OK_CANCEL_OPTION);
        if (opcion != JOptionPane.OK_OPTION) {
            return;
        }

        String nombre = txtNombre.getText().trim();
        String desc = txtDesc.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Ejercicio> seleccionados = listEjercicios.getSelectedValuesList();
        Rutina nueva = new Rutina(nombre, desc, false, LocalDate.now().toString());

        for (Ejercicio e : seleccionados) {
            nueva.agregarEjercicio(e);
        }

        // Guardar rutina en la base de datos y obtener ID generado
        int idGenerado = rutinaService.guardarRutina(nueva);

        if (idGenerado <= 0) {
            JOptionPane.showMessageDialog(this, "Error al guardar la rutina en la BD", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //persistir via servicio si existe
        try {
            entrenadorService.crearRutina(nueva);
        } catch (Exception ex) {
            try {
                rutinaService.guardarRutina(nueva);
            } catch (Exception ex2) {
            }
        }

        // para guardarlo en memoria
        sistema.getRutinas().add(nueva);

        // guarda en historial entrenador
        entrenador.getHistorialR().add(nueva);

        // refresca la interfaz
        cargarRutinas();
        JOptionPane.showMessageDialog(this, "Rutina creada correctamente.");
    }

    private void eliminarRutina() {
        Rutina sel = listRutinas.getSelectedValue();
        if (sel == null) {
            JOptionPane.showMessageDialog(this, "Seleccioná una rutina para eliminar.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int ok = JOptionPane.showConfirmDialog(this, "Eliminar rutina \"" + sel.getNombre() + "\"?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (ok != JOptionPane.YES_OPTION) {
            return;
        }

        entrenador.eliminarRutina(sel);
        try {
            rutinaService.eliminarRutina(sel.getId());
        } catch (Exception ex) {
        }
        try {
            sistema.getRutinas().remove(sel);
        } catch (Exception ignored) {
        }
        cargarRutinas();
        JOptionPane.showMessageDialog(this, "Rutina eliminada.");
    }

    private void asignarRutinaACliente() {
        Rutina selRut = listRutinas.getSelectedValue();
        Cliente selCli = listClientes.getSelectedValue();

        if (selRut == null || selCli == null) {
            JOptionPane.showMessageDialog(this, "Seleccioná cliente y rutina.");
            return;
        }

        boolean ok = sistema.getRutinaclienteService().asignarRutina(selCli, selRut);

        if (ok) {
            JOptionPane.showMessageDialog(this, "Rutina asignada con éxito.");
        } else {
            JOptionPane.showMessageDialog(this, "Error al asignar rutina.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // NEW: elegir cliente desde la BD (solo los disponibles: Id_Entrenador IS NULL)
    private void agregarClienteAlEntrenador() {
        // 1) obtener clientes desde BD
        List<Cliente> clientesBD = sistema.getClienteBD().listarClientes(); // retorna todos

        // 2) filtrar NO asignados
        List<Cliente> disponibles = new ArrayList<>();
        for (Cliente c : clientesBD) {
            if (c.getIdEntrenador() == 0) { // en tu modelo 0 significa sin entrenador; si usas null, ajustar
                disponibles.add(c);
            }
        }

        if (disponibles.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay clientes disponibles para asignar.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Cliente elegido = (Cliente) JOptionPane.showInputDialog(
                this,
                "Seleccioná cliente a asignar:",
                "Asignar cliente",
                JOptionPane.PLAIN_MESSAGE,
                null,
                disponibles.toArray(new Cliente[0]),
                disponibles.get(0)
        );

        if (elegido == null) {
            return;
        }

        // 4) Asignar en memoria al entrenador
        boolean ok = entrenadorService.agregarCliente(entrenador, elegido);

        // 5) Persistir asignación en BD (usar método corregido)
        boolean exitoBD = sistema.getClienteBD().asignarEntrenador(elegido.getDNI(), entrenador.getDNI());
        if (!exitoBD) {
            JOptionPane.showMessageDialog(this, "Error al guardar asignación en BD.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // actualizar objeto elegido
        elegido.setIdEntrenador(entrenador.getDNI());

        // si el entrenador no tenía ese cliente en memoria, agregarlo
        if (!entrenador.getCliente().contains(elegido)) {
            entrenador.getCliente().add(elegido);
        }

        cargarClientes();
        JOptionPane.showMessageDialog(this, "Cliente asignado correctamente al entrenador.");
    }

    private void mostrarHistorialCliente() {
        Cliente sel = listClientes.getSelectedValue();
        if (sel == null) {
            JOptionPane.showMessageDialog(this, "Seleccioná un cliente.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Cargar historial real desde BD
        sel.getHistorialR().clear();
        sel.getHistorialR().addAll(
                sistema.getRutinaclienteService().getRutinasCliente(sel.getDNI())
        );

        // Crear ventana
        JDialog dialogo = new JDialog(this, "Historial de " + sel.getNombre(), true);
        dialogo.setSize(400, 400);
        dialogo.setLocationRelativeTo(this);

        DefaultListModel<Rutina> modelHist = new DefaultListModel<>();
        JList<Rutina> listaHist = new JList<>(modelHist);

        for (Rutina r : sel.getHistorialR()) {
            modelHist.addElement(r);
        }

        dialogo.add(new JScrollPane(listaHist));

        // Doble click → ver ejercicios
        listaHist.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Rutina r = listaHist.getSelectedValue();
                    if (r != null) {
                        mostrarEjerciciosHistorial(r);
                    }
                }
            }
        });

        dialogo.setVisible(true);
    }

    private void mostrarEjerciciosHistorial(Rutina r) {

        // Obtener ejercicios desde BD
        List<Ejercicio> ejercicios
                = sistema.getRutinaEjercicioBD().obtenerEjerciciosDeRutina(r.getId());

        JDialog dialogo = new JDialog(this, "Ejercicios de " + r.getNombre(), true);
        dialogo.setSize(350, 300);
        dialogo.setLocationRelativeTo(this);

        DefaultListModel<String> modelo = new DefaultListModel<>();
        for (Ejercicio e : ejercicios) {
            modelo.addElement(e.getNombre());
        }

        JList<String> lista = new JList<>(modelo);
        dialogo.add(new JScrollPane(lista));

        dialogo.setVisible(true);
    }

    @Override
    public String toString() {
        return "VistaEntrenador for " + entrenador.getNombre();
    }
}*/
package vista;

import modelo.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VistaEntrenador extends JFrame {

    private final Entrenador entrenador;
    private final Sistema sistema;

    private DefaultListModel<Cliente> modelClientes = new DefaultListModel<>();
    private DefaultListModel<Rutina> modelRutinas = new DefaultListModel<>();
    private DefaultListModel<Ejercicio> modelEjercicios = new DefaultListModel<>();

    private JList<Cliente> listClientes;
    private JList<Rutina> listRutinas;
    private JList<Ejercicio> listEjercicios;

    private Rutina rutinaActual;

    public VistaEntrenador(Entrenador entrenador, Sistema sistema) {
        this.entrenador = entrenador;
        this.sistema = sistema;

        setTitle("Panel Entrenador - " + entrenador.getNombre());
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();
        cargarDatos();
    }

    private void initComponents() {
        JPanel main = new JPanel(new BorderLayout(10, 10));
        getContentPane().add(main);

        // Título
        JLabel lblTitulo = new JLabel("Entrenador: " + entrenador.getNombre() + " " + entrenador.getApellido(),
                SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        main.add(lblTitulo, BorderLayout.NORTH);

        // Panel central con 3 columnas
        JPanel center = new JPanel(new GridLayout(1, 3, 10, 10));
        main.add(center, BorderLayout.CENTER);

        // ====== Clientes ======
        JPanel pnlClientes = new JPanel(new BorderLayout(5, 5));
        pnlClientes.setBorder(BorderFactory.createTitledBorder("Clientes a cargo"));
        listClientes = new JList<>(modelClientes);
        listClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        pnlClientes.add(new JScrollPane(listClientes), BorderLayout.CENTER);

        JPanel pnlClientesBtns = new JPanel(new GridLayout(3, 1, 5, 5));
        JButton btnCrearCliente = new JButton("Crear cliente nuevo");
        JButton btnVerHistorial = new JButton("Ver historial");
        JButton btnRefrescarClientes = new JButton("Refrescar lista");
        pnlClientesBtns.add(btnCrearCliente);
        pnlClientesBtns.add(btnVerHistorial);
        pnlClientesBtns.add(btnRefrescarClientes);
        pnlClientes.add(pnlClientesBtns, BorderLayout.SOUTH);
        center.add(pnlClientes);

        // ====== Rutinas ======
        JPanel pnlRutinas = new JPanel(new BorderLayout(5, 5));
        pnlRutinas.setBorder(BorderFactory.createTitledBorder("Rutinas creadas"));
        listRutinas = new JList<>(modelRutinas);
        listRutinas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        pnlRutinas.add(new JScrollPane(listRutinas), BorderLayout.CENTER);

        JPanel pnlRutinasBtns = new JPanel(new GridLayout(4, 1, 5, 5));
        JButton btnAgregarEjercicio = new JButton("Agregar ejercicios");
        JButton btnCrearRutina = new JButton("Crear rutina");
        JButton btnAsignarRutina = new JButton("Asignar a cliente");
        JButton btnEliminarRutina = new JButton("Eliminar rutina");
        pnlRutinasBtns.add(btnAgregarEjercicio);
        pnlRutinasBtns.add(btnCrearRutina);
        pnlRutinasBtns.add(btnAsignarRutina);
        pnlRutinasBtns.add(btnEliminarRutina);
        pnlRutinas.add(pnlRutinasBtns, BorderLayout.SOUTH);
        center.add(pnlRutinas);

        // ====== Ejercicios ======
        JPanel pnlEjercicios = new JPanel(new BorderLayout(5, 5));
        pnlEjercicios.setBorder(BorderFactory.createTitledBorder("Ejercicios disponibles"));
        listEjercicios = new JList<>(modelEjercicios);
        listEjercicios.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        pnlEjercicios.add(new JScrollPane(listEjercicios), BorderLayout.CENTER);

        JPanel pnlEjBtns = new JPanel(new GridLayout(2, 1, 5, 5));
        JButton btnCrearEjercicio = new JButton("Crear ejercicio");
        JButton btnRefrescarEj = new JButton("Refrescar ejercicios");
        pnlEjBtns.add(btnCrearEjercicio);
        pnlEjBtns.add(btnRefrescarEj);
        pnlEjercicios.add(pnlEjBtns, BorderLayout.SOUTH);
        center.add(pnlEjercicios);

        // ====== Bottom ======
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnCerrar = new JButton("Cerrar");
        bottom.add(btnCerrar);
        main.add(bottom, BorderLayout.SOUTH);

        // ---------- Listeners ----------
        btnCerrar.addActionListener(e -> dispose());

        btnRefrescarClientes.addActionListener(e -> cargarClientes());
        btnRefrescarEj.addActionListener(e -> cargarEjercicios());

        btnCrearEjercicio.addActionListener(e -> crearEjercicioDialog());
        btnCrearRutina.addActionListener(e -> crearRutinaDialog());
        btnEliminarRutina.addActionListener(e -> eliminarRutina());
        btnAsignarRutina.addActionListener(e -> asignarRutinaACliente());

        btnCrearCliente.addActionListener(e -> {
            VistaCrearCliente v = new VistaCrearCliente(sistema, entrenador);
            v.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    cargarClientes(); // refrescar al cerrar
                }
            });
        });

        btnVerHistorial.addActionListener(e -> mostrarHistorialCliente());

        listRutinas.addListSelectionListener(e -> {
            if (!listRutinas.getValueIsAdjusting()) {
                rutinaActual = listRutinas.getSelectedValue();
                mostrarEjerciciosDeRutina(rutinaActual);
            }
        });

        listClientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    mostrarHistorialCliente();
                }
            }
        });

        btnAgregarEjercicio.addActionListener(e -> {
            if (rutinaActual == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar una rutina primero.");
                return;
            }
            VistaSeleccionEjercicio v = new VistaSeleccionEjercicio(this, sistema, rutinaActual);
            v.setVisible(true);
            cargarRutinas();
        });
    }

    private void cargarDatos() {
        cargarClientes();
        cargarRutinas();
        cargarEjercicios();
    }

    private void cargarClientes() {
        modelClientes.clear();
        ArrayList<Cliente> clientesBD = sistema.getClienteBD().listarClientesPorEntrenador(entrenador.getDNI());
        entrenador.getCliente().clear();
        entrenador.getCliente().addAll(clientesBD);
        clientesBD.forEach(modelClientes::addElement);
    }

    private void cargarRutinas() {
        modelRutinas.clear();
        entrenador.getHistorialR().forEach(modelRutinas::addElement);
    }

    private void cargarEjercicios() {
        modelEjercicios.clear();
        if (sistema.getEjercicios() != null) {
            sistema.getEjercicios().forEach(e -> modelEjercicios.addElement(e));
        }
        for (Ejercicio e : entrenador.getEjercicio()) {
            if (!modelEjercicios.contains(e)) {
                modelEjercicios.addElement(e);
            }
        }
    }

    private void mostrarEjerciciosDeRutina(Rutina r) {
        modelEjercicios.clear();
        if (r == null) {
            return;
        }
        List<Ejercicio> ejercicios = sistema.getRutinaEjercicioBD().obtenerEjerciciosDeRutina(r.getId());
        ejercicios.forEach(modelEjercicios::addElement);
    }

    // ================= ACCIONES =================
    private void crearEjercicioDialog() {
        JTextField txtNombre = new JTextField();
        JTextField txtDesc = new JTextField();
        JTextField txtReps = new JTextField();
        JTextField txtPeso = new JTextField();
        JTextField txtGrupoM = new JTextField();

        Object[] campos = {
            "Nombre:", txtNombre,
            "Descripción:", txtDesc,
            "Repeticiones (int):", txtReps,
            "Peso (double):", txtPeso,
            "Grupo Muscular:", txtGrupoM
        };

        int opcion = JOptionPane.showConfirmDialog(this, campos, "Crear ejercicio", JOptionPane.OK_CANCEL_OPTION);
        if (opcion != JOptionPane.OK_OPTION) {
            return;
        }

        String nombre = txtNombre.getText().trim();
        String desc = txtDesc.getText().trim();
        String grupoM = txtGrupoM.getText().trim();
        int reps = parseIntSafe(txtReps.getText().trim());
        double peso = parseDoubleSafe(txtPeso.getText().trim());

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Ejercicio nuevo = new Ejercicio(nombre, desc, reps, peso, grupoM);
        entrenador.agregarEjercicio(nuevo);
        sistema.getEntrenadorService().agregarEjercicio(nuevo);

        cargarEjercicios();
        JOptionPane.showMessageDialog(this, "Ejercicio creado correctamente.");
    }

    private int parseIntSafe(String txt) {
        try {
            return Integer.parseInt(txt);
        } catch (Exception e) {
            return 0;
        }
    }

    private double parseDoubleSafe(String txt) {
        try {
            return Double.parseDouble(txt);
        } catch (Exception e) {
            return 0;
        }
    }

    private void crearRutinaDialog() {
        JTextField txtNombre = new JTextField();
        JTextArea txtDesc = new JTextArea(4, 20);
        JScrollPane scrollDesc = new JScrollPane(txtDesc);

        Object[] campos = {
            "Nombre:", txtNombre,
            "Descripción:", scrollDesc,
            "Seleccione ejercicios de la lista:"
        };

        int opcion = JOptionPane.showConfirmDialog(this, campos, "Crear rutina", JOptionPane.OK_CANCEL_OPTION);
        if (opcion != JOptionPane.OK_OPTION) {
            return;
        }

        String nombre = txtNombre.getText().trim();
        String desc = txtDesc.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Ejercicio> seleccionados = listEjercicios.getSelectedValuesList();
        Rutina nueva = new Rutina(nombre, desc, false, LocalDate.now().toString());
        seleccionados.forEach(nueva::agregarEjercicio);

        int idGenerado = sistema.getRutinaService().guardarRutina(nueva);
        if (idGenerado <= 0) {
            JOptionPane.showMessageDialog(this, "Error al guardar la rutina en BD", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        sistema.getRutinas().add(nueva);
        entrenador.getHistorialR().add(nueva);
        cargarRutinas();
        JOptionPane.showMessageDialog(this, "Rutina creada correctamente.");
    }

    private void eliminarRutina() {
        Rutina sel = listRutinas.getSelectedValue();
        if (sel == null) {
            JOptionPane.showMessageDialog(this, "Seleccioná una rutina para eliminar.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int ok = JOptionPane.showConfirmDialog(this, "Eliminar rutina \"" + sel.getNombre() + "\"?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (ok != JOptionPane.YES_OPTION) {
            return;
        }

        entrenador.eliminarRutina(sel);
        sistema.getRutinaService().eliminarRutina(sel.getId());
        sistema.getRutinas().remove(sel);
        cargarRutinas();
        JOptionPane.showMessageDialog(this, "Rutina eliminada.");
    }

    private void asignarRutinaACliente() {
        Rutina selRut = listRutinas.getSelectedValue();
        Cliente selCli = listClientes.getSelectedValue();
        if (selRut == null || selCli == null) {
            JOptionPane.showMessageDialog(this, "Seleccioná cliente y rutina.");
            return;
        }

        boolean ok = sistema.getRutinaclienteService().asignarRutina(selCli, selRut);
        JOptionPane.showMessageDialog(this, ok ? "Rutina asignada con éxito."
                : "Error al asignar rutina.", "Error", ok ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarHistorialCliente() {
        Cliente sel = listClientes.getSelectedValue();
        if (sel == null) {
            JOptionPane.showMessageDialog(this, "Seleccioná un cliente.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        sel.getHistorialR().clear();
        sel.getHistorialR().addAll(sistema.getRutinaclienteService().getRutinasCliente(sel.getDNI()));

        JDialog dialogo = new JDialog(this, "Historial de " + sel.getNombre(), true);
        dialogo.setSize(400, 400);
        dialogo.setLocationRelativeTo(this);

        DefaultListModel<Rutina> modelHist = new DefaultListModel<>();
        JList<Rutina> listaHist = new JList<>(modelHist);
        sel.getHistorialR().forEach(modelHist::addElement);
        dialogo.add(new JScrollPane(listaHist));

        listaHist.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Rutina r = listaHist.getSelectedValue();
                    if (r != null) {
                        mostrarEjerciciosHistorial(r);
                    }
                }
            }
        });

        dialogo.setVisible(true);
    }

    private void mostrarEjerciciosHistorial(Rutina r) {
        List<Ejercicio> ejercicios = sistema.getRutinaEjercicioBD().obtenerEjerciciosDeRutina(r.getId());
        JDialog dialogo = new JDialog(this, "Ejercicios de " + r.getNombre(), true);
        dialogo.setSize(350, 300);
        dialogo.setLocationRelativeTo(this);

        DefaultListModel<String> modelo = new DefaultListModel<>();
        ejercicios.forEach(e -> modelo.addElement(e.getNombre()));
        dialogo.add(new JScrollPane(new JList<>(modelo)));
        dialogo.setVisible(true);
    }
}
