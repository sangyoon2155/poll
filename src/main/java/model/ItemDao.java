package model;

import java.sql.*;
import java.util.*;


import dto.*;

// Table: item crud
public class ItemDao {
	public void insertItem(Item item) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = null;
		PreparedStatement stmt = null;
		String sql = "insert into item(qnum, inum, content) values(?,?,?)";
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poll", "root", "java1234");
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, item.getQnum());
		stmt.setInt(2, item.getInum());
		stmt.setString(3, item.getContent());
		int row = stmt.executeUpdate();
		if(row == 1) {
			System.out.println("ItemDao.insertItem - 입력성공");
		} else {
			System.out.println("ItemDao.insertItem - 입력실패");
		}
		conn.close();
	}
	
	public void deleteItem(int qnum) throws ClassNotFoundException, SQLException { // item 테이블값 삭제
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = null;
		PreparedStatement stmt = null;
		String sql = "delete from item where qnum = ?";
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poll", "root", "java1234");
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, qnum);
		int row = stmt.executeUpdate();
		conn.close();
	}
	
	public ArrayList<Item> selectItem(int qnum) throws ClassNotFoundException, SQLException {
		ArrayList<Item> list = new ArrayList<>();
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "select qnum, inum, content, count from item where qnum = ?";
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/poll", "root", "java1234");
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, qnum);
		
		rs = stmt.executeQuery();
		
		while(rs.next()) {
			Item i = new Item();
			i.setQnum(rs.getInt("qnum"));
			i.setInum(rs.getInt("inum"));
			i.setContent(rs.getString("content"));
			i.setCount(rs.getInt("count"));
			list.add(i);
		}
		conn.close();
		
		return list;
	}
}
