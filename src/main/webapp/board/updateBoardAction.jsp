<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="dto.*" %>
<%@ page import="model.*" %>
<%
    // 입력받은 값 가져오기
    int num = Integer.parseInt(request.getParameter("num"));
    String pass = request.getParameter("pass");  // 입력된 비밀번호
    String name = request.getParameter("name");
    String subject = request.getParameter("subject");
    String content = request.getParameter("content");

    // BoardDao 객체 생성
    BoardDao boardDao = new BoardDao();

    // 비밀번호와 게시글 번호에 해당하는 게시글을 수정
    Board b = new Board();
    b.setNum(num);
    b.setPass(pass); // 비밀번호를 먼저 설정하여 확인

    boolean isPassCorrect = boardDao.verifyPassword(b); // 비밀번호 검증 메서드 호출

    if (isPassCorrect) {
        // 비밀번호가 맞으면 게시글 수정 작업
        b.setName(name);
        b.setSubject(subject);
        b.setContent(content);

        // BoardDao의 updateBoard 메서드 호출하여 게시글 수정
        boardDao.updateBoard(b);  // 게시글 수정 메서드 호출

        // 수정 후 게시판 목록으로 리다이렉트
        response.sendRedirect("/poll/board/boardList.jsp");
    } else {
        // 비밀번호가 틀리면 에러 메시지 출력
        out.println("<script>");
        out.println("alert('비밀번호가 틀립니다.');");
        out.println("history.back();");  // 이전 페이지로 돌아가도록
        out.println("</script>");
    }
%>
