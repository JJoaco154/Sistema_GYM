package modelo;

import java.util.List;
import persistencia.*;

public class ClienteService {

    private Sistema sistema;

    public ClienteService(Sistema sistema) {
        this.sistema = sistema;
    }

    // ==========================================================
    // REGISTRAR CLIENTE
    // ==========================================================
    public boolean registrarCliente(Cliente cliente) {
        return sistema.registrarCliente(cliente); // Usa el método del sistema
    }

    // CARGAR EJERCICIOS
    public void cargarEjerciciosRutina(Rutina rutina) {
        if (rutina == null) {
            return;
        }
        List<Ejercicio> ejercicios = new RutinaEjercicioBdD().obtenerEjerciciosDeRutina(rutina.getId());
        rutina.setLista(ejercicios);
    }

    // ==========================================================
    // INICIAR RUTINA
    // ==========================================================
    public boolean iniciarRutina(Cliente cliente, Rutina rutina) {
        if (cliente == null || rutina == null) {
            return false;
        }

        if (cliente.getClases() == 0) {
            System.out.println("No puedes iniciar la rutina, no tienes clases disponibles.");
            return false;
        }

        // Primera rutina (no descuenta si tiene 30 clases)
        boolean esPrimeraRutina = cliente.getHistorialR().isEmpty();

        cliente.setRutinaActual(rutina);

        // Marcamos si debe descontar o no al finalizar
        if (esPrimeraRutina && cliente.getClases() == 30) {
            rutina.setEstado(false); // lo uso como bandera interna
            System.out.println("✔ Primera rutina con 30 clases → no se descontará.");
        } else {
            rutina.setEstado(true); // TRUE = descontar
        }

        return true;
    }

    // ==========================================================
    // MARCAR EJERCICIO COMPLETADO
    // ==========================================================
    public boolean marcarEjercicioCompletado(Cliente cliente) {
        if (cliente.getRutinaActual() == null) {
            return false;
        }

        Ejercicio pendiente = cliente.getRutinaActual().getEjercicioPendiente();
        if (pendiente == null) {
            return false;
        }

        return cliente.getRutinaActual().marcarRealizado(pendiente);
    }

    // ==========================================================
    // FINALIZAR RUTINA
    // ==========================================================
    public boolean finalizarRutina(Cliente cliente) {
        Rutina rutina = cliente.getRutinaActual();

        if (rutina == null || rutina.tienePendientes()) {
            return false;
        }

        // ---- 🔥 DESCONTAR CLASES ----
        if (rutina.getEstado() == true) {  // TRUE = descontar clases
            int nuevas = cliente.getClases() - 1;
            cliente.setClases(nuevas);

            // Guardar en BD:
            new ClienteBdD().actualizarClases(cliente.getDNI(), nuevas);

            System.out.println("Se descontó 1 clase. Quedan: " + nuevas);
        } else {
            System.out.println("Primera rutina → NO se descuenta clase.");
        }

        // ---- Guardar historial ----
        cliente.getHistorialR().add(rutina);
        cliente.setRutinaActual(null);

        new persistencia.HistorialRutinaBdD().guardarHistorial(rutina, cliente);

        return true;
    }

    public boolean asignarEntrenadorACliente(int dniCliente, int dniEntrenador) {

        // 1) comprobar existencia en BD
        Cliente c = new ClienteBdD().buscarPorDNI(dniCliente); // clienteBase = ClienteBdD
        if (c == null) {
            System.out.println("Cliente no encontrado (dni=" + dniCliente + ")");
            return false;
        }

        Entrenador ent = new EntrenadorBdD().buscarPorDNI(dniEntrenador); // o usar entrenadorBD
        if (ent == null) {
            System.out.println("Entrenador no encontrado (dni=" + dniEntrenador + ")");
            return false;
        }

        boolean ok = new ClienteBdD().asignarEntrenador(dniCliente, dniEntrenador);

        // 3) actualizar objeto en memoria (si existe en el sistema)
        // si tenés un Sistema con lista en memoria:
        Cliente mem = sistema.buscarClientePorDNI(dniCliente); // si tu service tiene acceso al sistema
        if (mem != null) {
            mem.setIdEntrenador(dniEntrenador); // actualizar memoria
        } else {
            // si no usás el sistema, actualizar el objeto c obtenido anteriormente
            c.setIdEntrenador(dniEntrenador);
        }

        return true;
    }

    // Acreditar pago y que se le agregarn 30 clases nuevas
    public boolean acreditarPago(Cliente cliente) {
        int nuevasClases = cliente.getClases() + 30;
        cliente.setClases(nuevasClases);
        return sistema.getClienteBD().actualizarClases(cliente.getDNI(), nuevasClases);
    }

}
