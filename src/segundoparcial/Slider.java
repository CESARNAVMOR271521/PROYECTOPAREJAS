package segundoparcial;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Toolkit;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class Slider {

	public JFrame frmSlidder;
	private JSlider sldDeslizador;
	private JLabel lblValor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Slider window = new Slider();
					window.frmSlidder.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Slider() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSlidder = new JFrame();
		frmSlidder.setTitle("Slider");
		frmSlidder.setBounds(100, 100, 450, 300);
		frmSlidder.setLocationRelativeTo(null);
		frmSlidder.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmSlidder.getContentPane().setLayout(null);
		
		lblValor = new JLabel("");
		lblValor.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		lblValor.setBounds(102, 120, 227, 33);
		frmSlidder.getContentPane().add(lblValor);
		
		sldDeslizador = new JSlider();
		sldDeslizador.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				lblValor.setText("El valor es: "+sldDeslizador.getValue());
			}
		});
		sldDeslizador.setPaintLabels(true);
		sldDeslizador.setPaintTicks(true);
		sldDeslizador.setMajorTickSpacing(50);
		sldDeslizador.setValue(400);
		sldDeslizador.setMinimum(100);
		sldDeslizador.setMaximum(500);
		sldDeslizador.setBounds(10, 32, 414, 45);
		frmSlidder.getContentPane().add(sldDeslizador);
		
		
	}
}
