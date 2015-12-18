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
		stage.getIcons().add(new Image(getClass().getResource("logo.png").toString()));   

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
	
	@FXML private void Help() {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("How to use");
			alert.setHeaderText(null);
			alert.setContentText("After running the app, you can either do a simple search or a profile search."
					+ "\nBy selecting the simple search, you can write a keyword, date or language."
					+ "\nYou will get the collect from the fields you have filled."
					+ "\nBy selecting the profile search, you can enter a username "
					+ "and have the tweets, likes, followers, following and informations "
					+ "about this profile."
					+ "\nA dialog window will appear to ask you if you want to update the collect in the case "
					+ "you have already done this collect."
					+ "\nIf you say yes, you will get the new informations from the Twitter API, but if you"
					+ "say no, you will get the collect stored."
					+ "\nThe button \"Add 20\" asks for 20 more tweets."
					+ "\nThe \"File\" button in the menu bar enables the reset of the database. "
					+ "\nIn a tweet, the \"Load media\" button opens a window with the media attached"
					+ "\nA double click on a user in the profile searches puts the user in the research field "
					+ "to get informations about him.");
			alert.showAndWait();
	}
	
	@FXML public void initialize() {
		
	}
}
