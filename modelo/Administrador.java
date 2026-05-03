
package modelo;

public class Administrador extends Usuario {

    public Administrador(String nombre, String apellido, int dni, String contraseña) {
        super(nombre, apellido, dni, contraseña);
    }

    public Cliente crearCliente(String nombre, String apellido, int dni, String contraseña, int clases, int idEntrenador) {
        return new Cliente(nombre, apellido, dni, contraseña, clases, idEntrenador);
    }

    public Entrenador crearEntrenador(String nombre, String apellido, int dni, String contraseña) {
        return new Entrenador(nombre, apellido, dni, contraseña);
    }

    public Administrador crearAdmin(String nombre, String apellido, int dni, String contraseña) {
        return new Administrador(nombre, apellido, dni, contraseña);
    }
}

