<%@ page import="java.util.List" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style type="text/css">
  BODY {
  	background-color: #<%=session.getAttribute("pickedBgCol") %>;
  }
  </style>
<title>trigonometric</title>
</head>
<body>
	<h1>Sines and cosines</h1>
	<p>
		Here is a table of the requested sines and cosines.
	</p>
	<table border="1">
		<tr><th>angle</th><th>sin</th><th>cos</th></tr>
		<%
		@SuppressWarnings("unchecked")
		List<Double> sines = (List<Double>) request.getAttribute("sines");
		@SuppressWarnings("unchecked")
		List<Double> cosines = (List<Double>) request.getAttribute("cosines");
		int a = (Integer) request.getAttribute("a");
		int b = (Integer) request.getAttribute("b");
		for (int angle = a, i = 0; angle <= b; ++angle, ++i) { %>
			<tr>
				<td><%=angle %></td>
				<td><%=String.format("%.4f", sines.get(i)) %></td>
				<td><%=String.format("%.4f", cosines.get(i)) %></td>
			</tr>
		<% }%>
	</table>
</body>
</html>