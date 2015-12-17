/**
 * This class collects create an object Tweet
 * @author The Coding Bang Fraternity
 * @version 4.0
 */

package model;

import java.sql.Date;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Tweet {

    private LongProperty id;
    
    private StringProperty name;
    private StringProperty screen_name;
    private StringProperty text;
    private IntegerProperty retweet;
    private StringProperty city;
    private StringProperty country;
    private IntegerProperty latitude;
    private IntegerProperty longitude;
    private LongProperty date_creation;
    private StringProperty content;
    
	public Tweet(Long id, String name, String screen_name, String text,
			Integer retweet, String city, String country, Integer latitude,
			Integer longitude, Long date_creation, String content) {
		
		this.id = new SimpleLongProperty(id);
		this.name = new SimpleStringProperty(name);
		this.screen_name = new SimpleStringProperty(screen_name);
		this.text =new SimpleStringProperty(text);
		this.retweet = new SimpleIntegerProperty(retweet);
		this.city = new SimpleStringProperty(city);
		this.country = new SimpleStringProperty(country);
		this.latitude = new SimpleIntegerProperty(latitude);
		this.longitude = new SimpleIntegerProperty(longitude);
		this.date_creation = new SimpleLongProperty(date_creation);
		this.content = new SimpleStringProperty(content);
	}
	
	public LongProperty idProperty() {
		return id;
	}
	
	public StringProperty nameProperty() {
		return name;
	}
	
	public StringProperty screen_nameProperty() {
		return screen_name;
	}
	
	public StringProperty textProperty() {
		return text;
	}
	
	public IntegerProperty retweetProperty() {
		return retweet;
	}
	
	public StringProperty countryProperty() {
		return country;
	}
	
	public StringProperty cityProperty() {
		return city;
	}
	
	public IntegerProperty latitudeProperty() {
		return latitude;
	}
	
	public IntegerProperty longitudeProperty() {
		return longitude;
	}
	
	public LongProperty dateProperty(){
		return date_creation;
	}
	
	public StringProperty contentProperty() {
		return content;
	}
}
  