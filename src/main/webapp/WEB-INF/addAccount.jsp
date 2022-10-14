<%--
  Created by IntelliJ IDEA.
  User: Roman
  Date: 07.10.2022
  Time: 17:17
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <%--  <jsp:include page="fragments/header.jsp"/> --%>
  <jsp:include page="fragments/header.jsp"/>
</head>
<html>

<body>
<jsp:include page="fragments/nav-header.jsp"/>
<h2>Add account</h2>
<form action="/addAccount" method="post">
  <table style="with: 20%">
    <td align="center">
      Bank account
    </td>
    <tr>
      <td>Account number</td>
      <td><input required="required" type="text" name="accountNo" /></td>
    </tr>
    <tr>
      <td>Currency</td>
      <td>
        <select name="currency">
        <option value="USD" selected="selected">United States Dollars</option>
        <option value="EUR">Euro</option>
        <option value="UAH">Ukrainian Hryvnia</option>
      </select>
      </td>
    </tr>
    <tr>
      <td>Owner</td>
      <td><input required="required" type="text" name="ownerName" /></td>
    </tr>
    <tr>
      <td>Owner phone</td>
      <td><input required="required" type="text" name="ownerPhone" /></td>
    </tr>
    <tr>
      <td>Owner address</td>
      <td><input required="required" type="text" name="ownerAddress" /></td>
    </tr>
    <tr>
      <td>Postal code</td>
      <td><input required="required" type="text" name="postalCode" /></td>
    </tr>
    <tr>
      <td align="center">
        Credit card
      </td>
    </tr>

    <tr>
      <td>Credit card number</td>
      <td><input type="text" id="ccnum" name="cardnumber" placeholder="1111222233334444"></td>
    </tr>
    <tr>
      <td>Exp Month</td>
      <td><input type="text"  name="expMonth" placeholder="1-12"></td>
    </tr>
    <tr>
      <td>Exp Year</td>
      <td><input type="text"  name="expYear" placeholder="2022"></td>
    </tr>
    <tr>
      <td>CVC</td>
      <td><input type="text"  name="cvc" placeholder="355"></td>
    </tr>

  </table>
  <input type="submit" value="Submit" /></form>
</form>
</body>
</html>
