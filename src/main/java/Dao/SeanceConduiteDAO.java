package Dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import Entities.Categorie;
import Entities.SeanceCode;
import Entities.SeanceConduite;
import Entities.Vehicule;

public class SeanceConduiteDAO {
	
	
		private Connection conn = connection.getInstance();
		
		public List<SeanceConduite> getAllScConduit() throws SQLException {
		    List<SeanceConduite> conduites = new ArrayList<>();
		    String sql = "SELECT * FROM seanceconduite"; 

		    try (PreparedStatement stmt = conn.prepareStatement(sql);
		         ResultSet rs = stmt.executeQuery()) {

		        while (rs.next()) {
		            int nums = rs.getInt("num");
		            Date date = rs.getDate("date");
		            Time tp = rs.getTime("time");
		            String adresse = rs.getString("lieu");
		            double lalt = rs.getDouble("latitude");
		            double lag = rs.getDouble("longitude");
		            int monit = rs.getInt("idMoniteur");
		            int condidat = rs.getInt("idCondidat");
		            String vehic = rs.getString("idVehicule");

		            SeanceConduite conduite = new SeanceConduite(
		                nums, date.toLocalDate(), tp.toLocalTime(), adresse,
		                lag, lalt, monit, condidat, vehic
		            );

		            conduites.add(conduite);
		        }
		    }

		    return conduites;
		}

		public List<SeanceConduite> getScConduitBytp(LocalTime time) throws SQLException {
		    List<SeanceConduite> conduites = new ArrayList<>();
		    String sql = "SELECT * FROM seanceconduite WHERE time = ?"; 

		    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
		        stmt.setTime(1, java.sql.Time.valueOf(time));
		        try (ResultSet rs = stmt.executeQuery()) {
		            while (rs.next()) {
		                int nums = rs.getInt("num");
		                Date date = rs.getDate("date");
		                Time tp = rs.getTime("time");
		                String adresse = rs.getString("lieu");
		                double lalt = rs.getDouble("latitude");
		                double lag = rs.getDouble("longitude");
		                int monit = rs.getInt("idMoniteur");
		                int condidat = rs.getInt("idCondidat");
		                String vehic = rs.getString("idVehicule");

		                SeanceConduite conduite = new SeanceConduite(
		                    nums, date.toLocalDate(), tp.toLocalTime(), adresse,
		                    lag, lalt, monit, condidat, vehic
		                );

		                conduites.add(conduite);
		            }
		        }
		    }

