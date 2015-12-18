/**
 * This class collects all the informations about a specific User
 * @author The Coding Bang Fraternity
 * @version 5.0
 */

package model;

import java.io.IOException;
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

	private int tweet_range = 20;
	private int like_range = 20;
	private boolean more = false;

	private Twitter twitter;
	private Database db;

	/**
	 * @param name
	 *            : User's name
	 * @param twitter
	 *            : Object Twitter
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
	 * @param follow
	 *            : "Followers" OR "Following"
	 */
	public int get(String follow) {
		try {
			// Init attributes
			long cursor = -1;
			int nb_cursor = 0;
			PagableResponseList<twitter4j.User> result;

			// Twitter request
			if (follow.compareTo("Followers") == 0)
				result = twitter.getFollowersList(screen_name.get(), cursor);
			else
				result = twitter.getFriendsList(screen_name.get(), cursor);

			if (result.size() != 0) {
				// Insert a new collect
				int id_request = -1;

				ResultSet tweetsResult = db.select_request("SELECT id_request as id FROM request WHERE reference = '@"
						+ screen_name.get() + "' AND req = '" + follow + "' LIMIT 1");

				if (tweetsResult.next()) {
					id_request = tweetsResult.getInt("id");
				} else {
					id_request = db.getAutoIncRequest();
					String query = "INSERT INTO request VALUES(" + id_request + ", 'user','@" + screen_name.get()
							+ "', '" + follow + "')";
					if (db.request(query) == -1)
						return -1;
				}

				do {
					if (cursor != -1) {
						if (follow.compareTo("Followers") == 0)
							result = twitter.getFollowersList(screen_name.get(), cursor);
						else
							result = twitter.getFriendsList(screen_name.get(), cursor);
					}

					for (twitter4j.User user : result) {
						String name = user.getName().replace("\'", "\'\'");
						String sc_name = user.getScreenName().replace("\'", "\'\'");
						String image = user.getProfileImageURL();

						tweetsResult = db.select_request("SELECT * FROM user WHERE id_user = " + user.getId()
								+ " AND id_request = " + id_request);

						if (!tweetsResult.next()) {
							String query = "INSERT INTO user VALUES(" + user.getId() + "," + id_request + ",'" + name
									+ "','" + sc_name + "', '" + image + "');";
							db.request(query);
						}
					}
					nb_cursor++;
				} while ((cursor = result.getNextCursor()) != 0 && nb_cursor < 5);

				return id_request;
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * Catch the 100 last likes
	 * 
	 * @throws IOException
	 */
	public int getLikes() {
		try {
			if (more)
				like_range += 20;

			// Request to Twitter
			ResponseList<Status> result = twitter.getFavorites(screen_name.get(), new Paging(1, like_range));

			int id_request = -1;
			boolean more_tweet = more;

			ResultSet tweetsResult = db.select_request("SELECT id_request as id FROM request WHERE reference = '@"
					+ screen_name.get() + "' AND req = 'likes' LIMIT 1");

			if (tweetsResult.next()) {
				id_request = tweetsResult.getInt("id");
			} else {
				id_request = db.getAutoIncRequest();
				if (result.size() != 0) {
					// Insert new collect
					String query = "INSERT INTO request VALUES(" + id_request + ", 'tweet','@" + screen_name.get()
							+ "', 'likes')";
					if (db.request(query) == -1)
						return -1;
					more = false;
				}
			}
			return getObjectTweet(result, more_tweet, id_request);
		} catch (TwitterException | SQLException | IOException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * Catch the 100 last tweets
	 * 
	 * @throws IOException
	 */
	public int startRequest() {
		try {
			// Request to Twitter
			ResponseList<Status> result = null;

			int id_request = -1;
			boolean more_tweet = more;

			ResultSet tweetsResult = db.select_request("SELECT id_request as id FROM request WHERE reference = '@"
					+ screen_name.get() + "' AND req = 'timeline' LIMIT 1");
			if (tweetsResult.next()) {
				id_request = tweetsResult.getInt("id");

				ResultSet resultat = db
						.select_request("SELECT COUNT(*) AS count FROM tweet WHERE id_request = " + id_request);
				if (resultat.next()) {
					tweet_range = resultat.getInt("count") + 20;
					result = twitter.getUserTimeline(screen_name.get(), new Paging(1, tweet_range));
				}
			} else {
				id_request = db.getAutoIncRequest();
				result = twitter.getUserTimeline(screen_name.get(), new Paging(1, tweet_range));
				if (result.size() != 0) {
					// Insert new collect
					String query = "INSERT INTO request VALUES(" + id_request + ", 'tweet','@" + screen_name.get()
							+ "', 'timeline')";
					if (db.request(query) == -1)
						return -1;
					more = false;
				}
			}
			return getObjectTweet(result, more_tweet, id_request);
		} catch (TwitterException | SQLException | IOException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * Insert into DB new Tweet Object - Print
	 * 
	 * @param result
	 *            : Tweet obtained
	 * @throws SQLException
	 * @throws IOException
	 */
	private int getObjectTweet(ResponseList<Status> result, boolean more_tweet, int id_request)
			throws SQLException, IOException {

		int taille_tot = result.size();
		int compteur = 0;
		
		for (Status status : result) {

			compteur++;
			if(compteur > taille_tot - 20){
				
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
				if (mediaEntity.length > 0) {
					URL = mediaEntity[0].getMediaURL().toString();
					String destFile[] = mediaEntity[0].getMediaURL().toString().split("/");
					String destinationFile = destFile[4];
					Media.saveMedia(URL, destinationFile);
				}
	
				if (g != null) {
					latitude = g.getLatitude();
					longitude = g.getLongitude();
				}
	
				ResultSet Result = db.select_request(
						"SELECT * FROM tweet WHERE id_tweet = " + status.getId() + " AND id_request = " + id_request);
	
				if (!Result.next()) {
					String query = "INSERT INTO tweet VALUES(" + status.getId() + "," + id_request + ",'" + name + "','"
							+ sc_name + "','" + text + "', " + retweet + ", '" + city + "', '" + country + "', " + latitude
							+ ", " + longitude + ", " + date_tweet.getTime() + ", '" + img_profile + "', '" + URL + "');";
					db.request(query);
				}
			}
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
			name = new SimpleStringProperty(user.getName());
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
