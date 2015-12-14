/**
 * This class is used to collect tweets in a specific language 
 * @author The Coding Bang Fraternity
 * @version 1.0
 */

package controller;

import twitter4j.TwitterException;

public class Language extends Params {

	private String language ;

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Override
	public void startRequest() throws TwitterException {
		// TODO Auto-generated method stub
		
	}
	
}
