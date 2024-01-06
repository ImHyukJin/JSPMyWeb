package com.myweb.user.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import com.myweb.util.JdbcUtil;

public class UserDAO {
	
	//싱글톤
	//1. 객체 1개 생성
	private static UserDAO instance = new UserDAO();
	//2. 생성자 private
	private UserDAO() {
		
		try {
			//드라이버 호출문장
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
		} catch (Exception e) {
		}
		
	}
	//3. getter메서드
	public static UserDAO getInstance() {
		return instance;
	}
	
	/////////////////////////////////////////////
	private String url = JdbcUtil.url;
	private String uid = JdbcUtil.uid;
	private String upw = JdbcUtil.upw;
	
	
	public int idCheck(String id) {
		int result = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select * from users where id = ?";
		
		try {
			//conn객체생성
			conn = DriverManager.getConnection(url, uid, upw);
			//pstmt객체생성
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			//sql실행 -> select는 executeQuery, DML -> executeUpdate
			rs = pstmt.executeQuery();
			
			if( rs.next() ) { //true라는 것은 -> 결과가 있다는 뜻
				result = 1; //중복의 의미
			} else { //false라는 것은 -> 중복된 결과가 없다는 뜻
				result = 0; //중복이 x
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
		return result;
	}
	//회원가입기능
	public void insertUser(UserVO vo) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "insert into users(id, pw, name, email, address, gender) "
					+"values(?, ?, ?, ?, ?, ?)";
		
		try {
			
			conn = DriverManager.getConnection(url, uid, upw);
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getId() );
			pstmt.setString(2, vo.getPw() );
			pstmt.setString(3, vo.getName() );
			pstmt.setString(4, vo.getEmail() );
			pstmt.setString(5, vo.getAddress() );
			pstmt.setString(6, vo.getGender() );
			
			pstmt.executeUpdate();
						
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, null);
		}
		
		
	}
	
	//로그인기능
	public UserVO login(String id, String pw) {
		
		UserVO vo = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select * from users where id = ? and pw = ?";
		
		try {
			
			conn = DriverManager.getConnection(url, uid, upw);
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) { //로그인 성공(UserVO에 필요한 값을 저장)
				
				vo = new UserVO();
				vo.setId(id);
				vo.setName( rs.getString("name") );
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
	
		return vo;
	}
	
	//회원의 정보를 조회
	public UserVO getUserInfo(String id) {
		
		UserVO vo = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select * from users where id = ?";
		
		try {
			
			conn = DriverManager.getConnection(url, uid, upw);
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) { //다음이 있다면 true
				
				//데이터 ORM작업
				String name = rs.getString("name");
				String email = rs.getString("email");
				String address = rs.getString("address");
				String gender =rs.getString("gender");
				Timestamp regdate = rs.getTimestamp("regdate");
				
				vo = new UserVO(id, null, name, email, address, gender, regdate);
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}

		return vo;
	}
	
	//회원 정보를 수정
	public int update(UserVO vo) {
		int result = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "update users set pw = ? , name = ? , email = ? , address = ? , gender = ? where id = ?";
		
		try {
			conn = DriverManager.getConnection(url , uid , upw);
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,vo.getPw());
			pstmt.setString(2,vo.getName());
			pstmt.setString(3,vo.getEmail());
			pstmt.setString(4,vo.getAddress());
			pstmt.setString(5,vo.getGender());
			pstmt.setString(6,vo.getId());
			
			result = pstmt.executeUpdate(); // 0이면 실패 , 1이면 성공
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.close(conn, pstmt , null);
		}
		
		return result;
	}
	
	
	
	
	
	
	
	
	
}
