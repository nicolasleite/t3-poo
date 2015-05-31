package libraryOOP;
 
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.*;
 
 
class User {
 
	private String nick;
	private String type;
	private LocalDateTime dateOfLoan;
	private int punishTime;
	 
	public User(String nick, String type, LocalDateTime dateOfLoan, int punishTime) {
		this.nick = nick;
		this.type = type;
		this.dateOfLoan = dateOfLoan;
		this.punishTime = punishTime;
	}
	 
	public User(String csv){
		String[] values = csv.split(",");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd'/'MM'/'yy");
 
		nick = values[0];
		type = values[1];
		dateOfLoan = LocalDateTime.parse(values[2], formatter);
		punishTime = Integer.parseInt(values[3]);
	}
 
	public String getNick(){
		return nick;
	}
 
	public String getType(){
		return type;
	}
	 
	public LocalDateTime getDateOfLoan () {
		return dateOfLoan;
	}
	 
	public int getPunishTime() {
		return punishTime;
	}
	 
	public String toString() {
		return "Nick: " + nick + "\nTipo: " + type + "\nEmprйstimo: " + dateOfLoan + "\n:Suspenзгo:" + punishTime;
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

	public void addNewUser(String nick, String type, String dateOfLoan, String punishTime, String userfile) throws IOException {
		
		BufferedWriter out = new BufferedWriter(new FileWriter(userfile, true));
		out.write(nick + "," + type  + ","  + dateOfLoan + "," + punishTime + "\n");
		out.close();
	}

	public String getUserType(String userfile, String user) {
		ru = new RegisterOfUsers (userfile);

		ru.users
			.stream ()
			.filter (s -> s.getNick().equals(user))
			.ifPresent (s -> return s.getType());
		return "";
	}

	public boolean loanBook (String userfile, String user, String book, LocalDateTime date) {
		ru = new RegisterOfUsers (userfile);

		ru.users
			.stream ()
			.filter (s -> s.getNick().equals(user))
			.ifNotPresent (return false)
			.filter (s -> date.isAfter(s.getDateOfLoan.plusDays(s.getPunishTime())))
			.ifNotPresent (return false);
		return true;
	} 
/*
static addNewUser
public static void main(String[] args) throws IOException, FileNotFoundException {
	//String usefile = "users.csv";
	RegisterOfUsers.addNewUser("NickTest", "TypeTest", "users.csv");
}
*/
}