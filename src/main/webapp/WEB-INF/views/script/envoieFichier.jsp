<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head> 
<body>
	<div>
		<fieldset>
			<legend>Envoie du fichier pour le script</legend>
			<label>Votre script doit contenir les colonnes :</label>
			<ul>
				<c:forEach items="${listeEntete}" var="entete" varStatus="status">
					<li>${entete}</li>
				</c:forEach>
			</ul>




			<form method="post"
				action="${pageContext.request.contextPath}/script/execution"
				enctype="multipart/form-data">

				<label>Voullez vous que l'INPN conserve vos données ?</label> 
				<INPUT id="oui" type="radio" name="conservation" value="1" checked="checked"><label for="oui">oui</label>
				<INPUT id="non" type="radio" name="conservation" value="0"><label for="non">non</label>
				<br />
				<label for="fichier">Selectionner un fichier : </label><input id="fichier" type="file" name="file" size="40000"><br/>
				<label for="outputName">Nom des fichiers de sortie</label> <input type="text" id="outputName" name="outputName"><br/>
				<button type="submit">Envoyer et executer</button>
			</form>
			<form action="${pageContext.request.contextPath}/script/choixScript" method="get">
				<button type="submit">Retour au début</button>
			</form>
		</fieldset>
	</div>
</body>
</html>