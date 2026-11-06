package Dashboard_View;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import java.awt.Color;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        // 1. Configuración de colores oscuros profundos
        Color fondoOscuro = new Color(20, 20, 24);    // Fondo base casi negro
        Color fondoPaneles = new Color(30, 30, 36);   // Fondo para tarjetas o botones

        UIManager.put("Panel.background", fondoOscuro);
        UIManager.put("RootPane.background", fondoOscuro);
        UIManager.put("control", fondoOscuro);
        UIManager.put("Button.background", fondoPaneles);

        // 2. Activar el Look & Feel de FlatLaf
        FlatMacDarkLaf.setup();

        // 3. Lanzar la aplicación en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            // Cuando crees la ventana real (ej. DashboardFrame o Controlador), se instancia aquí:
            TestView ventana = new TestView();
            ventana.setVisible(true);
        });
    }
}
