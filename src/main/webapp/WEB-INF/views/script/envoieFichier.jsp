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
			<p>Votre script doit contenir les colonnes :</p>
			<c:forEach items="${listeEntete}" var="entete" varStatus="status">
				${entete}<br />
			</c:forEach>
			<form method="post" action="${pageContext.request.contextPath}/script/envoieFichier"
				enctype="multipart/form-data">

				<input type="file" name="file" size="40000">
				<button type="submit">Envoyer</button>
			</form>
		</fieldset>
	</div>
</body>
</html>