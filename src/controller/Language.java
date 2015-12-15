/**
 * This class is used to collect tweets in a specific language 
 * @author The Coding Bang Fraternity
 * @version 1.0
 */

package controller;

import twitter4j.Query;
import twitter4j.QueryResult;
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
		super();
		this.twitter = twitter;
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
		Query query = new Query(keyword);
		query.setLang(language);
		QueryResult result;
		try {
			result = twitter.search(query);
			for (Status status : result.getTweets()) {
				System.out.println("\n@" + status.getUser().getScreenName() + ":" + status.getText() + " language : " + status.getLang());
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		}

    }
}
