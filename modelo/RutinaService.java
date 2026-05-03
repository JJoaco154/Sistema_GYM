package modelo;

import modelo.Rutina;
import modelo.Cliente;
import modelo.Ejercicio;
import persistencia.RutinaBdD;
import persistencia.conexion_BdD;

import java.sql.Connection;
import java.util.ArrayList;

public class RutinaService {

    private RutinaBdD rutinaDAO;

    public RutinaService(RutinaBdD rutinaDAO) {
        if (rutinaDAO == null) {
            throw new IllegalArgumentException("rutinaDAO no puede ser null");
        }
        this.rutinaDAO = rutinaDAO;
    }

    // ===================== CRUD PRINCIPAL ========================
    public int guardarRutina(Rutina rutina) {
        try {
            int id = rutinaDAO.crearRutina(rutina);
            rutina.setId(id);
            return id;
        } catch (Exception e) {
            System.out.println("Error al guardar rutina: " + e.getMessage());
            return -1;
        }
    }

    public ArrayList<Rutina> listarRutinas() {
        return rutinaDAO.listarRutina();
    }

    public Rutina obtenerRutinaPorId(int id) {
        try {
            return rutinaDAO.obtenerRutinaPorId(id);
        } catch (Exception e) {
            System.out.println("No se pudo obtener rutina: " + e.getMessage());
            return null;
        }
    }

    // ===================== RELACIONES ============================
    public void asignarRutinaACliente(Rutina rutina, Cliente cliente) {
        try {
            rutinaDAO.guardarRutina(rutina);

            cliente.agregarRutina(rutina);

            System.out.println("Rutina asignada correctamente a " + cliente.getNombre());

        } catch (Exception e) {
            System.out.println("Error asignando rutina: " + e.getMessage());
        }
    }

    public boolean eliminarRutina(int Id_Rutina) {
        try {
            rutinaDAO.eliminarRutina(Id_Rutina);
        } catch (Exception e) {
            System.out.println("Error eliminando rutina: " + e.getMessage());
            return false;
        }
        return true;
    }

    // ===================== ESTADOS ===============================
    public void marcarRutinaTerminada(Rutina rutina) {
        rutina.marcarRealizado();
        System.out.println("Rutina terminada y guardada en historial.");
    }

    public void actualizarEstado(Rutina rutina, boolean estado) {
        rutina.setEstado(estado);
    }

    // ===================== EJERCICIOS ============================
    public void agregarEjercicio(Rutina rutina, Ejercicio e) {
        rutina.agregarEjercicio(e);
        System.out.println("Ejercicio agregado.");
    }

    // ===================== CARGA PARA SISTEMA =====================
    public ArrayList<Rutina> cargarRutinasDesdeBD() {
        return rutinaDAO.listarRutina();
    }

    // ==================== Aaignar la rutina al cliente
    public boolean asignarClienteARutina(int idRutina, int dniCliente) {
        return rutinaDAO.asignarCliente(idRutina, dniCliente);
    }

}
