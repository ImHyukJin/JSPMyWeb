package com.myweb.user.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.myweb.util.JdbcUtil;

public class UserDAO {

	//싱글톤
	//1.
	private static UserDAO instance = new UserDAO();
	//2. 생성자 private
	private UserDAO() {
		try {
			//드라이버 호출문장
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//3. getter메소드
	public static UserDAO getInstance() {
		return instance;
	}
	///////////////////////////////
	//나는 원격이라 172.30.1.81 (호스트 이름) 
	private String url = JdbcUtil.url;
	private String uid = JdbcUtil.uid;
	private String upw = JdbcUtil.upw;
	
	public int idcheck(String id) {
		int result = 0 ;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select * from users where id = ?";
		
		try {
				//conn 객체생성
		conn =	DriverManager.getConnection(url, uid , upw);
		//pstmt 객체생성
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, id);
		//sql실행 -> select는 exceuteQuery , DML -> executeUpdate
		rs = pstmt.executeQuery();
		
		if(rs.next()) { //true 라는 것은 -> 결과가 있다는 뜻
			result = 1; //중복의 의미
		}else { //false라는 것은 -> 중복된 결과가 없다는 뜻
			result = 0 ; //중복이 없다는 의미
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
				
			
		}
		return result;
	}
}
