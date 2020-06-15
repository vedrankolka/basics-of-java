<%@page import="java.time.format.DateTimeFormatter"%>
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
<title>appinfo</title>
</head>
<body>
<h3>application info</h3>
<%
	long timeCreated = (Long) application.getAttribute("timeCreated");
	long timeElapsed = System.currentTimeMillis() - timeCreated;
	long days = timeElapsed / (1000 * 60 * 60 * 24);
	long rest = timeElapsed % (1000 * 60 * 60 * 24);
	long hours = rest / (1000 * 60 * 60);
	rest %= 1000 * 60 * 60;
	long minutes = rest / (1000 * 60);
	rest %= 1000 * 60;
	long seconds = rest / 1000;
	long millis = rest % 1000;
%>
<p>
	<a href="<%=request.getContextPath() %>/sike.jsp">End-user license agreement</a><br>
	<a href="<%=request.getContextPath() %>/sike.jsp">Terms of use</a>
</p>
<p>
	The app has been up for 
	<b><%=days %></b> days
	<b><%=hours %></b> hours
	<b><%=minutes %></b> minutes
	<b><%=seconds %></b> seconds and
	<b><%=millis %></b> milliseconds.
	</p>
	
	<p>
		<a href="<%=request.getContextPath() %>/index.jsp">Go back.</a>
	</p>
	
</body>
</html>