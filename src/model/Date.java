/**
 * This class is used to collect tweets posted at a specific date
 * @author The Coding Bang Fraternity
 * @version 2.0
 */

package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import twitter4j.*;

public class Date extends Params {

	private String date;
	private String keyword;

	/**
	 * Constructor
	 * 
	 * @param keyword : Keyword searched
	 * @param date : Date of the searching
	 * @param twitter : Twitter object
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
	public int startRequest() {
		if (dateIsValid()) {
			try {
				ResultSet tweetsResult = db.select_request("SELECT id_request as id FROM request WHERE reference = '" 
						+ keyword + "' AND req = 'date' LIMIT 1");
				int id_request = -1;
				
				if (tweetsResult.next()) {
					id_request = tweetsResult.getInt("id");
				} else {
					id_request = db.getAutoIncRequest();
					String qu = "INSERT INTO request VALUES(" + id_request + ", 'tweet','" + keyword
							+ "', 'date')";
					
					if (db.request(qu) == -1)
						return -1;
				}		
				
				Query query = new Query(keyword);
				query.setSince(date);
				
				getObjectTweet(twitter.search(query), id_request);
				return id_request;

			} catch (TwitterException | SQLException e) {
				e.printStackTrace();
				return -1;
			}
		}else return -2;
	}

	public void logConsole(Status status) {
		System.out.println("\n@" + status.getUser().getScreenName() + " : " + status.getText() + ", date : " + status.getCreatedAt());
	}
}
