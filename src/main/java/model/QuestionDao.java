package model;

import dto.Question;
import dto.Paging;
import java.sql.*;
import java.util.*;


// Table : question crud
public class QuestionDao  {
	
	// 입력 후 자동으로 생성된 키값을 반환값
	public int insertQuestion(Question question) throws ClassNotFoundException, SQLException {
		int pk = 0;
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = null;
		PreparedStatement stmt = null;
		// 입력이지만 키값을 받아올때 사용
		ResultSet rs = null;
		String sql = "insert into question(title, startdate, enddate, type) values(?,?,?,?)";
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poll", "root", "java1234");
		// statement.RETURN_GENERATED_KEYS 옵션 : insert 후 select max(pk) from ... 실행
		stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, question.getTitle());
		stmt.setString(2, question.getStartdate());
		stmt.setString(3, question.getEnddate());
		stmt.setInt(4, question.getType());
		int row = stmt.executeUpdate(); // insert
		rs = stmt.getGeneratedKeys(); // select max(num) from question
		if(rs.next()) {
			pk = rs.getInt(1);
		}
		conn.close();
		return pk;
	}
	
	// startpage endpage 페이징
	public ArrayList<Question> selectQuestion(Paging p) throws  ClassNotFoundException, SQLException {
		ArrayList<Question> list = new ArrayList<>();
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "select num, title, startdate startDate, enddate endDate, createdate createDate, type from question order by num desc limit ?, ?";
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poll", "root", "java1234");
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, p.getBeginRow());
		stmt.setInt(2, p.getRowPerPage());
		rs = stmt.executeQuery();
		
		while(rs.next()) {
			Question q = new Question();
			
			q.setNum(rs.getInt("num"));
			q.setTitle(rs.getString("title"));
			q.setStartdate(rs.getString("startDate"));
			q.setEnddate(rs.getString("endDate"));
			q.setCreatedate(rs.getString("createDate"));
			q.setType(rs.getInt("type"));
			
			list.add(q);
		}
		
		conn.close();
		
		return list;
	}
	
	public int getTotalQuestionCount() throws  ClassNotFoundException, SQLException {
		int totalCount = 0;
		Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poll", "root", "java1234");
        String sql = "SELECT COUNT(*) FROM question";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            totalCount = rs.getInt(1); // 첫 번째 컬럼 값이 총 개수
        }
        
        conn.close();
		return totalCount;
	}
}
