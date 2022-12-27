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
            <div class="container">
                <div class="row">
                    <div class="col">
                    </div>
                    <div class="col">
                        <h3>Make a payment</h3>
                        <form action="/pay" method="post">
                            <div class="form-group">
                                <label for="payerAcc">Payer`s account</label>
                                <select id = "payerAcc" class="form-control" name = "payer_id">
                                    <c:forEach var="account" items="${sessionScope.user.accounts}">
                                        <option value="${account.id}">${account.accountNo}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <br>
                            <div class="form-group">
                                <label for="recepientAcc"> Rerecipient`s account</label>
                                <input type="text" class="form-control" id="recepientAcc"
                                <c:if test="${sessionScope.replenishAccountNo != null}">
                                       value="${sessionScope.replenishAccountNo}"
                                </c:if>
                                       name="recipientAccountNo" required pattern="^[0-9]{8,17}$">
                            </div>
                            <br>
                            <div class="form-group">
                                <label for="sum">Sum</label>
                                <input id="sum" class="form-control" type="number" min="0" step="0.01" name="sum" required>
                            </div>
                            <br>
                            <input type="submit" class="btn btn-primary"value="Pay">
                        </form>
                    </div>
                    <div class="col">

                    </div>
                </div>
                <div class="row">
                    <c:if test="${not empty lastPaymentMessage}">
                        <p>${lastPaymentMessage}</p>
                    </c:if>
                </div>
                <div class="row">
                        ${errors }
                </div>

            </div>
        </c:if>
    </div>
</body>
</html>