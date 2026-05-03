package persistencia;

import modelo.Ejercicio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Rutina;

public class RutinaEjercicioBdD {

    private List<Rutina> rutinas = new ArrayList<>();

    // Asignar ejercicio a rutina
    public boolean asignarEjercicio(int idRutina, int idEjercicio) {

        String sql = "INSERT INTO rutina_ejercicio (id_rutina, id_ejercicio) VALUES (?, ?)";

        try (Connection con = conexion_BdD.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idRutina);
            ps.setInt(2, idEjercicio);

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error asignando ejercicio a rutina: " + e.getMessage());
            return false;
        }
    }

    public List<Ejercicio> obtenerEjerciciosDeRutina(int idRutina) {
        List<Ejercicio> ejercicios = new ArrayList<>();

        String sql = "SELECT e.Id_Ejercicio, e.Nombre, e.Descripcion, e.Repeticiones, e.Peso, e.Grupo_muscular, e.Estado "
                + "FROM rutina_ejercicio re "
                + "JOIN ejercicio e ON re.id_ejercicio = e.Id_Ejercicio "
                + "WHERE re.id_rutina = ?";

        try (Connection con = conexion_BdD.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idRutina);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Ejercicio e = new Ejercicio(
                        rs.getInt("Id_Ejercicio"),
                        rs.getString("Nombre"),
                        rs.getString("Descripcion"),
                        rs.getInt("Repeticiones"),
                        rs.getDouble("Peso"),
                        rs.getString("Grupo_muscular")
                );
                if (rs.getBoolean("Estado")) {
                    e.marcarRealizado();
                }
                ejercicios.add(e);
            }

        } catch (SQLException ex) {
            System.out.println("Error obteniendo ejercicios: " + ex.getMessage());
        }

        return ejercicios;
    }

    public void agregarEjercicioARutina(int idRutina, Ejercicio ejercicio) {
        for (Rutina r : rutinas) {
            if (r.getId() == idRutina) {
                r.getLista().add(ejercicio);
                return;
            }
        }
    }

    public boolean actualizarEstadoEjercicio(int idEjercicio, boolean realizado) {

        String sql = "UPDATE Ejercicio SET Estado = ? WHERE Id_Ejercicio = ?";

        try (Connection con = conexion_BdD.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setBoolean(1, realizado);
            ps.setInt(2, idEjercicio);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error actualizando estado ejercicio: " + e.getMessage());
            return false;
        }
    }

    public void agregarRutina(Rutina rutina) {
        rutinas.add(rutina);
    }

    public boolean eliminarEjerciciosDeRutina(int idRutina) {

        String sql = "DELETE FROM rutina_ejercicio WHERE id_rutina = ?";

        try (Connection con = conexion_BdD.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idRutina);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error eliminando ejercicios de la rutina: " + e.getMessage());
            return false;
        }
    }

}
