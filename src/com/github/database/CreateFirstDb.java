package com.github.database;

import org.sqlite.SQLiteDataSource;

import java.sql.*;
//import java.sql.Connection;
//import java.sql.SQLException;


public class CreateFirstDb {
	
	public void createDb() throws SQLException {
		SQLiteDataSource ds = new SQLiteDataSource();
		ds.setUrl("jdbc:sqlite:test.db");
		Connection conn = ds.getConnection();
		System.out.println("Opened database successfully");
		
		Statement stmt = conn.createStatement();
		
	    String sql = "CREATE TABLE IF NOT EXISTS MAPS " +	//It creates a table ONLY IF IT NOT EXISTS YET.
	                   "(TITLE          TEXT PRIMARY KEY    NOT NULL, " +
	                   " YEAR           INT     NOT NULL, " + 
	                   " PUBLISHER      TEXT     NOT NULL, " + 
	                   " DATERANGE     TEXT     NOT NULL, " + 
	                   " FORMAT         TEXT     NOT NULL, " + 
	                   " ORIGINAL     TEXT     NOT NULL, " + 
	                   " PRINTTECHNIQUE     TEXT     NOT NULL, " + 
	                   " TYPE           TEXT     NOT NULL, " + 
	                   " PRICE_TAG_NOT_AUCTION           DOUB     NOT NULL, " + 
	                   " AUCTION_SOLD_AT    DOUB     NOT NULL)";
	    stmt.executeUpdate(sql);
	    stmt.close();
	    conn.close();
	}
	
}
