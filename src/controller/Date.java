/**
 * This class is used to collect tweets posted at a specific date
 * @author The Coding Bang Fraternity
 * @version 1.0
 */

package controller;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.TwitterException;

public class Date extends Params {

    private Date date;

    public Date getDate() {
	return date;
    }

    public void setDate(Date date) {
	this.date = date;
    }

    @Override
    public void startRequest() throws TwitterException {
	// TODO Auto-generated method stub

	Query query = new Query(date);
	query.setCount(100);
	QueryResult result = twitter.search(query);
	for (Status status : result.getTweets()) {
	    System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
	}

    }

}
