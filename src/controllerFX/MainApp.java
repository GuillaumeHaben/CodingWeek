package controllerFX;

import java.io.IOException;

import controller.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    
    private Twitter twitter;
    private Database db;

    /**
     * Constructor
     */
    public MainApp() {
		twitter = TwitterFactory.getSingleton();
		twitter.setOAuthConsumer("qz06S2cROTQm1KYmuyNxFTEcr", "ki0GG0aNeU7hKziJpOEAk59saSXx7iggg64Bwp0vVorLJI2B7r");
		twitter.setOAuthAccessToken(new AccessToken("728437002-9mx6LMYTKfIkD0TVnEbv3KwJJXMNdqsVsPe0HWem",
				"NTFxjn6DKy5ontWdKfTPlklXwQZmYyCvgOZXstBjFBN6I"));

		db = new Database();
	}
    
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Coding Bang Fraternity");
        
        

        initRootLayout();

        showPersonOverview();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setMinHeight(400);
            primaryStage.setMinWidth(600);
            primaryStage.setMaxHeight(400);
            primaryStage.setMaxWidth(600);
            primaryStage.getIcons().add(new Image("file:logo.png"));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the home overview inside the root layout.
     */
    public void showPersonOverview() {
        try {
            // Load home overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("HomeOverview.fxml"));
            AnchorPane homeOverview = (AnchorPane) loader.load();
            // Set home overview into the center of root layout.
            rootLayout.setCenter(homeOverview);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Get Twitter Object
     * @return twitter
     */
    public Twitter getTwitter(){
    	return twitter;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}