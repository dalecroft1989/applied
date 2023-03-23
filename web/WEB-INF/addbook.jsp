<%--
  Created by IntelliJ IDEA.
  User: dalec
  Date: 2023-03-22
  Time: 10:05 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add a New Book</title>
</head>
<body>
    <h1>Add a New Book</h1>
    <form action="LibraryData" method="post">
        <%--@declare id="title"--%><%--@declare id="author"--%><input type="hidden" name="type" value="book">
        <label for="title">Title:</label>
        <input type="text" name="title" required><br>
        <label for="author">Author:</label>
        <input type="text" name="author" required><br>
        <input type="submit" value="Submit">
    </form>
    <br>
    <a href="index.jsp">Return to Menu</a>
</body>
</html>
