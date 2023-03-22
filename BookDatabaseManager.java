package ca.nl.cna.java3.a1help;

import java.net.ConnectException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * This class contains all the Database queries and all the methods to retrieve what you need
 *
 * THIS IS THE MOST IMPORTANT PART OF THE ASSIGNMENT
 *
 * @author Josh
 */
public class BookDatabaseManager {


    /**
     * This method will load the library from the database
     * @return - the library
     * @throws SQLException - catch any errors with the database
     */
    public static Library loadLibrary() throws SQLException, ConnectException {
        Library library = new Library();
        Connection connection = DBConfiguration.getBookDBConnection();
        library.setBookList(loadBookList(connection));
        library.setAuthorList(loadAuthorList(connection));

        connectBooksAndAuthors(connection, library.getBookList(), library.getAuthorList());

        return library;
    }

    /**
     * This method will load the book list from the database
     * @param connection - connection to the database
     * @return - list of books
     */
    static List<Book> loadBookList(Connection connection) {
        //TODO Write this method
        LinkedList<Book> bookLinkedList = new LinkedList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM " + DBConfiguration.DB_BOOKS;
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                bookLinkedList.add(new Book(
                        resultSet.getString(DBConfiguration.DB_BOOKS_TITLES_ISBN),
                        resultSet.getString(DBConfiguration.DB_BOOKS_TITLES_TITLE),
                        resultSet.getInt(DBConfiguration.DB_BOOKS_TITLES_EDITION_NUMBER),
                        resultSet.getString(DBConfiguration.DB_BOOKS_TITLES_COPYRIGHT)
                ));
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookLinkedList;
    }

    /**
     * This method will load the author list from the database
     * @param connection - connection to the database
     * @return - list of authors
     */
    static List<Author> loadAuthorList(Connection connection) {
            //TODO Write this method
        LinkedList<Author> authorLinkedList = new LinkedList<>();
        try{
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM " + DBConfiguration.DB_BOOKS_AUTHORS;
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                authorLinkedList.add(new Author(
                        resultSet.getInt(DBConfiguration.DB_BOOKS_AUTHORS_ID),
                        resultSet.getString(DBConfiguration.DB_BOOKS_AUTHORS_FIRST_NAME),
                        resultSet.getString(DBConfiguration.DB_BOOKS_AUTHORS_LAST_NAME)
                ));
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authorLinkedList;
}

    /**
     * This method will connect the books and authors by ISBNs and authorIDs
     * @param connection - connection to the database
     * @param bookList - list of books
     * @param authorList - list of authors
     * @throws SQLException - catch any errors with the database
     */
    private static void connectBooksAndAuthors(Connection connection, List<Book> bookList, List<Author> authorList){
        //TODO write this method
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM " + DBConfiguration.DB_BOOKS_AUTHORS_BOOKS_BRIDGE;
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                int authorID = resultSet.getInt(DBConfiguration.DB_BOOKS_AUTHORS_ID);
                String isbn = resultSet.getString(DBConfiguration.DB_BOOKS_TITLES_ISBN);

                Author author = authorList.stream().filter(a -> a.getAuthorID() == authorID).findAny().get();
                Book book = bookList.stream().filter(b -> b.getIsbn().equals(isbn)).findAny().get();

                author.addBook(book);
                book.addAuthor(author);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method will add a book to an author already in the database
     * @param connection - connection to the database
     * @param book - book to add to author
     * @param author - author of the book
     */
    public static void addBookToAuthor(Connection connection, Book book, Author author){
        try {
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO " + DBConfiguration.DB_BOOKS + " VALUES ('" + book.getIsbn() + "', '" + book.getTitle() + "', " + book.getEditionNumber() + ", '" + book.getCopyright() + "')";
            String sql2 = "INSERT INTO " + DBConfiguration.DB_BOOKS_AUTHORS_BOOKS_BRIDGE + " VALUES (" + author.getAuthorID() + ", '" + book.getIsbn() + "')";
            statement.executeUpdate(sql);
            statement.executeUpdate(sql2);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * This method will add an author to a book already in the database
     * @param connection - connection to the database
     * @param book - book to add author to
     * @param author - author to add to book
     */
    public static void addAuthorToBook(Connection connection, Book book, Author author){
        try {
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO " + DBConfiguration.DB_BOOKS_AUTHORS + " VALUES (" + author.getAuthorID() + ", '" + author.getFirstName() + "', '" + author.getLastName() + "')";
            String sql2 = "INSERT INTO " + DBConfiguration.DB_BOOKS_AUTHORS_BOOKS_BRIDGE + " VALUES (" + author.getAuthorID() + ", '" + book.getIsbn() + "')";
            statement.executeUpdate(sql);
            statement.executeUpdate(sql2);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
