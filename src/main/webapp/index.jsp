<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="WEB-INF/fragments/header.jsp"/>
</head>
<body>
<jsp:include page="WEB-INF/fragments/nav-header.jsp"/>
<h1><%= "Payments" %>
</h1>
<br/>
<p><c:if test="${sessionScope.user!=null}"><c:out value="${sessionScope.user.email}"/></c:if>
</p>
</body>
</html>