<%--
  Created by IntelliJ IDEA.
  User: Roman
  Date: 07.10.2022
  Time: 16:06
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<%--<fmt:setBundle basename="locale"/> --%>
<script>
  function langChange() {
    location.reload();
  }
</script>

<nav class="navbar sticky-top navbar-light bg-light">
  <div class="container-fluid">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/">Home</a>
    <div class="nav-item">
      <c:if test="${sessionScope.user != null}">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/user">
           My Profile </a>
      </c:if>
    </div>
    <div class="nav-item">
      <c:if test="${sessionScope.user != null && sessionScope.user.role == Role.Admin}">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/admin">
          Admin</a>
      </c:if>
    </div>
    <div class="nav-item">
      <c:if test="${sessionScope.user == null}">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/login">Log in </a>
      </c:if>
      <c:if test="${sessionScope.user == null}">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/register">Sign up</a>
      </c:if>
      <c:if test="${sessionScope.user != null}">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/logout">Log out</a>
      </c:if>
    </div>
  </div>
</nav>