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

  <c:choose>

    <c:when test="${sessionScope.adminPageMode == \"users\"}">
      <c:if test="${sessionScope.userStatusMessage !=null}">
        <c:out value="${sessionScope.userStatusMessage}"/>
        <br>
      </c:if>
    <table class="table table-hover">
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
        <td>
          <form action="/adminPage/userStatus" method="post">
            <input type="hidden" name="id" value="${user.id}">
            <input type="hidden" name="blocked" value="${user.blocked}">
            <input type="submit" class="btn btn-primary"
                   <c:if test="${user.blocked==false}">value="block"</c:if>
                   <c:if test="${user.blocked==true}">value="unblock"</c:if>
            >
          </form>
        </td>
      </tr>
    </c:forEach>
    </tbody>
    </table>
      <%--For displaying Previous link except for the 1st page --%>
      <c:if test="${currentPage != 1}">
        <c:set var="page" scope="session" value='${currentPage - 1}' />
        <td><a href="${pageContext.request.contextPath}/adminPage?adminPageMode=users&page=${currentPage - 1}">Previous</a></td>
      </c:if>

      <table class="table" border="1" cellpadding="5" cellspacing="5">
        <tr>
          <c:forEach begin="1" end="${noOfPages}" var="i">
            <c:choose>
              <c:when test="${currentPage eq i}">
                <td><c:out value="${i}"/></td>
              </c:when>
              <c:otherwise>
                <c:set var="page" scope="session" value='${i}' />
                <td><a href="${pageContext.request.contextPath}/adminPage?adminPageMode=users&page=${i}">${i}</a></td>
              </c:otherwise>
            </c:choose>
          </c:forEach>
        </tr>
      </table>
      <c:if test="${currentPage lt noOfPages}">
        <c:set var="page" scope="session" value='${currentPage+1}' />
        <td><a href="${pageContext.request.contextPath}/adminPage?adminPageMode=users&page=${currentPage+1}">Next</a></td>
      </c:if>
    </c:when>
    <c:when test="${sessionScope.adminPageMode == \"accounts\"}">
      <c:if test="${sessionScope.accountStatusMessage !=null}">
        <c:out value="${sessionScope.accountStatusMessage}"/>
        <br>
      </c:if>
      <table class="table">
        <thead>
        <tr>
          <th scope="col">Number</th>
          <th scope="col">Balance</th>
          <th scope="col">Currency</th>
          <th scope="col">Owner</th>
          <th scope="col">Address</th>
          <th scope="col">Postal code</th>
          <th scope="col">Card number</th>
          <th scope="col">Expire date</th>
          <th scope="col">Status</th>
        </tr>
        </thead>
        <tbody>
          <c:forEach var="account" items="${sessionScope.allAccounts}">
            <tr>
              <td><c:out value="${account.accountNo}"/></td>
              <td><c:out value="${account.balance}" /></td>
              <td><c:out value="${account.currency}" /></td>
              <td><c:out value="${account.ownerName}" /></td>
              <td><c:out value="${account.ownerAddress}" /></td>
              <td><c:out value="${account.postalCode}" /></td>
                <%--                              <c:if test="${account.card!=null}">--%>
              <td><c:out value="${account.card.cardNo}" /></td>
              <td><c:out value="${account.card.expMonth}" />/<c:out value="${account.card.expYear}" /></td>
                <%--                              </c:if>--%>
              <td>
                <form action="/accountStatus" method="post">
                  <input type="hidden" name="accountNo" value="${account.accountNo}">
                  <input type="hidden" name="blocked" value="${account.blocked}">
                  <input type="submit" class="btn btn-primary"
                         <c:if test="${account.blocked==false}">value="block"</c:if>
                         <c:if test="${account.blocked==true}">value="unblock"</c:if>
                  >
                </form>
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
      <c:if test="${currentPage != 1}">
