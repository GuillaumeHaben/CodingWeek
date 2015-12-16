package model;

import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Callback;

public class User_tweet {

	private final StringProperty name;
    private final StringProperty screenName;
    private final IntegerProperty id_request;
    
    public User_tweet(String name, String screenName, int id_request) {
        this.name = new SimpleStringProperty(name);
        this.screenName = new SimpleStringProperty(screenName);
        this.id_request = new SimpleIntegerProperty(id_request);
    }
    
    public String getName() {
        return name.get();
    }
    public String getScreenName() {
        return screenName.get();
    }
    public int getId_request() {
        return id_request.get();
    }
    
    public StringProperty nameProperty() {
        return name;
    }
    
    public StringProperty screenNameProperty() {
        return screenName;
    }
    
    public IntegerProperty id_requestProperty() {
        return id_request;
    }
    
    public static Callback<User_tweet, Observable[]> extractor() {
	    return (User_tweet u) -> new Observable[]{
	    		u.nameProperty(), u.screenNameProperty(), u.id_requestProperty()
	    };
    }
    
    public String stringValue() {
    	return String.format("%s : @%s", getName(), getScreenName());
    }
}
