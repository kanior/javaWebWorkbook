package spms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import spms.vo.Member;

public class MySqlMemberDao implements MemberDao{
	DataSource ds;
	
	public void setDataSource(DataSource ds) {
		this.ds = ds;
	}
	
	public List<Member> selectList() throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(
					"SELECT MNO, MNAME, EMAIL, CRE_DATE "
					+ "FROM MEMBERS "
					+ "ORDER BY MNO ASC");
			
			ArrayList<Member> members = new ArrayList<>();
			
			while (rs.next()) {
				members.add(
						new Member()
							.setNo(rs.getInt("MNO"))
							.setName(rs.getString("MNAME"))
							.setEmail(rs.getString("EMAIL"))
							.setCreatedDate(rs.getDate("CRE_DATE"))
						);
			}
			
			return members;
			
		} catch (Exception e) {
			throw e;
		} finally {
			try {if (rs != null) rs.close();} catch (Exception e) {}
			try {if (stmt != null) stmt.close();} catch (Exception e) {}
			try {if (conn != null) conn.close();} catch (Exception e) {}
		}
	}
	
	public int insert(Member member) throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = ds.getConnection();
			stmt = conn.prepareStatement(
					"INSERT INTO MEMBERS (EMAIL, PWD, MNAME, CRE_DATE, MOD_DATE) "
					+ "VALUES (?, ?, ?, NOW(), NOW())");
			stmt.setString(1, member.getEmail());
			stmt.setString(2, member.getPassword());
			stmt.setString(3, member.getName());
			int rowNum = stmt.executeUpdate();
			
			return rowNum;
			
		} catch (Exception e) {
			throw e;
		} finally {
			try {if (stmt != null) stmt.close();} catch (Exception e) {}
			try {if (conn != null) conn.close();} catch (Exception e) {}
		}
	}
	
	public int delete(int no) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			int rowNum = stmt.executeUpdate(
							"delete from MEMBERS "
							+ "where MNO=" + no);
			
			return rowNum;
			
		} catch (Exception e) {
			throw e;
		} finally {
			try {if (stmt != null) stmt.close();} catch (Exception e) {}
			try {if (conn != null) conn.close();} catch (Exception e) {}
		}
		
	}
	
	public Member selectOne(int no) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(
					"select MNO, EMAIL, MNAME, CRE_DATE "
					+ "from MEMBERS "
					+ "where MNO=" + no);
			
			rs.next();
			Member member = new Member()
							.setNo(Integer.parseInt(rs.getString("MNO")))
							.setEmail(rs.getString("EMAIL"))
							.setName(rs.getString("MNAME"))
							.setCreatedDate(rs.getDate("CRE_DATE"));
			
			return member;
			
		} catch (Exception e) {
			throw e;
		} finally {
			try {if (rs != null) rs.close();} catch (Exception e) {}
			try {if (stmt != null) stmt.close();} catch (Exception e) {}
			try {if (conn != null) conn.close();} catch (Exception e) {}
		}
	}
	
	public int update(Member member) throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = ds.getConnection();
			stmt = conn.prepareStatement(
					"update MEMBERS "
					+ "set EMAIL=?, MNAME=?, MOD_DATE=now() "
					+ "where MNO=? ");
			stmt.setString(1, member.getEmail());
			stmt.setString(2, member.getName());
			stmt.setInt(3, member.getNo());
			int rowNum = stmt.executeUpdate();
			
			return rowNum;
			
		} catch (Exception e) {
			throw e;
		} finally {
			try {if (stmt != null) stmt.close();} catch (Exception e) {}
			try {if (conn != null) conn.close();} catch (Exception e) {}
		}
	}
	
	public Member exist(String email, String password) throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = ds.getConnection();
			stmt = conn.prepareStatement(
					"SELECT MNAME, EMAIL "
					+ "FROM MEMBERS "
					+ "WHERE EMAIL=? AND PWD=?");
			stmt.setString(1, email);
			stmt.setString(2, password);
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				Member member = new Member()
								.setEmail(rs.getString("EMAIL"))
								.setName(rs.getString("MNAME"));
				
				return member;
			}
			
			return null;
			
		} catch (Exception e) {
			throw e;
		} finally {
			try {if (rs != null) rs.close();} catch (Exception e) {}
			try {if (stmt != null) stmt.close();} catch (Exception e) {}
			try {if (conn != null) conn.close();} catch (Exception e) {}
		}
	}

}
