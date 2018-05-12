package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JButton;

public class MainFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setTitle("Menú Principal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 400);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JButton btnTest = new JButton("Test");
		menuBar.add(btnTest);
			
		JButton btnEstadsticas = new JButton("Estadísticas");
		menuBar.add(btnEstadsticas);
		
		JButton btnConfiguracion = new JButton("Configuración");
		menuBar.add(btnConfiguracion);
		
		JButton btnAltaDePregunta = new JButton("Alta de Pregunta");
		menuBar.add(btnAltaDePregunta);
		
		JButton btnTree = new JButton("Creación de Árbol");
		menuBar.add(btnTree);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		ConfiguracionFrame frame = new ConfiguracionFrame();
		
		btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NombreUsuario frameUser = new NombreUsuario(frame);
				frameUser.setVisible(true);
			}
			
		});
		
		btnConfiguracion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				frame.setVisible(true);
			}
			
		});
		
		btnAltaDePregunta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AltaPreguntaView frame = new AltaPreguntaView();
				frame.setVisible(true);
			}
			
		});
		
		btnTree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConstruccionArbol1 nextQuestion = new ConstruccionArbol1();
				nextQuestion.setVisible(true);
			}
			
		});
	}

}
