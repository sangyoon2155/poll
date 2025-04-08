<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
</head>
<body>
	<form action="/poll/board/deleteBoardAction.jsp" method="post">
		비밀번호 입력 :
		<input type="password" name="pass">
		<br>
		<input type="hidden" name="num" value="<%= request.getParameter("num") %>">
		<input type="submit" value="삭제하기">
	</form>
</body>
</html>