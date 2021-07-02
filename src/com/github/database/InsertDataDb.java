package com.github.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;


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
	
	ArrayList<String> filteredString;
	
	public ArrayList<String> nullCheck(String... bookData) {
		
		filteredString = new ArrayList<String>();
		
		for (String book : bookData) {
			if (book == null) {
				book = "Vuoto";				
			}
			
			filteredString.add(book);
		}
		
		return filteredString;
	}
	
	public int insert(String titleArg, String publisherArg, int yearArg, ArrayList<String> filter, double pricetagnotauctionArg, double auctionsoldatArg) {
				
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
			ps.setString(4, filteredString.get(0));			//We extract the first element of the filtered list that it will be the "dateRange" value that will be passed in the
	        								//"nullCheck()" method as first parameter.
			ps.setString(5, filteredString.get(1));			//Like above, in order it will be the second parameter passed and then stored in the filtered list returned, the "format".
			ps.setString(6, filteredString.get(2));
			ps.setString(7, filteredString.get(3));
			ps.setString(8, filteredString.get(4));
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
