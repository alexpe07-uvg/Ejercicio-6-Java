package Dashboard_Model;
public class CronometroWidget extends Widget implements Interactuable, Actualizable {
    private int seconds;
    private boolean running;

    public CronometroWidget(String name, int ID, float memCapacity, boolean isState, int height, int width) {
        super(name, ID, memCapacity, isState, height, width);
        this.seconds = 0;
        this.running = false;
    }

    //getters y setters específicos
    public int getSeconds() {
        return seconds;
    }
    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
    public boolean isRunning() {
        return running;
    }
    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public String render() {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        String tiempoFormateado = String.format("%02d:%02d", minutes, remainingSeconds); // se le da formato de 2 dígitos a los minutos y segundos tal y como en cronometro real
        String estado = running ? "⏱ En marcha" : "⏸ Detenido"; //forma reducida de if-else con iconos para mejor estética brindados por la IA
        return " | " + getName() + " | Estado: " + estado + " | Cronómetro: " + tiempoFormateado;
    }

    @Override 
    public void refresh() {
        if (running) { // para que el tiempo avance si el cornometro ya se inició
            seconds++;
        }

    }
    @Override
    public void executeAction(String action) {
        if ("iniciar".equalsIgnoreCase(action)) {
            this.running = true;
        } else if ("detener".equalsIgnoreCase(action) || "pausar".equalsIgnoreCase(action)) {
            this.running = false;
        } else if ("reiniciar".equalsIgnoreCase(action)) {
            this.seconds = 0;
            this.running = false;
        }
    }
    

}
