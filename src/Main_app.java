/**
 * This class launch the program
 * @author The Coding Bang Fraternity
 * @version 2.0
 */

import java.io.IOException;
import java.util.Scanner;

import controller.Database;
import controller.Date;
import controller.KeyWord;
import controller.Language;
import controller.User;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class Main_app {

	private static Twitter twitter;
	private static Database db;

	public Main_app() {
		ConfigurationBuilder contractor = new ConfigurationBuilder();
		contractor.setOAuthConsumerKey("qz06S2cROTQm1KYmuyNxFTEcr");
		contractor.setOAuthConsumerSecret("ki0GG0aNeU7hKziJpOEAk59saSXx7iggg64Bwp0vVorLJI2B7r");
		Configuration configuration = contractor.build();
		TwitterFactory factory = new TwitterFactory(configuration);
		twitter = factory.getInstance();
		twitter.setOAuthAccessToken(new AccessToken("728437002-9mx6LMYTKfIkD0TVnEbv3KwJJXMNdqsVsPe0HWem", "NTFxjn6DKy5ontWdKfTPlklXwQZmYyCvgOZXstBjFBN6I"));
		
		//twitter = TwitterFactory.getSingleton();
		//twitter.setOAuthConsumer("qz06S2cROTQm1KYmuyNxFTEcr", "ki0GG0aNeU7hKziJpOEAk59saSXx7iggg64Bwp0vVorLJI2B7r");
		
		
		db = new Database();
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		Main_app main_instance = new Main_app();
		Scanner scan = new Scanner(System.in);


		/* --------------------- Global Menu --------------------- */

		Menu: do {

			System.out.println("[ ----- Coding bang Fraternity ----- ]");
			System.out.println("[ (1) Simple search                  ]");
			System.out.println("[ (2) Profile search                 ]");
			System.out.println("[ (3) Reset searches                 ]");
			System.out.println("[ (4) Credits                        ]");
			System.out.println("[ (5) Exit                           ]\n");
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
					// Reset Searches
					choice = 3;
					end = true;
					break;

				case 4:
					// Credits
					choice = 4;
					end = true;
					break;

				case 5:
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
			try {
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
								System.out.println("[ (1) By Keyword                     ]");
								System.out.println("[ (2) By Date                        ]");
								System.out.println("[ (3) By Language                    ]");
								System.out.println("[ (4) By Location                    ]");
								System.out.println("[ (5) With more than 1 parameter     ]");
								System.out.print("[ Select a parameter: ");
								int newSearchParam = scan.nextInt();
								if (newSearchParam >= 1 && newSearchParam <= 5) {
									searchChoice = newSearchParam;
									end = true;
								}
								else System.out.println("Please enter a valid option \n");
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
							};
						} while (!end);
						if (searchChoice == 1) {
							System.out.println();
							System.out.println("[ ----- Coding bang Fraternity ----- ]");
							System.out.println("[ -----      Simple search     ----- ]");
							System.out.println("[ -----        New search      ----- ]");
							System.out.print("[ KeyWord: ");
							String searchByKeyword = scan.next();
							KeyWord myKeyword = new KeyWord(searchByKeyword, twitter);
							myKeyword.startRequest();
							System.out.println("\nPress any key to continue...");
							System.in.read();
							
						}
						if (searchChoice == 2) {
							System.out.println();
							System.out.println("[ ----- Coding bang Fraternity ----- ]");
							System.out.println("[ -----      Simple s1earch     ----- ]");
							System.out.println("[ -----        New search      ----- ]");
							System.out.print("[ Date (YYYY-MM-DD): ");
							String searchByDate = scan.next();
							System.out.print("[ Keyword: ");
							String searchByKeyword = scan.next();
							Date myDate = new Date(searchByKeyword, searchByDate, twitter);
							myDate.startRequest();
							System.out.println("\nPress any key to continue...");
							System.in.read();
						}
						if (searchChoice == 3) {
							System.out.println();
							System.out.println("[ ----- Coding bang Fraternity ----- ]");
							System.out.println("[ -----      Simple search     ----- ]");
							System.out.println("[ -----        New search      ----- ]");
							System.out.print("[ Language: ");
							String searchByLanguage = scan.next();
							System.out.print("[ Keyword: ");
							String searchByKeyword = scan.next();
							Language myLanguage = new Language(searchByKeyword, searchByLanguage, twitter);
							myLanguage.startRequest();
							System.out.println("\nPress any key to continue...");
							System.in.read();
						}
						if (searchChoice == 4) {
							System.out.println();
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
							System.out.println();
							System.out.println("[ ----- Coming Soon ----- ]");
						}
					} while (true);
				}
			} catch (IOException e) {
				e.printStackTrace();
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
							myUser:do {
								System.out.println();
								System.out.println("[ ----- Coding bang Fraternity ----- ]");
								System.out.println("[ -----      Profile search    ----- ]");
								System.out.println("[ -----        New search      ----- ]");
								System.out.println("[ Select what you want about @"+ myUser.getScreen_name() +":");
								System.out.println("[ (1) Tweets                         ]");
								System.out.println("[ (2) Followers                      ]");
								System.out.println("[ (3) Following                      ]");
								System.out.println("[ (4) Tweets Liked                   ]");
								System.out.println("[ (5) Description                    ]");
								System.out.println("[ (6) Change User                    ]");
								System.out.print("[ Select a parameter: ");
								int newSearchOption = scan.nextInt();
								
								switch(newSearchOption) {
								case 1:
									myUser.startRequest();
									break;
									
								case 2:
									myUser.get("Followers");
									System.out.println();
									break;
									
								case 3:
									myUser.get("Following");
									System.out.println();
									break;
									
								case 4:
									myUser.getLikes();
									break;
									
								case 5:
									myUser.getInformation();
									break;
									
								case 6:
									profileSearchSelection = 1;
									break myUser;
								
								default:
									System.out.println("Please enter a valid option \n");
									break;
								}
							} while(true);
							break;
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
					} while (!end);
				} while (true);
			}

			/* ----------------- Reset Searches ----------------- */
			try {
				if (choice == 3) {
					db.reinit();
					System.out.println("Reset over !");
					System.out.println("Press any key to go back...");
					System.in.read();
				}
	
				/* ----------------- Credits ----------------- */
	
				if (choice == 4) {
					System.out.println("App made with love by Clémence MOULIN, Kilian CUNY, Quentin PAYET & Guillaume HABEN");
					System.out.println("Press any key to go back...");
					System.in.read();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} while (true);
	}
}