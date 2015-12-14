/**
 * This class collects all the informations about a specific user
 * @author The Coding Bang Fraternity
 * @version 1.0
 */

package controller;

import java.io.UnsupportedEncodingException;
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
	 * 
	 * @param name : User's name
	 * @param twitter : Object Twitter
	 */
	public User(String name, Twitter twitter) {
		this.name = name;
		this.twitter = twitter;
	}

	/**
	 * Get likes from a user
	 * @param id_request : ID for the request
	 */
	public void getLikes() {
		try {
			ResponseList<Status> result = twitter.getFavorites(name, new Paging(1, 100));
			
			// Init a DB connection
			Database db = new Database();

			// Insert new collect
			String query = "INSERT INTO request(type, reference) VALUES('tweet','@"
					+ name.replaceAll("'", "\'") + "')";
			db.request(query);
			
			// Get id_of the request
			int id_request = db.autoIncRequest();	
			
			// See tweets liked
			System.out.println("Likes from @" + name + "\n");
			for (Status status : result) {
				
				String text = "";
				String sc_name = "";
				String name = "";
				try {
					text = status.getText().replace("\'", "\\'");
					sc_name = new String(status.getUser().getScreenName().replaceAll("\'", "\\'").getBytes(),"UTF-8");
					name = status.getUser().getName().replaceAll("\'", "\\'");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}				

				// Console
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

			// Insert new collect
			String query = "INSERT INTO request(type, reference) VALUES('user','@"
					+ name.replaceAll("'", "\'") + "')";
			db.request(query);
			
			// Get id_of the request
			int id_request = db.autoIncRequest();	

			// Init attributes
			long cursor = -1;
			PagableResponseList<twitter4j.User> result;
			
			if(follow.compareTo("Followers")==0)
				System.out.println("Followers of " + name + " :\n");
			else 
				System.out.println("Following of " + name + " :\n");
			
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
	 * Catch the 200 first tweets
	 */
	public void startRequest() {
		try {
			ResponseList<Status> result = twitter.getUserTimeline(name, new Paging(1, 200));

			// Init a DB connection
			Database db = new Database();

			// Insert new collect
			String query = "INSERT INTO request(type, reference) VALUES('tweet','@ " 
					+ name.replaceAll("'", "\'") + "')";
			db.request(query);
			
			// Get id_of the request
			int id_request = db.autoIncRequest();	

			// See last tweets
			System.out.println("Last tweets : \n");
			for (Status status : result) {
				String text = status.getText().replace("\'", "\\'");
				String sc_name = status.getUser().getScreenName().replaceAll("\'", "\\'");
				String name = status.getUser().getName().replaceAll("\'", "\\'");

				query = "INSERT INTO tweet VALUES(" + status.getId() + "," + id_request + ",'" + name + "','" + sc_name
						+ "','" + text + "');";

				db.request(query);
				
				// Console
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
