package controllerFX;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

import model.User_tweet;
	
public class ProfileController extends ControllerFX {

	@FXML private ListView<User_tweet> userList;
	
	@FXML private ObservableList<User_tweet> userObservable;
	
	@FXML private Label NameLabel;
	
	@FXML private TextField username;
	
	private User_tweet user_tweet;

	/**
     * The constructor is called before the initialize() method.
     */
    public ProfileController() {
    	super();
    	userObservable = FXCollections.observableArrayList();
    	userList = new ListView<User_tweet>(userObservable); 
    }
    
    /**
     * Initialize cell format and list
     */
    public void initialize(){
    	userList.setItems(userObservable);
    	userList.setCellFactory(new Callback<ListView<User_tweet>, ListCell<User_tweet>>(){
            public ListCell<User_tweet> call(ListView<User_tweet> p) {
                return new ListCell<User_tweet>(){
                    protected void updateItem(User_tweet item, boolean empty) { 
                        super.updateItem(item, empty); 
                        if (item != null) 
                        	this.setText(item.screenNameProperty().get());
                    }
                };
        }});	
    }
    
    /**
     * Returns the data as an observable list of User
     * @return userObservable
     */
    public ObservableList<User_tweet> getUserData() {
        return userList.getItems();
    }

    /**
     * Link the main app object
     * @param ma : MainApp Object
     */
    public void setMainApp(MainApp mapp){
    	this.mainApp = mapp;
    }
    
    /**
     * Launch a request for on a specific author
     */
	@FXML public void handleRequest() {
		this.user_tweet = new User_tweet(username.getText(), this.mainApp.getTwitter());
		
		ResultSet rs = db.select_request("SELECT id_request FROM request WHERE reference = '@" + user_tweet.screenNameProperty().get() 
				+ "' AND req = 'Followers' LIMIT 1");
		try {
			if (rs.next())
				System.out.println("find");
			else {
				int id_request = user_tweet.get("Followers");
				
				rs = db.select_request("SELECT * FROM user WHERE id_request = " + id_request);
				createUsers(rs, userList);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
}
