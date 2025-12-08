package proyectoparejas;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class PantallaInicio {

    private JFrame frmPrimeraPrueba;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                PantallaInicio window = new PantallaInicio();
                window.frmPrimeraPrueba.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public PantallaInicio() {
        initialize();
    }

    private CardLayout cardLayout;
    private JPanel panelContenedor;
    private JButton btnProductos, btnEmpleado, btnClientes, btnVenta, btnCategorias, btnConfiguracion;

    private void initialize() {

        // FRAME SIN BORDE CLÁSICO
        frmPrimeraPrueba = new JFrame();
        frmPrimeraPrueba.setUndecorated(true);
        frmPrimeraPrueba.setBounds(100, 100, 900, 600);
        frmPrimeraPrueba.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmPrimeraPrueba.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frmPrimeraPrueba.setLayout(new BorderLayout());
        frmPrimeraPrueba.getContentPane().setBackground(new Color(15, 10, 10)); // Ébano

        // ========================================================
        // BARRA SUPERIOR - ESTILO PIEDRA SHEIKAH
        // ========================================================
        JPanel barraSuperior = new JPanel(new BorderLayout());
        barraSuperior.setBackground(new Color(30, 20, 15));
        barraSuperior.setBorder(new LineBorder(new Color(200, 155, 60), 2)); // Dorado

        // Panel arrastre
        JPanel barraMovimiento = new JPanel();
        barraMovimiento.setOpaque(false);

        final Point[] pos = {null};

        barraMovimiento.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                pos[0] = e.getPoint();
            }
        });

        barraMovimiento.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point p = e.getLocationOnScreen();
                frmPrimeraPrueba.setLocation(p.x - pos[0].x, p.y - pos[0].y);
            }
        });

        // BOTÓN CERRAR - ESTILO SHEIKAH
        JButton btnCerrar = new JButton("✕");
        btnCerrar.setPreferredSize(new Dimension(55, 35));
        btnCerrar.setFont(new Font("Consolas", Font.BOLD, 20));
        btnCerrar.setForeground(new Color(86, 215, 255)); // Azul Sheikah Glow
        btnCerrar.setBackground(new Color(35, 25, 20));
        btnCerrar.setBorder(new LineBorder(new Color(200, 155, 60), 1));

        btnCerrar.setFocusPainted(false);
        btnCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnCerrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnCerrar.setBackground(new Color(200, 50, 50));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnCerrar.setBackground(new Color(35, 25, 20));
            }
        });

        btnCerrar.addActionListener(e -> System.exit(0));

        barraSuperior.add(barraMovimiento, BorderLayout.CENTER);
        barraSuperior.add(btnCerrar, BorderLayout.EAST);

        frmPrimeraPrueba.add(barraSuperior, BorderLayout.NORTH);

        // ========================================================
        // PANEL LATERAL - ESTILO SHEIKAH
        // ========================================================
        JPanel panelLateral = new JPanel(new GridLayout(12, 1, 10, 10));
        panelLateral.setPreferredSize(new Dimension(240, 0));
        panelLateral.setBackground(new Color(25, 15, 10));
        panelLateral.setBorder(new EmptyBorder(20, 12, 20, 12));

        frmPrimeraPrueba.add(panelLateral, BorderLayout.WEST);

        // --------- BOTONES RENOMBRADOS ---------
        btnProductos     = crearBotonSheikah("📦 Productos");
        btnEmpleado      = crearBotonSheikah("🧑‍💼 Empleado");
        btnClientes      = crearBotonSheikah("👥 Clientes");
        btnVenta         = crearBotonSheikah("💰 Venta");
        btnCategorias    = crearBotonSheikah("🗂️ Categorías");
        btnConfiguracion = crearBotonSheikah("⚙️ Configuración");

        // --------- SE AÑADEN AL PANEL ---------
        panelLateral.add(btnProductos);
        panelLateral.add(crearSeparadorDorado());

        panelLateral.add(btnEmpleado);
        panelLateral.add(crearSeparadorDorado());

        panelLateral.add(btnClientes);
        panelLateral.add(crearSeparadorDorado());

        panelLateral.add(btnVenta);
        panelLateral.add(crearSeparadorDorado());

        panelLateral.add(btnCategorias);
        panelLateral.add(crearSeparadorDorado());

        panelLateral.add(btnConfiguracion);
        panelLateral.add(crearSeparadorDorado());

        // ========================================================
        // PANEL PRINCIPAL CON CARDS
        // ========================================================
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(15, 10, 10));
        frmPrimeraPrueba.add(panelPrincipal, BorderLayout.CENTER);

        // CardLayout para cambiar entre paneles
        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);
        panelContenedor.setBackground(new Color(15, 10, 10));

        // Crear y añadir todos los paneles
        ProductosPanel panelProductos = new ProductosPanel();
        EmpleadoPanel panelEmpleado = new EmpleadoPanel();
        ClientesPanel panelClientes = new ClientesPanel();
        VentaPanel panelVenta = new VentaPanel();
        CategoriasPanel panelCategorias = new CategoriasPanel();
        ConfiguracionPanel panelConfiguracion = new ConfiguracionPanel();

        // Panel de inicio (por defecto)
        JPanel panelInicio = crearPanelInicio();

        panelContenedor.add(panelInicio, "INICIO");
        panelContenedor.add(panelProductos, "PRODUCTOS");
        panelContenedor.add(panelEmpleado, "EMPLEADO");
        panelContenedor.add(panelClientes, "CLIENTES");
        panelContenedor.add(panelVenta, "VENTA");
        panelContenedor.add(panelCategorias, "CATEGORIAS");
        panelContenedor.add(panelConfiguracion, "CONFIGURACION");

        panelPrincipal.add(panelContenedor, BorderLayout.CENTER);

        // Agregar listeners a los botones
        btnProductos.addActionListener(e -> {
            cardLayout.show(panelContenedor, "PRODUCTOS");
            resetearBotonSeleccionado();
            marcarBotonSeleccionado(btnProductos);
        });

        btnEmpleado.addActionListener(e -> {
            cardLayout.show(panelContenedor, "EMPLEADO");
            resetearBotonSeleccionado();
            marcarBotonSeleccionado(btnEmpleado);
        });

        btnClientes.addActionListener(e -> {
            cardLayout.show(panelContenedor, "CLIENTES");
            resetearBotonSeleccionado();
            marcarBotonSeleccionado(btnClientes);
        });

        btnVenta.addActionListener(e -> {
            cardLayout.show(panelContenedor, "VENTA");
            resetearBotonSeleccionado();
            marcarBotonSeleccionado(btnVenta);
        });

        btnCategorias.addActionListener(e -> {
            cardLayout.show(panelContenedor, "CATEGORIAS");
            resetearBotonSeleccionado();
            marcarBotonSeleccionado(btnCategorias);
        });

        btnConfiguracion.addActionListener(e -> {
            cardLayout.show(panelContenedor, "CONFIGURACION");
            resetearBotonSeleccionado();
            marcarBotonSeleccionado(btnConfiguracion);
        });
    }

    private JPanel crearPanelInicio() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(15, 10, 10));
        panel.setBorder(new EmptyBorder(40, 40, 40, 40));

        JLabel lblTitulo = new JLabel("Piedra Sheikah – Panel Administrativo");
        lblTitulo.setFont(new Font("Consolas", Font.BOLD, 36));
        lblTitulo.setForeground(new Color(86, 215, 255));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBorder(new EmptyBorder(0, 0, 40, 0));

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(30, 20, 15));
        card.setBorder(new LineBorder(new Color(200, 155, 60), 3));

        JTextArea areaContenido = new JTextArea();
        areaContenido.setBackground(new Color(20, 15, 10));
        areaContenido.setForeground(new Color(200, 220, 255));
        areaContenido.setFont(new Font("Consolas", Font.PLAIN, 16));
        areaContenido.setText(
            "Bienvenido al Panel Administrativo de Gestión\n\n" +
            "Seleccione una opción del menú lateral para comenzar:\n\n" +
            "📦 Productos      - Gestione el catálogo de productos\n" +
            "🧑‍💼 Empleado      - Administre la información de empleados\n" +
            "👥 Clientes       - Gestione la base de datos de clientes\n" +
            "💰 Venta          - Realice ventas y transacciones\n" +
            "🗂️ Categorías     - Organice productos por categorías\n" +
            "⚙️ Configuración  - Ajustes y configuraciones del sistema\n\n" +
            "Sistema desarrollado con estilo Sheikah - Zelda BOTW"
        );
        areaContenido.setEditable(false);
        areaContenido.setLineWrap(true);
        areaContenido.setWrapStyleWord(true);
        areaContenido.setBorder(new EmptyBorder(20, 20, 20, 20));

        card.add(new JScrollPane(areaContenido), BorderLayout.CENTER);

        panel.add(lblTitulo, BorderLayout.NORTH);
        panel.add(card, BorderLayout.CENTER);

        return panel;
    }

    private void resetearBotonSeleccionado() {
        JButton[] botones = {btnProductos, btnEmpleado, btnClientes, btnVenta, btnCategorias, btnConfiguracion};
        for (JButton btn : botones) {
            btn.setBackground(new Color(40, 25, 15));
            btn.setForeground(new Color(200, 155, 60));
            btn.setBorder(new LineBorder(new Color(86, 215, 255), 2));
        }
    }

    private void marcarBotonSeleccionado(JButton btn) {
        btn.setBackground(new Color(86, 215, 255));
        btn.setForeground(new Color(15, 10, 10));
        btn.setBorder(new LineBorder(new Color(200, 155, 60), 3));
    }

    // ========================================================
    // BOTÓN SHEIKAH LIGHT
    // ========================================================
    private JButton crearBotonSheikah(String texto) {
        JButton b = new JButton(texto);

        b.setBackground(new Color(40, 25, 15));
        b.setForeground(new Color(200, 155, 60)); // Dorado
        b.setFont(new Font("Consolas", Font.BOLD, 16));

        b.setBorder(new LineBorder(new Color(86, 215, 255), 2)); // Azul neón Sheikah
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto de Glow Sheikah
        b.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                b.setBackground(new Color(55, 35, 20));
                b.setForeground(new Color(86, 215, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                b.setBackground(new Color(40, 25, 15));
                b.setForeground(new Color(200, 155, 60));
            }
        });

        return b;
    }

    // ========================================================
    // SEPARADOR DORADO
    // ========================================================
    private JSeparator crearSeparadorDorado() {
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(200, 155, 60));
        sep.setBackground(new Color(200, 155, 60));
        return sep;
    }
}