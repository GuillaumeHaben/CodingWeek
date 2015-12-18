/**
 * This class is used to collect tweets posted with specifics params
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

public class Multiparams extends Params {

	private String keyword;
	private String language = "";
	private String date = "";
	
	/**
	 * Constructor
	 * @param keyword : Keyword searched
	 * @param language : Language of the Tweeter
	 * @param date : date of the tweet
	 * @param screen_name : author of the tweet
	 * @param twitter : Twitter object
	 */
	public Multiparams(String keyword, String language, String date, Twitter twitter) {
		super(twitter);
		this.keyword = keyword;
		this.language = language;
		this.date = date;
	}

	public String getLanguage() {
		return language;
	}

	
	public String getKeyword() {
		return keyword;
	}
	
	public String getDate() {
		return date;
	}
	
	/**
	 *  Get Tweets from a keyword, a language, a date, an author
	 */
	public int startRequest() {
		try {
			ResultSet tweetsResult = db.select_request("SELECT id_request as id FROM request WHERE reference = '" 
					+ keyword + "' AND req = 'params' LIMIT 1");
			int id_request = -1;
			
			if (tweetsResult.next()) {
				id_request = tweetsResult.getInt("id");
			} else {
				id_request = db.getAutoIncRequest();
				String qu = "INSERT INTO request VALUES(" + id_request + ", 'user','" + keyword + "', 'params')";
				
				if (db.request(qu) == -1)
					return -1;
			}		
			
			Query query = new Query(keyword);
			if (language != "")
				query.setLang(language);
			
			if (date != "")
				query.setSince(date);
			
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
