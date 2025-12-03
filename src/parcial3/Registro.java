package parcial3;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Registro {

    public JFrame frmRegistro;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JTextField txtNombre;

    private JLabel lblUsername;
    private JLabel lblPassword;
    private JLabel lblNombre;

    private static final String CLAVE = "1234567890123456";

    public Registro() {
        initialize();
    }

    private void initialize() {

        frmRegistro = new JFrame();
        frmRegistro.setTitle("REGISTRO");
        frmRegistro.setBounds(100, 100, 500, 600);
        frmRegistro.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frmRegistro.setLocationRelativeTo(null);
        frmRegistro.setUndecorated(true);

        // PANEL DE FONDO CON DEGRADADO Y ESTRELLAS
        JPanel starPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                for (int y = 0; y < getHeight(); y++) {
                    int r = 10 + (int) (y * 0.05);
                    int gColor = 10 + (int) (y * 0.05);
                    int b = 25 + (int) (y * 0.1);
                    g.setColor(new Color(r, gColor, b));
                    g.drawLine(0, y, getWidth(), y);
                }

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
        frmRegistro.setContentPane(starPanel);

        // PANEL GLASS
        JPanel glass = new JPanel();
        glass.setBounds(70, 100, 360, 380);
        glass.setBackground(new Color(255, 255, 255, 25));
        glass.setLayout(null);
        glass.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(255, 255, 255, 80), 2));
        starPanel.add(glass);

        // ----------- LABELS Y CAMPOS --------------------

        lblUsername = new JLabel("USUARIO");
        lblUsername.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblUsername.setForeground(Color.WHITE);
        lblUsername.setBounds(30, 40, 150, 25);
        glass.add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setBounds(30, 70, 300, 30);
        glass.add(txtUsername);

        lblPassword = new JLabel("CONTRASEÑA");
        lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblPassword.setForeground(Color.WHITE);
        lblPassword.setBounds(30, 120, 150, 25);
        glass.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(30, 150, 300, 30);
        glass.add(txtPassword);

        lblNombre = new JLabel("NOMBRE");
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setBounds(30, 200, 150, 25);
        glass.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(30, 230, 300, 30);
        glass.add(txtNombre);

        // BOTÓN REGISTRAR
        JButton btnRegistrar = new JButton("REGISTRAR");
        btnRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnRegistrar.setBackground(new Color(135, 206, 250));
        btnRegistrar.setForeground(Color.BLACK);
        btnRegistrar.setBounds(100, 500 - 60, 300, 40);
        starPanel.add(btnRegistrar);

        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection cx = DriverManager.getConnection("jdbc:mysql://localhost/papeleria", "root", "");
                    PreparedStatement ps = cx.prepareStatement("INSERT INTO usuario VALUES(null,?,?,?)");

                    ps.setString(1, txtUsername.getText());
                    ps.setString(2, encriptarPassword(txtPassword.getText()));
                    ps.setString(3, txtNombre.getText());

                    if (ps.executeUpdate() > 0) {
                        JOptionPane.showMessageDialog(null, "SE REGISTRO CORRECTAMENTE");
                        limpiarCampos();
                    } else {
                        JOptionPane.showMessageDialog(null, "ERROR AL REGISTRAR");
                    }

                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, "ERROR AL REGISTRAR");
                }
            }
        });

        // BOTÓN CERRAR (arriba derecha)
        JButton btnCerrar = new JButton("X");
        btnCerrar.setBounds(430, 10, 50, 30);
        btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnCerrar.setBackground(new Color(255, 80, 80));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.addActionListener(e -> frmRegistro.dispose());
        starPanel.add(btnCerrar);
    }

    public static String encriptarPassword(String password) {
        try {
            SecretKeySpec key = new SecretKeySpec(CLAVE.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = cipher.doFinal(password.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);

        } catch (Exception e) {
            return null;
        }
    }

    protected void limpiarCampos() {
        txtUsername.setText("");
        txtPassword.setText("");
        txtNombre.setText("");
    }

}