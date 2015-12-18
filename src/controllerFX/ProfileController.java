/**
 * Controller for ProfileOverview
 * @author The Coding Bang Fraternity
 * @version 5.0
 */

package controllerFX;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
		more.setDisable(true);

		userList.setItems(userObservable);
		tweetList.setItems(tweetObservable);

		tweetList.setVisible(false);
		userList.setVisible(false);

		tweetList.setVisible(false);
		userList.setVisible(false);
		userList.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {
			public ListCell<User> call(ListView<User> p) {
				return new ListCell<User>() {
					protected void updateItem(User item, boolean empty) {
						super.updateItem(item, empty);
						if (item != null) {

                            HBox box = new HBox();
                            box.setSpacing(10);
                            VBox currentTweet = new VBox(new Label("@" + item.screen_nameProperty().get()));
                            
                            ImageView imageview = new ImageView();
                            imageview.setFitHeight(50);
                            imageview.setFitWidth(50);
                            imageview.setImage(new Image(item.image_URLProperty().get()));
                            
                            box.getChildren().addAll(imageview, currentTweet);
                            setGraphic(box);
						}
					}
				};
			}
		});

		tweetList.setCellFactory(new Callback<ListView<Tweet>, ListCell<Tweet>>() {
			public ListCell<Tweet> call(ListView<Tweet> p) {
				return new ListCell<Tweet>() {
					protected void updateItem(Tweet item, boolean empty) {
						super.updateItem(item, empty);
						if (item != null) {

                            HBox box = new HBox();
                            box.setSpacing(10);
                            Date date = new Date(item.dateProperty().getValue());
                            
                            Label tweetView = new Label("@" + item.screen_nameProperty().getValue() + " on " + date + "\n" + item.textProperty().getValue().replaceAll("(.{40} )", "$1\n"));
                           
                            VBox currentTweet;
                            if (item.contentProperty().get().compareTo("") != 0) {
                                Button mediaLoad = new Button("Load Media");
                                mediaLoad.setStyle("-fx-base: #ecf0f1;");
                                mediaLoad.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent event) {

                                        BorderPane pane;
                                        Scene scene;
                                        Stage stage;
                                        File imageFile;
                                        Image image;
                                        ImageView imageView;
                                        
                                        String destFile[] = item.contentProperty().get().split("/");
                    					String destinationFile = destFile[4];
                    					System.out.println("./SavedMedia/" + destinationFile);
                                        imageFile = new File("./SavedMedia/" + destinationFile);
                                        
                                        image = new Image(imageFile.toURI().toString());
                                        imageView = new ImageView(image);
                                        pane = new BorderPane();
                                        pane.setCenter(imageView);
                                        scene = new Scene(pane);
                                        stage = new Stage();
                                        stage.setScene(scene);
                                        // Without this, the audio won't stop!
                                        stage.setOnCloseRequest(
                                            e -> {
                                                e.consume();
                                                stage.close();
                                            }
                                        );
                                        stage.getIcons().add(new Image("file:logo.png"));
                                        stage.showAndWait();
                                    }
                                });
                                currentTweet = new VBox(tweetView, mediaLoad);
                            }
                            else {
                                currentTweet = new VBox(tweetView);
                            }

                            ImageView imageview = new ImageView();
                            imageview.setFitHeight(50);
                            imageview.setFitWidth(50);
                            imageview.setImage(new Image(item.profileProperty().getValue()));
                            
                            box.getChildren().addAll(imageview, currentTweet);
                            setGraphic(box);
							//this.setText(item.textProperty().get());
							// new Image(User.image_URLProperty().get());
						}
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
	public void enableMore() {
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

	public void moreResult() throws IOException {
		if (User != null && User.screen_nameProperty().get().compareTo(username.getText()) == 0) {
			int id_request = 0;
			switch (((RadioButton) choice.getSelectedToggle()).getId()) {

			case "tweets":
				User.setMore(true);
				id_request = User.startRequest();

				ResultSet tweetsResult = db.select_request("SELECT * FROM tweet WHERE id_request = " + id_request);
				cleanTweetScreen(tweetList);
				createTweets(tweetsResult, tweetList);
				break;

			case "likes":
				User.setMore(true);
				id_request = User.getLikes();

				ResultSet likesResult = db.select_request("SELECT * FROM tweet WHERE id_request = " + id_request);
				cleanTweetScreen(tweetList);
				createTweets(likesResult, tweetList);
				break;

			default:
				break;
			}
		} else
			loader.setText("Warning : Username is not valid !");
	}

	/**
	 * Launch a request on a specific author
	 * @throws IOException 
	 */
	@FXML
	public void handleRequest() throws IOException {
		if (username.getText().length() < 3) {
			username.setStyle("-fx-border-color: #AC58FA;");
			return;
		} else {
			username.setStyle("");
			loader.setText("");
		}

		Alert alertUpdate = new Alert(AlertType.CONFIRMATION);
		User = new User(username.getText(), mainApp.getTwitter());
		switch (((RadioButton) choice.getSelectedToggle()).getId()) {

		case "tweets":
			userList.setVisible(false);
			tweetList.setVisible(true);
			more.setDisable(false);
			ResultSet tweetsResult = db.select_request("SELECT id_request as id FROM request WHERE reference = '@"
					+ username.getText() + "' AND req = 'timeline' LIMIT 1");
			try {
				int id_request = 0;
				if (tweetsResult.next())
						id_request = tweetsResult.getInt("id");
				else
					id_request = User.startRequest();
				

				if (id_request == -1) {
					username.setStyle("-fx-border-color: #AC58FA;");
					loader.setText("Username unknown");
					loader.setTextFill(Color.RED);
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
			more.setDisable(false);
			userList.setVisible(false);
			tweetList.setVisible(true);

			ResultSet likesResult = db.select_request("SELECT id_request as id FROM request WHERE reference = '@"
					+ username.getText() + "' AND req = 'likes' LIMIT 1");
			try {
				int id_request = 0;
				if (!likesResult.next())
					id_request = User.getLikes();
				else
					id_request = likesResult.getInt("id");

				if (id_request == -1) {
					username.setStyle("-fx-border-color: #AC58FA;");
					loader.setText("Username unknown");
					loader.setTextFill(Color.RED);
					return;
				}

				ResultSet Result = db.select_request("SELECT * FROM tweet WHERE id_request = " + id_request);
				cleanTweetScreen(tweetList);
				createTweets(Result, tweetList);

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
					alertUpdate.setContentText("   Would do you like to update the Follower's list ?");

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
					loader.setText("Username unknown");
					loader.setTextFill(Color.RED);
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
					alertUpdate.setContentText("   Would do you like to update the Following's list ?");

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
					loader.setText("Username unknown");
					loader.setTextFill(Color.RED);
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
				loader.setText("Username unknown");
				loader.setTextFill(Color.RED);
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
		db.close();
	}
}
