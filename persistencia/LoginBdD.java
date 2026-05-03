package persistencia;

import modelo.*;
import java.sql.*;
import java.util.ArrayList;

public class LoginBdD {

    // ✅ Método para insertar un nuevo usuario según su tipo
    public void insertarUsuario(Usuario u) {
        String tabla = " ";
        String sql = " ";

        if (u instanceof Administrador) {
            tabla = "Administrador";
            sql = "INSERT INTO " + tabla + " (nombre, apellido, dni, contraseña) VALUES (?, ?, ?, ?)";
        } else if (u instanceof Entrenador) {
            tabla = "entrenadores";
            sql = "INSERT INTO " + tabla + " (nombre, apellido, dni, contraseña) VALUES (?, ?, ?, ?)";
        } else if (u instanceof Cliente) {
            tabla = "clientes";
            sql = "INSERT INTO " + tabla + " (nombre, apellido, dni, contraseña, clases, idEntrenador) VALUES (?, ?, ?, ?, ?, ?)";
        } else {
            System.out.println("Tipo de usuario no reconocido.");
            return;
        }

        try (Connection con = conexion_BdD.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setInt(3, u.getDNI());
            ps.setString(4, u.getContraseña());

            if (u instanceof Cliente) {
                Cliente c = (Cliente) u;     // cast para ver si u es un cliente
                ps.setInt(5, c.getClases());
                ps.setInt(6, c.getIdEntrenador());
            }

            ps.executeUpdate();
            System.out.println("Usuario agregado correctamente en la tabla " + tabla + ".");

        } catch (SQLException e) {
            System.out.println("Error al insertar usuario: " + e.getMessage());
        }
    }

    //  Método para verificar login (devuelve el objeto Usuario correcto)
    public Usuario verificarLogin(String dni, String contraseña) {
        Usuario usuario = null;

        // Se verifica en orden de prioridad
        usuario = buscarEnTabla("Administrador", dni, contraseña, "Administrador");
        if (usuario != null) {
            return usuario;
        }

        usuario = buscarEnTabla("Entrenador", dni, contraseña, "Entrenador");
        if (usuario != null) {
            return usuario;
        }

        usuario = buscarEnTabla("Cliente", dni, contraseña, "Cliente");
        return usuario;
    }

    // Método auxiliar para buscar en cada tabla
    private Usuario buscarEnTabla(String tabla, String dni, String contraseña, String tipo) {
        String sql = "SELECT * FROM " + tabla + " WHERE dni = ? AND contraseña = ?";
        try (Connection con = conexion_BdD.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, dni);
            ps.setString(2, contraseña);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                switch (tipo) {
                    case "Administrador":
                        return new Administrador(
                                rs.getString("nombre"),
                                rs.getString("apellido"),
                                rs.getInt("dni"),
                                rs.getString("contraseña")
                        );
                    case "Entrenador":
                        return new Entrenador(
                                rs.getString("nombre"),
                                rs.getString("apellido"),
                                rs.getInt("dni"),
                                rs.getString("contraseña")
                        );
                    case "Cliente":
                        return new Cliente(
                                rs.getString("nombre"),
                                rs.getString("apellido"),
                                rs.getInt("dni"),
                                rs.getString("contraseña"),
                                rs.getInt("clases"),
                                rs.getInt("idEntrenador")
                        );
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar en " + tabla + ": " + e.getMessage());
        }

        return null;
    }

    // Método para listar todos los usuarios de una tabla
    public ArrayList<Usuario> listarUsuarios(String tipo) {
        ArrayList<Usuario> lista = new ArrayList<>();
        String tabla;

        switch (tipo.toLowerCase()) {
            case "administrador":
                tabla = "Administrador";
                break;
            case "entrenador":
                tabla = "Entrenador";
                break;
            case "cliente":
                tabla = "Cliente";
                break;
            default:
                System.out.println("Tipo inválido para listar usuarios.");
                return lista;
        }

        String sql = "SELECT * FROM " + tabla;
        try (Connection con = conexion_BdD.conectar();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                switch (tipo.toLowerCase()) {
                    case "Administrador":
                        lista.add(new Administrador(
                                rs.getString("nombre"),
                                rs.getString("apellido"),
                                rs.getInt("dni"),
                                rs.getString("contraseña")));
                        break;
                    case "Entrenador":
                        lista.add(new Entrenador(
                                rs.getString("nombre"),
                                rs.getString("apellido"),
                                rs.getInt("dni"),
                                rs.getString("contraseña")));
                        break;
                    case "Cliente":
                        lista.add(new Cliente(
                                rs.getString("nombre"),
                                rs.getString("apellido"),
                                rs.getInt("dni"),
                                rs.getString("contraseña"),
                                rs.getInt("clases"),
                                rs.getInt("idEntrenador")));
                        break;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al listar usuarios de " + tabla + ": " + e.getMessage());
        }

        return lista;
    }
}
