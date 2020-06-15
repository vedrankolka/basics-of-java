<%@page import="javax.servlet.descriptor.TaglibDescriptor"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.SortedMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
	<style type="text/css">
		table.rez {text-align: center;}
		BODY { background-color: ${sessionScope.backgroundColor } }
	</style>
	<title>glasanje-rezultati</title>
	</head>
	<body>
		<h1>Rezultati glasanja</h1>
		<p>${requestScope.poll.title }</p>
		<p>Ovo su rezultati glasanja.</p>
		<table border="1" class="rez">
			<thead><tr><th>Opcija</th><th>Broj glasova</th></tr></thead>
			<tbody>
			<c:forEach var="option" items="${requestScope.options }">
				<tr><td>${option.title }</td><td>${option.votesCount }</td></tr>
			</c:forEach>
			</tbody>
		</table>
		<h2>Grafički prikaz rezultata</h2>
		<img alt="Pie-chart" src="<%=request.getContextPath() %>/servleti/glasanje-grafika?pollID=${requestScope.poll.id }" width="450" height="400"/>
		<h2>Rezultati u XLS formatu</h2>
		<p>
			Rezultati u XLS formatu dostupni su <a href="<%=request.getContextPath() %>/servleti/glasanje-xls?pollID=${requestScope.poll.id }">ovdje</a>
		</p>
		<h2>Razno</h2>
		<p>
			Poveznice na media sadržaj pobjednika:
		</p>
		<ul>
		<c:forEach var="option" items="${requestScope.winners }">
			<li><a href="${option.optionLink }" target="_blank">${option.title }</a></li>
		</c:forEach>
		</ul>
	</body>
</html>