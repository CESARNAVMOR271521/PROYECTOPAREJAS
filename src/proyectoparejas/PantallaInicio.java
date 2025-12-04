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

    private void initialize() {

        // FRAME SIN BORDE CLÁSICO
        frmPrimeraPrueba = new JFrame();
        frmPrimeraPrueba.setUndecorated(true);
        frmPrimeraPrueba.setBounds(100, 100, 900, 600);
        frmPrimeraPrueba.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmPrimeraPrueba.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frmPrimeraPrueba.setLayout(new BorderLayout());
        frmPrimeraPrueba.getContentPane().setBackground(new Color(15, 10, 10)); // ébano

        // -------- BARRA SUPERIOR SHEIKAH --------
        JPanel barraSuperior = new JPanel(new BorderLayout());
        barraSuperior.setBackground(new Color(30, 20, 15));
        barraSuperior.setBorder(new LineBorder(new Color(200, 155, 60), 2)); // dorado Sheikah

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

        // BOTÓN CERRAR ESTILO SHEIKAH
        JButton btnCerrar = new JButton("✕");
        btnCerrar.setPreferredSize(new Dimension(55, 35));
        btnCerrar.setFont(new Font("Consolas", Font.BOLD, 20));
        btnCerrar.setForeground(new Color(86, 215, 255)); // azul Sheikah glow
        btnCerrar.setBackground(new Color(35, 25, 20));
        btnCerrar.setBorder(new LineBorder(new Color(200, 155, 60), 1)); // dorado

        btnCerrar.setFocusPainted(false);
        btnCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnCerrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnCerrar.setBackground(new Color(200, 50, 50)); // alerta
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

        // -------- PANEL LATERAL --------
        JPanel panelLateral = new JPanel(new GridLayout(10, 1, 10, 10));
        panelLateral.setPreferredSize(new Dimension(240, 0));
        panelLateral.setBackground(new Color(25, 15, 10));
        panelLateral.setBorder(new EmptyBorder(20, 12, 20, 12));

        frmPrimeraPrueba.add(panelLateral, BorderLayout.WEST);

        // BOTONES ESTILO SHEIKAH LIGHT
        JButton btnUsuarios = crearBotonSheikah("👤 Gestión de Usuarios");
        JButton btnEstadisticas = crearBotonSheikah("📊 Estadísticas");
        JButton btnConfiguracion = crearBotonSheikah("⚙️ Configuración");
        JButton btnReportes = crearBotonSheikah("📁 Reportes");
        JButton btnCerrarSesion = crearBotonSheikah("🔒 Cerrar Sesión");

        panelLateral.add(btnUsuarios);
        panelLateral.add(crearSeparadorDorado());
        panelLateral.add(btnEstadisticas);
        panelLateral.add(crearSeparadorDorado());
        panelLateral.add(btnConfiguracion);
        panelLateral.add(crearSeparadorDorado());
        panelLateral.add(btnReportes);
        panelLateral.add(crearSeparadorDorado());
        panelLateral.add(btnCerrarSesion);

        // -------- PANEL PRINCIPAL --------
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(15, 10, 10));
        frmPrimeraPrueba.add(panelPrincipal, BorderLayout.CENTER);

        JLabel lblTitulo = new JLabel("Piedra Sheikah – Panel Administrativo");
        lblTitulo.setFont(new Font("Consolas", Font.BOLD, 28));
        lblTitulo.setForeground(new Color(86, 215, 255)); // azul neón Sheikah
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBorder(new EmptyBorder(25, 0, 20, 0));

        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // TARJETA CENTRAL
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(30, 20, 15));
        card.setBorder(new LineBorder(new Color(200, 155, 60), 3)); // dorado

        JTextArea areaContenido = new JTextArea();
        areaContenido.setBackground(new Color(20, 15, 10));
        areaContenido.setForeground(new Color(200, 220, 255));
        areaContenido.setFont(new Font("Consolas", Font.PLAIN, 18));
        areaContenido.setText("Aquí aparecerá la información correspondiente...");
        areaContenido.setEditable(false);
        areaContenido.setLineWrap(true);
        areaContenido.setWrapStyleWord(true);

        card.add(new JScrollPane(areaContenido), BorderLayout.CENTER);
        panelPrincipal.add(card, BorderLayout.CENTER);
    }

    // BOTÓN ESTILO SHEIKAH LIGHT
    private JButton crearBotonSheikah(String texto) {
        JButton b = new JButton(texto);

        b.setBackground(new Color(40, 25, 15));
        b.setForeground(new Color(200, 155, 60)); // dorado
        b.setFont(new Font("Consolas", Font.BOLD, 16));
        b.setBorder(new LineBorder(new Color(86, 215, 255), 2)); // azul glow

        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto Sheikah Glow
        b.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                b.setBackground(new Color(55, 35, 20));
                b.setForeground(new Color(86, 215, 255)); // azul neón
            }

            @Override
            public void mouseExited(MouseEvent e) {
                b.setBackground(new Color(40, 25, 15));
                b.setForeground(new Color(200, 155, 60));
            }
        });

        return b;
    }

    // SEPARADOR DORADO
    private JSeparator crearSeparadorDorado() {
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(200, 155, 60));
        sep.setBackground(new Color(200, 155, 60));
        return sep;
    }
}
