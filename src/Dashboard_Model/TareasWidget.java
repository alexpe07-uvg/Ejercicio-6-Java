package Dashboard_Model;

import java.util.ArrayList;
import java.util.List;

public class TareasWidget extends Widget implements Interactuable {

    private List<String> listaTareas;

    public TareasWidget(String name, int ID, float memCapacity, boolean state, int height, int width) {
        super(name, ID, memCapacity, state, height, width);
        this.listaTareas = new ArrayList<>();
        
        // Tareas precargadas de ejemplo
        this.listaTareas.add("Revisar modelo MVC");
        this.listaTareas.add("Subir cambios a Git");
    }

    @Override
    public boolean tieneAcciones() {
        return true;
    }

    @Override
    public void executeAction(String accion) {
        if (accion == null) return;
        String cmd = accion.trim().toLowerCase();

        if (cmd.startsWith("agregar")) {
            // Si escribe "agregar Estudiar cálculo" o solo "agregar"
            String nuevaTarea = accion.replaceFirst("(?i)agregar", "").trim();
            if (nuevaTarea.isEmpty()) {
                nuevaTarea = "Tarea pendiente #" + (listaTareas.size() + 1);
            }
            listaTareas.add(nuevaTarea);

        } else if (cmd.equals("completar") || cmd.equals("eliminar")) {
            // Remueve la primera tarea completada
            if (!listaTareas.isEmpty()) {
                listaTareas.remove(0);
            }
        }
    }

    @Override
    public String getInstrucciones() {
        return "Comandos disponibles:\n • agregar [nombre tarea]\n • completar (elimina la primera)";
    }

    @Override
    public String render() {
        StringBuilder html = new StringBuilder();
        html.append("<b>Pendientes (").append(listaTareas.size()).append("):</b><br>");

        if (listaTareas.isEmpty()) {
            html.append("<span style='color: gray;'>¡Al día! No hay pendientes.</span>"); //uso de está sintáxis para tener mejor estética y crear una lista más realista de tareas
        } else {
            html.append("<ul style='margin-left: 15px; margin-top: 2px;'>");
            for (String t : listaTareas) {
                html.append("<li>").append(t).append("</li>");
            }
            html.append("</ul>");
        }

        return html.toString();
    }
}