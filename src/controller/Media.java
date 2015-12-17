package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
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

	public void deleteMedia2() throws IOException {
		File MyFile = new File("./SavedMedia/");
		MyFile.delete();

	}

	static public void deleteMedia(String emplacement) {
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
		// String q = "INSERT INTO request(type, reference) VALUES('user','" +
		// keyword + "')";
		// db.request(q);
		Query query = new Query(keyword);
		QueryResult result = twitter.search(query);
		for (Status status : result.getTweets()) {
			MediaEntity[] mediaEntity = status.getMediaEntities();
			for (int i = 0; i < mediaEntity.length; i++) {
				// if (!status.isRetweet()) {
				String mediaURL = mediaEntity[i].getMediaURL();
				String destinationFile = "image" + status.getId() + ".jpg";
				try {
					saveMedia(mediaURL, destinationFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("\n@" + status.getUser().getScreenName() + ": " + status.getText()
						+ "\n MediaType : " + mediaEntity[i].getType() + "\n MediaId : " + mediaEntity[i].getId()
						+ "\n MediaURL : " + mediaEntity[i].getMediaURL());
				// } else {
				// ;
				// }

			}
		}
	}

	@Override
	public void logConsole(Status status) {
		// TODO Auto-generated method stubs

	}

}
