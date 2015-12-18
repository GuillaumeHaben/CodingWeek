/**
 * This class is used to collect tweets with a specific keyword
 * @author The Coding Bang Fraternity
 * @version 5.0
 */

package model;

import java.sql.SQLException;

import twitter4j.*;

public class KeyWord extends Params {

	private String keyword;

	/**
	 * Constructor
	 * @param keyword : Keyword searched
	 * @param twitter : Twitter object
	 */
	public KeyWord(String keyword, Twitter twitter) {
		super(twitter);
		this.keyword = keyword;
	}

	public String getKeyword() {
		return keyword;
	}

	/**
	 * Get Tweets from a keyword
	 */
	public void startRequest() {
		String q = "INSERT INTO request(type, reference) VALUES('user','" + keyword + "')";
		db.request(q);
		
		Query query = new Query(keyword);
		try {
			getObjectTweet(twitter.search(query));

		} catch (TwitterException | SQLException e) {
			e.printStackTrace();
		}
	}

	public void logConsole(Status status) {
		System.out.println("\n@" + status.getUser().getScreenName() + " : " + status.getText());
	}
}
