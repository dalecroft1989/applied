package ca.nl.cna.java3.a1help;

import java.util.LinkedList;
import java.util.List;

public class Library {

    private List<Book> bookList;
    private List<Author> authorList;

    /**
     * Constructor for Library
     */
    public Library(){
        bookList = new LinkedList<>();
        authorList = new LinkedList<>();
    }

    /**
     * GETTERS AND SETTERS
     */
    public List<Book> getBookList() {
        return bookList;
    }
    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }
    public List<Author> getAuthorList() {
        return authorList;
    }
    public void setAuthorList(List<Author> authorList) {
        this.authorList = authorList;
    }
}
