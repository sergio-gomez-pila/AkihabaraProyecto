package controlador;

import modelo.*;
import vista.InterfazConsola;

public class MenuPrincipal {
    private InterfazConsola consola;
    private ProductoDao dao;
    private LlmService llm;

    // Constructor: inicializa todo y carga datos de prueba si no hay productos
    public MenuPrincipal() {
        this.consola = new InterfazConsola(); // Para interactuar con el usuario por consola
        this.dao = new ProductoDao(); // Acceso a los métodos CRUD de productos
        this.llm = new LlmService(); // Servicio de IA conectado a OpenRouter
        SetupDatos.insertarSiBaseVacia(dao); // Cargamos productos de ejemplo si la tabla está vacía
    }

    // Método principal que lanza el menú en bucle
    public void mostrar() {
        boolean salir = false;
        while (!salir) {
            consola.mostrarMenuPrincipal(); // Mostramos el menú en pantalla
            int opcion = consola.leerOpcion(); // Leemos la opción que elige el usuario

            switch (opcion) {
                case 1:
                    // AÑADIR producto nuevo
                    ProductoOtaku nuevo = consola.pedirDatosProducto();
                    dao.agregarProducto(nuevo);
                    consola.mostrarMensaje("PRODUCTO AÑADIDO.");
                    break;

                case 2:
                    // CONSULTAR producto por ID
                    int idBuscar = consola.pedirIdProducto();
                    ProductoOtaku buscado = dao.obtenerProductoPorId(idBuscar);
                    consola.mostrarProducto(buscado);
                    break;

                case 3:
                    // MOSTRAR todos los productos del inventario
                    consola.mostrarListaProductos(dao.obtenerTodosLosProductos());
                    break;

                case 4:
                    // ACTUALIZAR producto existente
                    int idActualizar = consola.pedirIdProducto();
                    ProductoOtaku actual = dao.obtenerProductoPorId(idActualizar);
                    if (actual != null) {
                        ProductoOtaku editado = consola.pedirNuevosDatos(actual);
                        dao.actualizarProducto(editado);
                        consola.mostrarMensaje("PRODUCTO ACTUALIZADO.");
                    } else {
                        consola.mostrarMensaje("NO EXISTE ESE PRODUCTO.");
                    }
                    break;

                case 5:
                    // ELIMINAR producto por ID
                    int idEliminar = consola.pedirIdProducto();
                    if (dao.eliminarProducto(idEliminar)) {
                        consola.mostrarMensaje("PRODUCTO ELIMINADO.");
                    } else {
                        consola.mostrarMensaje("NO EXISTE ESE PRODUCTO.");
                    }
                    break;

                case 6:
                    // GENERAR DESCRIPCIÓN con IA
                    int idIA = consola.pedirIdProducto();
                    ProductoOtaku prodIA = dao.obtenerProductoPorId(idIA);
                    if (prodIA != null) {
                        String prompt = "Genera una descripcion en español de marketing breve y atractiva para el producto otaku: "
                                + prodIA.getNombre() + " de la categoria " + prodIA.getCategoria() + ".";
                        String respuesta = llm.preguntarLLM(prompt);
                        consola.mostrarMensaje("DESCRIPCION GENERADA POR LA IA:");
                        consola.mostrarMensaje(respuesta);
                    } else {
                        consola.mostrarMensaje("NO EXISTE ESE PRODUCTO.");
                    }
                    break;

                case 7:
                    // SUGERIR CATEGORÍA con IA
                    String nombreNuevo = consola.pedirNombreLibre();
                    String promptCat = "Para un producto otaku llamado '" + nombreNuevo +
                        "', sugiere una categoria adecuada de esta lista: Figura, Manga, Póster, Llavero, Ropa, Videojuego, Otro.";
                    String respuestaCat = llm.preguntarLLM(promptCat);
                    consola.mostrarMensaje("CATEGORIA SUGERIDA POR LA IA:");
                    consola.mostrarMensaje(respuestaCat);
                    break;
                case 8:
                		mostrarMenuClientes();
                    break;


                case 9:
                    // SALIR de la aplicación
                    consola.mostrarMensaje("Hata pronto, pequeño Sensei...");
                    salir = true;
                    break;
            }
        }
    }
 // Método para gestionar las operaciones de clientes
    public void mostrarMenuClientes() {
        ClienteDAO clienteDao = new ClienteDAO();  // Creamos una instancia de ClienteDAO para interactuar con la base de datos
        boolean volver = false;  // Variable para controlar si volvemos al menú principal o no

        // Bucle principal que muestra el menú y sigue ejecutándose hasta que el usuario elija salir
        while (!volver) {
            System.out.println("\n--- MENU CLIENTES ---");
            // Opciones para gestionar clientes
            System.out.println("1. AÑADIR CLIENTE");
            System.out.println("2. CONSULTAR CLIENTE POR ID");
            System.out.println("3. VER TODOS LOS CLIENTES");
            System.out.println("4. ACTUALIZAR CLIENTE");
            System.out.println("5. ELIMINAR CLIENTE");
            System.out.println("6. VOLVER AL MENU PRINCIPAL");
            System.out.print("SELECCIONA UNA OPCION: ");
            
            // Leemos la opción que el usuario seleccionó
            int opcion = consola.leerOpcion();

            // Realizamos la acción correspondiente según la opción seleccionada
            switch (opcion) {
                case 1:
                    // AÑADIR CLIENTE: Pedimos los datos del cliente y lo agregamos a la base de datos
                    ClienteOtaku nuevoCliente = consola.pedirDatosCliente();
                    clienteDao.agregarCliente(nuevoCliente);  // Guardamos el cliente
                    consola.mostrarMensaje("CLIENTE AÑADIDO.");
                    break;

                case 2:
                    // CONSULTAR CLIENTE POR ID: Pedimos el ID del cliente y mostramos los datos
                    int idBuscar = consola.pedirIdCliente();
                    ClienteOtaku buscado = clienteDao.obtenerClientePorId(idBuscar);
                    consola.mostrarCliente(buscado);  // Mostramos el cliente encontrado
                    break;

                case 3:
                    // VER TODOS LOS CLIENTES: Mostramos todos los clientes registrados
                    consola.mostrarListaClientes(clienteDao.obtenerTodosLosClientes());
                    break;

                case 4:
                    // ACTUALIZAR CLIENTE: Pedimos el ID y los nuevos datos para actualizar el cliente
                    int idActualizar = consola.pedirIdCliente();
                    ClienteOtaku clienteActual = clienteDao.obtenerClientePorId(idActualizar);
                    if (clienteActual != null) {
                        // Si el cliente existe, pedimos nuevos datos y actualizamos en la base de datos
                        ClienteOtaku editado = consola.pedirNuevosDatosCliente(clienteActual);
                        clienteDao.actualizarCliente(editado);
                        consola.mostrarMensaje("CLIENTE ACTUALIZADO.");
                    } else {
                        consola.mostrarMensaje("NO EXISTE ESE CLIENTE.");
                    }
                    break;

                case 5:
                    // ELIMINAR CLIENTE: Pedimos el ID y eliminamos el cliente de la base de datos
                    int idEliminar = consola.pedirIdCliente();
                    if (clienteDao.eliminarCliente(idEliminar)) {
                        consola.mostrarMensaje("CLIENTE ELIMINADO.");
                    } else {
                        consola.mostrarMensaje("NO EXISTE ESE CLIENTE.");
                    }
                    break;

                case 6:
                    // VOLVER AL MENU PRINCIPAL: Cambiamos la variable para salir del bucle
                    volver = true;
                    break;

                default:
                    // Si el usuario introduce una opción inválida
                    consola.mostrarMensaje("OPCIÓN NO VÁLIDA.");
            }
        }
    }
}
 

