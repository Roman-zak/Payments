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
    <div class="form-group">
        <label for="exampleInputEmail1">Email address</label>
        <input name="email" type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Enter email">
        <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
    </div>
    <div class="form-group">
        <label for="exampleInputPassword1">Password</label>
        <input name ="password" type="password" class="form-control" id="exampleInputPassword1" placeholder="Password">
    </div>
    <br>
    <button type="submit" class="btn btn-primary">Log in</button>
</form>
<c:if test="${not empty message}">
    <p>${message}</p>
</c:if>
</body>
</html>
