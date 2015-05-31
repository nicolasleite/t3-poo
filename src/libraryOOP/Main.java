package libraryOOP;

public class Main {
	private void printMenu() {
		System.out.println ("************Library OOP************\n");
		System.out.println ("List of commands:");
		System.out.println ("add user \t - Registers new user");
		System.out.println ("add book \t - Registers new book");
		System.out.println ("list books \t - List registred books");
		System.out.println ("list available books \t - List available books");
		System.out.println ("search book -n \t - Search book by name");
		System.out.println ("search book -c \t - Search book by code")
		System.out.println ("loan book \t - Loan book");
		System.out.println ("return book \n - Return loaned book");
		System.out.println ("exit \n - Finish execution\n");
		System.out.print ("Command: ");
	}

	public static void main(String[] args) {
		String cmd="", aux;
		Scanner s = new Scanner (System.in);
		Library lib = new Library ();

		while (cmd != "exit") {
			printMenu();
			cmd = s.readLine();

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
					aux = s.readLine();
					lib.searchBook ("N", aux);
					break;
				
				case "search book -c":
					System.out.print ("\nCode: ");
					aux = s.readLine();
					lib.searchBook ("C", aux);
					break;
				
				case "loan book":
					lib.loanBook();
					break;

				case "return book":
					lib.returnBook();
					break;

				default:
					System.out.println("Invalid command!!");
					break;
			}
		} 
	}
}