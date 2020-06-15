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
<title>report</title>
</head>
<body>
	<h1>OS usage</h1>
	<p>
	Here are the results of OS usage in survey that we completed.
	</p>
	<img src="<%=request.getContextPath() %>/reportImage" alt="reportImage">

</body>
</html>