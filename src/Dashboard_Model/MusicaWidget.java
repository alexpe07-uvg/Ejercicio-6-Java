package Dashboard_Model; //cambio de lógica para correcto funcionamiento de simulación de música

public class MusicaWidget extends Widget implements Interactuable {

    private String actualSong;
    private boolean isPlaying;

    // Lista de canciones para la simulación
    private String[] playlist = {
        "Ojitos Lindos - Bad Bunny",
        "La Gozadera - Gente de Zona",
        "Yandel 150 - Yandel & Feid"
    };
    private int indicePista = 0;

    public MusicaWidget(String name, int ID, float memCapacity, boolean isState, int height, int width, String actualSong, boolean isPlaying) {
        super(name, ID, memCapacity, isState, height, width);
        this.actualSong = actualSong;
        this.isPlaying = isPlaying;

        // Si la canción inicial coincide con alguna de la lista, sincroniza el índice
        for (int i = 0; i < playlist.length; i++) {
            if (playlist[i].equalsIgnoreCase(actualSong)) {
                this.indicePista = i;
                break;
            }
        }
    }

    // Getters y Setters
    public String getActualSong() {
        return actualSong;
    }

    public void setActualSong(String actualSong) {
        this.actualSong = actualSong;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }

    @Override
    public boolean tieneAcciones() {
        return true;
    }

    @Override
    public String getInstrucciones() {
        return "Comandos disponibles:\n • play\n • pausar\n • siguiente";
    }

    @Override
    public String render() {
        String estado = isPlaying ? "▶ Reproduciendo" : "⏸ Pausado";
        return "[" + getName() + "] " + estado + ": " + actualSong;
    }

    @Override
    public void executeAction(String action) {
        if (action == null) return;
        String cmd = action.trim().toLowerCase();

        switch (cmd) {
            case "siguiente":
                // Avanza al siguiente índice de la lista y reinicia a 0 si llega al final
                indicePista = (indicePista + 1) % playlist.length;
                this.actualSong = playlist[indicePista];
                this.isPlaying = true;
                break;

            case "play":
                this.isPlaying = true;
                break;

            case "pausar":
            case "pausa":
                this.isPlaying = false;
                break;

            default:
                // Si el comando no es ninguno de los tres, no altera el estado
                break;
        }
    }
}