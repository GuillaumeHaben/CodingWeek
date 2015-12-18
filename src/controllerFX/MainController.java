/**
 * Controller for MainOverview
 * @author The Coding Bang Fraternity
 * @version 3.0
 */

package controllerFX;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;

public class MainController extends ControllerFX {
	@FXML private ProfileController profileController;
	@FXML private SimpleController simpleController;
	@FXML private TabPane tabPane;
	
	public MainController() {
    	super();
	}
	
	@FXML public void initialize() {
		
	}
}
