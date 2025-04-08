<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="dto.*"%>
<%@ page import="model.*"%>
<%@ page import="java.util.*" %>
<%

	int num = Integer.parseInt(request.getParameter("num"));
	String pass = request.getParameter("pass");

	BoardDao boardDao = new BoardDao();

	if(boardDao.deleteBoard(num, pass)) {
		System.out.println("삭제성공");
		
		response.sendRedirect("/poll/board/boardList.jsp");
	} else {
		System.out.println("삭제실패");
		
		response.sendRedirect("/poll/board/boardList.jsp");
	}
%>