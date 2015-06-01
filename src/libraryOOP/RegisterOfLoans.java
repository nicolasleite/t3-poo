package libraryOOP;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
 
class Loan {
	private String userName;
	private String bookName;
	boolean isReturned;
	private LocalDateTime dateOfLoan;
	private LocalDateTime limitDateOfReturning;
 
	public Loan (String csv) {
		String[] values = csv.split(",");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd'/'MM'/'yy");
 
		userName = values[0];
		bookName = values[1];
		isReturned = Boolean.parseBoolean(values[2]);
		dateOfLoan = LocalDateTime.parse(values[3], formatter);
		limitDateOfReturning = LocalDateTime.parse(values[4], formatter);
	}
 
	public Loan (String userName, String bookName, boolean isReturned, LocalDateTime dateOfLoan, LocalDateTime limitDateOfReturning) {
		this.userName = userName;
		this.bookName = bookName;
		this.isReturned = isReturned; 
		this.dateOfLoan = dateOfLoan;
		this.limitDateOfReturning = limitDateOfReturning; 
	}
 
	public String getUserName () {
		return userName;
	}
 
	public String getBookName () {
		return bookName;
	}
 
	public Boolean isReturned () {
		return isReturned;
	}
 
	public LocalDateTime getDateOfLoan () {
		return dateOfLoan;
	}
 
	public LocalDateTime getLimitDate () {
		return limitDateOfReturning;
	}
 
	public String toString () {
		return "User: " + userName + "\nBook code: " + bookName + "\nDate of loan: " + dateOfLoan + "\nLimit date of returning the book: " + limitDateOfReturning + "\n";
	}
 
	public String toStringCSV () {
		return userName + "," + bookName + "," + dateOfLoan + "," + limitDateOfReturning;
	}
}
 
public class RegisterOfLoans {
	List<Loan> loans;
	private BufferedReader in;
 
	//construtor with name of file as argument
	public RegisterOfLoans(String loanFile) {
		loans = new ArrayList<Loan>();
		try {
			in = new BufferedReader(new FileReader(loanFile));
			String csv;
			while ((csv = in.readLine()) != null) {
				loans.add(new Loan(csv));
			}
		} catch (FileNotFoundException e) {
			System.out.println("File " + loanFile + " was not found!");
		} catch (IOException e) {
			System.out.println("Error reading the file!");
		}
	}

	public static boolean loanBook (String loanfile, String user, String book, LocalDateTime date, String typeOfUser) throws IOException {
		RegisterOfLoans rl = new RegisterOfLoans(loanfile);
		int daysToReturn = 0, i, lateLoans=0, loanedBooks=0;
		Loan aux;
		boolean rtn = false;
		
		for (i=0; i<rl.loans.size(); i++) {
			aux = rl.loans.get(i);
			if (aux.getUserName() == user && !(aux.isReturned())) {
				loanedBooks++;
				if (date.isAfter(aux.getLimitDate())) 
					lateLoans++;
			} 
		}

		switch (typeOfUser) {
			case "Community":
				daysToReturn = 15;
				break;
			case "Student":
				daysToReturn = 30;
				break;
			case "Teacher":
				daysToReturn = 60;
				break;
		}
 
		LocalDateTime returnDate = date.plusDays(daysToReturn);
 
		BufferedWriter out = new BufferedWriter(new FileWriter(loanfile, true));
			 
		out.write (user + "," + book + "," + date + "," + returnDate); 
		
		out.close();
		
		return rtn;
	}
}