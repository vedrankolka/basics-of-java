<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<!DOCTYPE html>
<html>
  <head>
  <meta charset="UTF-8">
  <style type="text/css">
  BODY {
  	background-color: #<%=session.getAttribute("pickedBgCol") %>;
  }
  </style>
  <title>index</title>
  </head>
  <body>
    <h2>Welcome!</h2>
    
    <p>Here is a link to a background color chooser!<br>
    	<a href="<%=request.getContextPath() %>/colors.jsp">Background color chooser</a>
    </p>
    
    <p>
    	Here is a link to a table of sines and cosines!<br>
    	<a href="<%=request.getContextPath() %>/trigonometric?a=0&b=90">sines and cosines</a>
    </p>
    
    <form action="trigonometric" method="GET">
    	Početni kut:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>
    	Završni kut:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br>
    <input type="submit" value="Tabeliraj"><input type="reset" value="Reset">
    </form>
    
    <p>
    	Here is a link to the funny pages!<br>
    	<a href="stories/funny.jsp">stories/funny</a>
    </p>
    
    <p>
    	Here is a link to our OS usage survey!<br>
    	<a href="report.jsp">OS usage report</a>
    </p>
    
    <p>
    	Here is a link to an excel file of numbers from 1 to 100 and their powers from 1 to 3!<br>
    	<a href="<%=request.getContextPath() %>/powers?a=1&b=100&n=3">powers.xls</a>
    </p>
    
    <p>
    	Here is a link to some info about the app!<br>
    	<a href="appinfo.jsp">app info</a>
    </p>
    
  </body>
</html>