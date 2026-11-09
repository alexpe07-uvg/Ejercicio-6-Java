package Dashboard_View;

import Dashboard_Model.Widget;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class DashboardFrame extends JFrame {

    // Paleta de colores para modo oscuro marcado / profundo (Dark Slate / OLED)
    private static final Color BG_OSCURO = new Color(13, 14, 18);
    private static final Color BG_PANEL = new Color(20, 22, 29);
    private static final Color BG_TARJETA = new Color(25, 28, 38);
    private static final Color BORDE_TARJETA = new Color(42, 47, 63);
    private static final Color COLOR_INACTIVO = new Color(139, 148, 158);

    // Componentes del panel superior (Búsqueda y Filtro para orden de memoria)
    private JTextField txtBusqueda;
    private JButton btnBuscar;
    private JButton btnLimpiar;
    private JComboBox<String> cmbFiltro;
    private JButton btnOrdenarMemoria;

    // Contenedor central para las tarjetas de los widgets
    private JPanel panelWidgets;
    private JScrollPane scrollPane;

    // Componentes del panel inferior (Acciones globales y Estado)
    private JButton btnRefrescarTodo;
    private JLabel lblResumen;

    // Caché de componentes visuales para actualización suave sin parpadeos ni pérdida de scroll
    private final java.util.Map<Integer, JLabel> mapLabelsRender = new java.util.HashMap<>();
    private final java.util.Map<Integer, JLabel> mapLabelsMeta = new java.util.HashMap<>();
    private List<Integer> ultimosIds = new java.util.ArrayList<>();

    public DashboardFrame() {
        super("Dashboard de Control de Widgets");
        initUI();
    }

    private void initUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setMinimumSize(new Dimension(800, 500));
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_OSCURO);
        setLayout(new BorderLayout(10, 10));

        // 1. Panel Superior - Búsqueda y Filtros
        JPanel panelSup = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        panelSup.setBackground(BG_PANEL);
        panelSup.setBorder(new EmptyBorder(8, 12, 8, 12));

        panelSup.add(new JLabel("Buscar widget (ID / Nombre):"));
        txtBusqueda = new JTextField(12);
        panelSup.add(txtBusqueda);

        btnBuscar = new JButton("Buscar");
        panelSup.add(btnBuscar);

        btnLimpiar = new JButton("Limpiar");
        panelSup.add(btnLimpiar);

        panelSup.add(new JSeparator(SwingConstants.VERTICAL));

        panelSup.add(new JLabel("Mostrar:"));
        cmbFiltro = new JComboBox<>(new String[]{"Todos", "Solo Activos"});
        panelSup.add(cmbFiltro);

        btnOrdenarMemoria = new JButton("Ordenar por Memoria Consumida");
        panelSup.add(btnOrdenarMemoria);

        add(panelSup, BorderLayout.NORTH);

        // 2. Panel Central (Centro) - Cuadrícula dinámica de Widgets con Scroll
        panelWidgets = new JPanel();
        panelWidgets.setBackground(BG_OSCURO);
        // GridLayout de 0 filas y 2 columnas (se adapta hacia abajo según la cantidad)
        panelWidgets.setLayout(new GridLayout(0, 2, 12, 12));
        panelWidgets.setBorder(new EmptyBorder(12, 16, 12, 16));

        scrollPane = new JScrollPane(panelWidgets);
        scrollPane.setBackground(BG_OSCURO);
        scrollPane.getViewport().setBackground(BG_OSCURO);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Scroll suave
        add(scrollPane, BorderLayout.CENTER);

        // 3. Panel Inferior - Acciones y Métricas
        JPanel panelInf = new JPanel(new BorderLayout(10, 10));
        panelInf.setBackground(BG_PANEL);
        panelInf.setBorder(new EmptyBorder(10, 15, 10, 15));

        btnRefrescarTodo = new JButton("Refrescar Actualizables");
        panelInf.add(btnRefrescarTodo, BorderLayout.WEST);

        lblResumen = new JLabel("Cargando especificaciones del sistema...");
        lblResumen.setHorizontalAlignment(SwingConstants.RIGHT);
        panelInf.add(lblResumen, BorderLayout.EAST);

        add(panelInf, BorderLayout.SOUTH);
    }

    // MÉTODOS PÚBLICOS PARA EL CONTROLADOR

    // Dibuja la lista de widgets recibida desde el modelo
    public void renderizarWidgets(List<Widget> widgets, ActionListener accionWidgetListener) {
        if (widgets == null) {
            widgets = java.util.Collections.emptyList();
        }

        List<Integer> idsActuales = new java.util.ArrayList<>();
        for (Widget w : widgets) {
            idsActuales.add(w.getID());
        }

        // Si los widgets en pantalla son los mismos, solo actualizamos los textos 
        if (idsActuales.equals(ultimosIds) && !mapLabelsRender.isEmpty()) {
            for (Widget w : widgets) {
                JLabel lblRender = mapLabelsRender.get(w.getID());
                JLabel lblMeta = mapLabelsMeta.get(w.getID());
                if (lblRender != null) {
                    lblRender.setText("<html>" + w.render() + "</html>");
                }
                if (lblMeta != null) {
                    String estadoTexto = w.isState() ? "● ACTIVO" : "○ INACTIVO";
                    String colorEstado = w.isState() ? "#3fb950" : "#8b949e";
                    lblMeta.setText("<html>" + w.getMemCapacity() + " KB | <span style='color:" 
                            + colorEstado + "; font-weight:bold;'>" + estadoTexto + "</span></html>");
                }
            }
            return;
        }

        // Si cambió la lista mostrada (por búsqueda, filtro u ordenamiento), se reconstruye la cuadrícula
        ultimosIds = idsActuales;
        mapLabelsRender.clear();
        mapLabelsMeta.clear();
        panelWidgets.removeAll();

        if (widgets.isEmpty()) {
            JLabel lblVacio = new JLabel("No se encontraron widgets para mostrar.", SwingConstants.CENTER);
            lblVacio.setForeground(COLOR_INACTIVO);
            panelWidgets.setLayout(new BorderLayout());
            panelWidgets.add(lblVacio, BorderLayout.CENTER);
        } else {
            panelWidgets.setLayout(new GridLayout(0, 2, 12, 12));
            for (Widget w : widgets) {
                panelWidgets.add(crearTarjetaWidget(w, accionWidgetListener));
            }
        }

        panelWidgets.revalidate();
        panelWidgets.repaint();
    }

    // Estructura visual de cada tarjeta individual
    private JPanel crearTarjetaWidget(Widget w, ActionListener accionWidgetListener) {
        JPanel tarjeta = new JPanel(new BorderLayout(10, 10));
        tarjeta.setBackground(BG_TARJETA);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE_TARJETA, 1, true),
                new EmptyBorder(12, 14, 12, 14)
        ));

        // Encabezado de la tarjeta (Nombre, ID, Memoria y Estado)
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        String estadoTexto = w.isState() ? "● ACTIVO" : "○ INACTIVO";
        String colorEstado = w.isState() ? "#3fb950" : "#8b949e";

        JLabel lblTitulo = new JLabel(w.getName() + " (ID: " + w.getID() + ")");
        lblTitulo.setFont(lblTitulo.getFont().deriveFont(Font.BOLD, 13f));

        JLabel lblMeta = new JLabel("<html>" + w.getMemCapacity() + " KB | <span style='color:" 
                + colorEstado + "; font-weight:bold;'>" + estadoTexto + "</span></html>");
        lblMeta.setFont(lblMeta.getFont().deriveFont(Font.PLAIN, 11f));

        header.add(lblTitulo, BorderLayout.WEST);
        header.add(lblMeta, BorderLayout.EAST);
        tarjeta.add(header, BorderLayout.NORTH);

        // Cuerpo: Contenido formateado del método render()
        JLabel lblRender = new JLabel("<html>" + w.render() + "</html>");
        tarjeta.add(lblRender, BorderLayout.CENTER);

        // Guardar referencias para actualización fluida sin reconstrucción
        mapLabelsRender.put(w.getID(), lblRender);
        mapLabelsMeta.put(w.getID(), lblMeta);

        // Pie de tarjeta: Botones autónomos polimórficos según el widget
        if (w.tieneAcciones()) {
            JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 2));
            footer.setOpaque(false);

            String[] acciones = w.getAccionesDisponibles();
            if (acciones != null && acciones.length > 0) {
                for (String accion : acciones) {
                    JButton btnAccion = new JButton(accion);
                    btnAccion.setActionCommand("ACCION:" + w.getID() + ":" + accion);
                    btnAccion.addActionListener(accionWidgetListener);
                    btnAccion.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    footer.add(btnAccion);
                }
            } else {
                JButton btnAccion = new JButton("Interactuar");
                btnAccion.setActionCommand("INTERACTUAR_" + w.getID());
                btnAccion.addActionListener(accionWidgetListener);
                btnAccion.setCursor(new Cursor(Cursor.HAND_CURSOR));
                footer.add(btnAccion);
            }

            tarjeta.add(footer, BorderLayout.SOUTH);
        }

        return tarjeta;
    }

    // Getters de datos de entrada
    public String getTextoBusqueda() {
        return txtBusqueda.getText().trim();
    }

    public String getFiltroSeleccionado() {
        return (String) cmbFiltro.getSelectedItem();
    }

    public void setTextoResumen(String texto) {
        lblResumen.setText(texto);
    }

    public void limpiarBusqueda() {
        txtBusqueda.setText("");
        cmbFiltro.setSelectedIndex(0);
    }

    // Registro de Listeners para el Controller
    public void setBuscarListener(ActionListener listener) {
        btnBuscar.addActionListener(listener);
        txtBusqueda.addActionListener(listener); // Permite buscar con Enter
    }

    public void setLimpiarListener(ActionListener listener) {
        btnLimpiar.addActionListener(listener);
    }

    public void setFiltroListener(ActionListener listener) {
        cmbFiltro.addActionListener(listener);
    }

    public void setOrdenarMemoriaListener(ActionListener listener) {
        btnOrdenarMemoria.addActionListener(listener);
    }

    public void setRefrescarTodoListener(ActionListener listener) {
        btnRefrescarTodo.addActionListener(listener);
    }

}