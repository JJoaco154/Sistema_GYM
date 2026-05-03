package modelo;

import java.util.Date;

public class HistorialRutina {
    private Rutina rutina;
    private Date fechaCompleta;  // Fecha de cuando se completó la rutina

    // Constructor
    public HistorialRutina(Rutina rutina) {
        this.rutina = rutina;
        this.fechaCompleta = new Date();  // Fecha actual
    }

    // Getters y Setters
    public Rutina getRutina() {
        return rutina;
    }

    public void setRutina(Rutina rutina) {
        this.rutina = rutina;
    }

    public Date getFechaCompleta() {
        return fechaCompleta;
    }

    public void setFechaCompleta(Date fechaCompleta) {
        this.fechaCompleta = fechaCompleta;
    }

    @Override
    public String toString() {
        return "HistorialRutina{" +
                "rutina=" + rutina.getNombre() +
                ", fechaCompleta=" + fechaCompleta +
                '}';
    }
}

