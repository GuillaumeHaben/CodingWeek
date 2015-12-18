/**
 * This abstract class handle params queries
 * @author The Coding Bang Fraternity
 * @version 5.0
 */

package model;

import java.sql.SQLException;

import controller.Collect;
import twitter4j.GeoLocation;
import twitter4j.MediaEntity;
import twitter4j.Place;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public abstract class Params implements Collect {

	protected Twitter twitter;
	protected Database db;
	public abstract void startRequest() throws TwitterException;
	public abstract void logConsole(Status status);

	public Params(Twitter twitter){
		this.db = new Database();
		this.twitter = twitter;
	}
	
	/**
	 * Insert into DB new Tweet Object - Print 
	 * @param result : Tweet obtenus
	 * @throws SQLException
	 */
	protected void getObjectTweet(QueryResult result) throws SQLException {
		int id_request = db.getAutoIncRequest();

		for (Status status : result.getTweets()) {

			// Fetch all the available informations
			String text = status.getText().replace("\'", "\\'");
			String name = status.getUser().getName().replace("\'", "\\'");
			String sc_name = status.getUser().getScreenName().replace("\'", "\\'");

			java.util.Date date_tweet = status.getCreatedAt();

			int retweet = status.getRetweetCount();
			Place p = status.getPlace();
			GeoLocation g = status.getGeoLocation();

			String city = null, country = null;
			double latitude = 0, longitude = 0;
			if (p != null) {
				city = p.getName();
				country = p.getCountry();
			}

			if (g != null) {
				latitude = g.getLatitude();
				longitude = g.getLongitude();
			}
			
			String URL = "";
			MediaEntity[] mediaEntity = status.getMediaEntities();
			if(mediaEntity.length > 0){
				URL = mediaEntity[0].toString();
			}
			
			String query = "INSERT INTO tweet VALUES(" + status.getId() + "," + id_request + ",'" + name + "','"
					+ sc_name + "','" + text + "', " + retweet + ", '" + city + "', '" + country + "', " + latitude
					+ ", " + longitude + ", " + date_tweet.getTime() + ", '" + URL + "');";
			db.request(query);

			// Console display
			//this.logConsole(status);
		}
	}
}
