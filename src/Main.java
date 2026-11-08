import Dashboard_Model.*;
import Dashboard_View.DashboardFrame;
import Dashboard_Controller.DashBoardController;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        // 1. Configurar FlatLaf Dark Mode
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
            System.err.println("No se pudo inicializar FlatLaf, usando tema por defecto.");
        }

        // 2. Ejecutar la UI
        SwingUtilities.invokeLater(() -> {
            // A. Instanciar el Modelo principal
            Dashboard dashboard = new Dashboard();

            // B. Poblar con los 10 widgets de prueba
            precargarWidgets(dashboard);

            // C. Instanciar la Vista
            DashboardFrame frame = new DashboardFrame();

            // D. Instanciar el Controlador conectando Modelo y Vista
            DashBoardController controller = new DashBoardController(dashboard, frame);

            // E. Desplegar la aplicación
            controller.iniciar();
        });
    }

    // Método para instanciar y cargar los 10 widgets
    private static void precargarWidgets(Dashboard dashboard) {
        // 1. Reloj Digital (Actualizable)
        dashboard.addWidget(new RelojWidget("Reloj Local", 1, 128.5f, true, 100, 200, "America/Guatemala"));

        // 2. Monitor de Batería (Actualizable)
        dashboard.addWidget(new MonBateriaWidget("Batería del Sistema", 2, 64.0f, true, 80, 150, 85));

        // 3. Visor de Clima (Actualizable)
        dashboard.addWidget(new ClimaWidget("Clima Local", 3, 256.0f, true, 120, 220, 21.5f, "Parcialmente nublado"));

        // 4. Cronómetro (Mixto: Actualizable e Interactable)
        dashboard.addWidget(new CronometroWidget("Cronómetro", 4, 96.0f, true, 90, 180));

        // 5. Notas Rápidas (Interactable)
        dashboard.addWidget(new NotasWidget("Notas Rápidas", 5, 512.0f, true, 150, 250, "Revisar rúbrica de POO antes de enviar."));

        // 6. Contador de Vasos de Agua (Interactable)
        dashboard.addWidget(new CounterVasosWidget("Hidratación Diaria", 6, 48.0f, true, 80, 140, 0, 8));

        // 7. Reproductor de Música (Interactable)
        dashboard.addWidget(new MusicaWidget("Reproductor de música", 7, 1024.0f, true, 130, 260, "Ojitos Lindos", false));

        // 8. Tareas Pendientes (Interactable)
        dashboard.addWidget(new TareasWidget("To-Do", 8, 320.0f, true, 160, 280));

        // 9. Frases del Día (Estático)
        dashboard.addWidget(new FrasesWidget("Frase Motivacional", 9, 32.0f, false, 70, 200, "Sé amable porque toda persona que conoces está librando una gran batalla", "Platón"));

        // 10. Notificaciones de Alerta (Estático)
        dashboard.addWidget(new NotAlertaWidget("Alerta del Sistema", 10, 16.0f, true, 60, 180, "Almacenamiento casi lleno", "INFO"));
    }
}