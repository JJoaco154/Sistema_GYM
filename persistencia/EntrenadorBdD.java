package persistencia;

import modelo.Entrenador;
import java.sql.*;
import java.util.ArrayList;

public class EntrenadorBdD {

    // Insertar un entrenador en la base de datos
    public boolean insertarEntrenador(Entrenador entrenador) {
        String sql = "INSERT INTO Entrenador (DNI, Nombre, Apellido, Contraseña) VALUES (?, ?, ?, ?)";

        try (Connection con = conexion_BdD.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, entrenador.getDNI());
            ps.setString(2, entrenador.getNombre());
            ps.setString(3, entrenador.getApellido());
            ps.setString(4, entrenador.getContraseña());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al insertar entrenador: " + e.getMessage());
            return false;
        }
    }

    // Listar todos los entrenadores de la base de datos
    public ArrayList<Entrenador> listarEntrenadores() {
        ArrayList<Entrenador> lista = new ArrayList<>();
        String sql = "SELECT * FROM Entrenador";

        try (Connection con = conexion_BdD.conectar();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Entrenador e = new Entrenador(
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getInt("dni"),
                        rs.getString("contraseña")
                );
                lista.add(e);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar entrenadores: " + e.getMessage());
        }

        return lista;
    }

    // Buscar un entrenador por DNI
    public Entrenador buscarPorDNI(int dni) {
        Entrenador e = null;
        String sql = "SELECT * FROM Entrenador WHERE dni = ?";

        try (Connection con = conexion_BdD.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, dni);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                e = new Entrenador(
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getInt("dni"),
                        rs.getString("contraseña")
                );
            }

        } catch (SQLException ex) {
            System.out.println("Error al buscar entrenador: " + ex.getMessage());
        }

        return e;
    }

    // Eliminar un entrenador por DNI
    public boolean eliminarEntrenador(int dni) {
        String sql = "DELETE FROM Entrenador WHERE dni = ?";
        try (Connection con = conexion_BdD.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, dni);
            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("Entrenador eliminado correctamente.");
            } else {
                System.out.println("No se encontró un entrenador con ese DNI.");
            }

        } catch (SQLException e) {
            System.out.println("Error al eliminar entrenador: " + e.getMessage());
        }
        return true;
    }

    /*public void agregarClienteAEntrenador(int dniEntrenador, int dniCliente) {
        String sql = "INSERT INTO Entrenador_Cliente (id_entrenador, id_cliente) VALUES (?, ?)";

        try (Connection con = conexion_BdD.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, dniEntrenador);
            ps.setInt(2, dniCliente);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al asociar cliente a entrenador: " + e.getMessage());
        }
    }*/

}
