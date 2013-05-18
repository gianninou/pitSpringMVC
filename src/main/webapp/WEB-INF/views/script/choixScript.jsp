<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link 	href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css" />
<script	src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"></script>
<script	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>

<script>
$(document).ready(function() {
    $("input#autocomplete").autocomplete({
    	source: [
    	        <c:forEach items="${listeSite}" var="item" varStatus="status">
    	        	"${item.value}",
				</c:forEach>
					
    	         ""]    	
	});
  });
</script>

<title>Insert title here</title>
</head>
<body>
	<div>
		<fieldset>
			<legend>Choix du site et du script</legend>
			<form method="post"
				action="${pageContext.request.contextPath}/script/choixScript"
				enctype="application/x-www-form-urlencoded">
				
				<label>quel site ? </label> <input id="autocomplete" name="site">
				
				<!-- <select name="site">
					<c:forEach items="${listeSite}" var="item" varStatus="status">
						<option value="${status.index}">${item}</option>
					</c:forEach>
				</select>-->
				
				<br /> <label>quel script ? </label> <select name="script">
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