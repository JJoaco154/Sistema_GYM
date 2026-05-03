
package modelo;

import java.util.ArrayList;

public class Entrenador extends Usuario {

    private ArrayList<Ejercicio> ejercicios = new ArrayList<>();
    private ArrayList<Rutina> rutinas = new ArrayList<>();
    private ArrayList<Cliente> clientes = new ArrayList<>();

    public Entrenador(String nombre, String apellido, int dni, String contraseña) {
        super(nombre, apellido, dni, contraseña);
    }

    public ArrayList<Ejercicio> getEjercicio() {
        return ejercicios;
    }

    public ArrayList<Rutina> getHistorialR() {
        return rutinas;
    }

    public ArrayList<Cliente> getCliente() {
        return clientes;
    }

    public Cliente agregarCliente(Cliente c) {
        if (!clientes.contains(c)) {
            clientes.add(c);
            System.out.println("Cliente agregado: " + c.getNombre());
        } else {
            System.out.println("El cliente ya existe en la lista.");
        }
        return c;
    }

    public Ejercicio agregarEjercicio(Ejercicio e) {
        if (!ejercicios.contains(e)) {
            ejercicios.add(e);
            System.out.println("Ejercicio agregado: " + e.getNombre());
        } else {
            System.out.println("El ejercicio ya existe.");
        }
        return e;
    }

    public Rutina crearRutina(Rutina r) {
        if (!rutinas.contains(r)) {
            rutinas.add(r);
            System.out.println("Rutina creada: " + r.getNombre());
        } else {
            System.out.println("La rutina ya existe.");
        }
        return r;
    }

    public void asignarRutinaACliente(Cliente c, Rutina r) {
        if (clientes.contains(c)) {
            c.setRutinaActual(r);
            c.getHistorialR().add(r);
            System.out.println("Rutina asignada a " + c.getNombre());
        } else {
            System.out.println("El cliente no pertenece a este entrenador.");
        }
    }

    public void eliminarRutina(Rutina r) {
        if (rutinas.contains(r)) {
            rutinas.remove(r);
            System.out.println("Rutina eliminada.");
        } else {
            System.out.println("La rutina no existe.");
        }
    }
}

