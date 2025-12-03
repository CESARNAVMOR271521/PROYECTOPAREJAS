package parcial3;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public class Barra {

    public JFrame frame;
    private JProgressBar Barra;
    Inicio inicio = new Inicio();
    String nombreUsuario = "";
    Thread hilo;

    // ---- LISTA DE ESTRELLAS (PARA QUE NO CAMBIEN CADA REPINTADO) ----
    private final List<int[]> estrellas = new ArrayList<>();

    public Barra() {
        inicializarEstrellas();  // Generamos las estrellas solo una vez
        initialize();

        hilo = new Thread(() -> {
            for (int i = 1; i <= 100; i++) {

                final int value = i;
                SwingUtilities.invokeLater(() -> Barra.setValue(value)); // Seguro para Swing

                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (i == 100) {
                    SwingUtilities.invokeLater(() -> {
                        inicio.frame.setVisible(true);
                        inicio.lblNombre.setText(nombreUsuario);
                        frame.setVisible(false);
                    });
                }
            }
        });
    }

    // ---------------- METODOS PARA NOMBRE ----------------------
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    // ------------------- INICIAR HILO -------------------------
    public void startProgress() {
        hilo.start();
    }

    // ---------------- GENERAR ESTRELLAS -----------------------
    private void inicializarEstrellas() {
        for (int i = 0; i < 150; i++) {
            int x = (int) (Math.random() * 450);
            int y = (int) (Math.random() * 220);
            int size = (int) (Math.random() * 3) + 1;

            estrellas.add(new int[]{x, y, size});
        }
    }

    // -------------------- UI ---------------------------------
    private void initialize() {

        frame = new JFrame();
        frame.setBounds(100, 100, 450, 220);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setUndecorated(true);

        // PANEL FONDO DEGRADADO + ESTRELLAS
        JPanel starPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Degradado vertical
                for (int y = 0; y < getHeight(); y++) {
                    int r = 10 + (int) (y * 0.05);
                    int gC = 10 + (int) (y * 0.05);
                    int b = 25 + (int) (y * 0.1);
                    g.setColor(new Color(r, gC, b));
                    g.drawLine(0, y, getWidth(), y);
                }

                // Estrellas ya generadas
                g.setColor(Color.WHITE);
                for (int[] e : estrellas) {
                    g.fillOval(e[0], e[1], e[2], e[2]);
                }
            }
        };
        starPanel.setLayout(null);
        frame.setContentPane(starPanel);

        // PROGRESSBAR
        Barra = new JProgressBar();
        Barra.setFont(new Font("Tahoma", Font.BOLD, 20));
        Barra.setForeground(Color.ORANGE);
        Barra.setStringPainted(true);
        Barra.setBounds(20, 120, 400, 35);
        starPanel.add(Barra);

        // BOTÓN CERRAR
        JButton btnCerrar = new JButton("X");
        btnCerrar.setBounds(380, 10, 50, 30);
        btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnCerrar.setBackground(new Color(255, 80, 80));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.addActionListener(e -> frame.dispose());
        starPanel.add(btnCerrar);
    }
}
