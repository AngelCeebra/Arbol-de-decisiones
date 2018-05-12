package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.btnAltaPreguntaController;
import controller.btnNuevoNombreController;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class NombreUsuario extends JFrame {

	private JPanel contentPane;
	private JTextField textName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//NombreUsuario frame = new NombreUsuario();
					//frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public NombreUsuario(ConfiguracionFrame frame) {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 360);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblIngreseSuNombre = new JLabel("Ingrese su nombre:");
		lblIngreseSuNombre.setBounds(175, 70, 121, 16);
		contentPane.add(lblIngreseSuNombre);
		
		textName = new JTextField();
		textName.setBounds(120, 155, 240, 26);
		contentPane.add(textName);
		textName.setColumns(10);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(70, 240, 117, 29);
		contentPane.add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(285, 240, 117, 29);
		contentPane.add(btnCancelar);
		
		btnAceptar.addActionListener(new btnNuevoNombreController(this, textName, frame));
		
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
}
