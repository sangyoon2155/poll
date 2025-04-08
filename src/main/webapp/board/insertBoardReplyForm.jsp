<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	int ref = Integer.parseInt(request.getParameter("ref"));
	int pos = Integer.parseInt(request.getParameter("pos"));
	pos = pos + 1;
	int depth = Integer.parseInt(request.getParameter("depth"));
	depth = depth + 1;
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
	<h1>글입력</h1>
	<form action="/poll/board/insertBoardReplyAction.jsp">
				<input type="hidden" id="ref" name="ref" value="<%=ref%>" readonly>
	            <input type="hidden" id="pos" name="pos" value="<%=pos%>" readonly>
	            <input type="hidden" id="depth" name="depth" value="<%=depth%>" readonly>
		<table class="table table-striped">
			<tr>
				<td>부모 ref와 동일</td>
				<td><input type="text" name="ref" value="<%=ref%>" readonly></td>
			</tr>
			<tr>
				<td>부모 pos + 1</td>
				<td><input type="text" name="pos" value="<%=pos%>" readonly></td>
			</tr>
			<tr>
				<td>부모 depth + 1</td>
				<td><input type="text" name="depth" value="<%=depth%>" readonly></td>
			</tr>
			
			<tr>
				<td>name</td>
				<td><input type="text" name="name"></td>
			</tr>
			<tr>
				<td>subject</td>
				<td><input type="text" name="subject"></td>
			</tr>
			<tr>
				<td>content</td>
				<td><textarea name="content" rows="5" cols="50"></textarea></td>
			</tr>
			<tr>
				<td>pass</td>
				<td><input type="password" name="pass"></td>
			</tr>
		</table>
		<button type="submit">글쓰기</button>
	</form>
</body>
</html>