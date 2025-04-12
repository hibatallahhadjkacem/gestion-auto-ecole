package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class loginDAO {
	private Connection conn = connection.getInstance();
	
	public int foundUser(String user) {
	    String sql = "SELECT 1 FROM login WHERE userName = ? LIMIT 1"; 
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, user);
	        ResultSet rs = stmt.executeQuery();
	        return rs.next() ? 1 : 0; 
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return -1; 
	    }
	}
	
	public int foundMdp(String mdp) {
	    String sql = "SELECT 1 FROM login WHERE mdp = ? LIMIT 1"; 
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, mdp);
	        ResultSet rs = stmt.executeQuery();
	        return rs.next() ? 1 : 0; 
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return -1; 
	    }
	}
	
	public int found2(String user,String mdp) {
	    String sql = "SELECT 1 FROM login WHERE mdp = ? AND userName = ? LIMIT 1"; 
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, mdp);
	        stmt.setString(2, user);
	        ResultSet rs = stmt.executeQuery();
	        return rs.next() ? 1 : 0; 
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return -1; 
	    }
	}
	
	
}
