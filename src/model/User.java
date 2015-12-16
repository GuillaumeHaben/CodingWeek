/**
 * This class collects all the informations about a specific user
 * @author The Coding Bang Fraternity
 * @version 3.0
 */

package model;

import java.sql.SQLException;

import controller.Database;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import twitter4j.GeoLocation;
import twitter4j.PagableResponseList;
import twitter4j.Paging;
import twitter4j.Place;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class User {

	private LongProperty id;

	private StringProperty name;
	private StringProperty screen_name;
	private StringProperty description;
	private IntegerProperty followers_count;
	private IntegerProperty friends_count;
	private IntegerProperty statuses_count;

	private Twitter twitter;
	private Database db;

	/**
	 * @param name : User's name
	 * @param twitter : Object Twitter
	 */
	public User(String sc_name, Twitter twitter) {
		this.screen_name = new SimpleStringProperty(sc_name);
		this.twitter = twitter;
		db = new Database();
	}
	
	public User(Long id, String name, String sc_name) {
		this.id = new SimpleLongProperty(id);
		this.name = new SimpleStringProperty(name);
		this.screen_name = new SimpleStringProperty(sc_name);
	}

	/**
	 * Catch all the followers / Following
	 * 
	 * @param follow : "Followers" OR "Following"
	 */
	public int get(String follow) {
		try {
			
			// Init attributes
			long cursor = -1;
			PagableResponseList<twitter4j.User> result;
			
			int id_request = db.getAutoIncRequest();
			
			// Twitter request
			if (follow.compareTo("Followers") == 0)
				result = twitter.getFollowersList(screen_name.get(), cursor);
			else
				result = twitter.getFriendsList(screen_name.get(), cursor);


			if(result.size() != 0) {
				// Insert a new collect
				String query = "INSERT INTO request(type, reference, req) VALUES('user','@" + screen_name.get() + "', '" + follow +"')";
				db.request(query);
				
				do {
					if(cursor != -1){
						if (follow.compareTo("Followers") == 0)
							result = twitter.getFollowersList(screen_name.get(), cursor);
						else
							result = twitter.getFriendsList(screen_name.get(), cursor);
					}
					
					for (twitter4j.User user : result) {
						String name = user.getName().replace("\'", "\\'");
						String sc_name = user.getScreenName().replace("\'", "\\'");
	
						query = "INSERT INTO user VALUES(" + user.getId() + "," + id_request + ",'" + name + "','" + sc_name + "');";
						db.request(query);
					}
				} while ((cursor = result.getNextCursor()) != 0);
				
				return id_request;
			}
		} catch (TwitterException e) {
			System.out.println("The user doesn't exist.. :'(");
		} catch ( SQLException e){
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * Catch the 100 last likes
	 */
	public int getLikes() {
		try {
			// Request to Twitter
			ResponseList<Status> result = twitter.getFavorites(screen_name.get(), new Paging(1, 100));

			if(result.size() != 0){
				// Insert new collect
				String query = "INSERT INTO request(type, reference, req) VALUES('tweet','@" + screen_name.get() + "', 'likes')";
				db.request(query);
	
				return getObjectTweet(result);
			}
		} catch (TwitterException | SQLException e) {
			System.out.println("The user doesn't exist.. :'(");
		}
		return -1;
	}

	/**
	 * Catch the 100 last tweets
	 */
	public int startRequest() {
		try {
			// Request to Twitter
			ResponseList<Status> result = twitter.getUserTimeline(screen_name.get(), new Paging(1, 100));

			if(result.size() != 0){
				// Insert new collect
				String query = "INSERT INTO request(type, reference) VALUES('tweet','@" + screen_name.get() + "', 'timeline')";
				db.request(query);
			
				return getObjectTweet(result);
			}
		} catch (TwitterException | SQLException e) {
			System.out.println("The user doesn't exist.. :'(");
		}
		return -1;
	}

	/**
	 * Insert into DB new Tweet Object - Print
	 * 
	 * @param result : Tweet obtenus
	 * @throws SQLException
	 */
	@SuppressWarnings("deprecation")
	private int getObjectTweet(ResponseList<Status> result) throws SQLException {
		int id_request = db.getAutoIncRequest();

		for (Status status : result) {

			// Fetch all the available informations
			String text = status.getText().replace("\'", "\\'");
			String name = status.getUser().getName().replace("\'", "\\'");
			String sc_name = status.getUser().getScreenName().replace("\'", "\\'");

			java.util.Date date_tweet = status.getCreatedAt();

			int retweet = status.getRetweetCount();
			Place p = status.getPlace();
			GeoLocation g = status.getGeoLocation();

			String city = null, country = null;
			double latitude = 0, longitude = 0;
			if (p != null) {
				city = p.getName();
				country = p.getCountry();
			}

			if (g != null) {
				latitude = g.getLatitude();
				longitude = g.getLongitude();
			}

			// Save the Tweet into DB Sun Aug 16 20:55:42 CEST 2015
			String query = "INSERT INTO tweet VALUES(" + status.getId() + "," + id_request + ",'" + name + "','"
					+ sc_name + "','" + text + "', " + retweet + ", '" + city + "', '" + country + "', " + latitude
					+ ", " + longitude + ", STR_TO_DATE('" + date_tweet.toGMTString() + "','%d %b %Y %H:%i:%s GMT'));";
			db.request(query);
		}
		return id_request;
	}

	/**
	 * Save the profiles informations of the current user
	 */
	public void getInformation() {
		try {
			twitter4j.User user = twitter.showUser(screen_name.get());

			name =  new SimpleStringProperty(user.getName());
			description = new SimpleStringProperty(user.getDescription());
			followers_count = new SimpleIntegerProperty(user.getFollowersCount());
			friends_count = new SimpleIntegerProperty(user.getFriendsCount());
			statuses_count = new SimpleIntegerProperty(user.getStatusesCount());
			id = new SimpleLongProperty(user.getId());
		

			System.out.println("\nInformations : ");
			System.out.println(
					"Name : " + name + "\nDescription : " + description + "\nNumber of followers : " + followers_count
							+ "\nNumber of followings : " + friends_count + "\nNumber of tweets : " + statuses_count);

		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	public StringProperty nameProperty() {
		return name;
	}

	public StringProperty screen_nameProperty() {
		return screen_name;
	}

	public LongProperty idProperty() {
		return id;
	}

	public StringProperty descriptionProperty() {
		return description;
	}

	public IntegerProperty followers_countProperty() {
		return followers_count;
	}

	public IntegerProperty friends_countProperty() {
		return friends_count;
	}

	public IntegerProperty statuses_countProperty() {
		return statuses_count;
	}
}
