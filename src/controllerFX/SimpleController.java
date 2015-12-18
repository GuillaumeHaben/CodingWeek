/**
 * Controller for ProfileOverview
 * @author The Coding Bang Fraternity
 * @version 5.0
 */

package controllerFX;

import java.io.File;
import java.sql.ResultSet;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.KeyWord;
import model.Tweet;

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
	private TextField localisation;
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
		localisation.setDisable(true);
		
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
	 * Returns the data as an observable list of Tweets
	 * 
	 * @return tweetObservable
	 */
	public ObservableList<Tweet> getTweetData() {
		return tweetList.getItems();
	}

	/**
	 * Launch a request on a keyword with parameters
	 */
	@FXML
	public void handleRequest() {
		if (keyword.getText().length() <= 0) {
			keyword.setStyle("-fx-border-color: #AC58FA;");
			return;
		} else {
			keyword.setStyle("");
		}

		if(date.getText().length() == 0 && language.getText().length() == 0){
			KeyWord kw = new KeyWord(keyword.getText(), this.getTwitter());
			int id_request = kw.startRequest();
			ResultSet tweetsResult = db.select_request("SELECT * FROM tweet WHERE id_request = " + id_request);
			cleanTweetScreen(tweetList);
			createTweets(tweetsResult, tweetList);

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