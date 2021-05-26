package com.github.database;

import java.sql.*;

public class InsertDataDb {
	
	public Connection connect() {
		// SQLite connection string
		Connection conn = null;
		String url = "jdbc:sqlite:test.db";
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}
	
	public int insert(String titleArg, int yearArg, String publisherArg, String daterangeArg, String formatArg, String originalArg, String printtechniqueArg, String typeArg, double pricetagnotauctionArg, double auctionsoldatArg) {
		
		if (daterangeArg == null) {
			daterangeArg = "Vuoto"; 
		} 
		if (formatArg == null) {
			formatArg = "Vuoto";
		}
		if (originalArg == null) {
			originalArg = "Vuoto";
		}
		if (printtechniqueArg == null) {
			printtechniqueArg = "Vuoto";
		}
		if (typeArg == null) {
			typeArg = "Vuoto";
		}
		if (pricetagnotauctionArg == 0.0d) {
			pricetagnotauctionArg = 0;
		}
		if (auctionsoldatArg == 0.0d) {
			auctionsoldatArg = 0;
		}
		
		
		String INSERT_SQL = "INSERT INTO MAPS(TITLE, YEAR, PUBLISHER, DATERANGE, FORMAT, ORIGINAL, PRINTTECHNIQUE, TYPE, PRICE_TAG_NOT_AUCTION, AUCTION_SOLD_AT) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		int numRowsInserted = 0;
	
		try (Connection conn = this.connect();
			PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {
			
			ps.setString(1, titleArg);
			ps.setInt(2, yearArg);
			ps.setString(3, publisherArg);
			ps.setString(4, daterangeArg);
			ps.setString(5, formatArg);
			ps.setString(6, originalArg);
			ps.setString(7, printtechniqueArg);
			ps.setString(8, typeArg);
			ps.setDouble(9, pricetagnotauctionArg);
			ps.setDouble(10, auctionsoldatArg);
			numRowsInserted = ps.executeUpdate();

			ps.close();
			System.out.println("Records created successfully");
			return numRowsInserted;
		
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("From InsertDataDb");
		}
		
		return numRowsInserted;
	}
	
}
