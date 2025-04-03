<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "dto.*" %>
<%@ page import = "model.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%
	// question 테이블 리스트 -> 페이징 -> title링크(startdate <= 오늘날짜 <= enddate) -> 투표프로그램
	// QuestionDao.selectQuestionList(Paging)
	int currentPage = 1;
	if(request.getParameter("currentPage") != null) {
		currentPage = Integer.parseInt(request.getParameter("currentPage"));
	}
	int rowPerPage = 3;
	Paging paging = new Paging();
	paging.setCurrentPage(currentPage);
	paging.setRowPerPage(rowPerPage);
	
	QuestionDao questionDao = new QuestionDao();
	ArrayList<Question> list = questionDao.selectQuestion(paging);
	// System.out.println(list.size());
	
	int totalCount = questionDao.getTotalQuestionCount();
	 
	int lastPage = (int) Math.ceil((double) totalCount / rowPerPage);
	
	Calendar today = Calendar.getInstance();
    today.set(Calendar.HOUR_OF_DAY, 0);
    today.set(Calendar.MINUTE, 0);
    today.set(Calendar.SECOND, 0);
    today.set(Calendar.MILLISECOND, 0);
    
    Date todayDate = today.getTime();
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>pollList</title>
</head>
<body>
	<h1>설문리스트</h1>
	<!-- foreach문 ArrayList<Question> list 출력 title링크(startdate <= 오늘날짜 <= enddate) 투표시작전, 투표종료, 투표하기 -->
	<table border="1">
		<tr>
			<td>번호</td>
			<td>제목</td>
			<td>시작일</td>
			<td>종료일</td>
			<td>항목</td>
			<td>투표하기</td>
		</tr>
		
		<%
			for(Question q : list) {
		%>
				<tr>
        			<td><%= q.getNum() %></td> 
    			
    				<td><%= q.getTitle() %></td>
    			
    				<td><%= q.getStartdate() %></td>
    			
    				<td><%= q.getEnddate()%></td>
    			
    				<td><%= q.getType()%></td>
    				
    				<td>
    					<%
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					    Date startDate = sdf.parse(q.getStartdate());
					    Date endDate = sdf.parse(q.getEnddate());
	
				        // 날짜 비교
				        if (todayDate.before(startDate)) {
				        	%>투표시작전<%
				        } else if (todayDate.after(endDate)) {
				        	%>투표종료<%
				        } else {
				        	%><a href="">[투표하기]</a><%
				        }
						%>
    				</td>
    			</tr>
		<% 		
			}
		%>
		
	</table>
	<%
		if(currentPage > 1) {
	%>
			<a href ="/poll/pollList.jsp?currentPage=<%=currentPage-1%>">[이전]</a>
	<% 		
		}
	%>
	<%
		if(currentPage < lastPage) {
	%>
			<a href ="/poll/pollList.jsp?currentPage=<%=currentPage+1%>">[다음]</a>
	<% 		
		}
	%>
	<p>현재페이지: <%=currentPage%> / <%=lastPage%> </p>
</body>
</html>