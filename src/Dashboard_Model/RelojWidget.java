package Dashboard_Model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class RelojWidget extends Widget implements Actualizable{
    private String localTime;

    public RelojWidget(String name, int ID, float memCapacity, boolean isState, int height, int width, String localTime) {
        super(name, ID, memCapacity, isState, height, width);
        this.localTime = localTime;
        refresh(); // Inicializa la hora al crear el widget
    }

    //getter específico
    public String getLocalTime() {
        return localTime;
    }

    @Override
    public String render() {
        refresh(); // Mantiene la hora exacta en tiempo real cada vez que se dibuja
        return " | " + getName() + " | Hora actual en Ciudad de Guatemala: " + localTime;
    }

    @Override 
    public void refresh() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm:ss"); //formato de hora
        this.localTime = LocalTime.now().format(formato);
    }

    @Override
public String toString() {
    return super.toString() + "\nHora registrada: " + localTime;
}
}
