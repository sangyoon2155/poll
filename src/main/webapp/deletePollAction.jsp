<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="dto.*" %>
<%@ page import="model.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%
	int qnum = Integer.parseInt(request.getParameter("qnum"));

	QuestionDao questionDao = new QuestionDao();
	
	if(questionDao.deleteQuestion(qnum)) {
		System.out.println("삭제성공");
		
		response.sendRedirect("/poll/pollList.jsp");
	} else {
		System.out.println("투표한사람이 있습니다. 삭제불가");
		
		response.sendRedirect("/poll/pollList.jsp");
	}

%>