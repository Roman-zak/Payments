<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@taglib uri="http://example.com/functions" prefix="f" %><%@ taglib prefix="f" uri=""%>--%>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale"/>
<html>
<head>
    <jsp:include page="/WEB-INF/fragments/header.jsp"/>
</head>
<body>
<div class="container">
    <div class="align-content-md-center">
        <h1>Oops...</h1>
        <c:set var="code" value="${requestScope['jakarta.servlet.error.status_code']}"/>
        <c:set var="message" value="${requestScope['jakarta.servlet.error.message']}"/>
        <c:set var="exception" value="${requestScope['jakarta.servlet.error.exception']}"/>

        <c:if test="${not empty code}">
            <h3>Error code: ${code}</h3>
        </c:if>

        <c:if test="${not empty message}">
            <h3>Message: ${message}</h3>
        </c:if>

        <c:if test="${not empty errorMessage and empty exception and empty code}">
            <h3>Error message: ${errorMessage}</h3>
        </c:if>
        <a href="/">Home</a>
    </div>

</div>

</body>
</html>
