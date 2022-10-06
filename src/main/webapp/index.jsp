<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Payments" %>
</h1>
<br/>
<p><c:if test="${sessionScope.user!=null}"><c:out value="${sessionScope.user.email}"/></c:if>
</p>
<a href="LoginController">Log in</a>
<a href="RegisterController">Sign up</a>
</body>
</html>