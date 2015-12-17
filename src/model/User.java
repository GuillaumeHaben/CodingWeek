/**
 * This class collects all the informations about a specific User
 * @author The Coding Bang Fraternity
 * @version 4.0
 */

package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import twitter4j.GeoLocation;
import twitter4j.MediaEntity;
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
	private IntegerProperty favourites_count;
	private StringProperty image_URL;
	
	private int tweet_range = 1;
	private int like_range = 1;
	private boolean more = false;

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
	
	public User(Long id, String name, String sc_name, String image) {
		this.id = new SimpleLongProperty(id);
		this.name = new SimpleStringProperty(name);
		this.screen_name = new SimpleStringProperty(sc_name);
		this.image_URL = new SimpleStringProperty(image);
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
			int nb_cursor = 0;
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
				if(db.request(query) == -1) return -1;
				
				do {
					if(cursor != -1){
						if (follow.compareTo("Followers") == 0)
							result = twitter.getFollowersList(screen_name.get(), cursor);
						else
							result = twitter.getFriendsList(screen_name.get(), cursor);
					}
					
					for (twitter4j.User user : result) {
						String name = user.getName().replace("\'", "\'\'");
						String sc_name = user.getScreenName().replace("\'", "\'\'");
						String image = user.getProfileImageURL();
	
						query = "INSERT INTO user VALUES(" + user.getId() + "," + id_request + ",'" + name + "','" + sc_name 
								+ "', '" + image + "');";
						
						db.request(query);
						
					}
					nb_cursor++;
				} while ((cursor = result.getNextCursor()) != 0 && nb_cursor <5);
				db.close();
				
				return id_request;
			}
		} catch (TwitterException e) {
			e.printStackTrace();
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
			ResponseList<Status> result = twitter.getFavorites(screen_name.get(), new Paging(like_range, like_range + 100));
			
			boolean more_tweet = more;
			if(result.size() != 0){
				if(more){
					// Insert new collect
					String query = "INSERT INTO request(type, reference, req) VALUES('tweet','@" + screen_name.get() + "', 'likes')";
					if(db.request(query) == -1) return -1;
				}
				like_range += 100;
				
				return getObjectTweet(result, more_tweet, "likes");
			}
		} catch (TwitterException | SQLException e) {
		}
		return -1;
	}

	/**
	 * Catch the 100 last tweets
	 */
	public int startRequest() {
		try {
			// Request to Twitter
			ResponseList<Status> result = twitter.getUserTimeline(screen_name.get(), new Paging(tweet_range, tweet_range +50));

			boolean more_tweet = more;
			if(result.size() != 0){
				if(more){
					// Insert new collect
					String query = "INSERT INTO request(type, reference, req) VALUES('tweet','@" + screen_name.get() + "', 'timeline')";
					if(db.request(query) == -1) return -1;
					more = false;
				}
				tweet_range += 50;
				
				return getObjectTweet(result, more_tweet, "timeline");
			}
		} catch (TwitterException | SQLException e) {
		}
		return -1;
	}

	/**
	 * Insert into DB new Tweet Object - Print
	 * 
	 * @param result : Tweet obtenus
	 * @throws SQLException
	 */
	private int getObjectTweet(ResponseList<Status> result, boolean more_tweet, String req) throws SQLException {
		
		int id_request = -1;
		if(!more_tweet)
			id_request = db.getAutoIncRequest();
		else {
			ResultSet tweetsResult = db.select_request("SELECT id_request as id FROM request WHERE reference = '@"
					+ screen_name + "' AND req = '" + req + "' LIMIT 1");
			if (tweetsResult.next())
				id_request = tweetsResult.getInt("id");	
			else return -1;
		}
		
		for (Status status : result) {

			// Fetch all the available informations
			String text = status.getText().replace("\'", "\'\'");
			String name = status.getUser().getName().replace("\'", "\'\'");
			String sc_name = status.getUser().getScreenName().replace("\'", "\'\'");

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
			
			String img_profile = status.getUser().getProfileImageURL();
			String URL = "";
			MediaEntity[] mediaEntity = status.getMediaEntities();
			if(mediaEntity.length > 0){
				URL = mediaEntity[0].getMediaURL().toString();
			}

			if (g != null) {
				latitude = g.getLatitude();
				longitude = g.getLongitude();
			}

			// Save the Tweet into DB Sun Aug 16 20:55:42 CEST 2015
			String query = "INSERT INTO tweet VALUES(" + status.getId() + "," + id_request + ",'" + name + "','"
					+ sc_name + "','" + text + "', " + retweet + ", '" + city + "', '" + country + "', " + latitude
					+ ", " + longitude + ", " + date_tweet.getTime() + ", '" + img_profile + "', '" + URL + "');";
			db.request(query);
		}
		db.close();
		return id_request;
	}

	/**
	 * Save the profiles informations of the current user
	 */
	public int getInformation() {
		try {
			twitter4j.User user = twitter.showUser(screen_name.get());

			image_URL = new SimpleStringProperty(user.getProfileImageURL());
			name =  new SimpleStringProperty(user.getName());
			description = new SimpleStringProperty(user.getDescription());
			followers_count = new SimpleIntegerProperty(user.getFollowersCount());
			friends_count = new SimpleIntegerProperty(user.getFriendsCount());
			statuses_count = new SimpleIntegerProperty(user.getStatusesCount());
			id = new SimpleLongProperty(user.getId());
			favourites_count = new SimpleIntegerProperty(user.getFavouritesCount());

		} catch (TwitterException e) {
			return -1;
		}
		return 0;
	}
	
	public void setMore(boolean b) {
		more = b;
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
	
	public IntegerProperty favourites_countProperty() {
		return favourites_count;
	}
	
	public StringProperty image_URLProperty() {
		return image_URL;
	}
}
