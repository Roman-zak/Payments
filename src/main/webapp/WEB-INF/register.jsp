<%--
  Created by IntelliJ IDEA.
  User: Roman
  Date: 06.10.2022
  Time: 22:24
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%--  <jsp:include page="fragments/header.jsp"/> --%>
  <jsp:include page="fragments/header.jsp"/>
</head>
<body>
<h1>User Register</h1>
<form action="RegisterController" method="post">
  <table style="with: 20%">
    <tr>
      <td>First Name</td>
      <td><input required="required" type="text" name="name" /></td>
    </tr>
    <tr>
      <td>Last Name</td>
      <td><input required="required" type="text" name="surname" /></td>
    </tr>
    <tr>
      <td>Email</td>
      <td><input required="required" type="email" name="email" /></td>
    </tr>
    <tr>
      <td>Password</td>
      <td><input required="required" type="password" name="password" /></td>
    </tr>
  </table>
  <input type="submit" value="Submit" /></form>
</body>
</html>
