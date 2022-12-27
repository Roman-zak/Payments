<%--<jsp:useBean id="errors" scope="request" type="java.lang.String"/>--%>
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
<jsp:include page="fragments/nav-header.jsp"/>
<h1>User Register</h1>
<form action="/register" method="post">
  <div class="form-group">
    <label for="InputName">Name</label>
    <input required pattern="\p{1,25}" name="name"  type="text" class="form-control" id="InputName" placeholder="Enter name">
  </div>
  <div class="form-group">
    <label for="InputSurname">Surname</label>
    <input required pattern="\p{1,25}" name="surname"  type="text" class="form-control" id="InputSurname" placeholder="Enter surname">
  </div>
  <div class="form-group">
    <label for="exampleInputEmail1">Email address</label>
    <input required name="email" type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Enter email">
    <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
  </div>
  <div class="form-group">
    <label for="InputPassword">Password</label>
    <input required pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$"  oninvalid="this.setCustomValidity('Password must contain minimum eight characters, at least one uppercase letter, one lowercase letter and one number')"
           oninput="this.setCustomValidity('')" name ="password" type="password" class="form-control" aria-describedby="passwordHelp" id="InputPassword" placeholder="Password">
<%--    <small id="passwordHelp" class="form-text text-muted">Password must contain minimum eight characters, at least one uppercase letter, one lowercase letter and one number.</small>--%>
  </div>

  <br>
  <button type="submit" class="btn btn-primary">Sign up</button>
</form>
${errors }
</body>
</html>
