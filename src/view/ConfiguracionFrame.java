package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class ConfiguracionFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textNumCasos;
	private JTextField textPorcentaje;
	
	public double numCasos, porcentaje;

	public double getNumCasos() {
		return numCasos;
	}

	public void setNumCasos(int numCasos) {
		this.numCasos = Integer.parseInt(this.textNumCasos.getText());
	}

	public double getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(int porcentaje) {
		this.porcentaje = Integer.parseInt(this.textPorcentaje.getText());
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ConfiguracionFrame() {
		setTitle("Configuración y Validación");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNmeroDeCasos = new JLabel("Número de casos de prueba:");
		lblNmeroDeCasos.setBounds(60, 70, 182, 16);
		contentPane.add(lblNmeroDeCasos);
		
		JLabel lblPorcentajeDeAceptacin = new JLabel("Porcentaje de aceptación:");
		lblPorcentajeDeAceptacin.setBounds(68, 140, 174, 16);
		contentPane.add(lblPorcentajeDeAceptacin);
		
		textNumCasos = new JTextField();
		textNumCasos.setBounds(254, 65, 130, 26);
		contentPane.add(textNumCasos);
		textNumCasos.setColumns(10);
		
		textPorcentaje = new JTextField();
		textPorcentaje.setBounds(254, 135, 130, 26);
		contentPane.add(textPorcentaje);
		textPorcentaje.setColumns(10);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(85, 200, 117, 29);
		contentPane.add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(250, 200, 117, 29);
		contentPane.add(btnCancelar);
		
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numCasos = Double.parseDouble(textNumCasos.getText());
				porcentaje = Double.parseDouble(textPorcentaje.getText());
				System.out.println(numCasos + " - " + porcentaje);
				setVisible(false);
				
			}
		});
		
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
	
}
