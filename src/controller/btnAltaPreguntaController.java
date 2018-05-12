package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import model.ExecuteQueries;
import view.AltaPreguntaView;

public class btnAltaPreguntaController implements ActionListener{
	
	private AltaPreguntaView altaPreguntaView;
	private JTextField textQuestion, textAnswer1, textAnswer2, textAnswer3, textAnswer4;
	private ExecuteQueries insertarPregunta = new ExecuteQueries();


	public btnAltaPreguntaController(AltaPreguntaView framePreguntaView, JTextField textQuestion, JTextField textAnswer1, JTextField texAnswer2, JTextField texAnswer3, JTextField texAnswer4) {
		
		this.textQuestion = textQuestion;
		this.textAnswer1 = textAnswer1;
		this.textAnswer2 = texAnswer2;
		this.textAnswer3 = texAnswer3;
		this.textAnswer4 = texAnswer4;
		this.altaPreguntaView = framePreguntaView;
		
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		altaPreguntaView = new AltaPreguntaView();
		String questionQ = textQuestion.getText();
		String answer1 = textAnswer1.getText();
		String answer2 = textAnswer2.getText();
		String answer3 = textAnswer3.getText();
		String answer4 = textAnswer4.getText();
		
		insertarPregunta.insertarNuevaPregunta(questionQ, answer1, answer2, answer3, answer4);
		
		textQuestion.setText("");
		textAnswer1.setText("");
		textAnswer2.setText("");
		textAnswer3.setText("");
		textAnswer4.setText("");
	}

}
