package controllerFX;

import controller.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class ProfilController {

	@FXML
	private ListView<String> userList;
	@FXML
	private ObservableList<String> items;
	@FXML
	private Label NameLabel;

	/**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public ProfilController() {
    	userList = new ListView<String>();
    	items =FXCollections.observableArrayList (
    		    "Single", "Double", "Suite", "Family App");
    	
    	userList.setItems(items);
    }

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// Initialize the person table with the two columns.
		//NameColumn.setCellValueFactory(cellData -> cellData.getValue().NameProperty());
		//ScreenNameColumn.setCellValueFactory(cellData -> cellData.getValue().ScreenNameProperty());
	}

	
	public void setData(int id_request) {

		// Add observable list data to the table
		//userTable.setItems(...);
	}
}
