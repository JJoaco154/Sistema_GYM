package persistencia;

import java.sql.*;
import java.util.ArrayList;
import modelo.Rutina;
import java.time.LocalDate;

public class RutinaBdD {

    public RutinaBdD() {
    }

    private Connection conn;

    public RutinaBdD(Connection conn) {
        this.conn = conn;
    }

    public int crearRutina(Rutina r) throws SQLException {
        String sql = "INSERT INTO Rutina (nombre, descripcion, estado, fecha) VALUES (?, ?, ?, ?)";

        try (Connection con = conexion_BdD.conectar();
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, r.getNombre());
            ps.setString(2, r.getDescripcion());
            ps.setBoolean(3, r.getEstado());
            ps.setString(4, r.getFecha());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new SQLException("No se generó ID para la rutina");
            }
        }
    }

    public int guardarRutina(Rutina rutina) {
        String sql = "INSERT INTO Rutina (nombre, descripcion, estado, fecha) VALUES (?, ?, ?, ?)";

        try (Connection con = conexion_BdD.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, rutina.getNombre());
            ps.setString(2, rutina.getDescripcion());
            ps.setBoolean(3, rutina.getEstado());
            ps.setString(4, rutina.getFecha());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                rutina.setId(id);
                return id;
            }

            System.out.println("Rutina guardada correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al guardar rutina: " + e.getMessage());
        }
        return -1;
    }

    public ArrayList<Rutina> listarRutina() {
        ArrayList<Rutina> lista = new ArrayList<>();
        String sql = "SELECT * FROM Rutina";

        try (Connection con = conexion_BdD.conectar();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Rutina r = new Rutina(
                        rs.getString("Nombre"),
                        rs.getString("Descripcion"),
                        rs.getBoolean("Estado"),
                        rs.getString("Fecha")
                );

                r.setId(rs.getInt("Id_Rutina")); // setea el id de la rutina y lo agrega

                lista.add(r);
            }

            System.out.println("Ejercicios listados correctamente (" + lista.size() + " encontrados).");

        } catch (SQLException e) {
            System.out.println("Error al listar ejercicios: " + e.getMessage());
        }

        return lista;
    }

    public Rutina obtenerRutinaPorId(int idRutina) throws SQLException {
        String query = "SELECT * FROM Rutina WHERE Id_Rutina = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idRutina);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Rutina(
                        rs.getString("Nombre"),
                        rs.getString("Descripcion"),
                        rs.getBoolean("Estado"),
                        rs.getString("Fecha")
                //rs.getInt("Id_Cliente")
                );
            }
        }
        return null;
    }

    public boolean eliminarRutina(int idRutina) {
        String sql = "DELETE FROM Rutina WHERE Id_Rutina = ?";

        try (Connection con = conexion_BdD.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idRutina);
            
            RutinaEjercicioBdD reBD = new RutinaEjercicioBdD();
            reBD.eliminarEjerciciosDeRutina(idRutina);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("Rutina eliminada correctamente.");
            } else {
                System.out.println("No se encontró la rutina a eliminar.");
            }

        } catch (SQLException e) {
            System.out.println("Error al eliminar rutina: " + e.getMessage());
        }
        return true;
    }

    public void eliminarEjerciciosDeRutina(int idRutina) {
        String sql = "DELETE FROM rutina_ejercicio WHERE Id_Rutina = ?";

        try (Connection con = conexion_BdD.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idRutina);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al eliminar ejercicios de rutina: " + e.getMessage());
        }
    }

    public boolean asignarCliente(int idRutina, int dniCliente) {
        String sql = "UPDATE Rutina SET Id_Cliente = ? WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, dniCliente);
            ps.setInt(2, idRutina);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
