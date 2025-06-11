package vista.gui;

import modelo.LlmService;
import modelo.ProductoDao;
import modelo.ProductoOtaku;

import javax.swing.*;

public class PanelIA extends JPanel {
    private static final long serialVersionUID = 1L;

    private ProductoDao dao;
    private LlmService llm;
    private JTextArea txtResultado;

    public PanelIA() {
        dao = new ProductoDao();
        llm = new LlmService();
        txtResultado = new JTextArea(); // Para poder usarla si decides mostrar algo
    }

    protected void generarDescripcion(int id) {
        ProductoOtaku producto = dao.obtenerProductoPorId(id);
        if (producto != null) {
            String prompt = "Genera una descripcion de marketing breve y atractiva para el producto otaku: "
                    + producto.getNombre() + " de la categoria " + producto.getCategoria() + ".";
            String respuesta = llm.preguntarLLM(prompt);
            txtResultado.setText("📝 Descripción:\n" + respuesta);
        } else {
            txtResultado.setText("❌ Producto no encontrado con ese ID.");
        }
    }

    protected void sugerirCategoria(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            txtResultado.setText(" Escribe un nombre de producto para sugerir categoría.");
            return;
        }

        String prompt = "Para un producto otaku llamado '" + nombre +
                "', sugiere una categoria adecuada de esta lista: Figura, Manga, Póster, Llavero, Ropa, Videojuego, Otro.";
        String respuesta = llm.preguntarLLM(prompt);
        txtResultado.setText("Categoría sugerida:\n" + respuesta);
    }

    public String getResultado() {
        return txtResultado.getText();
    }
}
