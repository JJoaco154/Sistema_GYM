package modelo;

public class Ejercicio implements Realizable {

    private String nombre;
    private String descripcion;
    private int repeticiones;
    private double peso;
    private boolean realizado;
    private String grupoMuscular;
    private int Id;

    public Ejercicio(int id, String nombre, String descripcion, int repeticiones, double peso, String grupoMuscular) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.repeticiones = repeticiones;
        this.peso = peso;
        this.grupoMuscular = grupoMuscular;
        this.realizado = false; // por defecto pendiente
        this.Id = id;
    }

    public Ejercicio(String nombre, String descripcion, int repeticiones,
            double peso, String grupoMuscular) {

        this.nombre = nombre;
        this.descripcion = descripcion;
        this.repeticiones = repeticiones;
        this.peso = peso;
        this.grupoMuscular = grupoMuscular;
        this.realizado = false;
    }

       // Getters y setters
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

    public int getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(int repeticiones) {
        this.repeticiones = repeticiones;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getGrupoMuscular() {
        return grupoMuscular;
    }

    public void setGrupoMuscular(String grupoMuscular) {
        this.grupoMuscular = grupoMuscular;
    }

    public int getId() {
        return Id;
    }

    @Override
    public boolean isRealizado() {
        return realizado;
    }

    @Override
    public void marcarRealizado() {
        this.realizado = true;
    }

    @Override
    public void marcarPendiente() {
        this.realizado = false;
    }

    @Override
    public String toString() {
        return nombre;
    }

}
