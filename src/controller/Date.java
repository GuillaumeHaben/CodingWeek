/**
 * This class is used to collect tweets posted at a specific date
 * @author The Coding Bang Fraternity
 * @version 1.0
 */

package controller;


import twitter4j.*;

public class Date extends Params {

	private String date;
	private String keyword;
	
	/**
	 * COnstructor
	 * @param keyword : Keyword searched
	 * @param date : Date of the searching
	 * @param twitter : Twitter object
	 */
	public Date(String keyword, String date, Twitter twitter) {
		super();
		this.twitter = twitter;
		this.date = date;
		this.keyword = keyword;
	}

	public String getDate() {
		return date; // doit être sous le format YYYY-MM-DD
	}

	public String getKeyword() {
		return keyword;
	}
	
	/**
	 * Get Tweets from a keyword and a date
	 */
	public void startRequest() {
		Query query = new Query(keyword);
		query.setSince(date);
		QueryResult result;
		try {
			result = twitter.search(query);
			for (Status status : result.getTweets()) {
				System.out.println("\n@" + status.getUser().getScreenName() + ":" + status.getText() + " language : " + status.getLang());
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}
}
