package controlador;

public class MainApp {

    // Punto de entrada de la aplicación
    public static void main(String[] args) {
        // Creamos el menú principal (esto inicializa todo el sistema)
        MenuPrincipal menu = new MenuPrincipal();

        // Mostramos el menú y dejamos que el usuario interactúe
        menu.mostrar();
    }
}
