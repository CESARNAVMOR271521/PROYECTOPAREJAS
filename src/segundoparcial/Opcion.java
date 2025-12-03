package segundoparcial;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import java.awt.Color;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Panel;
import javax.swing.JRadioButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Opcion {

	public JFrame frmBotonesDeOpcion;
	private ButtonGroup grupo;
	private JRadioButton optRojo;
	private JRadioButton optAzul;
	private JRadioButton optVerde;
	private JLabel lblResultado;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Opcion window = new Opcion();
					window.frmBotonesDeOpcion.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Opcion() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBotonesDeOpcion = new JFrame();
		frmBotonesDeOpcion.setTitle("Botones de Opcion");
		frmBotonesDeOpcion.setBounds(100, 100, 553, 436);
		frmBotonesDeOpcion.setLocationRelativeTo(null);
		frmBotonesDeOpcion.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmBotonesDeOpcion.getContentPane().setLayout(null);

		grupo = new ButtonGroup();

		lblResultado = new JLabel("");
		lblResultado.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		lblResultado.setBounds(42, 284, 357, 37);
		frmBotonesDeOpcion.getContentPane().add(lblResultado);

		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String mensaje="Color elegido: ";
				if (optRojo.isSelected()) {
				mensaje=mensaje+"Rojo";
				} else if (optVerde.isSelected()) {
				mensaje=mensaje+"Verde";
				} else if (optAzul.isSelected()) {
				mensaje=mensaje+"Azul";
				}
				lblResultado.setText(mensaje);
			}
		});
		btnAceptar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnAceptar.setBounds(34, 231, 89, 23);
		frmBotonesDeOpcion.getContentPane().add(btnAceptar);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Colores",
				TitledBorder.LEADING, TitledBorder.BELOW_TOP, null, new Color(30, 144, 255)));
		panel.setBounds(42, 26, 268, 178);
		frmBotonesDeOpcion.getContentPane().add(panel);
		panel.setLayout(null);

		optRojo = new JRadioButton("Rojo");
		optRojo.setSelected(true);
		optRojo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		optRojo.setBounds(56, 25, 68, 23);
		panel.add(optRojo);

		optAzul = new JRadioButton("Azul");
		optAzul.setFont(new Font("Tahoma", Font.PLAIN, 15));
		optAzul.setBounds(56, 62, 68, 23);
		panel.add(optAzul);

		optVerde = new JRadioButton("Verde");
		optVerde.setFont(new Font("Tahoma", Font.PLAIN, 15));
		optVerde.setBounds(56, 98, 68, 23);
		panel.add(optVerde);

		grupo.add(optVerde);
		grupo.add(optAzul);
		grupo.add(optRojo);
	}
}
