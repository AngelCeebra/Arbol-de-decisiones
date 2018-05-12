package view;
											//REALIZAR LA CONFIGURACION Y LAS ESTADISTICAS
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
import view.ConfiguracionFrame;

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

public class TestView2 extends JFrame {

	private JPanel contentPane;

	
	JComboBox comboBoxQuestion = new JComboBox();
	JComboBox BoxResponse1 = new JComboBox();
	JComboBox BoxResponse2 = new JComboBox();
	private String querySearch, querySearch2, queryCount, query;
	PreparedStatement sendQuery;
	ResultSet rs;
	String response, textQuestion;
	int countRows, countRowsQuestions, questionSearch, idUser;
	JLabel[] AnswersRadio;
	LinkedList listAnswers = new LinkedList();
	LinkedList listResponses = new LinkedList();
	LinkedList questionResponse = new LinkedList();
	
	int route = 0, link = 0, routeNumber = 0;
	int countCases = 0, countRoute = 0;
	double confianza;
	String getTextSelected;
	
	static TestView2 frame;
	
	Connection myConnection = new Connection();
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//frame = new TestView1();
					//frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public TestView2(int idQuestion, int idUser, ConfiguracionFrame frame2) {
		
		java.sql.Connection connect = myConnection.getMyConnection();
		double casos = frame2.getNumCasos();
		double porcentaje = frame2.getPorcentaje();

		queryCount = "SELECT count(id_question) FROM response where id_question=(?)";
		querySearch2="SELECT text FROM response where id_question=(?)";
		
