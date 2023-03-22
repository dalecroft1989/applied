package ca.nl.cna.java3.a1help;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

public class Book {

    //TODO Write this class - see lecture Jan 25th if you are stuck
    private String isbn;
    private String title;
    private int editionNumber;
    private String copyright;
    private List<Author> authorList;

    /**
     * Constructor for Book
     * @param isbn - unique ID for book
     * @param title - title of book
     * @param editionNumber - edition number of book
     * @param copyright - copyright of book
     */
    public Book(String isbn, String title, int editionNumber, String copyright) {
        this.isbn = isbn;
        this.title = title;
        this.editionNumber = editionNumber;
        this.copyright = copyright;
        authorList = new LinkedList<>();
    }

    /**
     * GETTERS AND SETTERS
     */
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getEditionNumber() {
        return editionNumber;
    }

    public void setEditionNumber(int editionNymber) {
        this.editionNumber = editionNymber;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public List<Author> getAuthorList() {
        return authorList;
    }

    public void addAuthor(Author author){
        this.authorList.add(author);
    }

    /**
     * Print a book to a PrintStream
     * @param printStream - PrintStream to print to
     * @param book - Book to print
     */
    public static void printBook(PrintStream printStream, Book book){
        printStream.printf("\n%s, %s, %d, %s",
                book.getIsbn(), book.getTitle(), book.getEditionNumber(), book.getCopyright());
        book.authorList.forEach(author -> System.out.printf("\n\t%s, %s",
                author.getFirstName(), author.getLastName()));
    }
}
