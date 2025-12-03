package parcial3;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.BorderFactory;

public class Login {

    public JFrame frmLogin;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    Registro r = new Registro();
    private static final String CLAVE = "1234567890123456";
    Barra b = new Barra();
    String nombreUsuario = "";

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Login window = new Login();
                window.frmLogin.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Login() {
        initialize();
    }

    private void initialize() {

        frmLogin = new JFrame();
        frmLogin.setTitle("LOGIN");
        frmLogin.setBounds(100, 100, 500, 600);
        frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmLogin.setLocationRelativeTo(null);
        frmLogin.setUndecorated(true);

        // ------------------------------
        // PANEL FONDO: DEGRADADO + ESTRELLAS
        // ------------------------------
        JPanel starPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Degradado espacial (igual que Registro)
                for (int y = 0; y < getHeight(); y++) {
                    int r = 10 + (int) (y * 0.05);
                    int gColor = 10 + (int) (y * 0.05);
                    int b = 25 + (int) (y * 0.1);
                    g.setColor(new Color(r, gColor, b));
                    g.drawLine(0, y, getWidth(), y);
                }

                // Estrellas
                g.setColor(Color.WHITE);
                for (int i = 0; i < 150; i++) {
                    int x = (int) (Math.random() * getWidth());
                    int y = (int) (Math.random() * getHeight());
                    int size = (int) (Math.random() * 3) + 1;
                    g.fillOval(x, y, size, size);
                }
            }
        };

        starPanel.setLayout(null);
        frmLogin.setContentPane(starPanel);

        // ------------------------------
        // BOTÓN CERRAR
        // ------------------------------
        JButton btnCerrar = new JButton("X");
        btnCerrar.setBounds(440, 10, 50, 30);
        btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnCerrar.setBackground(new Color(255, 80, 80));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.addActionListener(e -> frmLogin.dispose());
        starPanel.add(btnCerrar);

        // ------------------------------
        // GLASS PANEL (igual a Registro)
        // ------------------------------
        JPanel glass = new JPanel();
        glass.setBounds(70, 120, 360, 350);
        glass.setBackground(new Color(255, 255, 255, 25));
        glass.setLayout(null);
        glass.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 80), 2));
        starPanel.add(glass);

        // ------------------------------
        // TÍTULO
        // ------------------------------
        JLabel lblTitulo = new JLabel("WELCOME");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitulo.setBounds(0, 40, 500, 60);
        starPanel.add(lblTitulo);

        // ------------------------------
        // USERNAME
        // ------------------------------
        JLabel lblUsername = new JLabel("USERNAME");
        lblUsername.setForeground(Color.WHITE);
        lblUsername.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblUsername.setBounds(30, 30, 300, 25);
        glass.add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtUsername.setBounds(30, 60, 300, 40);
        glass.add(txtUsername);

        // ------------------------------
        // PASSWORD
        // ------------------------------
        JLabel lblPassword = new JLabel("PASSWORD");
        lblPassword.setForeground(Color.WHITE);
        lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblPassword.setBounds(30, 120, 300, 25);
        glass.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtPassword.setBounds(30, 150, 300, 40);
        glass.add(txtPassword);

        // ------------------------------
        // BOTÓN INICIAR SESIÓN
        // ------------------------------
        JButton btnIniciarSesion = new JButton("INICIAR SESIÓN");
        btnIniciarSesion.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnIniciarSesion.setBackground(new Color(70, 130, 180));
        btnIniciarSesion.setForeground(Color.WHITE);
        btnIniciarSesion.setFocusPainted(false);
        btnIniciarSesion.setBounds(30, 230, 300, 40);
        glass.add(btnIniciarSesion);

        btnIniciarSesion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection cx = DriverManager.getConnection("jdbc:mysql://localhost/papeleria", "root", "");
                    PreparedStatement ps = cx.prepareStatement(
                            "SELECT * FROM usuario WHERE username=? AND password=?"
                    );
                    ps.setString(1, txtUsername.getText());
                    ps.setString(2, encriptarPassword(txtPassword.getText()));
                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {
                        nombreUsuario = rs.getString(4);
                        // Ejecutar cambios de UI en el EDT para evitar problemas
                        java.awt.EventQueue.invokeLater(() -> {
                            b.setNombreUsuario(nombreUsuario);
                            b.frame.setVisible(true);
                            b.startProgress();
                            frmLogin.setVisible(false);
                        });
                    } else {
                        JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
                    }

                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, "Error en la conexión");
                }
            }
        });

        // ------------------------------
        // BOTÓN REGISTRAR
        // ------------------------------
        JButton btnRegistrar = new JButton("REGISTRAR");
        btnRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnRegistrar.setBackground(new Color(135, 206, 250));
        btnRegistrar.setBounds(100, 500, 300, 40);
        starPanel.add(btnRegistrar);

        btnRegistrar.addActionListener(e -> r.frmRegistro.setVisible(true));
    }

    public static String encriptarPassword(String password) {
        try {
            SecretKeySpec key = new SecretKeySpec(CLAVE.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = cipher.doFinal(password.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}