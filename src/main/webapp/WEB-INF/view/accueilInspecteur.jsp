<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Mizemply Sous Séchoir</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
	
	<script src="//code.jquery.com/jquery-1.10.2.min.js"></script>
    <link href="./bootstrap/css/bootstrap.css" rel="stylesheet">
    <style type="text/css">
	    .trash { color:rgb(209, 91, 71); }
		.flag { color:rgb(248, 148, 6); }
		.panel-body { padding:10px; }
		.panel-footer .pagination { margin: 0; }
		.panel .glyphicon,.list-group-item .glyphicon { margin-right:5px; }
		.panel-body .radio, .checkbox { display:inline-block;margin:0px; }
		.panel-body input[type=checkbox]:checked + label { text-decoration: line-through;color: rgb(128, 144, 160); }
		.list-group-item:hover, a.list-group-item:focus {text-decoration: none;background-color: rgb(245, 245, 245);}
		.list-group { margin-bottom:0px; }
    </style>
    <script src="//code.jquery.com/jquery-1.10.2.min.js"></script>
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
    <script src="./bootstrap/js/dependencies/datatable.js"></script>
    <link href="./bootstrap/css/dataTables.bootstrap.css" rel="stylesheet">
    <link href="./bootstrap/css/navbar.css" rel="stylesheet">
	<link rel="shortcut icon" href="./bootstrap/ico/icone_mairie.jpg">
</head>

<body
	style="background: url(./bootstrap/ico/Image1.png) fixed; background-size: cover; padding: 0; margin: 0;">
   
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
         <span class="navbar-brand application-heading">Inspecteur</span>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
          <li class="active"><a href="accueilInspecteur"><span class="glyphicon glyphicon-home"></span> Accueil</a></li>
            <!--  <li><a href="#about">About</a></li> -->
          </ul>
          <ul class="nav navbar-nav navbar-right">
            <li><a href="javascript:formSubmit()"> Déconnexion <span class="glyphicon glyphicon-cross"></span></a></li>
            <!--  <li class="active"><a href="/riverain">Static top <span class="sr-only">(current)</span></a></li> -->
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
	
	<form action="${logoutUrl}" method="POST" id="logoutForm">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>


	<div class="jumbotron" style="margin-bottom: 30px; margin-left: 30px;margin-right:30px;padding:48px;">
		<div class="row">
	        <div class="col-sm-8">
	         	<div class="panel panel-default">
			          <div class="panel-heading">
			          		<h4>Liste des demandes à inspecter</h4>
			          </div>
			          <br>
			          <div class="panel-body">
				        <div class="table-responsive">
				            <table id="mytable" class="table table-bordred table-striped">
				            	<thead>
					                 <th>Id</th>
						             <th>titre</th>
						             <th>Reçu le</th>
						             <th>Adresse</th>
						             <th hidden>Description</th>
						             <th>Sélectionner</th>
				            	</thead>
						    	<tbody id="bodytable">
