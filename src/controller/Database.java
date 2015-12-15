/**
 * This class allows access and requests to database
 * @author The Coding Bang Fraternity
 * @version 2.0
 */

package controller;

import java.sql.*;
import com.mysql.jdbc.Connection;

public class Database {

	private final String url = "jdbc:mysql://localhost:3306/coding_week";
	private final String user = "root";
	private final String password = "";
	Connection connection = null;

	/**
	 * Simple constructor
	 */
	public Database() {
		this.connect();
	}

	/**
	 * Init the connection with DB
	 */
	private void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = (Connection) DriverManager.getConnection(url, user, password);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Close connection with DB, should be call at the program ends
	 */
	public void close() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException ignore) {
			}
		}
	}
	
	/**
	 * Reinit request and tweet Table
	 */
	public void reinit(){
		Statement statement;
		try {
			statement = connection.createStatement();
			statement.executeQuery("Truncate request");
			statement.executeQuery("Truncate tweet");
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Make a select request to DB
	 * @param request
	 * @return results or null
	 */
	public ResultSet select_request(String request){
		try {
			Statement statement = connection.createStatement();
			return statement.executeQuery(request);	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Make an insert request to DB
	 * @param request
	 * @return 0 erreur, 1 success on insert request
	 * @return nb row updated on update and delete request
	 */
	public int request(String request){
		try {
			Statement statement = connection.createStatement();
			return statement.executeUpdate(request);
		} catch (SQLException e) {
			System.out.println("ERROR -> "+request);
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * Get the Auto Increment of Request Tabke
	 * @return Auto Increment
	 * @throws SQLException
	 */
	public int getAutoIncRequest() throws SQLException{
		String query = "SELECT `AUTO_INCREMENT` as auto FROM INFORMATION_SCHEMA.TABLES "
				+ "WHERE TABLE_SCHEMA = 'coding_week' AND TABLE_NAME = 'request'";
		java.sql.ResultSet res = this.select_request(query);
		res.next();
		return res.getInt("auto") -1;
	}

}
