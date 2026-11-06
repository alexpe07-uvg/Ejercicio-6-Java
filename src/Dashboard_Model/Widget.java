package Dashboard_Model;

public abstract class Widget implements Comparable<Widget>, Renderable{
    private String name;
    private int ID;
    private float memCapacity;
    private boolean state;
    private int height;
    private int width;
    
    
    public Widget(String name, int ID, float memCapacity, boolean state, int height, int width) {
        this.name = name;
        this.ID = ID;
        this.memCapacity = memCapacity;
        this.state = state;
        this.height = height;
        this.width = width;
    }

    // getters
    public String getName() { return name; }
    public int getID() { return ID; }
    public float getMemCapacity() { return memCapacity; }
    public boolean isState() { return state; }
    public int getHeight() { return height; }
    public int getWidth() { return width; }
    

    //método para interfaz comparable que compara widgets por capacidad de memoria
    
    public int compareTo(Widget other) {
        return Float.compare(this.memCapacity, other.memCapacity);
}

    
    public boolean equals(Object o) {
        if (this == o) {return true;} //verificación de si apuntan al mismo espacio de memoria
        if (o == null || !(o instanceof Widget)) {return false;} // verificación que el objeto no sea nulo y que sí sea widget

        Widget other = (Widget) o; //casteo del objeto a Widget

        return this.ID == other.ID;

    }

    @Override 
    public abstract String render(); //solo se establece base y se le delegan los detalles específicos a clases hijas
    
    @Override 
    public String toString() {
        return "\n=============================================================================================\n" 
        + "ID: " + ID + "| Name: " + name +"\n"
        + "Memory Capacity: " + memCapacity + "| State: " + (state ? "Activo" : "Inactivo") + "\n"
              + "==============================================================================================";

    }
}