		    return conduites;
		}

		public List<SeanceConduite> getScConduitBytpDate(LocalTime time, LocalDate dt) throws SQLException {
		    List<SeanceConduite> conduites = new ArrayList<>();
		    String sql = "SELECT * FROM seanceconduite WHERE time = ? AND date = ?"; 

		    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
		        stmt.setTime(1, java.sql.Time.valueOf(time));
		        stmt.setDate(2, java.sql.Date.valueOf(dt));
		        try (ResultSet rs = stmt.executeQuery()) {
		            while (rs.next()) {
		                int nums = rs.getInt("num");
		                Date date = rs.getDate("date");
		                Time tp = rs.getTime("time");
		                String adresse = rs.getString("lieu");
		                double lalt = rs.getDouble("latitude");
		                double lag = rs.getDouble("longitude");
		                int monit = rs.getInt("idMoniteur");
		                int condidat = rs.getInt("idCondidat");
		                String vehic = rs.getString("idVehicule");

		                SeanceConduite conduite = new SeanceConduite(
		                    nums, date.toLocalDate(), tp.toLocalTime(), adresse,
		                    lag, lalt, monit, condidat, vehic
		                );

		                conduites.add(conduite);
		            }
		        }
		    }

		    return conduites;
		}

		public List<SeanceConduite> getScConduitByDate(LocalDate dt) throws SQLException {
		    List<SeanceConduite> conduites = new ArrayList<>();
		    String sql = "SELECT * FROM seanceconduite WHERE date = ?"; 

		    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
		        stmt.setDate(1, java.sql.Date.valueOf(dt));
		        try (ResultSet rs = stmt.executeQuery()) {
		            while (rs.next()) {
		                int nums = rs.getInt("num");
		                Date date = rs.getDate("date");
		                Time tp = rs.getTime("time");
		                String adresse = rs.getString("lieu");
		                double lalt = rs.getDouble("latitude");
		                double lag = rs.getDouble("longitude");
		                int monit = rs.getInt("idMoniteur");
		                int condidat = rs.getInt("idCondidat");
		                String vehic = rs.getString("idVehicule");

		                SeanceConduite conduite = new SeanceConduite(
		                    nums, date.toLocalDate(), tp.toLocalTime(), adresse,
		                    lag, lalt, monit, condidat, vehic
		                );

		                conduites.add(conduite);
		            }
		        }
		    }

		    return conduites;
		}

		
		
		public boolean updateDate(LocalDate date,int num) {
			String sql = "UPDATE seanceconduite SET date= ? WHERE num= ?";
	        
	        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        	stmt.setDate(1, java.sql.Date.valueOf(date));
	        	stmt.setInt(2, num);
	            stmt.executeUpdate();
	            return true;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
		}
		
		public boolean updateTp(LocalTime tp,int num) {
			String sql = "UPDATE seanceconduite SET time= ? WHERE num= ?";
	        
	        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setTime(1, java.sql.Time.valueOf(tp));
	            stmt.setInt(2, num);
	            stmt.executeUpdate();
	            return true;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
		}
		
		public boolean updateMonit(int idMonit,int num) {
			String sql = "UPDATE seanceconduite SET idMoniteur= ? WHERE num= ?";
	        
	        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setInt(1, idMonit);
	            stmt.setInt(2, num);
	            stmt.executeUpdate();
	            return true;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
		}
		
		public boolean updateCondidat(int idCondidat,int num) {
			String sql = "UPDATE seanceconduite SET idCondidat= ? WHERE num= ?";
	        
	        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setInt(1, idCondidat);
	            stmt.setInt(2, num);
	            stmt.executeUpdate();
	            return true;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
		}
		
		public boolean updateVehicule(String immat,int num) {
			String sql = "UPDATE seanceconduite SET idVehicule= ? WHERE num= ?";
	        
	        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setString(1, immat);
	            stmt.setInt(2, num);
	            stmt.executeUpdate();
	            return true;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
		}


		public boolean updateMap(String adresse,double lang, double lant,int num) {
			String sql = "UPDATE seanceconduite SET lieu= ? , latitude=? , longitude=? WHERE num= ?";
	        
	        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setString(1, adresse);
	            stmt.setDouble(2, lant);
	            stmt.setDouble(3, lang);
	            stmt.setInt(4, num);
	            stmt.executeUpdate();
	            return true;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
		}
		
		

		
		public String getMoniteur(int idMonit) {
			String sql = "SELECT last_name,first_name FROM moniteur WHERE id = ? "; 
		    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
		        stmt.setInt(1, idMonit);

		        ResultSet rs = stmt.executeQuery();
		        while (rs.next()) {
		        return  rs.getString("last_name")+"test"+rs.getString("first_name");
		        }  
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return null; 
		    }
			return null;
			
		}


		public String getCondidat(int idcondid) {
			String sql = "SELECT nom,prenom FROM condidat WHERE numero = ? "; 
		    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
		        stmt.setInt(1, idcondid);

		        ResultSet rs = stmt.executeQuery();
		        while (rs.next()) {
		        return  rs.getString("nom")+" "+rs.getString("prenom");
		        }  
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return null; 
		    }
			return null;
			
		}
		
		public String getvehic(String immat) {
			String sql = "SELECT model FROM vehicule WHERE immatricule = ? "; 
		    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
		        stmt.setString(1, immat);

		        ResultSet rs = stmt.executeQuery();
		        while (rs.next()) {
		        return  rs.getString("model");
		        }  
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return null; 
		    }
			return null;
			
		}
		
		public List<SeanceConduite> getScParNum(int num){
			
		       List<SeanceConduite> conduites = new ArrayList<>();
		        String sql = "SELECT * FROM seanceconduite WHERE num = ?"; 

		        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
		            stmt.setInt(1, num); 
		            ResultSet rs =stmt.executeQuery();
		            while (rs.next()) {
		                int nums = rs.getInt("num");
		                Date date = rs.getDate("date");
		                Time tp = rs.getTime("time");
		                String adresse = rs.getString("lieu");
		                Double lalt = rs.getDouble("latitude");
		                Double lag = rs.getDouble("longitude");
		                int monit = rs.getInt("idMoniteur");
		                int condidat = rs.getInt("idCondidat");
		                String vehic = rs.getString("idVehicule");

		                SeanceConduite seanceConduite = new SeanceConduite(nums,date.toLocalDate(), tp.toLocalTime(), adresse, num, nums, monit, condidat, vehic);
		                conduites.add(seanceConduite);
		                
		            }
		        } catch (SQLException e) {
					e.printStackTrace();
				}

		        return conduites;
			
		}
		
		
		

	    public boolean insert(SeanceConduite seance) {
	    	String sql = "INSERT INTO seanceconduite VALUES (?, ?, ?,?,?,?,?,?,?)";
	        try (PreparedStatement stmt = conn.prepareStatement(sql);) {

	            stmt.setInt(1, seance.getNum());
	            stmt.setDate(2, java.sql.Date.valueOf(seance.getDate()));
	            stmt.setTime(3,java.sql.Time.valueOf( seance.getTemp()));
	            stmt.setString(4, seance.getLieuRDV());
	            stmt.setDouble(5, seance.getLatitude());
	            stmt.setDouble(6, seance.getLongitude());
	            stmt.setInt(7, seance.getIdMoniteur());
	            stmt.setInt(8, seance.getIdcondidat());
	            stmt.setString(9, seance.getIdVehicule());

	            stmt.executeUpdate();
	            changeDispo(
	            		 seance.getIdVehicule(),seance.getDate(),seance.getTemp());
	            return true;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	    
		public boolean changeDispo(String immatV, LocalDate date, LocalTime time) {
		    String sql = "UPDATE disponibilitev SET dispo =false WHERE immatV = ? AND DATE(dateTime) = ? AND TIME(dateTime) BETWEEN ? AND ?";

		    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
		        stmt.setString(1, immatV); 
		        stmt.setDate(2, java.sql.Date.valueOf(date));
		        stmt.setTime(3, java.sql.Time.valueOf(time));
		        stmt.setTime(4, java.sql.Time.valueOf(time.plusMinutes(4)));

	            stmt.executeUpdate();
	            return true;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        } 
		    }
	    
	    
		public int rechMoniteur(int idMonit) {
		    String sql = "SELECT 1 FROM moniteur WHERE id = ? LIMIT 1"; 
		    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
		        stmt.setInt(1, idMonit);
		        ResultSet rs = stmt.executeQuery();
		        return rs.next() ? 1 : 0; 
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return -1; 
		    }
		}
		
		public int rechCondidat(int idCondid) {
		    String sql = "SELECT 1 FROM condidat WHERE numero  = ? LIMIT 1"; 
		    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
		        stmt.setInt(1, idCondid);
		        ResultSet rs = stmt.executeQuery();
		        return rs.next() ? 1 : 0; 
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return -1; 
		    }
		}
		
		public int rechNumSc(int num) {
		    String sql = "SELECT 1 FROM seanceconduite WHERE num = ? LIMIT 1"; 
		    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
		        stmt.setInt(1, num);
		        ResultSet rs = stmt.executeQuery();
		        return rs.next() ? 1 : 0; 
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return -1; 
		    }
		}
		
		
		public int disponibVehicule(String immatV, LocalDate date, LocalTime time) {
		    String sql = "SELECT dispo FROM disponibilitev WHERE immatV = ? AND DATE(dateTime) = ? AND TIME(dateTime) BETWEEN ? AND ?";
		    
		    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
		        stmt.setString(1, immatV); 
		        stmt.setDate(2, java.sql.Date.valueOf(date));
		        stmt.setTime(3, java.sql.Time.valueOf(time));
		        stmt.setTime(4, java.sql.Time.valueOf(time.plusMinutes(4)));

		        ResultSet rs = stmt.executeQuery();
		        if (rs.next()) {  
		            boolean dispo = rs.getBoolean("dispo"); 
		            if (!dispo) {
		                return 0; 
		            }
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return -1;
		    }
		    
		    String sql2 = "SELECT * FROM seanceconduite WHERE idVehicule = ? AND date=? AND time=?";
		    try (PreparedStatement stmt = conn.prepareStatement(sql2)) {
		        stmt.setString(1, immatV);
		        stmt.setDate(2, java.sql.Date.valueOf(date));
		        stmt.setTime(3, java.sql.Time.valueOf(time));

		        ResultSet rs = stmt.executeQuery();
		        if (rs.next()) {
		            return 0; 
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return -1; 
		    }

		    String sql3 = "SELECT * FROM examenconduite WHERE vehicule_immat = ? AND date=? AND starttime=?";
		    try (PreparedStatement stmt = conn.prepareStatement(sql3)) {
		        stmt.setString(1, immatV);
		        stmt.setDate(2, java.sql.Date.valueOf(date));
		        stmt.setTime(3, java.sql.Time.valueOf(time));

		        ResultSet rs = stmt.executeQuery();
		        if (rs.next()) {
		            return 0; 
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return -1; 
		    }

		    return 1; 
		}

		
		
		public int disponibMoniteur(int idMonit,LocalDate date,LocalTime time) {
		    String sql = "SELECT * FROM seanceconduite WHERE idMoniteur = ? AND date=? AND time=?"; 
		    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
		        stmt.setInt(1, idMonit);
		        stmt.setDate(2, java.sql.Date.valueOf(date));
		        stmt.setTime(3, java.sql.Time.valueOf(time));
		        ResultSet rs = stmt.executeQuery();
		        if (rs.next()) {
		        return  0;
		        }  
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return -1; 
		    }
		    String sql2 = "SELECT * FROM seancecode WHERE idMoniteur = ? AND date=? AND time=?"; 
		    try (PreparedStatement stmt = conn.prepareStatement(sql2)) {
		        stmt.setInt(1, idMonit);
		        stmt.setDate(2, java.sql.Date.valueOf(date));
		        stmt.setTime(3, java.sql.Time.valueOf(time));
		        ResultSet rs = stmt.executeQuery();
		        if (rs.next()) {
		        return  0;
		        }  
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return -1; 
		    }
		    
		    String sql3= "SELECT availability FROM disponibilite WHERE moniteur_id = ? AND session_date=? AND start_time=?"; 
		    try (PreparedStatement stmt = conn.prepareStatement(sql3)) {
		        stmt.setInt(1, idMonit);
		        stmt.setDate(2, java.sql.Date.valueOf(date));
		        stmt.setTime(3, java.sql.Time.valueOf(time));
		        ResultSet rs = stmt.executeQuery();
		        while (rs.next()) {
		        boolean dispo = rs.getBoolean("availability");
		        if(dispo==false) {
		        	return 0;
		        }
		 
		        }  
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return -1; 
		    }
		    
		    String sql4 = "SELECT * FROM examenconduite WHERE moniteur_id  = ? AND date=? AND starttime=?";
		    try (PreparedStatement stmt = conn.prepareStatement(sql4)) {
		        stmt.setInt(1, idMonit);
		        stmt.setDate(2, java.sql.Date.valueOf(date));
		        stmt.setTime(3, java.sql.Time.valueOf(time));

		        ResultSet rs = stmt.executeQuery();
		        if (rs.next()) {
		            return 0; 
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return -1; 
		    }
		    
		    String sql5 = "SELECT * FROM examencode WHERE moniteur_id  = ? AND date=? AND starttime=?";
		    try (PreparedStatement stmt = conn.prepareStatement(sql5)) {
		        stmt.setInt(1, idMonit);
		        stmt.setDate(2, java.sql.Date.valueOf(date));
		        stmt.setTime(3, java.sql.Time.valueOf(time));

		        ResultSet rs = stmt.executeQuery();
		        if (rs.next()) {
		            return 0; 
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return -1; 
		    }
		    
		    return 1;
		    
		}
	


}
