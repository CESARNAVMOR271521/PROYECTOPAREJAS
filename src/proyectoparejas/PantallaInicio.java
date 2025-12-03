package proyectoparejas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;

public class PantallaInicio {

	private JFrame frmPrimeraPrueba;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PantallaInicio window = new PantallaInicio();
					window.frmPrimeraPrueba.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PantallaInicio() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmPrimeraPrueba = new JFrame();
		frmPrimeraPrueba.setTitle("PRIMERA PRUEBA\r\n");
		frmPrimeraPrueba.setBounds(100, 100, 450, 300);
		frmPrimeraPrueba.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPrimeraPrueba.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("HOLA MUNDO");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel.setBounds(31, 74, 184, 75);
		frmPrimeraPrueba.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("FILLO DA PUTA");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel_1.setBounds(41, 145, 248, 63);
		frmPrimeraPrueba.getContentPane().add(lblNewLabel_1);
	}
}
