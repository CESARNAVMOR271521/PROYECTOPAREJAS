package parcial3;

import java.awt.*;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.sql.*;
import java.io.File;
import java.awt.Desktop;

public class Inicio {

    public JFrame frame;
    public JLabel lblNombre;
    String nombreUsuario = "";

    // Panel activo del sidebar
    private JButton btnActivo = null;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Inicio window = new Inicio();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Inicio() {
        initialize();
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
        if (lblNombre != null) {
            lblNombre.setText("👋 Bienvenido, " + nombreUsuario);
        }
    }

    // ----------------------------------------------------------
    //                     INICIALIZACIÓN
    // ----------------------------------------------------------
    private void initialize() {

        frame = new JFrame("Panel de Administración");
        frame.setSize(1000, 650);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel basePanel = new JPanel(new BorderLayout());
        frame.setContentPane(basePanel);

        // Fondo degradado
        JPanel fondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(30, 30, 30),
                        0, getHeight(), new Color(15, 15, 20)
                );
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        fondo.setLayout(new BorderLayout());
        basePanel.add(fondo, BorderLayout.CENTER);

        // -----------------------------------------------------
        //                       SIDEBAR
        // -----------------------------------------------------
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setLayout(new GridLayout(10, 1, 0, 10));
        sidebar.setBackground(new Color(25, 25, 30));
        sidebar.setBorder(new EmptyBorder(20, 10, 20, 10));

        Font menuFont = new Font("Segoe UI", Font.BOLD, 15);

        JButton btnHome = crearBotonMenu("Inicio", menuFont, "/parcial3/img/home.png");
        JButton btnUsers = crearBotonMenu("Usuarios", menuFont, "/parcial3/img/users.png");
        JButton btnProducts = crearBotonMenu("Productos", menuFont, "/parcial3/img/products.png");
        JButton btnCategories = crearBotonMenu("Categorías", menuFont, "/parcial3/img/categories.png");
        JButton btnReports = crearBotonMenu("Reportes", menuFont, "/parcial3/img/report.png");

        sidebar.add(btnHome);
        sidebar.add(btnUsers);
        sidebar.add(btnProducts);
        sidebar.add(btnCategories);
        sidebar.add(btnReports);

        fondo.add(sidebar, BorderLayout.WEST);

        // -----------------------------------------------------
        //                        HEADER
        // -----------------------------------------------------
        JPanel header = new JPanel(new BorderLayout());
        header.setPreferredSize(new Dimension(0, 60));
        header.setBackground(new Color(22, 22, 26));
        header.setBorder(new EmptyBorder(10, 20, 10, 20));

        lblNombre = new JLabel("Bienvenido");
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblNombre.setForeground(Color.WHITE);

        // Crear icono X para el botón cerrar
        ImageIcon closeIcon = crearIconoCerrar(20, 20);
        JButton btnCerrar = new JButton(closeIcon);
        btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setBackground(new Color(180, 40, 40));
        btnCerrar.setFocusPainted(false);
        btnCerrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCerrar.setPreferredSize(new Dimension(50, 35));
        btnCerrar.setToolTipText("Cerrar aplicación");
        btnCerrar.addActionListener(e -> frame.dispose());

        header.add(lblNombre, BorderLayout.WEST);
        header.add(btnCerrar, BorderLayout.EAST);

        fondo.add(header, BorderLayout.NORTH);

        // -----------------------------------------------------
        //                 CONTENIDO CENTRAL (CARD)
        // -----------------------------------------------------
        JPanel contentPanel = new JPanel(new CardLayout());
        contentPanel.setOpaque(false);
        fondo.add(contentPanel, BorderLayout.CENTER);

        // panel de inicio
        JPanel panelHome = crearPanelInicio();

        // panel usuarios simple
        JPanel panelUsers = crearPanelSimple("👤 Gestión de Usuarios");

        // panel productos (Clase que ya tienes)
        ProductosPanel panelProducts = new ProductosPanel();

