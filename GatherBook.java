//package main;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;

public class GatherBook {
	
	int x = 0;					//Used as a counter in "AbeBooksSub".
	int x2 = 1;					//Used as a counter in "EbaySoldSub" and "EbaySoldMapSub".
	int w = 2;					//Used in the "AbeBooksSub" class for now.
    int y = 1;					//Used in the "EbaySoldSub" and "EbaySoldMapSub" classes for now.
    int z = 0;					//For "megalist" List index counter.
    
    static String title;
    static String year;
    static int yearInt;			//Declared var. for converted values from string to int.
    static String publisher;
    static String dateRange;
    static String format;
    static String originalOrReprod;
    static String printTech;
    static String type;
    static String priceTagNotAuction;			//Used for not-auction websites book's tag.
    static String auctionSoldAt;				//Used just for Ebay related books.
    
    static double priceTagNotAuctionDoub;		//Declared vars. for converted values from string to double.
    static double auctionSoldAtDoub;			//Declared vars. for converted values from string to double.
    static double d1;							//"d1" is the result of the average calculation provided by the class "PriceAvgCalc".
    
	ArrayList<String[]> superList;
	public void getElements(String url) throws Exception {	//Method to extract raw data from the website(s).
		//Empty as it has to be override by others sub-classes.
	}
}