package Dashboard_Controller;

import Dashboard_Model.Dashboard;
import Dashboard_Model.Widget;
import Dashboard_View.DashboardFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class DashBoardController {
    private final Dashboard model;
    private final DashboardFrame view;
    private Timer timerEnTiempoReal;
    
    private List<Widget> listaActual = new ArrayList<>();
    private boolean enBusqueda = false;

    public DashBoardController(Dashboard model, DashboardFrame view) {
        this.model = model;
        this.view = view;
        initListeners();
    }

    public void iniciar() {
        listaActual = model.getWidgets();
        actualizarVista(listaActual);
        iniciarTimerTiempoReal();
        view.setVisible(true);
    }

    // Timer para que el reloj y cronómetro avancen visualmente en vivo
    private void iniciarTimerTiempoReal() {
        timerEnTiempoReal = new Timer(1000, e -> {
            // Actualiza visualmente la pantalla sin alterar el estado de widgets como Clima o Batería
            if (enBusqueda) {
                actualizarVista(listaActual);
            } else {
                refrescarSegunFiltro();
            }
        });
        timerEnTiempoReal.start();
    }

    // Enlace de listeners con la vista
    private void initListeners() {
        view.setBuscarListener(e -> manejarBusqueda());
        view.setLimpiarListener(e -> manejarLimpiar());
        view.setFiltroListener(e -> {
            enBusqueda = false;
            refrescarSegunFiltro();
        });
        view.setOrdenarMemoriaListener(e -> manejarOrdenarPorMemoria());
        view.setRefrescarTodoListener(e -> manejarRefrescarTodo());
    }

    // Lógica de búsqueda de widgets por ID o nombre
    private void manejarBusqueda() {
        String inputUsuario = view.getTextoBusqueda();

        if (inputUsuario == null || inputUsuario.trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                    "Ingresa un ID o Nombre para buscar.", 
                    "Aviso", 
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Widget resultado = null;

        try {
            int id = Integer.parseInt(inputUsuario.trim());
            resultado = model.getWidget(id);
        } catch (NumberFormatException e) {
            resultado = model.getWidget(inputUsuario.trim());
        }

        listaActual = new ArrayList<>();
        if (resultado != null) {
            listaActual.add(resultado);
            enBusqueda = true;
        } else {
            enBusqueda = false;
            JOptionPane.showMessageDialog(view, 
                    "No se encontró ningún widget con: \"" + inputUsuario + "\"", 
                    "Sin resultados", 
                    JOptionPane.INFORMATION_MESSAGE);
            listaActual = model.getWidgets();
        }

        actualizarVista(listaActual);
    }

    // Lógica para limpiar cualquier búsqueda
    private void manejarLimpiar() {
        view.limpiarBusqueda();
        enBusqueda = false;
        refrescarSegunFiltro();
    }

    // Lógica para filtrar widgets activos
    private void manejarFiltro() {
        enBusqueda = false;
        refrescarSegunFiltro();
    }

    private void refrescarSegunFiltro() {
        String filtro = view.getFiltroSeleccionado();
        if ("Solo Activos".equalsIgnoreCase(filtro)) {
            listaActual = model.getWidgetsActivos();
        } else {
            listaActual = model.getWidgets();
        }
        actualizarVista(listaActual);
    }

    // Lógica para ordenar widgets por memoria consumida
    private void manejarOrdenarPorMemoria() {
        model.orderMemCapacity();
        if (enBusqueda) {
            actualizarVista(listaActual);
        } else {
            refrescarSegunFiltro();
        }
    }

    // Lógica para refrescar todos los widgets actualizables
    private void manejarRefrescarTodo() {
        model.refreshWidgets(); // Polimórfico puro: refresca Clima, Batería, etc.

        if (enBusqueda) {
            actualizarVista(listaActual);
        } else {
            refrescarSegunFiltro();
        }
    }

    // Interacción individual con cada tarjeta mediante botones autónomos
    private ActionListener crearAccionWidgetListener() {
        return (ActionEvent e) -> {
            String comando = e.getActionCommand();
            if (comando == null) return;

            if (comando.startsWith("ACCION:")) {
                String[] partes = comando.split(":", 3);
                if (partes.length == 3) {
                    try {
                        int id = Integer.parseInt(partes[1]);
                        String accion = partes[2];
                        Widget widget = model.getWidget(id);
                        if (widget != null) {
                            ejecutarAccionAutonoma(widget, accion);
                        }
                    } catch (NumberFormatException ex) {
                        // Ignora formato no numérico
                    }
                }
            } else if (comando.startsWith("INTERACTUAR_")) {
                try {
                    int id = Integer.parseInt(comando.replace("INTERACTUAR_", ""));
                    Widget widget = model.getWidget(id);
                    if (widget != null) {
                        solicitarAccionUsuario(widget);
                    }
                } catch (NumberFormatException ex) {
                    // Ignora formato no numérico
                }
            }
        };
    }

    // Ejecuta la acción directa del botón autónomo sin abrir diálogos innecesarios
    private void ejecutarAccionAutonoma(Widget widget, String accion) {
        if ("Escribir".equalsIgnoreCase(accion)) {
            String texto = JOptionPane.showInputDialog(view,
                    "Escribe el contenido para la nota rápida:",
                    "Escribir Nota",
                    JOptionPane.PLAIN_MESSAGE);
            if (texto != null && !texto.trim().isEmpty()) {
                widget.executeAction(texto.trim());
            }
        } else {
            widget.executeAction(accion);
        }
        actualizarVista(listaActual);
    }

    private void solicitarAccionUsuario(Widget widget) {
    String accion = JOptionPane.showInputDialog(view,
            "Widget: " + widget.getName() + "\n\n"
            + widget.getInstrucciones() + "\n\n"
            + "Ingresa la acción:",
            "Interactuar con " + widget.getName(),
            JOptionPane.QUESTION_MESSAGE);

    if (accion != null && !accion.trim().isEmpty()) {
        widget.executeAction(accion.trim());
        actualizarVista(listaActual);
    }
}

    // Conexión final con la vista
    private void actualizarVista(List<Widget> listaAMostrar) {
        view.renderizarWidgets(listaAMostrar, crearAccionWidgetListener());

        int totalWidgets = model.getWidgets().size();
        int activos = model.getWidgetsActivos().size();
        float ramTotal = 0;
        for (Widget w : model.getWidgets()) {
            ramTotal += w.getMemCapacity();
        }

        view.setTextoResumen(String.format("Total: %d | Activos: %d | RAM Total: %.1f KB", 
                totalWidgets, activos, ramTotal));
    }
}