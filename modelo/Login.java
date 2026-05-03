package modelo;

import util.Validador;
import java.util.ArrayList;
import java.util.Scanner;
import util.HashUtil;


public class Login {
    private final ArrayList<Usuario> lista;
    private final Scanner scan = new Scanner(System.in);

    public Login() {
        lista = new ArrayList<>();
    }

    // -----------------------------
    // Método para registrar usuario
    // -----------------------------
    public void cargarUsuario() {
        String nombre, apellido, dni, contrasena, contrasenaAux = null;

        // Validar nombre
        do {
            System.out.print("Ingrese nombre: ");
            nombre = scan.nextLine();
            if (!Validador.esTexto(nombre)) {
                System.out.println("Nombre inválido. Solo letras.");
            }
        } while (!Validador.esTexto(nombre));

        // Validar apellido
        do {
            System.out.print("Ingrese apellido: ");
            apellido = scan.nextLine();
            if (!Validador.esTexto(apellido)) {
                System.out.println("Apellido inválido. Solo letras.");
            }
        } while (!Validador.esTexto(apellido));

        // Validar DNI único
        int Documento = 0;
        boolean existe;
        do {
            existe = false;
            System.out.print("Ingrese DNI (solo números): ");
            String input = scan.nextLine();

            if (!Validador.esNumero(input)) {
                System.out.println("El DNI debe contener solo números.");
                existe = true;
                continue;
            }

            
            Documento = Integer.parseInt(input);
            
            for (Usuario u : lista) {
                if (u.getDNI() == Documento) {
                    System.out.println("Ya existe un usuario con ese DNI.");
                    existe = true;
                    break;
                }
            }

            
        } while (existe);

        // Validar contraseña y confirmación
        do {
            System.out.print("Ingrese contraseña (mínimo 6 caracteres, letras y números): ");
            contrasena = scan.nextLine();
            if (!Validador.contrasenaValida(contrasena)) {
                System.out.println("Contraseña insegura. Use letras y números.");
                continue;
            }

            System.out.print("Repita contraseña: ");
            contrasenaAux = scan.nextLine();

            if (!contrasena.equals(contrasenaAux)) {
                System.out.println("Las contraseñas no coinciden.");
            }
        } while (!contrasena.equals(contrasenaAux));
        
        // Hashear la contraseña antes de crear el usuario
        contrasena = HashUtil.generarHash(contrasena);

        // Elegir tipo de usuario
        int opcion = 0;
        do {
            System.out.println("\nSeleccione tipo de usuario:");
            System.out.println("1 - Cliente");
            System.out.println("2 - Entrenador");
            System.out.println("3 - Administrador");
            String input = scan.nextLine();

            if (!Validador.esNumero(input)) {
                System.out.println("Ingrese un número válido.");
                continue;
            }

            opcion = Integer.parseInt(input);
            if (opcion < 1 || opcion > 3) {
                System.out.println("Opción inválida.");
            }
        } while (opcion < 1 || opcion > 3);

        Usuario usuario = null;

        switch (opcion) {
            case 1: { // CLIENTE
                int clases = 0;
                do {
                    System.out.print("Ingrese cantidad de clases (número positivo): ");
                    String input = scan.nextLine();
                    if (!Validador.esEnteroPositivo(input)) {
                        System.out.println("Número inválido.");
                        continue;
                    }
                    clases = Integer.parseInt(input);
                    break;
                } while (true);

                // 0 es el idEntrenador por defecto
                usuario = new Cliente(nombre, apellido, Documento, contrasena, clases, 0);
                break;
            }

            case 2: // ENTRENADOR
                usuario = new Entrenador(nombre, apellido, Documento, contrasena);
                break;

            case 3: // ADMINISTRADOR
                usuario = new Administrador(nombre, apellido, Documento, contrasena);
                break;
        }

        // Agregar usuario a la lista
        if (usuario != null) {
            lista.add(usuario);
            System.out.println("\n✅ Usuario creado correctamente: " + usuario.getClass().getSimpleName());
        }
    }

    // -----------------------------
    // Método para iniciar sesión
    // -----------------------------
    public Usuario loguearUsuario() {
        
        System.out.print("Ingrese DNI: ");
        String inputDni = scan.nextLine();

        if (!Validador.esNumero(inputDni)) {
            System.out.println("DNI inválido.");
            return null;
        }

        int dni = Integer.parseInt(inputDni);

        System.out.print("Ingrese contraseña: ");
        String contrasena = scan.nextLine();
        
        // Hasheamos la contraseña ingresada
        String contrasenaHash = HashUtil.generarHash(contrasena);

        
        for (Usuario u : lista) {
            if (u.login(dni, contrasena)) {
                System.out.println("\n Bienvenido/a " + u.getNombre() + " (" + u.getClass().getSimpleName() + ")");
                return u;
            }
        }
        System.out.println("Usuario o contraseña incorrectos.");
        return null;
    }

    // -----------------------------
    // Método para listar usuarios
    // -----------------------------
    public void mostrarUsuarios() {
        if (lista.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
        } else {
            System.out.println("\nLista de usuarios registrados:");
            for (Usuario u : lista) {
                System.out.println("- " + u.getNombre() + " " + u.getApellido() + " [" + u.getClass().getSimpleName() + "]");
            }
        }
    }

    // Getter de lista (útil si querés vincular con Sistema)
    public ArrayList<Usuario> getUsuarios() {
        return lista;
    }
}





