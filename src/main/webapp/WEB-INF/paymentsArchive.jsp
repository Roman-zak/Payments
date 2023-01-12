<%--
  Created by IntelliJ IDEA.
  User: Roman
  Date: 11.10.2022
  Time: 22:46
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
  <h3>
    My payments
  </h3>
  <c:if test="${sessionScope.user!=null &&( sessionScope.payments.isEmpty() || sessionScope.payments==null)}">
    <c:out value="There are no payments yet."/>
  </c:if>
  <c:if test="${sessionScope.user!=null &&  !sessionScope.payments.isEmpty() && sessionScope.payments!=null}">
    <c:out value="You have ${sessionScope.payments.size()} payments."/>
    <c:if test="${sessionScope.user!=null && !sessionScope.user.accounts.isEmpty()}">
      <c:out value="Sorted by ${sessionScope.paymentsSortMode}"/>
    </c:if>
    <form action="/paymentsSort" method="get">
      <select name="paymentsSortMode">
        <option value="number">By number</option>
        <option value="date">By date</option>
      </select>
      <input type="submit" value="Sort">
    </form>
    <br>
    <table  class="table">
      <thead>
        <tr>
          <th scope="col">Payer account</th>
          <th scope="col">Sum</th>
          <th scope="col">Recipient account</th>
          <th scope="col">Time</th>
          <th scope="col">Status</th>
        </tr>
      </thead>
      <tbody>
        <c:choose>
          <c:when test="${sessionScope.paymentsSortMode == \"date\"}">
            <c:forEach var="payment" items="${p:sortPaymentsByDate(sessionScope.payments)}">

                <tr>
                  <td>${payment.payerAccountNumber}</td>
                  <td>${payment.sum}</td>
                  <td>${payment.recipientAccountNo}</td>
                  <td>${payment.timeStamp}</td>
                  <td name ="status">${payment.status}</td>
                  <c:if test="${payment.status.ordinal() == 0}">
                    <td><form action="/repay" method="post"><input type="hidden" name ="id" value="${payment.id}"><input type="submit" value="Pay"></form></td>
                  </c:if>
                </tr>

            </c:forEach>
          </c:when>
          <c:when test="${sessionScope.paymentsSortMode == \"number\"}">
            <c:forEach var="payment" items="${p:sortPaymentsByNumber(sessionScope.payments)}">
              <tr>
                <td>${payment.payerAccountNumber}</td>
                <td>${payment.sum}</td>
                <td>${payment.recipientAccountNo}</td>
                <td>${payment.timeStamp}</td>
                <td>${payment.status}</td>
                <c:if test="${payment.status.ordinal() == 0}">
                  <td><form action="/repay" method="post"><input type="hidden" name ="id" value="${payment.id}"><input type="submit" value="Pay"></form></td>
                </c:if>
              </tr>
            </c:forEach>
          </c:when>
        </c:choose>
      </tbody>
    </table>
    <div class="row">
      <c:if test="${not empty lastPaymentMessage}">
        <p>${lastPaymentMessage}</p>
      </c:if>
    </div>
  </c:if>
</body>
</html>
