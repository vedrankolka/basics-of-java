<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<meta charset="UTF-8">
<style type="text/css">
	.formLabel {
		display: inline-block;
		width: 140px;
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
<meta charset="UTF-8">
  <body>

  <c:choose>
    <c:when test="${blogEntry==null}">
      No such entry!
    </c:when>
    <c:otherwise>
      <h1>${blogEntry.title }</h1>
      <p>${blogEntry.text}</p>
      <p>
      	created at ${blogEntry.createdAt }<br>
      	last modified at ${blogEntry.lastModifiedAt }
      </p>
      <!-- edit button -->
      <c:if test="${author.nick.equals(currentUserNick) }">
		<a href="${pageContext.request.contextPath }/servleti/author/${author.nick }/edit?id=${blogEntry.id }">edit</a>
      </c:if>
	  <!-- list comments -->
      <c:if test="${!blogEntry.comments.isEmpty() }">
      <ul>
      <c:forEach var="e" items="${blogEntry.comments}">
        <li>
        	<div><b>[user=${e.usersEMail}]</b> ${e.postedOn}</div>
        	<div style="padding-left: 10px;">${e.message}</div>
        </li>
      </c:forEach>
      </ul>
      </c:if>
      <!-- form for adding a comment -->
      <p>
      	Add a comment:
      	<form action="${pageContext.request.contextPath }/servleti/showEntry" method="post">
      		<span class="errorLabel">${commentForm.getError("email") }</span>
      		<c:choose>
      			<c:when test="${currentUserEmail == null }">
      				<span class="formLabel">email:</span><input type="text" name="email" size="30"><br>
      			</c:when>
      			<c:otherwise>
      				<input type="hidden" name="email" value="${currentUserEmail }"><br>
      			</c:otherwise>
      		</c:choose>
      		<span class="errorLabel">${commentForm.getError("message") }</span>
      		<textarea rows="6" cols="40" name="message">${commentForm.message }</textarea><br>
      		<input type="hidden" name="id" value="${blogEntry.id }">
      		<input type="hidden" name="authorNick" value="${blogEntry.creator.nick }">
      		<input type="submit" name="commentForm" value="post">
       </form>
     
    </c:otherwise>
  </c:choose>

  </body>
</html>
