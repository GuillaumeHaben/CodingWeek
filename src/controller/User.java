/**
 * This class collects all the informations about a specific user
 * @author The Coding Bang Fraternity
 * @version 2.0
 */

package controller;

import java.sql.SQLException;

import twitter4j.PagableResponseList;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class User implements Collect {

	private String name;
	private String screen_name;
	private int followers_count;
	private int friends_count;
	private int statuses_count;
	private Twitter twitter;

	/**
	 * Constructor
	 * @param name : User's name
	 * @param twitter : Object Twitter
	 */
	public User(String name, Twitter twitter) {
		this.name = name;
		this.twitter = twitter;
	}

	/**
	 * Catch the 100 last likes
	 * @param id_request : ID for the request
	 */
	public void getLikes() {
		try {
			// Init a DB connection
			Database db = new Database();
			int id_request = db.autoIncRequest();	
			
			// Insert new collect
			String query = "INSERT INTO request(type, reference) VALUES('tweet','@" + name.replaceAll("'", "\'") + "')";
			db.request(query);
			
			// Request to Twitter
			ResponseList<Status> result = twitter.getFavorites(name, new Paging(1, 100));
			
			// See tweets liked
			System.out.println("Likes from @" + name + "\n");
			for (Status status : result) {
				
				String text = status.getText().replace("\'", "\\'");
				String sc_name = status.getUser().getScreenName().replace("\'", "\\'");
				String name = status.getUser().getName().replace("\'", "\\'");

				// Console log
				System.out.println(sc_name + " : " + text);
				
				query = "INSERT INTO tweet VALUES(" + status.getId()
						+ "," + id_request + ",'" + name + "','" + sc_name
						+ "','" + text + "');";
				
				db.request(query);	
			}
		} catch (TwitterException e) {
			System.out.println("The user doesn't exist :'(");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Catch all the followers / Following
	 * @param follow : "Followers" OR "Following"
	 */
	public void get(String follow) {
		try {
			// Init a DB connection
			Database db = new Database();
			int id_request = db.autoIncRequest();
			
			// Insert new collect
			String query = "INSERT INTO request(type, reference) VALUES('user','@"
					+ name.replaceAll("'", "\'") + "')";
			db.request(query);				

			// Init attributes
			long cursor = -1;
			PagableResponseList<twitter4j.User> result;
			
			if(follow.compareTo("Followers")==0)
				System.out.println("Followers of " + name + " :");
			else 
				System.out.println("Following of " + name + " :");
			
			do {
				if(follow.compareTo("Followers")==0)
					result = twitter.getFollowersList(name, cursor);
				else 
					result = twitter.getFriendsList(name, cursor);
				
				for (twitter4j.User status : result) {
					String name = status.getScreenName().replaceAll("\'", "\\'");
					System.out.print("\n-" + name);
				}
				
			} while((cursor = result.getNextCursor()) != 0);
		} catch (TwitterException | SQLException e) {
			System.out.println("The user doesn't exist.. :'(");
		}
	}

	/**
	 * Catch the 100 first tweets
	 */
	public void startRequest() {
		try {
			// Init a DB connection
			Database db = new Database();
			int id_request = db.autoIncRequest();	

			// Insert new collect
			String query = "INSERT INTO request(type, reference) VALUES('tweet','@" 
					+ name.replaceAll("'", "\'") + "')";
			db.request(query);			

			// Request to Twitter
			ResponseList<Status> result = twitter.getUserTimeline(name, new Paging(1, 100));

			// See last tweets
			System.out.println("Last tweets : \n");
			for (Status status : result) {
				String text = status.getText().replace("\'", "\\'");
				String sc_name = status.getUser().getScreenName().replace("\'", "\\'");
				String name = status.getUser().getName().replace("\'", "\\'");

				query = "INSERT INTO tweet VALUES(" + status.getId() + "," + id_request + ",'" + name + "','" + sc_name
						+ "','" + text + "');";
				
				db.request(query);
				
				// Console log
				System.out.println(sc_name + " : " + text);
			}
		} catch (TwitterException | SQLException e) {
			System.out.println("The user doesn't exist.. :'(");
		}
	}

	/**
	 * Getter for all the attribute
	 */
	public String getName() {
		return name;
	}

	public String getScreen_name() {
		return screen_name;
	}

	public int getFollowers_count() {
		return followers_count;
	}

	public int getFriends_count() {
		return friends_count;
	}

	public int getStatuses_count() {
		return statuses_count;
	}
}
