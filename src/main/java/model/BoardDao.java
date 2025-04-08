package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import dto.*;

public class BoardDao {
	
	public boolean verifyPassword(Board b) throws ClassNotFoundException, SQLException {
	    Class.forName("com.mysql.cj.jdbc.Driver");
	    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poll", "root", "java1234");
	    PreparedStatement stmt = conn.prepareStatement("SELECT pass FROM board WHERE num = ?");
	    
	    stmt.setInt(1, b.getNum());  // 게시글 번호를 설정하여 비밀번호를 조회
	    ResultSet rs = stmt.executeQuery();
	    
	    boolean isPassCorrect = false;  // 비밀번호가 맞는지 확인할 변수
	    
	    if (rs.next()) {
	        String dbPass = rs.getString("pass");  // DB에서 가져온 비밀번호
	        if (dbPass.equals(b.getPass())) {
	            isPassCorrect = true;  // 비밀번호가 맞으면 true
	        }
	    }

	    rs.close();
	    stmt.close();
	    conn.close();

	    return isPassCorrect;
	}

	
	
	public void updateBoard(Board b) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "update board set name = ?, subject = ?, content = ?, pass = ? where num = ?";
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poll","root","java1234");
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, b.getName());
		stmt.setString(2, b.getSubject());
		stmt.setString(3, b.getContent());
		stmt.setString(4, b.getPass());
		stmt.setInt(5, b.getNum());
		int row = stmt.executeUpdate(); 
		
		conn.close();
	}
	
	public boolean deleteBoard(int num, String pass) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "delete from board where num = ? and pass = ?";
		boolean isDeleted = false;
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poll","root","java1234");
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, num);
		stmt.setString(2, pass);
		int row = stmt.executeUpdate();
		if (row > 0) {
            isDeleted = true;
        }
		
		return isDeleted;
	}
	
	// 답글 입력
	public void insertBoardReply(Board b) throws ClassNotFoundException, SQLException {
	    Class.forName("com.mysql.cj.jdbc.Driver");
	    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poll", "root", "java1234");

	    // 트랜잭션 시작
	    conn.setAutoCommit(false);

	    // 1. 현재 글과 같은 그룹(ref)인 글들의 pos 값을 증가시켜서 기존 글들의 순서를 밀어냄
	    String sql2 = "UPDATE board SET pos = pos + 1 WHERE ref = ? AND pos >= ?";
	    PreparedStatement stmt2 = conn.prepareStatement(sql2);
	    stmt2.setInt(1, b.getRef());  // 부모 글의 ref 값 사용
	    stmt2.setInt(2, b.getPos());
	    int row2 = stmt2.executeUpdate();

	    // 답글 입력 (ref는 부모글과 동일, pos는 부모글보다 1 더 큰 값, depth는 부모글보다 1 더 큰 값)
	    String sql = "INSERT INTO board (name, subject, content, pos, ref, depth, pass, ip) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	    PreparedStatement stmt = conn.prepareStatement(sql);
	    stmt.setString(1, b.getName());
	    stmt.setString(2, b.getSubject());
	    stmt.setString(3, b.getContent());
	    stmt.setInt(4, b.getPos());  // 부모 글의 pos + 1 값
	    stmt.setInt(5, b.getRef());  // 부모 글의 ref 값 그대로
	    stmt.setInt(6, b.getDepth());  // 부모 글의 depth + 1 값
	    stmt.setString(7, b.getPass());
	    stmt.setString(8, b.getIp());
	    stmt.executeUpdate();

	    // 트랜잭션 커밋
	    conn.commit();
	    conn.close();
	}

		
	// 새글 입력(부모글)
	public void insertBoard(Board b) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null; // 입력직후 PK값을 반환받기 위해
		String sql = "insert into board (name, subject, content, ref, pass, ip) values (?, ?, ?, ?, ?, ?)";
		
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poll", "root", "java1234");
		conn.setAutoCommit(false); // executeUpdate()시마다 자동커밋기능을 false	
		stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, b.getName());
		stmt.setString(2, b.getSubject());
		stmt.setString(3, b.getContent());
		stmt.setInt(4, b.getRef());
		stmt.setString(5, b.getPass());
		stmt.setString(6, b.getIp());
		stmt.executeUpdate();
		// ref == 0이면 입력직후 pk값을 반환 받아서 ref값을 동일하게
		rs = stmt.getGeneratedKeys();
		int pk = 0;
		if (rs.next()) {
			pk = rs.getInt(1);
		}

		PreparedStatement stmt2 = null;
		String sql2 = "update board set ref = ? where num = ?";
		stmt2 = conn.prepareStatement(sql2);
		// update쿼리가 실패하면 이전의 insert도 롤백 : conn.rollback();
		stmt2.setInt(1, pk);
		stmt2.setInt(2, pk);
		stmt2.executeUpdate();
		
		conn.commit(); // conn.setAutoCommit(false) 코드 때문에 필요
		conn.close();
	}
	
	public Board selectBoardOne(int num) throws ClassNotFoundException, SQLException {
		Board b = null;
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "select * from board where num = ?";
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poll","root","java1234");
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, num);
		rs = stmt.executeQuery();
		
		// rs -> board
		if(rs.next()) {
			b = new Board();
			b.setNum(rs.getInt("num"));
			b.setName(rs.getString("name"));
			b.setSubject(rs.getString("subject"));
			b.setContent(rs.getString("content"));
			b.setPos(rs.getInt("pos"));
			b.setRef(rs.getInt("ref"));
			b.setDepth(rs.getInt("depth"));
			b.setRegdate(rs.getString("regdate"));
			b.setIp(rs.getString("ip"));
			b.setCount(rs.getInt("count"));
		}
		conn.close();
		return b;
	}
	
	public ArrayList<Board> selectBoardList(Paging p) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "select * from board order by ref desc, pos asc limit ?, ?";
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poll","root","java1234");
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, p.getBeginRow());
		stmt.setInt(2, p.getRowPerPage());
		rs = stmt.executeQuery();
		ArrayList<Board> list = new ArrayList<>();
		// rs -> list
		while(rs.next()) {
			Board b = new Board();
			b.setNum(rs.getInt("num"));
			b.setName(rs.getString("name"));
			b.setSubject(rs.getString("subject"));
			b.setPos(rs.getInt("pos"));
			b.setRef(rs.getInt("ref"));
			b.setDepth(rs.getInt("depth"));
			b.setCount(rs.getInt("count"));
			list.add(b);
		}
		conn.close();
		return list;
	}
}
