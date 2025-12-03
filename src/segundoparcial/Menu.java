package segundoparcial;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Cursor;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Menu {

	/*
	 * Interfaz (refactor) - 2025-11-24
	 *
	 * Cambios realizados:
	 * - La ventana principal ahora usa un JPanel con BorderLayout y un GridLayout
	 *   para disponer los botones de forma automática y responsiva.
	 * - No se ha alterado la lógica existente: todos los ActionListener siguen
	 *   llamando a las mismas referencias de ventana (por ejemplo, a.frmVerificacion).
	 * - Se evitó el uso de posicionamiento absoluto (setBounds) para mejorar la
	 *   portabilidad y mantenimiento.
	 *
	 * Nota: Si alguna de las clases referenciadas (CheckAnimal, Opcion, etc.)
	 * cambia el nombre de los campos públicos de sus frames, habrá que actualizar
	 * los listeners correspondientes. Por ahora, sólo se cambió la disposición.
	 */
	private JFrame frmMenu;
	CheckAnimal a=new CheckAnimal();
	Opcion b=new Opcion();
	Listas c=new Listas();
	Combo d=new Combo();
	ListaModelos f=new ListaModelos();
	CombosModelos g=new CombosModelos();
	Guiados h=new Guiados();
	Slider i=new Slider();
	Spinner j=new Spinner();
	Barras k=new Barras();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu window = new Menu();
					window.frmMenu.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Menu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMenu = new JFrame();
		frmMenu.setTitle("MENU");
		frmMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMenu.setSize(475, 428);
		frmMenu.setLocationRelativeTo(null);

		// panel principal y estilos generales
		JPanel content = new JPanel(new BorderLayout());
		content.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));
		content.setBackground(new Color(245, 245, 250));

		// encabezado llamativo
		JLabel header = new JLabel("Menú de Prácticas", SwingConstants.CENTER);
		header.setFont(new Font("Tahoma", Font.BOLD, 22));
		header.setForeground(new Color(35, 47, 62));
		header.setBorder(BorderFactory.createEmptyBorder(6,6,12,6));
		content.add(header, BorderLayout.NORTH);

		JPanel grid = new JPanel(new GridLayout(5, 2, 12, 12));
		grid.setBackground(Color.WHITE);
		grid.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(200,200,210)),
				BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(6,6,6,6))));

		JButton btnP1 = new JButton("PRACTICA 1");
		btnP1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				a.frmVerificacion.setVisible(true);
			}
		});
		btnP1.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnP1.setBackground(new Color(102, 153, 255));
		btnP1.setForeground(Color.WHITE);
		btnP1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnP1.setOpaque(true);
		btnP1.setToolTipText("Abrir práctica 1: verificación de animales");
		grid.add(btnP1);

		JButton btnP2 = new JButton("PRACTICA 2");
		btnP2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				b.frmBotonesDeOpcion.setVisible(true);
			}
		});
		btnP2.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnP2.setBackground(new Color(255, 153, 102));
		btnP2.setForeground(Color.WHITE);
		btnP2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnP2.setOpaque(true);
		btnP2.setToolTipText("Abrir práctica 2: botones de opción");
		grid.add(btnP2);

		JButton btnP3 = new JButton("PRACTICA 3");
		btnP3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c.frmListas.setVisible(true);
			}
		});
		btnP3.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnP3.setBackground(new Color(102, 204, 153));
		btnP3.setForeground(Color.WHITE);
		btnP3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnP3.setOpaque(true);
		btnP3.setToolTipText("Abrir práctica 3: listas");
		grid.add(btnP3);

		JButton btnP4 = new JButton("PRACTICA 4");
		btnP4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				d.frmCombo.setVisible(true);
			}
		});
		btnP4.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnP4.setBackground(new Color(153, 102, 255));
		btnP4.setForeground(Color.WHITE);
		btnP4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnP4.setOpaque(true);
		btnP4.setToolTipText("Abrir práctica 4: combo");
		grid.add(btnP4);

		JButton btnP5 = new JButton("PRACTICA 5");
		btnP5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.frmModelos.setVisible(true);
			}
		});
		btnP5.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnP5.setBackground(new Color(255, 102, 153));
		btnP5.setForeground(Color.WHITE);
		btnP5.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnP5.setOpaque(true);
		btnP5.setToolTipText("Abrir práctica 5: modelos de lista");
		grid.add(btnP5);

		JButton btnP6 = new JButton("PRACTICA 6");
		btnP6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				g.frmCombosModelos.setVisible(true);
			}
		});
		btnP6.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnP6.setBackground(new Color(60, 179, 225));
		btnP6.setForeground(Color.WHITE);
		btnP6.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnP6.setOpaque(true);
		btnP6.setToolTipText("Abrir práctica 6: combos modelos");
		grid.add(btnP6);

		JButton btnP7 = new JButton("PRACTICA 7");
		btnP7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				h.frmGuiadosTooble.setVisible(true);
			}
		});
		btnP7.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnP7.setBackground(new Color(255, 204, 102));
		btnP7.setForeground(new Color(40,40,40));
		btnP7.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnP7.setOpaque(true);
		btnP7.setToolTipText("Abrir práctica 7: guiados y tooltips");
		grid.add(btnP7);

		JButton btnP8 = new JButton("PRACTICA 8");
		btnP8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				i.frmSlidder.setVisible(true);
			}
		});
		btnP8.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnP8.setBackground(new Color(153, 204, 255));
		btnP8.setForeground(Color.DARK_GRAY);
		btnP8.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnP8.setOpaque(true);
		btnP8.setToolTipText("Abrir práctica 8: slider");
		grid.add(btnP8);

		JButton btnP9 = new JButton("PRACTICA 9");
		btnP9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				j.frmSpinner.setVisible(true);
			}
		});
		btnP9.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnP9.setBackground(new Color(180, 180, 255));
		btnP9.setForeground(Color.DARK_GRAY);
		btnP9.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnP9.setOpaque(true);
		btnP9.setToolTipText("Abrir práctica 9: spinner");
		grid.add(btnP9);

		JButton btnP10 = new JButton("PRACTICA 10");
		btnP10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				k.frmBarrasDeDesplazamiento.setVisible(true);
			}
		});
		btnP10.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnP10.setBackground(new Color(120, 200, 150));
		btnP10.setForeground(Color.WHITE);
		btnP10.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnP10.setOpaque(true);
		btnP10.setToolTipText("Abrir práctica 10: barras de desplazamiento");
		grid.add(btnP10);

		content.add(grid, BorderLayout.CENTER);
		frmMenu.setContentPane(content);
	}
}
