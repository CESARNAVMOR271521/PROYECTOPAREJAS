package parcial3;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriasPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;

    private SwingWorker<Void, Object[]> searchWorker;

    private static final String DB_URL = "jdbc:mysql://localhost/papeleria";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";

    // Listener de selección
    public interface CategorySelectionListener { void categorySelected(int id); }
    private final List<CategorySelectionListener> categoryListeners = new ArrayList<>();
    public void addCategorySelectionListener(CategorySelectionListener l) { categoryListeners.add(l); }
    private void notifyCategorySelected(int id) { for (CategorySelectionListener l : categoryListeners) l.categorySelected(id); }

    // Listener de cambios (para notificar a otros paneles)
    private final List<Runnable> changeListeners = new ArrayList<>();
    public void addChangesListener(Runnable r) { changeListeners.add(r); }
    private void notifyChanges() { for (Runnable r : changeListeners) { try { r.run(); } catch (Exception ignored) {} } }

    public CategoriasPanel() {

        setLayout(new BorderLayout());
        setBackground(new Color(20, 20, 20));

        /* ------------------------------
         *         TÍTULO
         * ------------------------------*/
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(new Color(25,25,25));
        top.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel title = new JLabel("📁 Gestión de Categorías");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(Color.WHITE);

        top.add(title, BorderLayout.WEST);
        add(top, BorderLayout.NORTH);

        /* ------------------------------
         *         BUSCADOR
         * ------------------------------*/
        JPanel searchPanel = new JPanel(new BorderLayout(10, 10));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        searchPanel.setBackground(new Color(25, 25, 25));

        JTextField txtSearch = new JTextField();
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtSearch.setBackground(new Color(40, 40, 40));
        txtSearch.setForeground(Color.WHITE);
        txtSearch.setCaretColor(Color.WHITE);
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(80, 80, 80), 1),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));

        JButton btnSearch = crearBoton("Buscar", new Color(70, 130, 180), loadIcon("search.png"));
        JButton btnClear  = crearBoton("Limpiar", new Color(150, 70, 70), loadIcon("clear.png"));

        JPanel rightSearch = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        rightSearch.setBackground(new Color(25, 25, 25));
        rightSearch.add(btnSearch);
        rightSearch.add(btnClear);

        searchPanel.add(txtSearch, BorderLayout.CENTER);
        searchPanel.add(rightSearch, BorderLayout.EAST);
        add(searchPanel, BorderLayout.BEFORE_FIRST_LINE);

        btnSearch.addActionListener(e -> buscarCategorias(txtSearch.getText()));
        btnClear.addActionListener(e -> { txtSearch.setText(""); cargarCategorias(); });
        txtSearch.addActionListener(e -> buscarCategorias(txtSearch.getText()));

        // Búsqueda en vivo
        final Timer debounce = new Timer(300, e -> buscarCategorias(txtSearch.getText()));
        debounce.setRepeats(false);

        txtSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override public void insertUpdate(javax.swing.event.DocumentEvent e) { debounce.restart(); }
            @Override public void removeUpdate(javax.swing.event.DocumentEvent e) { debounce.restart(); }
            @Override public void changedUpdate(javax.swing.event.DocumentEvent e) { debounce.restart(); }
        });

        /* ------------------------------
         *          TABLA
         * ------------------------------*/
        model = new DefaultTableModel(
                new Object[]{"ID", "Nombre", "Descripción"}, 0
        ) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(model) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component c = super.prepareRenderer(renderer, row, col);

                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? new Color(34, 34, 34) : new Color(28, 28, 28));
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(new Color(60, 130, 230));
                    c.setForeground(Color.WHITE);
                }

                return c;
            }
        };

        table.setRowHeight(30);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        JTableHeader th = table.getTableHeader();
        th.setBackground(new Color(45, 45, 45));
        th.setForeground(Color.WHITE);
        th.setFont(new Font("Segoe UI", Font.BOLD, 15));

        JScrollPane scroll = new JScrollPane(table);
        scroll.getViewport().setBackground(new Color(30, 30, 30));
        scroll.setBorder(BorderFactory.createEmptyBorder());

        add(scroll, BorderLayout.CENTER);

        /* ------------------------------
         *          BOTONES CRUD
         * ------------------------------*/
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        bottom.setBackground(new Color(25, 25, 25));

        JButton btnAdd    = crearBoton("Agregar", new Color(45, 150, 90), loadIcon("add.png"));
        JButton btnEdit   = crearBoton("Editar",  new Color(70, 120, 200), loadIcon("edit.png"));
        JButton btnDelete = crearBoton("Eliminar", new Color(180, 60, 60), loadIcon("delete.png"));

        bottom.add(btnAdd);
        bottom.add(btnEdit);
        bottom.add(btnDelete);

        add(bottom, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> agregarCategoria());
        btnEdit.addActionListener(e -> editarCategoria());
        btnDelete.addActionListener(e -> eliminarCategoria());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int r = table.getSelectedRow();
                notifyCategorySelected(r >= 0 ? (int) model.getValueAt(r, 0) : -1);
            }
        });

        cargarCategorias();
    }

    /* ------------------------------
     *     CARGAR ICONOS
     * ------------------------------*/
    private ImageIcon loadIcon(String fileName) {
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/" + fileName));

        // Escalar el icono a 20x20 px
        Image scaled = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);

        return new ImageIcon(scaled);
    }

    /* ------------------------------
     *     BOTÓN ESTILIZADO
     * ------------------------------*/
    private JButton crearBoton(String txt, Color base, ImageIcon icon) {
        JButton b = new JButton(txt, icon);
        b.setFont(new Font("Segoe UI", Font.BOLD, 15));
        b.setForeground(Color.WHITE);
        b.setBackground(base);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setIconTextGap(10);

        b.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                b.setBackground(base.brighter());
            }
            @Override public void mouseExited(MouseEvent e) {
                b.setBackground(base);
            }
        });

        return b;
    }

    /* ------------------------------
     *        MYSQL
     * ------------------------------*/
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    /* ------------------------------
     *      CARGAR CATEGORÍAS
     * ------------------------------*/
    private void cargarCategorias() {
        buscarCategorias("");
    }

    /* ------------------------------
     *     BÚSQUEDA OPTIMIZADA
     * ------------------------------*/
    private void buscarCategorias(String query) {

        if (searchWorker != null && !searchWorker.isDone()) {
            searchWorker.cancel(true);
        }

        model.setRowCount(0);

        searchWorker = new SwingWorker<>() {

            @Override
            protected Void doInBackground() throws Exception {

                String sql;
                boolean esBusqueda = query != null && !query.trim().isEmpty();

                if (esBusqueda) {
                    sql = "SELECT id_categoria, nombre, descripcion FROM categorias " +
                            "WHERE nombre LIKE ? OR descripcion LIKE ?";
                } else {
                    sql = "SELECT id_categoria, nombre, descripcion FROM categorias";
                }

                try (Connection cx = getConnection();
                     PreparedStatement ps = cx.prepareStatement(sql)) {

                    if (esBusqueda) {
                        String q = "%" + query.trim() + "%";
                        ps.setString(1, q);
                        ps.setString(2, q);
                    }

                    try (ResultSet rs = ps.executeQuery()) {
                        while (!isCancelled() && rs.next()) {
                            publish(new Object[]{
                                    rs.getInt("id_categoria"),
                                    rs.getString("nombre"),
                                    rs.getString("descripcion")
                            });
                        }
                    }
                }

                return null;
            }

            @Override
            protected void process(List<Object[]> rows) {
                for (Object[] r : rows) model.addRow(r);
            }

            @Override
            protected void done() {
                if (model.getRowCount() > 0) {
                    table.setRowSelectionInterval(0, 0);
                    notifyCategorySelected((int) model.getValueAt(0, 0));
                } else {
                    notifyCategorySelected(-1);
                }
            }
        };

        searchWorker.execute();
    }

    /* ------------------------------
     *               CRUD
     * ------------------------------*/
    private void agregarCategoria() {
        JTextField name = new JTextField();
        JTextField desc = new JTextField();

        int opt = JOptionPane.showConfirmDialog(
                this, new Object[]{"Nombre:", name, "Descripción:", desc},
                "Agregar Categoría", JOptionPane.OK_CANCEL_OPTION
        );

        if (opt != JOptionPane.OK_OPTION) return;
        if (name.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio.");
            return;
        }

        try (Connection cx = getConnection();
             PreparedStatement ps = cx.prepareStatement("INSERT INTO categorias VALUES(NULL, ?, ?)")) {

            ps.setString(1, name.getText());
            ps.setString(2, desc.getText());
            ps.executeUpdate();
            cargarCategorias();
            notifyChanges();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar: " + ex.getMessage());
        }
    }

    private void editarCategoria() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una categoría.");
            return;
        }

        int id = (int) model.getValueAt(row, 0);
        String currentName = (String) model.getValueAt(row, 1);
        String currentDesc = (String) model.getValueAt(row, 2);

        JTextField name = new JTextField(currentName);
        JTextField desc = new JTextField(currentDesc);

        int opt = JOptionPane.showConfirmDialog(
                this, new Object[]{"Nombre:", name, "Descripción:", desc},
                "Editar Categoría", JOptionPane.OK_CANCEL_OPTION
        );

        if (opt != JOptionPane.OK_OPTION) return;

        try (Connection cx = getConnection();
             PreparedStatement ps = cx.prepareStatement(
                     "UPDATE categorias SET nombre=?, descripcion=? WHERE id_categoria=?")) {

            ps.setString(1, name.getText());
            ps.setString(2, desc.getText());
            ps.setInt(3, id);

            ps.executeUpdate();
            cargarCategorias();
            notifyChanges();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al editar: " + ex.getMessage());
        }
    }

    private void eliminarCategoria() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una categoría.");
            return;
        }

        int id = (int) model.getValueAt(row, 0);

        try (Connection cx = getConnection();
             PreparedStatement ck = cx.prepareStatement(
                     "SELECT COUNT(*) AS c FROM productos WHERE id_categoria=?")) {

            ck.setInt(1, id);
            ResultSet rs = ck.executeQuery();

            if (rs.next() && rs.getInt("c") > 0) {
                JOptionPane.showMessageDialog(this, "No se puede eliminar: tiene productos asociados.");
                return;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error verificando relación: " + ex.getMessage());
            return;
        }

        int opt = JOptionPane.showConfirmDialog(this, "¿Eliminar categoría?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opt != JOptionPane.YES_OPTION) return;

        try (Connection cx = getConnection();
             PreparedStatement ps = cx.prepareStatement("DELETE FROM categorias WHERE id_categoria=?")) {

            ps.setInt(1, id);
            ps.executeUpdate();
            cargarCategorias();
            notifyChanges();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage());
        }
    }
}
