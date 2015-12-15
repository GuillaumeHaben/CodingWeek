/**
 * This class collects all the informations about a specific user
 * @author The Coding Bang Fraternity
 * @version 1.0
 */

package controller;

import twitter4j.*;

public class User implements Collect {

	private String name;
	private String screen_name;
	private String description;
	private int followers_count;
	private int friends_count;
	private int statuses_count;
	private Twitter twitter;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            : User's name
	 * @param twitter
	 *            : Object Twitter
	 */
	public User(String name, Twitter twitter) {
		this.name = name;
		this.twitter = twitter;
	}

	public void getLikes() {
		try {

			ResponseList<Status> result = twitter.getFavorites();

			/*
			 * long cursor = -1; ResponseList<User> pagableLikes;
			 * 
			 * do { pagableLikes = twitter.getFavorites(name, cursor); for (User
			 * user : pagableLikes) { listFriends.add(user); // ArrayList<User>
			 * } } while ((cursor = pagableLikes.getNextCursor()) != 0);
			 */

			for (Status status : result) {

				String text = status.getText().replace("\'", "\\'");
				String sc_name = status.getUser().getScreenName().replaceAll("\'", "\\'");
				String name = status.getUser().getName().replaceAll("\'", "\\'");

				System.out.println(result);
				/*
				 * query = "INSERT INTO tweet VALUES(" + name + "','" + sc_name
				 * + "','" + text + "');"; db.request(query);
				 */
				// System.out.println("getLikes: Done inserting");

			}
		} catch (TwitterException e) {
			System.out.println("The user doesn't exist :'(");
		}
	}

	/**
	 * Catch all the followers / Following
	 * @param follow : "Followers" OU "Following"
	 */
	public void get(String follow) {
		try {
			long cursor = -1;
			PagableResponseList<twitter4j.User> result;
			
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

	    // Init a DB connection
	    Database db = new Database();
	    String query = "INSERT INTO request(type, reference) VALUES('UTW','" + name.replaceAll("'", "\'") + "')";
	    db.request(query);

		} catch (TwitterException e) {
			System.out.println("The user doesn't exist.. :'(");
		}
	}

	/**
	 * Catch the 200 first tweets
	 */
	public void startRequest(int id_request) {
		try {
			ResponseList<Status> result = twitter.getUserTimeline(name, new Paging(1, 200));

			// Init a DB connection
			Database db = new Database();

			// Insert new collect
			String query = "INSERT INTO request(type, reference) VALUES('tweet','@ " + name.replaceAll("'", "\'")
					+ "')";
			db.request(query);

			// See tweets
			for (Status status : result) {
				String text = status.getText().replace("\'", "\\'");
				String sc_name = status.getUser().getScreenName().replaceAll("\'", "\\'");
				String name = status.getUser().getName().replaceAll("\'", "\\'");

				query = "INSERT INTO tweet VALUES(" + status.getId() + "," + id_request + ",'" + name + "','" + sc_name
						+ "','" + text + "');";

				db.request(query);
				System.out.println(sc_name + " : " + text);
			}

			System.out.println("StartRequest: Done inserting");

		} catch (TwitterException e) {
			System.out.println("The user doesn't exist.. :'(");
		}
	}
	
	/**
	public void getInformation(String name, Twitter twitter) {
		Query query = new Query("from:" + name);
		QueryResult result;
		try {
			result = twitter.searchUsers(query);
			for (User user : result.showUser()) {
				System.out.println("\n Screen name : " + user.getScreen_name() + ", Description : " + user.getDescription() + ", Number of followers : " + user.getFollowers_count() + ", Number of followings : " + user.getFriends_count() + ", Number of tweets : " + user.getStatuses_count());
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}
	*/
	
	public void getInformation(String screen_name, Twitter twitter) {
		try {
			User user = showUser(screen_name);
			System.out.println("\n Screen name : " + user.getScreen_name() + ", Description : " + user.getDescription() + ", Number of followers : " + user.getFollowers_count() + ", Number of followings : " + user.getFriends_count() + ", Number of tweets : " + user.getStatuses_count());
		} catch (TwitterException e) {
			e.printStackTrace();
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
		
	public String getDescription() {
		return description;
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
