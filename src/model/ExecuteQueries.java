package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ExecuteQueries {

	private Connection myConnection;
	private PreparedStatement sendQuery;
	private ResultSet rs;
	private String querySearch;
	private String queryInsert;
	private String queryModify;
	private int route = 1;
	
	public ExecuteQueries() {
		myConnection = new Connection();
	}
	
	public boolean searchUser(String usuario, String contrasena) {
		
		java.sql.Connection connect = myConnection.getMyConnection();
		
		querySearch = "SELECT * FROM users where name=(?)";
		String nombreQ = null;
		String contrasenaQ = null;
		try {
			sendQuery = connect.prepareStatement(querySearch);
			sendQuery.setNString(1, usuario);
			rs = sendQuery.executeQuery();
			while(rs.next()) {
				nombreQ = rs.getString(2);
				contrasenaQ = rs.getString(3);
				if(contrasenaQ.equals(contrasena)) {
					//JOptionPane.showMessageDialog(null, "Bienvenido " + nombreQ);
					return true;
				}else {
					//JOptionPane.showMessageDialog(null, "Usuario no existe");
					return false;
				}
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return false;
	}
	
	public void insertarNuevaPregunta(String question, String answer1, String answer2, String answer3, String answer4) {
		
		java.sql.Connection connect = myConnection.getMyConnection();
		int id_question = 0;
		
		queryInsert = "INSERT INTO question(text, ok) VALUES(?,?)";
		try {
			//INSERT QUESTION
			sendQuery = connect.prepareStatement(queryInsert);
			sendQuery.setString(1, question);
			sendQuery.setInt(2, 1);
			sendQuery.execute();
			System.out.println("Se ha insertado la pregunta correctamente");
			
			//SEARCH ID_QUESTION
			querySearch ="SELECT * FROM question where text=(?)";
			sendQuery = connect.prepareStatement(querySearch);
			sendQuery.setNString(1, question);
			rs = sendQuery.executeQuery();
			while(rs.next()) {
				id_question = rs.getInt(1);
			}
			
			//INSERT ANSWERS
			if(answer3.length()==0 & answer4.length()==0) {
				queryInsert = "INSERT INTO response(id_question, text, route) VALUES(?,?,?)";
				sendQuery = connect.prepareStatement(queryInsert);
				sendQuery.setInt(1, id_question);
				sendQuery.setString(2, answer1);
				sendQuery.setInt(3, route);
				sendQuery.execute();	
				route ++;
				
				sendQuery = connect.prepareStatement(queryInsert);
				sendQuery.setInt(1, id_question);
				sendQuery.setString(2, answer2);
				sendQuery.setInt(3, route);
				sendQuery.execute();
				route ++;
			}else if(answer4.length()==0) {
				queryInsert = "INSERT INTO response(id_question, text, route) VALUES(?,?,?)";
				sendQuery = connect.prepareStatement(queryInsert);
				sendQuery.setInt(1, id_question);
				sendQuery.setString(2, answer1);
				sendQuery.setInt(3, route);
				sendQuery.execute();
				
				sendQuery = connect.prepareStatement(queryInsert);
				sendQuery.setInt(1, id_question);
				sendQuery.setString(2, answer2);
				sendQuery.setInt(3, route);
				sendQuery.execute();
				
				sendQuery = connect.prepareStatement(queryInsert);
				sendQuery.setInt(1, id_question);
				sendQuery.setString(2, answer3);
				sendQuery.setInt(3, route);
				sendQuery.execute();
			}else if(answer2.length()==0 & answer3.length()==0 & answer4.length()==0){
				JOptionPane.showMessageDialog(null, "No puedes enviar una sola respuesta");
			}else if(answer2.length()==0 & answer2.length()==0 & answer3.length()==0 & answer4.length()==0) {
				JOptionPane.showMessageDialog(null, "No has escrito ninguna respuesta");
			}else {
				queryInsert = "INSERT INTO response(id_question, text) VALUES(?,?,?)";
				sendQuery = connect.prepareStatement(queryInsert);
				sendQuery.setInt(1, id_question);
				sendQuery.setString(2, answer1);
				sendQuery.setInt(3, route);
				sendQuery.execute();
				
				sendQuery = connect.prepareStatement(queryInsert);
				sendQuery.setInt(1, id_question);
				sendQuery.setString(2, answer2);
				sendQuery.setInt(3, route);
				sendQuery.execute();
				
				sendQuery = connect.prepareStatement(queryInsert);
				sendQuery.setInt(1, id_question);
				sendQuery.setString(2, answer3);
				sendQuery.setInt(3, route);
				sendQuery.execute();
				
				sendQuery = connect.prepareStatement(queryInsert);
				sendQuery.setInt(1, id_question);
				sendQuery.setString(2, answer4);
				sendQuery.setInt(3, route);
				sendQuery.execute();
			}
			
			JOptionPane.showMessageDialog(null, "Se ha insertado correctamente.");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public boolean searchName(String name) {
		
		java.sql.Connection connect = myConnection.getMyConnection();
		
		querySearch = "SELECT * FROM historical where name=(?)";
		String nameQ;
		
		try {
			sendQuery = connect.prepareStatement(querySearch);
			sendQuery.setNString(1, name);
			rs = sendQuery.executeQuery();
			while(rs.next()) {
				nameQ = rs.getString("name");
				System.out.println(nameQ);
				if(name.equals(nameQ)) {
					
					return true;
				}else {
					//JOptionPane.showMessageDialog(null, "El usuario " + name + " no existe");
					return false;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}
	
	public void insertName(String name) {
		java.sql.Connection connect = myConnection.getMyConnection();
		
		queryInsert = "INSERT INTO historical(name) VALUES(?)";
		
		try {
			sendQuery = connect.prepareStatement(queryInsert);
			sendQuery.setString(1, name);
			sendQuery.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public String searchBBDD() {
		
		java.sql.Connection connect = myConnection.getMyConnection();
		
		querySearch = "SELECT * FROM question where id_question=(?)";
		
		return queryInsert;
		
	}

	public void Configuracion(int numCasos, int porcentaje) {
		
		
		
	}
	
	
	
}
