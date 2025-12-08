package proyectoparejas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;

public class VentaPanel extends JPanel {
    
    private JTable tableProductos;
    private JTable tableVenta;
    private DefaultTableModel modelProductos;
    private DefaultTableModel modelVenta;
    private JLabel lblTotal;
    private JTextField txtCantidad;
    private DecimalFormat formato = new DecimalFormat("#.##");
    
    public VentaPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(15, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título
        JLabel titulo = new JLabel("💰 Proceso de Venta");
        titulo.setFont(new Font("Consolas", Font.BOLD, 32));
        titulo.setForeground(new Color(86, 215, 255));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        add(titulo, BorderLayout.NORTH);
        
        // Panel principal dividido
        JSplitPane splitPrincipal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPrincipal.setDividerLocation(600);
        splitPrincipal.setResizeWeight(0.6);
        splitPrincipal.setBackground(new Color(15, 10, 10));
        
        // Panel izquierdo - Productos disponibles
        JPanel panelProductos = new JPanel(new BorderLayout());
        panelProductos.setBackground(new Color(15, 10, 10));
        panelProductos.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(86, 215, 255), 2),
            " Productos Disponibles ",
            0, 0,
            new Font("Consolas", Font.BOLD, 16),
            new Color(200, 155, 60)
        ));
        
        String[] columnasProductos = {"ID", "Nombre", "Precio", "Stock"};
        modelProductos = new DefaultTableModel(columnasProductos, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableProductos = new JTable(modelProductos);
        tableProductos.setFont(new Font("Consolas", Font.PLAIN, 14));
        tableProductos.setForeground(new Color(200, 220, 255));
        tableProductos.setBackground(new Color(30, 20, 15));
        tableProductos.setSelectionBackground(new Color(86, 215, 255));
        tableProductos.setSelectionForeground(new Color(15, 10, 10));
        tableProductos.setRowHeight(30);
        tableProductos.getTableHeader().setBackground(new Color(40, 25, 15));
        tableProductos.getTableHeader().setForeground(new Color(200, 155, 60));
        tableProductos.getTableHeader().setFont(new Font("Consolas", Font.BOLD, 14));
        
        JScrollPane scrollProductos = new JScrollPane(tableProductos);
        scrollProductos.getViewport().setBackground(new Color(30, 20, 15));
        panelProductos.add(scrollProductos, BorderLayout.CENTER);
        
        // Panel de controles para agregar producto
        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelControles.setBackground(new Color(15, 10, 10));
        
        JLabel lblCantidad = new JLabel("Cantidad:");
        lblCantidad.setFont(new Font("Consolas", Font.BOLD, 14));
        lblCantidad.setForeground(new Color(200, 155, 60));
        
        txtCantidad = new JTextField("1", 5);
        txtCantidad.setFont(new Font("Consolas", Font.PLAIN, 14));
        txtCantidad.setBackground(new Color(30, 20, 15));
        txtCantidad.setForeground(new Color(200, 220, 255));
        txtCantidad.setBorder(BorderFactory.createLineBorder(new Color(86, 215, 255), 1));
        
        JButton btnAgregar = crearBotonAccion("Agregar al Carrito", new Color(60, 170, 90));
        btnAgregar.setPreferredSize(new Dimension(180, 35));
        btnAgregar.addActionListener(e -> agregarAlCarrito());
        
        panelControles.add(lblCantidad);
        panelControles.add(txtCantidad);
        panelControles.add(btnAgregar);
        panelProductos.add(panelControles, BorderLayout.SOUTH);
        
        // Panel derecho - Carrito de compra
        JPanel panelCarrito = new JPanel(new BorderLayout());
        panelCarrito.setBackground(new Color(15, 10, 10));
        panelCarrito.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 155, 60), 2),
            " Carrito de Compra ",
            0, 0,
            new Font("Consolas", Font.BOLD, 16),
            new Color(200, 155, 60)
        ));
        
        String[] columnasVenta = {"ID", "Producto", "Cantidad", "Precio Unit.", "Subtotal"};
        modelVenta = new DefaultTableModel(columnasVenta, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableVenta = new JTable(modelVenta);
        tableVenta.setFont(new Font("Consolas", Font.PLAIN, 14));
        tableVenta.setForeground(new Color(200, 220, 255));
        tableVenta.setBackground(new Color(30, 20, 15));
        tableVenta.setSelectionBackground(new Color(86, 215, 255));
        tableVenta.setSelectionForeground(new Color(15, 10, 10));
        tableVenta.setRowHeight(30);
        tableVenta.getTableHeader().setBackground(new Color(40, 25, 15));
        tableVenta.getTableHeader().setForeground(new Color(200, 155, 60));
        tableVenta.getTableHeader().setFont(new Font("Consolas", Font.BOLD, 14));
        
        JScrollPane scrollVenta = new JScrollPane(tableVenta);
        scrollVenta.getViewport().setBackground(new Color(30, 20, 15));
        panelCarrito.add(scrollVenta, BorderLayout.CENTER);
        
        // Panel inferior del carrito
        JPanel panelCarritoInferior = new JPanel(new BorderLayout(10, 10));
        panelCarritoInferior.setBackground(new Color(15, 10, 10));
        panelCarritoInferior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel lblTotalTexto = new JLabel("Total: ");
        lblTotalTexto.setFont(new Font("Consolas", Font.BOLD, 18));
        lblTotalTexto.setForeground(new Color(200, 155, 60));
        
        lblTotal = new JLabel("$0.00");
        lblTotal.setFont(new Font("Consolas", Font.BOLD, 24));
        lblTotal.setForeground(new Color(86, 215, 255));
        lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
        
        JPanel panelTotal = new JPanel(new BorderLayout());
        panelTotal.setBackground(new Color(15, 10, 10));
        panelTotal.add(lblTotalTexto, BorderLayout.WEST);
        panelTotal.add(lblTotal, BorderLayout.CENTER);
        
        JPanel panelBotonesCarrito = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotonesCarrito.setBackground(new Color(15, 10, 10));
        
        JButton btnQuitar = crearBotonAccion("Quitar", new Color(200, 70, 70));
        JButton btnLimpiar = crearBotonAccion("Limpiar", new Color(150, 100, 70));
        JButton btnFinalizar = crearBotonAccion("Finalizar Venta", new Color(60, 170, 90));
        
        btnQuitar.setPreferredSize(new Dimension(120, 35));
        btnLimpiar.setPreferredSize(new Dimension(120, 35));
        btnFinalizar.setPreferredSize(new Dimension(150, 35));
        
        btnQuitar.addActionListener(e -> quitarDelCarrito());
        btnLimpiar.addActionListener(e -> limpiarCarrito());
        btnFinalizar.addActionListener(e -> finalizarVenta());
        
        panelBotonesCarrito.add(btnQuitar);
        panelBotonesCarrito.add(btnLimpiar);
        panelBotonesCarrito.add(btnFinalizar);
        
        panelCarritoInferior.add(panelTotal, BorderLayout.NORTH);
        panelCarritoInferior.add(panelBotonesCarrito, BorderLayout.SOUTH);
        panelCarrito.add(panelCarritoInferior, BorderLayout.SOUTH);
        
        splitPrincipal.setLeftComponent(panelProductos);
        splitPrincipal.setRightComponent(panelCarrito);
        add(splitPrincipal, BorderLayout.CENTER);
        
        // Cargar productos
        cargarProductos();
    }
    
    private JButton crearBotonAccion(String texto, Color colorBase) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Consolas", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(colorBase);
        btn.setBorder(BorderFactory.createLineBorder(new Color(86, 215, 255), 2));
        btn.setFocusPainted(false);
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
    
    private void cargarProductos() {
        modelProductos.setRowCount(0);
        modelProductos.addRow(new Object[]{1, "Lápiz HB", 5.50, 100});
        modelProductos.addRow(new Object[]{2, "Cuaderno", 25.00, 50});
        modelProductos.addRow(new Object[]{3, "Goma", 3.00, 200});
        modelProductos.addRow(new Object[]{4, "Bolígrafo Azul", 4.50, 150});
        modelProductos.addRow(new Object[]{5, "Regla 30cm", 8.00, 80});
    }
    
    private void agregarAlCarrito() {
        int fila = tableProductos.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto");
            return;
        }
        
        try {
            int cantidad = Integer.parseInt(txtCantidad.getText());
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a 0");
                return;
            }
            
            int idProducto = (Integer) modelProductos.getValueAt(fila, 0);
            String nombreProducto = modelProductos.getValueAt(fila, 1).toString();
            double precioUnitario = Double.parseDouble(modelProductos.getValueAt(fila, 2).toString());
            int stock = Integer.parseInt(modelProductos.getValueAt(fila, 3).toString());
            
            if (cantidad > stock) {
                JOptionPane.showMessageDialog(this, "Stock insuficiente. Disponible: " + stock);
                return;
            }
            
            double subtotal = precioUnitario * cantidad;
            
            modelVenta.addRow(new Object[]{
                idProducto,
                nombreProducto,
                cantidad,
                precioUnitario,
                subtotal
            });
            
            actualizarTotal();
            txtCantidad.setText("1");
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese una cantidad válida");
        }
    }
    
    private void quitarDelCarrito() {
        int fila = tableVenta.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un item para quitar");
            return;
        }
        
        modelVenta.removeRow(fila);
        actualizarTotal();
    }
    
    private void limpiarCarrito() {
        int confirmacion = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de limpiar el carrito?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            modelVenta.setRowCount(0);
            actualizarTotal();
        }
    }
    
    private void actualizarTotal() {
        double total = 0;
        for (int i = 0; i < modelVenta.getRowCount(); i++) {
            total += Double.parseDouble(modelVenta.getValueAt(i, 4).toString());
        }
        lblTotal.setText("$" + formato.format(total));
    }
    
    private void finalizarVenta() {
        if (modelVenta.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "El carrito está vacío");
            return;
        }

        double total = 0;
        for (int i = 0; i < modelVenta.getRowCount(); i++) {
            total += Double.parseDouble(modelVenta.getValueAt(i, 4).toString());
        }

        int confirmacion = JOptionPane.showConfirmDialog(
            this,
            "¿Confirmar venta por un total de $" + formato.format(total) + "?",
            "Finalizar Venta",
            JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                // Crear archivo PDF
                String nombreArchivo = "ticket_venta_" + System.currentTimeMillis() + ".pdf";
                java.io.File archivoPDF = new java.io.File(nombreArchivo);
                com.itextpdf.text.Document documento = new com.itextpdf.text.Document();
                com.itextpdf.text.pdf.PdfWriter.getInstance(documento, new java.io.FileOutputStream(archivoPDF));
                documento.open();

                com.itextpdf.text.Font tituloFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.COURIER, 14, com.itextpdf.text.Font.BOLD);
                com.itextpdf.text.Font normalFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.COURIER, 12, com.itextpdf.text.Font.NORMAL);

                documento.add(new com.itextpdf.text.Paragraph("TICKET DE VENTA", tituloFont));
                documento.add(new com.itextpdf.text.Paragraph("Fecha: " + java.time.LocalDateTime.now(), normalFont));
                documento.add(new com.itextpdf.text.Paragraph("==================================", normalFont));

                for (int i = 0; i < modelVenta.getRowCount(); i++) {
                    String producto = modelVenta.getValueAt(i, 1).toString();
                    int cantidad = Integer.parseInt(modelVenta.getValueAt(i, 2).toString());
                    double precioUnit = Double.parseDouble(modelVenta.getValueAt(i, 3).toString());
                    double subtotal = Double.parseDouble(modelVenta.getValueAt(i, 4).toString());

                    documento.add(new com.itextpdf.text.Paragraph(
                        cantidad + " x " + producto + " @ $" + formato.format(precioUnit) + " = $" + formato.format(subtotal),
                        normalFont
                    ));
                }

                documento.add(new com.itextpdf.text.Paragraph("==================================", normalFont));
                documento.add(new com.itextpdf.text.Paragraph("TOTAL: $" + formato.format(total), tituloFont));

                documento.close();

                // Abrir automáticamente el PDF generado
                if (java.awt.Desktop.isDesktopSupported()) {
                    java.awt.Desktop.getDesktop().open(archivoPDF);
                }

                limpiarCarrito();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al generar o abrir el ticket PDF: " + e.getMessage());
            }
        }
    }


}
