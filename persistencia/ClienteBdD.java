package persistencia;

import modelo.Cliente;
import java.sql.*;
import java.util.ArrayList;

public class ClienteBdD {

    public boolean insertarCliente(Cliente cliente) {
        String sql = "INSERT INTO Cliente (nombre, apellido, dni, contraseña, clases, id_entrenador) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = conexion_BdD.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setInt(3, cliente.getDNI());
            ps.setString(4, cliente.getContraseña());
            ps.setInt(5, cliente.getClases());

            if (cliente.getIdEntrenador() <= 0) {
                ps.setNull(6, Types.INTEGER);
            } else {
                ps.setInt(6, cliente.getIdEntrenador());
            }

            int filas = ps.executeUpdate();  // INSERTAr EN BDD

            return filas > 0;

        } catch (SQLException e) {
            System.out.println("Error al insertar cliente: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Cliente> listarClientes() {
        ArrayList<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM Cliente";

        try (Connection con = conexion_BdD.conectar();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Cliente c = new Cliente(
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getInt("dni"),
                        rs.getString("contraseña"),
                        rs.getInt("clases"),
                        rs.getInt("id_entrenador")
                );
                lista.add(c);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar clientes: " + e.getMessage());
        }

        return lista;
    }

    public Cliente buscarPorDNI(int dni) {
        Cliente c = null;
        String sql = "SELECT * FROM Cliente WHERE dni = ?";

        try (Connection con = conexion_BdD.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, dni);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                c = new Cliente(
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getInt("dni"),
                        rs.getString("contraseña"),
                        rs.getInt("clases"),
                        rs.getInt("id_entrenador")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar cliente: " + e.getMessage());
        }

        return c;
    }

    public boolean asignarEntrenador(int dniCliente, int dniEntrenador) {
        String sql = "UPDATE Cliente SET id_entrenador = ? WHERE dni = ?";

        try (Connection con = conexion_BdD.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {

            if (dniEntrenador <= 0) {
                ps.setNull(1, java.sql.Types.INTEGER);
            } else {
                ps.setInt(1, dniEntrenador);
            }

            ps.setInt(2, dniCliente);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al asignar entrenador: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarClases(int dniCliente, int nuevasClases) {
        String sql = "UPDATE Cliente SET clases = ? WHERE dni = ?";

        try (Connection con = conexion_BdD.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, nuevasClases);
            ps.setInt(2, dniCliente);

            int filas = ps.executeUpdate();

            return filas > 0; // true si actualizó, false si no existe el cliente

        } catch (SQLException e) {
            System.out.println("Error al actualizar clases: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarCliente(int dni) {
        boolean eliminado = false;

        // 1. Eliminar rutinas del cliente
        RutinaClienteBdD rutinaBD = new RutinaClienteBdD();
        rutinaBD.eliminarRutinasDeCliente(dni);
        
        // 2. Eliminar cliente
        String sql = "DELETE FROM Cliente WHERE dni = ?";

        try (Connection con = conexion_BdD.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, dni);

            int filas = ps.executeUpdate();

            if (filas > 0) {
                System.out.println("Cliente eliminado correctamente.");
            } else {
                System.out.println("No se encontró un cliente con ese DNI.");
            }

        } catch (SQLException e) {
            System.out.println("Error al eliminar cliente: " + e.getMessage());
        }
        return eliminado;
    }

    public ArrayList<Cliente> listarClientesPorEntrenador(int dniEntrenador) {
        ArrayList<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM Cliente WHERE id_entrenador = ?";

        try (Connection con = conexion_BdD.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, dniEntrenador);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Cliente c = new Cliente(
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getInt("dni"),
                        rs.getString("contraseña"),
                        rs.getInt("clases"),
                        rs.getInt("id_entrenador")
                );
                lista.add(c);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar clientes por entrenador: " + e.getMessage());
        }

        return lista;
    }

}
