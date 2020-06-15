<%@page import="java.util.Map"%>
<%@page import="java.util.SortedMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>
<!DOCTYPE html>
<html>
	<head>
	<style type="text/css">
		table.reztd {text-align: center;}
	</style>
	<title>glasanje-rezultati</title>
	</head>
	<body>
		<h1>Rezultati glasanja</h1>
		<p>Ovo su rezultati glasanja.</p>
		<table border="1" class="rez">
			<thead><tr><th>Bend</th><th>Broj glasova</th></tr></thead>
			<tbody>
			<%
			@SuppressWarnings("unchecked")
			SortedMap<Integer, String[]> bands = (SortedMap<Integer, String[]>) session.getAttribute("bands");
			@SuppressWarnings("unchecked")
			SortedMap<Integer, Integer> votes = (SortedMap<Integer, Integer>) request.getAttribute("votes");
			for (Integer id : votes.keySet()) { %>
				<tr><td><%=bands.get(id)[0] %></td><td><%=votes.get(id) %></td></tr>
			<%} %>
			</tbody>
		</table>
		<h2>Grafički prikaz rezultata</h2>
		<img alt="Pie-chart" src="<%=request.getContextPath() %>/glasanje-grafika" width="450" height="400"/>
		<h2>Rezultati u XLS formatu</h2>
		<p>
			Rezultati u XLS formatu dostupni su <a href="<%=request.getContextPath() %>/glasanje-xls">ovdje</a>
		</p>
		<h2>Razno</h2>
		<p>
			Primjeri pjesama pobjedničkih bendova:
		</p>
		<ul>
		<%
		int maxVotes = votes.values().stream().max((i1, i2) -> i1.compareTo(i2)).get();
		for (Integer id : votes.keySet()) {
			if (votes.get(id) != maxVotes)
				continue; %>
			<li><a href="<%=bands.get(id)[1] %>" target="_blank"><%=bands.get(id)[0] %></a></li>
		<%} %>
		</ul>
	</body>
</html>