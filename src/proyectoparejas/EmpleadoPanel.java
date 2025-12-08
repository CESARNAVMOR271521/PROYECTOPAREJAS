package proyectoparejas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class EmpleadoPanel extends JPanel {
    
    private JTable table;
    private DefaultTableModel model;
    
    public EmpleadoPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(15, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título
        JLabel titulo = new JLabel("🧑‍💼 Gestión de Empleados");
        titulo.setFont(new Font("Consolas", Font.BOLD, 32));
        titulo.setForeground(new Color(86, 215, 255));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        add(titulo, BorderLayout.NORTH);
        
        // Tabla
        String[] columnas = {"ID", "Nombre", "Apellidos", "Cargo", "Teléfono", "Email", "Estado"};
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
        
        btnAgregar.addActionListener(e -> agregarEmpleado());
        btnEditar.addActionListener(e -> editarEmpleado());
        btnEliminar.addActionListener(e -> eliminarEmpleado());
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
        // Ejemplo de datos de demostración:
        model.addRow(new Object[]{1, "Juan", "Pérez García", "Vendedor", "555-0101", "juan@empresa.com", "Activo"});
        model.addRow(new Object[]{2, "María", "López Sánchez", "Gerente", "555-0102", "maria@empresa.com", "Activo"});
        model.addRow(new Object[]{3, "Carlos", "González Martínez", "Cajero", "555-0103", "carlos@empresa.com", "Activo"});
    }
    
    private void agregarEmpleado() {
        JTextField nombre = new JTextField();
        JTextField apellidos = new JTextField();
        JTextField cargo = new JTextField();
        JTextField telefono = new JTextField();
        JTextField email = new JTextField();
        JComboBox<String> estado = new JComboBox<>(new String[]{"Activo", "Inactivo"});
        
        Object[] mensaje = {
            "Nombre:", nombre,
            "Apellidos:", apellidos,
            "Cargo:", cargo,
            "Teléfono:", telefono,
            "Email:", email,
            "Estado:", estado
        };
        
        int opcion = JOptionPane.showConfirmDialog(
            this,
            mensaje,
            "Agregar Empleado",
            JOptionPane.OK_CANCEL_OPTION
        );
        
        if (opcion == JOptionPane.OK_OPTION) {
            try {
                int id = model.getRowCount() + 1;
                model.addRow(new Object[]{
                    id,
                    nombre.getText(),
                    apellidos.getText(),
                    cargo.getText(),
                    telefono.getText(),
                    email.getText(),
                    estado.getSelectedItem()
                });
                JOptionPane.showMessageDialog(this, "Empleado agregado correctamente");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al agregar empleado: " + e.getMessage());
            }
        }
    }
    
    private void editarEmpleado() {
        int fila = table.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un empleado para editar");
            return;
        }
        
        JTextField nombre = new JTextField(model.getValueAt(fila, 1).toString());
        JTextField apellidos = new JTextField(model.getValueAt(fila, 2).toString());
        JTextField cargo = new JTextField(model.getValueAt(fila, 3).toString());
        JTextField telefono = new JTextField(model.getValueAt(fila, 4).toString());
        JTextField email = new JTextField(model.getValueAt(fila, 5).toString());
        JComboBox<String> estado = new JComboBox<>(new String[]{"Activo", "Inactivo"});
        estado.setSelectedItem(model.getValueAt(fila, 6).toString());
        
        Object[] mensaje = {
            "Nombre:", nombre,
            "Apellidos:", apellidos,
            "Cargo:", cargo,
            "Teléfono:", telefono,
            "Email:", email,
            "Estado:", estado
        };
        
        int opcion = JOptionPane.showConfirmDialog(
            this,
            mensaje,
            "Editar Empleado",
            JOptionPane.OK_CANCEL_OPTION
        );
        
        if (opcion == JOptionPane.OK_OPTION) {
            try {
                model.setValueAt(nombre.getText(), fila, 1);
                model.setValueAt(apellidos.getText(), fila, 2);
                model.setValueAt(cargo.getText(), fila, 3);
                model.setValueAt(telefono.getText(), fila, 4);
                model.setValueAt(email.getText(), fila, 5);
                model.setValueAt(estado.getSelectedItem(), fila, 6);
                JOptionPane.showMessageDialog(this, "Empleado editado correctamente");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al editar empleado: " + e.getMessage());
            }
        }
    }
    
    private void eliminarEmpleado() {
        int fila = table.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un empleado para eliminar");
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de eliminar este empleado?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            model.removeRow(fila);
            JOptionPane.showMessageDialog(this, "Empleado eliminado correctamente");
        }
    }
}

