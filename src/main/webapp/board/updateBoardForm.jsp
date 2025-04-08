<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="dto.*" %>
<%@ page import="model.*" %>
<%
	int num = Integer.parseInt(request.getParameter("num"));
	BoardDao boardDao = new BoardDao();
	Board b = boardDao.selectBoardOne(num);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
</head>
<body>
	<h1>글 수정</h1>
	<form method="post" action="/poll/board/updateBoardAction.jsp">
		<input type="hidden" name="num" value="<%= num %>">
		<table class="table table-striped">
			<tr>
				<td>name</td>
				<td><input type="text" name="name" value="<%=b.getName()%>"></td>
			</tr>
			<tr>
				<td>subject</td>
				<td><input type="text" name="subject" value="<%=b.getSubject()%>"></td>
			</tr>
			<tr>
				<td>content</td>
				<td><textarea name="content" rows="5" cols="50"><%= b.getContent() %></textarea></td>
			</tr>
			<tr>
				<td>pass</td>
				<td><input type="password" name="pass"></td>
			</tr>
		</table>
		<button type="submit">수정하기</button>
	</form>
</body>
</html>