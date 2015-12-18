/**
 * Controller for MainOverview
 * @author The Coding Bang Fraternity
 * @version 3.0
 */

package controllerFX;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;

public class MainController extends ControllerFX {
	@FXML private Tab profileController;
	@FXML private Tab simpleController;
	@FXML private TabPane tabPane;
	
	public MainController() {
    	super();
	}
	
	@FXML public void initialize() {
		
	}
}
