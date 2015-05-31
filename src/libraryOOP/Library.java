package libraryOOP;
 
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
 
    public void newUser() {
        System.out.print ("Name: ");
        String name = s.readLine();
        System.out.print ("Type: ");
        String type = s.readLine();
    }
 
    public void newBook() {
        System.out.print ("Name: ");
        String name = s.readLine();
        System.out.print ("Code: ");
        String code = s.readLine();
        System.out.print ("Type: ");
        String type = s.readLine();
         
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
                RegisterOfBooks.searchByName(bookfile, argument);
                break;
            case "C":
                RegisterOfBooks.searchByCode(bookfile, argument);
                break;
            default:
                System.out.println ("Invalid option!! Usage: (C)ode or (N)ame!!");
                return;
        }
        System.out.println("Search completed!!");
    }
 
    public void loanBook() {
        System.out.print("User: ");
        String user = s.readLine();
        System.out.print("Desired book: ");
        String book = s.readLine();
        System.out.print("Date (dd/mm/yy) [press ENTER to use OS time]: ");
        String dateString = s.readLine();
 
        LocalDateTime date;
        switch (dateString) {
            case "":
                date = LocalDateTime.now();
                break;
            default:
                date = LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern("dd'/'MM'/'yy"));
                break;
        }
 
        try{    
            RegisterOfUsers.loanBook(userfile, user);
            RegisterOfBooks.loanBook(bookfile, book);
            RegisterOfLoans.loanBook(loanfile, user, book, date);
            System.out.println ("Successful loan!!");
        } catch (IOException e) {
            System.out.println ("Unsuccessful loan!!");
        }
    }
 
    public void returnBook() {
        System.out.print("User: ");
        String user = s.readLine();
        System.out.print("Book to be returned: ");
        String book = s.readLine();
 
        try {
            RegisterOfUsers.returnBook(userfile, user);
            RegisterOfBooks.returnBook(bookfile, book);
            RegisterOfLoans.returnBook(loanfile, user, book);
            System.out.println ("Successful return!!");
        } catch (IOException e) {
            System.out.println ("Unsuccessful return!!");
        }
    }
}