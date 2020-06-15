<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${author.nick }</title>
</head>
<body>
	<h2>${author.nick }'s blog</h2>
	<c:choose>
		<c:when test="${entries == null || entries.isEmpty() }">
			There are no blog entries from ${author.firstName } ${author.lastName }.
		</c:when>
		<c:otherwise>
			Blog entries:<br><br>
			<c:forEach var="e" items="${entries }">
				Title: ${e.title }<br>
				Last modified at: ${e.lastModifiedAt }<br>
				<a href="${pageContext.request.contextPath }/servleti/author/${author.nick }/${e.id }">link</a><br><br>
			</c:forEach>
		</c:otherwise>
	</c:choose>
	<p>
	<c:if test="${author.nick.equals(currentUserNick) }">
		Add a <a href="${pageContext.request.contextPath }/servleti/author/${author.nick }/new">new</a> blog entry.
	</c:if>
	
</body>
</html>