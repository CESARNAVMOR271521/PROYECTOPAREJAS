package segundoparcial;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class Listas {

	public JFrame frmListas;
	private JLabel lblResultado;
	private JList lstColores;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Listas window = new Listas();
					window.frmListas.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Listas() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmListas = new JFrame();
		frmListas.setTitle("Listas");
		frmListas.setBounds(100, 100, 450, 300);
		frmListas.setLocationRelativeTo(null);
		frmListas.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmListas.getContentPane().setLayout(null);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String mensaje;
				if (lstColores.getSelectedIndex()==-1) {
				mensaje="No hay un color seleccionado.";
				} else {
				mensaje="El color seleccionado es: "+lstColores.getSelectedValue().toString();
				}
				lblResultado.setText(mensaje);
			}
		});
		btnAceptar.setBounds(26, 158, 89, 23);
		frmListas.getContentPane().add(btnAceptar);
		
		lblResultado = new JLabel("");
		lblResultado.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		lblResultado.setBounds(26, 192, 375, 35);
		frmListas.getContentPane().add(lblResultado);
		
		lstColores = new JList();
		lstColores.setModel(new AbstractListModel() {
			String[] values = new String[] {"Rojo", "Azul", "Verde"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		lstColores.setBounds(26, 26, 122, 105);
		frmListas.getContentPane().add(lstColores);
	}
}
