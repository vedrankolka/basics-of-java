<%@page import="java.util.SortedMap"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<style>
		BODY { background-color: ${sessionScope.backgroundColor } }
	</style>

	<title>glasanje</title>
	<body>
	
		<h1><%=request.getAttribute("title") %></h1>
		<p>
			<%=request.getAttribute("message") %>
		</p>
		<ol>
		<c:forEach var="option" items="${requestScope.options }">
			<li><a href="<%=request.getContextPath() %>/servleti/glasanje-glasaj?id=${option.id }">${option.title }</a></li>
		</c:forEach>
		</ol>
	</body>
</html>