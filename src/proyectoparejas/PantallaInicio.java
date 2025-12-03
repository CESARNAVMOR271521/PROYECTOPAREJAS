package proyectoparejas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import javax.swing.JLabel;

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
		lblNewLabel.setBounds(77, 82, 264, 75);
		frmPrimeraPrueba.getContentPane().add(lblNewLabel);
	}
}
