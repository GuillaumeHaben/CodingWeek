/**
 * This class launch the program
 * @author The Coding Bang Fraternity
 * @version 2.0
 */

/* TO-DO Link methode au bon appel et sous appel */

import java.io.IOException;
import java.util.Scanner;

import controller.Database;
import controller.Date;
import controller.Hashtag;
import controller.KeyWord;
import controller.Language;
import controller.User;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public class main {

	private static Twitter twitter;
	private static Database db;

	public main() {
		twitter = TwitterFactory.getSingleton();
		twitter.setOAuthConsumer("qz06S2cROTQm1KYmuyNxFTEcr", "ki0GG0aNeU7hKziJpOEAk59saSXx7iggg64Bwp0vVorLJI2B7r");
		twitter.setOAuthAccessToken(new AccessToken("728437002-9mx6LMYTKfIkD0TVnEbv3KwJJXMNdqsVsPe0HWem",
				"NTFxjn6DKy5ontWdKfTPlklXwQZmYyCvgOZXstBjFBN6I"));

		db = new Database();
	}

	// Ajouter le réutilisation de l'utilisateur
	// Historique
	// Simple search location
	// Hashtag == KeyWord Suppresion ?
	
	@SuppressWarnings("unused")
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
					db.close();
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
					int searchChoice = 0;
					int simpleSearchSelection = scan.nextInt();
					end = false;
					do {
						switch (simpleSearchSelection) {
						case 1:
							System.out.println();
							System.out.println("[ ----- Coding bang Fraternity ----- ]");
							System.out.println("[ -----      Simple search     ----- ]");
							System.out.println("[ -----        New search      ----- ]");
							System.out.println("[ (1) By Hashtag                     ]");
							System.out.println("[ (2) By Date                        ]");
							System.out.println("[ (3) By Keyword                     ]");
							System.out.println("[ (4) By Location                    ]");
							System.out.println("[ (5) By Language                    ]");
							System.out.print("[ Select a parameter: ");
							int newSearchParam = scan.nextInt();
							if (newSearchParam >= 1 && newSearchParam <= 5) {
								searchChoice = newSearchParam;
								end = true;
							}
							else {
								System.out.println("Please enter a valid option \n");
							}
							break;
						case 2:
							end = true;
							System.out.println("");
							break simpleSearch;
						case 3:
							System.out.print("Exiting Program...");
							db.close();
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
					if (searchChoice == 1) {
						System.out.println("[ ----- Coding bang Fraternity ----- ]");
						System.out.println("[ -----      Simple search     ----- ]");
						System.out.println("[ -----        New search      ----- ]");
						System.out.print("[ Hashtag: ");
						String searchByHashtag = scan.next();
						Hashtag myHashtag = new Hashtag(searchByHashtag, twitter);
						myHashtag.startRequest();
						System.out.println("Press any key to continue...");
						System.in.read();
					}
					if (searchChoice == 2) {
						System.out.println("[ ----- Coding bang Fraternity ----- ]");
						System.out.println("[ -----      Simple search     ----- ]");
						System.out.println("[ -----        New search      ----- ]");
						System.out.print("[ Date: ");
						String searchByDate = scan.next();
						System.out.print("[ Keyword: ");
						String searchByKeyword = scan.next();
						Date myDate = new Date(searchByKeyword, searchByDate, twitter);
						myDate.startRequest();
						System.out.println("Press any key to continue...");
						System.in.read();
					}
					if (searchChoice == 3) {
						System.out.println("[ ----- Coding bang Fraternity ----- ]");
						System.out.println("[ -----      Simple search     ----- ]");
						System.out.println("[ -----        New search      ----- ]");
						System.out.print("[ Keyword: ");
						String searchByKeyword = scan.next();
						KeyWord myKeyword = new KeyWord(searchByKeyword, twitter);
						myKeyword.startRequest();
						System.out.println("Press any key to continue...");
						System.in.read();
					}
					if (searchChoice == 4) {
						System.out.println("[ ----- Coming Soon ----- ]");
						/*
						System.out.println("[ ----- Coding bang Fraternity ----- ]");
						System.out.println("[ -----      Simple search     ----- ]");
						System.out.println("[ -----        New search      ----- ]");
						System.out.print("[ Location: ");
						String searchByLocation = scan.next();
						Location myLocation = new Location();
						myLocation.startRequest();
						System.out.println("Press any key to continue...");
						System.in.read();
						*/
					}
					if (searchChoice == 5) {
						System.out.println("[ ----- Coding bang Fraternity ----- ]");
						System.out.println("[ -----      Simple search     ----- ]");
						System.out.println("[ -----        New search      ----- ]");
						System.out.print("[ Language: ");
						String searchByLanguage = scan.next();
						System.out.print("[ Keyword: ");
						String searchByKeyword = scan.next();
						Language myLanguage = new Language(searchByKeyword, searchByLanguage, twitter);
						myLanguage.startRequest();
						System.out.println("Press any key to continue...");
						System.in.read();
					}
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

					int searchChoice = 0;
					int profileSearchSelection = scan.nextInt();
					end = false;
					do {
						switch (profileSearchSelection) {
						case 1:
							System.out.println();
							System.out.println("[ ----- Coding bang Fraternity ----- ]");
							System.out.println("[ -----      Profile search    ----- ]");
							System.out.println("[ -----        New search      ----- ]");
							System.out.print("[ Select a user: ");
							
							String newSearchUser = scan.next();
							User myUser = new User(newSearchUser, twitter);
							
							System.out.println();
							System.out.println("[ ----- Coding bang Fraternity ----- ]");
							System.out.println("[ -----      Profile search    ----- ]");
							System.out.println("[ -----        New search      ----- ]");
							System.out.println("[ Select what you want about this user:");
							System.out.println("[ (1) Tweets                         ]");
							System.out.println("[ (2) Followers                      ]");
							System.out.println("[ (3) Following                      ]");
							System.out.println("[ (4) Tweets Liked                   ]");
							System.out.print("[ Select a parameter: ");
							int newSearchOption = scan.nextInt();
							if (newSearchOption == 1) {
								myUser.startRequest();
								end = true;
								break;
							}
							if (newSearchOption == 2) {
								myUser.get("Followers");
								System.out.println();
								end = true;
								break;
							}
							if (newSearchOption == 3) {
								myUser.get("Following");
								System.out.println();
								end = true;
								break;
							}
							if (newSearchOption == 4) {
								myUser.getLikes();
								end = true;
								break;
							}
							else {
								System.out.println("Please enter a valid option \n");
								end = true;
								break;
							}
						case 2:
							end = true;
							System.out.println();
							break profileSearch;
						case 3:
							System.out.print("Exiting Program...");
							db.close();
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
				db.reinit();
				System.out.println("Reset over !");
				System.out.println("Press any key to go back...");
				System.in.read();
			}

			/* ----------------- Credits ----------------- */

			if (choice == 5) {
				System.out.println("App made with love by Clémence MOULIN, Kilian CUNY, Quentin PAYET & Guillaume HABEN");
				System.out.println("Press any key to go back...");
				System.in.read();
			}

		} while (true);
	}
}