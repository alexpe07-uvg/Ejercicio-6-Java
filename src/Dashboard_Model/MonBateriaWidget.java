package Dashboard_Model;
public class MonBateriaWidget extends Widget implements Actualizable {
    private int percentageLevel; //nivel de batería en porcentaje
    
    public MonBateriaWidget(int ID, String name, float memCapacity, boolean isState, int height, int width, int percentageLevel) {
        super(name, ID, memCapacity, isState, height, width);
        
        //verificación de niveles superiores de los límites para que no exista un nivel de batería mayor a 100% o menor a 0%
        if (percentageLevel < 0) {
            this.percentageLevel = 0;
        } else if (percentageLevel > 100) {
            this.percentageLevel = 100;
        } else {
            this.percentageLevel = percentageLevel;
        }
    }

    //getters y setters específicos
    public int getPercentageLevel() {
        return percentageLevel;
    }

    public void setPercentageLevel(int percentageLevel) {
        this.percentageLevel = percentageLevel;
    }

    @Override 
    public String render() {
        return " | " + getName() + " | Nivel de batería: " + percentageLevel + "%";

    }

    @Override
    public void refresh() {
        //Simulacion de descarga de 1% cada vez que se refresque
        if (percentageLevel > 0) {
            percentageLevel--;
        }
    }
    
}
