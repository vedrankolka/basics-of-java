<%@page import="java.util.SortedMap"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<title>glasanjeIndex</title>
	<body>
		<h1>Glasanje za omiljeni bend:</h1>
		<p>
		Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali!</p>
		<ol>
		<%
		@SuppressWarnings("unchecked")
		SortedMap<Integer, String[]> bands = (SortedMap<Integer, String[]>) request.getAttribute("bands");
		for (Map.Entry<Integer, String[]> e : bands.entrySet()) {
		%>
			<li><a href="glasanje-glasaj?id=<%=e.getKey() %>"><%=e.getValue()[0] %></a></li>
		<%} %>
		</ol>
	</body>
</html>