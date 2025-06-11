package vista.gui;

import modelo.ProductoDao;
import modelo.ClienteDAO;
import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    private static final long serialVersionUID = 1L;

    // Constructor principal donde montamos toda la interfaz
    public VentanaPrincipal() {
        setTitle("Akihabara Market - Gestión de Inventario"); // Título de la ventana
        setSize(800, 600); // Tamaño inicial de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cierra el programa al cerrar la ventana
        setLocationRelativeTo(null); // Centra la ventana en la pantalla

        // Panel principal para añadir todos los componentes
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());

        // Título grande en la parte superior
        JLabel titulo = new JLabel("Bienvenido a Akihabara Market", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        // Panel con botones organizados en rejilla (3 filas x 2 columnas)
        JPanel panelBotones = new JPanel(new GridLayout(4, 2, 10, 10));

        // Botones de la interfaz
        JButton btnVer = new JButton("Ver productos");
        JButton btnAgregar = new JButton("Añadir producto");
        JButton btnActualizar = new JButton("Actualizar producto");
        JButton btnEliminar = new JButton("Eliminar producto");
        JButton btnIA = new JButton("Descripción con IA");
        JButton btnCategoria = new JButton("Sugerir categoría");
        JButton btnVerClientes = new JButton("Ver clientes");
        JButton btnAñadir = new JButton("Añadir cliente");
        JButton btnEliminarCliente = new JButton("Eliminar cliente");

        // Acción al pulsar "Ver productos" → abre la ventana con la tabla de productos
        btnVer.addActionListener(e -> {
            JFrame ventanaTabla = new JFrame("Inventario de Productos");
            ventanaTabla.setSize(700, 400);
            ventanaTabla.setLocationRelativeTo(null); // Centra la ventana
            ventanaTabla.add(new ProductoPanel()); // Añade el panel con la tabla
            ventanaTabla.setVisible(true); // Muestra la ventana
        });

        // Acción al pulsar "Añadir producto" → abre el formulario para añadir un producto
        btnAgregar.addActionListener(e -> {
            JFrame ventanaFormulario = new JFrame("Añadir un nuevo Producto");
            ventanaFormulario.setSize(450, 400);
            ventanaFormulario.setLocationRelativeTo(null); // Centrar la nueva ventana
            ventanaFormulario.add(new FormularioProducto()); // Añadimos el formulario al frame
            ventanaFormulario.setVisible(true); // Mostramos la ventana
        });

        // Acción al pulsar "Actualizar producto" → abre el formulario para actualizar un producto
        btnActualizar.addActionListener(e -> {
            JFrame actualizarProducto = new JFrame("Actualizar Producto");
            actualizarProducto.setSize(450, 400);
            actualizarProducto.setLocationRelativeTo(null); // Centrar la nueva ventana
            actualizarProducto.add(new FormularioProducto()); // Añadimos el formulario al frame
            actualizarProducto.setVisible(true); // Mostramos la ventana
        });

        // Acción al pulsar "Eliminar producto" → pide el ID y elimina el producto
        btnEliminar.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog("Introduce el ID del producto a eliminar:");

            if (idStr != null) {
                try {
                    int id = Integer.parseInt(idStr);
                    ProductoDao dao = new ProductoDao();
                    boolean eliminado = dao.eliminarProducto(id);

                    if (eliminado) {
                        JOptionPane.showMessageDialog(null, "Producto eliminado correctamente.");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontró el producto.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "El ID debe ser un número.");
                }
            }
        });

        // Acción al pulsar "Descripción con IA" → pide el ID del producto y genera su descripción con IA
        btnIA.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog("Introduce el ID del producto:");

            if (idStr != null && !idStr.trim().isEmpty()) {
                try {
                    int id = Integer.parseInt(idStr);
                    PanelIA panel = new PanelIA(); // creamos el objeto
                    panel.generarDescripcion(id); // ejecutamos el método con el ID

                    JOptionPane.showMessageDialog(null, panel.getResultado()); // mostramos el resultado

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "El ID debe ser un número válido.");
                }
            }
        });

        // Acción al pulsar "Sugerir categoría" → pide el nombre del producto y genera su categoría con IA
        btnCategoria.addActionListener(e -> {
            String nombre = JOptionPane.showInputDialog("Nombre del nuevo producto:");
            if (nombre != null && !nombre.trim().isEmpty()) {
                PanelIA panel = new PanelIA();
                panel.sugerirCategoria(nombre);
                JOptionPane.showMessageDialog(null, panel.getResultado());
            }
        });

        // Acción al pulsar "Ver clientes" → abre la ventana para gestionar los clientes
        btnVerClientes.addActionListener(e -> {
            JFrame ventanaClientes = new JFrame("Gestión de Clientes");
            ventanaClientes.setSize(800, 600);
            ventanaClientes.setLocationRelativeTo(null);
            ventanaClientes.add(new ClientesPanel());  // Agregamos el panel que contiene la tabla
            ventanaClientes.setVisible(true);
        });

        // Llamar al formulario para añadir un nuevo cliente
        btnAñadir.addActionListener(e -> {
            JFrame ventanaFormulario = new JFrame("Añadir Cliente");
            ventanaFormulario.setSize(400, 300);
            ventanaFormulario.setLocationRelativeTo(null);
            ventanaFormulario.add(new FormularioCliente(null));  // null porque es para añadir
            ventanaFormulario.setVisible(true);
        });

        // Llamar al formulario para eliminar un cliente
        btnEliminarCliente.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog("Introduce el ID del cliente a eliminar:");
            if (idStr != null && !idStr.trim().isEmpty()) {
                try {
                    int id = Integer.parseInt(idStr);
                    ClienteDAO clienteDao = new ClienteDAO();
                    boolean eliminado = clienteDao.eliminarCliente(id);

                    if (eliminado) {
                        JOptionPane.showMessageDialog(null, "Cliente eliminado correctamente.");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontró el cliente.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "El ID debe ser un número válido.");
                }
            }
        });



        // Añadimos todos los botones al panel de botones (en el orden deseado)
        panelBotones.add(btnVer);
        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnIA);
        panelBotones.add(btnCategoria);
        panelBotones.add(btnVerClientes);
        panelBotones.add(btnAñadir);
        panelBotones.add(btnEliminarCliente);  // Botón para eliminar cliente

        // Colocamos el panel de botones en el centro del panel principal
        panelPrincipal.add(panelBotones, BorderLayout.CENTER);

        // Finalmente, cargamos todo el contenido en la ventana principal (JFrame)
        this.getContentPane().add(panelPrincipal);
    }

    // Método para obtener el cliente seleccionado en la tabla de clientes
}


