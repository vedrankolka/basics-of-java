<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style type="text/css">
span.formLabel {
		display: inline-block;
		width: 140px;
        font-weight: bold;
        text-align: right;
		padding-right: 5px;	
}

span.errorLabel {
		display: inline-block;
		padding-left: 150px;
		color: red;
}
</style>
<title>Home - register</title>
</head>
<body>
	<h3>Register</h3>
	<p>
		<form action="register" method="post">
			<!-- first name -->
			<span class="errorLabel">${form.getError("firstName")}</span><br>
			<span class="formLabel">First name:</span>
			<input type="text" name="firstName" value="${form.firstName }" size="20" maxlength="30"><br>
			<!-- last name -->
			<span class="errorLabel">${form.getError("lastName") }</span><br>
			<span class="formLabel">Last name:</span>
			<input type="text" name="lastName" value="${form.lastName }" size="20" maxlength="30"><br>
			<!-- email -->
			<span class="errorLabel">${form.getError("email")}</span><br>
			<span class="formLabel">email:</span>
			<input type="text" name="email" value="${form.email }" size="20" maxlength="30"><br>
			<!-- nickname -->
			<span class="errorLabel">${form.getError("nick")}</span><br>
			<span class="formLabel">nick:</span>
			<input type="text" name="nick" value="${form.nick }" size="20" maxlength="50"><br>
			<!-- password -->
			<span class="errorLabel">${form.getError("password")}</span><br>
			<span class="formLabel">password:</span>
			<input type="password" name="password" size="20"><br>
			<!-- confirm password -->
			<span class="errorLabel">${form.getError("confirmPassword")}</span><br>
			<span class="formLabel">confirm password:</span>
			<input type="password" name="confirmPassword" size="20"><br>
			<!-- submit button -->
			<span class="formLabel">&nbsp;</span>
			<input type="submit" value="register">
			</form>
</body>
</html>