<%--        <c:set var="page" scope="session" value='${sessionScope.currentPage - 1}' />--%>
        <td><a href="${pageContext.request.contextPath}/adminPage?adminPageMode=accounts&page=${currentPage - 1}">Previous</a></td>
      </c:if>

      <table class="table" border="1" cellpadding="5" cellspacing="5">
        <tr>
          <c:forEach begin="1" end="${noOfPages}" var="i">
            <c:choose>
              <c:when test="${currentPage eq i}">
                <td><c:out value="${i}"/></td>
              </c:when>
              <c:otherwise>
<%--                <c:set var="page" scope="session" value='${i}' />--%>
                <td><a href="${pageContext.request.contextPath}/adminPage?adminPageMode=accounts&page=${i}">${i}</a></td>
              </c:otherwise>
            </c:choose>
          </c:forEach>
        </tr>
      </table>
      <c:if test="${currentPage lt noOfPages}">
<%--        <c:set var="page" scope="session" value='${sessionScope.currentPage+1}' />--%>
        <td><a href="${pageContext.request.contextPath}/adminPage?adminPageMode=accounts&page=${currentPage + 1}">Next</a></td>
      </c:if>
    </c:when>
    <c:when test="${sessionScope.adminPageMode == \"unblockRequests\"}">
      <c:if test="${sessionScope.requestStatusMessage !=null}">
        <c:out value="${sessionScope.requestStatusMessage}"/>
        <br>
      </c:if>
      <table class="table">
        <thead>
        <tr>
          <th scope="col">Number</th>
          <th scope="col">Balance</th>
          <th scope="col">Currency</th>
          <th scope="col">Owner</th>
          <th scope="col">Address</th>
          <th scope="col">Postal code</th>
          <th scope="col">Card number</th>
          <th scope="col">Expire date</th>
          <th scope="col">Status</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="request" items="${sessionScope.allUnblockRequest}">
          <tr>
            <td><c:out value="${request.account.accountNo}"/></td>
            <td><c:out value="${request.account.balance}" /></td>
            <td><c:out value="${request.account.currency}" /></td>
            <td><c:out value="${request.account.ownerName}" /></td>
            <td><c:out value="${request.account.ownerAddress}" /></td>
            <td><c:out value="${request.account.postalCode}" /></td>
              <%--                              <c:if test="${account.card!=null}">--%>
            <td><c:out value="${request.account.card.cardNo}" /></td>
            <td><c:out value="${request.account.card.expMonth}" />/<c:out value="${request.account.card.expYear}" /></td>
              <%--                              </c:if>--%>
            <td>
              <form action="/accountStatus" method="post">
                <input type="hidden" name="accountNo" value="${request.account.accountNo}">
                <input type="hidden" name="blocked" value="${request.account.blocked}">
                <input type="submit" class="btn btn-primary"
                       <c:if test="${request.account.blocked==false}">value="block"</c:if>
                       <c:if test="${request.account.blocked==true}">value="unblock"</c:if>
                >
              </form>
            </td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
      <c:if test="${currentPage != 1}">
        <c:set var="page" scope="session" value='${currentPage - 1}' />
        <td><a href="${pageContext.request.contextPath}/adminPage?adminPageMode=unblockRequests&page=${currentPage - 1}">Previous</a></td>
      </c:if>
      <table class="table" border="1" cellpadding="5" cellspacing="5">
        <tr>
          <c:forEach begin="1" end="${noOfPages}" var="i">
            <c:choose>
              <c:when test="${currentPage eq i}">
                <td><c:out value="${i}"/></td>
              </c:when>
              <c:otherwise>
<%--                <c:set var="page" scope="session" value='${i}' />--%>
                <td><a href="${pageContext.request.contextPath}/adminPage?adminPageMode=unblockRequests&page=${i}">${i}</a></td>
              </c:otherwise>
            </c:choose>
          </c:forEach>
        </tr>
      </table>
      <c:if test="${currentPage lt noOfPages}">
        <c:set var="page" scope="session" value='${currentPage+1}' />
        <td><a href="${pageContext.request.contextPath}/adminPage?adminPageMode=unblockRequests&page=${currentPage + 1}">Next</a></td>
      </c:if>
    </c:when>
  </c:choose>


</body>
</html>
