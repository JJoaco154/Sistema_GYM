import javax.swing.SwingUtilities;
import modelo.Sistema;
import modelo.ClienteService;
import modelo.EntrenadorService;
import modelo.AdminService;
import vista.VistaLogin;

public class MainApp {
    public static void main(String[] args) {

        // Crear sistema (carga datos desde la BD)
        Sistema sistema = new Sistema();

        // Crear services compartiendo el mismo sistema
        ClienteService clienteService = new ClienteService(sistema);
        EntrenadorService entrenadorService = new EntrenadorService(sistema);
        AdminService adminService = new AdminService(sistema);

        // Ejecutar interfaz gráfica en el hilo de Swing
        SwingUtilities.invokeLater(() -> {
             VistaLogin login = new VistaLogin(sistema);

            login.setVisible(true);
        });

    }
}


