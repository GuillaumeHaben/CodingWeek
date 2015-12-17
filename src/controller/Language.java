/**
 * This class is used to collect tweets in a specific language 
 * @author The Coding Bang Fraternity
 * @version 2.0
 */

package controller;

import java.sql.SQLException;

import model.Params;
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
	public void startRequest() {
		String q = "INSERT INTO request(type, reference) VALUES('user','" + keyword + "," + language + "')";
		db.request(q);
		
		Query query = new Query(keyword);
		query.setLang(language);
		
		try {
			getObjectTweet(twitter.search(query));
			
		} catch (TwitterException | SQLException e) {
			e.printStackTrace();
		}
    }
	
	public void logConsole(Status status) {
		System.out.println("\n@" + status.getUser().getScreenName() + " : " + status.getText() + ", language : " + status.getLang());
	}
}
