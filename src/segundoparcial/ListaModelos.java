package segundoparcial;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ListaModelos {

	public JFrame frmModelos;
	private JLabel lblResultado;
	private JList lstNombres;
	private JButton btnVaciar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ListaModelos window = new ListaModelos();
					window.frmModelos.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ListaModelos() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmModelos = new JFrame();
		frmModelos.setTitle("Modelos");
		frmModelos.setBounds(100, 100, 505, 300);
		frmModelos.setLocationRelativeTo(null);
		frmModelos.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmModelos.getContentPane().setLayout(null);
		
		lblResultado = new JLabel("");
		lblResultado.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		lblResultado.setBounds(160, 11, 319, 52);
		frmModelos.getContentPane().add(lblResultado);
		
		lstNombres = new JList();
		lstNombres.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblResultado.setText(lstNombres.getSelectedValue().toString());
			}
		});
		lstNombres.setBounds(31, 11, 103, 131);
		frmModelos.getContentPane().add(lstNombres);
		
		JButton btnCurso1 = new JButton("Curso 1");
		btnCurso1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultListModel modelo = new DefaultListModel();
				modelo.addElement("Juan");
				modelo.addElement("María");
				modelo.addElement("Luis");
				lstNombres.setModel(modelo);
			}
		});
		btnCurso1.setBounds(31, 164, 89, 23);
		frmModelos.getContentPane().add(btnCurso1);
		
		JButton btnCurso2 = new JButton("Curso 2");
		btnCurso2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultListModel modelo = new DefaultListModel();
				modelo.addElement("Ana");
				modelo.addElement("Marta");
				modelo.addElement("Jose");
				lstNombres.setModel(modelo);
			}
		});
		btnCurso2.setBounds(31, 206, 89, 23);
		frmModelos.getContentPane().add(btnCurso2);
		
		btnVaciar = new JButton("Vaciar");
		btnVaciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultListModel modelo = new DefaultListModel();
				lstNombres.setModel(modelo);
				modelo.addElement("");
			}
		});
		btnVaciar.setBounds(243, 119, 89, 23);
		frmModelos.getContentPane().add(btnVaciar);
	}

}
