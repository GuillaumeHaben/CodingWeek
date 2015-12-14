import java.util.Scanner;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class main {
	
	private ConfigurationBuilder cb;
	private TwitterFactory tf;
	private Twitter twitter;
	
	public main(){
		cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey("eGS1wmKed0vp7lfhOK4KH5AH3")
		  .setOAuthConsumerSecret("KlF5VJOvC0gQWBPhnMBgQmUu6irX3HLOjdnbN0XSPzTenC4MNO")
		  .setOAuthAccessToken("728437002-umsMNJXthkjEN9y0IfQfaxD7iNHwCPrE1I6LbqQJ")
		  .setOAuthAccessTokenSecret("5RZptLSdXsieaWZ0YmZ6SuJEGXxQVUka6EsUySCc7JfwF");
		tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();
	}
	
	public static void main(String[] args) {
		
		main main_instance = new main();		
		
		/* --------------------- Menu --------------------- */

		Scanner scan = new Scanner(System.in);

		System.out.println("[ ----- Coding bang Fraternity ----- ]");
		System.out.println("[ (1) Simple search                  ]");
		System.out.println("[ (2) Profile search                 ]");
		System.out.println("[ (3) Exit                           ]\n");
		System.out.print("Selection: ");

		int globalSelection = scan.nextInt();
		boolean end = false;
		int choice = 0; 
		do {
			switch (globalSelection) {
			case 1:
				choice = 1;
				end = true;
				break;

			case 2:
				choice = 2;
				end = true;
				//scan.nextLine();
				break;

			case 3:
				System.out.print("Exiting Program...");
				scan.close();
				System.exit(0);

			default:
				System.out.print("Enter a valid menu: ");
				globalSelection = scan.nextInt();
				break;
			};
		} while (!end);
		
		/* ----------------- Simple Search ----------------- */
		if (choice == 1) {
			System.out.println("[ ----- Coding bang Fraternity ----- ]");
			System.out.println("[ -----      Simple search     ----- ]");
			System.out.println("[ (1) ...                            ]");
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
					break;
				case 3:
					System.out.print("Exiting Program...");
					scan.close();
					System.exit(0);
					break;
				default:
					System.out.print("Enter a valid menu: ");
					simpleSearchSelection = scan.nextInt();
					break;
				};
			} while (!end);
		}

		/* ----------------- Profile Search ----------------- */
		if (choice == 2) {
			System.out.println("[ ----- Coding bang Fraternity ----- ]");
			System.out.println("[ -----      Profile search    ----- ]");
			System.out.println("[ (1) ...                            ]");
			System.out.println("[ (2) ...                            ]");
			System.out.println("[ (3) Exit                           ]\n");
			System.out.print("Selection: ");

			int profileSearchSelection = scan.nextInt();
			end = false;
			do {
				switch (profileSearchSelection) {
				case 1:
					end = true;
					break;
				case 2:
					end = true;
					break;
				case 3:
					System.out.print("Exiting Program...");
					scan.close();
					System.exit(0);
					break;
				default:
					System.out.print("Enter a valid menu: ");
					profileSearchSelection = scan.nextInt();
					break;
				};
			} while (!end);
		}
	}
}