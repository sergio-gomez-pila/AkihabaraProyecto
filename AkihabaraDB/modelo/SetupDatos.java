package modelo;

import java.util.List;

public class SetupDatos {

    // Este método se encarga de insertar algunos productos de ejemplo
    // Solo se ejecuta si la tabla está vacía (o sea, no hay ningún producto aún)
    public static void insertarSiBaseVacia(ProductoDao dao) {
        List<ProductoOtaku> productos = dao.obtenerTodosLosProductos(); // Traemos todos los productos

        if (productos.isEmpty()) {
            // Si no hay productos, insertamos 3 de prueba
            dao.agregarProducto(new ProductoOtaku("Figura de Anya Forger", "Figura", 59.95, 8));
            dao.agregarProducto(new ProductoOtaku("Manga Chainsaw Man Vol.1", "Manga", 9.99, 20));
            dao.agregarProducto(new ProductoOtaku("Póster Studio Ghibli Colección", "Póster", 15.50, 15));
            System.out.println("⚡ PRODUCTOS DE EJEMPLO INSERTADOS ⚡");
        } else {
            // Si ya hay productos, simplemente avisamos
            System.out.println("🔎 Ya hay productos en la base de datos.");
        }
    }
}
