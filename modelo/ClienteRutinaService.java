package modelo;

import java.util.ArrayList;
import java.util.List;
import persistencia.ClienteBdD;
import persistencia.RutinaClienteBdD;
import persistencia.RutinaEjercicioBdD;

public class ClienteRutinaService {

    private RutinaClienteBdD dao;
    private Sistema sistema;
    private Ejercicio ejercicio;

    public ClienteRutinaService(Sistema sis) {
        this.sistema = sis;
        this.dao = new RutinaClienteBdD();
    }

    public boolean asignarRutina(Cliente cliente, Rutina rutina) {
        boolean ok = dao.asignarRutina(cliente.getDNI(), rutina.getId());

        if (ok) {
            cliente.getRutinas().add(rutina);
        }

        return ok;
    }

    public List<Rutina> getRutinasCliente(int dni) {
        return dao.obtenerRutinasDeCliente(dni);
    }

    public Rutina getRutinaActual(int dni) {
        List<Rutina> rutinas = dao.obtenerRutinasDeCliente(dni);
        if (rutinas.isEmpty()) {
            return null;
        }
        return rutinas.get(rutinas.size() - 1); // la última asignada
    }

    public void cargarEjerciciosRutina(Rutina rutina) {
        if (rutina == null) {
            return;
        }

        // Traer la lista de ejercicios desde la BD
        List<Ejercicio> ejercicios = new RutinaEjercicioBdD().obtenerEjerciciosDeRutina(rutina.getId());

        rutina.setLista(ejercicios); // asignar a la rutina
    }

}
