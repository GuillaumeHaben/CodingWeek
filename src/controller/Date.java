/**
 * This class is used to collect tweets posted at a specific date
 * @author The Coding Bang Fraternity
 * @version 1.0
 */

package controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.TwitterException;

public class Date extends Params {

	private Date date ;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void startRequest() throws TwitterException {
	    Query query = new Query("since:" + date + "until:" + date);
	    query.setCount(100);
	    QueryResult result = twitter.search(query);
	    for (Status status : result.getTweets()) {
	        System.out.println("\n@" + status.getUser().getScreenName() + ":" + status.getText());
	    }
	}
	
}
