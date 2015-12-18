import java.net.HttpURLConnection;
import java.net.URL;
import controllerFX.MainApp;
import defaultpackage.Main_app;

/**
 * Main application, launch the program or in console or in interface, and init
 * the layouts
 * 
 * @author The Coding Bang Fraternity
 * @version 5.0
 */
public class Main {

	public static void main(String[] args) {

		/* Test of Java's version */
		Double version = Double.parseDouble(System.getProperty("java.specification.version"));

		if (version < 1.8) {
			System.out.println("Your version of Java isn't compatible. Please upgrade to Java 1.8 or more.");
			return;
		}

		/* Test the Internet connection */
		try {
			URL url = new URL("http://www.google.com");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.connect();
			if (con.getResponseCode() == 200) {
			}
		} catch (Exception exception) {
			System.out.println("You haven't an Internet connection");
			return;
		}

		// Launch the application in Console or Interface
		if (args.length > 0) {
			if (args[0].compareTo("-console") == 0) {
				Main_app main_app = new Main_app();
				main_app.launchApp();
			} else {
				MainApp mainapp = new MainApp();
				mainapp.launchApp(args);
			}
		} else {
			MainApp mainapp = new MainApp();
			mainapp.launchApp(args);
		}
	}

}
