package modelo;

import java.sql.*;
import java.util.*;

public class ProductoDao {

    // Método para insertar un nuevo producto en la base de datos
    public void agregarProducto(ProductoOtaku producto) {
        String sql = "INSERT INTO productos(nombre,categoria, precio, stock) VALUES (?,?,?,?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            // Se asignan los valores a cada signo de interrogación (placeholders)
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getCategoria());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getStock());
            stmt.executeUpdate(); // Ejecuta la inserción

        } catch (SQLException e) {
            System.out.println("ERROR AL AÑADIR PRODUCTO: " + e.getMessage());
        }
    }

    // Método para buscar un producto por su ID y devolverlo si existe
    public ProductoOtaku obtenerProductoPorId(int id) {
        String sql = "SELECT * FROM productos WHERE id= ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id); // Insertamos el id en el WHERE
            ResultSet rs = stmt.executeQuery(); // Ejecutamos la consulta

            // Si hay resultado, se construye el objeto ProductoOtaku con los datos del ResultSet
            if (rs.next()) {
                return new ProductoOtaku(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getDouble("precio"),
                    rs.getInt("stock")
                );
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL AÑADIR PRODUCTO: " + e.getMessage());
        }
        return null; // Si no encuentra nada, devuelve null
    }

    // Devuelve una lista con todos los productos guardados en la base de datos
    public List<ProductoOtaku> obtenerTodosLosProductos() {
        List<ProductoOtaku> lista = new ArrayList<ProductoOtaku>();
        String sql = "SELECT * FROM productos";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // Mientras haya resultados, creamos objetos y los añadimos a la lista
            while (rs.next()) {
                ProductoOtaku p = new ProductoOtaku(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getDouble("precio"),
                    rs.getInt("stock")
                );
                lista.add(p);
            }

        } catch (SQLException e) {
            System.out.println("ERROR AL OBTENER PRODUCTOS: " + e.getMessage());
        }

        return lista;
    }

    // Actualiza los datos de un producto según su ID
    public boolean actualizarProducto(ProductoOtaku producto) {
        String sql = "UPDATE productos SET nombre = ?, categoria= ?, precio= ?, stock = ? WHERE id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getCategoria());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getStock());
            stmt.setInt(5, producto.getId());

            return stmt.executeUpdate() > 0; // Devuelve true si se actualizó

        } catch (SQLException e) {
            System.out.println("ERROR AL ACTUALIZAR PRODUCTO: " + e.getMessage());
        }

        return false;
    }

    // Elimina un producto de la base de datos usando su ID
    public boolean eliminarProducto(int id) {
        String sql = "DELETE FROM productos WHERE id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0; // Devuelve true si se eliminó

        } catch (SQLException e) {
            System.out.println("ERROR AL ELIMINAR PRODUCTO: " + e.getMessage());
        }

        return false;
    }
}
