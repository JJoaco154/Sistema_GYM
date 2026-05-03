package persistencia;

import java.sql.*;

public class conexion_BdD {

    static String url = "jdbc:mysql://localhost:3306/Sistema_Gimnasio";
    static String user = "root";
    static String pass = "root";

    public static Connection conectar() {
        Connection con = null;

        try {
            // Cargar el driver de MySQL (opcional con JDBC 4+)
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(url, user, pass);
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (ClassNotFoundException e) {
            System.out.println("No se encontró el driver JDBC.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos.");
            e.printStackTrace();
        }

        return con;
    }
    
    // Método para cerrar conexión
    public static void cerrarConexion(Connection con) {
        if (con != null) {
            try {
                con.close();
                System.out.println("Conexión cerrada correctamente.");
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión.");
                e.printStackTrace();
            }
        }
    }
    
}
