/**
 * This class launch the program
 * @author The Coding Bang Fraternity
 * @version 1.0
 */

import java.io.IOException;
import java.util.Scanner;

import controller.User;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class main {

	private static int id_request = 0;
	private static Twitter twitter;

	public main() {
		twitter = TwitterFactory.getSingleton();
		twitter.setOAuthConsumer("qz06S2cROTQm1KYmuyNxFTEcr", "ki0GG0aNeU7hKziJpOEAk59saSXx7iggg64Bwp0vVorLJI2B7r");
		twitter.setOAuthAccessToken(new AccessToken("728437002-9mx6LMYTKfIkD0TVnEbv3KwJJXMNdqsVsPe0HWem",
				"NTFxjn6DKy5ontWdKfTPlklXwQZmYyCvgOZXstBjFBN6I"));

		// Database db = new Database();
		// java.sql.ResultSet resultset = db.select_request("Select
		// count(id_request) as id FROM request");
		// db.close();

		User user = new User("kilian_cuny", twitter);
		user.get("Followers");
		// user.startRequest(0);
	}

	public static void main(String[] args) throws IOException {

		main main_instance = new main();
		Scanner scan = new Scanner(System.in);

		/* --------------------- Global Menu --------------------- */

		Menu: do {

			System.out.println("[ ----- Coding bang Fraternity ----- ]");
			System.out.println("[ (1) Simple search                  ]");
			System.out.println("[ (2) Profile search                 ]");
			System.out.println("[ (3) History                        ]");
			System.out.println("[ (4) Reset searches                 ]");
			System.out.println("[ (5) Credits                        ]");
			System.out.println("[ (6) Exit                           ]\n");
			System.out.print("Selection: ");

			int globalSelection = scan.nextInt();
			boolean end = false;
			int choice = 0;
			do {
				switch (globalSelection) {
				case 1:
					// Simple Search
					choice = 1;
					end = true;
					break;

				case 2:
					// Profile Search
					choice = 2;
					end = true;
					break;

				case 3:
					// History
					choice = 3;
					end = true;
					break;

				case 4:
					// Reset Searches
					choice = 4;
					end = true;
					break;

				case 5:
					// Credits
					choice = 5;
					end = true;
					break;

				case 6:
					// Exit
					System.out.print("Exiting Program...");
					scan.close();
					System.exit(0);

				default:
					System.out.print("Enter a valid menu: ");
					globalSelection = scan.nextInt();
					break;
				}
				;
			} while (!end);

			/* ----------------- Simple Search ----------------- */

			if (choice == 1) {
				simpleSearch: do {

					System.out.println();
					System.out.println("[ ----- Coding bang Fraternity ----- ]");
					System.out.println("[ -----      Simple search     ----- ]");
					System.out.println("[ (1) New search                     ]");
					System.out.println("[ (2) Previous                       ]");
					System.out.println("[ (3) Exit                           ]\n");
					System.out.print("Selection: ");

					int simpleSearchSelection = scan.nextInt();
					end = false;
					do {
						switch (simpleSearchSelection) {
						case 1:
							end = true;
							break;
						case 2:
							end = true;
							break simpleSearch;
						case 3:
							System.out.print("Exiting Program...");
							scan.close();
							System.exit(0);
							break;
						default:
							System.out.print("Enter a valid menu: ");
							simpleSearchSelection = scan.nextInt();
							break;
						}
						;
					} while (!end);
				} while (true);
			}

			/* ----------------- Profile Search ----------------- */

			if (choice == 2) {
				profileSearch: do {

					System.out.println();
					System.out.println("[ ----- Coding bang Fraternity ----- ]");
					System.out.println("[ -----      Profile search    ----- ]");
					System.out.println("[ (1) New search                     ]");
					System.out.println("[ (2) Previous                       ]");
					System.out.println("[ (3) Exit                           ]\n");
					System.out.print("Selection: ");

					int profileSearchSelection = scan.nextInt();
					end = false;
					do {
						switch (profileSearchSelection) {
						case 1:
							System.out.println("Enter a Username");
							String userNameScan = scan.next();
							User userName = new User(userNameScan, twitter);
							userName.startRequest(id_request);
							System.out.println("Press any key to continue...");
							System.in.read();
							end = true;
							break;
						case 2:
							end = true;
							break profileSearch;
						case 3:
							System.out.print("Exiting Program...");
							scan.close();
							System.exit(0);
							break;
						default:
							System.out.print("Enter a valid menu: ");
							profileSearchSelection = scan.nextInt();
							break;
						}
						;
					} while (!end);
				} while (true);
			}

			/* ----------------- History ----------------- */

			if (choice == 3) {

			}

			/* ----------------- Reset Searches ----------------- */

			if (choice == 4) {

			}

			/* ----------------- Credits ----------------- */

			if (choice == 5) {
				System.out
						.println("App made with love by Clémence MOULIN, Kilian CUNY, Quentin PAYET & Guillaume HABEN");
				System.out.println("Press any key to go back...");
				System.in.read();
			}

		} while (true);
	}
}