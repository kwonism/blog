package com.cos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.cos.dto.MemberVO;
import com.cos.util.DBManager;

public class MemberDAO {
	private PreparedStatement pstmt;
	private ResultSet rs;

	//회원가입
	public int insert(MemberVO member) {
		String SQL = "INSERT INTO member VALUES(?,?,?,?,?,false)";
		Connection conn = DBManager.getConnection();
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getPasswoard());
			pstmt.setString(3, member.getUsername());
			pstmt.setString(4, member.getEmail());
			pstmt.setString(5, member.getSalt());
			pstmt.executeUpdate();
			return 1;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("실패");
		} finally {
			DBManager.close(conn, pstmt);
		}
		return -1;
	}
	
	
	public int select_emailcheck(String id) {
		boolean emalicheck = false;
		String SQL = "SELECT emailcheck FROM member where id = ?";
		Connection conn = DBManager.getConnection();
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				boolean emailcheck = rs.getBoolean("emailcheck");
				if (emailcheck == true) {
					return 1;
				} else {
					return 2;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt, rs);
		}
		return -1; // DB오류
	}
	
	
	public String select_email(String id) {
		String SQL = "SELECT email FROM member where id = ?";
		Connection conn = DBManager.getConnection();
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				String email = rs.getString("email");
				return email;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return null;
	}
	
	//salt 값 가져오기 
	public String select_salt(String id) {
		String SQL = "SELECT salt FROM member where id = ?";
		Connection conn = DBManager.getConnection();
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				String salt = rs.getString("salt");
				return salt;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int update_emailcheck(String id) {
		String SQL = "UPDATE member SET emailcheck = true WHERE id = ?";
		Connection conn = DBManager.getConnection();
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, id);
		    pstmt.executeUpdate();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt);
		}
		return -1;
	}
	public int login(MemberVO data) {
		String SQL = "SELECT id,emailcheck FROM member WHERE id = ? and password = ?";
		Connection conn = DBManager.getConnection();
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, data.getId());
			pstmt.setString(2, data.getPasswoard());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getBoolean("emailcheck")) {
					return 1;
				}else {
					return 2;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
		
	}
}