        // panel categorías (Clase que ya tienes)
        CategoriasPanel panelCategoria = new CategoriasPanel();

        // vincular categorías ↔ productos
        panelCategoria.addCategorySelectionListener(id -> {
            if (id > 0) panelProducts.filterByCategory(id);
            else panelProducts.cargarProductos();
        });
        panelCategoria.addChangesListener(panelProducts::cargarProductos);

        JPanel panelReports = crearPanelReportes();

        contentPanel.add(panelHome, "HOME");
        contentPanel.add(panelUsers, "USERS");
        contentPanel.add(panelProducts, "PRODUCTS");
        contentPanel.add(panelCategoria, "CATEGORIES");
        contentPanel.add(panelReports, "REPORTS");

        btnHome.addActionListener(e -> cambiarPanel(contentPanel, "HOME", btnHome));
        btnUsers.addActionListener(e -> cambiarPanel(contentPanel, "USERS", btnUsers));
        btnProducts.addActionListener(e -> cambiarPanel(contentPanel, "PRODUCTS", btnProducts));
        btnCategories.addActionListener(e -> cambiarPanel(contentPanel, "CATEGORIES", btnCategories));
        btnReports.addActionListener(e -> cambiarPanel(contentPanel, "REPORTS", btnReports));

        marcarBotonActivo(btnHome); // por defecto
    }

    // ----------------------------------------------------------
    //               PANEL DE INICIO CON ESTADÍSTICAS
    // ----------------------------------------------------------
    private JPanel crearPanelInicio() {
        JPanel p = new JPanel(null);
        p.setOpaque(false);

        JLabel title = new JLabel("📊 Inicio - Estadísticas Generales");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setBounds(20, 20, 500, 40);
        p.add(title);

        JPanel card1 = crearTarjeta("Usuarios registrados", 120);
        card1.setBounds(40, 90, 250, 120);
        p.add(card1);

        JPanel card2 = crearTarjeta("Productos totales", 240);
        card2.setBounds(330, 90, 250, 120);
        p.add(card2);

        JPanel card3 = crearTarjeta("Categorías registradas", 60);
        card3.setBounds(620, 90, 250, 120);
        p.add(card3);

        JPanel grafico = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.WHITE);
                g.drawString("Gráfico de ejemplo (ficticio)", 20, 20);

                int[] datos = {120, 240, 60};
                Color[] colores = {new Color(100, 200, 255), new Color(255, 180, 80), new Color(120, 255, 120)};
                String[] labels = {"Usuarios", "Productos", "Categorías"};

                int x = 40;
                for (int i = 0; i < datos.length; i++) {
                    g.setColor(colores[i]);
                    g.fillRect(x, 120 - (datos[i] / 3), 80, (datos[i] / 3));
                    g.setColor(Color.WHITE);
                    g.drawString(labels[i], x, 140);
                    x += 120;
                }
            }
        };
        grafico.setBackground(new Color(0, 0, 0, 50));
        grafico.setBounds(40, 240, 820, 200);
        p.add(grafico);

        return p;
    }

    private JPanel crearTarjeta(String titulo, int valor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(40, 40, 50));
        card.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBorder(new EmptyBorder(10, 10, 5, 10));

        JLabel lblNumero = new JLabel(String.valueOf(valor));
        lblNumero.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblNumero.setForeground(new Color(90, 180, 255));
        lblNumero.setHorizontalAlignment(SwingConstants.CENTER);

        card.add(lblTitulo, BorderLayout.NORTH);
        card.add(lblNumero, BorderLayout.CENTER);
        return card;
    }

    // ----------------------------------------------------------
    //           COMPONENTES UTILITARIOS
    // ----------------------------------------------------------
    private JButton crearBotonMenu(String texto, Font f, String iconPath) {
        ImageIcon icon = null;
        try {
            URL res = getClass().getResource(iconPath);
            if (res != null) {
                icon = new ImageIcon(res);
                Image img = icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
                icon = new ImageIcon(img);
            } else {
                // Intentar cargar desde carpeta del proyecto (workspace) si existe
                try {
                    String[] parts = iconPath.replace("\\", "/").split("/");
                    String fname = parts[parts.length - 1];
                    java.io.File f1 = new java.io.File("src/parcial3/img/" + fname);
                    if (f1.exists()) {
                        ImageIcon ico2 = new ImageIcon(f1.getAbsolutePath());
                        Image img2 = ico2.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
                        icon = new ImageIcon(img2);
                    } else {
                        // Si no se encuentra recurso ni archivo, crear un icono de reserva simple
                        BufferedImage buf = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
                        Graphics2D g2 = buf.createGraphics();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g2.setColor(new Color(100, 100, 100));
                        g2.fillOval(0, 0, 24, 24);
                        g2.setColor(Color.WHITE);
                        g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
                        String ch = texto != null && texto.length() > 0 ? texto.substring(0, 1) : "?";
                        g2.drawString(ch, 6, 16);
                        g2.dispose();
                        icon = new ImageIcon(buf);
                    }
                } catch (Exception ex2) {
                    // fallback visual
                    BufferedImage buf = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2 = buf.createGraphics();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(100, 100, 100));
                    g2.fillOval(0, 0, 24, 24);
                    g2.setColor(Color.WHITE);
                    g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
                    String ch = texto != null && texto.length() > 0 ? texto.substring(0, 1) : "?";
                    g2.drawString(ch, 6, 16);
                    g2.dispose();
                    icon = new ImageIcon(buf);
                }
            }
        } catch (Exception ex) {
            icon = null;
        }

        JButton btn = (icon != null) ? new JButton(texto, icon) : new JButton(texto);
        btn.setFont(f);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(40, 40, 48));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);

        btn.addChangeListener(e -> {
            if (btn.getModel().isRollover() && btn != btnActivo)
                btn.setBackground(new Color(55, 55, 65));
            else if (btn != btnActivo)
                btn.setBackground(new Color(40, 40, 48));
        });

        return btn;
    }


    private void cambiarPanel(JPanel cards, String nombre, JButton boton) {
        CardLayout cl = (CardLayout) cards.getLayout();
        cl.show(cards, nombre);
        marcarBotonActivo(boton);
    }

    private void marcarBotonActivo(JButton boton) {
        if (btnActivo != null) btnActivo.setBackground(new Color(40, 40, 48));
        btnActivo = boton;
        btnActivo.setBackground(new Color(70, 100, 210));
    }

    private JPanel crearPanelSimple(String titulo) {
        JPanel p = new JPanel(null);
        p.setOpaque(false);
        JLabel l = new JLabel(titulo);
        l.setFont(new Font("Segoe UI", Font.BOLD, 22));
        l.setForeground(Color.WHITE);
        l.setBounds(30, 30, 400, 40);
        p.add(l);
        return p;
    }

    // ----------------------------------------------------------
    //               PANEL DE REPORTES CON BOTÓN PDF
    // ----------------------------------------------------------
    private JPanel crearPanelReportes() {
        JPanel p = new JPanel(null);
        p.setOpaque(false);

        JLabel titulo = new JLabel("📊 Reportes del Sistema");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        titulo.setBounds(30, 30, 400, 40);
        p.add(titulo);

        JLabel descripcion = new JLabel("<html>Genera reportes en formato PDF con información<br>de productos, categorías y estadísticas del sistema.</html>");
        descripcion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descripcion.setForeground(new Color(200, 200, 200));
        descripcion.setBounds(30, 80, 600, 60);
        p.add(descripcion);

        // Crear icono para el botón PDF
        ImageIcon pdfIcon = crearIconoPDF(32, 32);
        JButton btnPDF = new JButton("Generar Reporte PDF", pdfIcon);
        btnPDF.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnPDF.setForeground(Color.WHITE);
        btnPDF.setBackground(new Color(200, 60, 60));
        btnPDF.setFocusPainted(false);
        btnPDF.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        btnPDF.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnPDF.setBounds(30, 160, 300, 50);
        btnPDF.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnPDF.setIconTextGap(10);

        btnPDF.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btnPDF.setBackground(new Color(220, 80, 80));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btnPDF.setBackground(new Color(200, 60, 60));
            }
        });

        btnPDF.addActionListener(e -> generarPDF());
        p.add(btnPDF);

        return p;
    }

    // ----------------------------------------------------------
    //               GENERAR PDF CON REPORTE
    // ----------------------------------------------------------
    private void generarPDF() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Reporte PDF");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(java.io.File f) {
                return f.isDirectory() || f.getName().toLowerCase().endsWith(".pdf");
            }

            @Override
            public String getDescription() {
                return "Archivos PDF (*.pdf)";
            }
        });

        int result = fileChooser.showSaveDialog(frame);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        String filePath = fileChooser.getSelectedFile().getAbsolutePath();
        if (!filePath.toLowerCase().endsWith(".pdf")) {
            filePath += ".pdf";
        }

        try {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Título
            com.itextpdf.text.Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.BLACK);
            Paragraph title = new Paragraph("Reporte del Sistema - Papelería", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // Fecha
            com.itextpdf.text.Font dateFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.GRAY);
            Paragraph date = new Paragraph("Fecha: " + new java.util.Date().toString(), dateFont);
            date.setAlignment(Element.ALIGN_CENTER);
            date.setSpacingAfter(30);
            document.add(date);

            // Estadísticas generales
            com.itextpdf.text.Font sectionFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.BLACK);
            Paragraph statsTitle = new Paragraph("Estadísticas Generales", sectionFont);
            statsTitle.setSpacingAfter(10);
            document.add(statsTitle);

            try (Connection cx = DriverManager.getConnection(
                    "jdbc:mysql://localhost/papeleria", "root", "")) {

                // Contar productos
                try (PreparedStatement ps = cx.prepareStatement("SELECT COUNT(*) FROM productos");
                     ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        document.add(new Paragraph("Total de Productos: " + rs.getInt(1)));
                    }
                }

                // Contar categorías
                try (PreparedStatement ps = cx.prepareStatement("SELECT COUNT(*) FROM categorias");
                     ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        document.add(new Paragraph("Total de Categorías: " + rs.getInt(1)));
                    }
                }

                document.add(new Paragraph(" ")); // Espacio

                // Tabla de productos
                Paragraph productsTitle = new Paragraph("Lista de Productos", sectionFont);
                productsTitle.setSpacingBefore(20);
                productsTitle.setSpacingAfter(10);
                document.add(productsTitle);

                PdfPTable table = new PdfPTable(6);
                table.setWidthPercentage(100);
                table.setWidths(new float[]{1, 3, 3, 2, 2, 2});

                // Encabezados
                table.addCell(crearCelda("ID", true));
                table.addCell(crearCelda("Nombre", true));
                table.addCell(crearCelda("Descripción", true));
                table.addCell(crearCelda("Precio Compra", true));
                table.addCell(crearCelda("Precio Venta", true));
                table.addCell(crearCelda("Stock Mín", true));

                // Datos
                try (PreparedStatement ps = cx.prepareStatement(
                        "SELECT p.id_producto, p.nombre, p.descripcion, p.precio_compra, " +
                                "p.precio_venta, p.stock_minimo, c.nombre AS categoria " +
                                "FROM productos p LEFT JOIN categorias c ON p.id_categoria = c.id_categoria " +
                                "ORDER BY p.id_producto");
                     ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {
                        table.addCell(crearCelda(String.valueOf(rs.getInt("id_producto")), false));
                        table.addCell(crearCelda(rs.getString("nombre"), false));
                        String desc = rs.getString("descripcion");
                        table.addCell(crearCelda(desc != null && desc.length() > 30 ? desc.substring(0, 30) + "..." : desc, false));
                        table.addCell(crearCelda(rs.getBigDecimal("precio_compra").toString(), false));
                        table.addCell(crearCelda(rs.getBigDecimal("precio_venta").toString(), false));
                        table.addCell(crearCelda(String.valueOf(rs.getInt("stock_minimo")), false));
                    }
                }

                document.add(table);

                // Tabla de categorías
                Paragraph categoriesTitle = new Paragraph("Lista de Categorías", sectionFont);
                categoriesTitle.setSpacingBefore(20);
                categoriesTitle.setSpacingAfter(10);
                document.add(categoriesTitle);

                PdfPTable catTable = new PdfPTable(3);
                catTable.setWidthPercentage(100);
                catTable.setWidths(new float[]{1, 3, 4});

                catTable.addCell(crearCelda("ID", true));
                catTable.addCell(crearCelda("Nombre", true));
                catTable.addCell(crearCelda("Descripción", true));

                try (PreparedStatement ps = cx.prepareStatement(
                        "SELECT id_categoria, nombre, descripcion FROM categorias ORDER BY id_categoria");
                     ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {
                        catTable.addCell(crearCelda(String.valueOf(rs.getInt("id_categoria")), false));
                        catTable.addCell(crearCelda(rs.getString("nombre"), false));
                        String desc = rs.getString("descripcion");
                        catTable.addCell(crearCelda(desc != null ? desc : "", false));
                    }
                }

                document.add(catTable);

            } catch (SQLException ex) {
                document.add(new Paragraph("Error al obtener datos: " + ex.getMessage()));
            }

            document.close();
            
            // Abrir el PDF automáticamente
            try {
                File pdfFile = new File(filePath);
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(pdfFile);
                } else {
                    // Fallback para sistemas que no soportan Desktop API
                    Runtime.getRuntime().exec("cmd /c start \"\" \"" + filePath + "\"");
                }
            } catch (Exception ex) {
                // Si no se puede abrir, solo mostrar el mensaje
                System.out.println("No se pudo abrir el PDF automáticamente: " + ex.getMessage());
            }
            
            JOptionPane.showMessageDialog(frame,
                    "Reporte PDF generado exitosamente y abierto.\nUbicación: " + filePath,
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame,
                    "Error al generar PDF: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private com.itextpdf.text.pdf.PdfPCell crearCelda(String texto, boolean esEncabezado) {
        com.itextpdf.text.pdf.PdfPCell cell = new com.itextpdf.text.pdf.PdfPCell(new Phrase(texto));
        if (esEncabezado) {
            cell.setBackgroundColor(new BaseColor(200, 200, 200));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.getRight();
        } else {
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.getRight();
        }
        cell.setPadding(5);
        return cell;
    }

    // ----------------------------------------------------------
    //               CREAR ICONO PDF PARA BOTÓN
    // ----------------------------------------------------------
    private ImageIcon crearIconoPDF(int width, int height) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Fondo rojo del icono PDF
        g2.setColor(new Color(200, 60, 60));
        g2.fillRoundRect(0, 0, width, height, 5, 5);
        
        // Borde blanco
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(1, 1, width - 3, height - 3, 5, 5);
        
        // Dibujar "PDF" en el icono
        g2.setFont(new Font("Segoe UI", Font.BOLD, height / 3));
        FontMetrics fm = g2.getFontMetrics();
        String text = "PDF";
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();
        g2.drawString(text, (width - textWidth) / 2, (height + textHeight) / 2 - 2);
        
        g2.dispose();
        return new ImageIcon(img);
    }

    // ----------------------------------------------------------
    //               CREAR ICONO X PARA BOTÓN CERRAR
    // ----------------------------------------------------------
    private ImageIcon crearIconoCerrar(int width, int height) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Dibujar X blanca
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2.5f));
        int margin = 3;
        g2.drawLine(margin, margin, width - margin, height - margin);
        g2.drawLine(width - margin, margin, margin, height - margin);
        
        g2.dispose();
        return new ImageIcon(img);
    }
}