package controller;

import java.sql.SQLException;

import twitter4j.Query;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class Multiparams extends Params {

	private String keyword;
	private String language = "";
	private String date = "";
	private String screen_name = "";
	
	/**
	 * Constructor
	 * @param keyword : Keyword searched
	 * @param language : Language of the Tweeter
	 * @param date : date of the tweet
	 * @param screen_name : author of the tweet
	 * @param twitter : Twitter object
	 */
	public Multiparams(String keyword, String language, String date, String screen_name, Twitter twitter) {
		super(twitter);
		this.keyword = keyword;
		this.language = language;
		this.date = date;
		this.screen_name = screen_name;
	}

	public String getLanguage() {
		return language;
	}

	
	public String getKeyword() {
		return keyword;
	}
	
	public String getDate() {
		return date;
	}
	
	public String getScreen_Name() {
		return screen_name;
	}

	/**
	 *  Get Tweets from a keyword, a language, a date, an author
	 */
	public void startRequest() {
		Query query = null;
		assert keyword != "";
		
		if (keyword != "") {
			String request = keyword;
			if (screen_name != "")
				request += " from:" + screen_name;
			
			query = new Query(request);
			if (language != "")
				query.setLang(language);
			
			if (date != "")
				query.setSince(date);
			
			try {
				getObjectTweet(twitter.search(query));
				
			} catch (TwitterException | SQLException e) {
				e.printStackTrace();
			}
		} else return;
    }

	public void logConsole(Status status) {
		System.out.println("\n@" + status.getUser().getScreenName() + " : " + status.getText());		
	}
}
