/**
 * This class collects all the informations about a specific user
 * @author The Coding Bang Fraternity
 * @version 2.0
 */

package controller;

import twitter4j.*;
import java.sql.SQLException;

import model.Collect;
import model.Database;

public class User implements Collect {

	private long id;
	private String name; // Real Name
	private String screen_name; // Pseudo
	private String description;
	private int followers_count;
	private int friends_count;
	private int statuses_count;

	private boolean upd_profile;
	private Twitter twitter;
	private Database db;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            : User's name
	 * @param twitter
	 *            : Object Twitter
	 */
	public User(String name, Twitter twitter) {
		this.screen_name = name.replace("'", "\'"); // Escape caracter '
		this.twitter = twitter;
		this.upd_profile = false; // Prevent to sync twice the profile
		db = new Database();
	}

	/**
	 * Catch all the followers / Following
	 * 
	 * @param follow
	 *            : "Followers" OR "Following"
	 */
	public void get(String follow) {
		try {
			int id_request = db.getAutoIncRequest();

			// Insert new collect
			String query = "INSERT INTO request(type, reference) VALUES('user','@" + screen_name + "')";
			db.request(query);

			// Init attributes
			long cursor = -1;
			PagableResponseList<twitter4j.User> result;

			if (follow.compareTo("Followers") == 0)
				System.out.println("Followers of " + screen_name + " :");
			else
				System.out.println("Following of " + screen_name + " :");

			do {
				if (follow.compareTo("Followers") == 0)
					result = twitter.getFollowersList(screen_name, cursor);
				else
					result = twitter.getFriendsList(screen_name, cursor);

				for (twitter4j.User user : result) {
					String name = user.getName().replace("\'", "\\'");
					String sc_name = user.getScreenName().replace("\'", "\\'");
					System.out.print("\n" + user.getName());
					
					query = "INSERT INTO user VALUES(" + user.getId() + "," + id_request + ",'" + name + "','"
						+ sc_name + "');";
					db.request(query);
				}

			} while ((cursor = result.getNextCursor()) != 0);
		} catch (TwitterException | SQLException e) {
			System.out.println("The user doesn't exist.. :'(");
		}
	}

	/**
	 * Catch the 100 last likes
	 */
	public void getLikes() {
		try {
			// Insert new collect
			String query = "INSERT INTO request(type, reference) VALUES('tweet','@" + screen_name + "')";
			db.request(query);

			// Request to Twitter
			ResponseList<Status> result = twitter.getFavorites(screen_name, new Paging(1, 100));

			// See tweets liked
			System.out.println("Likes from @" + screen_name + "\n");

			getObjectTweet(result);

		} catch (TwitterException | SQLException e) {
			System.out.println("The user doesn't exist.. :'(");
		}
	}

	/**
	 * Catch the 100 last tweets
	 */
	public void startRequest() {
		try {
			// Insert new collect
			String query = "INSERT INTO request(type, reference) VALUES('tweet','@" + screen_name + "')";
			db.request(query);

			// Request to Twitter
			ResponseList<Status> result = twitter.getUserTimeline(screen_name, new Paging(1, 100));

			// See last tweets
			System.out.println("Last 100 tweets of " + screen_name + ": \n");

			getObjectTweet(result);

		} catch (TwitterException | SQLException e) {
			System.out.println("The user doesn't exist.. :'(");
		}
	}

	
	/**
	 * Insert into DB new Tweet Object - Print 
	 * @param result : Tweet obtenus
	 * @throws SQLException
	 */
	@SuppressWarnings("deprecation")
	private void getObjectTweet(ResponseList<Status> result) throws SQLException {
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

			// Console log
			System.out.println(status.getUser().getScreenName() + " : " + status.getText());
		}
	}

	/**
	 * Save the profiles informations of the current user
	 */
	public void getInformation() {
		try {
			if (!upd_profile) {
				twitter4j.User user = twitter.showUser(screen_name);

				name = user.getName();
				description = user.getDescription();
				followers_count = user.getFollowersCount();
				friends_count = user.getFriendsCount();
				statuses_count = user.getStatusesCount();
				id = user.getId();
			}

			System.out.println("\nInformations : ");
			System.out.println("Name : " + name + "\nDescription : " + description + "\nNumber of followers : " + followers_count
							+ "\nNumber of followings : " + friends_count + "\nNumber of tweets : " + statuses_count);

		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Getter for all the attribute
	 */
	public String getName() {
		return name;
	}

	public String getScreen_name() {
		return screen_name;
	}

	public String getDescription() {
		return description;
	}

	public int getFollowers_count() {
		return followers_count;
	}

	public int getFriends_count() {
		return friends_count;
	}

	public int getStatuses_count() {
		return statuses_count;
	}

	public long getId() {
		return id;
	}
}
