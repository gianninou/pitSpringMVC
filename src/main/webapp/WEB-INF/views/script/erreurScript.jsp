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
			<legend>Erreur de fichier</legend>
			Le fichier que vous avez envoyé ne convient pas au script séléctionné ou contient des erreurs.
			Vérifiez également les entêtes demandées. 
			Veuillez cliquer sur le bouton pour recommencer
			<form action="${pageContext.request.contextPath}/script/choixScript" method="get">
				<button type="submit">Recommencer</button>
			</form>
		</fieldset>
	</div>
</body>
</html>