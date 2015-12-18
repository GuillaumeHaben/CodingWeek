/**
 * This class is used to collect tweets in a specific language 
 * @author The Coding Bang Fraternity
 * @version 2.0
 */

package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import twitter4j.Query;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class Language extends Params {

	private String language;
	private String keyword;
	
	/**
	 * Constructor
	 * @param keyword : Keyword searched
	 * @param language : Language of the Tweeter
	 * @param twitter : Twitter object
	 */
	public Language(String keyword, String language, Twitter twitter) {
		super(twitter);
		this.language = language;
		this.keyword = keyword;
	}

	public String getLanguage() {
		return language;
	}

	
	public String getKeyword() {
		return keyword;
	}

	/**
	 *  Get Tweets from a keyword and a language
	 */
	public int startRequest() {
		try {
			ResultSet tweetsResult = db.select_request("SELECT id_request as id FROM request WHERE reference = '" 
					+ keyword + "' AND req = 'date' LIMIT 1");
			int id_request = -1;
			
			if (tweetsResult.next()) {
				id_request = tweetsResult.getInt("id");
			} else {
				id_request = db.getAutoIncRequest();
				String qu = "INSERT INTO request VALUES(" + id_request + ", 'tweet','" + keyword
						+ "', 'date')";
				
				if (db.request(qu) == -1)
					return -1;
			}		
			
			Query query = new Query(keyword);
			query.setLang(language);
			
			getObjectTweet(twitter.search(query), id_request);
			return id_request;

		} catch (TwitterException | SQLException e) {
			e.printStackTrace();
			return -1;
		}
    }
	
	public void logConsole(Status status) {
		System.out.println("\n@" + status.getUser().getScreenName() + " : " + status.getText() + ", language : " + status.getLang());
	}
}
