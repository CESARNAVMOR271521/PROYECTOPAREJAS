package proyectoparejas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class ClientesPanel extends JPanel {
    
    private JTable table;
    private DefaultTableModel model;
    private DefaultTableModel modelOriginal;
    private JTextField txtBuscar;
    
    public ClientesPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(15, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título
        JLabel titulo = new JLabel("👥 Gestión de Clientes");
        titulo.setFont(new Font("Consolas", Font.BOLD, 32));
        titulo.setForeground(new Color(86, 215, 255));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new BorderLayout(10, 10));
        panelBusqueda.setBackground(new Color(15, 10, 10));
        panelBusqueda.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        JLabel lblBuscar = new JLabel("Buscar:");
        lblBuscar.setFont(new Font("Consolas", Font.BOLD, 16));
        lblBuscar.setForeground(new Color(200, 155, 60));
        
        txtBuscar = new JTextField();
        txtBuscar.setFont(new Font("Consolas", Font.PLAIN, 14));
        txtBuscar.setBackground(new Color(30, 20, 15));
        txtBuscar.setForeground(new Color(200, 220, 255));
        txtBuscar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(86, 215, 255), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        JButton btnBuscar = crearBotonAccion("Buscar", new Color(70, 140, 200));
        btnBuscar.setPreferredSize(new Dimension(100, 35));
        btnBuscar.addActionListener(e -> buscarCliente());
        
        panelBusqueda.add(lblBuscar, BorderLayout.WEST);
        panelBusqueda.add(txtBuscar, BorderLayout.CENTER);
        panelBusqueda.add(btnBuscar, BorderLayout.EAST);
        
        // Panel superior con título y búsqueda
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(new Color(15, 10, 10));
        panelSuperior.add(titulo, BorderLayout.NORTH);
        panelSuperior.add(panelBusqueda, BorderLayout.SOUTH);
        add(panelSuperior, BorderLayout.NORTH);
        
        // Tabla
        String[] columnas = {"ID", "Nombre", "Apellidos", "Teléfono", "Email", "Dirección", "Ciudad"};
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
        
        btnAgregar.addActionListener(e -> agregarCliente());
        btnEditar.addActionListener(e -> editarCliente());
        btnEliminar.addActionListener(e -> eliminarCliente());
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
        if (modelOriginal == null) {
            String[] columnas = {"ID", "Nombre", "Apellidos", "Teléfono", "Email", "Dirección", "Ciudad"};
            modelOriginal = new DefaultTableModel(columnas, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
        }
        
        modelOriginal.setRowCount(0);
        // Ejemplo de datos de demostración:
        modelOriginal.addRow(new Object[]{1, "Ana", "García Ruiz", "555-1001", "ana@email.com", "Calle Principal 123", "Ciudad A"});
        modelOriginal.addRow(new Object[]{2, "Luis", "Martínez Torres", "555-1002", "luis@email.com", "Av. Central 456", "Ciudad B"});
        modelOriginal.addRow(new Object[]{3, "Sofía", "Rodríguez López", "555-1003", "sofia@email.com", "Calle Secundaria 789", "Ciudad A"});
        
        // Restaurar modelo original
        table.setModel(modelOriginal);
        model = modelOriginal;
    }
    
    private void buscarCliente() {
    	String busqueda = txtBuscar.getText().toLowerCase();
    	if (busqueda.isEmpty()) {
    	    cargarDatos();
    	    return;
    	}

    	if (modelOriginal == null) {
    	    cargarDatos();
    	}

    	// Obtener nombres de columnas correctamente
    	String[] nombresColumnas = new String[modelOriginal.getColumnCount()];
    	for (int i = 0; i < modelOriginal.getColumnCount(); i++) {
    	    nombresColumnas[i] = modelOriginal.getColumnName(i);
    	}

    	DefaultTableModel modeloFiltrado = new DefaultTableModel(nombresColumnas, 0) {
    	    @Override
    	    public boolean isCellEditable(int row, int column) {
    	        return false;
    	    }
    	};

    	for (int i = 0; i < modelOriginal.getRowCount(); i++) {
    	    boolean coincide = false;

    	    for (int j = 0; j < modelOriginal.getColumnCount(); j++) {
    	        Object valor = modelOriginal.getValueAt(i, j);
    	        if (valor != null && valor.toString().toLowerCase().contains(busqueda)) {
    	            coincide = true;
    	            break;
    	        }
    	    }

    	    if (coincide) {
    	        Object[] fila = new Object[modelOriginal.getColumnCount()];
    	        for (int j = 0; j < modelOriginal.getColumnCount(); j++) {
    	            fila[j] = modelOriginal.getValueAt(i, j);
    	        }
    	        modeloFiltrado.addRow(fila);
    	    }
    	}

    	table.setModel(modeloFiltrado);
    	model = modeloFiltrado;

    }
    
    private void agregarCliente() {
        JTextField nombre = new JTextField();
        JTextField apellidos = new JTextField();
        JTextField telefono = new JTextField();
        JTextField email = new JTextField();
        JTextField direccion = new JTextField();
        JTextField ciudad = new JTextField();
        
        Object[] mensaje = {
            "Nombre:", nombre,
            "Apellidos:", apellidos,
            "Teléfono:", telefono,
            "Email:", email,
            "Dirección:", direccion,
            "Ciudad:", ciudad
        };
        
        int opcion = JOptionPane.showConfirmDialog(
            this,
            mensaje,
            "Agregar Cliente",
            JOptionPane.OK_CANCEL_OPTION
        );
        
        if (opcion == JOptionPane.OK_OPTION) {
            try {
                // Asegurar que usamos el modelo original
                if (modelOriginal == null) {
                    cargarDatos();
                }
                
                int id = modelOriginal.getRowCount() + 1;
                modelOriginal.addRow(new Object[]{
                    id,
                    nombre.getText(),
                    apellidos.getText(),
                    telefono.getText(),
                    email.getText(),
                    direccion.getText(),
                    ciudad.getText()
                });
                
                // Actualizar la vista
                buscarCliente();
                JOptionPane.showMessageDialog(this, "Cliente agregado correctamente");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al agregar cliente: " + e.getMessage());
            }
        }
    }
    
    private void editarCliente() {
        int fila = table.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente para editar");
            return;
        }
        
        JTextField nombre = new JTextField(model.getValueAt(fila, 1).toString());
        JTextField apellidos = new JTextField(model.getValueAt(fila, 2).toString());
        JTextField telefono = new JTextField(model.getValueAt(fila, 3).toString());
        JTextField email = new JTextField(model.getValueAt(fila, 4).toString());
        JTextField direccion = new JTextField(model.getValueAt(fila, 5).toString());
        JTextField ciudad = new JTextField(model.getValueAt(fila, 6).toString());
        
        Object[] mensaje = {
            "Nombre:", nombre,
            "Apellidos:", apellidos,
            "Teléfono:", telefono,
            "Email:", email,
            "Dirección:", direccion,
            "Ciudad:", ciudad
        };
        
        int opcion = JOptionPane.showConfirmDialog(
            this,
            mensaje,
            "Editar Cliente",
            JOptionPane.OK_CANCEL_OPTION
        );
        
        if (opcion == JOptionPane.OK_OPTION) {
            try {
                // Obtener el ID de la fila seleccionada (puede ser del modelo filtrado)
                int idSeleccionado = Integer.parseInt(model.getValueAt(fila, 0).toString());
                
                // Buscar en el modelo original y actualizar
                if (modelOriginal == null) {
                    cargarDatos();
                }
                
                for (int i = 0; i < modelOriginal.getRowCount(); i++) {
                    if (Integer.parseInt(modelOriginal.getValueAt(i, 0).toString()) == idSeleccionado) {
                        modelOriginal.setValueAt(nombre.getText(), i, 1);
                        modelOriginal.setValueAt(apellidos.getText(), i, 2);
                        modelOriginal.setValueAt(telefono.getText(), i, 3);
                        modelOriginal.setValueAt(email.getText(), i, 4);
                        modelOriginal.setValueAt(direccion.getText(), i, 5);
                        modelOriginal.setValueAt(ciudad.getText(), i, 6);
                        break;
                    }
                }
                
                // Actualizar la vista
                buscarCliente();
                JOptionPane.showMessageDialog(this, "Cliente editado correctamente");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al editar cliente: " + e.getMessage());
            }
        }
    }
    
    private void eliminarCliente() {
        int fila = table.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente para eliminar");
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de eliminar este cliente?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                // Obtener el ID de la fila seleccionada
                int idSeleccionado = Integer.parseInt(model.getValueAt(fila, 0).toString());
                
                // Eliminar del modelo original
                if (modelOriginal == null) {
                    cargarDatos();
                }
                
                for (int i = 0; i < modelOriginal.getRowCount(); i++) {
                    if (Integer.parseInt(modelOriginal.getValueAt(i, 0).toString()) == idSeleccionado) {
                        modelOriginal.removeRow(i);
                        break;
                    }
                }
                
                // Actualizar la vista
                buscarCliente();
                JOptionPane.showMessageDialog(this, "Cliente eliminado correctamente");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar cliente: " + e.getMessage());
            }
        }
    }
}

