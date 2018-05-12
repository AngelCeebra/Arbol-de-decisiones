package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.ExecuteQueries;
import view.ConfiguracionFrame;
import view.NombreUsuario;
import view.Pregunta1;
import view.TestView1;

public class btnNuevoNombreController implements ActionListener{

	private NombreUsuario nombreUsuarioView;
	private JTextField textName;
	private ExecuteQueries nombreUsuario = new ExecuteQueries();
	ConfiguracionFrame frame;
	
	public btnNuevoNombreController(NombreUsuario frameNombreUsuario, JTextField textName, ConfiguracionFrame frame) {
		this.textName = textName;
		this.nombreUsuarioView = frameNombreUsuario;
		this.frame = frame;
	}


	public void actionPerformed(ActionEvent e) {
		
		
		nombreUsuarioView = new NombreUsuario(frame);
		String textNameQ = textName.getText();
		
		if(nombreUsuario.searchName(textNameQ)) {
			JOptionPane.showMessageDialog(null, "El usuario " + textNameQ + " ya existe.");
			textName.setText("");
		}else{
			nombreUsuario.insertName(textNameQ);
			JOptionPane.showMessageDialog(null, "Nuevo usuario insertado correctamente.");
			nombreUsuarioView.setVisible(false);
			TestView1 frameP = new TestView1(textNameQ, frame);
			frameP.setVisible(true);
		};
		
	}

}
