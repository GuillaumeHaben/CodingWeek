/**
 * This class is used to collect tweets with a specific keyword
 * @author The Coding Fang Fraternity
 * @version 1.0
 */

package controller;

import twitter4j.Twitter;

public class KeyWord extends Params {

	private String keyword ;
	private Twitter twitter ;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
}
