package segundoparcial;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CombosModelos {

	public JFrame frmCombosModelos;
	private JLabel lblResultado;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CombosModelos window = new CombosModelos();
					window.frmCombosModelos.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CombosModelos() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCombosModelos = new JFrame();
		frmCombosModelos.setTitle("Combos Modelos");
		frmCombosModelos.setBounds(100, 100, 517, 300);
		frmCombosModelos.setLocationRelativeTo(null);
		frmCombosModelos.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmCombosModelos.getContentPane().setLayout(null);
		
		lblResultado = new JLabel("");
		lblResultado.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		lblResultado.setBounds(42, 175, 316, 39);
		frmCombosModelos.getContentPane().add(lblResultado);
		
		JComboBox cboNumeros = new JComboBox();
		cboNumeros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblResultado.setText(cboNumeros.getSelectedItem().toString());
			}
		});
		cboNumeros.setBounds(42, 24, 168, 22);
		frmCombosModelos.getContentPane().add(cboNumeros);
		
		JButton btnPares = new JButton("Pares");
		btnPares.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i;
				DefaultComboBoxModel modelo = new DefaultComboBoxModel();
				for (i=0;i<10;i+=2) {
				modelo.addElement("No "+i);
				}
				cboNumeros.setModel(modelo);
			}
		});
		btnPares.setBounds(244, 24, 89, 23);
		frmCombosModelos.getContentPane().add(btnPares);
		
		JButton btnImpares = new JButton("Impares");
		btnImpares.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i;
				DefaultComboBoxModel modelo = new DefaultComboBoxModel();
				for (i=1;i<10;i+=2) {
				modelo.addElement("No "+i);
				}
				cboNumeros.setModel(modelo);
			}
		});
		btnImpares.setBounds(367, 24, 89, 23);
		frmCombosModelos.getContentPane().add(btnImpares);
		
		JButton btnVaciar = new JButton("Vaciar");
		btnVaciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultComboBoxModel modelo = new DefaultComboBoxModel();
				cboNumeros.setModel(modelo);
				modelo.addElement("");
			}
		});
		btnVaciar.setBounds(297, 89, 89, 23);
		frmCombosModelos.getContentPane().add(btnVaciar);
	}

}
