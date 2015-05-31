package libraryOOP;
 
class Loan {
    private String userName;
    private String bookName;
    private boolean isReturned;
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
 
    public toString () {
        return "User: " + userName + "\nBook code: " + bookName + "\nDate of loan: " + dateOfLoan + "\nLimit date of returning the book: " + limitDateOfReturning + "\n";
    }
 
    public toStringCSV () {
        return userName + "," + bookName + "," + dateOfLoan + "," + limitDateOfReturning;
    }
}
 
public class RegisterOfLoans {
    List<Loan> loans;
 
    //construtor with name of file as argument
    public RegisterOfLoans(String loanFile) {
        loans = new ArrayList<Loan>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(loanFile));
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
 
 
 
    //não ta funcionando ainda
    public void loanBook (String loanfile, String user, String book, LocalDateTime date, String typeOfUser) {
        RegisterOfLoans rl = new RegisterOfLoans(loanfile);
        int daysToReturn, i, lateLoans=0, loanedBooks=0;
        Book aux;
 
        for (i=0; i<rl.loans.size(); i++) {
            aux = rl.loans.get(i);
            if (aux.getUserName() == user && !(aux.isReturned)) {
                loanedBooks++;
                if (date.isAfter(aux.getLimitDate)) 
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
 
        LocalDateTime returnDate = LocalDateTime.plusDays(daysToReturn);
 
        BufferedWritter out = new BufferedWritter(new FileWritter(loanfile, true));
         
        out.write (user + "," + book + "," + date + "," + returnDate);  
    }
}