package libraryOOP;
   
import java.util.*;
import java.io.*;
    
class Book {
    private String name;
    private String code;
    private String type;
    private String availability;
    
    public Book(String name, String code, String type, String availability) {
        this.name = name;
        this.code = code;
        this.type = type;
        this.availability = availability;
    }
    
    public Book(String csv) {
        String[] values = csv.split(",");
        name = values[0];
        code = values[1];
        type = values[2];
        availability = values[3];
    }
    
    public String getName(){
        return name;
    }
    
    public String getCode(){
        return code;
    }
    
    public String getType(){
        return type;
    }
    
    public String getAvailability(){
        return availability;
    }
    
    public String toString() {
        return "Nome: " + name + "\nCódigo: " + code + "\nTipo: " + type + "\nDisponibilidade: " + availability;
    }
    
    public String toStringCSV() {
        return name + "," + code + "," + type + "," + availability + "\n";
    }
    
    public boolean loanBook(String typeOfUser) {
        if (typeOfUser == "Community" && type == "textbook"){
            System.out.println ("This user doesn't have permission to loan this book!!");
            return true;
        }
        availability = "Unavailable";
        return false;
    }
    
    public void returnBook() {
        availability = "Available";
    }
}
    
public class RegisterOfBooks {
    List<Book> books;
    private BufferedReader in;
    
    public RegisterOfBooks(String bookfile) {
        books = new ArrayList<Book>();
        try {
            in = new BufferedReader (new FileReader(bookfile));
            String csv;
            while ((csv = in.readLine()) != null) {
                books.add(new Book(csv));
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File " + bookfile + " was not found!");
        }
        catch(IOException e) {
            System.out.println("Error reading the file!");
        }
    }
    
    public static void addNewBook(String name, String code, String type, String availability, String bookfile) throws IOException {
          
        //não precisa disso aqui não, tava brisando e vai dar erro  
        //books.add(new Book(name, code, type, availability));
            
        BufferedWriter out = new BufferedWriter(new FileWriter(bookfile, true));
        out.write(name + "," + code + "," + type + "," + availability + "\n");
        out.close();
    }
    
/*  public void removeBook (String bookfile, String code) {
        RegisterOfBooks rb = new RegisterOfBooks (bookfile);
        List<Book> newList =
            rb.books
            .stream()
            .filter(s -> !(s.getCode().equals(code)));
            .collect(Collectors.toList());
    
    }*/
    
    public static void printEverything (String bookfile) {
        RegisterOfBooks rb = new RegisterOfBooks (bookfile);
    
        rb.books
            .stream()
            .sorted()
            .forEach(s -> System.out.print(s.toString() + "\n"));
    }
    
    public static void printAvailableOnly (String bookfile) {
        RegisterOfBooks rb = new RegisterOfBooks (bookfile);
    
        rb.books
            .stream()
            .filter(s -> s.getAvailability().equals("Available"))
            .sorted()
            .forEach(s -> System.out.print(s.toString() + "\n"));
    }
    
    public static void searchBookByName (String bookfile, String name){
        RegisterOfBooks rb = new RegisterOfBooks (bookfile);
        rb.books
            .stream()
            .filter(s -> s.getName().equals(name))
            .sorted()
            .forEach(s -> System.out.print(s.toString() + "\n"));
    }
    
    public static void searchBookByCode (String bookfile, String code){
        RegisterOfBooks rb = new RegisterOfBooks (bookfile);
        rb.books
            .stream()
            .filter(s -> s.getCode().equals(code))
            .sorted()
            .forEach(s -> System.out.print(s.toString() + "\n"));
    }
    
    public boolean loanBook (String bookfile, String name, String typeOfUser) throws IOException {
        RegisterOfBooks rb = new RegisterOfBooks (bookfile);
        int i;
        boolean flag = false;
        Book aux;
  
/*      //false overwrite the file
        BufferedWriter out = new BufferedWriter(new FileWriter(bookfile, false));*/
    
/*      rb.books
            .stream()
            .filter(s -> s.getName().equals(name))
            .findAny()
            .filter(s -> s.getAvailability().equals("Available"))
//Error     .ifPresent(s -> (flag = s.loanBook(typeOfUser)));    */
    
        for (i=0; i<rb.books.size(); i++) {
            aux = rb.books.get(i);
            if (aux.getName() == name && aux.getAvailability() == "Available"){
                flag = aux.loanBook (typeOfUser);
                break;
            }
        }
 
        if (flag) {
            //false overwrite the file
            BufferedWriter out = new BufferedWriter(new FileWriter(bookfile, false));
            for (i=0; i<rb.books.size(); i++) {
                aux = rb.books.get(i);
                out.write(aux.toStringCSV());
            }
            out.close();
        }
 
        return flag;
    }
    
    public String returnBook (String bookfile, String name, String type) throws IOException {
        RegisterOfBooks rb = new RegisterOfBooks (bookfile);
        int i;
        Book aux;
  
        //false overwrite the file
        BufferedWriter out = new BufferedWriter(new FileWriter(bookfile, false));
    
        rb.books
            .stream()
            .filter(s -> s.getName().equals(name))
            .findAny()
            .filter(s -> s.getAvailability().equals("Unavailable"))
            .ifPresent(s -> s.returnBook());
          
        for (i=0; i<rb.books.size(); i++) {
            aux = rb.books.get(i);
            out.write(aux.toStringCSV());
        }
    
        out.close();
         
        String rtn = rb.getType();
        return rtn;
    }
}