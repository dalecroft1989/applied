package ca.nl.cna.java3.a1help;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.nimbus.State;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "LibraryData", value = {"/LibraryData"})
    public class LibraryData extends HttpServlet {
        private Connection connection;
        public void init() {
            try {
                connection = DBConnection.initDatabase();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        PrintWriter out = response.getWriter();

        if (name.equals("addBook")) {
            String isbn = request.getParameter("isbn");
            String title = request.getParameter("title");
            String editionNumber = request.getParameter("editionNumber");
            String copyright = request.getParameter("copyright");
            String lastName = request.getParameter("lastName");
            String firstName = request.getParameter("firstName");

            String addBookQuery = String .format("INSERT INTO %s (%s, %s, %s, %s) VALUES (?, ?, ?, ?)",
                    DBConfiguration.DB_BOOKS, DBConfiguration.DB_BOOKS_TITLES_ISBN,
                    DBConfiguration.DB_BOOKS_TITLES_TITLE, DBConfiguration.DB_BOOKS_TITLES_EDITION_NUMBER,
                    DBConfiguration.DB_BOOKS_TITLES_COPYRIGHT);

            String addAuthorQuery = String .format("INSERT INTO %s (%s, %s) VALUES (?, ?)",
                    DBConfiguration.DB_BOOKS_AUTHORS, DBConfiguration.DB_BOOKS_AUTHORS_LAST_NAME,
                    DBConfiguration.DB_BOOKS_AUTHORS_FIRST_NAME, DBConfiguration.DB_BOOKS_AUTHORS_ID);

            String addBridgeTableQuery = String .format("INSERT INTO %s (%s, %s) VALUES (?, ?)",
                    DBConfiguration.DB_BOOKS_AUTHORS_BOOKS_BRIDGE, DBConfiguration.DB_BOOKS_TITLES_ISBN,
                    DBConfiguration.DB_BOOKS_AUTHORS_ID);

            String getNewAuthorIDQuery = String .format("SELECT %s FROM %s WHERE %s = ? AND %s = ?",
                    DBConfiguration.DB_BOOKS_AUTHORS_ID, DBConfiguration.DB_BOOKS_AUTHORS,
                    DBConfiguration.DB_BOOKS_AUTHORS_LAST_NAME, DBConfiguration.DB_BOOKS_AUTHORS_FIRST_NAME);

            try {
                PreparedStatement preparedStatement = connection.prepareStatement(addBookQuery);
                preparedStatement.setString(1, isbn);
                preparedStatement.setString(2, title);
                preparedStatement.setString(3, editionNumber);
                preparedStatement.setString(4, copyright);
                preparedStatement.executeUpdate();

                preparedStatement = connection.prepareStatement(addAuthorQuery);
                preparedStatement.setString(1, lastName);
                preparedStatement.setString(2, firstName);
                preparedStatement.executeUpdate();

                preparedStatement = connection.prepareStatement(getNewAuthorIDQuery);
                preparedStatement.setString(1, lastName);
                preparedStatement.setString(2, firstName);
                ResultSet resultSet = preparedStatement.executeQuery();
                String newAuthorID = "";
                while (resultSet.next()) {
                    newAuthorID = resultSet.getString(DBConfiguration.DB_BOOKS_AUTHORS_ID);
                }

                preparedStatement = connection.prepareStatement(addBridgeTableQuery);
                preparedStatement.setString(1, isbn);
                preparedStatement.setString(2, newAuthorID);
                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("text/html");

            String view = request.getParameter("view");
            PrintWriter out = response.getWriter();

            if (view.equals("author")){
                out.println("<html><body>");
                out.println("<h1>Authors</h1>");
                out.println("<table border=\"1\">");
                out.println("<tr><th>Author Name</th><th>Books</th></tr>");

                try {
                    List<String> author = new ArrayList<>();

                    String getAuthorsQuery = String .format("SELECT * FROM %s", DBConfiguration.DB_BOOKS_AUTHORS);
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(getAuthorsQuery);

                    String getBooksQuery = String .format("SELECT %s, %s AS isbn FROM %s " +
                                    "WHERE isbn IN (SELECT isbn FROM %s WHERE %s = ?)",
                            DBConfiguration.DB_BOOKS, DBConfiguration.DB_BOOKS_TITLES_ISBN,
                            DBConfiguration.DB_BOOKS_TITLES_TITLE, DBConfiguration.DB_BOOKS_AUTHORS_BOOKS_BRIDGE,
                            DBConfiguration.DB_BOOKS_AUTHORS_ID);
                    PreparedStatement preparedStatement = connection.prepareStatement(getBooksQuery);

                    while (resultSet.next()) {
                        String authorName = resultSet.getString(DBConfiguration.DB_BOOKS_AUTHORS_LAST_NAME) + ", " +
                                resultSet.getString(DBConfiguration.DB_BOOKS_AUTHORS_FIRST_NAME);
                        author.add(authorName);
                    }

                    for(String authorName : author) {
                        String[] authorNameSplit = authorName.split(", ");
                        String authorName1 = authorNameSplit[0];
                        String authorID = authorNameSplit[1];
                        out.println("<tr><td>" + authorName1 + "</td><td>");
                        preparedStatement.setString(1, authorID);
                        ResultSet resultSetBooks = preparedStatement.executeQuery();
                        while (resultSetBooks.next()) {
                            out.println(resultSetBooks.getString(DBConfiguration.DB_BOOKS + "<br>"));
                        }
                        out.println("</td></tr>");
                    }

                    out.println("</table>");
                    out.println("</body></html>");

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            else if (view.equals("books")) {
                out.println("<html><body>");
                out.println("<h1>Books</h1>");
                out.println("<table border=\"1\">");
                out.println("<tr><th>ISBN</th><th>Title</th><th>Edition Number</th><th>Copyright</th><th>Authors</th></tr>");

                try {
                    List<String> books = new ArrayList<>();
                    String getBooksQuery = String .format("SELECT * FROM %s", DBConfiguration.DB_BOOKS_TITLES_TITLE);
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(getBooksQuery);

                    String getAuthorsQuery = String .format("SELECT %s, %s AS isbn FROM %s " +
                                    "WHERE isbn IN (SELECT isbn FROM %s WHERE %s = ?)",
                            DBConfiguration.DB_BOOKS_AUTHORS, DBConfiguration.DB_BOOKS_TITLES_ISBN,
                            DBConfiguration.DB_BOOKS_AUTHORS_BOOKS_BRIDGE, DBConfiguration.DB_BOOKS_AUTHORS_BOOKS_BRIDGE,
                            DBConfiguration.DB_BOOKS_TITLES_ISBN);
                    PreparedStatement preparedStatement = connection.prepareStatement(getAuthorsQuery);

                    while (resultSet.next()) {
                        String book = resultSet.getString(DBConfiguration.DB_BOOKS_TITLES_ISBN) + ", " +
                                resultSet.getString(DBConfiguration.DB_BOOKS_TITLES_TITLE) + ", " +
                                resultSet.getString(DBConfiguration.DB_BOOKS_TITLES_EDITION_NUMBER) + ", " +
                                resultSet.getString(DBConfiguration.DB_BOOKS_TITLES_COPYRIGHT);
                        books.add(book);
                    }

                    for (String book : books){
                        String[] bookSplit = book.split(", ");
                        String isbn = bookSplit[0];
                        String title = bookSplit[1];
                        String editionNumber = bookSplit[2];
                        String copyright = bookSplit[3];
                        out.println("<tr><td>" + isbn + "</td><td>" + title + "</td><td>" + editionNumber + "</td><td>" + copyright + "</td><td>");
                        preparedStatement.setString(1, isbn);
                        ResultSet resultSetAuthors = preparedStatement.executeQuery();
                        while (resultSetAuthors.next()) {
                            out.println(resultSetAuthors.getString(DBConfiguration.DB_BOOKS_AUTHORS) + "<br>");
                        }
                        out.println("</td></tr>");

                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                out.println("</table>");
                out.println("</body></html>");
            }
    }
}
