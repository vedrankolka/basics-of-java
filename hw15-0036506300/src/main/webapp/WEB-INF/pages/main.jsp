<%@page import="hr.fer.zemris.java.hw15.web.forms.LoginForm"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style type="text/css">
	.formLabel {
		display: inline-block;
		width: 85px;
        font-weight: bold;
        text-align: right;
		padding-right: 10px;	
	}
	.errorLabel {
		display: inline-block;
		padding-left: 15px;
		color: red;
	}
</style>
<title>Home</title>
</head>
<body>
	<c:choose>
		<c:when test="${currentUserID==null }">
			<h4>not logged in</h4>
			${invalidEditError }
			<p>
			<form action="main" method="post">
				<span class="errorLabel">${form.getError("nick")}</span><br>
				<span class="formLabel">nick:</span>
				<input type="text" name="nick" value="${form.nick }" size="20"><br>
				<span class="errorLabel">${form.getError("password") }</span><br>
				<span class="formLabel">password:</span>
				<input type="password" name="password" size="20"><br>
				<span class="formLabel">&nbsp;</span>
				<input type="submit" value="log in">
			</form>
			<p>
				Not registered? Do it <a href="${pageContext.request.contextPath }/servleti/register">here</a>
		</c:when>
		<c:otherwise>
			<h4>${currentUserFN } ${currentUserLN }  <a href="logout">log out</a></h4>
			${invalidEditError }
		</c:otherwise>
	</c:choose>
	
	<!-- remove the error since it was already shown -->
	<%session.setAttribute("invalidEditError", null); %>
	<!-- List of all the recorded bloggers -->
	<p>
	Available authors:
	<ul>
	<c:forEach var="b" items="${bloggers }">
		<li><a href="${pageContext.request.contextPath }/servleti/author/${b.nick}">${b.firstName } ${b.lastName }</a></li>
	</c:forEach>
	</ul>
</body>
</html>