package vista.gui;

import modelo.ProductoDao;

import modelo.ProductoOtaku;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProductoPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    // Tabla y su modelo para mostrar los productos
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;

    public ProductoPanel() {
        setLayout(new BorderLayout()); // Layout principal del panel

        // Título que aparece en la parte superior
        JLabel titulo = new JLabel("Inventario de Productos", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        // Definimos las columnas de la tabla
        String[] columnas = {"ID", "Nombre", "Categoría", "Precio", "Stock"};
        modeloTabla = new DefaultTableModel(columnas, 0); // 0 filas al principio
        tablaProductos = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaProductos); // Scroll para la tabla
        add(scrollPane, BorderLayout.CENTER); // Colocamos la tabla en el centro

        // Cargamos los productos al iniciar el panel
        cargarDatos();
    }

    // Método que obtiene los productos desde la base de datos y los carga en la tabla
    private void cargarDatos() {
        modeloTabla.setRowCount(0); // Limpiamos la tabla antes de cargar nuevos datos
        ProductoDao dao = new ProductoDao(); // DAO para acceder a la BD
        List<ProductoOtaku> lista = dao.obtenerTodosLosProductos(); // Traemos todos los productos

        // Recorremos cada producto y lo añadimos como fila a la tabla
        for (ProductoOtaku p : lista) {
            Object[] fila = {
                p.getId(),
                p.getNombre(),
                p.getCategoria(),
                p.getPrecio(),
                p.getStock()
            };
            modeloTabla.addRow(fila);
        }
    }
}

