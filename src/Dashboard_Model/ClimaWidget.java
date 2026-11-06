package Dashboard_Model;
public class ClimaWidget extends Widget implements Actualizable {
    private float temperature;
    private String weatherCondition;

    public ClimaWidget(String name, int ID, float memCapacity, boolean isState, int height, int width, float temperature, String weatherCondition) {
        super(name, ID, memCapacity, isState, height, width);
        this.temperature = temperature;
        this.weatherCondition = weatherCondition;
    }

    //getters y setters específicos
    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    @Override
    public String render() {
        return " | " + getName() + " | Temperatura: " + temperature + "°C | Condición climática: " + weatherCondition;
    }

    //condición de clima fija para simplificar la simulación, pero se puede mejorar para que cambie dependiendo de la temperatura
    @Override 
    public void refresh() {
        // Simulación: pequeña variación térmica
        this.temperature = Math.round((20.0f + (float)(Math.random() * 5.0)) * 10.0f) / 10.0f;
    }
    
}
