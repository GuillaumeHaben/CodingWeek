/**
 * This abstract class handle params queries
 * @author The Coding Bang Fraternity
 * @version 1.0
 */

package controller;

import twitter4j.Twitter;
import twitter4j.TwitterException;

public abstract class Params implements Collect {

	protected Twitter twitter;

	public abstract void startRequest() throws TwitterException;

}
