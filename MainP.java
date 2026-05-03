 /*import persistencia.*;
import java.sql.*;

public class MainP {
    
     public static void main(String[] args){
            Connection con = conexion_BdD.conectar(); 

   if (con != null) {
        System.out.println("✅ Conexión a la base de datos exitosa.");
    } else {
        System.out.println("❌ No se pudo establecer conexión.");
     }
    }   
}*/

/*import modelo.*;
import persistencia.*;
import java.util.ArrayList;

public class MainP {

    public static void main(String[] args) {

        System.out.println("=== INICIANDO SISTEMA ===");

        // 1) Cargar datos iniciales desde la BD
        Sistema sistema = new Sistema();

        // Servicios
        EntrenadorService entrenadorService = new EntrenadorService();
        ClienteService clienteService = new ClienteService();
        RutinaService rutinaService = new RutinaService();

        System.out.println("\n=== CREANDO OBJETOS DE PRUEBA ===");

        // ============================================================
        // 2) PRIMERO: Crear entrenador (porque el cliente depende de él)
        // ============================================================
        Entrenador nuevoEntrenador = new Entrenador(
                "Carlos",
                "Gomez",
                34567,         // DNI del entrenador (PK)
                "passwordCarlos"
        );

        entrenadorService.registrarEntrenador(nuevoEntrenador);

        // ============================================================
        // 3) SEGUNDO: Crear cliente con Id_Entrenador = DNI EN BD
        // ============================================================
        Cliente nuevoCliente = new Cliente(
                "Juan",
                "Perez",
                12345678,          // DNI del cliente (PK)
                "soyJuan",
                30,                // clases
                34567              // FK → debe ser un ENTRENADOR EXISTENTE
        );

        clienteService.registrarCliente(nuevoCliente);

        // ============================================================
        // 4) TERCERO: Crear rutina para un cliente existente
        // ============================================================
        Rutina nuevaRutina = new Rutina(
                "Rutina Full Body",
                "Entrenamiento para todo el cuerpo",
                false,
                "2024-11-27",
                nuevoCliente.getDNI()   // FK → cliente existente
        );

        rutinaService.guardarRutina(nuevaRutina);

        // ============================================================
        // 5) Asignar rutina (esto solo afecta la memoria)
        // ============================================================
        rutinaService.asignarRutinaACliente(nuevaRutina, nuevoCliente);

        System.out.println("\n=== RECARGANDO SISTEMA PARA VERIFICAR BD ===");

        Sistema sistema2 = new Sistema();

        // Mostrar datos cargados desde la BD
        mostrar("Clientes", sistema2.getClientes());
        mostrar("Entrenadores", sistema2.getEntrenadores());
        mostrar("Rutinas", sistema2.getRutinas());

        System.out.println("\n=== FIN DE LA PRUEBA ===");
    }

    private static <T> void mostrar(String titulo, ArrayList<T> lista) {
        System.out.println("\n" + titulo + " (" + lista.size() + "):");
        for (T item : lista) {
            System.out.println(" - " + item.toString());
        }
    }
}*/