		try {
			//COUNT ROWS ID_QUESTION IN TABLE RESPONSE
			sendQuery = connect.prepareStatement(queryCount);
			sendQuery.setInt(1, idQuestion);// NUMBER QUESTION
			rs = sendQuery.executeQuery();
			if(rs.next()) {
				countRows = rs.getInt(1);
			}
			
			//SEARCH QUESTION
			querySearch = "SELECT text FROM question where id_question=(?)";
			sendQuery = connect.prepareStatement(querySearch);
			sendQuery.setInt(1, idQuestion);
			rs = sendQuery.executeQuery();
			while(rs.next()) {
				textQuestion = rs.getString("text");
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

		setTitle("Pregunta");
		setBounds(100, 100, 599, 360);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPregunta = new JLabel("Pregunta:");
		lblPregunta.setBounds(50, 49, 78, 16);
		contentPane.add(lblPregunta);
		
		AnswersRadio = new JLabel[countRows];
		for (int i = 0, v = 105; i < countRows && v <= 255; i++, v+=40){
			AnswersRadio[i] = new JLabel((String) listAnswers.get(i));
			AnswersRadio[i].setBounds(150, v, 375, 20);
			contentPane.add(AnswersRadio[i]);
		}
		
		JRadioButton[] radioAnswer = new JRadioButton[countRows];
		for(int i = 0, v = 105; i < countRows && v <= 255; i++, v+=40) {
			radioAnswer[i] = new JRadioButton();
			radioAnswer[i].setBounds(100, v, 375, 20);
			contentPane.add(radioAnswer[i]);
		}
		
		comboBoxQuestion.setBounds(180, 45, 355, 27);
		comboBoxQuestion.addItem(textQuestion);
		contentPane.add(comboBoxQuestion);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(120, 270, 117, 29);
		contentPane.add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(350, 270, 117, 29);
		contentPane.add(btnCancelar);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBounds(205, 159, 1, 1);
		contentPane.add(desktopPane);
		
		JLabel lblHaPagado = new JLabel("Ha pagado:");
		lblHaPagado.setBounds(250, 201, 90, 16);
		contentPane.add(lblHaPagado);
		
		JButton btnSi = new JButton("Si");
		btnSi.setBounds(205, 229, 75, 29);
		contentPane.add(btnSi);
		
		JButton btnNo = new JButton("No");
		btnNo.setBounds(300, 229, 75, 29);
		contentPane.add(btnNo);
		
		
		
		
		
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				java.sql.Connection connect = myConnection.getMyConnection();
				
				//SE EVALU EL PRIMER RADIOBUTTON
				if(radioAnswer[0].isSelected() == true) {
					getTextSelected = AnswersRadio[0].getText();
					try {
						query = "SELECT link, route, route_number FROM tree where text=(?)";
						sendQuery = connect.prepareStatement(query);
						sendQuery.setString(1, getTextSelected);
						rs = sendQuery.executeQuery();
						while(rs.next()) {
							link = rs.getInt("link");
							route = rs.getInt("route");
							routeNumber = rs.getInt("route_number");
						}
						//SE EVALUA PARA LA SIGUIENTE PREGUNTA
						if(link == -1) {
							//MANDA AL SEGUNDO JFRAME
							TestView2 nextQuestion = new TestView2(route, idUser, frame2);
							nextQuestion.setVisible(true);
						
						//SE EVALUA CUANDO ES ACEPTADO
						}else if(link == 1) {
							try {
		//TODO------------------------------------------------------------------------------------------
								//SE CONTABILIZA EL NUMERO TOTAL DE RUTA
								query = "SELECT count(route) FROM historical where response=(?)";
								sendQuery = connect.prepareStatement(query);
								sendQuery.setString(1, getTextSelected);
								rs = sendQuery.executeQuery();
								if(rs.next()) {
									countRoute = rs.getInt(1);
								}
								System.out.println("Cuenta ruta " + countRoute);
								System.out.println("Casos " + casos);
								
								//SE EVALÚA EL NUMERO DE CASOS QUE SE PROPORCIONO CON EL NUMERO DE RUTA
								if(countRoute == casos) {
									//SE CONTABILIZA EL NUMERO DE ACEPTADOS QUE HAY EN ESA RESPUESTA
									query ="SELECT count(1_0) FROM historical where response=(?) and 1_0=1";
									sendQuery = connect.prepareStatement(query);
									sendQuery.setString(1, getTextSelected);
									rs = sendQuery.executeQuery();
									if(rs.next()) {
										countCases = rs.getInt(1);
									}
									System.out.println("casos contados = " + countCases);
									System.out.println("numero de casos  = " + casos);
									confianza = (countCases/casos) * 100;
									System.out.println("Confianza = " + confianza);
									if(confianza < porcentaje) {
										JOptionPane.showMessageDialog(null, "Su credito ha sido rechazado por en nivel de confianza");
										
										query = "UPDATE historical SET route=(?), response=(?), 1_0=(?) where id_historical=(?)";
										sendQuery = connect.prepareStatement(query);
										sendQuery.setInt(1,routeNumber);
										sendQuery.setString(2,getTextSelected);
										sendQuery.setInt(3,0);
										sendQuery.setInt(4, idUser);
										sendQuery.execute();

									}else {
										query = "UPDATE response SET link=1 where text=(?)";
										sendQuery = connect.prepareStatement(query);
										sendQuery.setString(1, getTextSelected);
										sendQuery.execute();
										
										query = "UPDATE historical SET route=(?), response=(?), 1_0=(?) where id_historical=(?)";
										sendQuery = connect.prepareStatement(query);
										sendQuery.setInt(1,routeNumber);
										sendQuery.setString(2,getTextSelected);
										sendQuery.setInt(3,link);
										sendQuery.setInt(4, idUser);
										sendQuery.execute();
										
										JOptionPane.showMessageDialog(null, "Su crédito ha sido aceptado");
									}
								}else {
									query = "UPDATE response SET link=1 where text=(?)";
									sendQuery = connect.prepareStatement(query);
									sendQuery.setString(1, getTextSelected);
									sendQuery.execute();
									
									query = "UPDATE historical SET route=(?), response=(?), 1_0=(?) where id_historical=(?)";
									sendQuery = connect.prepareStatement(query);
									sendQuery.setInt(1,routeNumber);
									sendQuery.setString(2,getTextSelected);
									sendQuery.setInt(3,link);
									sendQuery.setInt(4, idUser);
									sendQuery.execute();
									
									JOptionPane.showMessageDialog(null, "Su crédito ha sido aceptado");
								}			
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							
						//SE EVALUA CUANDO ES RECHAZADO
						}else {
							try {								
								query = "SELECT route, link, route_number FROM tree where text=(?)";
								sendQuery = connect.prepareStatement(query);
								sendQuery.setString(1, getTextSelected);
								rs = sendQuery.executeQuery();
								while(rs.next()) {
									route = rs.getInt("route");
									link = rs.getInt("link");
									routeNumber = rs.getInt("route_number");
								}
								
								query = "UPDATE historical SET route=(?), response=(?), 1_0=(?) where id_historical=(?)";
								sendQuery = connect.prepareStatement(query);
								sendQuery.setInt(1,routeNumber);
								sendQuery.setString(2,getTextSelected);
								sendQuery.setInt(3,link);
								sendQuery.setInt(4, idUser);
								sendQuery.execute();

								JOptionPane.showMessageDialog(null, "Su crédito ha sido rechazado");								
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				
				//SE EVALUA EL SEGUNDO RADIOBUTTON
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------	
				}else {
					getTextSelected = AnswersRadio[1].getText();
					try {
						query = "SELECT link, route, route_number FROM tree where text=(?)";
						sendQuery = connect.prepareStatement(query);
						sendQuery.setString(1, getTextSelected);
						rs = sendQuery.executeQuery();
						while(rs.next()) {
							link = rs.getInt("link");
							route = rs.getInt("route");
							routeNumber = rs.getInt("route_number");
						}
						//SE EVALUA PARA LA SIGUIENTE PREGUNTA
						if(link == -1) {
							//MANDA AL SEGUNDO JFRAME
							TestView2 nextQuestion = new TestView2(route, idUser, frame2);
							nextQuestion.setVisible(true);
						
						//SE EVALUA CUANDO ES ACEPTADO
						}else if(link == 1) {
		//TODO------------------------------------------------------------------------------------------
							try {
								//SE CONTABILIZA EL NUMERO TOTAL DE RUTA
								query = "SELECT count(route) FROM historical where response=(?)";
								sendQuery = connect.prepareStatement(query);
								sendQuery.setString(1, getTextSelected);
								rs = sendQuery.executeQuery();
								if(rs.next()) {
									countRoute = rs.getInt(1);
								}
								System.out.println("Cuenta ruta " + countRoute);
								System.out.println("Casos " + casos);
								//SE EVALÚA EL NUMERO DE CASOS QUE SE PROPORCIONO CON EL NUMERO DE RUTA
								if(countRoute == casos) {
									//SE CONTABILIZA EL NUMERO DE ACEPTADOS QUE HAY EN ESA RESPUESTA
									query ="SELECT count(1_0) FROM historical where response=(?) and 1_0=1";
									sendQuery = connect.prepareStatement(query);
									sendQuery.setString(1, getTextSelected);
									rs = sendQuery.executeQuery();
									if(rs.next()) {
										countCases = rs.getInt(1);
									}
									System.out.println("casos contados = " + countCases);
									System.out.println("numero de casos  = " + casos);
									confianza = (countCases/casos) * 100;
									System.out.println("Confianza = " + confianza);
									if(confianza < porcentaje) {
										JOptionPane.showMessageDialog(null, "Su credito ha sido rechazado por en nivel de confianza");
										
										query = "UPDATE historical SET route=(?), response=(?), 1_0=(?) where id_historical=(?)";
										sendQuery = connect.prepareStatement(query);
										sendQuery.setInt(1,routeNumber);
										sendQuery.setString(2,getTextSelected);
										sendQuery.setInt(3,0);
										sendQuery.setInt(4, idUser);
										sendQuery.execute();

									}else {
										query = "UPDATE response SET link=1 where text=(?)";
										sendQuery = connect.prepareStatement(query);
										sendQuery.setString(1, getTextSelected);
										sendQuery.execute();
										
										query = "UPDATE historical SET route=(?), response=(?), 1_0=(?) where id_historical=(?)";
										sendQuery = connect.prepareStatement(query);
										sendQuery.setInt(1,routeNumber);
										sendQuery.setString(2,getTextSelected);
										sendQuery.setInt(3,link);
										sendQuery.setInt(4, idUser);
										sendQuery.execute();
										
										JOptionPane.showMessageDialog(null, "Su crédito ha sido aceptado");
									}																			
								}else {
									query = "UPDATE response SET link=1 where text=(?)";
									sendQuery = connect.prepareStatement(query);
									sendQuery.setString(1, getTextSelected);
									sendQuery.execute();
									
									query = "UPDATE historical SET route=(?), response=(?), 1_0=(?) where id_historical=(?)";
									sendQuery = connect.prepareStatement(query);
									sendQuery.setInt(1,routeNumber);
									sendQuery.setString(2,getTextSelected);
									sendQuery.setInt(3,link);
									sendQuery.setInt(4, idUser);
									sendQuery.execute();
									
									JOptionPane.showMessageDialog(null, "Su crédito ha sido aceptado");
								}			
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							
						//SE EVALUA CUANDO ES RECHAZADO
						}else {
							try {
								query = "SELECT route, link, route_number FROM tree where text=(?)";
								sendQuery = connect.prepareStatement(query);
								sendQuery.setString(1, getTextSelected);
								rs = sendQuery.executeQuery();
								while(rs.next()) {
									route = rs.getInt("route");
									link = rs.getInt("link");
									routeNumber = rs.getInt("route_number");
								}
								
								query = "UPDATE historical SET route=(?), response=(?), 1_0=(?) where id_historical=(?)";
								sendQuery = connect.prepareStatement(query);
								sendQuery.setInt(1,routeNumber);
								sendQuery.setString(2,getTextSelected);
								sendQuery.setInt(3,link);
								sendQuery.setInt(4, idUser);
								sendQuery.execute();

								JOptionPane.showMessageDialog(null, "Su crédito ha sido rechazado");								
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//System.out.println("Radio 2 seleccionado");
				}
				
			}

		});
		
		btnSi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				java.sql.Connection connect = myConnection.getMyConnection();
				
				//SE EVALU EL PRIMER RADIOBUTTON
				if(radioAnswer[0].isSelected() == true) {
					getTextSelected = AnswersRadio[0].getText();
					try {
						query = "SELECT link, route, route_number FROM tree where text=(?)";
						sendQuery = connect.prepareStatement(query);
						sendQuery.setString(1, getTextSelected);
						rs = sendQuery.executeQuery();
						while(rs.next()) {
							link = rs.getInt("link");
							route = rs.getInt("route");
							routeNumber = rs.getInt("route_number");
						}
						if(link == 1) {
							try {
		//TODO------------------------------------------------------------------------------------------
								//SE CONTABILIZA EL NUMERO TOTAL DE RUTA
								query = "SELECT count(route) FROM historical where response=(?)";
								sendQuery = connect.prepareStatement(query);
								sendQuery.setString(1, getTextSelected);
								rs = sendQuery.executeQuery();
								if(rs.next()) {
									countRoute = rs.getInt(1);
								}
								System.out.println("Cuenta ruta " + countRoute);
								System.out.println("Casos " + casos);
								
								//SE EVALÚA EL NUMERO DE CASOS QUE SE PROPORCIONO CON EL NUMERO DE RUTA
								if(countRoute == casos) {
									//SE CONTABILIZA EL NUMERO DE ACEPTADOS QUE HAY EN ESA RESPUESTA
									query ="SELECT count(1_0) FROM historical where response=(?) and 1_0=1";
									sendQuery = connect.prepareStatement(query);
									sendQuery.setString(1, getTextSelected);
									rs = sendQuery.executeQuery();
									if(rs.next()) {
										countCases = rs.getInt(1);
									}
									System.out.println("casos contados = " + countCases);
									System.out.println("numero de casos  = " + casos);
									confianza = (countCases/casos) * 100;
									System.out.println("Confianza = " + confianza);
									if(confianza < porcentaje) {
										JOptionPane.showMessageDialog(null, "Su credito ha sido rechazado por en nivel de confianza");
										
										query = "UPDATE historical SET route=(?), response=(?), 1_0=(?) where id_historical=(?)";
										sendQuery = connect.prepareStatement(query);
										sendQuery.setInt(1,routeNumber);
										sendQuery.setString(2,getTextSelected);
										sendQuery.setInt(3,0);
										sendQuery.setInt(4, idUser);
										sendQuery.execute();

									}else {
										query = "UPDATE response SET link=1 where text=(?)";
										sendQuery = connect.prepareStatement(query);
										sendQuery.setString(1, getTextSelected);
										sendQuery.execute();
										
										query = "UPDATE historical SET route=(?), response=(?), 1_0=(?) where id_historical=(?)";
										sendQuery = connect.prepareStatement(query);
										sendQuery.setInt(1,routeNumber);
										sendQuery.setString(2,getTextSelected);
										sendQuery.setInt(3,link);
										sendQuery.setInt(4, idUser);
										sendQuery.execute();
										
										JOptionPane.showMessageDialog(null, "Su crédito ha sido aceptado");
									}																				
								}else {
									query = "UPDATE response SET link=1 where text=(?)";
									sendQuery = connect.prepareStatement(query);
									sendQuery.setString(1, getTextSelected);
									sendQuery.execute();
									
									query = "UPDATE historical SET route=(?), response=(?), 1_0=(?) where id_historical=(?)";
									sendQuery = connect.prepareStatement(query);
									sendQuery.setInt(1,routeNumber);
									sendQuery.setString(2,getTextSelected);
									sendQuery.setInt(3,link);
									sendQuery.setInt(4, idUser);
									sendQuery.execute();
									
									JOptionPane.showMessageDialog(null, "Su crédito ha sido aceptado");
								}			
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

						}						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				
				//SE EVALUA EL SEGUNDO RADIOBUTTON
//------------------------------------------------------------------------------------------------------
				}else {
					getTextSelected = AnswersRadio[1].getText();
					try {
						query = "SELECT link, route, route_number FROM tree where text=(?)";
						sendQuery = connect.prepareStatement(query);
						sendQuery.setString(1, getTextSelected);
						rs = sendQuery.executeQuery();
						while(rs.next()) {
							link = rs.getInt("link");
							route = rs.getInt("route");
							routeNumber = rs.getInt("route_number");
						}
						if(link == 1) {
		//TODO------------------------------------------------------------------------------------------
							try {
								//SE CONTABILIZA EL NUMERO TOTAL DE RUTA
								query = "SELECT count(route) FROM historical where response=(?)";
								sendQuery = connect.prepareStatement(query);
								sendQuery.setString(1, getTextSelected);
								rs = sendQuery.executeQuery();
								if(rs.next()) {
									countRoute = rs.getInt(1);
								}
								System.out.println("Cuenta ruta " + countRoute);
								System.out.println("Casos " + casos);
								//SE EVALÚA EL NUMERO DE CASOS QUE SE PROPORCIONO CON EL NUMERO DE RUTA
								if(countRoute == casos) {
									//SE CONTABILIZA EL NUMERO DE ACEPTADOS QUE HAY EN ESA RESPUESTA
									query ="SELECT count(1_0) FROM historical where response=(?) and 1_0=1";
									sendQuery = connect.prepareStatement(query);
									sendQuery.setString(1, getTextSelected);
									rs = sendQuery.executeQuery();
									if(rs.next()) {
										countCases = rs.getInt(1);
									}
									System.out.println("casos contados = " + countCases);
									System.out.println("numero de casos  = " + casos);
									confianza = (countCases/casos) * 100;
									System.out.println("Confianza = " + confianza);
									if(confianza < porcentaje) {
										JOptionPane.showMessageDialog(null, "Su credito ha sido rechazado por en nivel de confianza");
										
										query = "UPDATE historical SET route=(?), response=(?), 1_0=(?) where id_historical=(?)";
										sendQuery = connect.prepareStatement(query);
										sendQuery.setInt(1,routeNumber);
										sendQuery.setString(2,getTextSelected);
										sendQuery.setInt(3,0);
										sendQuery.setInt(4, idUser);
										sendQuery.execute();

									}else {
										query = "UPDATE response SET link=1 where text=(?)";
										sendQuery = connect.prepareStatement(query);
										sendQuery.setString(1, getTextSelected);
										sendQuery.execute();
										
										query = "UPDATE historical SET route=(?), response=(?), 1_0=(?) where id_historical=(?)";
										sendQuery = connect.prepareStatement(query);
										sendQuery.setInt(1,routeNumber);
										sendQuery.setString(2,getTextSelected);
										sendQuery.setInt(3,link);
										sendQuery.setInt(4, idUser);
										sendQuery.execute();
										
										JOptionPane.showMessageDialog(null, "Su crédito ha sido aceptado");
									}																				
								}else {
									query = "UPDATE response SET link=1 where text=(?)";
									sendQuery = connect.prepareStatement(query);
									sendQuery.setString(1, getTextSelected);
									sendQuery.execute();
									
									query = "UPDATE historical SET route=(?), response=(?), 1_0=(?) where id_historical=(?)";
									sendQuery = connect.prepareStatement(query);
									sendQuery.setInt(1,routeNumber);
									sendQuery.setString(2,getTextSelected);
									sendQuery.setInt(3,link);
									sendQuery.setInt(4, idUser);
									sendQuery.execute();
									
									JOptionPane.showMessageDialog(null, "Su crédito ha sido aceptado");
								}			
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

						}						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//System.out.println("Radio 2 seleccionado");
				}
			}
		});
		
		
		
		
		btnNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				java.sql.Connection connect = myConnection.getMyConnection();
				
				//SE EVALUA EL PRIMER RADIOBUTTON
				if(radioAnswer[0].isSelected() == true) {
					getTextSelected = AnswersRadio[0].getText();
					try {
						query = "SELECT link, route, route_number FROM tree where text=(?)";
						sendQuery = connect.prepareStatement(query);
						sendQuery.setString(1, getTextSelected);
						rs = sendQuery.executeQuery();
						while(rs.next()) {
							link = rs.getInt("link");
							route = rs.getInt("route");
							routeNumber = rs.getInt("route_number");
						}

							try {
								query = "SELECT route, link, route_number FROM tree where text=(?)";
								sendQuery = connect.prepareStatement(query);
								sendQuery.setString(1, getTextSelected);
								rs = sendQuery.executeQuery();
								while(rs.next()) {
									route = rs.getInt("route");
									link = rs.getInt("link");
									routeNumber = rs.getInt("route_number");
								}
								
								query = "UPDATE historical SET route=(?), response=(?), 1_0=(?) where id_historical=(?)";
								sendQuery = connect.prepareStatement(query);
								sendQuery.setInt(1,routeNumber);
								sendQuery.setString(2,getTextSelected);
								sendQuery.setInt(3,0);
								sendQuery.setInt(4, idUser);
								sendQuery.execute();

								JOptionPane.showMessageDialog(null, "Su crédito ha sido rechazado");								
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
												
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				
				//SE EVALUA EL SEGUNDO RADIOBUTTON
//------------------------------------------------------------------------------------------------------
				}else {
					getTextSelected = AnswersRadio[1].getText();
					try {
						query = "SELECT link, route, route_number FROM tree where text=(?)";
						sendQuery = connect.prepareStatement(query);
						sendQuery.setString(1, getTextSelected);
						rs = sendQuery.executeQuery();
						while(rs.next()) {
							link = rs.getInt("link");
							route = rs.getInt("route");
							routeNumber = rs.getInt("route_number");
						}

							try {								
								query = "SELECT route, link, route_number FROM tree where text=(?)";
								sendQuery = connect.prepareStatement(query);
								sendQuery.setString(1, getTextSelected);
								rs = sendQuery.executeQuery();
								while(rs.next()) {
									route = rs.getInt("route");
									link = rs.getInt("link");
									routeNumber = rs.getInt("route_number");
								}
								
								query = "UPDATE historical SET route=(?), response=(?), 1_0=(?) where id_historical=(?)";
								sendQuery = connect.prepareStatement(query);
								sendQuery.setInt(1,routeNumber);
								sendQuery.setString(2,getTextSelected);
								sendQuery.setInt(3,0);
								sendQuery.setInt(4, idUser);
								sendQuery.execute();

								JOptionPane.showMessageDialog(null, "Su crédito ha sido rechazado");								
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

											
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//System.out.println("Radio 2 seleccionado");
				}
			}
		});
		
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
	}
}
