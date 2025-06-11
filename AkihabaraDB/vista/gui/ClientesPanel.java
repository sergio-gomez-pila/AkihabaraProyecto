package vista.gui;

import modelo.ClienteDAO;
import modelo.ClienteOtaku;


import javax.swing.*;
import java.awt.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class ClientesPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private JTable tablaClientes; // La tabla para mostrar los clientes
    private DefaultTableModel modeloTabla; // Modelo de la tabla


    public ClientesPanel() {
    	 setLayout(new BorderLayout()); // Layout principal del panel
        // Título del panel
        JLabel titulo = new JLabel("Gestión de Clientes", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        // Crear la tabla de clientes
        String[] columnas = {"ID", "Nombre", "Email", "Teléfono", "Fecha de Registro"};
        modeloTabla = new DefaultTableModel(columnas, 0);  // Inicializa el modelo con las columnas
        tablaClientes = new JTable(modeloTabla);  // Creamos la tabla
        // Añadimos la tabla dentro de un JScrollPane (para que sea desplazable)
        JScrollPane scrollPane = new JScrollPane(tablaClientes);
        add(scrollPane, BorderLayout.CENTER);

      cargarDatos();
    }
    // Método que obtiene los productos desde la base de datos y los carga en la tabla
    private void cargarDatos() {
        modeloTabla.setRowCount(0); // Limpiamos la tabla antes de cargar nuevos datos
        ClienteDAO dao = new ClienteDAO(); // DAO para acceder a la BD
        List<ClienteOtaku> lista = dao.obtenerTodosLosClientes(); // Traemos todos los productos

        // Recorremos cada producto y lo añadimos como fila a la tabla
        for (ClienteOtaku p : lista) {
            Object[] fila = {
                p.getId(),
                p.getNombre(),
                p.getEmail(),
                p.getTelefono(),
                p.getFechaRegistro()
            };
            modeloTabla.addRow(fila);
        }
    }
}
    

