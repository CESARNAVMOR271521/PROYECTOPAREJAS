package segundoparcial;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CheckAnimal {

	public JFrame frmVerificacion;
	private JCheckBox chkPerro;
	private JCheckBox chkGato;
	private JCheckBox chkRaton;
	private JButton btnAceptar;
	private JLabel lblResultado;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CheckAnimal window = new CheckAnimal();
					window.frmVerificacion.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CheckAnimal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmVerificacion = new JFrame();
		frmVerificacion.setTitle("verificacion");
		frmVerificacion.setBounds(100, 100, 450, 300);
		frmVerificacion.setLocationRelativeTo(null);
		frmVerificacion.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmVerificacion.getContentPane().setLayout(null);
		
		btnAceptar = new JButton("Aceptar\r\n");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String mensaje="Animales elegidos: ";
				if (chkPerro.isSelected()) {
				mensaje=mensaje+"Perro ";
				}
				if (chkGato.isSelected()) {
				mensaje=mensaje+"Gato ";
				}
				if (chkRaton.isSelected()) {
				mensaje=mensaje+"Raton ";
				}
				lblResultado.setText(mensaje);
			}
		});
		btnAceptar.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnAceptar.setBounds(10, 124, 103, 23);
		frmVerificacion.getContentPane().add(btnAceptar);
		
		lblResultado = new JLabel("");
		lblResultado.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		lblResultado.setBounds(38, 176, 345, 37);
		frmVerificacion.getContentPane().add(lblResultado);
		
		chkPerro = new JCheckBox("Perro");
		chkPerro.setBounds(10, 7, 97, 23);
		frmVerificacion.getContentPane().add(chkPerro);
		
		chkGato = new JCheckBox("Gato");
		chkGato.setBounds(10, 44, 97, 23);
		frmVerificacion.getContentPane().add(chkGato);
		
		chkRaton = new JCheckBox("Raton");
		chkRaton.setBounds(10, 79, 97, 23);
		frmVerificacion.getContentPane().add(chkRaton);
	}
}
