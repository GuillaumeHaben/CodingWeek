/**
 * This class is used to collect tweets posted in a specific location
 * @author The Coding Bang Fraternity
 * @version 1.0
 */

package controller;

import twitter4j.TwitterException;

public class Location extends Params {

	private String location ;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public void startRequest() throws TwitterException {
		// TODO Auto-generated method stub
		
	}
	
}
