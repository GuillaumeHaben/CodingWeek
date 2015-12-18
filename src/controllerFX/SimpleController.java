/**
 * Controller for ProfileOverview
 * @author The Coding Bang Fraternity
 * @version 5.0
 */

package controllerFX;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.Tweet;
import model.User;

public class SimpleController extends ControllerFX {

	@FXML
	private ListView<Tweet> tweetList;
	@FXML
	private ObservableList<Tweet> tweetObservable;
	@FXML
	private TextField keyword;
	@FXML
	private TextField date;
	@FXML
	private TextField location;
	@FXML
	private TextField language;

	/**
	 * The constructor is called before the initialize() method.
	 */
	public SimpleController() {
		super();
		tweetObservable = FXCollections.observableArrayList();
		tweetList = new ListView<Tweet>(tweetObservable);
	}

	/**
	 * Initialize cell format and list
	 */
	public void initialize() {
		tweetList.setItems(tweetObservable);

		tweetList.setCellFactory(new Callback<ListView<Tweet>, ListCell<Tweet>>() {
			public ListCell<Tweet> call(ListView<Tweet> p) {
				return new ListCell<Tweet>() {
					protected void updateItem(Tweet item, boolean empty) {
						super.updateItem(item, empty);
						if (item != null)
							// new Image(User.image_URLProperty().get());
							this.setText(item.textProperty().get());
					}
				};
			}
		});
	}

	/**
	 * Returns the data as an observable list of Tweets
	 * 
	 * @return tweetObservable
	 */
	public ObservableList<Tweet> getTweetData() {
		return tweetList.getItems();
	}

	/*public void moreResult() {
		if (User != null && User.screen_nameProperty().get().compareTo(username.getText()) == 0) {
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
		} else
			loader.setText("Warning : Username is not valid !");
	}*/

	/**
	 * Launch a request on a specific author
	 */
	@FXML
	public void handleRequest() {
		if (keyword.getText().length() <= 0) {
			keyword.setStyle("-fx-border-color: #AC58FA;");
			return;
		} else {
			keyword.setStyle("");
		}

		
		/*
		case "tweets":
			userList.setVisible(false);
			tweetList.setVisible(true);

			ResultSet tweetsResult = db.select_request("SELECT id_request as id FROM request WHERE reference = '@"
					+ username.getText() + "' AND req = 'timeline' LIMIT 1");
			try {
				int id_request = 0;
				if (tweetsResult.next()) {
					alertUpdate.setTitle("Confirmation Dialog");
					alertUpdate.setHeaderText(null);
					alertUpdate.setContentText("   Would do you like to update ?");

					Image image = new Image("file:logo.png");
					ImageView img = new ImageView(image);
					alertUpdate.setGraphic(img);
					Stage stage = (Stage) alertUpdate.getDialogPane().getScene().getWindow();
					stage.getIcons().add(image);

					alertUpdate.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

					Optional<ButtonType> result = alertUpdate.showAndWait();

					if (result.get() == ButtonType.YES) {
						id_request = User.startRequest();
					} else {
						id_request = tweetsResult.getInt("id");
					}
				} else {
					id_request = User.startRequest();
				}

				if (id_request == -1) {
					username.setStyle("-fx-border-color: #AC58FA;");
					loader.setText("Warning : User unknow || Error");
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
				if (likesResult.next()) {

					alertUpdate.setTitle("Confirmation Dialog");
					alertUpdate.setHeaderText(null);
					alertUpdate.setContentText("   Would do you like to update ?");

					Image image = new Image("file:logo.png");
					ImageView img = new ImageView(image);
					alertUpdate.setGraphic(img);
					Stage stage = (Stage) alertUpdate.getDialogPane().getScene().getWindow();
					stage.getIcons().add(image);

					alertUpdate.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

					Optional<ButtonType> result = alertUpdate.showAndWait();

					if (result.get() == ButtonType.YES) {
						id_request = User.getLikes();
					} else {
						id_request = likesResult.getInt("id");
					}
				} else {
					id_request = User.getLikes();
				}

				if (id_request == -1) {
					username.setStyle("-fx-border-color: #AC58FA;");
					loader.setText("Warning : User unknown || Error");
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
				if (followersResult.next()) {

					alertUpdate.setTitle("Confirmation Dialog");
					alertUpdate.setHeaderText(null);
					alertUpdate.setContentText("   Would do you like to update ?");

					Image image = new Image("file:logo.png");
					ImageView img = new ImageView(image);
					alertUpdate.setGraphic(img);
					Stage stage = (Stage) alertUpdate.getDialogPane().getScene().getWindow();
					stage.getIcons().add(image);

					alertUpdate.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

					Optional<ButtonType> result = alertUpdate.showAndWait();

					if (result.get() == ButtonType.YES) {
						id_request = User.get("Followers");
					} else {
						id_request = followersResult.getInt("id");
					}
				} else {
					id_request = User.get("Followers");
				}

				if (id_request == -1) {
					username.setStyle("-fx-border-color: #AC58FA;");
					loader.setText("Warning : User unknown || Error");
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
				if (followingResult.next()) {
					alertUpdate.setTitle("Confirmation Dialog");
					alertUpdate.setHeaderText(null);
					alertUpdate.setContentText("   Would do you like to update ?");

					Image image = new Image("file:logo.png");
					ImageView img = new ImageView(image);
					alertUpdate.setGraphic(img);
					Stage stage = (Stage) alertUpdate.getDialogPane().getScene().getWindow();
					stage.getIcons().add(image);

					alertUpdate.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

					Optional<ButtonType> result = alertUpdate.showAndWait();

					if (result.get() == ButtonType.YES) {
						id_request = User.get("Following");
					} else {
						id_request = followingResult.getInt("id");
					}
				} else {
					id_request = User.get("Following");
				}

				if (id_request == -1) {
					username.setStyle("-fx-border-color: #AC58FA;");
					loader.setText("Warning : User unknown || Error ");
					return;
				}

				followingResult = db.select_request("SELECT * FROM user WHERE id_request = " + id_request);
				cleanUser_tweetScreen(userList);
				createUsers(followingResult, userList);

			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;

		case "informations":
			if (User.getInformation() == -1) {
				username.setStyle("-fx-border-color: #AC58FA;");
				loader.setText("Warning : User unknown");
			} else {

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle(User.nameProperty().get() + " : @" + User.screen_nameProperty().get());
				alert.setHeaderText(User.descriptionProperty().get().replaceAll("(.{40} )", "$1\n"));
				alert.setContentText("Followers : " + User.followers_countProperty().get() + "   \tFollowing : "
						+ User.friends_countProperty().get() + "\nFavourites : " + User.favourites_countProperty().get()
						+ "   \tTweets : " + User.statuses_countProperty().get());

				Image image = new Image(User.image_URLProperty().get());
				ImageView img = new ImageView(image);
				alert.setGraphic(img);
				Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
				stage.getIcons().add(new Image("file:logo.png"));

				alert.showAndWait();
			}
			break;
		default:
			break;
		}
		db.close();*/
	}
}