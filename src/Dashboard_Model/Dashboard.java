package Dashboard_Model;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Dashboard {
    private List<Widget> widgets;

    public Dashboard() {
        this.widgets = new ArrayList<>(); // se inicializa la lista de widgets
    }

    public List<Widget> getWidgets() {
    return widgets;
}

    public void addWidget(Widget widget) {
        if (widget != null) //verificación no nulo
            this.widgets.add(widget);
    }
    
    public void orderMemCapacity() {
        Collections.sort(this.widgets);
    
    }

    //Overloading en búsqueda de widgets por ID y abajo por nombre
    public Widget getWidget(int ID) {
        for (Widget w: widgets) {
            if(ID == w.getID()) {
                return w;

            }
        }
        return null;
    }

    public Widget getWidget(String name) {
        for (Widget w: widgets) {
            if(name.equalsIgnoreCase(w.getName())) { //IgnoreCase para evitar errores en diferencias de mayúsculas
                return w;

            }
        }
        return null;
    }


    //lista solo para widgets activos
    public List<Widget> getWidgetsActivos() {
        List<Widget> activos = new ArrayList<>();
        for (Widget w: widgets) {
            if(w.isState()) { //verifica que esten en true para agregarlos a la lista de activos
                activos.add(w);
            }
        }
        return activos;
    }

    // Refresca solo los widgets actualizables que estén encendidos
    public void refreshWidgets() {
        for (Widget w : widgets) {
            if (w.isState()) {
                w.refresh(); // Polimorfismo puro
            }
        }
    }
}
