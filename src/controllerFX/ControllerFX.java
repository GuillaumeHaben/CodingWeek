/**
 * This class collects supervise the other controller
 * Give some generic methods
 * 
 * @author The Coding Bang Fraternity
 * @version 3.0
 */

package controllerFX;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.ListView;
import model.Database;
import model.Tweet;
import model.User;

public abstract class ControllerFX {
	
	protected static MainApp mainApp;
	protected Database db;
	
	protected ControllerFX(){
		db = new Database();
	}
	
	/**
	 * Create a User's object list
	 * @param rs : query results
	 * @param list : ListView
	 */
	protected void createUsers(ResultSet rs, ListView<User> list){
		try {
			while(rs.next()){
				list.getItems().add(new User(rs.getLong("id_user"), rs.getString("name"), rs.getString("screen_name")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create a User's object list
	 * @param rs : query results
	 * @param list : ListView
	 */
	protected void createTweets(ResultSet rs, ListView<Tweet> list){
		try {
			while(rs.next()){
				Tweet tweet = new Tweet(rs.getLong("id_tweet"), rs.getString("name"), rs.getString("screen_name"), 
						rs.getString("text"), rs.getInt("retweet"), rs.getString("city"), rs.getString("country"),
						rs.getInt("latitude"), rs.getInt("longitude"), rs.getDate("date_tweet"));
				list.getItems().add(tweet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

    /**
     * Clean a User List
     * @param list
     */
    protected void cleanUser_tweetScreen(ListView<User> list) {
            list.getItems().clear();
    }

    /**
     * Clean a Tweet List
     * @param list
     */
    protected void cleanTweetScreen(ListView<Tweet> list) {
            list.getItems().clear();
    }

    /**
     * Link the main app object
     * @param ma : MainApp Object
     */
    @SuppressWarnings("static-access")
	public void setMainApp(MainApp mapp){
    	this.mainApp = mapp;
    }
}
