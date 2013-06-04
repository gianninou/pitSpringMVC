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
			<legend>Résultats du script</legend>

			<c:forEach items="${listeFichier}" var="fichier" varStatus="status">
				<a href="${pageContext.request.contextPath}/script/getFile?id=${fichier.key}">${fichier.value[1]}</a>
				<br />
				<!--<c:url value="getFile?id=${fichier.key}" >${fichier.value}</c:url><br />
		 ${fichier.key} : ${fichier.value}<br />  -->
			</c:forEach>
			<form action="${pageContext.request.contextPath}/script/choixScript" method="get">
				<button type="submit">Début</button>
			</form>
		</fieldset>
	</div>
</body>
</html>