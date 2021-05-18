import java.util.Arrays;

public class GatherBookMain {
	public static void main(String[] args)throws Exception {
		
		GatherBook AbeWebClient = new AbeBooksSub();		
		//We call the "getElements()" method to gather books data from website(s) and store it in a list (or multiple lists).
		AbeWebClient.getElements("https://www.abebooks.com/collections/sc/atlases/2dzei9jydsjGsGfJtSsP4H?cm_sp=ccbrowse-_-p0-_-collections");
		GatherBook EbaySoldMapWebClient = new EbaySoldMapSub();
		EbaySoldMapWebClient.getElements("https://www.ebay.com/sch/37967/i.html?_from=R40&_nkw=charts&LH_TitleDesc=0&_sop=12&_sadis=200&LH_Auction=1&LH_Complete=1&LH_Sold=1");
		TestWriteFile newWriter = new TestWriteFile();		//We write the list(s) to one (or multiple) text file.
		newWriter.writingAFile(AbeWebClient.superList, "mega_list_Abe.txt");	//We create and write the list of Abe using the "AbeBooksSub" class instance object.
		newWriter.writingAFile(EbaySoldMapWebClient.superList, "mega_list_Ebay_Map_Sold.txt");	//We create and write the list of Ebay using the "EbaySub" class instance object.		
	}
}

