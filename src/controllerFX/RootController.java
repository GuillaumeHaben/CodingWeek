/**
 * Controller for RootLayout
 * @author The Coding Bang Fraternity
 * @version 5.0
 */

package controllerFX;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class RootController extends ControllerFX {

	@FXML private MenuBar mainNavBar;
	
	@FXML private void Credit() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Application's Credits");
		alert.setHeaderText(null);
		alert.setContentText("App made with love by Clémence MOULIN, Kilian CUNY, Quentin PAYET & Guillaume HABEN !");
		
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("file:logo.png"));

		alert.setGraphic(null);		
		alert.showAndWait();
	}
	
	@FXML private void Reinit() {
		if(db.reinit() == -1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Ooops, there was an error with the reinitalisation!");
			alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Success");
			alert.setHeaderText(null);
			alert.setContentText("The database was erased successfully !\nYou can go fetch new requests ");
			alert.showAndWait();
		}
	}
	
	@FXML public void initialize() {
		
	}
}
