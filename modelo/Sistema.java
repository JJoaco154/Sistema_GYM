package modelo;

import java.util.ArrayList;

import persistencia.ClienteBdD;
import persistencia.EntrenadorBdD;
import persistencia.EjercicioBdD;
import persistencia.RutinaBdD;
import persistencia.RutinaClienteBdD;
import persistencia.RutinaEjercicioBdD;
import util.HashUtil;

public class Sistema {

    private ArrayList<Cliente> clientes = new ArrayList<>();
    private ArrayList<Entrenador> entrenadores = new ArrayList<>();
    private ArrayList<Ejercicio> ejercicios = new ArrayList<>();
    private ArrayList<Rutina> rutinas = new ArrayList<>();

    private ClienteService clienteService;
    private EntrenadorService entrenadorService;
    private RutinaService rutinaService;
    private ClienteRutinaService rutinaclienteService;

    private ClienteBdD clienteBD = new ClienteBdD();
    private EntrenadorBdD entrenadorBD = new EntrenadorBdD();
    private EjercicioBdD ejercicioBD = new EjercicioBdD();
    private RutinaBdD rutinaBD = new RutinaBdD();
    private RutinaClienteBdD RutClienteBD = new RutinaClienteBdD();
    private RutinaEjercicioBdD rutinaEjercicioBD = new RutinaEjercicioBdD();

    // --- ADMIN FIJO ---
    private Administrador adminPrincipal
            = new Administrador("Admin", "Mayot", 00000, "Hola1234");

    public Sistema() {
        // Inicializar BD
        clienteBD = new ClienteBdD();
        entrenadorBD = new EntrenadorBdD();
        ejercicioBD = new EjercicioBdD();
        rutinaBD = new RutinaBdD();

        // Inicializar Services
        clienteService = new ClienteService(this);
        entrenadorService = new EntrenadorService(this);
        rutinaService = new RutinaService(rutinaBD);
        rutinaclienteService = new ClienteRutinaService(this);

        cargarDatosDesdeBD();
    }

    // ==========================================================
    //               CARGA DE DATOS
    // ==========================================================
    private void cargarDatosDesdeBD() {
        System.out.println("Cargando datos desde la base de datos...");

        clientes = clienteBD.listarClientes();
        entrenadores = entrenadorBD.listarEntrenadores();
        ejercicios = ejercicioBD.listarEjercicios();
        rutinas = rutinaBD.listarRutina();

        System.out.println("Datos cargados correctamente.");
    }

