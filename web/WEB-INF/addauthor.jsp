<%--
  Created by IntelliJ IDEA.
  User: dalec
  Date: 2023-03-22
  Time: 10:08 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add a New Author</title>
</head>
<body>
    <h1>Add a new author</h1>
    <form method="post" action="LibraryData">
        <input type="hidden" name="type" value="author">
        <label for="name">Name:</label>
        <input type="text" name="name" id="name" required><br>
        <label for="email">Email:</label>
        <input type="email" name="email" id="email" required><br>
        <button type="submit">Add author</button>
    </form>
    <br>
    <a href="index.jsp">Return to menu</a>
</body>
</html>
