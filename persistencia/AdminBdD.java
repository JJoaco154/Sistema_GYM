
package persistencia;

import modelo.Administrador;
import java.sql.*;
import java.util.ArrayList;

public class AdminBdD {
    
    public void insertarAdministrador(Administrador administrador) {
        String sql = "INSERT INTO Administrador (nombre, apellido, dni, contraseña, clases) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = conexion_BdD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, administrador.getNombre());
            ps.setString(2, administrador.getApellido());
            ps.setInt(3, administrador.getDNI());
            ps.setString(4, administrador.getContraseña());
            ps.executeUpdate();

            System.out.println("Administrador agregado correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al insertar admin: " + e.getMessage());
        }
    }

    public ArrayList<Administrador> listaradministrador() {
        ArrayList<Administrador> lista = new ArrayList<>();
        String sql = "SELECT * FROM Administrador";

        try (Connection con = conexion_BdD.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Administrador a = new Administrador(
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getInt("dni"),
                        rs.getString("contraseña")
                );
                lista.add(a);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar admin: " + e.getMessage());
        }

        return lista;
    }

    public Administrador buscarPorDNI(String dni) {
        Administrador a = null;
        String sql = "SELECT * FROM Administrador WHERE dni = ?";

        try (Connection con = conexion_BdD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, dni);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                a = new Administrador(
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getInt("dni"),
                        rs.getString("contraseña")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar admin: " + e.getMessage());
        }

        return a;
    }
}
