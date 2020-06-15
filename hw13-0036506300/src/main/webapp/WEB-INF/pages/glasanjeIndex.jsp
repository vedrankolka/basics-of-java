<%@page import="java.util.SortedMap"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<title>glasanje</title>
	<body>
		<h1>${request.title }</h1>
		<p>
			${request.message }
		</p>
		<ol>
		<c:forEach var="option" items="${request.options }">
			<li><a href="servleti/glasanje-glasaj?id=${option.id }">${option.optionTitle }</a></li>
		</c:forEach>
		</ol>
	</body>
</html>