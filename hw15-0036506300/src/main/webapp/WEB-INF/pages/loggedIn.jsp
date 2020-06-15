<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home</title>
</head>
<body>
	<h3>You are already logged in</h3>
	<p>
	<a href="${pageContext.request.contextPath }/servleti/logout">log out</a><br>
	or<br>
	<a href="${pageContext.request.contextPath }/servleti/main">Continue</a>
	as ${currentUserFN } ${currentUserLN }<br>
</body>
</html>