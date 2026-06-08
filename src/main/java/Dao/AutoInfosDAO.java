package Dao;
import Entities.AutoEcoleInfo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
public class AutoInfosDAO {

	private static Connection conn =connection.getInstance();
	    public List<AutoEcoleInfo> findAll() {
	        List<AutoEcoleInfo> autoEcoleInfoList = new ArrayList<>();
	        Statement stmt=null;
			ResultSet rs=null;

	        String SQL = "SELECT * FROM autoinfo";  // Assuming the table is named `auto_info`

	        try {

				stmt=conn.createStatement();

				rs=stmt.executeQuery(SQL);

				while (rs.next()) {
	              
					String nom = rs.getString("nom");
					String email = rs.getString("email");
					int tel = rs.getInt("tel");
					String adress = rs.getString("adress");
					AutoEcoleInfo infos = new AutoEcoleInfo(nom,email,tel,adress);
	                autoEcoleInfoList.add(infos);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        
	        return autoEcoleInfoList;
	    }
	}


