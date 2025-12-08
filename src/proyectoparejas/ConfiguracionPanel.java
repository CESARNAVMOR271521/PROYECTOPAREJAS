package proyectoparejas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ConfiguracionPanel extends JPanel {
    
    private JLabel titulo;  // Lo hago atributo para modificarlo después
    
    public ConfiguracionPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(15, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título
        titulo = new JLabel("⚙️ Configuración del Sistema");
        titulo.setFont(new Font("Consolas", Font.BOLD, 32));
        titulo.setForeground(new Color(86, 215, 255));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));
        add(titulo, BorderLayout.NORTH);
        
        // Panel central con opciones
        JPanel panelCentral = new JPanel(new GridBagLayout());
        panelCentral.setBackground(new Color(15, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Opción 1: Base de datos
        JPanel cardBD = crearTarjetaConfiguracion(
            "🗄️ Base de Datos",
            "Configurar conexión a la base de datos",
            new Color(70, 140, 200)
        );
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        panelCentral.add(cardBD, gbc);
        
        // Opción 2: Backup
        JPanel cardBackup = crearTarjetaConfiguracion(
            "💾 Backup y Restauración",
            "Realizar backup o restaurar datos del sistema",
            new Color(200, 155, 60)
        );
        gbc.gridx = 1;
        gbc.gridy = 0;
        panelCentral.add(cardBackup, gbc);
        
        // Opción 3: Usuarios
        JPanel cardUsuarios = crearTarjetaConfiguracion(
            "👤 Usuarios",
            "Gestionar usuarios y permisos del sistema",
            new Color(60, 170, 90)
        );
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelCentral.add(cardUsuarios, gbc);
        
        // Opción 4: Apariencia
        JPanel cardApariencia = crearTarjetaConfiguracion(
            "🎨 Apariencia",
            "Personalizar tema y colores de la interfaz",
            new Color(150, 100, 200)
        );
        gbc.gridx = 1;
        gbc.gridy = 1;
        panelCentral.add(cardApariencia, gbc);
        
        // Opción 5: Reportes
        JPanel cardReportes = crearTarjetaConfiguracion(
            "📊 Reportes",
            "Configurar formato y destino de reportes",
            new Color(200, 100, 100)
        );
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelCentral.add(cardReportes, gbc);
        
        // Opción 6: Sistema
        JPanel cardSistema = crearTarjetaConfiguracion(
            "🔧 Sistema",
            "Información del sistema y actualizaciones",
            new Color(100, 150, 200)
        );
        gbc.gridx = 1;
        gbc.gridy = 2;
        panelCentral.add(cardSistema, gbc);
        
        add(panelCentral, BorderLayout.CENTER);
        
        // Panel inferior con información del sistema
        JPanel panelInfo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelInfo.setBackground(new Color(15, 10, 10));
        panelInfo.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(86, 215, 255), 1),
            " Información del Sistema ",
            0, 0,
            new Font("Consolas", Font.BOLD, 14),
            new Color(200, 155, 60)
        ));
        
        JLabel lblInfo = new JLabel(
            "<html><center>" +
            "Sistema de Gestión - Versión 1.0<br>" +
            "Piedra Sheikah Panel Administrativo<br>" +
            "© 2024 - Todos los derechos reservados" +
            "</center></html>"
        );
        lblInfo.setFont(new Font("Consolas", Font.PLAIN, 12));
        lblInfo.setForeground(new Color(200, 220, 255));
        
        panelInfo.add(lblInfo);
        add(panelInfo, BorderLayout.SOUTH);
    }
    
    private JPanel crearTarjetaConfiguracion(String tituloTarjeta, String descripcion, Color colorBase) {
        JPanel tarjeta = new JPanel(new BorderLayout());
        tarjeta.setBackground(new Color(30, 20, 15));
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(86, 215, 255), 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        tarjeta.setPreferredSize(new Dimension(300, 150));
        tarjeta.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JLabel lblTitulo = new JLabel(tituloTarjeta);
        lblTitulo.setFont(new Font("Consolas", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(200, 155, 60));
        
        JLabel lblDesc = new JLabel("<html><p style='width:250px'>" + descripcion + "</p></html>");
        lblDesc.setFont(new Font("Consolas", Font.PLAIN, 12));
        lblDesc.setForeground(new Color(200, 220, 255));
        
        JButton btnConfigurar = new JButton("Configurar");
        btnConfigurar.setFont(new Font("Consolas", Font.BOLD, 14));
        btnConfigurar.setForeground(Color.WHITE);
        btnConfigurar.setBackground(colorBase);
        btnConfigurar.setBorder(BorderFactory.createLineBorder(new Color(86, 215, 255), 1));
        btnConfigurar.setFocusPainted(false);
        btnConfigurar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnConfigurar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnConfigurar.setBackground(colorBase.brighter());
                btnConfigurar.setForeground(new Color(86, 215, 255));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                btnConfigurar.setBackground(colorBase);
                btnConfigurar.setForeground(Color.WHITE);
            }
        });
        
        // Acciones personalizadas según el título del botón
        btnConfigurar.addActionListener(e -> {
            switch (tituloTarjeta) {
                case "🗄️ Base de Datos":
                    configurarBaseDatos();
                    break;
                case "💾 Backup y Restauración":
                    realizarBackup();
                    break;
                case "👤 Usuarios":
                    gestionarUsuarios();
                    break;
                case "🎨 Apariencia":
                    cambiarTituloPanel();
                    break;
                case "📊 Reportes":
                    JOptionPane.showMessageDialog(this,
                        "Configuración de Reportes aún no implementada.",
                        "Reportes",
                        JOptionPane.INFORMATION_MESSAGE);
                    break;
                case "🔧 Sistema":
                    JOptionPane.showMessageDialog(this,
                        "Información del sistema y actualizaciones.\nVersión 1.0\n© 2024",
                        "Sistema",
                        JOptionPane.INFORMATION_MESSAGE);
                    break;
                default:
                    JOptionPane.showMessageDialog(this,
                        "Funcionalidad en desarrollo.",
                        "Información",
                        JOptionPane.INFORMATION_MESSAGE);
                    break;
            }
        });
        
        tarjeta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                tarjeta.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 155, 60), 3),
                    BorderFactory.createEmptyBorder(19, 19, 19, 19)
                ));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                tarjeta.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(86, 215, 255), 2),
                    BorderFactory.createEmptyBorder(20, 20, 20, 20)
                ));
            }
        });
        
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(new Color(30, 20, 15));
        panelSuperior.add(lblTitulo, BorderLayout.NORTH);
        panelSuperior.add(lblDesc, BorderLayout.CENTER);
        
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelInferior.setBackground(new Color(30, 20, 15));
        panelInferior.add(btnConfigurar);
        
        tarjeta.add(panelSuperior, BorderLayout.CENTER);
        tarjeta.add(panelInferior, BorderLayout.SOUTH);
        
        return tarjeta;
    }
    
    private void configurarBaseDatos() {
        JTextField txtHost = new JTextField("localhost");
        JTextField txtPuerto = new JTextField("3306");
        JTextField txtUsuario = new JTextField("root");
        JTextField txtPassword = new JPasswordField();
        
        Object[] mensaje = {
            "Host:", txtHost,
            "Puerto:", txtPuerto,
            "Usuario:", txtUsuario,
            "Contraseña:", txtPassword
        };
        
        int opcion = JOptionPane.showConfirmDialog(
            this,
            mensaje,
            "Configurar Base de Datos",
            JOptionPane.OK_CANCEL_OPTION
        );
        
        if (opcion == JOptionPane.OK_OPTION) {
            // Aquí podrías guardar la configuración o validar
            JOptionPane.showMessageDialog(this,
                "Configuración guardada:\nHost: " + txtHost.getText() +
                "\nPuerto: " + txtPuerto.getText() +
                "\nUsuario: " + txtUsuario.getText(),
                "Base de Datos",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void realizarBackup() {
        int opcion = JOptionPane.showConfirmDialog(
            this,
            "¿Desea realizar un backup de los datos ahora?",
            "Backup y Restauración",
            JOptionPane.YES_NO_OPTION
        );
        
        if (opcion == JOptionPane.YES_OPTION) {
            // Simular backup
            JOptionPane.showMessageDialog(this,
                "Backup realizado con éxito.",
                "Backup",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void gestionarUsuarios() {
        // Esto sería un diálogo o ventana para gestionar usuarios
        JOptionPane.showMessageDialog(this,
            "Gestión de usuarios aún no implementada.",
            "Usuarios",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void cambiarTituloPanel() {
        String nuevoTitulo = JOptionPane.showInputDialog(
            this,
            "Ingrese el nuevo nombre para el panel principal:",
            titulo.getText()
        );
        
        if (nuevoTitulo != null && !nuevoTitulo.trim().isEmpty()) {
            titulo.setText(nuevoTitulo.trim());
            JOptionPane.showMessageDialog(this,
                "Título actualizado correctamente.",
                "Apariencia",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
