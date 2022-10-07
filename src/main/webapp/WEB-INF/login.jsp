<%--
  Created by IntelliJ IDEA.
  User: Roman
  Date: 05.10.2022
  Time: 14:02
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
<html>

<body>
<jsp:include page="fragments/nav-header.jsp"/>
<h1>User Login</h1>
<form action="/login" method="post">
    <table style="with: 50%">

        <tr>
            <td>Email</td>
            <td><input required="required" type="email" name="email" /></td>
        </tr>
        <tr>
            <td>Password</td>
            <td><input required="required" type="password" name="password" /></td>
        </tr>
    </table>
    <input type="submit" value="Login" /></form>
<c:if test="${not empty message}">
    <p>${message}</p>
</c:if>
</body>
</html>
