<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<form method="post" action="envoieFichier" enctype="multipart/form-data">
		quel site ? <select name="site">
			<c:forEach items="${listeSite}" var="item" varStatus="status">
				<option value="${status.index}">${item}</option>
			</c:forEach>
		</select> 
		<br /> 
		quel script ? <select name="script">
			<c:forEach items="${listeScript}" var="item" varStatus="status">
				<option value="${status.index}">${item}</option>
			</c:forEach>
		</select>
		<input type="file" name="file" size="40000">
		<button type="submit" value="Valider"/>
	</form>
</body>
</html>