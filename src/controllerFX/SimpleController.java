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
import model.Language;
import model.Multiparams;
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
	@FXML
	private Label loader;
	
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
					@SuppressWarnings("deprecation")
					protected void updateItem(Tweet item, boolean empty) {
						super.updateItem(item, empty);
						if (item != null) {

                            HBox box = new HBox();
                            box.setSpacing(10);

                            Date date = new Date(item.dateProperty().getValue());
                            VBox currentTweet;
                            Label tweetView = new Label("@" + item.screen_nameProperty().getValue() + " on " + date.toLocaleString() + "\n" + item.textProperty().getValue().replaceAll("(.{40} )", "$1\n"));     
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
                        				stage.getIcons().add(new Image(getClass().getResource("../logo.png").toString()));   

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
			loader.setText("Keyword missing");
			return;
		} else {
			keyword.setStyle("");
			date.setStyle("");
			language.setStyle("");
			loader.setText("");
		}

		if(date.getText().length() == 0 && language.getText().length() == 0){
			KeyWord kw = new KeyWord(keyword.getText(), this.getTwitter());
			int id_request = kw.startRequest();
			if(id_request == -1){
				loader.setText("Keyword missing");
				return;
			}
			ResultSet tweetsResult = db.select_request("SELECT * FROM tweet WHERE id_request = " + id_request);
			cleanTweetScreen(tweetList);
			createTweets(tweetsResult, tweetList);
			return;
		}
		
		if(language.getText().length() == 0){
			model.Date dt = new model.Date(keyword.getText(), date.getText(), this.getTwitter());
			int id_request = dt.startRequest();
			if(id_request == -2){
				date.setStyle("-fx-border-color: #AC58FA;");
				loader.setText("Invalid Date format");
				return;
			}
			if(id_request == -1)
				return;
			ResultSet tweetsResult = db.select_request("SELECT * FROM tweet WHERE id_request = " + id_request);
			cleanTweetScreen(tweetList);
			createTweets(tweetsResult, tweetList);
			return;
		}
		
		if(date.getText().length() == 0){
			Language la = new Language(keyword.getText(), language.getText(), this.getTwitter());
			int id_request = la.startRequest();
			if(id_request == -2){
				language.setStyle("-fx-border-color: #AC58FA;");
				loader.setText("Invalid language");
				return;
			}
			if(id_request == -1)
				return;
			ResultSet tweetsResult = db.select_request("SELECT * FROM tweet WHERE id_request = " + id_request);
			cleanTweetScreen(tweetList);
			createTweets(tweetsResult, tweetList);
			return;
		}
		
		Multiparams mp = new Multiparams(keyword.getText(), language.getText(), date.getText(), this.getTwitter());
		int id_request = mp.startRequest();
		if(id_request == -1){
			loader.setText("An error occured..");
			return;
		}
		ResultSet tweetsResult = db.select_request("SELECT * FROM tweet WHERE id_request = " + id_request);
		cleanTweetScreen(tweetList);
		createTweets(tweetsResult, tweetList);
		return;
	}
}