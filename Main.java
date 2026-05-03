/*import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        // 1️⃣ Creamos el sistema
        Sistema sistema = new Sistema();

        // 2️⃣ Obtenemos el admin principal
        Administrador admin1 = sistema.getAdmin();
        System.out.println("Admin principal: " + admin1.getNombre() + " " + admin1.getApellido());

        // 3️⃣ Creamos un segundo administrador usando admin1
        Administrador admin2 = admin1.crearAdmin("Laura", "Martínez", "55555", "pass789");
        sistema.agregarAdministrador(admin1, admin2);
        System.out.println("Admin 2 agregado: " + admin2.getNombre() + " " + admin2.getApellido());

        // 4️⃣ Creamos clientes usando ambos admins
        Cliente cliente1 = admin1.crearCliente("Juan", "Pérez", "11111111", "pass123", 5);
        sistema.agregarCliente(admin1,  cliente1);

        Cliente cliente2 = admin2.crearCliente("Ana", "Gómez", "22222222", "pass456", 3);
        sistema.agregarCliente( admin2, cliente2);

        // 5️⃣ Creamos entrenadores usando admin2
        Entrenador entrenador1 = admin2.crearEntrenador("María", "López", "33333333", "ent123");
        sistema.agregarEntrenador(admin1, entrenador1);

        Entrenador entrenador2 = admin1.crearEntrenador("Carlos", "Ramírez", "44444444", "ent456");
        sistema.agregarEntrenador(admin2, entrenador2);

        // 6️⃣ Creamos ejercicios
        Ejercicio e1 = new Ejercicio("Flexión", "Flexiones de pecho", 15, 0, false);
        Ejercicio e2 = new Ejercicio("Sentadilla", "Sentadillas normales", 20, 0, false);
        Ejercicio e3 = new Ejercicio("Peso muerto", "Levantamiento de barra", 10, 50, false);

        // 7️⃣ Creamos rutinas
        Rutina rutina1 = new Rutina("Rutina A", "Full body", false, "2025-09-18");
        rutina1.agregarEjercicio(e1);
        rutina1.agregarEjercicio(e2);

        Rutina rutina2 = new Rutina("Rutina B", "Piernas", false, "2025-09-19");
        rutina2.agregarEjercicio(e2);
        rutina2.agregarEjercicio(e3);

        // 8️⃣ Asignamos rutinas a clientes
        cliente1.setRutinaActual(rutina1);
        cliente2.setRutinaActual(rutina2);

        // 9️⃣ Marcamos ejercicios como realizados para cliente1
        System.out.println("\nMarcando ejercicios realizados para cliente1:");
        cliente1.marcarEjercicioCompletado(); // Flexión
        cliente1.marcarEjercicioCompletado(); // Sentadilla

        //  🔹 Finalizamos rutina de cliente1
        cliente1.finalizarRutina(cliente1.getRutinaActual());

        // 🔹 Ver historial de cliente1
        System.out.println("\nHistorial de cliente1:");
        cliente1.verHistorialRutina();

        // 10️⃣ Mostrar todos los clientes desde ambos admins
        System.out.println("\nClientes vistos por admin1:");
        for (Cliente c : sistema.getCliente()) {
            sistema.verCliente(admin1, c);
        }

        System.out.println("\nClientes vistos por admin2:");
        for (Cliente c : sistema.getCliente()) {
            sistema.verCliente(admin2, c);
        }

        // 11️⃣ Mostrar todos los entrenadores desde ambos admins
        System.out.println("\nEntrenadores vistos por admin1:");
        for (Entrenador e : sistema.getEntrenador()) {
            sistema.verEntrenador(admin1, e);
        }

        System.out.println("\nEntrenadores vistos por admin2:");
        for (Entrenador e : sistema.getEntrenador()) {
            sistema.verEntrenador(admin2, e);
        }

        System.out.println("\n✅ Ambos admins pueden ejecutar métodos de sistema correctamente.");
    }
}
*/

