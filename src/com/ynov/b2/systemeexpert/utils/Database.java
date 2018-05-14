package com.ynov.b2.systemeexpert.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



public class Database {

	private Connection connection;
	private String driver_name = "com.mysql.jdbc.Driver";
	
	
	
	public Database(String serverName, String baseName, String login, String password) {
		DriverLoad(this.driver_name);
		String url = "jdbc:mysql:"+serverName+"/"+baseName;
		this.connection = DriverConnection(this.connection, url, login, password);
	}
	
	
	public Database(String baseName) {
		this("//localhost", baseName, "guillaume", "erty");
	}
	
	
	/**
	 * Execute une requete select
	 * @param requete
	 * @return
	 */
	
	public ResultSet requete(String requete) {
		ResultSet resultats = null;
		try {
			Statement stmt = this.connection.createStatement();
			requete = (requete.contains(" ")) ? requete : "SELECT * FROM "+requete;
			resultats = stmt.executeQuery(requete);
		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
			System.exit(1);
		}
		//printResultats(resultats);
		return resultats;
	}
	
	
	/**
	 * Retourne le nb de ligne 
	 * @param requete
	 * @return updated_row 
	 */
	public int update(String requete) {
		int updated_row = 0;
		
		try {
			Statement stmt = this.connection.createStatement();
			updated_row = stmt.executeUpdate(requete);
		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
			System.exit(1);
		}
		return updated_row;
	}
	
	
	/**
	 * Retourne l'id de l'entité insérée
	 * @param requete
	 * @return
	 */
	public int insertAI(String requete) {
		
		int inserted_id = 0;
		
		try {
			Statement stmt = this.connection.createStatement();
			stmt.executeUpdate(requete, Statement.RETURN_GENERATED_KEYS);
			
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next())
				inserted_id=rs.getInt(1);
			
		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
			System.exit(1);
		}
		return inserted_id;
	}
	
	
	
	public int delete(String requete) {
		int deleted_row = 0;
		try {
			Statement stmt = this.connection.createStatement();
			deleted_row = stmt.executeUpdate(requete);
		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
			System.exit(1);
		}
		return deleted_row;
	}
	

	
	/**
	 * Affiche toutes les colonnes d'un resultat
	 * @param resultats
	 */
	public void printResultats(ResultSet resultats) {
		try {
			ResultSetMetaData rsmd = resultats.getMetaData();
			int nbCols = rsmd.getColumnCount();
			
			while(resultats.next()) {
				for(int i = 1; i <= nbCols; i++) {
					System.out.print(resultats.getString(i) + "    ");
				}
				System.out.println();
			}

		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
			System.exit(1);
		}
	}
	

	private void DriverLoad(String driver_name) {
		try {
			Class.forName(driver_name);
		} catch (ClassNotFoundException e) {
			System.err.println("Impossible de charger le pilote: "+ driver_name);
			System.exit(1);
		}
	}
	


	private Connection DriverConnection(Connection conn, String url, String user, String password) {
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException ex) {
			System.err.println("Erreur Connection Driver: " + ex.getMessage());
			System.exit(1);
		}
		return conn;
	}
	
	
	
	
	
}
