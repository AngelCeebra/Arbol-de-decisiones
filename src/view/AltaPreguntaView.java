package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.btnAltaPreguntaController;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class AltaPreguntaView extends JFrame {

	private JPanel contentPane;
	private JTextField textQuestion;
	private JTextField textAnswer1;
	private JTextField texAnswer2;
	private JTextField texAnswer3;
	private JTextField texAnswer4;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//AltaPregunta frame = new AltaPregunta();
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
	public AltaPreguntaView() {
		setTitle("Alta de Pregunta");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 540, 335);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPregunta = new JLabel("Pregunta: ");
		lblPregunta.setBounds(50, 40, 63, 16);
		contentPane.add(lblPregunta);
		
		textQuestion = new JTextField();
		textQuestion.setBounds(150, 35, 320, 26);
		contentPane.add(textQuestion);
		textQuestion.setColumns(10);
		
		JLabel lblRespuesta = new JLabel("Respuesta #1:");
		lblRespuesta.setBounds(50, 90, 90, 16);
		contentPane.add(lblRespuesta);
		
		JLabel lblRespuesta_1 = new JLabel("Respuesta #2:");
		lblRespuesta_1.setBounds(50, 130, 90, 16);
		contentPane.add(lblRespuesta_1);
		
		JLabel lblRespuesta_2 = new JLabel("Respuesta #3:");
		lblRespuesta_2.setBounds(50, 170, 90, 16);
		contentPane.add(lblRespuesta_2);
		
		JLabel lblRespuesta_3 = new JLabel("Respuesta #4:");
		lblRespuesta_3.setBounds(52, 210, 88, 16);
		contentPane.add(lblRespuesta_3);
		
		textAnswer1 = new JTextField();
		textAnswer1.setBounds(150, 85, 320, 26);
		contentPane.add(textAnswer1);
		textAnswer1.setColumns(10);
		
		texAnswer2 = new JTextField();
		texAnswer2.setBounds(150, 125, 320, 26);
		contentPane.add(texAnswer2);
		texAnswer2.setColumns(10);
		
		texAnswer3 = new JTextField();
		texAnswer3.setBounds(150, 165, 320, 26);
		contentPane.add(texAnswer3);
		texAnswer3.setColumns(10);
		
		texAnswer4 = new JTextField();
		texAnswer4.setBounds(150, 205, 320, 26);
		contentPane.add(texAnswer4);
		texAnswer4.setColumns(10);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(125, 255, 117, 29);
		contentPane.add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(300, 255, 117, 29);
		contentPane.add(btnCancelar);
		
		
		btnAceptar.addActionListener(new btnAltaPreguntaController(this, textQuestion, textAnswer1, texAnswer2, texAnswer3, texAnswer4));
		
		
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
}
