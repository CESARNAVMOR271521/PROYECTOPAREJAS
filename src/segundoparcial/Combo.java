package segundoparcial;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Combo {

	public JFrame frmCombo;
	private JLabel lblResultado;
	private JComboBox cboColores;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Combo window = new Combo();
					window.frmCombo.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Combo() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCombo = new JFrame();
		frmCombo.setTitle("Combo");
		frmCombo.setBounds(100, 100, 450, 300);
		frmCombo.setLocationRelativeTo(null);
		frmCombo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmCombo.getContentPane().setLayout(null);
		
		lblResultado = new JLabel("");
		lblResultado.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		lblResultado.setBounds(45, 183, 306, 49);
		frmCombo.getContentPane().add(lblResultado);
		
		cboColores = new JComboBox();
		cboColores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String mensaje="El color elegido es ";
				mensaje=mensaje+cboColores.getSelectedItem().toString();
				lblResultado.setText(mensaje);
			}
		});
		cboColores.setModel(new DefaultComboBoxModel(new String[] {"Rojo", "Azul", "Verde"}));
		cboColores.setEditable(true);
		cboColores.setBounds(45, 43, 170, 22);
		frmCombo.getContentPane().add(cboColores);
	}
}
