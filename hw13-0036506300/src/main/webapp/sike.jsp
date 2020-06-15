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
<title>sike!</title>
</head>
<body>
<h3>Sike!</h3>
<p>You got played! There is no such thing.</p>
</body>
</html>