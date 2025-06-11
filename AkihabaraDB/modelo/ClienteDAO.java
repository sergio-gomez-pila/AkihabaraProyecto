 package modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    // Este método guarda un nuevo cliente en la base de datos
    public void agregarCliente(ClienteOtaku cliente) {
        String sql = "INSERT INTO clientes (nombre, email, telefono) VALUES (?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection(); // Conexión a la BD
             PreparedStatement stmt = con.prepareStatement(sql)) { // Preparamos el SQL
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getTelefono());
            stmt.executeUpdate(); // Ejecutamos la inserción
        } catch (SQLException e) {
            System.out.println("ERROR al agregar cliente: " + e.getMessage());
        }
    }

    // Busca un cliente por su ID y lo devuelve como objeto ClienteOtaku
    public ClienteOtaku obtenerClientePorId(int id) {
        String sql = "SELECT * FROM clientes WHERE id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id); // Asignamos el ID al SQL
            ResultSet rs = stmt.executeQuery(); // Ejecutamos la consulta

            if (rs.next()) { // Si encuentra un resultado
                return new ClienteOtaku(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("telefono"),
                        rs.getDate("fecha_registro")
                );
            }
        } catch (SQLException e) {
            System.out.println(" ERROR al buscar cliente: " + e.getMessage());
        }
        return null; // Si no encuentra nada, devuelve null
    }

    // Devuelve una lista con todos los clientes registrados en la BD
    public List<ClienteOtaku> obtenerTodosLosClientes() {
        List<ClienteOtaku> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ClienteOtaku c = new ClienteOtaku(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("telefono"),
                        rs.getDate("fecha_registro")
                );
                lista.add(c); // Lo añadimos a la lista
            }
        } catch (SQLException e) {
            System.out.println("ERROR al obtener clientes: " + e.getMessage());
        }
        return lista; // Devuelve la lista completa
    }

    // Actualiza los datos de un cliente
    public boolean actualizarCliente(ClienteOtaku cliente) {
        String sql = "UPDATE clientes SET nombre = ?, email = ?, telefono = ? WHERE id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getTelefono());
            stmt.setInt(4, cliente.getId());
            return stmt.executeUpdate() > 0; // Devuelve true si se actualizó
        } catch (SQLException e) {
            System.out.println("ERROR al actualizar cliente: " + e.getMessage());
            return false;
        }
    }

    // Elimina un cliente de la BD por su ID
    public boolean eliminarCliente(int id) {
        String sql = "DELETE FROM clientes WHERE id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0; // Devuelve true si se eliminó
        } catch (SQLException e) {
            System.out.println("❌ ERROR al eliminar cliente: " + e.getMessage());
            return false;
        }
    }

    // Busca un cliente por su correo electrónico
    public ClienteOtaku buscarPorEmail(String email) {
        String sql = "SELECT * FROM clientes WHERE email = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new ClienteOtaku(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("telefono"),
                        rs.getDate("fecha_registro")
                );
            }
        } catch (SQLException e) {
            System.out.println("ERROR al buscar cliente por email: " + e.getMessage());
        }
        return null;
    }
}

