package model;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection {
java.sql.Connection myConnect= null;
	
	public Connection() {
		
	}
	//192.168.99.100
	public java.sql.Connection getMyConnection() {
		try {
			myConnect = DriverManager.getConnection("jdbc:mysql://192.168.99.100/arbol_decisiones", "root", "mysql");//192.168.99.100
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return myConnect;
	}
}
