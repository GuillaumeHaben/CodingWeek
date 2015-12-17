/**
 * Controller for ProfileOverview
 * @author The Coding Bang Fraternity
 * @version 3.0
 */

package controllerFX;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
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
	private Label nameLabel;
	@FXML
	private Label loader;
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
	@FXML
	private Button more;

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
		
//		//Hide result panel by default
//		userList.setVisible(false);
//		tweetList.setVisible(false);
	}

	/**
	 * Initialize cell format and list
	 */
	public void initialize() {
		more.setDisable(true);
		
		userList.setItems(userObservable);
		tweetList.setItems(tweetObservable);
		tweetList.setVisible(false);
		userList.setVisible(false);
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
	
	@FXML
	public void enableMore(){
		switch (((RadioButton) choice.getSelectedToggle()).getId()) {
			case "tweets":
				more.setDisable(false);
				break;
			case "likes":
				more.setDisable(false);
				break;
			default:
				more.setDisable(true);
				break;
		}
	}

	
	public void moreResult(){
		if(User != null && User.screen_nameProperty().get().compareTo(username.getText()) == 0){
			int id_request = 0;
			switch (((RadioButton) choice.getSelectedToggle()).getId()) {
			
			case "tweets":
				User.setMore(true);
				User.startRequest();
				
				ResultSet tweetsResult = db.select_request("SELECT * FROM tweet WHERE id_request = " + id_request);
				createTweets(tweetsResult, tweetList);
				break;
				
			case "likes":
				User.setMore(true);
				User.getLikes();
				
				ResultSet likesResult = db.select_request("SELECT * FROM tweet WHERE id_request = " + id_request);
				createTweets(likesResult, tweetList);
				break;
			
			default:
				break;
			}
		} else loader.setText("Invalid input for more results");
	}
	
	/**
	 * Launch a request on a specific author
	 */
	@FXML
	public void handleRequest() {
		if(username.getText().length() < 3) {
			username.setStyle("-fx-border-color: #AC58FA;");
			return;
		} else { 
			username.setStyle("");
			loader.setText("");
		}
		
		db.init();
		
		
		User = new User(username.getText(), mainApp.getTwitter());
		switch (((RadioButton) choice.getSelectedToggle()).getId()) {

		case "tweets":
			userList.setVisible(false);
			tweetList.setVisible(true);
			ResultSet tweetsResult = db.select_request("SELECT id_request as id FROM request WHERE reference = '@"
					+ username.getText() + "' AND req = 'timeline' LIMIT 1");
			try {
				int id_request = 0;
				if (tweetsResult.next())
					id_request = tweetsResult.getInt("id");	
				else
					id_request = User.get("Likes");
				
				if(id_request == -1) {
					username.setStyle("-fx-border-color: #AC58FA;");
					loader.setText("Warning : User unknown");
					return;
				}
				
				tweetsResult = db.select_request("SELECT * FROM tweet WHERE id_request = " + id_request);
				cleanTweetScreen(tweetList);
				createTweets(tweetsResult, tweetList);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
			
		case "likes":
			userList.setVisible(false);
			tweetList.setVisible(true);
			ResultSet likesResult = db.select_request("SELECT id_request as id FROM request WHERE reference = '@"
					+ username.getText() + "' AND req = 'likes' LIMIT 1");
			try {
				int id_request = 0;
				if (likesResult.next())
					id_request = likesResult.getInt("id");
				else
					id_request = User.get("Likes");
				
				if(id_request == -1) {
					username.setStyle("-fx-border-color: #AC58FA;");
					loader.setText("Warning : User unknown");
					return;
				}
				
				likesResult = db.select_request("SELECT * FROM tweet WHERE id_request = " + id_request);
				cleanTweetScreen(tweetList);
				createTweets(likesResult, tweetList);
			
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
			
		case "followers":
			tweetList.setVisible(false);
			userList.setVisible(true);
			ResultSet followersResult = db.select_request("SELECT id_request as id FROM request WHERE reference = '@"
					+ username.getText() + "' AND req = 'Followers' LIMIT 1");
			try {
				int id_request = 0;
				if (followersResult.next())
					id_request = followersResult.getInt("id");
				else
					id_request = User.get("Followers");
				
				if(id_request == -1) {
					username.setStyle("-fx-border-color: #AC58FA;");
					loader.setText("Warning : User unknown");
					return;
				}
				
				followersResult = db.select_request("SELECT * FROM user WHERE id_request = " + id_request);
				cleanUser_tweetScreen(userList);
				createUsers(followersResult, userList);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
			
		case "following":
			tweetList.setVisible(false);
			userList.setVisible(true);
			ResultSet followingResult = db.select_request("SELECT id_request as id FROM request WHERE reference = '@"
					+ username.getText() + "' AND req = 'Following' LIMIT 1");
			try {
				int id_request = 0;
				if (followingResult.next())
					id_request = followingResult.getInt("id");
				else 
					id_request = User.get("Following");
				
				if(id_request == -1) {
					username.setStyle("-fx-border-color: #AC58FA;");
					loader.setText("Warning : User unknown");
					return;
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
			if(User.getInformation() == -1){
				username.setStyle("-fx-border-color: #AC58FA;");
				loader.setText("Warning : User unknown");
			} else {
			
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle(User.nameProperty().get() + " : @" + User.screen_nameProperty().get());
				alert.setHeaderText(User.descriptionProperty().get().replaceAll("(.{50})", "$1\n"));
				alert.setContentText("Number of followers : " + User.followers_countProperty().get()
						+ "\nNumber of following : " + User.friends_countProperty().get()
						+ "\nNumber of tweets : " + User.statuses_countProperty().get());
				
				Image image = new Image("file:logo.png");
				ImageView img = new ImageView(image);
				alert.setGraphic(img);
				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				stage.getIcons().add(image);
				
				alert.showAndWait();
			}
			break;
		default:
			break;
		}
		db.close();
	}
}
