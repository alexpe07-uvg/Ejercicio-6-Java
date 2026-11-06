package Dashboard_View;

import Dashboard_Model.Widget;
import Dashboard_Model.Interactable;
import Dashboard_Model.Actualizable;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class DashboardFrame extends JFrame {

    // Componentes del panel superior (Búsqueda y Filtros)
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

    public DashboardFrame() {
        super("Dashboard de Control de Widgets");
        initUI();
    }

    private void initUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setMinimumSize(new Dimension(800, 500));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // 1. Panel Superior (Norte) - Búsqueda y Filtros
        JPanel panelNorte = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        panelNorte.setBorder(new EmptyBorder(5, 10, 5, 10));

        panelNorte.add(new JLabel("Buscar (ID / Nombre):"));
        txtBusqueda = new JTextField(12);
        panelNorte.add(txtBusqueda);

        btnBuscar = new JButton("Buscar");
        panelNorte.add(btnBuscar);

        btnLimpiar = new JButton("Limpiar");
        panelNorte.add(btnLimpiar);

        panelNorte.add(new JSeparator(SwingConstants.VERTICAL));

        panelNorte.add(new JLabel("Mostrar:"));
        cmbFiltro = new JComboBox<>(new String[]{"Todos", "Solo Activos"});
        panelNorte.add(cmbFiltro);

        btnOrdenarMemoria = new JButton("Ordenar por RAM");
        panelNorte.add(btnOrdenarMemoria);

        add(panelNorte, BorderLayout.NORTH);

        // 2. Panel Central (Centro) - Cuadrícula dinámica de Widgets con Scroll
        panelWidgets = new JPanel();
        // GridLayout de 0 filas y 2 columnas (se adapta hacia abajo según la cantidad)
        panelWidgets.setLayout(new GridLayout(0, 2, 12, 12));
        panelWidgets.setBorder(new EmptyBorder(10, 15, 10, 15));

        scrollPane = new JScrollPane(panelWidgets);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Scroll suave
        add(scrollPane, BorderLayout.CENTER);

        // 3. Panel Inferior (Sur) - Acciones y Métricas
        JPanel panelSur = new JPanel(new BorderLayout(10, 10));
        panelSur.setBorder(new EmptyBorder(10, 15, 10, 15));

        btnRefrescarTodo = new JButton("Refrescar Actualizables");
        panelSur.add(btnRefrescarTodo, BorderLayout.WEST);

        lblResumen = new JLabel("Cargando métricas del sistema...");
        lblResumen.setHorizontalAlignment(SwingConstants.RIGHT);
        panelSur.add(lblResumen, BorderLayout.EAST);

        add(panelSur, BorderLayout.SOUTH);
    }

    // --- MÉTODOS PÚBLICOS PARA EL CONTROLADOR ---

    // Dibuja la lista de widgets recibida desde el modelo
    public void renderizarWidgets(List<Widget> widgets, ActionListener accionWidgetListener) {
        panelWidgets.removeAll();

        if (widgets == null || widgets.isEmpty()) {
            JLabel lblVacio = new JLabel("No se encontraron widgets para mostrar.", SwingConstants.CENTER);
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
        JPanel tarjeta = new JPanel(new BorderLayout(8, 8));
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIManager.getColor("Component.borderColor"), 1),
                new EmptyBorder(10, 12, 10, 12)
        ));

        // Encabezado de la tarjeta (Nombre, ID, RAM y Estado)
        JPanel header = new JPanel(new BorderLayout());
        String estadoTexto = w.isState() ? "● ACTIVO" : "○ INACTIVO";
        JLabel lblTitulo = new JLabel(w.getName() + " (ID: " + w.getID() + ")");
        lblTitulo.setFont(lblTitulo.getFont().deriveFont(Font.BOLD, 13f));

        JLabel lblMeta = new JLabel(w.getMemCapacity() + " KB | " + estadoTexto);
        lblMeta.setFont(lblMeta.getFont().deriveFont(Font.PLAIN, 11f));

        header.add(lblTitulo, BorderLayout.WEST);
        header.add(lblMeta, BorderLayout.EAST);
        tarjeta.add(header, BorderLayout.NORTH);

        // Cuerpo: Contenido formateado del método render()
        JLabel lblRender = new JLabel("<html>" + w.render() + "</html>");
        tarjeta.add(lblRender, BorderLayout.CENTER);

        // Pie de tarjeta: Botón de acción si implementa Interactable
        if (w instanceof Interactuable) {
            JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
            JButton btnAccion = new JButton("Interactuar");
            // Se le asigna el ID en el ActionCommand para que el Controller sepa a cuál pertenece
            btnAccion.setActionCommand("INTERACTUAR_" + w.getID());
            btnAccion.addActionListener(accionWidgetListener);
            footer.add(btnAccion);
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