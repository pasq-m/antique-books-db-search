package com.github.util;

import java.util.ArrayList;

public class GatherBook {
	
	protected int x = 0;					//Used as a counter in "AbeBooksSub".
	protected int x2 = 1;					//Used as a counter in "EbaySoldSub" and "EbaySoldMapSub".
	protected int w = 2;					//Used in the "AbeBooksSub" class for now.
	protected int y = 1;					//Used in the "EbaySoldSub" and "EbaySoldMapSub" classes for now.
	protected int z = 0;					//For "megalist" List index counter.
    
	protected static String title;
	protected static String year;
	protected static int yearInt;			//Declared var. for converted values from string to int.
	protected static String publisher;
	protected static String dateRange;
	protected static String format;
	protected static String originalOrReprod;
	protected static String printTech;
	protected static String type;
	protected static String priceTagNotAuction;			//Used for not-auction websites book's tag.
	protected static String auctionSoldAt;				//Used just for Ebay related books.
    
	protected static double priceTagNotAuctionDoub;		//Declared vars. for converted values from string to double.
	protected static double auctionSoldAtDoub;			//Declared vars. for converted values from string to double.
	protected static double d1;							//"d1" is the result of the average calculation provided by the class "PriceAvgCalc".
    
	ArrayList<String[]> superList;
	public void getElements(String url) throws Exception {	//Method to extract raw data from the website(s).
		//Empty as it has to be override by others sub-classes.
	}
}
