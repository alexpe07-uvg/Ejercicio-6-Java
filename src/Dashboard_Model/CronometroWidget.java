package Dashboard_Model;
public class CronometroWidget extends Widget implements Interactuable, Actualizable {
    private int seconds;
    private boolean running;
    private long lastStartTime = 0;

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
        if (running) {
            this.seconds = (int) ((System.currentTimeMillis() - this.lastStartTime) / 1000L);
        }
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        String tiempoFormateado = String.format("%02d:%02d", minutes, remainingSeconds); // se le da formato de 2 dígitos a los minutos y segundos tal y como en cronometro real
        String estado = running ? "⏱ En marcha" : "⏸ Detenido"; //forma reducida de if-else con iconos para mejor estética brindados por la IA
        return " | " + getName() + " | Estado: " + estado + " | Cronómetro: " + tiempoFormateado;
    }

    @Override 
    public void refresh() {
        if (running) {
            this.seconds = (int) ((System.currentTimeMillis() - this.lastStartTime) / 1000L);
        }
    }

    @Override
    public boolean tieneAcciones() {
        return true;
    }
    
    @Override
    public void executeAction(String action) {
        if ("iniciar".equalsIgnoreCase(action)) {
            if (!this.running) {
                this.running = true;
                this.lastStartTime = System.currentTimeMillis() - (this.seconds * 1000L);
            }
        } else if ("detener".equalsIgnoreCase(action) || "pausar".equalsIgnoreCase(action)) {
            if (this.running) {
                this.running = false;
                this.seconds = (int) ((System.currentTimeMillis() - this.lastStartTime) / 1000L);
            }
        } else if ("reiniciar".equalsIgnoreCase(action)) {
            this.seconds = 0;
            this.running = false;
            this.lastStartTime = 0;
        }
    }
    

}
