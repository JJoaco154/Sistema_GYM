
package persistencia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import modelo.Rutina;
import modelo.Cliente;

public class HistorialRutinaBdD {

    public void guardarHistorial(Rutina r, Cliente c) {
        String sql = "INSERT INTO historial_rutinas (Id_Cliente, Id_Rutina, Fecha_inicio) VALUES (?, ?, ?)";

        try (Connection con = conexion_BdD.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, c.getDNI());
            stmt.setInt(2, r.getId());
            stmt.setString(3, r.getFecha());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al guardar historial de rutina: " + e.getMessage());
        }
    }
}

