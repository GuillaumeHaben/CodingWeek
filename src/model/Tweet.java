package model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.function.Consumer;
import java.util.logging.Level;

import com.sun.istack.internal.logging.Logger;

import twitter4j.JSONArray;
import twitter4j.JSONObject;
import twitter4j.JSONTokener;

// http://tweettracker.fulton.asu.edu/tda/TwitterDataAnalytics.pdf


public class Tweet {
	
	/*public JSONArray GetFollowers ( String username ) {
	
		// Step 1: Create the API request using the supplied username
		URL url = new URL(" https :// api . twitter . com /1.1/followers / list . json ? screen_name ="+ username +"&cursor =" + cursor ) ;
		HttpURLConnection huc = ( HttpURLConnection ) url .openConnection ();
		huc . setReadTimeout (5000);
		
		// Step 2: Sign the request using the OAuth Secret
		Consumer . sign (huc) ;
		huc . connect ();
		
		/** Step 3: If the requests have been exhausted ,
		* then wait until the quota is renewed*/
		if( huc . getResponseCode ()==429) {
			try {
				Thread . sleep ( this . GetWaitTime ("/followers / list "));
			} catch ( InterruptedException ex) {
				Logger.getLogger ( RESTApiExample . class. getName ()). log ( Level .SEVERE ,null , ex) ;
			}
		}
		
		// Step 4: Retrieve the followers list from Twitter
		bRead = new BufferedReader (new InputStreamReader ((InputStream ) huc. getContent ()));
		StringBuilder content = new StringBuilder ();
		String temp = "";
		while(( temp = bRead . readLine ())!=null) {
			content . append ( temp ) ;
		}
		try {
			JSONObject jobj = new JSONObject ( content .toString ());
		}
		
		// Step 5: Retrieve the token for the next request
		cursor = jobj . getLong (" next_cursor ") ;
		JSONArray idlist = jobj . getJSONArray (" users ");
		for ( int i=0;i< idlist . length ();i ++) {
			followers . put( idlist . getJSONObject (i)) ;
		}

		return followers ;
}
	
public JSONArray GetFriends ( String username ) {
		
		JSONArray friends = new JSONArray ();
		
		// Step 1: Create the API request using the supplied username
		URL url = new URL(" https :// api . twitter . com /1.1/friends / list . json ? screen_name ="+ username +"& cursor="+ cursor ) ;
		HttpURLConnection huc = ( HttpURLConnection ) url .openConnection ();
		huc . setReadTimeout (5000);
		
		// Step 2: Sign the request using the OAuth Secret
		Consumer . sign (huc) ;
		huc . connect ();
		
		/** Step 3: If the requests have been exhausted ,
		* then wait until the quota is renewed
		*/
		if( huc . getResponseCode ()==429) {
			try {
				Thread . sleep ( this . GetWaitTime ("/friends / list "));
			} catch ( InterruptedException ex) {
			Logger . getLogger ( RESTApiExample . class. getName ()). log ( Level .SEVERE ,null , ex) ;
			}
		}
		
		// Step 4: Retrieve the friends list from Twitter
		bRead = new BufferedReader (new InputStreamReader ((InputStream ) huc. getContent ()));
		JSONObject jobj = new JSONObject ( content . toString ());
		
		// Step 5: Retrieve the token for the next request
		cursor = jobj . getLong (" next_cursor ") ;
		JSONArray userlist = jobj . getJSONArray (" users ") ;
		for ( int i=0;i< userlist . length ();i ++) {
			friends .put ( userlist . get(i));
		}
		
		return friends ;
}

public JSONArray GetStatuses ( String username ) {

	// Step 1: Create the API request using the supplied username
	// Use ( max_id -1) to avoid getting redundant Tweets .
	url = new URL (" https :// api . twitter . com /1.1/ statuses /user_timeline . json ? screen_name =" + username +"&include_rts ="+ include_rts +"& count ="+ tweetcount +"&max_id ="+( maxid -1));
	HttpURLConnection huc = ( HttpURLConnection ) url .openConnection ();
	huc . setReadTimeout (5000);
	
	// Step 2: Sign the request using the OAuth Secret
	Consumer . sign (huc) ;

	/** Step 3: If the requests have been exhausted ,
	 * * then wait until the quota is renewed */

	// Step 4: Retrieve the Tweets from Twitter
	bRead = new BufferedReader (new InputStreamReader ((InputStream ) huc. getInputStream ()));
	for ( int i=0;i< statusarr . length ();i ++) {
		JSONObject jobj = statusarr . getJSONObject (i) ;
		statuses . put( jobj ) ;
	}
	
	// Step 5: Get the id of the oldest Tweet IDas max_id to retrieve the next batch of Tweets
	if(! jobj . isNull ("id")) {
		maxid = jobj . getLong ("id") ;
	}
	
	return statuses ;
}
	
public JSONArray GetSearchResults ( String query ) {
		
		try {
		
			// Step 1:
			String URL_PARAM_SEPERATOR = "&";
			StringBuilder url = new StringBuilder ();
			url . append (" https :// api. twitter . com /1.1/ search / tweets. json ?q=") ;
		
			// query needs to be encoded
			url . append ( URLEncoder . encode (query , "UTF -8"));
			url . append ( URL_PARAM_SEPERATOR ) ;
			url . append (" count =100 ") ;
			URL navurl = new URL ( url . toString ());
			HttpURLConnection huc = ( HttpURLConnection ) navurl .openConnection ();
			huc . setReadTimeout (5000);
			Consumer . sign (huc) ;
			huc . connect ();

			// Step 2: Read the retrieved search results
			BufferedReader bRead = new BufferedReader (new InputStreamReader (( InputStream ) huc .getInputStream ()));
			String temp ;
			StringBuilder page = new StringBuilder ();
			while( ( temp = bRead . readLine ())!=null) {
				page . append ( temp ) ;
			}
			JSONTokener jsonTokener = new JSONTokener ( page .toString ());
		
			try {
				JSONObject json = new JSONObject ( jsonTokener ) ;
				
			// Step 4: Extract the Tweet objects as an array
				JSONArray results = json . getJSONArray (" statuses ") ;
		
			return results ;

			}
}
		*/