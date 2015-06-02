package libraryOOP;
 
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
 
 
class User {
 
	private String nick;
	private String type;
	private LocalDate dateOfLoan;
	private int punishTime;
	 
	public User(String nick, String type, LocalDate dateOfLoan, int punishTime) {
		this.nick = nick;
		this.type = type;
		this.dateOfLoan = dateOfLoan;
		this.punishTime = punishTime;
	}
	 
	public User(String csv){
		String[] values = csv.split(",");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
 
		nick = values[0];
		type = values[1];
		
		dateOfLoan = LocalDate.parse(values[2], formatter);
		punishTime = Integer.parseInt(values[3]);
	}
 
	public String getNick(){
		return nick;
	}
 
	public String getType(){
		return type;
	}
	 
	public LocalDate getDateOfLoan () {
		return dateOfLoan;
	}
	 
	public int getPunishTime() {
		return punishTime;
	}
	 
	public String toString() {
		return "Nick: " + nick + "\nType: " + type + "\nLoan: " + dateOfLoan + "\nSuspension:" + punishTime;
	}

	public String toStringCSV() {
		return nick + "," + type + "," + dateOfLoan + "," + punishTime + "\n";
	}
	
	public void returnBook(LocalDate date, int suspension) {
		dateOfLoan = date;
		punishTime = suspension;
	}
 
}
 
public class RegisterOfUsers {
	List<User> users;
	private BufferedReader in;
  
	public RegisterOfUsers(String userfile) {
		users = new ArrayList<User>();
		try {
			in = new BufferedReader (new FileReader(userfile));
			String csv;
			while ((csv = in.readLine()) != null) {
				users.add(new User(csv));
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("File " + userfile + " was not found!");
		}
		catch(IOException e) {
			System.out.println("Error reading the file!");
		}
	}

	public static void addNewUser(String nick, String type, String dateOfLoan, String punishTime, String userfile) throws IOException {
		
		BufferedWriter out = new BufferedWriter(new FileWriter(userfile, true));
		out.write(nick + "," + type  + ","  + dateOfLoan + "," + punishTime + "\n");
		out.close();
	}

	public static String getUserType(String userfile, String user) {
		RegisterOfUsers ru = new RegisterOfUsers (userfile);
		int i;
		User aux;
		String type = "";

		for (i=0; i<ru.users.size(); i++) {
			aux = ru.users.get(i);
			if (aux.getNick().equals(user)){
				type = aux.getType();
				break;
			}
		}

		return type;
	}

	public static boolean loanBook (String userfile, String user, String book, LocalDate date) {
		RegisterOfUsers ru = new RegisterOfUsers (userfile);
		int i;
		User aux;
		boolean flag = false;

		for (i=0; i<ru.users.size(); i++) {
			aux = ru.users.get(i);
			if (aux.getNick().equals(user) && date.isAfter(aux.getDateOfLoan().plusDays(aux.getPunishTime()))) {
				flag = true;
				break;
			}
		}
		
		if (!flag) 
			System.out.println("User not found or in period of suspension");

		return flag;
	} 

	public static boolean returnBook(String userfile, String user, LocalDate date, int suspension) throws IOException {
		RegisterOfUsers ru = new RegisterOfUsers (userfile);
		int i;
		User aux;
		boolean flag = false;
		
		for (i=0; i<ru.users.size(); i++) {
			aux = ru.users.get(i);
			if (aux.getNick().equals(user)) {
				flag = true;
				aux.returnBook(date, suspension);
				break;
			}
		}
		
			
		if (flag) {
			BufferedWriter out = new BufferedWriter(new FileWriter(userfile, false));
			for (i=0; i<ru.users.size(); i++) {
				aux = ru.users.get(i);
				out.write(aux.toStringCSV());
			}
			out.close();
		}
		
		return flag;
	}
}