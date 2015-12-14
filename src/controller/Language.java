/**
 * This class is used to collect tweets in a specific language 
 * @author The Coding Bang Fraternity
 * @version 1.0
 */

package controller;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.TwitterException;

public class Language extends Params {

    private String language;

    public String getLanguage() {
	return language;
    }

    public void setLanguage(String language) {
	this.language = language;
    }

    @Override
    public void startRequest() throws TwitterException {
	// TODO Auto-generated method stub

	Query query = new Query(language);
	query.setCount(100);
	query.setLang(language);
	QueryResult result = twitter.search(query);
	for (Status status : result.getTweets()) {
	    System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText() + "\n");
	}
    }

}
