package Dashboard_Model;
public class NotAlertaWidget extends Widget {
    private String alertMessage;
    private String level;

    public NotAlertaWidget(String name, int ID, float memCapacity, boolean isState, int height, int width, String alertMessage, String level) {
        super(name, ID, memCapacity, isState, height, width);
        this.alertMessage = alertMessage;
        this.level = level;
    }

    //getters y setters específicos
    public String getAlertMessage() {
        return alertMessage;
    }

    public void setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override 
    public String render() {
        return " | " + getName() + " | Alerta: " + alertMessage + " | Nivel: " + level;
    }

} 
    

