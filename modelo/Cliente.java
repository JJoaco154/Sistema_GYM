package modelo;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Usuario {

    private int clases;
    private ArrayList<Rutina> historialR = new ArrayList<>();
    private Rutina RutinaActual;
    private int id_entrenador; 
    private List<Rutina> rutinas = new ArrayList<>();

    public Cliente(String nombre, String apellido, int dni, String contraseña, int clases, int id_entrenador) {
        super(nombre, apellido, dni, contraseña);
        this.clases = clases;
        this.id_entrenador = id_entrenador;
    }

    /*public Cliente(String nombre, String apellido, int dni, String contraseña) {
        super(nombre, apellido, dni, contraseña);
        this.clases = 0;
        this.id_entrenador = null;
    }*/

    public int getClases() {
        return clases;
    }

    public void setClases(int clases) {
        this.clases = clases;
    }

    public List<Rutina> getRutinas() {
        return rutinas;
    }

    public void setRutinas(List<Rutina> rutinas) {
        this.rutinas = rutinas;
    }

    
    public ArrayList<Rutina> getHistorialR() {
        return historialR;
    }

    public void agregarRutina(Rutina r) {
        if (r == null) {
            System.out.println("No se puede agregar una rutina nula.");
            return;
        }
        if (!historialR.contains(r)) {
            historialR.add(r);
        }

        this.RutinaActual = r;
        System.out.println("Rutina actual: " + r.getNombre());
    }

    public Rutina getRutinaActual() {
        return RutinaActual;
    }

    public void setRutinaActual(Rutina RutinaActual) {
        this.RutinaActual = RutinaActual;
    }

    public int getIdEntrenador() {
        return id_entrenador;
    }

    public void setIdEntrenador(int Id_Entrenador) {
        this.id_entrenador = Id_Entrenador;
    }

    public void desconectarClase(Rutina r, Cliente c) {
        if (r.getEjercicioPendiente() == null) {
            c.clases = -1;
        }
    }
}