<%-- 								    <c:forEach items="${listeDemandeRiverain}" var="demandeRiverain"> --%>
<!-- 										    <tr> -->
<%-- 										    	<td>${demandeRiverain.idDemandeRiverain}</td> --%>
<%-- 										    	<td>${demandeRiverain.objetDemande}</td> --%>
<%-- 										    	<td>${demandeRiverain.dateDemande}</td> --%>
<%-- 										    	<td>${demandeRiverain.adresse.libelle} ${demandeRiverain.adresse.ville}</td> --%>
<!-- 										    	<td><div ><a href='#' class=''><span class='glyphicon glyphicon-search'></span></a></div></td> -->
<!-- 											    <td><div ><a href='#' class='addAdressList'><span class='glyphicon glyphicon-arrow-right'></span></a></div></td> -->
<!-- 										    </tr> -->
<%-- 									    </c:forEach> --%>
								</tbody>
							</table>
						</div>
	        		</div>
	        	</div>
			</div>
			<div class="col-sm-4">
			        <div class="panel panel-default">
			          <div class="panel-heading">
			          		<h4>Constat inspecteur</h4>
			          </div>
			          <br>
			          <div class="panel-body">
			          
				          <form id="formInspecteur" class="form-horizontal" action="routage" method="post">
				            <!-- Objet input-->
				            <div class="form-group">
				              <label class="col-md-3 control-label" for="objet">Titre</label>
				              <div class="col-md-9">
				                <input id="titre" name="titreCache" type="text" class="form-control" disabled>
				                <input type="hidden" id="titreHidden" name="titre" type="text" />
				              </div>
				            </div>
				    
				            <!-- date input-->
				            <div class="form-group">
				              <label class="col-md-3 control-label" for="date">Date</label>
				              <div class="col-md-9">
				               <input id="date" name="dateCache" type="text" placeholder="xx/xx/xxxx" class="form-control" disabled>
				                <input type="hidden" id="dateHidden" name="date" type="text"  />
				              </div>
				            </div>
				    		
							<div class="form-group">
				              <label class="col-md-3 control-label" for="descriptionRiverain">Description riverain</label>
				              <div class="col-md-9">
				                <textarea class="form-control" id="descriptionRiverain" name="descriptionCache" rows="5" disabled></textarea>
				                <input type="hidden" id="descriptionHidden" name="description"/>
				              </div>
				            </div>
				            <div class="page-header" >
							</div>
				    		
				            <div class="form-group">
				              <label class="col-md-3 control-label" for="descriptionInspecteur">Description inspecteur</label>
				              <div class="col-md-9">
				                <textarea class="form-control" id="descriptionInspecteur" name="descriptionInspecteur" placeholder="Ajouter vos remarques" rows="5"></textarea>
				              </div>
				            </div>
				    		<input type="hidden" name="role" value="inspecteur">
				    		<input type="hidden" name="idDemande" id="idDemande">
				            <!-- Form actions -->
				            <div class="form-group">
				              <div class="col-md-6 text-left">
				                <button id="clotureMission" name="clotureMission" class="btn btn-danger">Clôturer</button>
				              </div>
				              <div class="col-md-6 text-right">
				                <button id="envoyerMission" name="envoyerMission" class="btn btn-success">Envoyer</button>
				              </div>
				            </div>
				          </form>
			          </div>
				</div>
			</div>
		</div>
	</div>
	
	
	
		<script type="text/javascript">

	
		$(document).ready(function() {
	
		    $('#mytable')
			.removeClass( 'display' )
			.addClass('table table-striped table-bordered');
				
	
// 	    $('.afficheDetail').click(function () {  
// 		   	 var $row = $(this).closest("tr");    // Find the row
// 		   	 var titre = $row.find('td').eq(1).text();// Find the text
// 		   	 var date = $row.find('td').eq(2).text();
// 		   	 var descriptionRiverain = $row.find('td').eq(4).text();
// 		   	 $("#titre").val(titre);
// 		   	 $("#date").val(date);
// 		   	 $("#descriptionRiverain").val(descriptionRiverain);
// 		     });
		 
		});

		$('#envoyerMission').click(function () { 
			$("#formInspecteur").append('<input type="hidden" name="action" value="envoyer"/>');
			$("#formInspecteur").submit();
		});

		$('#clotureMission').click(function () { 
			$("#formInspecteur").append('<input type="hidden" name="action" value="cloturer"/>');
			$("#formInspecteur").submit();
		});
		
		function removeRecord (index) {
			
			 var $li = $(this).closest("li");    // Find the row
			$li.remove();
		    var $row = $('#mytable').children('tbody').children('tr')[index];    // Find the row
		    $($row).show(); 
		    
		}
		
		    var table = $('#mytable').dataTable({"sPaginationType": "full_numbers",
	    	"bLengthChange": true,  
			"ordering": false,
			"bInfo" : true	,
	        "oLanguage": {
				"sSearch": "Rechercher:",
				"sEmptyTable": "Aucune recherche ne correspond à vos critères.",
				"sInfo": "Page _START_ sur _END_ (_TOTAL_ lignes au total) ",
				"sInfoEmpty": "Aucune recherche ne correspond",
				"sInfoFiltered": "_MAX_ filtre exécuté",
				"sLengthMenu": '<div class="form-group"><label class="col-md-3 control-label" for="date">Show</label><div class="col-md-8"><select class="form-control">'+
		        	'<option value="10">10</option>'+
		        	'<option value="20">20</option>'+
		        	'<option value="30">30</option>'+
		        	'<option value="40">40</option>'+
		        	'<option value="50">50</option>'+
		        	'<option value="-1">Tous</option>'+
		        	'</select></div></div>',
				"sLoadingRecords": "Chargement...",
				"sZeroRecords": "Aucune recherche ne correspond",
				"oPaginate": {
			        "sFirst": "Début",
			        "sLast": "Fin",
			        "sNext": "Suivant",
			        "sPrevious": "Précedent"
			    }
			},
		});

		    function refreshTableFromJSON() {
				//Interrogation du serveur pour récupérer la réponse JSON
				$.getJSON( "./containers/demande_riverain.json", function(data) {
					drawTable(data);
					$('.afficheDetail').click(function () {  
						var $row = $(this).closest("tr");    // Find the row
					   	 var idDemande = $row.find('td').eq(0).text();
					   	 var titre = $row.find('td').eq(1).text();// Find the text
					   	 var date = $row.find('td').eq(2).text();
					   	 var descriptionRiverain = $row.find('td').eq(4).text();
					   	 $("#titre").val(titre);
					   	$("#titreHidden").val(titre);
					   	 $("#date").val(date);
					   	$("#dateHidden").val(date);
					   	 $("#descriptionRiverain").val(descriptionRiverain);
					   	$("#descriptionHidden").val(descriptionRiverain);
					   	$("#idDemande").val(idDemande);
					 });
				});
			}
			
			/**
			 * //Permet de remplir le tableau avec les données du JSON
			 * @param data
			 * 			Les données du tableau
			 */
			function drawTable(data) {
				//On vide le contenu de l'ancien tableau
				$("#bodytable").html("");
				//Construction du nouveau tableau
			    for (var i = 0; i < data.length; i++) {
			        drawRow(data[i]);
			    }
			}

			/**
			 * Permet d'écrire les lignes d'un tableau
			 * @param rowData
			 * 			Les données de la ligne
			 */
			function drawRow(rowData) {
			    var row = $("<tr />");
			    $("#bodytable").append(row); //this will append tr element to table... keep its reference for a while since we will add cels into it
			    row.append($("<td>" + rowData.idDemande + "</td>"));
			    row.append($("<td>" + rowData.titre + "</td>"));
			    row.append($("<td>" + rowData.date + "</td>"));
			    row.append($("<td>" + rowData.adresse + "</td>"));
			    row.append($("<td hidden>" + rowData.description + "</td>"));
			    row.append("<td class=' ''><div ><a  class='afficheDetail'><span class='glyphicon glyphicon-arrow-right'></span></a></div></td>");
			}

			refreshTableFromJSON();

			setInterval(function(){
				refreshTableFromJSON();
			}, 5000);
		</script>
</body>
</html>