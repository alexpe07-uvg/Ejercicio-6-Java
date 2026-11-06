package Dashboard_Model;
public class MusicaWidget extends Widget implements Interactuable {
    private String actualSong;
    private boolean isPlaying;

    public MusicaWidget(String name, int ID, float memCapacity, boolean isState, int height, int width, String actualSong, boolean isPlaying) {
        super(name, ID, memCapacity, isState, height, width);
        this.actualSong = actualSong;
        this.isPlaying = isPlaying;
    }


    //getters y setters (interactivo) específicos
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
    public String render() {
        // forma resumida de un if-else
        String estado = isPlaying ? "▶ Reproduciendo" : "⏸ Pausado"; // si se está reproducioendo es verdadero, arroja el mensaje correspondiente; sino arroja pausado
        return "[" + getName() + "] " + estado + ": " + actualSong;
    }

    //lista con nombres de canciones para hacer la simulacion más realista
    private int indicePista = 0;
    private String[] playlist = {"Ojitos Lindos", "La Gozadera", "Yandel 150"};

    @Override 
    public void executeAction(String action) {
        if ("toggle".equalsIgnoreCase(action)) {
            this.isPlaying = !this.isPlaying; // Cambia el estado de reproducción
        } else if ("cambiar".equalsIgnoreCase(action)) {
            indicePista++;

            if (indicePista >= playlist.length) {
                indicePista = 0; // Reinicia la lista si se llega al final
            }
            this.actualSong = playlist[indicePista]; // Cambia a la siguiente canción

    }

}

}
    

