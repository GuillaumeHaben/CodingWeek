import java.util.Scanner;

public class main {
	public static void main(String[] args) {
		
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