package controller;
/**
 * This class allows access and requests to database
 * @author The Coding Bang Fraternity
 * @version 1.0
 */

import java.sql.*;
import com.mysql.jdbc.Connection;

public class Database {

	private final String url = "jdbc:mysql://localhost:3306/coding_week";
	private final String user = "root";
	private final String password = "";
	Connection connexion = null;

	/**
	 * Constructor
	 */
	public Database() {
		this.connect();
	}

	/**
	 * Init the connection with DB
	 * Print errors
	 */
	private void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connexion = (Connection) DriverManager.getConnection(url, user, password);
			
			System.out.println("Connected");
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Close connection with DB, should be call at the program ends
	 */
	public void close() {
		if (connexion != null) {
			try {
				connexion.close();
			} catch (SQLException ignore) {
			}
		}
	}
	
	/**
	 * Make a select request to DB
	 * @param request
	 * @return results or null
	 */
	public ResultSet select_request(String request){
		try {
			Statement statement = connexion.createStatement();
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
			/* Création de l'objet gérant les requêtes */
			Statement statement = connexion.createStatement();
			return statement.executeUpdate(request);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	/**
	while ( resultat.next() ) {
    	int iduser = resultat.getInt( "id" );
    	String emailuser = resultat.getString( "email" );
    }
	*/
}
