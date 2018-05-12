package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import model.Connection;

import javax.swing.JButton;

public class tableView extends JFrame {

	private JPanel contentPane;
	private DefaultTableModel model;
	private JTable table_1;
	String listaProductos [] = new String[100];
	int prod = 0;
	
	private Statement sendQuery;
	
	private Connection myConnection;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					tableView frame = new tableView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public tableView() {
		setTitle("Tabla de estadísticas");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 629, 488);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(310, 380, 117, 29);
		contentPane.add(btnAceptar);
		
		/*table_1 = new JTable();
		table_1.setBounds(143, 114, 404, 233);
		Object[] comumns = {"Código", "Nombre", "Precio Unitario", "Cantidad", "Precio"};
		model = new DefaultTableModel();
		model.setColumnIdentifiers(comumns);
		table_1.setModel(model);
		contentPane.add(table_1);*/
		
		table_1 = new JTable();
		table_1.setBounds(143, 114, 404, 233);
		contentPane.add(table_1);
		
		
		//-----------------------------------------------------------------
		myConnection = new Connection();
		java.sql.Connection connect = myConnection.getMyConnection();
		
		String querySearch = "SELECT * FROM historical";
		try {
			sendQuery = connect.createStatement();
			ResultSet rs = sendQuery.executeQuery(querySearch);
			ResultSetMetaData rsmd = rs.getMetaData();
			int col = rsmd.getColumnCount();
			DefaultTableModel model = new DefaultTableModel();
			for(int i = 1; i <= col; i++) {
				model.addColumn(rsmd.getColumnLabel(i));
			}
			while(rs.next()) {
				String fila[] = new String[col];
				for(int j = 0; j < col; j++) {
					fila[j] = rs.getString(j+1);
				}
				model.addRow(fila);
			}
			table_1.setModel(model);
			rs.close();
			connect.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/*final Object[] row = new Object[5];
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				myConnection = new Connection();
				java.sql.Connection connect = myConnection.getMyConnection();
				String querySearch = "Select * FROM historical";
				
				try {
					PreparedStatement sendQuery = connect.prepareStatement(querySearch);
					//sendQuery.setString(1, codigo);
					ResultSet rs = sendQuery.executeQuery();
					if(rs.next()) {
						int id = rs.getInt(1);
						int route = rs.getInt(2);
						String response = rs.getString(3);
						int aceptadoRechazado = rs.getInt(4);
						String name = rs.getString(5);
						
						row[0] = id;
						row[1] = route;
						row[2] = response;
						row[3] = aceptadoRechazado;
						row[4] = name;
						
						listaProductos[prod] = id + " " + route + " " + response + " "  + aceptadoRechazado + " " + name;
						prod++;
						
						model.addRow(row);

					}else {
		
						JOptionPane.showMessageDialog(null, "No existe el producto.");
					}
					
				} catch (SQLException e2) {
					System.out.println(e2.getMessage());
					e2.printStackTrace();
				}
		
			}
		});*/
		
		
	}
}
