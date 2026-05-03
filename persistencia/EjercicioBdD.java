
package persistencia;

import modelo.Ejercicio;
import java.sql.*;
import java.util.ArrayList;

public class EjercicioBdD {

    private Connection conn = conexion_BdD.conectar();

    public EjercicioBdD() {
    }

    // INSERT CORRECTO
    public boolean guardarEjercicio(Ejercicio ejercicio) {
        String sql = "INSERT INTO Ejercicio (Nombre, Descripcion, Repeticiones, Peso, Estado, Grupo_muscular) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ejercicio.getNombre());
            ps.setString(2, ejercicio.getDescripcion());
            ps.setInt(3, ejercicio.getRepeticiones());
            ps.setInt(4, (int) ejercicio.getPeso());
            ps.setBoolean(5, ejercicio.isRealizado());
            ps.setString(6, ejercicio.getGrupoMuscular());

            ps.executeUpdate();
            System.out.println("Ejercicio guardado correctamente.");
            return true;

        } catch (SQLException e) {
            System.out.println("Error al guardar ejercicio: " + e.getMessage());
            return false;
        }
    }

    // SELECT CORRECTO
    public ArrayList<Ejercicio> listarEjercicios() {

        ArrayList<Ejercicio> lista = new ArrayList<>();
        String sql = "SELECT * FROM Ejercicio";

        try (
                Connection conn = conexion_BdD.conectar();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                Ejercicio e = new Ejercicio(
                        rs.getInt("Id_Ejercicio"),
                        rs.getString("Nombre"),
                        rs.getString("Descripcion"),
                        rs.getInt("Repeticiones"),
                        rs.getInt("Peso"),
                        rs.getString("Grupo_muscular")
                );

                if (rs.getBoolean("Estado")) {
                    e.marcarRealizado();
                }

                lista.add(e);
            }

            System.out.println("Ejercicios listados: " + lista.size());

        } catch (SQLException e) {
            System.out.println("Error al listar ejercicios: " + e.getMessage());
        }

        return lista;
    }

    public Ejercicio buscarPorGrupoMuscular(String grupo) {
        Ejercicio ej = null;
        String sql = "SELECT * FROM Ejercicio WHERE Grupo_muscular = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, grupo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                ej = new Ejercicio(
                        rs.getInt("Id_Ejercicio"),
                        rs.getString("Nombre"),
                        rs.getString("Descripcion"),
                        rs.getInt("Repeticiones"),
                        rs.getInt("Peso"),
                        rs.getString("Grupo_muscular")
                );

                if (rs.getBoolean("Estado")) {
                    ej.marcarRealizado();
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar ejercicio: " + e.getMessage());
        }

        return ej;
    }
}
