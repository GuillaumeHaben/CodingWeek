/**
 * This class collects all the informations about a specific user
 * @author The Coding Fang Fraternity
 * @version 1.0
 */

package controller;

public class User implements Collect {

	private String name ;
	private String screen_name ;
	private int followers_count ;
	private int friends_count ;
	private int statuses_count ;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScreen_name() {
		return screen_name;
	}

	public void setScreen_name(String screen_name) {
		this.screen_name = screen_name;
	}

	public int getFollowers_count() {
		return followers_count;
	}

	public void setFollowers_count(int followers_count) {
		this.followers_count = followers_count;
	}

	public int getFriends_count() {
		return friends_count;
	}

	public void setFriends_count(int friends_count) {
		this.friends_count = friends_count;
	}

	public int getStatuses_count() {
		return statuses_count;
	}

	public void setStatuses_count(int statuses_count) {
		this.statuses_count = statuses_count;
	}

	/**
	 * Methods to get Followers, Following, Tweets and Likes
	 */
	
	public void getFollowers() {
		
	}
	
	public void getFollowing() {
		
	}
	
	public void getTweets() {
		
	}
	
	public void getLikes() {
		
	}
	
}
