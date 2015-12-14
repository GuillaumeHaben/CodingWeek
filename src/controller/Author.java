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
	
	public Author(String name, Twitter twitter) {
		super();
		this.twitter = twitter;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	public void startRequest() throws TwitterException {
	    //Méthode 1
		Query query = new Query();
	    query.setSinceId(name); // réclame un long et non un String
	    QueryResult result = twitter.search(query);
	    for (Status status : result.getTweets()) {
	        System.out.println("\n@" + status.getUser().getScreenName() + ":" + status.getText());
	    }
	    
	    //Méthode 2
	    //Query query = new Query("from:" + name);
	    //QueryResult result = twitter.search(query);
	    //for (Status status : result.getTweets()) {
	    //    System.out.println("\n@" + status.getUser().getScreenName() + ":" + status.getText());
	    //}
	}
	*/
	
	public void startRequest() {
		Query query = new Query("from:" + name);
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
