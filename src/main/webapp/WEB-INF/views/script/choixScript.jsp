<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div>
		<fieldset>
			<legend>Choix du site et du script</legend>
			<form method="post" action="${pageContext.request.contextPath}/script/choixScript"
				enctype="application/x-www-form-urlencoded">
				<label>quel site ? </label> <select name="site">
					<c:forEach items="${listeSite}" var="item" varStatus="status">
						<option value="${status.index}">${item}</option>
					</c:forEach>
				</select><br /> <label>quel script ? </label> <select name="script">
					<c:forEach items="${listeScript}" var="item" varStatus="status">
						<option value="${status.index}">${item}</option>
					</c:forEach>
				</select>
				<button type="submit">Valider</button>
			</form>
		</fieldset>
	</div>
</body>
</html>