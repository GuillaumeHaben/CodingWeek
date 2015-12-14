/**
 * This class is used to collect tweets of a specific name
 * @author The Coding Bang Fraternity
 * @version 1.0
 */

package controller;

import twitter4j.TwitterException;

public class Author extends Params {

	private String name ;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void startRequest() throws TwitterException {
		// TODO Auto-generated method stub
		
	}
	
}
