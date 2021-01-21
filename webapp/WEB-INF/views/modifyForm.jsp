<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>전화번호 수정화면</h1>

	<form action="${pageContext.request.contextPath }/phone/modify" method="get">
		이름(name):<input type="text" name="name" value="${personVo.name }"><br>
		핸드폰(hp):<input type="text" name="hp" value="${personVo.hp }"><br>
		회사(company)<input type="text" name="company"
			value="${personVo.company }"><br> <input type="hidden"
			name="personId" value="${personVo.personId }">

		<button type="submit">수정</button>
	</form>
</body>
</html>