<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="dto.*" %>
<%@ page import ="model.*" %>
<%@ page import="java.util.*" %>
<%
	String enddate = request.getParameter("enddate");
	int qnum = Integer.parseInt(request.getParameter("qnum"));
	
	QuestionDao questionDao = new QuestionDao();
	questionDao.updateQuestionEnddate(qnum,enddate);
	
	response.sendRedirect("/poll/pollList.jsp");
	
%>