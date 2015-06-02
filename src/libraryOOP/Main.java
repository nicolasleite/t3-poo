package libraryOOP;

import java.io.IOException;
import java.util.Scanner;

public class Main {
	private static Scanner s;

	private static void printMenu() {
		System.out.println ("************Library OOP************\n");
		System.out.println ("List of commands:");
		System.out.println ("add user \t\t - Registers new user");
		System.out.println ("add book \t\t - Registers new book");
		System.out.println ("list books \t\t - List registred books");
		System.out.println ("list available books \t - List available books");
		System.out.println ("search book -n \t\t - Search book by name");
		System.out.println ("search book -c \t\t - Search book by code");
		System.out.println ("loan book \t\t - Loan book");
		System.out.println ("return book \t\t - Return loaned book");
		System.out.println ("exit \t\t\t - Finish execution\n");
		System.out.print ("Command: ");
	}

	public static void main(String[] args) throws IOException {
		String cmd="", aux;
		s = new Scanner (System.in);
		Library lib = new Library ();

		do {
			printMenu();
			cmd = s.nextLine();

			switch (cmd) {
				case "add user":
					lib.newUser();
					break;
				
				case "add book":
					lib.newBook();
					break;
				
				case "list books":
					lib.seeAllBooks();
					break;
				
				case "list available books":
					lib.seeAvailableBooks ();
					break;
				
				case "search book -n":
					System.out.print ("\nName: ");
					aux = s.nextLine();
					lib.searchBook ("N", aux);
					break;
				
				case "search book -c":
					System.out.print ("\nCode: ");
					aux = s.nextLine();
					lib.searchBook ("C", aux);
					break;
				
				case "loan book":
					lib.loanBook();
					break;
				case "exit":
					cmd ="exit";
					break;

				//not working
				/*case "return book":
					lib.returnBook();
					break;*/

				default:
					System.out.println("Invalid command!!");
			}
		}while (cmd != "exit");
		
		System.out.println ("Finished execution");
	}
}