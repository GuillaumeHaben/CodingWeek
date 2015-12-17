/**
 * This class allows access and requests to database
 * @author The Coding Bang Fraternity
 * @version 3.0
 */

package model;

import java.sql.*;

public class Database {

	java.sql.Connection connection = null;

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
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:mydb.db");
			
			Statement stmt = connection.createStatement();
		    String sql = "CREATE TABLE IF NOT EXISTS `tweet` ( `id_tweet` bigint(20) NOT NULL," +
		      "`id_request` int(5) NOT NULL, `name` varchar(25) NOT NULL, `screenName` varchar(25) NOT NULL, " +
		      "`text` varchar(170) NOT NULL, `retweet` int(8) NOT NULL, `city` varchar(40) DEFAULT NULL, " +
		      "`country` varchar(30) DEFAULT NULL, `latitude` double NOT NULL,  `longitude` double NOT NULL, " +
		      "`date_tweet` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, `content` varchar(150) NOT NULL, PRIMARY KEY " +
		      "(`id_tweet`,`id_request`))"; 
		    stmt.executeUpdate(sql);
		    stmt.close();
		    
		    sql = "CREATE TABLE IF NOT EXISTS `user` ( `id_user` bigint(20) NOT NULL, `id_request` int(5) NOT NULL, " +
		    		"`name` varchar(25) NOT NULL, `screen_name` varchar(25) NOT NULL, PRIMARY KEY (`id_user`,`id_request`) " +
		    		")";
			stmt.executeUpdate(sql);
		
			sql = "CREATE TABLE IF NOT EXISTS `request` ( `id_request` INTEGER PRIMARY KEY NOT NULL, `type` varchar(10) " +
					"NOT NULL, `reference` varchar(30) NOT NULL, `req` varchar(20) NOT NULL)";
			stmt.executeUpdate(sql);
			stmt.close();
			close();
		      
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Init a connection
	 */
	public void init() {
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:mydb.db");
		} catch (SQLException e) {
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
	public int reinit(){
		try {
			if (connection.isClosed())
				init();
			connection = DriverManager.getConnection("jdbc:sqlite:mydb.db");
			Statement statement = connection.createStatement();
			statement.executeUpdate("DELETE FROM request");
			statement.executeUpdate("DELETE FROM tweet");
			statement.executeUpdate("DELETE FROM user");
			statement.executeUpdate("VACUUM");
			statement.close();
			close();
		} catch (SQLException e) {
			return -1;
		}
		return 0;
	}
	
	/**
	 * Make a select request to DB
	 * @param request
	 * @return results or null
	 */
	public ResultSet select_request(String request){
		try {
			if (connection.isClosed())
				init();
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
			if (connection.isClosed())
				init();
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
		if (connection.isClosed())
			init();
		String query = " SELECT count(id_request) as auto FROM request";
		ResultSet res = this.select_request(query);
		
		if(!res.next())
			return 0;

		return res.getInt("auto") +1;
	}
}
