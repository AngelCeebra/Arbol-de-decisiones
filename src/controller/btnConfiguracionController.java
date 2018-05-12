package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import model.ExecuteQueries;
import view.ConfiguracionFrame;

public class btnConfiguracionController implements ActionListener{
	
	private ConfiguracionFrame configuracionFrame;
	private JTextField textNumCasos, textPorcentaje;
	private ExecuteQueries configuracion = new ExecuteQueries();

	public btnConfiguracionController(ConfiguracionFrame configuracionFrame, JTextField textNumCasos, JTextField textPorcentaje) {
		this.configuracionFrame = configuracionFrame;
		this.textNumCasos = textNumCasos;
		this.textPorcentaje = textPorcentaje;
	}

	public void actionPerformed(ActionEvent e) {
		configuracionFrame = new ConfiguracionFrame();
		int numCasos = Integer.parseInt(textNumCasos.getText());
		int porcentaje = Integer.parseInt(textPorcentaje.getText());
		
		configuracion.Configuracion(numCasos, porcentaje);
		
	}

}
