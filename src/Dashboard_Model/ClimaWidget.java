package Dashboard_Model;

public class ClimaWidget extends Widget implements Actualizable, Interactuable {
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
        String icono = "⛅";
        String condMin = weatherCondition.toLowerCase();
        if (condMin.contains("sol") || condMin.contains("despejado")) {
            icono = "☀️";
        } else if (condMin.contains("lluv")) {
            icono = "🌧️";
        } else if (condMin.contains("nub")) {
            icono = "☁️";
        }

        return "<b>Temperatura:</b> <span style='font-size:15px; font-weight:bold; color:#f0883e;'>" 
                + icono + " " + temperature + "°C</span><br>"
                + "<b>Condición:</b> <span style='color:#e6edf3;'>" + weatherCondition + "</span>";
    }

    @Override 
    public void refresh() {
        // Se ejecuta cuando se presiona el botón "Refrescar Actualizables"
        simularCambioClima();
    }

    // Lógica para variar la temperatura y la condición climática
    private void simularCambioClima() {
        this.temperature = Math.round((18.0f + (float)(Math.random() * 8.0)) * 10.0f) / 10.0f;
        if (this.temperature >= 24.0f) {
            this.weatherCondition = "Soleado";
        } else if (this.temperature >= 21.0f) {
            this.weatherCondition = "Parcialmente nublado";
        } else if (this.temperature >= 19.0f) {
            this.weatherCondition = "Mayormente nublado";
        } else {
            this.weatherCondition = "Lluvia ligera";
        }
    }

    @Override
    public boolean tieneAcciones() {
        return true;
    }

    @Override
    public String[] getAccionesDisponibles() {
        return new String[]{"Actualizar Clima"};
    }

    @Override
    public void executeAction(String action) {
        // Se ejecuta con su propio botón autónomo
        simularCambioClima();
    }
}
