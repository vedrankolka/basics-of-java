<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home</title>
</head>
<body>

	<c:choose>
		<c:when test="${currentUserID==null }">
			<h5>not logged in</h5>
			<p>
			<form action="login" method="POST">
				${errors.get("nick") }<br>
				nick: <input type="text" name="nick" size="20"><br>
				${errors.get("password") }<br>
				password: <input type="password" name="password" size="20">
				<input type="submit" value="log in">
			</form>
		</c:when>
		<c:otherwise>
			<h5>${currentUserFN } ${currentUserLN }  <a href="logout">log out</a></h5>
		</c:otherwise>
	</c:choose>
	
	

</body>
</html>