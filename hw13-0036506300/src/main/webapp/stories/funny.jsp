<%@ page import="java.util.Random" language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>
<%!
private String randomColor(Random random) {
	short r = (short) random.nextInt(256);
	short g = (short) random.nextInt(256);
	short b = (short) random.nextInt(256);
	String color = Integer.toHexString(r) + Integer.toHexString(g) + Integer.toHexString(b);
	return color;
}
%>
<%
	Random random = new Random();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style type="text/css">
  BODY {
  	background-color: #<%=session.getAttribute("pickedBgCol") %>;
  }
  </style>
<title>stories/funny</title>
</head>
<body>
	<h3>Vicevi</h3>
	<p>
		<font color="#<%=randomColor(random)%>">Uđu dva programera u bar i dođe prvi: "Zaš ja smišljam vic za zadaću iz jave?"<br>
		A kaže drugi: "A dobar vic Veki, baš si se potrudio...".</font>
		
	</p>
	
	<p>
		<font color="#<%=randomColor(random)%>">Uđu dva regexa u bar i dođe prvi: "\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}\b"<br>
		A kaže prvi: "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$".</font>
	</p>
	
</body>
</html>