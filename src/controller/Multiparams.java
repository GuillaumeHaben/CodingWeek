package controller;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class Multiparams extends Params {

	private String language = "";
	private String keyword;
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
		super();
		this.twitter = twitter;
		this.language = language;
		this.keyword = keyword;
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
	 *  Get Tweets from a keyword, a language and a date
	 */
	public void startRequest() {
		Query query = null;
		if (keyword != "") {
			String request = keyword;
			if (screen_name != "") {
				request += " from:" + screen_name;
			}
			query = new Query(request);
			if (language != "") {
				query.setLang(language);
			}
			if (date != "") {
				query.setSince(date);
			}
			QueryResult result;
			try {
				result = twitter.search(query);
				for (Status status : result.getTweets()) {
					System.out.println("\n@" + status.getUser().getScreenName() + ":" + status.getText() + " language : " + status.getLang());
				}
			} catch (TwitterException e) {
				e.printStackTrace();
			}
		} else {
			return;
		}
    }
}