    // ==========================================================
    //               MÉTODOS GENERALES
    // ==========================================================
    public Administrador getAdmin() {
        return adminPrincipal;
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public ArrayList<Entrenador> getEntrenadores() {
        return entrenadores;
    }

    public ArrayList<Ejercicio> getEjercicios() {
        return ejercicios;
    }

    public void setEjercicios(ArrayList<Ejercicio> ejercicios) {
        this.ejercicios = ejercicios;
    }

    public ArrayList<Rutina> getRutinas() {
        return rutinas;
    }

    public void setRutinas(ArrayList<Rutina> rutinas) {
        this.rutinas = rutinas;
    }

    public ClienteBdD getClienteBD() {
        return clienteBD;
    }

    public EntrenadorBdD getEntrenadorBD() {
        return entrenadorBD;
    }

    public EjercicioBdD getEjercicioBD() {
        return ejercicioBD;
    }

    public RutinaBdD getRutinaBD() {
        return rutinaBD;
    }

    public ClienteService getClienteService() {
        return clienteService;
    }

    public void setClienteService(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    public EntrenadorService getEntrenadorService() {
        return entrenadorService;
    }

    public void setEntrenadorService(EntrenadorService entrenadorService) {
        this.entrenadorService = entrenadorService;
    }

    public RutinaService getRutinaService() {
        return rutinaService;
    }

    public void setRutinaService(RutinaService rutinaService) {
        this.rutinaService = rutinaService;
    }

    public ClienteRutinaService getRutinaclienteService() {
        return rutinaclienteService;
    }

    public void setRutinaclienteService(ClienteRutinaService rutinaclienteService) {
        this.rutinaclienteService = rutinaclienteService;
    }

    public RutinaClienteBdD getRutClienteBD() {
        return RutClienteBD;
    }

    public void setRutClienteBD(RutinaClienteBdD RutCliente) {
        this.RutClienteBD = RutCliente;
    }

    public RutinaEjercicioBdD getRutinaEjercicioBD() {
        return rutinaEjercicioBD;
    }
    

    // ==========================================================
    //               LOGIN
    // ==========================================================
    public Usuario login(int dni, String contraseña, String tipo) {

        // Hashear la contraseña ingresada
        String hashIngresado = HashUtil.generarHash(contraseña);

        // ADMIN
        if (tipo.equalsIgnoreCase("Administrador")) {
            if (adminPrincipal.getDNI() == dni
                    && adminPrincipal.getContraseña().equals(hashIngresado)) {
                return adminPrincipal;
            }
            return null;
        }

        // CLIENTE
        if (tipo.equalsIgnoreCase("Cliente")) {
            for (Cliente c : clientes) {
                if (c.getDNI() == dni && c.getContraseña().equals(hashIngresado)) {
                    return c;
                }
            }
            return null;
        }

        // ENTRENADOR
        if (tipo.equalsIgnoreCase("Entrenador")) {
            for (Entrenador e : entrenadores) {
                if (e.getDNI() == dni && e.getContraseña().equals(hashIngresado)) {
                    return e;
                }
            }
            return null;
        }

        return null;
    }

    // ==========================================================
    //              REGISTRO - ALTA
    // ==========================================================
    public boolean registrarCliente(Cliente c) {

        for (Cliente existe : clientes) {
            if (existe.getDNI() == c.getDNI()) {
                return false;
            }
        }

        boolean ok = clienteBD.insertarCliente(c);
        if (ok) {
            clientes.add(c);
        }

        return ok;
    }

    public boolean registrarEntrenador(Entrenador e) {

        for (Entrenador existe : entrenadores) {
            if (existe.getDNI() == e.getDNI()) {
                return false;
            }
        }

        boolean ok = entrenadorBD.insertarEntrenador(e);
        if (ok) {
            entrenadores.add(e);
        }

        return ok;
    }

    // BUSQUEDAS:
    public Cliente buscarClientePorDNI(int dni) {
        for (Cliente c : clientes) {
            if (c.getDNI() == dni) {
                return c;
            }
        }
        return null; // No encontrado
    }

    public Entrenador buscarEntrenadorPorDNI(int dni) {
        for (Entrenador e : entrenadores) {
            if (e.getDNI() == dni) {
                return e;
            }
        }
        return null; // No encontrado
    }

    public Rutina buscarRutinaPorId(int id) {
        for (Rutina r : rutinas) {
            if (r.getId() == id) {
                return r;
            }
        }
        return null; // No encontrado
    }

    // ACTUALIZAR CLASES:
    public boolean actualizarClases(int dniCliente, int nuevasClases) {
        Cliente c = buscarClientePorDNI(dniCliente);

        if (c == null) {
            return false; // El cliente no existe
        }

        if (nuevasClases < 0) {
            return false; // No se permiten valores negativos
        }

        // Actualizo en el objeto en memoria
        c.setClases(nuevasClases);

        // Actualizo en la BD
        boolean exito = clienteBD.actualizarClases(dniCliente, nuevasClases);

        return exito;
    }

    public boolean setIdEntrenador(int dniCliente, int dniEntrenador) {
        Cliente c = buscarClientePorDNI(dniCliente);
        Entrenador e = buscarEntrenadorPorDNI(dniEntrenador);

        if (c == null || e == null) {
            return false; // Alguno no existe
        }

        c.setIdEntrenador(dniEntrenador);

        return true;
    }

    // =====================
// ELIMINAR CLIENTE
// =====================
    public boolean eliminarCliente(int dni) {

        // 1) Eliminar de BD
        clienteBD.eliminarCliente(dni);

        // 2) Eliminar de memoria
        boolean eliminado = clientes.removeIf(c -> c.getDNI() == dni);

        return eliminado;
    }

// =====================
// ELIMINAR ENTRENADOR
// =====================
    public boolean eliminarEntrenador(int dni) {

        // 1) Eliminar de BD
        entrenadorBD.eliminarEntrenador(dni);

        // 2) Eliminar de memoria
        boolean eliminado = entrenadores.removeIf(e -> e.getDNI() == dni);

        return eliminado;
    }

// =====================
// ELIMINAR RUTINA
// =====================
    public boolean eliminarRutina(int idRutina) {

        // 1) Eliminar de BD
        rutinaBD.eliminarEjerciciosDeRutina(idRutina);
        rutinaBD.eliminarRutina(idRutina);

        // 2) Eliminar de memoria
        boolean eliminado = rutinas.removeIf(r -> r.getId() == idRutina);

        return eliminado;
    }

}
