package libraryOOP;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Library {
	String bookfile;
	String userfile;
	String loanfile;
	Scanner s;
 
	public Library() {
		bookfile = "bookfile.csv";
		userfile = "userfile.csv";
		loanfile = "loanfile.csv";
		s = new Scanner (System.in);
	}
 
	public void newUser() throws IOException {
		System.out.print ("Name: ");
		String name = s.nextLine();
		System.out.print ("Type: ");
		String type = s.nextLine();
		RegisterOfUsers.addNewUser(name, type, "1971-01-01", "0", userfile);
	}
 
	public void newBook() {
		System.out.print ("Name: ");
		String name = s.nextLine();
		System.out.print ("Code: ");
		String code = s.nextLine();
		System.out.print ("Type: ");
		String type = s.nextLine();
		 
		try {
			RegisterOfBooks.addNewBook (name,code,type,"Available",bookfile);
			System.out.println ("Book added successfuly!!");
		} catch (IOException e) {
			System.out.println ("Failed to add new book!!");
		}
	}
 
	public void seeAllBooks () {
		RegisterOfBooks.printEverything (bookfile);
	}
 
	public void seeAvailableBooks () {
		RegisterOfBooks.printAvailableOnly (bookfile);
	}
 
	public void searchBook (String typeOfSearch, String argument) {
		switch (typeOfSearch) {
			case "N":
				RegisterOfBooks.searchBookByName(bookfile, argument);
				break;
			case "C":
				RegisterOfBooks.searchBookByCode(bookfile, argument);
				break;
			default:
				System.out.println ("Invalid option!! Usage: (-c)ode or (-n)ame!!");
				return;
		}
		System.out.println("Search completed!!");
	}
	
	public void loanBook() {
		System.out.print("User: ");
		String user = s.nextLine();
		System.out.print("Desired book: ");
		String book = s.nextLine();
		System.out.print("Date (dd/mm/yyyy) [press ENTER to use OS time]: ");
		String dateString = s.nextLine();
 
		LocalDate date;
		if(dateString.equals(""))
			date = LocalDate.now();
		else 
			date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
 
		try{    
			//if the user is on period of suspension or doesn't exist loanBook() is stopped 
			if (RegisterOfUsers.loanBook (userfile, user, book, date) == false)
				return;
			
			//returns the type of the user and if has no type stop execution
			String typeOfUser = RegisterOfUsers.getUserType(userfile, user);
						
			//if the user doesn't have permission to loan that type of book or the book doesn't exist, loanBook is stopped
			if (RegisterOfBooks.loanBook(bookfile, book, typeOfUser))
				return;

			//if the user has more loaned books than he's allowed to, loanBook is stopped
			if (RegisterOfLoans.loanBook(loanfile, user, book, date, typeOfUser))
				return;

			System.out.println ("Successful loan!!");
		} catch (IOException e) {
			System.out.println ("Unsuccessful loan!!");
		}
	}

	//not working 
/*	public void returnBook() {
		System.out.print("User: ");
		String user = s.nextLine();
		System.out.print("Book to be returned: ");
		String book = s.nextLine();
 
		try {
			RegisterOfUsers.returnBook(userfile, user);
			RegisterOfBooks.returnBook(bookfile, book);
			RegisterOfLoans.returnBook(loanfile, user, book);
			System.out.println ("Successful return!!");
		} catch (IOException e) {
			System.out.println ("Unsuccessful return!!");
		}
	}*/
}