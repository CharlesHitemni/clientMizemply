<%@ page language="java" contentType="text/html; charset=UTF-8"
    %>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page session="true"%>
<html>
<head>
	<title>Mizemply Sous Séchoir</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="shortcut icon" href="./bootstrap/ico/icone_mairie.jpg">
	<link href="./bootstrap/css/login.css" rel="stylesheet">
	<link href="./bootstrap/css/bootstrap.css" rel="stylesheet">
	<script src="./bootstrap/js/dependencies/jquery-1.10.1.min.js"></script>
	 
	 <style>
		.error {
			padding: 15px;
			margin-bottom: 20px;
			border: 1px solid transparent;
			border-radius: 4px;
			color: #a94442;
			background-color: #f2dede;
			border-color: #ebccd1;
		}
		
		.msg {
			padding: 15px;
			margin-bottom: 20px;
			border: 1px solid transparent;
			border-radius: 4px;
			color: #31708f;
			background-color: #d9edf7;
			border-color: #bce8f1;
		}
	</style>
</head>
<body onload='document.form-box.username.focus();'>
	<div class="container">
		<div class="login-container" style="min-width: 400px;">
	            <div id="output"></div>
	            <div class="avatar">
	            	<img src="./bootstrap/ico/logo_mairie.jpg" style="min-width: 100px;height: 100px;">
	            </div>
	            
	            <div class="form-box">
		            <c:if test="${not empty error}">
						<div class="error">${error}</div>
					</c:if>
					<c:if test="${not empty msg}">
						<div class="msg">${msg}</div>
					</c:if>
	                <form name='form-box' action="redirectLogin" method='POST'>
						<input type='text' name='login' placeholder="login">
						<input type='password' name='password' placeholder="mot de passe"/>
						<button class="btn btn-info btn-block login" type="submit">Login</button> 
					</form>
	            </div>
	      </div>
	</div>
</body>
</html>