/*import java.util.Scanner;
import modelo.*;

public class Main
{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Sistema sistema = new Sistema(); // Sistema con adminPrincipal ya creado

        while (true) {
            System.out.println("\n=== BIENVENIDO AL GIMNASIO ===");
            System.out.println("Seleccione tipo de usuario:");
            System.out.println("1. Administrador");
            System.out.println("2. Cliente");
            System.out.println("3. Entrenador");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            int opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1: // ADMIN
                    System.out.println("Ingrese nombre del admin:");
                    String adminNombre = sc.nextLine();
                    System.out.println("Ingrese DNI:");
                    String adminDNI = sc.nextLine();

                    // Menú Admin
                    boolean salirAdmin = false;
                    while (!salirAdmin) {
                        System.out.println("\n--- MENÚ ADMIN ---");
                        System.out.println("1. Crear Cliente");
                        System.out.println("2. Crear Entrenador");
                        System.out.println("3. Ver Clientes");
                        System.out.println("4. Ver Entrenadores");
                        System.out.println("0. Salir");
                        System.out.print("Opción: ");
                        int opAdmin = sc.nextInt();
                        sc.nextLine();

                        switch (opAdmin) {
                            case 1: // Crear cliente
                                System.out.println("Nombre:");
                                String cNombre = sc.nextLine();
                                System.out.println("Apellido:");
                                String cApellido = sc.nextLine();
                                System.out.println("DNI:");
                                String cDNI = sc.nextLine();
                                System.out.println("Contraseña:");
                                String cPass = sc.nextLine();
                                System.out.println("Cantidad de clases:");
                                int cClases = sc.nextInt();
                                sc.nextLine();
                                break;

                            case 2: // Crear entrenador
                                System.out.println("Nombre:");
                                String eNombre = sc.nextLine();
                                System.out.println("Apellido:");
                                String eApellido = sc.nextLine();
                                System.out.println("DNI:");
                                String eDNI = sc.nextLine();
                                System.out.println("Contraseña:");
                                String ePass = sc.nextLine();

                                System.out.println("Entrenador creado y agregado al sistema.");
                                break;

                            case 0:
                                salirAdmin = true;
                                break;
                            default:
                                System.out.println("Opción inválida");
                        }
                    }
                    break;

                case 2: // CLIENTE
                    System.out.println("Ingrese DNI del cliente:");
                    String cDNIlog = sc.nextLine();
                    Cliente clienteLogueado = null;
                    for (Cliente c : sistema.getCliente()) {
                        if (c.getDNI().equals(cDNIlog)) {
                            clienteLogueado = c;
                            break;
                        }
                    }

                    if (clienteLogueado == null) {
                        System.out.println("Cliente no encontrado.");
                        break;
                    }

                    System.out.println("Bienvenido " + clienteLogueado.getNombre());

                    boolean salirCliente = false;
                    while (!salirCliente) {
                        System.out.println("\n--- MENÚ CLIENTE ---");
                        System.out.println("1. Marcar ejercicio completado");
                        System.out.println("2. Finalizar rutina");
                        System.out.println("3. Ver historial de rutinas");
                        System.out.println("0. Salir");
                        System.out.print("Opción: ");
                        int opCli = sc.nextInt();
                        sc.nextLine();

                        switch (opCli) {
                            case 1:
                                if (clienteLogueado.marcarEjercicioCompletado()) {
                                    System.out.println("Ejercicio marcado como completado.");
                                } else {
                                    System.out.println("No hay ejercicios pendientes.");
                                }
                                break;
                            case 2:
                                if (clienteLogueado.finalizarRutina(clienteLogueado.getRutinaActual())) {
                                    System.out.println("Rutina finalizada y agregada al historial.");
                                } else {
                                    System.out.println("No se pudo finalizar la rutina.");
                                }
                                break;
                            case 3:
                                clienteLogueado.verHistorialRutina();
                                break;
                            case 0:
                                salirCliente = true;
                                break;
                            default:
                                System.out.println("Opción inválida");
                        }
                    }
                    break;

                case 3: // ENTRENADOR
                    System.out.println("Ingrese DNI del entrenador:");
                    String eDNIlog = sc.nextLine();
                    Entrenador entrenadorLog = null;
                    for (Entrenador e : sistema.getEntrenador()) {
                        if (e.getDNI().equals(eDNIlog)) {
                            entrenadorLog = e;
                            break;
                        }
                    }

                    if (entrenadorLog == null) {
                        System.out.println("Entrenador no encontrado.");
                        break;
                    }

                    System.out.println("Bienvenido " + entrenadorLog.getNombre());
                    // Aquí podrías agregar menú de entrenador si querés
                    break;

                case 0:
                    System.out.println("¡Hasta luego!");
                    sc.close();
                    return;

                default:
                    System.out.println("Opción inválida");
            }
        }
    }
}*/
