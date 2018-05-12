package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import model.Connection;
import model.ExecuteQueries;
import model.Questions;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;

public class Pregunta2 extends JFrame {

	private JPanel contentPane;

	
	JComboBox comboBoxQuestion = new JComboBox();
	JComboBox BoxResponse1 = new JComboBox();
	JComboBox BoxResponse2 = new JComboBox();
	JComboBox comboBoxDecision1, comboBoxDecision2;
	private String querySearch, querySearch2, queryCount, queryCountQuestions, query;
	PreparedStatement sendQuery;
	ResultSet rs;
	String response;
	int countRows, countRowsQuestions;
	JLabel[] AnswersRadio;
	LinkedList listAnswers = new LinkedList();
	LinkedList listResponses = new LinkedList();
	LinkedList questionResponse = new LinkedList();
	LinkedList listQuestions = new LinkedList();
	private ItemHandler handler = new ItemHandler();
	private ItemHandler2 handler2 = new ItemHandler2();
	
	Connection myConnection = new Connection();
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//frame = new Pregunta2();
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
	public Pregunta2(int idQuestion, int idUser, ConfiguracionFrame frame2) {
		
		java.sql.Connection connect = myConnection.getMyConnection();
		
		querySearch = "SELECT text FROM question";
		queryCountQuestions = "SELECT count(id_question) FROM question";
		queryCount = "SELECT count(id_question) FROM response where id_question=(?)";
		querySearch2="SELECT text FROM response where id_question=(?)";
		query = "UPDATE question SET ok=0 where text=(?)";
		
		try {
			//SEARCH QUESTION
			sendQuery = connect.prepareStatement(querySearch);
			rs = sendQuery.executeQuery();
			while(rs.next()) {
				listQuestions.add(rs.getString("text"));
			}
			
			sendQuery = connect.prepareStatement(query);
			sendQuery.setString(1,(String) listQuestions.get(idQuestion-1));
			sendQuery.execute();
			
			//COUNT ROWS ID_QUESTION IN TABLE RESPONSE
			sendQuery = connect.prepareStatement(queryCount);
			sendQuery.setInt(1, idQuestion);// NUMBER QUESTION
			rs = sendQuery.executeQuery();
			if(rs.next()) {
				countRows = rs.getInt(1);
			}
			
			//SEARCH RESPONSES FOR QUESTION
			sendQuery = connect.prepareStatement(querySearch2);
			sendQuery.setInt(1, idQuestion);//NUMBER QUESTION
			rs = sendQuery.executeQuery();
			while(rs.next()) {
				listAnswers.addLast(rs.getString(1));
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		setTitle("Pregunta Siguiente");
		setBounds(100, 100, 830, 360);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPregunta = new JLabel("Pregunta:");
		lblPregunta.setBounds(180, 65, 78, 16);
		contentPane.add(lblPregunta);
		
		AnswersRadio = new JLabel[countRows];
		for (int i = 0, v = 135; i < countRows && v <= 255; i++, v+=40){
			AnswersRadio[i] = new JLabel((String) listAnswers.get(i));
			AnswersRadio[i].setBounds(60, v, 375, 20);
			contentPane.add(AnswersRadio[i]);
		}
		
		comboBoxQuestion.setBounds(300, 61, 355, 27);
		comboBoxQuestion.addItem(listQuestions.get(idQuestion-1));
		contentPane.add(comboBoxQuestion);
		
		BoxResponse1.setBounds(234, 135, 205, 20);
		BoxResponse1.addItem("Selecciona una opcion");
		BoxResponse1.addItem("Pregunta");
		BoxResponse1.addItem("Decisión");
		BoxResponse1.addItemListener(handler);
		contentPane.add(BoxResponse1);
		
		BoxResponse2.setBounds(234, 175, 205, 20);
		BoxResponse2.addItem("Selecciona una opcion");
		BoxResponse2.addItem("Pregunta");
		BoxResponse2.addItem("Decisión");
		BoxResponse2.addItemListener(handler2);
		contentPane.add(BoxResponse2);
		
		
		comboBoxDecision1 = new JComboBox();
		comboBoxDecision1.setBounds(451, 132, 300, 27);
		contentPane.add(comboBoxDecision1);
		
		comboBoxDecision2 = new JComboBox();
		comboBoxDecision2.setBounds(451, 172, 300, 27);
		contentPane.add(comboBoxDecision2);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(260, 253, 117, 29);
		contentPane.add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(510, 253, 117, 29);
		contentPane.add(btnCancelar);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBounds(205, 159, 1, 1);
		contentPane.add(desktopPane);
		
		
		
		
		
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				java.sql.Connection connect = myConnection.getMyConnection();
				int route = 0, link = 0;
				double casos = frame2.getNumCasos();
				double porcentaje = frame2.getPorcentaje();
				int countCases = 0, countRoute = 0;
				double confianza;
				
				//PRIMERA RESPUESTA - SE EVALUA LA SELECCION DE PREGUNTA O DECISION
				if(BoxResponse1.getSelectedItem().toString().equals("Pregunta") | BoxResponse1.getSelectedItem().toString().equals("Decisión")) {
					String box = comboBoxDecision1.getSelectedItem().toString(); //ComboBox question/decision
					String answer = AnswersRadio[0].getText(); //JLabel answer
					
					//SE EVALUA SI ES ACEPTADO
					if(box.equals("Aceptado")) {
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
						try {
							//SE CONTABILIZA EL NUMERO TOTAL DE RUTA
							query = "SELECT count(route) FROM historical where response=(?)";
							sendQuery = connect.prepareStatement(query);
							sendQuery.setString(1, answer);
							rs = sendQuery.executeQuery();
							if(rs.next()) {
								countRoute = rs.getInt(1);
							}
							System.out.println("Cuenta ruta " + countRoute);
							System.out.println("Casos " + casos);
							//SE EVALÚA EL NUMERO DE CASOS QUE SE PROPORCIONO CON EL NUMERO DE RUTA
							if(countRoute == casos) {
								//SE CONTABILIZA EL NUMERO DE ACEPTADOS QUE HAY DE HACEPTADOS EN ESA RESPUESTA
								query ="SELECT count(1_0) FROM historical where response=(?) and 1_0=1";
								sendQuery = connect.prepareStatement(query);
								sendQuery.setString(1, answer);
								rs = sendQuery.executeQuery();
								if(rs.next()) {
									countCases = rs.getInt(1);
								}
								System.out.println("casos contados = " + countCases);
								System.out.println("numero de casos  = " + casos);
								confianza = (countCases/casos) * 100;
								System.out.println("Confianza = " + confianza);
								if(confianza < porcentaje) {
									JOptionPane.showMessageDialog(null, "Su credito ha sido rechazado");
									
									query = "UPDATE response SET link=0 where text=(?)";
									sendQuery = connect.prepareStatement(query);
									sendQuery.setString(1, answer);
									sendQuery.execute();
									
									query = "SELECT route, link FROM response where text=(?)";
									sendQuery = connect.prepareStatement(query);
									sendQuery.setString(1, answer);
									rs = sendQuery.executeQuery();
									while(rs.next()) {
										route = rs.getInt("route");
										link = rs.getInt("link");
									}
									
									query = "UPDATE historical SET route=(?), response=(?), 1_0=(?) where id_historical=(?)";
									sendQuery = connect.prepareStatement(query);
									sendQuery.setInt(1,route);
									sendQuery.setString(2,answer);
									sendQuery.setInt(3,link);
									sendQuery.setInt(4, idUser);
									sendQuery.execute();
									
									query = "UPDATE question SET ok=1 where ok=0";
									sendQuery = connect.prepareStatement(query);
									sendQuery.execute();
								}
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------								
								
								
							}else if(countRoute >= casos){
								JOptionPane.showMessageDialog(null, "Se han superado los casos");
								//QUE HACER AQUI --------------------------
							}else {
								query = "UPDATE response SET link=1 where text=(?)";
								sendQuery = connect.prepareStatement(query);
								sendQuery.setString(1, answer);
								sendQuery.execute();
								
								query = "SELECT route, link FROM response where text=(?)";
								sendQuery = connect.prepareStatement(query);
								sendQuery.setString(1, answer);
								rs = sendQuery.executeQuery();
								while(rs.next()) {
									route = rs.getInt("route");
									link = rs.getInt("link");
								}
								
								query = "UPDATE historical SET route=(?), response=(?), 1_0=(?) where id_historical=(?)";
								sendQuery = connect.prepareStatement(query);
								sendQuery.setInt(1,route);
								sendQuery.setString(2,answer);
								sendQuery.setInt(3,link);
								sendQuery.setInt(4, idUser);
								sendQuery.execute();
								
								query = "UPDATE question SET ok=1 where ok=0";
								sendQuery = connect.prepareStatement(query);
								sendQuery.execute();
								
								JOptionPane.showMessageDialog(null, "Su crédito ha sido aceptado");
							}
							
							
							
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						//SE EVALUA SI ES RECHAZADO
					}else if(box.equals("Rechazado")){
						query = "UPDATE response SET link=0 where text=(?)";
						try {
							sendQuery = connect.prepareStatement(query);
							sendQuery.setString(1, answer);
							sendQuery.execute();
							
							query = "SELECT route, link FROM response where text=(?)";
							sendQuery = connect.prepareStatement(query);
							sendQuery.setString(1, answer);
							rs = sendQuery.executeQuery();
							while(rs.next()) {
								route = rs.getInt("route");
								link = rs.getInt("link");
							}
							
							query = "UPDATE historical SET route=(?), response=(?), 1_0=(?) where id_historical=(?)";
							sendQuery = connect.prepareStatement(query);
							sendQuery.setInt(1,route);
							sendQuery.setString(2,answer);
							sendQuery.setInt(3,link);
							sendQuery.setInt(4, idUser);
							sendQuery.execute();
							
							query = "UPDATE question SET ok=1 where ok=0";
							sendQuery = connect.prepareStatement(query);
							sendQuery.execute();
							
							JOptionPane.showMessageDialog(null, "Su crédito ha sido rechazado");
							
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						//SE EVALUA CUANDO ES UNA PREGUNTA
					}else {
						//SE BUSCA EL ID DE LA SIGUIENTE PREGUNTA PARA MANDARLO AL JFRAME
						querySearch ="SELECT id_question FROM question where text=(?)";
						query = "UPDATE response SET link=-1 where text=(?)";
						int nextIDquestion = 0;
						try {
							sendQuery = connect.prepareStatement(querySearch);
							sendQuery.setString(1, box);
							rs = sendQuery.executeQuery();
							while(rs.next()) {
								nextIDquestion = rs.getInt("id_question");
							}
							
							sendQuery = connect.prepareStatement(query);
							sendQuery.setString(1, answer);
							sendQuery.execute();
							
							String thisQuestion = comboBoxQuestion.getSelectedItem().toString();
							query = "UPDATE question SET ok=0 where text=(?)";
							sendQuery = connect.prepareStatement(query);
							sendQuery.setString(1, thisQuestion);
							sendQuery.execute();
							
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						//MANDA AL SEGUNDO JFRAME
						Pregunta2 nextQuestion = new Pregunta2(nextIDquestion, idUser, frame2);
						nextQuestion.setVisible(true);
						
						System.out.println("NEXT ID" + nextIDquestion); 
						System.out.println("1 - " + box); 
					}
				
				
				//SEGUNDA RESPUESTA - SE EVALUA LA SELECCION DE PREGUNTA O DECISION
				}else if(BoxResponse2.getSelectedItem().toString().equals("Pregunta") | BoxResponse2.getSelectedItem().toString().equals("Decisión") ) {
					String box = comboBoxDecision2.getSelectedItem().toString();
					String answer = AnswersRadio[1].getText();
					
					//SE EVALUA SI ES ACEPTADO
					if(box.equals("Aceptado")) {
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
						try {
							//SE CONTABILIZA EL NUMERO TOTAL DE RUTA
							query = "SELECT count(route) FROM historical where response=(?)";
							sendQuery = connect.prepareStatement(query);
							sendQuery.setString(1, answer);
							rs = sendQuery.executeQuery();
							if(rs.next()) {
								countRoute = rs.getInt(1);
							}
							System.out.println("Cuenta ruta " + countRoute);
							System.out.println("Casos " + casos);
							//SE EVALÚA EL NUMERO DE CASOS QUE SE PROPORCIONO CON EL NUMERO DE RUTA
							if(countRoute == casos) {
								//SE CONTABILIZA EL NUMERO DE ACEPTADOS QUE HAY DE HACEPTADOS EN ESA RESPUESTA
								query ="SELECT count(1_0) FROM historical where response=(?) and 1_0=1";
								sendQuery = connect.prepareStatement(query);
								sendQuery.setString(1, answer);
								rs = sendQuery.executeQuery();
								if(rs.next()) {
									countCases = rs.getInt(1);
								}
								System.out.println("casos contados = " + countCases);
								System.out.println("numero de casos  = " + casos);
								confianza = (countCases/casos) * 100;
								System.out.println("Confianza = " + confianza);
								if(confianza < porcentaje) {
									JOptionPane.showMessageDialog(null, "Su credito ha sido rechazado");
									
									query = "UPDATE response SET link=0 where text=(?)";
									sendQuery = connect.prepareStatement(query);
									sendQuery.setString(1, answer);
									sendQuery.execute();
									
									query = "SELECT route, link FROM response where text=(?)";
									sendQuery = connect.prepareStatement(query);
									sendQuery.setString(1, answer);
									rs = sendQuery.executeQuery();
									while(rs.next()) {
										route = rs.getInt("route");
										link = rs.getInt("link");
									}
									
									query = "UPDATE historical SET route=(?), response=(?), 1_0=(?) where id_historical=(?)";
									sendQuery = connect.prepareStatement(query);
									sendQuery.setInt(1,route);
									sendQuery.setString(2,answer);
									sendQuery.setInt(3,link);
									sendQuery.setInt(4, idUser);
									sendQuery.execute();
									
									query = "UPDATE question SET ok=1 where ok=0";
									sendQuery = connect.prepareStatement(query);
									sendQuery.execute();
								}
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------								
								
								
							}else if(countRoute >= casos){
								JOptionPane.showMessageDialog(null, "Se han superado los casos");
								//QUE HACER AQUI --------------------------
							}else {
								query = "UPDATE response SET link=1 where text=(?)";
								sendQuery = connect.prepareStatement(query);
								sendQuery.setString(1, answer);
								sendQuery.execute();
								
								query = "SELECT route, link FROM response where text=(?)";
								sendQuery = connect.prepareStatement(query);
								sendQuery.setString(1, answer);
								rs = sendQuery.executeQuery();
								while(rs.next()) {
									route = rs.getInt("route");
									link = rs.getInt("link");
								}
								
								query = "UPDATE historical SET route=(?), response=(?), 1_0=(?) where id_historical=(?)";
								sendQuery = connect.prepareStatement(query);
								sendQuery.setInt(1,route);
								sendQuery.setString(2,answer);
								sendQuery.setInt(3,link);
								sendQuery.setInt(4, idUser);
								sendQuery.execute();
								
								query = "UPDATE question SET ok=1 where ok=0";
								sendQuery = connect.prepareStatement(query);
								sendQuery.execute();
								
								JOptionPane.showMessageDialog(null, "Su crédito ha sido aceptado");
							}
							
							
							
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						//SE EVALUA SI ES RECHAZADO
					}else if(box.equals("Rechazado")){
						query = "UPDATE response SET link=0 where text=(?)";
						try {
							sendQuery = connect.prepareStatement(query);
							sendQuery.setString(1, answer);
							sendQuery.execute();
							
							query = "SELECT route, link FROM response where text=(?)";
							sendQuery = connect.prepareStatement(query);
							sendQuery.setString(1, answer);
							rs = sendQuery.executeQuery();
							while(rs.next()) {
								route = rs.getInt("route");
								link = rs.getInt("link");
							}
							
							query = "UPDATE historical SET route=(?), response=(?), 1_0=(?) where id_historical=(?)";
							sendQuery = connect.prepareStatement(query);
							sendQuery.setInt(1,route);
							sendQuery.setString(2,answer);
							sendQuery.setInt(3,link);
							sendQuery.setInt(4, idUser);
							sendQuery.execute();
							
							query = "UPDATE question SET ok=1 where ok=0";
							sendQuery = connect.prepareStatement(query);
							sendQuery.execute();
							
							JOptionPane.showMessageDialog(null, "Su crédito ha sido rechazado");
							
							/*query = "UPDATE response SET link=NULL where link=0";
							sendQuery = connect.prepareStatement(query);
							sendQuery.execute();
							
							query = "UPDATE response SET link=NULL where link=-1";
							sendQuery = connect.prepareStatement(query);
							sendQuery.execute();
							
							query = "UPDATE response SET link=NULL where link=1";
							sendQuery = connect.prepareStatement(query);
							sendQuery.execute();*/
							
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						//SE EVALUA CUANDO ES UNA PREGUNTA
					}else {
						//SE BUSCA EL ID DE LA SIGUIENTE PREGUNTA PARA MANDARLO AL JFRAME
						querySearch ="SELECT id_question FROM question where text=(?)";
						query = "UPDATE response SET link=-1 where text=(?)";
						int nextIDquestion = 0;
						try {
							sendQuery = connect.prepareStatement(querySearch);
							sendQuery.setString(1, box);
							rs = sendQuery.executeQuery();
							while(rs.next()) {
								nextIDquestion = rs.getInt("id_question");
							}
							
							sendQuery = connect.prepareStatement(query);
							sendQuery.setString(1, answer);
							sendQuery.execute();
							
							String thisQuestion = comboBoxQuestion.getSelectedItem().toString();
							query = "UPDATE question SET ok=0 where text=(?)";
							sendQuery = connect.prepareStatement(query);
							sendQuery.setString(1, thisQuestion);
							sendQuery.execute();
							
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						//MANDA AL SEGUNDO JFRAME
						Pregunta2 nextQuestion = new Pregunta2(nextIDquestion, idUser, frame2);
						nextQuestion.setVisible(true);
						
						System.out.println("NEXT ID" + nextIDquestion); 
						System.out.println("1 - " + box); 
					}
				}
				
				
				
			}

		});
		
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
	}
	
	private class ItemHandler implements ItemListener{

		public void itemStateChanged(ItemEvent e) {
			
			String selected = BoxResponse1.getSelectedItem().toString();
			if(selected.equals("Pregunta")) {
					
				Questions questions = new Questions();
				DefaultComboBoxModel modelQuestions = new DefaultComboBoxModel(questions.showQuestions());
				comboBoxDecision1.setModel(modelQuestions);
					
				System.out.println("Primer if" + selected);
				
			}else if(selected.equals("Decisión")) {
				DefaultComboBoxModel modelDec = new DefaultComboBoxModel();
				modelDec.addElement("Selecciona una opcion");
				modelDec.addElement("Aceptado");
				modelDec.addElement("Rechazado");
				comboBoxDecision1.setModel(modelDec);
				System.out.println(selected);
					
			}
			
		}
		
	}
	
	private class ItemHandler2 implements ItemListener{

		public void itemStateChanged(ItemEvent e) {
			
			String selected = BoxResponse2.getSelectedItem().toString();
			if(selected.equals("Pregunta")) {
					
				Questions questions = new Questions();
				DefaultComboBoxModel modelQuestions = new DefaultComboBoxModel(questions.showQuestions());
				comboBoxDecision2.setModel(modelQuestions);
					
				System.out.println("Primer if" + selected);
				
			}else if(selected.equals("Decisión")) {
				DefaultComboBoxModel modelDec = new DefaultComboBoxModel();
				modelDec.addElement("Selecciona una opcion");
				modelDec.addElement("Aceptado");
				modelDec.addElement("Rechazado");
				comboBoxDecision2.setModel(modelDec);
				System.out.println(selected);
					
			}
			
		}
		
	}
}
