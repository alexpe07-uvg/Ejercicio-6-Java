package Dashboard_Model;
public class NotasWidget extends Widget implements Interactuable{
    private String textNota;

    public NotasWidget(String name, int ID, float memCapacity, boolean isState, int height, int width, String textNota) {
        super(name, ID, memCapacity, isState, height, width);
        this.textNota = textNota;
    }

    //getters y setters específicos
    public String getTextNota() {
        return textNota;
    }

    public void setTextNota(String textNota) {
        this.textNota = textNota;
    }

    @Override
    public String getInstrucciones() {
        return "Escribe el texto de la nueva nota a guardar:";
    }

    @Override
    public String render() {
        return " | " + getName() + " | Nota rápida: " + textNota;
        
    }

    @Override
    public boolean tieneAcciones() {
        return true;
    }

    @Override
    public void executeAction(String action) {
        if ("limpiar".equalsIgnoreCase(action)) { // Acción para limpiar la nota
            this.textNota = "(Vacía)";
        } else {
            this.textNota = action; // Actualiza el contenido con el texto recibido
        }
    }

    }


    

