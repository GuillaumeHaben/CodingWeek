package controller;

import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.util.Set;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;

public class Live implements Collect {

	private static ConfigurationBuilder contractor = new ConfigurationBuilder();
	private Set<Twitter> mTwitterConnectorsSet;

	public Live(ConfigurationBuilder contractor, Twitter twitter) {
		super();
		this.contractor = contractor;
	}

	/**
	 * Getter and Setter of params
	 * 
	 * @return
	 */
	public static ConfigurationBuilder getContractor() {
		return contractor;
	}

	public static void setContractor(ConfigurationBuilder contractor) {
		Live.contractor = contractor;
	}

	public void startRequest() throws TwitterException {

		TwitterStream live = new TwitterStreamFactory(contractor.build()).getInstance();
		StatusListener listener = new StatusListener() {

			public void onStatus(Status status) {
				System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
			}

			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
				System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
			}

			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
				System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
			}

			public void onScrubGeo(long userId, long upToStatusId) {
				System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
			}

			public void onException(Exception ex) {
				ex.printStackTrace();
			}

			@Override
			public void onStallWarning(StallWarning arg0) {
				// TODO Auto-generated method stub

			}
		};

		FilterQuery fq = new FilterQuery();
		String keywords[] = { "keyword1", "keyword2" };

		fq.track(keywords);

		live.addListener(listener);
		live.filter(fq);
	}

	private Twitter getTweetConnector() {
		for (Twitter tc : mTwitterConnectorsSet) {
			try {
				if (tc.getRateLimitStatus() != null) {
					if (tc.getRateLimitStatus().containsKey("/users/lookup")) {
						if (tc.getRateLimitStatus().get("/users/lookup") != null) {
							System.out.println("tc - " + tc);
							System.out.println(
									"tc rate - " + tc.getRateLimitStatus().get("/users/lookup").getRemaining());
							if (tc.getRateLimitStatus().get("/users/lookup").getRemaining() > 2) {
								return tc;
							}
						}
					}
				}
			} catch (TwitterException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
