/**
 * This class is used to collect tweets with a specific keyword
 * @author The Coding Bang Fraternity
 * @version 5.0
 */

package model;

import java.sql.ResultSet;
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
	public int startRequest() {
		try {
			ResultSet tweetsResult = db.select_request("SELECT id_request as id FROM request WHERE reference = '" 
					+ keyword + "' AND req = 'keyword' LIMIT 1");
			int id_request = -1;
			
			if (tweetsResult.next()) {
				id_request = tweetsResult.getInt("id");
			} else {
				id_request = db.getAutoIncRequest();
				String query = "INSERT INTO request VALUES(" + id_request + ", 'tweet','" + keyword
						+ "', 'keyword')";
				if (db.request(query) == -1)
					return -1;
			}		
			
			Query query = new Query(keyword);
			getObjectTweet(twitter.search(query), id_request);
			return id_request;

		} catch (TwitterException | SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public void logConsole(Status status) {
		System.out.println("\n@" + status.getUser().getScreenName() + " : " + status.getText());
	}
}
