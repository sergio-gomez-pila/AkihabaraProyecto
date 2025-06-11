package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Datos de conexión a la base de datos (URL, usuario y contraseña)
    // Cambia estos valores si usas otra configuración en MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/akihabara_db";
    private static final String USUARIO = "root";
    private static final String CONTRASEÑA = "root";

    // Este método devuelve una conexión activa a la base de datos
    // Se puede reutilizar desde cualquier clase que necesite conectarse
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
    }

    // Método main para probar que la conexión funciona correctamente
    // Se puede ejecutar solo para comprobar si todo está bien configurado
    public static void main(String[] args) {
        try (Connection conexion = getConnection()) {
            System.out.println("¡Conexión exitosa!");
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
    }
}

