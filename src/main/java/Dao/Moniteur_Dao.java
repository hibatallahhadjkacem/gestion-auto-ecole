package Dao;



import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Entities.Moniteur;


	public class Moniteur_Dao {
		

	    private static Connection connexion;

	    private final String DB_URL = "jdbc:mysql://localhost:3306/autoecole";
	    private final String USER = "root";
	    private final String PASS = "";

	    public Moniteur_Dao() throws SQLException{

	        try{
	               Class.forName("com.mysql.cj.jdbc.Driver");
	        } catch (ClassNotFoundException e) {

	            e.printStackTrace();
	        }
	        connexion= DriverManager.getConnection(DB_URL, USER, PASS);
	    }

	    public static Connection getInstance(){
	        if (connexion == null)
	            try {
	                new Moniteur_Dao();
	            }catch(Exception e){
	                System.out.println("--"+e.getMessage());
	            }
	        return connexion;
	    }
	    
	    
	    
	    
	    
	    private static Connection conn = getInstance();

		public static List<Moniteur> findAll_butsome_D(){

				Statement stmt=null;

				ResultSet rs=null;

				List<Moniteur> moniteurs=new ArrayList<Moniteur>();

				String SQL="SELECT * FROM moniteur";

				try {

					stmt=conn.createStatement();//Créer un objet Statement pour exécuter la requête SQL

					rs=stmt.executeQuery(SQL);// Étape 4 : Exécuter une requête SQL et récupérer le résultat

					while (rs.next()) {

						int id =rs.getInt("id");//1 c'est le nombre de colonne.

						String firstName=rs.getString("first_name");

						String lastName=rs.getString("last_name");

						Moniteur moniteur =new Moniteur (id, firstName, lastName);

						moniteurs.add(moniteur);

					}

					

				}catch (SQLException e) {

					e.printStackTrace();

				}

				return moniteurs;

		}
		
		
		
		public static List<Moniteur> findAll() {
		    Statement stmt = null;
		    ResultSet rs = null;
		    List<Moniteur> moniteurs = new ArrayList<>();

		    String SQL = "SELECT * FROM moniteur";

		    try {
		        stmt = conn.createStatement(); // Créer un objet Statement pour exécuter la requête SQL
		        rs = stmt.executeQuery(SQL); // Exécuter la requête SQL et récupérer le résultat

		        while (rs.next()) {
		            int id = rs.getInt("id"); // Récupération par nom de colonne
		            String prenom = rs.getString("first_name");
		            String nom = rs.getString("last_name");
		            String sexe = rs.getString("sexe");
		            String email = rs.getString("email");
		            String phone = rs.getString("phone");
		            LocalDate dateRecrutement = rs.getDate("date_recrutement").toLocalDate(); // Convertir SQL Date en LocalDate
		           


		            // Création d'un objet Moniteur
		            Moniteur moniteur = new Moniteur(id, nom, prenom, sexe, email, phone, dateRecrutement);
		            moniteurs.add(moniteur);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        // Fermer les ressources pour éviter les fuites mémoire
		        try {
		            if (rs != null) rs.close();
		            if (stmt != null) stmt.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }

		    return moniteurs;
		}

		

		public static int save(Moniteur m) {
		    int moniteurId = 0;
		    PreparedStatement stmt = null;
		    ResultSet rs = null;

		    try {
		        String sql = "INSERT INTO moniteur (first_name, last_name, sexe, email, phone, date_recrutement) VALUES (?, ?, ?, ?, ?, ?)";

		        // Préparer la requête avec l'option pour récupérer les clés générées
		        stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

		        // Remplir les paramètres
		        stmt.setString(1, m.getPrenom());
		        stmt.setString(2, m.getNom());
		        stmt.setString(3, m.getSexe());
		        stmt.setString(4, m.getEmail());
		        stmt.setString(5, m.getPhone());
		        stmt.setDate(6, java.sql.Date.valueOf(m.getDateRecrutement())); // Convertir LocalDate en SQL Date

		        // Exécuter l'insertion
		        stmt.executeUpdate();

		        // Récupérer l'ID généré par le SGBD
		        rs = stmt.getGeneratedKeys();
		        if (rs.next()) {
		            moniteurId = rs.getInt(1);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        // Fermer les ressources pour éviter les fuites mémoire
		        try {
		            if (rs != null) rs.close();
		            if (stmt != null) stmt.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }

		    return moniteurId;
		}

		
		public  static boolean delete(int id) {
		    String SQL = "DELETE FROM moniteur WHERE id = ?";
		    try (PreparedStatement stmt = conn.prepareStatement(SQL)) {
		        stmt.setInt(1, id);  // Passer l'ID du moniteur à supprimer
		        int rowsAffected = stmt.executeUpdate();
		        return rowsAffected > 0;
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return false;
		    }
		}
		
		
		
	
		  public static boolean updateMoniteur(Moniteur moniteur) {
		        String sql = "UPDATE moniteur SET first_name = ?, last_name = ?, sexe = ?, email = ?, phone = ?, date_recrutement = ? WHERE id = ?";
		        
		        try (PreparedStatement statement = connexion.prepareStatement(sql)) {
		            // Remplir les paramètres de la requête
		            statement.setString(1, moniteur.getPrenom());
		            statement.setString(2, moniteur.getNom());
		            statement.setString(3, moniteur.getSexe());
		            statement.setString(4, moniteur.getEmail());
		            statement.setString(5, moniteur.getPhone());
		            statement.setDate(6, java.sql.Date.valueOf(moniteur.getDateRecrutement()));
		            statement.setInt(7, moniteur.getId());

		            // Exécuter la mise à jour
		            int rowsUpdated = statement.executeUpdate();
		            return rowsUpdated > 0;  // Retourner true si la mise à jour a réussi
		        } catch (SQLException e) {
		            e.printStackTrace();
		            return false;  // Retourner false en cas d'erreur
		        }
		    }      

	        
	        
	        
	  
		

			

	    
	    
	    
	}

