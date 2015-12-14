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

	private String language ;
	
	public Language(String language, Twitter twitter) {
		super();
		this.twitter = twitter;
		this.language = language;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	public void startRequest() throws TwitterException {
		Query query = new Query();
	    query.setLang(language);
	    QueryResult result = twitter.search(query);
	    for (Status status : result.getTweets()) {
	        System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
	    }
	}
	*/
	
	public void startRequest() {
		Query query = new Query("keyword");
		query.setLang(language);
		QueryResult result;
		try {
			result = twitter.search(query);
			for (Status status : result.getTweets()) {
				System.out.println("\n@" + status.getUser().getScreenName() + ":" + status.getText());
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		}

	}
	
}
