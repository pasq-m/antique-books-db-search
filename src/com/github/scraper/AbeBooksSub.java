package com.github.scraper;

import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import com.github.database.CreateFirstDb;
import com.github.database.InsertDataDb;
import com.github.util.GatherBook;

public class AbeBooksSub extends GatherBook {
	
	public void getElements(String url) throws Exception {	//Method to extract raw data from the website(s).
		try (final WebClient webClient = new WebClient(BrowserVersion.CHROME)) {
			final HtmlPage page = webClient.getPage(url);
			webClient.waitForBackgroundJavaScript(5000);	//It's fundamental to obtain the price value as does it seems that we have to wait a little bit before obtaining the price tag on the webpage.
			List<String[]> megaList = new ArrayList<String[]>();												//We create the main list where we will store every book data as an array (a list of arrays).
			
			int counter = 0;
			DomNodeList<DomNode> elementList = page.querySelectorAll("div.collection-item");
			for (DomNode element : elementList) {											//We count each element presents in the "elementList", that is the total books present by default in the page.
	        	counter++;	        												//This way we can then use the counter as a length parameter to know how many elements loop in the page without errors.
			}
			
			while (x < counter) {
	        	
				title = page.querySelector("#collection > div:nth-child(" + w + ") > div > a > div > div.title").getTextContent();	//We got the text using the "getTextContent()" method and String type.
				publisher = page.querySelector("#collection > div:nth-child(" + w + ") > div > a > div > div.authors").getTextContent();
				year = page.querySelector("#collection > div:nth-child(" + w + ") > div > a > div > div.published-year").getTextContent();
				yearInt = Integer.parseInt(year);																					//We convert from string to int the "year" value.
				
				DomNode priceDom = (DomNode) page.getByXPath("//span[contains(@id,'price-')]").get(x);	//"get(x)" means "get the x object that you find in the DOM", so in this case the first price ID, then the second, etc..
		        
				priceTagNotAuction = priceDom.getTextContent();									//It extracts the string price from the DOM element found above and bound to the "price" variable.
				String stringed = priceTagNotAuction.replaceAll("[^\\d.]", "");
				priceTagNotAuctionDoub = Double.parseDouble(stringed);							//We convert from string to double the value of "priceTagNotAuction".
		        
				if (x < 1) {
					CreateFirstDb createDbOne = new CreateFirstDb();	//Using the ".createDb" method of the "CreateFirstDb" class we create the DB only in the first loop of the while ("(x < 1)").
					createDbOne.createDb();
				}
		        
				InsertDataDb insertData = new InsertDataDb();			//Here we run another external method to create a new instance object of the "InsertDataDb" class, so we can insert the data inside
		        														//the rows of the created DB at every next while loop.
				InsertData.insert(title, publisher, yearInt, insertData.nullCheck(dateRange, format, originalOrReprod, printTech, type), priceTagNotAuctionDoub, auctionSoldAtDoub);
				
				priceTagNotAuctionDoub = 0.0d;							//Here we reset the var because we want it to be reset when used in the new object of the class "EbaySoldMapSub", as it has to be
																		//0.0 in the elements of the DB scraped from Ebay, because it represents items still not sold from other websites. 
				megaList.add(new String[4]);							//Create an array inside the list "megaList".
		        
				String[] insideArray = megaList.get(z);	//Access at the x position the list now occupied by the new created array above. In practice, it creates a new array in the next "x" number
		        										//index position, so it creates every time a new array and place it in the main list (megaList).
				x++;
				z++;
				w++;
			}
			superList = (ArrayList<String[]>) megaList;
	        
		}
	}
}
