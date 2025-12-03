package proyectoparejas;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

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
        // Crear el frame principal
        frmPrimeraPrueba = new JFrame();
        frmPrimeraPrueba.setTitle("Panel de Administrador");
        frmPrimeraPrueba.setBounds(100, 100, 800, 500);
        frmPrimeraPrueba.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmPrimeraPrueba.setLayout(new BorderLayout());

        // PANEL LATERAL (botones)
        JPanel panelLateral = new JPanel();
        panelLateral.setBackground(new Color(34, 45, 65));
        panelLateral.setPreferredSize(new Dimension(200, 0));
        panelLateral.setLayout(new GridLayout(6, 1, 10, 10));
        panelLateral.setBorder(new EmptyBorder(20, 10, 20, 10));
        frmPrimeraPrueba.add(panelLateral, BorderLayout.WEST);

        // Botones del panel lateral
        JButton btnUsuarios = crearBoton("Gestionar Usuarios");
        JButton btnEstadisticas = crearBoton("Ver Estadísticas");
        JButton btnConfiguracion = crearBoton("Configuración");
        JButton btnReportes = crearBoton("Reportes");
        JButton btnCerrarSesion = crearBoton("Cerrar Sesión");

        panelLateral.add(btnUsuarios);
        panelLateral.add(btnEstadisticas);
        panelLateral.add(btnConfiguracion);
        panelLateral.add(btnReportes);
        panelLateral.add(btnCerrarSesion);

        // PANEL PRINCIPAL (contenido)
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBackground(new Color(246, 246, 246));
        panelPrincipal.setLayout(new BorderLayout());
        frmPrimeraPrueba.add(panelPrincipal, BorderLayout.CENTER);

        // Título del panel principal
        JLabel lblTitulo = new JLabel("Bienvenido al Panel de Administración");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBorder(new EmptyBorder(20, 0, 20, 0));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // Área de contenido
        JTextArea areaContenido = new JTextArea();
        areaContenido.setText("Aquí se mostrará la información de la sección seleccionada...");
        areaContenido.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        areaContenido.setEditable(false);
        areaContenido.setLineWrap(true);
        areaContenido.setWrapStyleWord(true);
        panelPrincipal.add(new JScrollPane(areaContenido), BorderLayout.CENTER);
    }

    // Método auxiliar para crear botones modernos
    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFocusPainted(false);
        boton.setBackground(new Color(58, 74, 97));
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }
}
