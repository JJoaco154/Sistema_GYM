package modelo;

import java.util.ArrayList;

public class EntrenadorService {

    private Sistema sistema;

    public EntrenadorService(Sistema sistema) {
        this.sistema = sistema;
    }

    // ==========================================================
    // REGISTRAR ENTRENADOR
    // ==========================================================
    public boolean registrarEntrenador(Entrenador e) {
        return sistema.registrarEntrenador(e);
    }

    // ==========================================================
    // AGREGAR CLIENTE A ENTRENADOR
    // ==========================================================
    public boolean agregarCliente(Entrenador entren, Cliente c) {
        // 1) Agregar en memoria
        if (!entren.getCliente().contains(c)) {
            entren.getCliente().add(c);
        } else {
            return false; // ya estaba
        }

        // 2) Guardar en BD
        boolean ok = sistema.getClienteBD().asignarEntrenador(c.getDNI(), entren.getDNI());

        return ok;
    }

    // ASIGNAR ENTRENADOR A CLIENTE
    public boolean asignarClienteAEntrenador(int dniCliente, int dniEntrenador) {
        Cliente cliente = sistema.getClienteBD().buscarPorDNI(dniCliente);

        if (cliente == null) {
            return false;
        }

        // Actualizar en memoria
        cliente.setIdEntrenador(dniEntrenador);

        // Actualizar en BD
        sistema.getClienteBD().asignarEntrenador(dniCliente, dniEntrenador);

        return true;
    }

    // ==========================================================
    // CREAR RUTINA
    // ==========================================================
    public boolean crearRutina(Rutina r) {
        if (r == null) {
            return false;
        }

        // Validaciones mínimas
        if (r.getNombre() == null || r.getNombre().isEmpty()) {
            return false;
        }
        if (r.getDescripcion() == null || r.getDescripcion().isEmpty()) {
            return false;
        }

        // 1) Guardar en la BD
        int idGenerado = sistema.getRutinaBD().guardarRutina(r);

        if (idGenerado <= 0) {
            System.out.println("Error al guardar rutina en la BD");
            return false;
        }

        r.setId(idGenerado);
        // 2) Guardar en memoria
        sistema.getRutinas().add(r);

        return true;
    }

    // ==========================================================
    // AGREGAR EJERCICIO
    // ==========================================================
    public Ejercicio agregarEjercicio(Ejercicio e) {

        if (e == null) {
            System.out.println("ERROR: Ejercicio nulo");
            return null;
        }

        // Validaciones mínimas
        if (e.getNombre() == null || e.getNombre().isEmpty()) {
            System.out.println("ERROR: El ejercicio debe tener nombre");
            return null;
        }

        if (e.getDescripcion() == null || e.getDescripcion().isEmpty()) {
            System.out.println("ERROR: El ejercicio debe tener una descripción");
            return null;
        }

        // 1) Guardar en la BD
        boolean guardado = sistema.getEjercicioBD().guardarEjercicio(e);
        if (!guardado) {
            System.out.println("ERROR: No se pudo guardar el ejercicio en la BD");
            return null;
        }

        // 2) Guardar en memoria
        sistema.getEjercicios().add(e);

        return e;
    }

    public boolean agregarEjercicioARutina(Rutina rutina, Ejercicio ej) {

        if (rutina == null || ej == null) {
            return false;
        }

        rutina.agregarEjercicio(ej);

        persistencia.RutinaEjercicioBdD dao
                = new persistencia.RutinaEjercicioBdD();

        return dao.asignarEjercicio(
                rutina.getId(),
                ej.getId()
        );
    }

    // ==========================================================
    // ASIGNAR RUTINA A CLIENTE
    // ==========================================================
    public boolean asignarRutina(Cliente c, Rutina r) {
        if (c == null || r == null) {
            return false;
        }

        c.setRutinaActual(r);
        c.getHistorialR().add(r);

        sistema.getRutClienteBD().asignarRutina(c.getDNI(), r.getId());
        return true;
    }

    // ==========================================================
    // HISTORIAL
    // ==========================================================
    public ArrayList<Rutina> verHistorial(Cliente c) {
        return c.getHistorialR();
    }
}
