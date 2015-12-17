/**
 * Controller for SimpleOverview
 * 
 * @author The Coding Bang Fraternity
 * @version 3.0
 */

package controllerFX;

import controller.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

public class SimpleController {

	@FXML
	private ListView<User> userList;
	
	@FXML
	private ObservableList<User> userObservable;
	
	@FXML
	private Label NameLabel;
	
	@FXML
	private TextField username;
	
	private MainApp mainApp;

	/**
     * The constructor is called before the initialize() method.
     */
    public SimpleController() {
    	userList = new ListView<User>();
    	userObservable =FXCollections.observableArrayList();
    	userList.setItems(userObservable);
    	userList.setCellFactory(new Callback<ListView<User>, ListCell<User>>(){
             public ListCell<User> call(ListView<User> p) {
                 ListCell<User> cell = new ListCell<User>(){
                     protected void updateItem(User u, boolean bln) {
                         super.updateItem(u, bln);
                         if (u != null)
                             setText(u.getName() + " : @" + u.getScreen_name());
                     }
                 };
                 return cell;
             }
    	});
    }
    
    /**
     * Returns the data as an observable list of User
     * @return userObservable
     */
    public ObservableList<User> getUserData() {
        return userObservable;
    }

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// Initialize the person table with the two columns.
	}

	 public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
     }
	
	@FXML
	public void setData() {
		User u = new User(username.getText(), mainApp.getTwitter());
		userObservable.add(u);
		userList.setItems(userObservable);
	}
}
