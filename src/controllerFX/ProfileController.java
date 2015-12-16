/**
 * Controller for ProfileOverview
 * @author The Coding Bang Fraternity
 * @version 3.0
 */

package controllerFX;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.util.Callback;
import model.Tweet;
import model.User;

public class ProfileController extends ControllerFX {

	@FXML
	private ListView<User> userList;
	@FXML
	private ListView<Tweet> tweetList;
	@FXML
	private ObservableList<User> userObservable;
	@FXML
	private ObservableList<Tweet> tweetObservable;
	@FXML
	private Label NameLabel;
	@FXML
	private TextField username;
	@FXML
	private ToggleGroup choice;
	@FXML
	private RadioButton tweets;
	@FXML
	private RadioButton likes;
	@FXML
	private RadioButton followers;
	@FXML
	private RadioButton following;
	@FXML
	private RadioButton informations;

	private User User;
	private Tweet Tweet;

	/**
	 * The constructor is called before the initialize() method.
	 */
	public ProfileController() {
		super();
		userObservable = FXCollections.observableArrayList();
		userList = new ListView<User>(userObservable);
		tweetList = new ListView<Tweet>(tweetObservable);
	}

	/**
	 * Initialize cell format and list
	 */
	public void initialize() {
		userList.setItems(userObservable);
		tweetList.setItems(tweetObservable);

		userList.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {
			public ListCell<User> call(ListView<User> p) {
				return new ListCell<User>() {
					protected void updateItem(User item, boolean empty) {
						super.updateItem(item, empty);
						if (item != null)
							this.setText(item.screen_nameProperty().get());
					}
				};
			}
		});

		tweetList.setCellFactory(new Callback<ListView<Tweet>, ListCell<Tweet>>() {
			public ListCell<Tweet> call(ListView<Tweet> p) {
				return new ListCell<Tweet>() {
					protected void updateItem(Tweet item, boolean empty) {
						super.updateItem(item, empty);
						if (item != null)
							this.setText(item.textProperty().get());
					}
				};
			}
		});
	}

	/**
	 * Returns the data as an observable list of User
	 * 
	 * @return userObservable
	 */
	public ObservableList<User> getUserData() {
		return userList.getItems();
	}

	/**
	 * Returns the data as an observable list of Tweet
	 * 
	 * @return tweetObservable
	 */
	public ObservableList<Tweet> getTweetData() {
		return tweetList.getItems();
	}

	/**
	 * Launch a request for on a specific author
	 */
	@FXML
	public void handleRequest() {
		User = new User(username.getText(), mainApp.getTwitter());
		switch (((RadioButton) choice.getSelectedToggle()).getId()) {

		case "tweets":
			ResultSet tweetsResult = db.select_request("SELECT id_request FROM request WHERE reference = '@"
					+ username.getText() + "' AND req = 'Likes' LIMIT 1");
			try {
				int id_request = 0;
				if (tweetsResult.next()) {
					id_request = tweetsResult.getInt(0);	
				} else {
					id_request = User.get("Likes");
				}
				
				tweetsResult = db.select_request("SELECT * FROM tweet WHERE id_request = " + id_request);
				cleanTweetScreen(tweetList);
				createTweets(tweetsResult, tweetList);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
			
		case "likes":
			
			ResultSet likesResult = db.select_request("SELECT id_request FROM request WHERE reference = '@"
					+ username.getText() + "' AND req = 'Likes' LIMIT 1");
			try {
				int id_request = 0;
				if (likesResult.next()) {
					id_request = likesResult.getInt(0);
				} else {
					id_request = User.get("Likes");
				}
				
				likesResult = db.select_request("SELECT * FROM tweet WHERE id_request = " + id_request);
				cleanTweetScreen(tweetList);
				createTweets(likesResult, tweetList);
			
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
			
		case "followers":
			ResultSet followersResult = db.select_request("SELECT id_request FROM request WHERE reference = '@"
					+ username.getText() + "' AND req = 'Followers' LIMIT 1");
			try {
				int id_request = 0;
				if (followersResult.next())
					id_request = followersResult.getInt(0);
				else {
					id_request = User.get("Followers");
				}
				
				followersResult = db.select_request("SELECT * FROM user WHERE id_request = " + id_request);
				cleanUser_tweetScreen(userList);
				createUsers(followersResult, userList);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
			
		case "following":
			ResultSet followingResult = db.select_request("SELECT id_request FROM request WHERE reference = '@"
					+ username.getText() + "' AND req = 'Following' LIMIT 1");
			try {
				int id_request = 0;
				if (followingResult.next()) {
					id_request = followingResult.getInt(0);
				} else {
					id_request = User.get("Following");
				}
				
				followingResult = db.select_request("SELECT * FROM user WHERE id_request = " + id_request);
				cleanUser_tweetScreen(userList);
				createUsers(followingResult, userList);
			
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
			
			//TODO Infomation
		case "informations":
			break;
		default:
			break;
		}
	}
}
