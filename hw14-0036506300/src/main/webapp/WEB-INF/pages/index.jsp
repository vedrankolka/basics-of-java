<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<style>
		BODY { background-color: ${sessionScope.backgroundColor } }
	</style>
<title>Home</title>
</head>
<body>
<h1>Izaberite anketu za glasanje!</h1>
<p>
	<c:forEach var="poll" items="${requestScope.polls }">
		<h4><a href="<%=request.getContextPath() %>/servleti/glasanje?pollID=${poll.id }">${poll.title }</a></h4>
	</c:forEach>

</body>
</html>