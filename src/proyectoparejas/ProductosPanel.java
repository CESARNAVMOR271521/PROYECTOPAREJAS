package proyectoparejas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class ProductosPanel extends JPanel {
    
    private JTable table;
    private DefaultTableModel model;
    
    public ProductosPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(15, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título
        JLabel titulo = new JLabel("📦 Gestión de Productos");
        titulo.setFont(new Font("Consolas", Font.BOLD, 32));
        titulo.setForeground(new Color(86, 215, 255));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        add(titulo, BorderLayout.NORTH);
        
        // Tabla
        String[] columnas = {"ID", "Nombre", "Descripción", "Precio", "Stock", "Categoría"};
        model = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(model);
        table.setFont(new Font("Consolas", Font.PLAIN, 14));
        table.setForeground(new Color(200, 220, 255));
        table.setBackground(new Color(30, 20, 15));
        table.setSelectionBackground(new Color(86, 215, 255));
        table.setSelectionForeground(new Color(15, 10, 10));
        table.setRowHeight(30);
        table.getTableHeader().setBackground(new Color(40, 25, 15));
        table.getTableHeader().setForeground(new Color(200, 155, 60));
        table.getTableHeader().setFont(new Font("Consolas", Font.BOLD, 14));
        table.setBorder(BorderFactory.createLineBorder(new Color(200, 155, 60), 2));
        
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(86, 215, 255), 1));
        scroll.getViewport().setBackground(new Color(30, 20, 15));
        
        add(scroll, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        panelBotones.setBackground(new Color(15, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        JButton btnAgregar = crearBotonAccion("Agregar", new Color(60, 170, 90));
        JButton btnEditar = crearBotonAccion("Editar", new Color(70, 140, 200));
        JButton btnEliminar = crearBotonAccion("Eliminar", new Color(200, 70, 70));
        JButton btnActualizar = crearBotonAccion("Actualizar", new Color(200, 155, 60));
        
        btnAgregar.addActionListener(e -> agregarProducto());
        btnEditar.addActionListener(e -> editarProducto());
        btnEliminar.addActionListener(e -> eliminarProducto());
        btnActualizar.addActionListener(e -> cargarDatos());
        
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnActualizar);
        
        add(panelBotones, BorderLayout.SOUTH);
        
        // Cargar datos iniciales
        cargarDatos();
    }
    
    private JButton crearBotonAccion(String texto, Color colorBase) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Consolas", Font.BOLD, 16));
        btn.setForeground(Color.WHITE);
        btn.setBackground(colorBase);
        btn.setBorder(BorderFactory.createLineBorder(new Color(86, 215, 255), 2));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(150, 40));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(colorBase.brighter());
                btn.setForeground(new Color(86, 215, 255));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(colorBase);
                btn.setForeground(Color.WHITE);
            }
        });
        
        return btn;
    }
    
    private void cargarDatos() {
        model.setRowCount(0);
        // Aquí se cargarían los datos de la base de datos
        // Ejemplo de datos de demostración:
        model.addRow(new Object[]{1, "Lápiz HB", "Lápiz grafito número 2", 5.50, 100, "Escritura"});
        model.addRow(new Object[]{2, "Cuaderno", "Cuaderno de 100 hojas", 25.00, 50, "Papelería"});
        model.addRow(new Object[]{3, "Goma", "Goma de borrar blanca", 3.00, 200, "Escritura"});
    }
    
    private void agregarProducto() {
        JTextField nombre = new JTextField();
        JTextField descripcion = new JTextField();
        JTextField precio = new JTextField();
        JTextField stock = new JTextField();
        JTextField categoria = new JTextField();
        
        Object[] mensaje = {
            "Nombre:", nombre,
            "Descripción:", descripcion,
            "Precio:", precio,
            "Stock:", stock,
            "Categoría:", categoria
        };
        
        int opcion = JOptionPane.showConfirmDialog(
            this,
            mensaje,
            "Agregar Producto",
            JOptionPane.OK_CANCEL_OPTION
        );
        
        if (opcion == JOptionPane.OK_OPTION) {
            try {
                int id = model.getRowCount() + 1;
                model.addRow(new Object[]{
                    id,
                    nombre.getText(),
                    descripcion.getText(),
                    Double.parseDouble(precio.getText()),
                    Integer.parseInt(stock.getText()),
                    categoria.getText()
                });
                JOptionPane.showMessageDialog(this, "Producto agregado correctamente");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al agregar producto: " + e.getMessage());
            }
        }
    }
    
    private void editarProducto() {
        int fila = table.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para editar");
            return;
        }
        
        JTextField nombre = new JTextField(model.getValueAt(fila, 1).toString());
        JTextField descripcion = new JTextField(model.getValueAt(fila, 2).toString());
        JTextField precio = new JTextField(model.getValueAt(fila, 3).toString());
        JTextField stock = new JTextField(model.getValueAt(fila, 4).toString());
        JTextField categoria = new JTextField(model.getValueAt(fila, 5).toString());
        
        Object[] mensaje = {
            "Nombre:", nombre,
            "Descripción:", descripcion,
            "Precio:", precio,
            "Stock:", stock,
            "Categoría:", categoria
        };
        
        int opcion = JOptionPane.showConfirmDialog(
            this,
            mensaje,
            "Editar Producto",
            JOptionPane.OK_CANCEL_OPTION
        );
        
        if (opcion == JOptionPane.OK_OPTION) {
            try {
                model.setValueAt(nombre.getText(), fila, 1);
                model.setValueAt(descripcion.getText(), fila, 2);
                model.setValueAt(Double.parseDouble(precio.getText()), fila, 3);
                model.setValueAt(Integer.parseInt(stock.getText()), fila, 4);
                model.setValueAt(categoria.getText(), fila, 5);
                JOptionPane.showMessageDialog(this, "Producto editado correctamente");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al editar producto: " + e.getMessage());
            }
        }
    }
    
    private void eliminarProducto() {
        int fila = table.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para eliminar");
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de eliminar este producto?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            model.removeRow(fila);
            JOptionPane.showMessageDialog(this, "Producto eliminado correctamente");
        }
    }
}

