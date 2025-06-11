package vista.gui;

import modelo.ProductoDao;

import modelo.ProductoOtaku;

import javax.swing.*;
import java.awt.*;

public class FormularioProducto extends JPanel {
    private static final long serialVersionUID = 1L;

    // Campos de texto para los datos del producto
    private JTextField txtId, txtNombre, txtCategoria, txtPrecio, txtStock;
    private ProductoDao dao; // Acceso a la base de datos

    // Constructor del formulario
    public FormularioProducto() {
        dao = new ProductoDao(); // Inicializamos el DAO
        setLayout(new BorderLayout()); // Usamos BorderLayout para dividir la ventana

        // Título superior
        JLabel titulo = new JLabel("Gestión de Producto", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        // Panel del formulario (centro)
        JPanel panelForm = new JPanel(new GridLayout(6, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40)); // Margen

        // Campos del formulario
        panelForm.add(new JLabel("ID del producto:"));
        txtId = new JTextField();
        panelForm.add(txtId);

        panelForm.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelForm.add(txtNombre);

        panelForm.add(new JLabel("Categoría:"));
        txtCategoria = new JTextField();
        panelForm.add(txtCategoria);

        panelForm.add(new JLabel("Precio:"));
        txtPrecio = new JTextField();
        panelForm.add(txtPrecio);

        panelForm.add(new JLabel("Stock:"));
        txtStock = new JTextField();
        panelForm.add(txtStock);

        // Panel con los botones CRUD
        JPanel panelBotones = new JPanel(new GridLayout(1, 3, 10, 0));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");

        // Asignamos la lógica a cada botón
        btnGuardar.addActionListener(e -> guardarProducto());
        btnActualizar.addActionListener(e -> actualizarProducto());
        btnEliminar.addActionListener(e -> eliminarProducto());

        // Añadimos los botones al panel
        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);

        // Añadimos el formulario y los botones al panel principal
        add(panelForm, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    // Lógica para guardar un nuevo producto
    private void guardarProducto() {
        try {
            String nombre = txtNombre.getText();
            String categoria = txtCategoria.getText();
            double precio = Double.parseDouble(txtPrecio.getText());
            int stock = Integer.parseInt(txtStock.getText());

            // Creamos el objeto con los datos y lo mandamos al DAO
            ProductoOtaku producto = new ProductoOtaku(nombre, categoria, precio, stock);
            dao.agregarProducto(producto);
            JOptionPane.showMessageDialog(this, " Producto añadido.");
            limpiarCampos(); // Limpiamos después de guardar

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, " Error al guardar: " + e.getMessage());
        }
    }

    // Lógica para actualizar un producto existente
    private void actualizarProducto() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String nombre = txtNombre.getText();
            String categoria = txtCategoria.getText();
            double precio = Double.parseDouble(txtPrecio.getText());
            int stock = Integer.parseInt(txtStock.getText());

            ProductoOtaku producto = new ProductoOtaku(id, nombre, categoria, precio, stock);
            boolean ok = dao.actualizarProducto(producto);

            if (ok) {
                JOptionPane.showMessageDialog(this, " Producto actualizado.");
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, " No se encontró el producto.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, " Error al actualizar: " + e.getMessage());
        }
    }

    // Lógica para eliminar un producto por su ID
    private void eliminarProducto() {
        try {
            int id = Integer.parseInt(txtId.getText());

            // Confirmación antes de eliminar
            int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro que quieres eliminar este producto?",
                    "Confirmación", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                boolean eliminado = dao.eliminarProducto(id);
                if (eliminado) {
                    JOptionPane.showMessageDialog(this, " Producto eliminado.");
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(this, " No se encontró el producto.");
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, " Error al eliminar: " + e.getMessage());
        }
    }

    // Limpia todos los campos del formulario
    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtCategoria.setText("");
        txtPrecio.setText("");
        txtStock.setText("");
    }
}

