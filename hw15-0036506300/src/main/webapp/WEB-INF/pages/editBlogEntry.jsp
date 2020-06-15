<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style type="text/css">
	.formLabel {
		display: inline-block;
		width: 60px;
        font-weight: bold;
        text-align: right;
		padding-right: 5px;	
	}
	.errorLabel {
		display: inline-block;
		padding-left: 70px;
		color: red;
	}
</style>
<title>edit</title>
</head>
<body>
<h2>
<%String id = request.getParameter("id"); %>
<c:choose>
	<c:when test="<%=id == null %>">New blog entry</c:when>
	<c:otherwise>Edit blog entry</c:otherwise>
</c:choose>
</h2>
<p>
	<form action="${pageContext.request.contextPath }/servleti/update" method="post">
		<!-- title -->
		<span class="errorLabel">${entryForm.getError("title") }</span><br>
		<span class="formLabel">title:</span>
		<input type="text" name="title" value="${entryForm.title }" size="38" maxlength="30"><br>
		<!-- text -->
		<span class="errorLabel">${entryForm.getError("text") }</span><br>
		<span class="formLabel">text:</span>
		<textarea rows="5" cols="30" name="text" maxlength="4096">${entryForm.text }</textarea><br>
		<!-- blog entry id, null if it is a new entry-->
		<input type="hidden" name="id" value="<%=id == null ? "" : id %>">
		<input type="hidden" name="authorNick" value="${authorNick }">
		<!-- submit button -->
		<span class="formLabel">&nbsp;</span>
		<input type="submit" value="post">
	</form>

</body>
</html>