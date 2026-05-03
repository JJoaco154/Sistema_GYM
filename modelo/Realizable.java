package modelo;

public interface Realizable {
    void marcarRealizado();   // marca como realizado
    void marcarPendiente();   // marca como pendiente
    boolean isRealizado();    // consulta si está realizado
}

