/**
 * This class is used to collect tweets with a specific keyword
 * @author The Coding Bang Fraternity
 * @version 1.0
 */

package controller;

import twitter4j.*;

public class KeyWord extends Params {

	private String keyword ;
	
	public KeyWord(String keyword, Twitter twitter) {
		super();
		this.twitter = twitter;
		this.keyword = keyword;
	}
	
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public void startRequest() throws TwitterException {
	    Query query = new Query(keyword);
	    QueryResult result = twitter.search(query);
	    for (Status status : result.getTweets()) {
	        System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
	    }
	}
	
}
