<%--
  Created by IntelliJ IDEA.
  User: Roman
  Date: 07.10.2022
  Time: 16:34
  To change this template use File | Settings | File Templates.
  <%@ taglib prefix = "ex" uri = "custom.tld"%>
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
  <div class="container">
      <div class="row">
          <div class="row">
              <h3>Personal data</h3>
          </div>
          <div class="row">
              <p>Name: <c:out value="${sessionScope.user.name}"/></p>
          </div>
          <div class="row">
              <p>Surname: <c:out value="${sessionScope.user.surname}"/></p>
          </div>
          <div class="row">
              <p>Email: <c:out value="${sessionScope.user.email}"/></p>
          </div>
      </div>
    <div class="row">
      <div class="row">
        <h3>My accounts:</h3>
        <form action="/accountSort" method="get">
          <select name="accountsSortMode">
            <option value="balance">By balance</option>
            <option value="number">By number</option>
            <option value="name">By name</option>
          </select>
          <input type="submit" value="Sort">
        </form>
        <c:if test="${sessionScope.user!=null && sessionScope.user.accounts.isEmpty()}">
          <c:out value="No accounts added yet."/>
        </c:if>
        <c:if test="${sessionScope.user!=null && !sessionScope.user.accounts.isEmpty()}">
            <c:if test="${sessionScope.accStatusMessage !=null}">
                <c:out value="${sessionScope.accStatusMessage}"/>
                <br>
            </c:if>
          <c:out value="Sorted by ${sessionScope.accountsSortMode}"/>
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
                  <th scope="col">Replenish</th>
                  <th scope="col">Status</th>
              </tr>
              </thead>
              <tbody>
              <c:choose>
                  <c:when test="${sessionScope.accountsSortMode == \"balance\"}">
                      <c:forEach var="account" items="${p:sortByBalance(sessionScope.user.accounts)}">
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
                              <td><form action="/replenish" method="get"><input type="hidden" value="${account.accountNo}" name="replenishAccountNo"><input type="submit" class="btn btn-primary" value="replenish"></form></td>
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
                  </c:when>
                  <c:when test="${sessionScope.accountsSortMode == \"number\"}">
                      <c:forEach var="account" items="${p:sortByNumber(sessionScope.user.accounts)}">
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
                              <td><form action="/replenish" method="get"><input type="hidden" value="${account.accountNo}" name="replenishAccountNo"><input type="submit" class="btn btn-primary" value="replenish"></form></td>
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
                  </c:when>
                  <c:when test="${sessionScope.accountsSortMode == \"name\"}">
                      <c:forEach var="account" items="${p:sortByName(sessionScope.user.accounts)}">
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
                              <td><form action="/replenish" method="get"><input type="hidden" value="${account.accountNo}" name="replenishAccountNo"><input type="submit" class="btn btn-primary" value="replenish"></form></td>
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
                  </c:when>
              </c:choose>
              </tbody>
          </table>
      </div>
      <div class="row">
        <form action="/addAccount" method="get" c>
          <input type="submit" class="btn btn-primary" value="Add">
        </form>
      </div>
    </div>
  </div>
</body>
</html>
