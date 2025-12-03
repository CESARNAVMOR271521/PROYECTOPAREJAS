package segundoparcial;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JToggleButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Guiados {

	public JFrame frmGuiadosTooble;
	private JTextField txtPrecioBase;
	private JToggleButton tbtnInstalacion;
	private JToggleButton tbtnFormacion;
	private JToggleButton tbtnAlimentacionBD;
	private JLabel etiPrecioInstalacion;
	private JLabel etiPrecioFormacion;
	private JLabel etiPrecioAlimentacionBD;
	private JLabel lblTotal;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Guiados window = new Guiados();
					window.frmGuiadosTooble.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Guiados() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmGuiadosTooble = new JFrame();
		frmGuiadosTooble.setTitle("Guiados TOGGLEBUTTONS");
		frmGuiadosTooble.setBounds(100, 100, 410, 406);
		frmGuiadosTooble.setLocationRelativeTo(null);
		frmGuiadosTooble.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmGuiadosTooble.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Precio Base");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(28, 25, 112, 34);
		frmGuiadosTooble.getContentPane().add(lblNewLabel);
		
		txtPrecioBase = new JTextField();
		txtPrecioBase.setBounds(38, 51, 102, 28);
		frmGuiadosTooble.getContentPane().add(txtPrecioBase);
		txtPrecioBase.setColumns(10);
		
		JButton btnCalcular = new JButton("Calcular");
		btnCalcular.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double precio_base;
				double precio_instal; //precio instalación
				double precio_for; //precio formacion
				double precio_ali; //precio alimentacion
				//Recojo datos desde la ventana:
				precio_base = Double.parseDouble(txtPrecioBase.getText());
				precio_instal = Double.parseDouble(etiPrecioInstalacion.getText());
				precio_for = Double.parseDouble(etiPrecioFormacion.getText());
				precio_ali = Double.parseDouble(etiPrecioAlimentacionBD.getText());
				//Ahora que tengo los datos, puedo hacer cálculos.
				//Al precio base se le van añadiendo precio de extras
				//según estén o no activados los JToggleButtons
				double precio_total;
				precio_total = precio_base;
				if (tbtnInstalacion.isSelected()) { //Si se seleccionó instalación
				precio_total = precio_total+precio_instal;
				}
				if (tbtnFormacion.isSelected()) { //Si se seleccionó formación
				precio_total = precio_total+precio_for;
				}
				if (tbtnAlimentacionBD.isSelected()) { //Si se seleccionó Alimentación BD
				precio_total = precio_total+precio_ali;
				}
				//Finalmente pongo el resultado en la etiqueta
				lblTotal.setText(precio_total+" €");
			}
		});
		btnCalcular.setBounds(38, 258, 89, 23);
		frmGuiadosTooble.getContentPane().add(btnCalcular);
		
		lblTotal = new JLabel("");
		lblTotal.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTotal.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		lblTotal.setBounds(22, 309, 211, 47);
		frmGuiadosTooble.getContentPane().add(lblTotal);
		
		tbtnInstalacion = new JToggleButton("Instalacion");
		tbtnInstalacion.setSelected(true);
		tbtnInstalacion.setBounds(28, 112, 121, 23);
		frmGuiadosTooble.getContentPane().add(tbtnInstalacion);
		
		tbtnFormacion = new JToggleButton("Formacion");
		tbtnFormacion.setBounds(28, 146, 121, 23);
		frmGuiadosTooble.getContentPane().add(tbtnFormacion);
		
		tbtnAlimentacionBD = new JToggleButton("Alimentación BD");
		tbtnAlimentacionBD.setBounds(28, 180, 121, 23);
		frmGuiadosTooble.getContentPane().add(tbtnAlimentacionBD);
		
		etiPrecioInstalacion = new JLabel("40");
		etiPrecioInstalacion.setFont(new Font("Tahoma", Font.PLAIN, 15));
		etiPrecioInstalacion.setHorizontalAlignment(SwingConstants.CENTER);
		etiPrecioInstalacion.setBounds(165, 116, 68, 19);
		frmGuiadosTooble.getContentPane().add(etiPrecioInstalacion);
		
		etiPrecioFormacion = new JLabel("200");
		etiPrecioFormacion.setFont(new Font("Tahoma", Font.PLAIN, 15));
		etiPrecioFormacion.setHorizontalAlignment(SwingConstants.CENTER);
		etiPrecioFormacion.setBounds(175, 148, 46, 14);
		frmGuiadosTooble.getContentPane().add(etiPrecioFormacion);
		
		etiPrecioAlimentacionBD = new JLabel("200");
		etiPrecioAlimentacionBD.setHorizontalAlignment(SwingConstants.CENTER);
		etiPrecioAlimentacionBD.setFont(new Font("Tahoma", Font.PLAIN, 15));
		etiPrecioAlimentacionBD.setBounds(175, 184, 46, 14);
		frmGuiadosTooble.getContentPane().add(etiPrecioAlimentacionBD);
	}
}
