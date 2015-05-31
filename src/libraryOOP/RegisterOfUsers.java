package libraryOOP;

import java.util.*;
import java.io.*;


class User {

	private String nick;
    private String type;

    public User(String nick, String type) {
    	this.nick = nick;
    	this.type = type;
    }
    
    public User(String csv){
    	String[] values = csv.split(",");
    	nick = values[0];
    	type = values[1];
	}

    public String getNick(){
    	return nick;
    }

    public String getType(){
    	return type;
    }

    public String toString() {
    	return "Nick: " + nick + "\nTipo: " + type;
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
public void addNewUser(String nick, String type, String userfile) throws IOException {
   
    BufferedWriter out = new BufferedWriter(new FileWriter(userfile, true));
    out.write(nick + "," + type + "\n");
    out.close();
	}
/*
static addNewUser
public static void main(String[] args) throws IOException, FileNotFoundException {
	//String usefile = "users.csv";
	RegisterOfUsers.addNewUser("NickTest", "TypeTest", "users.csv");
}
*/
}