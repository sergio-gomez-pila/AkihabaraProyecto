package vista;

import modelo.ProductoOtaku;
import modelo.ClienteOtaku;
import java.util.List;
import java.util.Scanner;

public class InterfazConsola {
    private Scanner entrada = new Scanner(System.in); // Para leer la entrada del usuario

    // Muestra el menú principal con las opciones disponibles
    public void mostrarMenuPrincipal() {
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("1. AÑADIR PRODUCTO");
        System.out.println("2. CONSULTAR PRODUCTO POR ID");
        System.out.println("3. VER TODOS LOS PRODUCTOS");
        System.out.println("4. ACTUALIZAR PRODUCTO");
        System.out.println("5. ELIMINAR PRODUCTO");
        System.out.println("6. GENERAR DESCRIPCION CON IA");
        System.out.println("7. SUGERIR CATEGORIA CON IA");
        System.out.println("8. MOSTRAR MENU CON CLIENTES");
        System.out.println("9. SALIR");
        System.out.print("SELECCIONA UNA OPCION: ");
    }

    // Lee la opción introducida por el usuario y la convierte a int
    public int leerOpcion() {
        try {
            return Integer.parseInt(entrada.nextLine());
        } catch (NumberFormatException e) {
            return -1; // En caso de error devuelve -1 para manejarlo
        }
    }

    // Pide al usuario que introduzca los datos para crear un nuevo producto
    public ProductoOtaku pedirDatosProducto() {
        System.out.print("NOMBRE: ");
        String nombre = entrada.nextLine();

        System.out.print("CATEGORIA: ");
        String categoria = entrada.nextLine();

        System.out.print("PRECIO: ");
        double precio = Double.parseDouble(entrada.nextLine());

        System.out.print("STOCK: ");
        int stock = Integer.parseInt(entrada.nextLine());

        return new ProductoOtaku(nombre, categoria, precio, stock);
    }

    // Pide el ID de un producto para hacer búsquedas o modificaciones
    public int pedirIdProducto() {
        System.out.print("ID DEL PRODUCTO: ");
        return Integer.parseInt(entrada.nextLine());
    }

    // Pide nuevos datos para un producto ya existente (actualización)
    // Si el usuario no escribe nada, se deja el valor anterior
    public ProductoOtaku pedirNuevosDatos(ProductoOtaku producto) {
        System.out.println("EDITANDO PRODUCTO: " + producto.getNombre());

        System.out.print("NUEVO NOMBRE (" + producto.getNombre() + "): ");
        String nombre = entrada.nextLine();
        if (!nombre.isEmpty()) producto.setNombre(nombre);

        System.out.print("NUEVA CATEGORIA (" + producto.getCategoria() + "): ");
        String categoria = entrada.nextLine();
        if (!categoria.isEmpty()) producto.setCategoria(categoria);

        System.out.print("NUEVO PRECIO (" + producto.getPrecio() + "): ");
        String precioStr = entrada.nextLine();
        if (!precioStr.isEmpty()) producto.setPrecio(Double.parseDouble(precioStr));

        System.out.print("NUEVO STOCK (" + producto.getStock() + "): ");
        String stockStr = entrada.nextLine();
        if (!stockStr.isEmpty()) producto.setStock(Integer.parseInt(stockStr));

        return producto;
    }

    // Muestra en consola los datos de un producto específico
    public void mostrarProducto(ProductoOtaku producto) {
        if (producto != null) {
            System.out.println("\nID: " + producto.getId());
            System.out.println("NOMBRE: " + producto.getNombre());
            System.out.println("CATEGORIA: " + producto.getCategoria());
            System.out.println("PRECIO: " + producto.getPrecio());
            System.out.println("STOCK: " + producto.getStock());
        } else {
            System.out.println("PRODUCTO NO ENCONTRADO.");
        }
    }

    // Muestra todos los productos en formato tabla/resumen
    public void mostrarListaProductos(List<ProductoOtaku> lista) {
        System.out.println("\n--- INVENTARIO ---");
        for (ProductoOtaku p : lista) {
            System.out.println("ID: " + p.getId() + " | " + p.getNombre() + " | " + p.getCategoria()
                + " | €" + p.getPrecio() + " | Stock: " + p.getStock());
        }
    }

    // Muestra un mensaje cualquiera (éxito, error, etc.)
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    // Pide al usuario un nombre libre (se usa para IA o nuevas creaciones)
    public String pedirNombreLibre() {
        System.out.print("INGRESA EL NOMBRE DEL NUEVO PRODUCTO: ");
        return entrada.nextLine();
    }
 // CLIENTES - Pide los datos para registrar un nuevo cliente
    public ClienteOtaku pedirDatosCliente() {
        System.out.print("NOMBRE: ");
        String nombre = entrada.nextLine();

        System.out.print("EMAIL: ");
        String email = entrada.nextLine();

        System.out.print("TELEFONO: ");
        String telefono = entrada.nextLine();

        return new ClienteOtaku(nombre, email, telefono);
    }

    // CLIENTES - Pide el ID del cliente
    public int pedirIdCliente() {
        System.out.print("ID DEL CLIENTE: ");
        return Integer.parseInt(entrada.nextLine());
    }

    // CLIENTES - Pide nuevos datos para actualizar un cliente
    public ClienteOtaku pedirNuevosDatosCliente(ClienteOtaku cliente) {
        System.out.println("EDITANDO CLIENTE: " + cliente.getNombre());

        System.out.print("NUEVO NOMBRE (" + cliente.getNombre() + "): ");
        String nombre = entrada.nextLine();
        if (!nombre.isEmpty()) cliente.setNombre(nombre);

        System.out.print("NUEVO EMAIL (" + cliente.getEmail() + "): ");
        String email = entrada.nextLine();
        if (!email.isEmpty()) cliente.setEmail(email);

        System.out.print("NUEVO TELEFONO (" + cliente.getTelefono() + "): ");
        String telefono = entrada.nextLine();
        if (!telefono.isEmpty()) cliente.setTelefono(telefono);

        return cliente;
    }

    // CLIENTES - Muestra los datos de un cliente
    public void mostrarCliente(ClienteOtaku cliente) {
        if (cliente != null) {
            System.out.println("\nID: " + cliente.getId());
            System.out.println("NOMBRE: " + cliente.getNombre());
            System.out.println("EMAIL: " + cliente.getEmail());
            System.out.println("TELEFONO: " + cliente.getTelefono());
            System.out.println("FECHA REGISTRO: " + cliente.getFechaRegistro());
        } else {
            System.out.println("CLIENTE NO ENCONTRADO.");
        }
    }

    // CLIENTES - Muestra la lista de clientes
    public void mostrarListaClientes(List<ClienteOtaku> lista) {
        System.out.println("\n--- LISTA DE CLIENTES ---");
        for (ClienteOtaku c : lista) {
            System.out.println("ID: " + c.getId() + " | " + c.getNombre() + " | " + c.getEmail() + " | " + c.getTelefono() + " | " + c.getFechaRegistro());
        }
    }

    
}

