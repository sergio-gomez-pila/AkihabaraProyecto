
===============================
PROYECTO: AKIHABARA MARKET
===============================
 Desarrollado como reto de prácticas DAM/DAW - SEIDOR

--------------------------------
1. CONFIGURAR LA BASE DE DATOS
--------------------------------

 Requisitos previos:
- Tener MySQL instalado y corriendo.
- Tener un usuario (por ejemplo: root) con permisos para crear base de datos y tablas.

 Pasos:
1. Abre tu herramienta de gestión (como MySQL Workbench o phpMyAdmin) o consola.
2. Ejecuta este script SQL para crear la base de datos y la tabla productos:

CREATE DATABASE IF NOT EXISTS akihabara_db;

USE akihabara_db;

CREATE TABLE IF NOT EXISTS productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    categoria ENUM('Figura','Manga','Póster','Llavero','Ropa','Videojuego','Otro') NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL
);

 *Nota:* Puedes cambiar el nombre de usuario y contraseña en `DatabaseConnection.java` si no estás usando root/root.


----------------------------------------
2. CONFIGURAR LA API KEY DE OPENROUTER
----------------------------------------

¿Para qué sirve?
Usamos OpenRouter.ai para integrar una IA que genera descripciones y sugiere categorías.

 Pasos:
1. Entra a https://openrouter.ai y crea una cuenta (gratis).
2. Una vez dentro, ve a tu perfil y copia tu API KEY personal.
3. En tu proyecto Java, crea un archivo llamado config.properties en la raíz del proyecto (al mismo nivel que src).
4. Dentro de config.properties, pega esto:

openrouter.key=TU_API_KEY_AQUI

 No subas este archivo a GitHub si es público, ya que contiene tu clave privada.


-------------------------------
3. COMPILAR Y EJECUTAR EL PROYECTO
-------------------------------

 Este proyecto está hecho en Java, se puede usar con Eclipse, IntelliJ o desde consola.

Desde Eclipse o IntelliJ:
- Importa el proyecto como carpeta existente.
- Asegúrate de tener el .jar de GSON en tu ruta de librerías externas.
- Ejecuta MainApp.java que está dentro del paquete controlador.

 Desde consola:
1. Compila:

javac -cp ".;lib/gson-2.10.1.jar" src/**/*.java

2. Ejecuta:

java -cp ".;lib/gson-2.10.1.jar;src" controlador.MainApp

*Reemplaza ; por : si usas Linux o macOS.*

-----------------------------------------
4. FUNCIONALIDADES Y ESTRUCTURA DE CLASES
-----------------------------------------

 Funcionalidades:
- Añadir, buscar, actualizar y eliminar productos otaku.
- Mostrar inventario completo.
- Usar una IA para generar descripciones automáticas.
- Sugerencia de categoría para un nuevo producto vía IA.
- Inserción automática de datos de prueba si la base está vacía.

 Estructura general:

- modelo  
  - ProductoOtaku: clase que representa cada producto.
  - ProductoDao: clase que hace operaciones con la base de datos (CRUD).
  - ClienteOtaku: clase que representa cada cliente.
  - ClienteDao: clase que hace operaciones con la base de datos (CRUD).
  - DatabaseConnection: clase para conectar a MySQL.
  - LlmService: clase que se comunica con la API de OpenRouter.
  - SetupDatos: inserta datos de ejemplo si no hay productos.

- vista  
  - InterfazConsola: se encarga de mostrar el menú y pedir datos al usuario.

- controlador  
  - MenuPrincipal: organiza todo el flujo del menú y las operaciones.
  - MainApp: clase principal que lanza la aplicación.

README - Interfaz Gráfica (vista.gui)
=====================================

 CLASES INCLUIDAS
--------------------

1. **VentanaPrincipal.java**
   - Es la ventana principal del sistema GUI.
   - Contiene botones para acceder a funcionalidades de productos, clientes e IA.
   - Abre otras ventanas como formularios o tablas según la opción seleccionada.

2. **FormularioProducto.java**
   - Panel con campos (`JTextField`) y botones (`JButton`) para añadir, actualizar o eliminar productos.
   - Utiliza `ProductoDAO` para acceder a la base de datos.
   - Valida la entrada del usuario y muestra mensajes con `JOptionPane`.

3. **ProductoPanel.java**
   - Muestra todos los productos en una tabla (`JTable`).
   - Tiene un botón para actualizar la tabla con los últimos datos.
   - Se conecta a `ProductoDAO`.

4. **PanelIA.java**
   - Integra funciones de inteligencia artificial (IA) usando OpenRouter.
   - Permite generar una descripción de producto o sugerir una categoría.
   - Se conecta a `LlmService`.

5. **FormularioCliente.java**
   - Similar al de productos, pero para clientes.
   - Permite insertar, actualizar y eliminar registros en la tabla `clientes`.
   - Trabaja con `ClienteDAO`.

6. **ClientesPanel.java**
   - Muestra todos los clientes registrados en una `JTable`.
   - Facilita la visualización de datos sin necesidad de consola.

7. **MainGUI.java**
   - Punto de entrada del programa si se desea iniciar en modo gráfico.
   - Solo contiene el `main()` que lanza `VentanaPrincipal`.

 REQUISITOS
--------------
- Java 17 o superior
- Conexión a base de datos MySQL (ver clase `DatabaseConnection`)
- Archivo `config.properties` en la raíz del proyecto con la API Key de OpenRouter
- Librería `gson` para parseo de JSON (requerida por `LlmService`)





