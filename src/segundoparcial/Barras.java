package segundoparcial;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JScrollBar;
import java.awt.event.AdjustmentListener;
import java.awt.event.AdjustmentEvent;

public class Barras {

	public JFrame frmBarrasDeDesplazamiento;
	private JLabel lblValor;
	private JScrollBar scrValor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Barras window = new Barras();
					window.frmBarrasDeDesplazamiento.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Barras() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBarrasDeDesplazamiento = new JFrame();
		frmBarrasDeDesplazamiento.setTitle("Barras de Desplazamiento");
		frmBarrasDeDesplazamiento.setBounds(100, 100, 450, 300);
		frmBarrasDeDesplazamiento.setLocationRelativeTo(null);
		frmBarrasDeDesplazamiento.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmBarrasDeDesplazamiento.getContentPane().setLayout(null);
		
		lblValor = new JLabel("El valor es 70");
		lblValor.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		lblValor.setBounds(57, 153, 258, 41);
		frmBarrasDeDesplazamiento.getContentPane().add(lblValor);
		
		scrValor = new JScrollBar();
		scrValor.setValue(70);
		scrValor.setVisibleAmount(5);
		scrValor.setBlockIncrement(20);
		scrValor.setUnitIncrement(2);
		scrValor.setMinimum(50);
		scrValor.setMaximum(150);
		scrValor.addAdjustmentListener(new AdjustmentListener() {
			public void adjustmentValueChanged(AdjustmentEvent e) {
				lblValor.setText("El valor es: "+scrValor.getValue());
			}
		});
		scrValor.setOrientation(JScrollBar.HORIZONTAL);
		scrValor.setBounds(38, 58, 292, 34);
		frmBarrasDeDesplazamiento.getContentPane().add(scrValor);
	}
}
