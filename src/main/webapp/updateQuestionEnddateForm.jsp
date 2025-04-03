<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="dto.*" %>
<%@ page import="model.*" %>
<%
	int qnum = 0;
	if(request.getParameter("qnum") != null){
		qnum = Integer.parseInt(request.getParameter("qnum"));
	}
	
	QuestionDao questionDao = new QuestionDao();
	
	Question question = questionDao.selectQuestion(qnum);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
</head>
<body>
	<h1>설문 수정</h1>
	<form method="post" action="/poll/updateQuestionEnddateAction.jsp">
		<input type="hidden" name="qnum" value="<%=question.getNum()%>">
		<table border="1">
			<tr>
				<td>질문</td>
				<td colspan="2">
					<input type="text" name="title" value="<%=question.getTitle()%>" readonly>
				</td>
			</tr>
			<tr>
				<td>시작일</td>
				<td>
					<input type="date" name="startdate" value="<%=question.getStartdate()%>" readonly>
				</td>
			</tr>
			<tr>
				<td>종료일</td>
				<td>
					<input type="date" name="enddate" value="<%=question.getEnddate()%>">
				</td>
			</tr>
		</table>
		<button type="submit">수정하기</button>
	</form>
</body>
</html>