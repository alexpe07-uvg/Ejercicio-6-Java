package Dashboard_Model;

public class RelojWidget extends Widget implements Actualizable, Renderable{
    private String localTime;

    public RelojWidget(String name, int ID, float memCapacity, boolean isState, int height, int width, String localTime) {
        super(name, ID, memCapacity, isState, height, width);
        this.localTime = localTime;
    }

    @Override
    public String render() {
        return 
    }


    





    @Override
public String toString() {
    return super.toString() + "\nHora registrada: " + localTime;
}
}
