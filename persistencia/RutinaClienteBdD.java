package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import modelo.Cliente;
import modelo.Rutina;

public class RutinaClienteBdD {

    public boolean asignarRutina(int dniCliente, int idRutina) {
        String sql = "INSERT INTO cliente_rutina (dni_cliente, id_rutina, fecha_asignacion) VALUES (?, ?, ?)";

        try (Connection con = conexion_BdD.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, dniCliente);
            ps.setInt(2, idRutina);
            ps.setString(3, LocalDate.now().toString());

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println("Error asignando rutina al cliente: " + e.getMessage());
            return false;
        }
    }

    public List<Rutina> obtenerRutinasDeCliente(int dniCliente) {
        List<Rutina> lista = new ArrayList<>();

        String sql = "SELECT r.* FROM rutina r JOIN cliente_rutina cr "
                + "ON r.Id_Rutina = cr.id_rutina WHERE cr.dni_cliente = ?";

        try (Connection con = conexion_BdD.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, dniCliente);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Rutina r = new Rutina(
                        //rs.getInt("Id_Rutina"),
                        rs.getString("Nombre"),
                        rs.getString("Descripcion"),
                        rs.getBoolean("Estado"),
                        rs.getString("Fecha")
                );
                r.setId(rs.getInt("Id_Rutina"));
                lista.add(r);
            }

        } catch (Exception e) {
            System.out.println("Error obteniendo rutinas del cliente: " + e.getMessage());
        }

        return lista;
    }

    public boolean eliminarRutinasDeCliente(int dniCliente) {
        String sql = "DELETE FROM cliente_rutina WHERE dni_cliente = ?";

        try (Connection con = conexion_BdD.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, dniCliente);
            ps.executeUpdate(); // puede ser 0 o más filas
            return true;

        } catch (SQLException e) {
            System.out.println("Error eliminando rutinas del cliente: " + e.getMessage());
            return false;
        }
    }

}
