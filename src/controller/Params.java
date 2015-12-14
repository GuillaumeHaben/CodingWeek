package controller;

import twitter4j.Twitter;
import twitter4j.TwitterException;

public abstract class Params implements Collect {

	protected Twitter twitter;

	public abstract void startRequest() throws TwitterException;

}
