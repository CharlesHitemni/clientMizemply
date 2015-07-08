<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page session="true"%>
<html>
	<head>
		<title>Mizemply Sous Séchoir</title>
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="">
		<meta name="author" content="">
		<meta charset="UTF-8">
		
		<!-- Bootstrap core CSS  -->
		<link href="./bootstrap/css/bootstrap.css" rel="stylesheet">
		<script src="./bootstrap/js/dependencies/jquery-1.10.1.min.js"></script>
		<script src="./bootstrap/js/bootstrap.js"></script>
		<link rel="shortcut icon" href="./bootstrap/ico/icone_mairie.jpg">
		<link href="./bootstrap/css/navbar.css" rel="stylesheet">
		
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
	
	<body style="background:url(./bootstrap/ico/Image1.png) fixed; background-size: cover;
    padding: 0;
    margin: 0;	 ">
	
    <!-- Static navbar -->
    
    <nav class="navbar navbar-default">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
         <img style="min-width: 60px;height: 50px;margin-left: -2px;position: absolute;display: block;" src="./bootstrap/ico/logo_mairie.jpg" alt="">
         <span class="navbar-brand application-heading">RIVERAIN</span>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
          <li class="active"><a href="accueilRiverain"><span class="glyphicon glyphicon-home"></span> Accueil</a></li>
            <!--  <li><a href="#about">About</a></li> -->
          </ul>
          <ul class="nav navbar-nav navbar-right">
            <li><a href="javascript:formSubmit()"> Déconnexion <span class="glyphicon glyphicon-cross"></span></a></li>
            <!--  <li class="active"><a href="/riverain">Static top <span class="sr-only">(current)</span></a></li> -->
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
	

	<div class="container">
		<div class="jumbotron">
		<div class="panel panel-primary" >
			<div class="panel-heading">La Mairie de Mizemply Sous Séchoir traitera votre demande dans les plus brefs délais.</div>
			<div class="panel-body">
				   	<c:if test="${not empty messageErrorAdresse}">
						<div class="error">${messageErrorAdresse}</div>
					</c:if>
					<c:if test="${not empty messageSuccess}">
						<div class="msg">${messageSuccess}</div>
					</c:if>
				
				<form:form class="form form-horizontal" method="POST" action="routage">
					<div class="form-group">
						<label class="col-sm-3 control-label" for="titreDemande">Objet
							de la demande :*</label>
						<div class="col-sm-6">
							<input id="objet" class="form-control"
								placeholder="Titre de la demande" type="text" name="titre"
								required="required" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label" for="adresse">Lieu
							concerné :*</label>
						<div class="col-sm-6">
							<input id="adresse" class="placepicker form-control"
								placeholder="Adresse géolocalisée" type="text" name="adresse"
								required="required" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label" for="descriptionDemande">Description
							de la demande :* </label>
						<div class="col-sm-6">
							<textarea class="form-control"
								placeholder="Quel est la demande ?" id="descriptionAlert"
								name="description" required="required" rows="3"></textarea>
						</div>
					</div>
					<input type="hidden" name="role" value="riverain">
					<div class="form-group">
						<div class="col-sm-offset-8 col-xs-offset-8 col-sm-6">
							<button type="submit" class="btn btn-success">Soumettre</button>
						</div>
					</div>
				</form:form>
				</div>
			</div>
		</div>
	</div>
			
			<form:form action="${logoutUrl}" method="POST" id="logoutForm">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			</form:form>
			
	  <script>
			function formSubmit() {
				document.getElementById("logoutForm").submit();
			}
	  </script>
	  	<script
		src="https://maps.googleapis.com/maps/api/js?sensor=true&libraries=places"></script>
	<script src="./bootstrap/js/jquery.placepicker.js"></script>
	<script>
		$(document).ready(function() {
			// Basic usage
			$(".placepicker").placepicker();
		}); // END document.ready
	</script>
	</body>
</html>