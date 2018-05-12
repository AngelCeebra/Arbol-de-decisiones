package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class Questions {
	private int id;
	private String text;
	Connection myConnection = new Connection();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String toString() {
		return text;
	}
	
	public Vector<Questions>showQuestions(){
		java.sql.Connection connect = myConnection.getMyConnection();
		
		String querySearch = "SELECT * FROM question where ok=1";
		Vector<Questions> data = new Vector<Questions>();
		Questions dat = null;
		
		try {
			//SEARCH QUESTION
			PreparedStatement sendQuery = connect.prepareStatement(querySearch);
			ResultSet rs = sendQuery.executeQuery();
			
			dat = new Questions();
			dat.setId(0);
			dat.setText("Selecciona la pregunta");
			data.add(dat);

			while(rs.next()) {
				dat = new Questions();
				dat.setId(rs.getInt("id_question"));
				dat.setText(rs.getString("text"));
				data.add(dat);
			}
			
			rs.close();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return data;
		
	}
}
