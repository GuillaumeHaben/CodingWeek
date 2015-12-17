/**
 * This class is used to collect tweets posted at a specific date
 * @author The Coding Bang Fraternity
 * @version 2.0
 */

package controller;

import java.sql.SQLException;

import model.Params;
import twitter4j.*;

public class Date extends Params {

	private String date;
	private String keyword;

	/**
	 * Constructor
	 * 
	 * @param keyword
	 *            : Keyword searched
	 * @param date
	 *            : Date of the searching
	 * @param twitter
	 *            : Twitter object
	 */
	public Date(String keyword, String date, Twitter twitter) {
		super(twitter);
		this.date = date;
		this.keyword = keyword;
	}

	public String getDate() {
		return date; // doit être sous le format YYYY-MM-DD
	}

	public String getKeyword() {
		return keyword;
	}

	public boolean dateIsValid() {
		String decomposedDate[] = date.toString().split("-");
		if (decomposedDate[0].length() != 4 || decomposedDate[1].length() != 2 || decomposedDate[2].length() != 2)
			return false;			
		else
			return true;
	}

	/**
	 * Get Tweets from a keyword and a date
	 */
	public void startRequest() {
		if (dateIsValid()) {
			String q = "INSERT INTO request(type, reference) VALUES('user','" + keyword + "," + date + "')";
			db.request(q);
			
			Query query = new Query(keyword);
			query.setSince(date);
			try {
				getObjectTweet(twitter.search(query));

			} catch (TwitterException | SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Date format is not valid !");
		}
	}

	public void logConsole(Status status) {
		System.out.println("\n@" + status.getUser().getScreenName() + " : " + status.getText() + ", date : " + status.getCreatedAt());
	}
}
