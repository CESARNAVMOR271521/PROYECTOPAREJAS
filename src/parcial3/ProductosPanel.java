package parcial3;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.sql.*;

public class ProductosPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private JComboBox<CategoryItem> comboCategorias;

    private static final String DB_URL = "jdbc:mysql://localhost/papeleria";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";

    public ProductosPanel() {

        setLayout(new BorderLayout());
        setBackground(new Color(30, 30, 35));

        // ================================
        // PANEL SUPERIOR - TÍTULO + COMBO
        // ================================
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(30, 30, 35));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel titulo = new JLabel("📦 Gestión de Productos");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);

        JPanel comboPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        comboPanel.setBackground(new Color(30, 30, 35));

        JLabel lblCat = new JLabel("Categoría:");
        lblCat.setForeground(Color.WHITE);
        lblCat.setFont(new Font("Segoe UI", Font.BOLD, 14));

        comboCategorias = new JComboBox<>(loadCategories());
        comboCategorias.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboCategorias.setBackground(new Color(60, 60, 70));
        comboCategorias.setForeground(Color.WHITE);

        comboPanel.add(lblCat);
        comboPanel.add(comboCategorias);

        topPanel.add(titulo, BorderLayout.WEST);
        topPanel.add(comboPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        comboCategorias.addActionListener(e -> {
            CategoryItem item = (CategoryItem) comboCategorias.getSelectedItem();
            filterByCategory(item.id);
        });

        // ================================
        // MODELO Y TABLA
        // ================================
        model = new DefaultTableModel(
                new Object[]{"ID", "Nombre", "Descripción", "Precio Compra",
                        "Precio Venta", "Stock Min", "Categoría",
                        "Activo", "Imagen (BLOB)"}, 0
        ) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(28);
        table.getTableHeader().setReorderingAllowed(false);

        table.getTableHeader().setBackground(new Color(50, 50, 60));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setBackground(new Color(40, 40, 50));
        table.setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(70, 120, 200));
        table.setSelectionForeground(Color.WHITE);

        TableColumn colBlob = table.getColumnModel().getColumn(8);
        colBlob.setMinWidth(0);
        colBlob.setMaxWidth(0);
        colBlob.setPreferredWidth(0);

        JScrollPane scroll = new JScrollPane(table);
        scroll.getViewport().setBackground(new Color(40, 40, 50));
        scroll.setBorder(BorderFactory.createEmptyBorder());

        // ================================
        // PANEL DE IMAGEN
        // ================================
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBackground(new Color(35, 35, 42));
        imagePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                " Vista previa ",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 14),
                Color.WHITE
        ));

        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imagePanel.add(imageLabel, BorderLayout.CENTER);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scroll, imagePanel);
        split.setDividerLocation(700);
        split.setResizeWeight(0.80);
        split.setBackground(new Color(45, 45, 55));

        add(split, BorderLayout.CENTER);

        // ================================
        // BOTONES INFERIORES
        // ================================
        JPanel botones = new JPanel();
        botones.setBackground(new Color(30, 30, 35));

        JButton btnAdd = botonModerno("Agregar", new Color(60, 170, 90), loadIcon("add.png"));
        JButton btnEdit = botonModerno("Editar", new Color(70, 140, 200), loadIcon("edit.png"));
        JButton btnDelete = botonModerno("Eliminar", new Color(200, 70, 70), loadIcon("delete.png"));

        botones.add(btnAdd);
        botones.add(btnEdit);
        botones.add(btnDelete);
        add(botones, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> agregarProducto());
        btnEdit.addActionListener(e -> editarProducto());
        btnDelete.addActionListener(e -> eliminarProducto());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    Object blobObj = model.getValueAt(row, 8);

                    if (blobObj instanceof byte[]) {
                        ImageIcon icon = bytesToImage(
                                (byte[]) blobObj,
                                imagePanel.getWidth() - 20,
                                imagePanel.getHeight() - 20
                        );
                        imageLabel.setIcon(icon);
                    } else {
                        imageLabel.setIcon(null);
                    }
                }
            }
        });

        cargarProductos();
    }

    // ============================================
    //   CARGAR ICONOS DESDE /icons/
    // ============================================
    private ImageIcon loadIcon(String fileName) {
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/" + fileName));

        // Escalar el icono a 20x20 px
        Image scaled = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);

        return new ImageIcon(scaled);
    }

    // ============================================
    //       CONVERTIR IMAGEN ↔ BYTES (BLOB)
    // ============================================
    private byte[] imageToBytes(String path) {
        try {
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            byte[] bytes = fis.readAllBytes();
            fis.close();
            return bytes;

        } catch (Exception e) {
            System.out.println("Error convirtiendo imagen: " + e.getMessage());
            return null;
        }
    }

    private ImageIcon bytesToImage(byte[] bytes, int w, int h) {
        if (bytes == null) return null;

        Image img = Toolkit.getDefaultToolkit().createImage(bytes);
        Image scaled = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    // ============================================
    // BOTONES MODERNOS + ICONOS
    // ============================================
    private JButton botonModerno(String text, Color baseColor, ImageIcon icon) {
        JButton btn = new JButton(text, icon);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(baseColor);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setIconTextGap(10);

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(baseColor.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(baseColor);
            }
        });

        return btn;
    }

    // ============================================
    //              CARGAR CATEGORÍAS
    // ============================================
    private static class CategoryItem {
        int id;
        String name;

        public CategoryItem(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private DefaultComboBoxModel<CategoryItem> loadCategories() {
        DefaultComboBoxModel<CategoryItem> model = new DefaultComboBoxModel<>();
        model.addElement(new CategoryItem(-1, "⭐ Todas"));

        try (Connection cx = getConnection();
             PreparedStatement ps = cx.prepareStatement("SELECT id_categoria, nombre FROM categorias");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                model.addElement(new CategoryItem(
                        rs.getInt("id_categoria"),
                        rs.getString("nombre")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Error cargando categorías: " + e.getMessage());
        }

        return model;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    // =================================================
    //                   CARGAR PRODUCTOS
    // =================================================
    void cargarProductos() {
        cargarProductos(-1);
    }

    public void cargarProductos(int idCategoria) {
        model.setRowCount(0);

        String sql =
                "SELECT p.id_producto, p.nombre, p.descripcion, p.precio_compra, p.precio_venta, "
                        + "p.stock_minimo, c.nombre AS categoria, p.activo, p.imagen "
                        + "FROM productos p "
                        + "LEFT JOIN categorias c ON p.id_categoria = c.id_categoria";

        if (idCategoria > 0)
            sql += " WHERE p.id_categoria = ?";

        try (Connection cx = getConnection();
             PreparedStatement ps = cx.prepareStatement(sql)) {

            if (idCategoria > 0)
                ps.setInt(1, idCategoria);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getInt("id_producto"),
                            rs.getString("nombre"),
                            rs.getString("descripcion"),
                            rs.getBigDecimal("precio_compra"),
                            rs.getBigDecimal("precio_venta"),
                            rs.getInt("stock_minimo"),
                            rs.getString("categoria"),
                            rs.getInt("activo"),
                            rs.getBytes("imagen")
                    });
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error cargando productos: " + ex.getMessage());
        }
    }

    public void filterByCategory(int id) {
        if (id <= 0) cargarProductos();
        else cargarProductos(id);
    }

    // =================================================
    //                    CRUD — AGREGAR
    // =================================================
    private void agregarProducto() {

        JTextField nombre = new JTextField();
        JTextField desc = new JTextField();
        JTextField pc = new JTextField();
        JTextField pv = new JTextField();
        JTextField stock = new JTextField();
        JTextField categoria = new JTextField();
        JTextField imgPath = new JTextField();

        JButton btnImg = new JButton("Seleccionar imagen");

        btnImg.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
                imgPath.setText(fc.getSelectedFile().getAbsolutePath());
        });

        JPanel imgPanel = new JPanel(new BorderLayout());
        imgPanel.add(imgPath, BorderLayout.CENTER);
        imgPanel.add(btnImg, BorderLayout.EAST);

        JCheckBox activoBox = new JCheckBox("Activo", true);

        Object[] msg = {
                "Nombre:", nombre,
                "Descripción:", desc,
                "Precio compra:", pc,
                "Precio venta:", pv,
                "Stock mínimo:", stock,
                "ID Categoría:", categoria,
                "Imagen:", imgPanel,
                activoBox
        };

        if (JOptionPane.showConfirmDialog(this, msg, "Agregar Producto",
                JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION)
            return;

        try (Connection cx = getConnection();
             PreparedStatement ps = cx.prepareStatement(
                     "INSERT INTO productos VALUES (NULL,?,?,?,?,?,?,?,?,?)"
             )) {

            ps.setString(1, nombre.getText());
            ps.setString(2, desc.getText());
            ps.setBigDecimal(3, new java.math.BigDecimal(pc.getText()));
            ps.setBigDecimal(4, new java.math.BigDecimal(pv.getText()));
            ps.setInt(5, Integer.parseInt(stock.getText()));
            ps.setInt(6, Integer.parseInt(categoria.getText()));
            ps.setInt(7, activoBox.isSelected() ? 1 : 0);

            byte[] imgBytes = imageToBytes(imgPath.getText());
            ps.setBytes(8, imgBytes);

            ps.setString(9, "");

            if (ps.executeUpdate() > 0)
                cargarProductos();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar: " + ex.getMessage());
        }
    }

    // =================================================
    //                    CRUD — EDITAR
    // =================================================
    private void editarProducto() {

        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto.");
            return;
        }

        int id = (int) model.getValueAt(row, 0);

        JTextField nombre = new JTextField(model.getValueAt(row, 1).toString());
        JTextField desc = new JTextField(model.getValueAt(row, 2).toString());
        JTextField pc = new JTextField(model.getValueAt(row, 3).toString());
        JTextField pv = new JTextField(model.getValueAt(row, 4).toString());
        JTextField stock = new JTextField(model.getValueAt(row, 5).toString());
        JTextField categoria = new JTextField();

        JTextField imgPath = new JTextField();

        JButton btnImg = new JButton("Seleccionar nueva imagen");

        btnImg.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
                imgPath.setText(fc.getSelectedFile().getAbsolutePath());
        });

        JPanel imgPanel = new JPanel(new BorderLayout());
        imgPanel.add(imgPath, BorderLayout.CENTER);
        imgPanel.add(btnImg, BorderLayout.EAST);

        JCheckBox activoBox = new JCheckBox("Activo",
                model.getValueAt(row, 7).toString().equals("1"));

        Object[] msg = {
                "Nombre:", nombre,
                "Descripción:", desc,
                "Precio compra:", pc,
                "Precio venta:", pv,
                "Stock mínimo:", stock,
                "Categoría:", categoria,
                "Nueva imagen (opcional):", imgPanel,
                activoBox
        };

        if (JOptionPane.showConfirmDialog(this, msg, "Editar Producto",
                JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION)
            return;

        try (Connection cx = getConnection();
             PreparedStatement ps = cx.prepareStatement(
                     "UPDATE productos SET nombre=?, descripcion=?, precio_compra=?, precio_venta=?, "
                             + "stock_minimo=?, id_categoria=?, activo=?, imagen=? WHERE id_producto=?"
             )) {

            ps.setString(1, nombre.getText());
            ps.setString(2, desc.getText());
            ps.setBigDecimal(3, new java.math.BigDecimal(pc.getText()));
            ps.setBigDecimal(4, new java.math.BigDecimal(pv.getText()));
            ps.setInt(5, Integer.parseInt(stock.getText()));
            ps.setInt(6, Integer.parseInt(categoria.getText()));
            ps.setInt(7, activoBox.isSelected() ? 1 : 0);

            byte[] imgBytes = imgPath.getText().isEmpty()
                    ? (byte[]) model.getValueAt(row, 8)
                    : imageToBytes(imgPath.getText());

            ps.setBytes(8, imgBytes);
            ps.setInt(9, id);

            if (ps.executeUpdate() > 0)
                cargarProductos();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error editando: " + ex.getMessage());
        }
    }

    // =================================================
    //                    CRUD — ELIMINAR
    // =================================================
    private void eliminarProducto() {

        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto.");
            return;
        }

        int id = (int) model.getValueAt(row, 0);

        if (JOptionPane.showConfirmDialog(this,
                "¿Eliminar producto?",
                "Confirmación",
                JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION)
            return;

        try (Connection cx = getConnection();
             PreparedStatement ps = cx.prepareStatement(
                     "DELETE FROM productos WHERE id_producto=?"
             )) {

            ps.setInt(1, id);

            if (ps.executeUpdate() > 0)
                cargarProductos();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error eliminando: " + ex.getMessage());
        }
    }
}
