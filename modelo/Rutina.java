package modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Rutina implements Realizable {

    private String nombre;
    private String descripcion;
    private List<Ejercicio> lista = new ArrayList<>();
    private boolean estado; // true = terminada, false = no terminada
    private String fecha;
    private int Id;

    // Lista para guardar el historial de rutinas
    private static List<HistorialRutina> historialRutinas = new ArrayList<>();

    // Constructor
    public Rutina(String nombre, String descripcion, boolean estado, String fecha) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fecha = fecha;
        this.lista = new ArrayList<>();
    }

    // Métodos para obtener y actualizar los atributos
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Ejercicio> getLista() {
        return lista;
    }

    public void setLista(List<Ejercicio> lista) {
        this.lista = lista;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    // Agregar ejercicio
    public void agregarEjercicio(Ejercicio e) {
        lista.add(e);
    }

    // Marcar ejercicio realizado y activar el siguiente como pendiente
    public boolean marcarRealizado(Ejercicio e) {
        e.marcarRealizado(); // ✅ usando interfaz
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).equals(e)) {
                if (i + 1 < lista.size()) {
                    Ejercicio siguiente = lista.get(i + 1);
                    siguiente.marcarPendiente(); // lo dejo pendiente
                }
                break;
            }
        }
        return true;
    }

    // Obtener el primer ejercicio pendiente
    public Ejercicio getEjercicioPendiente() {
        for (Ejercicio e : lista) {
            if (!e.isRealizado()) {
                return e; // el primero no realizado
            }
        }
        return null; // todos hechos
    }

    // Ver si tiene ejercicios pendientes
    public boolean tienePendientes() {
        for (Ejercicio e : lista) {
            if (!e.isRealizado()) {
                return true;   // Todavía hay ejercicios sin terminar
            }
        }
        return false;  // Todos completos
    }

    // Cambiar estado de la rutina (por empezar, en proceso, terminada)
    public boolean completarRutina(int dato) {
        switch (dato) {
            case 1:
                estado = false; // por empezar
                break;
            case 2:
                estado = false; // en proceso
                break;
            case 3:
                estado = true;  // terminada
                // Añadir la rutina al historial
                guardarEnHistorial();
                break;
            default:
                System.out.println("dato incorrecto");
                break;
        }
        return estado;
    }

    // Método para guardar la rutina completada en el historial
    public void guardarEnHistorial() {
        if (this.estado) {
            HistorialRutina historial = new HistorialRutina(this);
            historialRutinas.add(historial);
            System.out.println("Rutina guardada en el historial.");
        } else {
            System.out.println("La rutina no está terminada.");
        }
    }

    // Métodos de la interfaz Realizable aplicados a la rutina completa
    @Override
    public void marcarRealizado() {
        this.estado = true; // rutina completa
        guardarEnHistorial();  // Guardar en el historial al marcar como realizada
    }

    @Override
    public void marcarPendiente() {
        this.estado = false; // rutina no terminada
    }

    @Override
    public boolean isRealizado() {
        return estado;
    }

    // Métodos estáticos para ver el historial
    public static void mostrarHistorial() {
        if (historialRutinas.isEmpty()) {
            System.out.println("No hay rutinas completadas.");
        } else {
            for (HistorialRutina hr : historialRutinas) {
                System.out.println(hr.toString());
            }
        }
    }

    @Override
    public String toString() {
        return nombre;
    }
}
