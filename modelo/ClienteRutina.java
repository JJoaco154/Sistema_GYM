package modelo;


public class ClienteRutina {
    private int dniCliente;
    private int idRutina;
    private String fechaAsignacion;

    public ClienteRutina(int dniCliente, int idRutina, String fechaAsignacion) {
        this.dniCliente = dniCliente;
        this.idRutina = idRutina;
        this.fechaAsignacion = fechaAsignacion;
    }

    public int getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(int dniCliente) {
        this.dniCliente = dniCliente;
    }

    public int getIdRutina() {
        return idRutina;
    }

    public void setIdRutina(int idRutina) {
        this.idRutina = idRutina;
    }

    public String getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(String fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    
}


