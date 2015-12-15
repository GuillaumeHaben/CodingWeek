/**
 * This class is used to collect tweets posted in a specific location
 * @author The Coding Bang Fraternity
 * @version 0.0
 */

package controller;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class Location extends Params {

	private String city;
	private String country;
	
	public Location (String city, String country, Twitter twitter) {
		super();
		this.twitter = twitter;
		this.city = city;
		this.country = country;
	}
	
	public String getCity() {
		return city;
	}
	
	public String getCountry() {
		return country;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}

	public void startRequest() throws TwitterException {
		Query query = new Query();
		
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
