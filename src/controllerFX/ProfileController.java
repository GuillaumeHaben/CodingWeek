package controllerFX;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.User_tweet;
	
public class ProfileController{

	@FXML
	private ListView<User_tweet> userList;
	
	@FXML
	private ObservableList<User_tweet> userObservable;
	
	@FXML
	private Label NameLabel;
	
	@FXML
	private TextField username;

	/**
     * The constructor is called before the initialize() method.
     */
    public ProfileController() {
    	userObservable = FXCollections.observableArrayList();
    	userList = new ListView<User_tweet>(userObservable); 
    }
    
    /**
     * Initialize cell format
     */
    public void initialize(){
    	userList.setItems(userObservable);
    	userList.setCellFactory(new Callback<ListView<User_tweet>, ListCell<User_tweet>>(){
            public ListCell<User_tweet> call(ListView<User_tweet> p) {
                return new ListCell<User_tweet>(){
                    protected void updateItem(User_tweet item, boolean empty) { 
                        super.updateItem(item, empty); 
                        if (item != null) { 
                        	this.setText(item.screenNameProperty().get());
                        }
                    }
                };
            }
        });	
    }
    
    /**
     * Returns the data as an observable list of User
     * @return userObservable
     */
    public ObservableList<User_tweet> getUserData() {
        return userObservable;
    }

    /**
     * Launch a request for a specific author
     */
	@FXML
	public void setData() {
		User_tweet u = new User_tweet("Kilian", username.getText(), 20);
		this.userList.getItems().add(u);
	}
}
