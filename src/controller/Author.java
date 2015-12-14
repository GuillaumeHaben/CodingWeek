/**
 * This class is used to collect tweets of a specific name
 * @author The Coding Bang Fraternity
 * @version 1.0
 */

package controller;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.TwitterException;

public class Author extends Params {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void startRequest() throws TwitterException {
	    Query query = new Query(name);
	    QueryResult result = twitter.search(query);
	    for (Status status : result.getTweets()) {
	        System.out.println("\n@" + status.getUser().getScreenName() + ":" + status.getText());
	    }
	}
	
}
