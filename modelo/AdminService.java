package modelo;

import java.util.ArrayList;

public class AdminService {

    private Sistema sistema;

    public AdminService(Sistema sistema) {
        this.sistema = sistema;
    }

    // ================================
    //      GETTERS
    // ================================
    public Administrador getAdmin() {
        return sistema.getAdmin();
    }

    public ArrayList<Cliente> getClientes() {
        return sistema.getClientes();
    }

    public ArrayList<Entrenador> getEntrenadores() {
        return sistema.getEntrenadores();
    }

    // ================================
    //     ALTA CLIENTE
    // ================================
    public boolean agregarCliente(Administrador admin, Cliente c) {
        if (!admin.equals(sistema.getAdmin())) {
            System.out.println("No tenés permisos de admin.");
            return false;
        }

        return sistema.registrarCliente(c);
    }

    // ================================
    //     AGREGAR CLASES
    // ================================
    public void agregarClaseCliente(Administrador admin, Cliente c, int clases) {
        if (!admin.equals(sistema.getAdmin())) {
            System.out.println("No tenés permisos de admin.");
            return;
        }

        c.setClases(clases);
    }

    // ================================
    //     ASIGNAR RUTINA
    // ================================
    public void asignarRutinaACliente(Administrador admin, Cliente c, Rutina r) {

        if (!admin.equals(sistema.getAdmin())) {
            System.out.println("No tenés permisos de admin.");
            return;
        }

        c.setRutinaActual(r);
        c.getHistorialR().add(r);

        // persistencia
        sistema.getRutClienteBD().asignarRutina(c.getDNI(), r.getId()); // asigna rutina a cliente

        System.out.println("Rutina asignada correctamente.");
    }

    // ================================
    //     VER CLIENTE
    // ================================
    public void verCliente(Administrador admin, Cliente c) {
        if (!admin.equals(sistema.getAdmin())) {
            System.out.println("No tenés permisos de admin.");
            return;
        }

        System.out.println("Cliente: " + c.getNombre() + " " + c.getApellido());
        System.out.println("DNI: " + c.getDNI());
        System.out.println("Clases: " + c.getClases());

        System.out.println("Historial:");
        for (Rutina r : c.getHistorialR()) {
            System.out.println(" - " + r.getNombre());
        }
    }

    // ================================
    //     ELIMINAR CLIENTE
    // ================================
    public void eliminarCliente(Administrador admin, Cliente c) {
        if (!admin.equals(sistema.getAdmin())) {
            System.out.println("No tenés permisos de admin.");
            return;
        }

        sistema.getClientes().remove(c);
        sistema.getClienteBD().eliminarCliente(c.getDNI());
    }

    // ================================
    //     ENTRENADORES
    // ================================
    public boolean agregarEntrenador(Administrador admin, Entrenador e) {
        if (!admin.equals(sistema.getAdmin())) {
            return false;
        }

        return sistema.registrarEntrenador(e);
    }

    public void eliminarEntrenador(Administrador admin, Entrenador e) {
        if (!admin.equals(sistema.getAdmin())) return;

        sistema.getEntrenadores().remove(e);
        sistema.getEntrenadorBD().eliminarEntrenador(e.getDNI());
    }
}

