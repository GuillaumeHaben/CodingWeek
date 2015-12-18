/**
 * This abstract class handle params queries
 * @author The Coding Bang Fraternity
 * @version 5.0
 */

package model;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import defaultpackage.Main;
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
	public abstract int startRequest() throws TwitterException;
	public abstract void logConsole(Status status);

	public Params(Twitter twitter){
		this.db = new Database();
		this.twitter = twitter;
	}
	
	
	/**
	 * Insert into DB new Tweet Object
	 * @param result : obtained tweet
	 * @return id_request
	 * @throws SQLException
	 */
	protected int getObjectTweet(QueryResult result, int id_request) throws SQLException {
		int taille_tot = result.getCount();
		int compteur = 0;

		for (Status status : result.getTweets()) {
			
			if (Main.isConsole()) {
				logConsole(status);
			}
			
			compteur++;
			if(compteur > taille_tot - 20){

				// Fetch all the available informations
				String text = status.getText().replace("\'", "\'\'");
				String name = status.getUser().getName().replace("\'", "\'\'");
				String sc_name = status.getUser().getScreenName().replace("\'", "\'\'");
		
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
				
				String img_profile = status.getUser().getProfileImageURL();
				String URL = "";
				MediaEntity[] mediaEntity = status.getMediaEntities();
				if (mediaEntity.length > 0) {
					URL = mediaEntity[0].getMediaURL().toString();
					String destFile[] = mediaEntity[0].getMediaURL().toString().split("/");
					String destinationFile = destFile[4];
					try {
						Media.saveMedia(URL, destinationFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
		
				if (g != null) {
					latitude = g.getLatitude();
					longitude = g.getLongitude();
				}
				
				ResultSet Result = db.select_request(
						"SELECT * FROM tweet WHERE id_tweet = " + status.getId() + " AND id_request = " + id_request);
	
				if (!Result.next()) {
					String query = "INSERT INTO tweet VALUES(" + status.getId() + "," + id_request + ",'" + name + "','"
							+ sc_name + "','" + text + "', " + retweet + ", '" + city + "', '" + country + "', " + latitude
							+ ", " + longitude + ", " + date_tweet.getTime() + ", '" + img_profile + "', '" + URL + "');";
					db.request(query);
				}
			}
		}
		db.close();
		return id_request;
	}
}
