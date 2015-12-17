package controller;

import java.sql.SQLException;

import twitter4j.MediaEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class Media extends Params {

	private String keyword;
	private long mediaId;
	private String mediaURL;
	private String mediaType;
	private Twitter twitter;
	
	public String getKeyword() {
		return keyword;
	}

	public void getKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public long getMediaId() {
		return mediaId;
	}

	public void setMediaId(long mediaId) {
		this.mediaId = mediaId;
	}

	public String getMediaURL() {
		return mediaURL;
	}

	public void setMediaURL(String mediaURL) {
		this.mediaURL = mediaURL;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	
	public Twitter getTwitter() {
		return twitter;
	}
	
	public void setTwitter(Twitter twitter) {
		this.twitter = twitter;
	}
	
	public Media(String keyword, Twitter twitter) {
		super(twitter);
		this.keyword = keyword;
	}

	public void startRequest() throws TwitterException {
		//String q = "INSERT INTO request(type, reference) VALUES('user','" + keyword + "')";
		//db.request(q);
		Query query = new Query(keyword);
		QueryResult result = twitter.search(query);
		try {
			getObjectTweet(twitter.search(query));
			for (Status status : result.getTweets()) {
				MediaEntity[] mediaEntity = status.getMediaEntities();
				for (int i = 0; i < mediaEntity.length; i++) {
					System.out.println("\n@" + status.getUser().getScreenName() + ": " + status.getText()
							+ "\n MediaType : " + mediaEntity[i].getType() + "\n MediaId : " + mediaEntity[i].getId()
							+ "\n MediaURL : " + mediaEntity[i].getMediaURL());

				}
			}

		} catch (TwitterException | SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void logConsole(Status status) {
		// TODO Auto-generated method stub
		
	}

	
}
