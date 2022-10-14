<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="WEB-INF/fragments/header.jsp"/>
</head>
<body>
    <jsp:include page="WEB-INF/fragments/nav-header.jsp"/>
    <h1>
        <%= "Payments" %>
    </h1>
    <br/>
    <div>
        <c:if test="${sessionScope.user==null}">
            In order to make payment log in please.
        </c:if>
        <c:if test="${sessionScope.user!=null}">
            <c:out value="${sessionScope.user.role}"/>
            <div class="container">
                <div class="row">
                    <form action="/pay" method="post">
                        <div class="row">
                            <div class="col">
                                Payer`s account
                            </div>
                            <div class="col">
<%--                                ${sessionScope.user.accounts}--%>
                                <select name = "payer_id">
                                    <c:forEach var="account" items="${sessionScope.user.accounts}">
                                        <option value="${account.id}"><c:out value="${account.accountNo}"/></option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col">
                                Rerecipient`s account
                            </div>
                            <div class="col">
                                <input type="text"
                                    <c:if test="${sessionScope.replenishAccountNo != null}">
                                        value="${sessionScope.replenishAccountNo}"
                                    </c:if>
                                name="recipientAccountNo" required>
                            </div>
                            <div class="col">
                                Sum
                            </div>
                            <div class="col">
                                <input type="number" min="0" step="0.01" name="sum" required>
                            </div>
                            <div class="col">
                                <input type="submit" value="Pay">
                            </div>
                        </div>
                    </form>
                </div>
                <div class="row">
                    <c:if test="${not empty lastPaymentMessage}">
                        <p>${lastPaymentMessage}</p>
                    </c:if>
                </div>
            </div>
        </c:if>

    </div>
</body>
</html>