package com.github.scraper;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.database.CreateFirstDb;
import com.github.database.InsertDataDb;
import com.github.util.GatherBook;

public class EbaySoldMapSub extends GatherBook {
	
	
	public void getElements(String url) throws Exception {		//Method to extract raw data from the website(s).
		
		System.setProperty("webdriver.chrome.driver", "C:\\webdriver\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors");  
		WebDriver driver = new ChromeDriver(options);
        driver.get(url);
        
        WebDriverWait wait = new WebDriverWait(driver, 5);		//Wait for the "All Listing" element to load as a way to wait that most of the page is already loaded before continue.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class= 'carousel__viewport']")));
    	WebElement auction;
    	auction = driver.findElement(By.xpath("//li[3][@class='fake-tabs__item btn']/a"));
    	auction.click();		//We need to click on an element of the page to activate the page before the real clicking below.
		
        List<String[]> megaList = new ArrayList<String[]>();	//We create the main list where we will store every book data as an array (a list of arrays).
        //Check if element is present on the website, if yes, continue to the next element, if not, stop the iteration and set the length of the elements - 1 as the last elements is not present.
        //This way we can know every time, how many elements to scrape are present on the page.
        int counter = 0;
        List<WebElement> elementList = driver.findElements(By.xpath("//ul[@class='srp-results srp-list clearfix']/li[contains(@class,'s-item')]"));
        for (WebElement element : elementList) {	//We count each element presents in the "elementList", that is the total books present after filtered by "Buy Now" button on the page.
        	counter++;								//This way we can then use the counter as a length parameter to know how many elements loop in the page without errors.
        }
        
        while (x2 < counter) {
        	//We have to click on each listing first to analyze others details like author.
        	WebElement page = null;;
        	
        	page = driver.findElement(By.xpath("//*[@id=\"srp-river-results\"]/ul/li["+ (y) +"]/div/div[2]/a"));		//We use a var that starts with 1 (check "GatherBook" superclass for declaration)
        																												//and add one unit every loop of the while above to select the next Ebay's item.        	
        	page.click();
        	try {
        		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()= 'Item specifics']")));	//Wait to load the portion of the page needed and then continue.
        	}
        	catch (Exception e) {
        		//Item specifics block is not present - we get back and keep on with the next item.
        		System.out.println("!!!Exception for no item specifics!!!");
        		
        		if (y == 50)  {									//We check if we are at the last element of the loop, if we are, we break the loop and go out of the while and run the rest of the code.
    	        	//y--;
    	        	break;
    	        } else {										//Under element number 50 we keep going adding +1 to the counter of the var "y" and going back to the next loop. 
    	        	y++;
    	        }
        		
        		driver.navigate().back();
        		continue;
        	}
        	
        	title = driver.findElement(By.xpath("//*[@id='itemTitle']")).getText();							//We extract the title of the listing from the page.
        	
        	try {
        		auctionSoldAt = driver.findElement(By.xpath("//span[@id='convbidPrice']")).getText();		//First we try to see if there is a conversed value of price (the item is sold not in US dollars).
        																									//If not, the item is sold from USA and so below in the catch block it gets the correct element
        																									//for price.
        		String stringed = auctionSoldAt.replaceAll("[^\\d.]", "");									//Removes all non-numeric characters.
    			auctionSoldAtDoub = Double.parseDouble(stringed);	
        	}
        	
        	catch (NoSuchElementException e) {
        		try {
        			auctionSoldAt = driver.findElement(By.xpath("//span[@class='notranslate vi-VR-cvipPrice']")).getText();
        			String stringed = auctionSoldAt.replaceAll("[^\\d.]", "");
        			auctionSoldAtDoub = Double.parseDouble(stringed);														//We convert from string to double the value of "auctionSoldAt".
        		}
        		catch (Exception a) {
        			auctionSoldAt = driver.findElement(By.xpath("//span[@id='prcIsum']")).getText();
        			String stringed = auctionSoldAt.replaceAll("[^\\d.]", "");
        			auctionSoldAtDoub = Double.parseDouble(stringed);	
        		}
        	}
        	
        	try {
        		year = driver.findElement(By.xpath("//*[contains(text(),'Year:')]/following-sibling::td[1]")).getText();	//It finds the published year's field value if present.
        		yearInt = Integer.parseInt(year);																			//We convert the "year" value from string to int.
        	}
        	catch (Exception e) {
        		
        		yearInt = 0;																								//If the scraper won't finds the field, it will set it as "0" (int).
        	}
        	
        	try {
        		publisher = driver.findElement(By.xpath("//*[contains(text(),'Cartographer/Publisher:')]/following-sibling::td[1]")).getText();		//It finds the publisher field value if present.
        	}
        	catch (Exception e) {
        		publisher = "Vuoto";																									//If the scraper won't finds the field, it will set it as "Vuoto".
        	}
        	try {
        		dateRange = driver.findElement(By.xpath("//*[contains(text(),'Date Range:')]/following-sibling::td[1]")).getText();					//It finds the date/range field value if present.
        	}
        	catch (Exception e) {
        		dateRange = "Vuoto";
        	}
    		try {
        		format = driver.findElement(By.xpath("//*[contains(text(),'Format:')]/following-sibling::td[1]")).getText();						//It finds the format field value if present.
        	}
        	catch (Exception e) {
        		format = "Vuoto";
        	}
        	try {
        		originalOrReprod = driver.findElement(By.xpath("//*[contains(text(),'Original/Reproduction:')]/following-sibling::td[1]")).getText();	//It finds the original or reproduction field value if present.
        	}
        	catch (Exception e) {
        		originalOrReprod = "Vuoto";
        	}
        	try {
        		printTech = driver.findElement(By.xpath("//*[contains(text(),'Printing Technique:')]/following-sibling::td[1]")).getText();		//It finds the printing technique field value if present.
        	}
        	catch (Exception e) {
        		printTech = "Vuoto";
        	}
        	try {
        		type = driver.findElement(By.xpath("//*[contains(text(),'Type:')]/following-sibling::td[1]")).getText();							//It finds the type field value if present.
        	}
        	catch (Exception e) {
        		type = "Vuoto";
        	}
        	
        	megaList.add(new String[10]);																					//It creates an array inside the list "megaList" that can contains 10 items.
	        
	        String[] insideArray;
	        
	        if (x2 < 2) {
	        	CreateFirstDb createDbOne = new CreateFirstDb();	//Using the ".createDb" method of the "CreateFirstDb" class we create the DB only in the first loop of the while ("(x2 < 2)").
	    		createDbOne.createDb();
	        }
	        InsertDataDb insertData = new InsertDataDb();			//Here we run another external method to insert the data inside the rows of the created DB at every next while loop.
		insertData.insert(title, publisher, yearInt, insertData.nullCheck(dateRange, format, originalOrReprod, printTech, type), priceTagNotAuctionDoub, auctionSoldAtDoub);
	        
	        insideArray = megaList.get(z);		//It bounds the created array of 10 elements above to a specific index position ("z") and put inside of it the values gathered (Title, Authors, etc.).
	        
	        insideArray[0] = ("Title: " + title);
	        insideArray[1] = ("Published Year: " + year);
	        insideArray[2] = ("Publisher/Cartographer: " + publisher);
	        insideArray[3] = ("Date Range: " + dateRange);
	        insideArray[4] = ("Format: " + format);
	        insideArray[5] = ("Original/Reproduction: " + originalOrReprod);
	        insideArray[6] = ("Print Technique: " + printTech);
	        insideArray[7] = ("Type: " + type);
	        insideArray[8] = ("Winning Bid Price: " + auctionSoldAt);
	        
	        x2++;
	        z++;
	        
	        if (y == 50)  {
	        	//y--;
	        	break;
	        } else {
	        	y++;
	        }
	        
	        driver.navigate().back();					//We need to get back to the previous page (the search results page) after we got the data of the current listing.
	        
        }
        
        superList = (ArrayList<String[]>) megaList;		//We put the list of array on a var. of the super class GatherBook.
	
        driver.close();
	}
}
