<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style type="text/css">
  BODY {
  	background-color: #<%=session.getAttribute("pickedBgCol") %>;
  }
  </style>
<title>colors</title>
</head>
<body>
<h3>Colors</h3>
	<%
		String appName = request.getContextPath();
		String name = "pickedBgCol";
	%>
	<a href="<%=appName %>/setcolor?color=FFFFFF">WHITE</a><br>
	<a href="<%=appName %>/setcolor?color=FF0000">RED</a><br>
	<a href="<%=appName %>/setcolor?color=00FF00">GREEN</a><br>
	<a href="<%=appName %>/setcolor?color=00FFFF">CYAN</a><br>
	<a href="<%=appName %>/setcolor?color=66B2FF">LIGHT BLUE</a><br>
	<a href="<%=appName %>/setcolor?color=FFCC99">PALE ORANGE</a><br>
</body>
</html>