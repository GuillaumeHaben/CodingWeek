/**
 * This class is used to collect medias with a specific keyword
 * @author The Coding Bang Fraternity
 * @version 5.0
 */

package model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import twitter4j.MediaEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class Media extends Params {

	private String keyword;

	public Media(String keyword, Twitter twitter) {
		super(twitter);
		this.keyword = keyword;
	}

	public static void saveMedia(String mediaURL, String destinationFile) throws IOException {
		URL url = new URL(mediaURL);
		InputStream is = url.openStream();
		OutputStream os = new FileOutputStream("./SavedMedia/" + destinationFile);
		
		byte[] b = new byte[2048];
		int length;

		while ((length = is.read(b)) != -1) {
			os.write(b, 0, length);
		}

		is.close();
		os.close();
	}

	public static void deleteMedia(String emplacement) {
		File path = new File(emplacement);
		if (path.exists()) {
			File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteMedia(path + "\\" + files[i]);
				} else {
					files[i].delete();
				}
			}
		}
	}

	public void startRequest() throws TwitterException {
		Query query = new Query(keyword);
		QueryResult result = twitter.search(query);
		for (Status status : result.getTweets()) {
			MediaEntity[] mediaEntity = status.getMediaEntities();
			for (int i = 0; i < mediaEntity.length; i++) {
				String mediaURL = mediaEntity[i].getMediaURL();
				String destFile[] = mediaEntity[i].getMediaURL().toString().split("/");
				String destinationFile = destFile[4];
				try {
					saveMedia(mediaURL, destinationFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("\n@" + status.getUser().getScreenName() + ": " + status.getText()
						+ "\n MediaType : " + mediaEntity[i].getType() + "\n MediaId : " + mediaEntity[i].getId()
						+ "\n MediaURL : " + mediaEntity[i].getMediaURL());
			}
		}
	}

	public void logConsole(Status status) {

	}

}
