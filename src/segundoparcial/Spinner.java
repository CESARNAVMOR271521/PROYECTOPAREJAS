package segundoparcial;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Toolkit;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class Spinner {

	public JFrame frmSpinner;
	private JSpinner spnValor;
	private JLabel lblValor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Spinner window = new Spinner();
					window.frmSpinner.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Spinner() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSpinner = new JFrame();
		frmSpinner.setTitle("Spinner");
		frmSpinner.setBounds(100, 100, 450, 300);
		frmSpinner.setLocationRelativeTo(null);
		frmSpinner.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmSpinner.getContentPane().setLayout(null);
		
		lblValor = new JLabel("");
		lblValor.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		lblValor.setBounds(55, 107, 124, 33);
		frmSpinner.getContentPane().add(lblValor);
		
		
		spnValor = new JSpinner();
		spnValor.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {

				lblValor.setText("El valor es: "+spnValor.getValue().toString());
				
			}
			
		});
		spnValor.setBounds(55, 40, 89, 20);
		frmSpinner.getContentPane().add(spnValor);
		SpinnerNumberModel nm = new SpinnerNumberModel();
		nm.setMaximum(10);
		nm.setMinimum(0);
		nm.setStepSize(2);
		spnValor.setModel(nm);
		
		
	}
}
