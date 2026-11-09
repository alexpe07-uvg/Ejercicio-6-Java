package Dashboard_Model;
public class CounterVasosWidget extends Widget implements Interactuable {
    private int drunkenVasos;
    private int goalVasos;

    public CounterVasosWidget(String name, int ID, float memCapacity, boolean isState, int height, int width, int drunkenVasos, int goalVasos) {
        super(name, ID, memCapacity, isState, height, width);
        this.drunkenVasos = drunkenVasos;
        this.goalVasos = goalVasos;
    }

    //getters y setters (interactivo) específicos
    public int getDrunkenVasos() {
        return drunkenVasos;
    }

    public void setDrunkenVasos(int drunkenVasos) {
        this.drunkenVasos = drunkenVasos;
    }

    public int getGoalVasos() {
        return goalVasos;
    }

    public void setGoalVasos(int goalVasos) {
        this.goalVasos = goalVasos;
    }

    @Override
    public String getInstrucciones() {
        return "Comandos disponibles:\n • incrementar\n • reiniciar";
    }

    @Override
    public String render() {
        return " | " + getName() + " | Vasos tomados: " + drunkenVasos + " | Meta de vasos: " + goalVasos;
    }

    @Override
    public boolean tieneAcciones() {
        return true;
    }
    
    @Override 
    public void executeAction(String action) {
        if ("incrementar".equalsIgnoreCase(action)) { // Acción para incrementar vasos tomados
            if(this.drunkenVasos < this.goalVasos) {
                this.drunkenVasos++;
            }
        } else if ("reiniciar".equalsIgnoreCase(action)) { // Acción para resetear conteo de vasos tomados
            this.drunkenVasos = 0;
        }
    }
    }


    

