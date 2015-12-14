/**
 * This class collects all the informations about a specific user
 * @author The Coding Bang Fraternity
 * @version 1.0
 */

package controller;

import sun.applet.Main;
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

	public void getTweets() {
		Query query = new Query("de : Kilian_cuny");

		try {
			QueryResult result = twitter.search(query);
			for (Status status : result.getTweets()) {
				System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	public void getLikes() {

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

	@Override
	public void startRequest() throws TwitterException {
		// TODO Auto-generated method stub
		
	}
	
	public int getStatuses_count() {
		return statuses_count;
	}

}
