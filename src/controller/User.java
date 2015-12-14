/**
 * This class collects all the informations about a specific user
 * @author The Coding Bang Fraternity
 * @version 1.0
 */

package controller;

import com.mysql.jdbc.DatabaseMetaData;
import sun.applet.Main;

import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class User implements Collect {

	private String name;
	private String screen_name;
	private int followers_count;
	private int friends_count;
	private int statuses_count;
	private Twitter twitter;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            : User's name
	 */
	public User(String name, Twitter twitter) {
		this.name = name;
		this.twitter = twitter;
	}

	public void getFollowers() {

	}

	public void getFollowing() {

	}

	public void getLikes() {

	}

	/**
	 * Catch all the tweets
	 */
	public void startRequest() {
		try {
			ResponseList<Status> result = twitter.getUserTimeline(name, new Paging(1, 200));

			// Init a DB connection
			Database db = new Database();

			for (Status status : result) {
				System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
				// String query = "INSERT INTO Tweet("

			}
		} catch (TwitterException e) {
			System.out.println("L'utilisateur spécifié est introuvable !");
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

	public int getFollowers_count() {
		return followers_count;
	}

	public int getFriends_count() {
		return friends_count;
	}

	public int getStatuses_count() {
		return statuses_count;
	}

}
