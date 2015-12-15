/**
 * This class is used to collect tweets posted in a specific location
 * @author The Coding Bang Fraternity
 * @version 2.0
 */

package controller;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class Location extends Params {

	private String city;
	private String country;
	
	public Location (String city, String country, Twitter twitter) {
		super(twitter);
		this.city = city;
		this.country = country;
	}
	
	public String getCity() {
		return city;
	}
	
	public String getCountry() {
		return country;
	}

	public void startRequest() throws TwitterException {
		return;
	}

	public void logConsole(Status status) {
		System.out.println("\n@" + status.getUser().getScreenName() + " : " + status.getText());	
	}	
}
