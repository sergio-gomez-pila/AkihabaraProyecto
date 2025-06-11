package vista.gui;

import modelo.ClienteDAO;
import modelo.ClienteOtaku;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;

public class FormularioCliente extends JPanel {
    private static final long serialVersionUID = 1L;

    // Campos para los datos del cliente
    private JTextField txtId, txtNombre, txtEmail, txtTelefono;
    private ClienteDAO clientedao;

    public FormularioCliente(ClienteOtaku cliente) {
        clientedao = new ClienteDAO();  // Inicializamos el DAO
        setLayout(new BorderLayout());  // Usamos BorderLayout para dividir la ventana

        // Título superior
        JLabel titulo = new JLabel(cliente == null ? "Añadir Cliente" : "Actualizar Cliente", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        // Panel del formulario (centro)
        JPanel panelForm = new JPanel(new GridLayout(5, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40)); // Margen

        // Campos del formulario
        panelForm.add(new JLabel("ID del cliente:"));
        txtId = new JTextField(cliente != null ? String.valueOf(cliente.getId()) : "");
        txtId.setEditable(false); // No editable si estamos actualizando
        panelForm.add(txtId);

        panelForm.add(new JLabel("Nombre:"));
        txtNombre = new JTextField(cliente != null ? cliente.getNombre() : "");
        panelForm.add(txtNombre);

        panelForm.add(new JLabel("Email:"));
        txtEmail = new JTextField(cliente != null ? cliente.getEmail() : "");
        panelForm.add(txtEmail);

        panelForm.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField(cliente != null ? cliente.getTelefono() : "");
        panelForm.add(txtTelefono);

        // Panel con los botones CRUD
        JPanel panelBotones = new JPanel(new GridLayout(1, 3, 10, 0));
        JButton btnGuardar = new JButton(cliente == null ? "Guardar" : "Actualizar");
        JButton btnEliminar = new JButton("Eliminar");

        // Asignamos la lógica a cada botón
        btnGuardar.addActionListener(e -> guardarOActualizarCliente(cliente));
        btnEliminar.addActionListener(e -> eliminarCliente(cliente));

        // Añadimos los botones al panel
        panelBotones.add(btnGuardar);
        panelBotones.add(btnEliminar);

        // Añadimos el formulario y los botones al panel principal
        add(panelForm, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    // Lógica para guardar o actualizar un cliente
    private void guardarOActualizarCliente(ClienteOtaku cliente) {
        String nombre = txtNombre.getText();
        String email = txtEmail.getText();
        String telefono = txtTelefono.getText();

        if (nombre.isEmpty() || email.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }

        try {
            Date fechaRegistro = new Date(System.currentTimeMillis());  // Fecha actual

            if (cliente == null) {
                // Si es un cliente nuevo, lo agregamos a la base de datos
                ClienteOtaku nuevoCliente = new ClienteOtaku(nombre, email, telefono);
                clientedao.agregarCliente(nuevoCliente);
                JOptionPane.showMessageDialog(this, "Cliente añadido correctamente.");
            } else {
                // Si estamos editando un cliente, lo actualizamos
                cliente.setNombre(nombre);
                cliente.setEmail(email);
                cliente.setTelefono(telefono);
                cliente.setFechaRegistro(fechaRegistro);
                clientedao.actualizarCliente(cliente);
                JOptionPane.showMessageDialog(this, "Cliente actualizado correctamente.");
            }

            limpiarCampos(); // Limpiamos los campos después de la operación
            SwingUtilities.getWindowAncestor(this).dispose();  // Cerrar la ventana del formulario

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar o actualizar: " + e.getMessage());
        }
    }

    // Lógica para eliminar un cliente
    private void eliminarCliente(ClienteOtaku cliente) {
        if (cliente != null) {
            int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro que quieres eliminar a este cliente?",
                    "Confirmación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean eliminado = clientedao.eliminarCliente(cliente.getId());
                if (eliminado) {
                    JOptionPane.showMessageDialog(this, "Cliente eliminado.");
                    SwingUtilities.getWindowAncestor(this).dispose();  // Cerrar la ventana del formulario
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo eliminar el cliente.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "No hay cliente para eliminar.");
        }
    }

    // Limpia todos los campos del formulario
    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
    }
}


