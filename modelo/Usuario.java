package modelo;

import java.util.Objects;
import util.HashUtil;

public abstract class Usuario {

    private String Nombre;
    private String Apellido;
    private int DNI;
    private String Contraseña;

    public Usuario(String Nombre, String Apellido, int DNI, String Contraseña) {
        this.Nombre = Nombre;
        this.Apellido = Apellido;
        this.DNI = DNI;
        this.Contraseña = HashUtil.generarHash(Contraseña);
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String Apellido) {
        this.Apellido = Apellido;
    }

    public int getDNI() {
        return DNI;
    }

    public void setDNI(int DNI) {
        this.DNI = DNI;
    }

    public String getContraseña() {
        return Contraseña;
    }

    public void setContraseña(String Contraseña) {
        this.Contraseña = HashUtil.generarHash(Contraseña);;
    }

    /*public void cambiarContraseña(String vieja, String nueva) {
        if (this.Contraseña.equals(String.valueOf(vieja.hashCode()))) {
            this.Contraseña = String.valueOf(nueva.hashCode());
            System.out.println("Contraseña cambiada correctamente.");
        } else {
            System.out.println("La contraseña actual no coincide.");
        }
    }*/

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) obj;
        return DNI == other.DNI;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(DNI);
    }

    public boolean login(int dni, String contraseñaIngresada) {
        String hashIngresado = HashUtil.generarHash(contraseñaIngresada); // Hasheamos la contraseña ingresada
        return this.DNI == dni && this.Contraseña.equals(hashIngresado); // Comparamos los hashes
    }

    @Override
    public String toString() {
        return Nombre;
    }
}
