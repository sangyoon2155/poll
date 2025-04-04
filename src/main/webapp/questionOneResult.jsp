<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.*" %>
<%@ page import="dto.*" %>
<%@ page import="java.util.*" %>
<%
	int qnum = Integer.parseInt(request.getParameter("qnum"));

	QuestionDao questionDao = new QuestionDao();
	Question question = questionDao.selectQuestion(qnum);
	
	ItemDao itemDao = new ItemDao();
	ArrayList<Item> itemList = itemDao.selectItem(qnum);
	
	int totalCount = itemDao.selectItemCountByQnum(qnum);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
</head>
<body>
	<div>
		<jsp:include page="/inc/nav.jsp"></jsp:include>
	</div>
	
	<h1><%=qnum%>번 설문 투표결과</h1>
	<table border="1" width="80%">
		<tr>
			<td colspan="4">
				Q : <%=question.getTitle()%>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				총 투표수 : <%=totalCount%>
			</td>
		</tr>
		<tr>
			<td>번호</td><td>내용</td><td>카운트</td><td>카운트</td>
		</tr>
		
		<%
			for(Item i : itemList) {
		%>
				<tr>
					<td><%=i.getInum()%></td>
					<td><%=i.getContent()%></td>
					<td>
						<%
							int percentage = (int)(Math.round((double)i.getCount() / (double)totalCount * 100));
							
							for(int n = 1; n<=percentage; n=n+1) {
						%>
								*
						<% 
							}
						%>
						
					</td>
					<td><%=i.getCount()%></td>
				</tr>
		<% 
			}
		%>
	</table>
</body>
</html>