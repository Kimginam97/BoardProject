package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class UserDAO {
	
	private static UserDAO instance = new UserDAO();
    //.jsp페이지에서 DB연동빈인 UserDAO클래스의 메소드에 접근시 필요
    public static UserDAO getInstance() {
        return instance;
    }
	
    //커넥션풀로부터 Connection객체를 얻어냄
    private Connection getConnection() throws Exception {
        Context initCtx = new InitialContext();
        Context envCtx = (Context) initCtx.lookup("java:comp/env");
        DataSource ds = (DataSource)envCtx.lookup("jdbc/orcl");
        return ds.getConnection();
    }
	
	
	//회원가입
	public int join(User user)throws Exception {
		Connection conn = null;
        PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql="";
		try {
			conn=getConnection();
			sql="INSERT INTO USERS VALUES (?, ?, ?, ?, ?)";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, user.getUserID());
			pstmt.setString(2, user.getUserPassword());
			pstmt.setString(3, user.getUserName());
			pstmt.setString(4, user.getUserGender());
			pstmt.setString(5, user.getUserEmail());
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (rs != null) try { rs.close(); } catch(SQLException ex) {}
            if (pstmt != null) try { pstmt.close(); } catch(SQLException ex) {}
            if (conn != null) try { conn.close(); } catch(SQLException ex) {}
		}
		return -1;  //회원가입문제
	}
}
 