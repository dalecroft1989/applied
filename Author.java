package ca.nl.cna.java3.a1help;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

public class Author {

    //TODO write this class - see lecture Jan 25th if you are stuck
    private int authorID;
    private String firstName;
    private String lastName;
    private List<Book> bookList;

    /**
     * Constructor for Author
     * @param authorID - unique ID for author
     * @param firstName - first name of author
     * @param lastName - last name of author
     */
    public Author(int authorID, String firstName, String lastName) {
        this.authorID = authorID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bookList = new LinkedList<>();
    }

    /**
     * GETTERS AND SETTERS
     */
    public int getAuthorID() {
        return authorID;
    }

    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void addBook(Book book) {
        this.bookList.add(book);
    }

    /**
     * Print the author and their books
     * @param printStream - the stream to print to
     * @param author - the author to print
     */
    public static void printAuthor(PrintStream printStream, Author author) {
        printStream.printf("\n%d, %s, %s",
                author.getAuthorID(), author.getFirstName(), author.getLastName());
        author.bookList.forEach(book -> System.out.printf("\n\t%s, %s, %d, %s",
                book.getIsbn(), book.getTitle(), book.getEditionNumber(), book.getCopyright()));
    }
}
