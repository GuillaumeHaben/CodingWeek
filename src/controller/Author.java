/**
 * This class is used to collect tweets of a specific name
 * @author The Coding Bang Fraternity
 * @version 1.0
 */

package controller;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class Author extends Params {

	private String name;
	
	/**
	 * Constructor
	 * @param name : Name of the tweet's author
	 * @param twitter : Twitter object
	 */
	public Author(String name, Twitter twitter) {
		super();
		this.twitter = twitter;
		this.name = name;
	}

	/**
	 * Get Tweets from an author
	 */
	// TO-DO get more tweets
	public void startRequest() {
		Query query = new Query("from:" + name);
		QueryResult result;
		try {
			result = twitter.search(query);
			for (Status status : result.getTweets()) {
				System.out.println("\n@" + status.getUser().getScreenName() + " : " + status.getText());
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}
}
