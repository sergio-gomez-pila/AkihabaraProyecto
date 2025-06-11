package vista.gui;
import javax.swing.SwingUtilities;

public class MainGUI {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(() -> {
			VentanaPrincipal ventana = new VentanaPrincipal();
			ventana.setVisible(true);
		});

	}

}
