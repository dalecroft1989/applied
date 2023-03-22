package ca.nl.cna.java3.a1help;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import static ca.nl.cna.java3.a1help.BookDatabaseManager.*;


/**
 * The purpose of this class is to run and maintain the book and authors
 * while the application is running
 *
 * @author Josh
 */
public class BookApplication {

    /**
     * This method will display the menu
     * method will show the books or authors
     * method will add a book or author
     */
    public static void main(String[] args) throws SQLException, ConnectException {

        Connection connection = DBConfiguration.getBookDBConnection();

        Library lib = BookDatabaseManager.loadLibrary();

        String input1 = "";
        Scanner scanner = new Scanner(System.in);

        while (!input1.equals("0")) {

            displayMenu();
            System.out.println("Enter Option");
            String input = scanner.nextLine();

            if (input.equals("0")) {
                System.out.println("Exiting Program");
                input1 = "0";
            }

            else if (input.equals("1")) {
                lib.getBookList().forEach(book -> Book.printBook(System.out, book));
            }

            else if (input.equals("2")) {
                lib.getAuthorList().forEach(author -> Author.printAuthor(System.out, author));
            }

            else if (input.equals("3")) {
                Scanner scanner2 = new Scanner(System.in);
                System.out.println("Enter ISBN");
                String isbn = scanner2.nextLine();
                System.out.println("Enter Title");
                String title = scanner2.nextLine();
                System.out.println("Enter Edition Number");
                int editionNumber = Integer.parseInt(scanner2.nextLine());
                System.out.println("Enter Copyright");
                String copyright = scanner2.nextLine();

                Book book1 = new Book(isbn, title, editionNumber, copyright);

                System.out.println("Enter AuthorID");
                String authorID = scanner2.nextLine();

                for (Author author : lib.getAuthorList()) {
                    if (author.getAuthorID() == Integer.parseInt(authorID)) {
                        author.addBook(book1);
                        addBookToAuthor(connection, book1, author);
                    }
                }
                lib.setBookList(loadBookList(connection));
            }

            else if (input.equals("4")) {
                Scanner scanner2 = new Scanner(System.in);
                System.out.println("Enter AuthorID");
                int authorID = Integer.parseInt(scanner2.nextLine());
                System.out.println("Enter First Name");
                String firstName = scanner2.nextLine();
                System.out.println("Enter Last Name");
                String lastName = scanner2.nextLine();

                Author author1 = new Author(authorID, firstName, lastName);

                System.out.println("Enter BookISBN");
                String bookID = scanner2.nextLine();

                for (Book book : lib.getBookList()) {
                    if (book.getIsbn().equals(bookID)) {
                        book.addAuthor(author1);
                        addAuthorToBook(connection, book, author1);
                    }
                }
                lib.setAuthorList(loadAuthorList(connection));
            } else {
                System.out.println("Invalid Input");
            }
        }
    }

    private static void displayMenu(){
        System.out.println("\n\nBOOK APPLICATION\n=============================");
        System.out.println("1.........DISPLAY BOOKS\n" +
                "2.........DISPLAY AUTHORS\n" +
                "3.........ADD BOOK TO AUTHOR\n" +
                "4.........ADD AUTHOR TO BOOK\n" +
                "0.........EXIT\n");
    }
}

