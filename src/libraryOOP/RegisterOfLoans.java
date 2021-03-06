package libraryOOP;

import java.util.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
 
class Loan {
	private String userName;
	private String bookName;
	boolean isReturned;
	private LocalDate dateOfLoan;
	private LocalDate limitDateOfReturning;
	
	public Loan (String csv) {
		String[] values = csv.split(",");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
 
		userName = values[0];
		bookName = values[1];
		isReturned = Boolean.parseBoolean(values[2]);
		dateOfLoan = LocalDate.parse(values[3], formatter);
		limitDateOfReturning = LocalDate.parse(values[4], formatter);
	}
 
	public Loan (String userName, String bookName, boolean isReturned, LocalDate dateOfLoan, LocalDate limitDateOfReturning) {
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
 
	public LocalDate getDateOfLoan () {
		return dateOfLoan;
	}
 
	public LocalDate getLimitDate () {
		return limitDateOfReturning;
	}
 
	public String toString () {
		return "User: " + userName + "\nBook code: " + bookName + "\nDate of loan: " + dateOfLoan + "\nLimit date of returning the book: " + limitDateOfReturning + "\n";
	}
 
	public String toStringCSV () {
		return userName + "," + bookName + "," + isReturned + "," + dateOfLoan + "," + limitDateOfReturning;
	}

	public void returnBook() {
		isReturned = true;		
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
				if (csv!=null && csv!="") loans.add(new Loan(csv));
			}
		} catch (FileNotFoundException e) {
			System.out.println("File " + loanFile + " was not found!");
		} catch (IOException e) {
			System.out.println("Error reading the file!");
		}
	}

	public static boolean loanBook (String loanfile, String user, String book, LocalDate date, String typeOfUser) throws IOException {
		RegisterOfLoans rl = new RegisterOfLoans(loanfile);
		int daysToReturn = 0, i, lateLoans=0, loanedBooks=0, loanLimit = 0;
		Loan aux;
		
		for (i=0; i<rl.loans.size(); i++) {
			aux = rl.loans.get(i);
			if (aux.getUserName().equals(user) && !(aux.isReturned())) {
				loanedBooks++;
				if (date.isAfter(aux.getLimitDate())) 
					lateLoans++;
			} 
		}
		
		if (typeOfUser.equals("Community")){
			daysToReturn = 15;
			loanLimit = 2;
		} else if (typeOfUser.equals("Student")){
			daysToReturn = 30;
			loanLimit = 4;
		} else if (typeOfUser.equals("Teacher")){
			daysToReturn = 60;
			loanLimit = 6;
		}
		
		if (lateLoans > 0) {
			System.out.println ("User has" + lateLoans + "late loans, can't loan book until return");
			return false;
		}
		if (loanedBooks >= loanLimit) {
			System.out.println("User has reached the limit of " + loanLimit + " loaned books, cannot loan more books");
			return false;
		}

		LocalDate returnDate = date.plusDays(daysToReturn); 
		
		BufferedWriter out = new BufferedWriter(new FileWriter(loanfile, true));
		out.write (user + "," + book + ",false," + date + "," + returnDate + "\n"); 
		out.close();
		
		
		return true;
	}

	public static boolean returnBook(String loanfile, String user, String book, LocalDate date) throws IOException {
		RegisterOfLoans rl = new RegisterOfLoans(loanfile);
		Loan aux;
		boolean flag=false;
		
		for (int i = 0; i<rl.loans.size(); i++) {
			aux = rl.loans.get(i);
			if (aux.getUserName().equals(user) && aux.getBookName().equals(book) && !(aux.isReturned())) {
				aux.returnBook();
				flag = true;
				break;
			} 
		}
		
		if (!flag) {
			System.out.println ("Loan not found or book has already been returned");
		} else {
			BufferedWriter out = new BufferedWriter(new FileWriter(loanfile, false));
			for (int i = 0; i<rl.loans.size(); i++){
				aux = rl.loans.get(i);
				out.write (aux.toStringCSV() + "\n"); 
			}
			out.close();
		}
		
		return flag;
	}

	public static int calcSuspension(String loanfile, String user, String book, LocalDate date) {
		RegisterOfLoans rl = new RegisterOfLoans(loanfile);
		Loan aux, last=null;
		int susp = -1, i;
		
		for (i=0; i<rl.loans.size(); i++) {
			aux = rl.loans.get(i);
			if (aux.getUserName().equals(user) && aux.getBookName().equals(book) && aux.getLimitDate().isBefore(date)) {
				last = aux;
			} 
		}
		
		if (last != null) {
			susp = (int) ChronoUnit.DAYS.between(last.getLimitDate(), date);
		}
		
		return susp;
	}
}