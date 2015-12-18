/**
 * Main application, launch the program and init the layouts
 * @author The Coding Bang Fraternity
 * @version 4.0
 */

package controllerFX;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout; 
    
    private Twitter twitter; // Twitter object
    
    /**
     * Constructor of the main application
     */
    public MainApp() {
    	
    	/* Configuration for twitter */
		ConfigurationBuilder contractor = new ConfigurationBuilder();
		contractor.setOAuthConsumerKey("qz06S2cROTQm1KYmuyNxFTEcr");
		contractor.setOAuthConsumerSecret("ki0GG0aNeU7hKziJpOEAk59saSXx7iggg64Bwp0vVorLJI2B7r");
		Configuration configuration = contractor.build();
		
		TwitterFactory factory = new TwitterFactory(configuration);
		twitter = factory.getInstance();
		twitter.setOAuthAccessToken(new AccessToken("728437002-9mx6LMYTKfIkD0TVnEbv3KwJJXMNdqsVsPe0HWem", "NTFxjn6DKy5ontWdKfTPlklXwQZmYyCvgOZXstBjFBN6I"));
	}
    
    /**
     * This method is call by launch()
     */
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Coding Bang Fraternity");        

        initRootLayout();
        showMainOverview();
    }

    /**
     * Initializes the root layout
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            // Show the scene containing the root layout
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
     * Shows the home overview inside the root layout
     */
    public void showMainOverview() {
        try {
            // Load profile overview
            
        	FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/MainOverview.fxml"));
           
            TabPane tab = (TabPane) loader.load();
            rootLayout.setCenter(tab);
            
            MainController controller = loader.getController();
            controller.setMainApp(this);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage
     * @return primaryStage
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