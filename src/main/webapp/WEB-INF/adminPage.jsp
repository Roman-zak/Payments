<%--
  Created by IntelliJ IDEA.
  User: Roman
  Date: 13.10.2022
  Time: 19:49
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--<%@ taglib prefix="p" uri="sortingService.tld" %>--%>
<%@ taglib prefix="p" uri="sortingService" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%--  <jsp:include page="fragments/header.jsp"/> --%>
  <jsp:include page="fragments/header.jsp"/>
</head>
<html>

<body>
  <jsp:include page="fragments/nav-header.jsp"/>
  <nav class="navbar sticky-top navbar-light bg-light">
    <div class="container-fluid">
      <div class="nav-item">
            <form action="/adminPage" method="get">
              <input type="hidden" name="adminPageMode" value="users">
              <input type="submit"class="btn" value="Users">
            </form>
      </div>
      <div class="nav-item">
        <form action="/adminPage" method="get">
          <input type="hidden" name="adminPageMode" value="accounts">
          <input type="submit"class="btn" style="background-color:transparent"  value="Accounts">
        </form>
      </div>
      <div class="nav-item">
        <form action="/adminPage" method="get">
          <input type="hidden" name="adminPageMode" value="unblockRequests">
          <input type="submit"class="btn" style="background-color:transparent"  value="Unblock requests">
        </form>
      </div>
    </div>
  </nav>
  <table class="table table-hover">
  <c:choose>
    <c:when test="${sessionScope.adminPageMode == \"users\"}">

    <thead>
    <tr>
      <th scope="col">id</th>
      <th scope="col">Email</th>
      <th scope="col">Name</th>
      <th scope="col">Surname</th>
      <th scope="col">Role</th>
      <th scope="col">Blocked</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${sessionScope.allUsers}">
      <tr>
        <th scope="row">${user.id}</th>
        <td>${user.email}</td>
        <td>${user.name}</td>
        <td>${user.surname}</td>
        <td>${user.role}</td>
        <td>${user.blocked}</td>
      </tr>
    </c:forEach>

    </tbody>
    </c:when>
    <c:when test="${sessionScope.adminPageMode == \"accounts\"}">

    </c:when>
    <c:when test="${sessionScope.adminPageMode == \"unblockRequests\"}">

    </c:when>
  </c:choose>
  </table>

</body>
</html>
