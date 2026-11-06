package Dashboard_Model;
public class TareasWidget extends Widget implements Interactuable{
    private int pendingTasks;

    public TareasWidget(String name, int ID, float memCapacity, boolean isState, int height, int width, int tareasPendientes) {
        super(name, ID, memCapacity, isState, height, width);
        this.pendingTasks = Math.max(0, pendingTasks); // Asegura que el número de tareas pendientes no sea negativo
    }

    //getters y setters (interactivo) específicos
    public int getPendingTasks() {
        return pendingTasks;
    }

    public void setPendingTasks(int pendingTasks) {
        this.pendingTasks = Math.max(0, pendingTasks); // Asegura que el número de tareas pendientes no sea negativo
    }

    @Override 
    public String render() {
        return " | " + getName() + " | Tareas pendientes: " + pendingTasks;
    }

    @Override 
    public void executeAction(String action) {
        if ("completar".equalsIgnoreCase(action)) { // Acción para completar una tarea
            if (this.pendingTasks > 0) {
                this.pendingTasks--; //reduce
            }
        } else if ("agregar".equalsIgnoreCase(action)) {
            this.pendingTasks++; //aumenta
        }
    }
}
