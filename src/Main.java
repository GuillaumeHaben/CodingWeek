import controllerFX.MainApp;

/**
 * Main application, launch the program or in console or in interface, and init
 * the layouts
 * 
 * @author The Coding Bang Fraternity
 * @version 5.0
 */
public class Main {

	public static void main(String[] args) {
		
		// Test of Java's version
		Double version = Double.parseDouble(System.getProperty("java.specification.version"));

		if (version < 1.8) {
		    System.out.println("Your version of Java isn't compatible. Please upgrade to Java 1.8 or more.");
		    return;
		}
		
		// Launch the application in Console or Interface
		if (args[0] != null) {
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
