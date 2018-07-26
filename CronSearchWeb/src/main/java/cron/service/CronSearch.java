package cron.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cron.pojos.SearchResultItem;
import cron.pojos.SearchResults;
import cron.tools.Constants;
import cron.tools.CopyFilesOrDirectorys;
import cron.tools.SeleniumUtils;
import cron.tools.Utils;

public class CronSearch extends Utils{
	
	private static Logger log 						= null;
	private SearchResults OBJ 						= null;
	private List<SearchResultItem> ListItem 		= null;
	private static SeleniumUtils sel 				= null;
	private static String FolderResultsSave 		= null;
	private static CopyFilesOrDirectorys filesUtils	= null;
	
	
	
	private String domain 			= null;
	private String inputSearchId 	= null;
	private String buttonSearchId 	= null;
	private String lowestPriceId 	= null;
	private String resultListId 	= null;
	private String resultListClass 	= null;
	
	static {
		log 				= LoggerFactory.getLogger(CronSearch.class);
		sel 				= new SeleniumUtils();
		FolderResultsSave 	= Constants.properties.getProperty("crawler.results.save.path", null);
		filesUtils 			= new CopyFilesOrDirectorys();
		filesUtils.createFolder(FolderResultsSave);
	}
	
	public CronSearch(SearchResults searchResults) {
		searchResults.setDate(new Date());
		ListItem = new ArrayList<>();
		this.OBJ=searchResults;
	}
	
	public SearchResults startSearch() {
		WebDriver driver = null;
		try {
			for (String name : OBJ.getStoreList()) {

				driver = sel.createDriver();
				if (driver == null) {
					throw new Exception("Selenium not work![WebDriver==null]");
				}
				
				loadAllPropertiesOfDomain(name);
				
				driver.get(domain);
				
				WebElement input = driver.findElement(By.id(inputSearchId));//ndElementById(inputSearchId);
				input.sendKeys(OBJ.getKeyWord());

				WebElement button = driver.findElement(By.id(buttonSearchId));//findElementById(buttonSearchId);
				button.click();
				
				switch (name) {
					case "ebay":{
									ListItem.addAll(processEbay(driver,name));
									break;
								}
				}

			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			driver.close();
		}
		OBJ.setListItem(ListItem);
		saveResults(OBJ);
		
		
		return OBJ;
	}
	
	private void saveResults(SearchResults resultsObj2) {
		String saveNameFile = (FolderResultsSave+OBJ.getDate().getTime()+"_"+resultsObj2.getKeyWord()+".json").replaceAll(" ", "+");
		if( saveJsonFile(resultsObj2, saveNameFile) ){
			log.info(String.format("Saved Results [%s]",saveNameFile) );
		}else {
			log.info(String.format("ERROR! Not Saved Results [%s]",saveNameFile) );
		}
	}


	private List<SearchResultItem> processEbay(WebDriver driver, String name) {
		driver.get(driver.getCurrentUrl().toString() + lowestPriceId);
		log.info(driver.getCurrentUrl().toString() + lowestPriceId);

		//filesUtils.saveTextInFile(String.format("Search_%s_%s.html", removeProtocol(name),OBJ.getKeyWord()),driver.getPageSource());
		List<WebElement> allElements = getElements(driver);
		if (allElements==null) {
			log.info("Nao encontrou nada!");
			return null;
		}		
		List<SearchResultItem> ListItem = new ArrayList<>();
		for (WebElement e : allElements) {
			SearchResultItem resultItem = new SearchResultItem();
			resultItem.setDomain(domain);
			resultItem.setKeyWord(OBJ.getKeyWord());
			
			List<WebElement> tagList = e.findElements(By.tagName("span"));
			resultItem = verifyByClassName(tagList,resultItem);
			
			tagList = e.findElements(By.tagName("a"));
			resultItem = verifyByClassName(tagList,resultItem);
			ListItem.add(resultItem);
		
		}
		return ListItem;
	}


	private SearchResultItem verifyByClassName(List<WebElement> tagList, SearchResultItem resultItem) {
		for (WebElement we : tagList) {
			if ( we.getAttribute("class")!=null ) {
				if(we.getAttribute("class").contains("title")) {
					System.out.println("Title: "+ we.getText());
					resultItem.setKeyWord(we.getText());
				}
				if(we.getAttribute("class").contains("price")) {
					System.out.println("price: "+ we.getText());
					resultItem.setPrice(we.getText());
				}
				if(we.getAttribute("class").contains("location")) {
					System.out.println("location: "+we.getText());
					resultItem.setCountryFrom(we.getText());
				}
				if(we.getAttribute("class").contains("shipping")) {
					System.out.println("shipping: "+we.getText());
					resultItem.setShipping(we.getText());
				}
				if(we.getAttribute("class").contains("link")) {
					System.out.println("title: "+we.getText());
					System.out.println("Link: "+we.getAttribute("href"));
					resultItem.setLink(we.getAttribute("href"));
					resultItem.setTitle(we.getText());
				}
				if(we.getAttribute("class").contains("time-left")) {
					System.out.println("time-left: "+we.getText());
					//resultItem.setPublishDate(we.getText());
				}
				if(we.getAttribute("class").contains("time-end")) {
					System.out.println("time-end: "+we.getText());
					resultItem.setEndDate(we.getText());
				}
				if(we.getAttribute("class").contains("purchaseOptions")) {
					System.out.println("purchaseOptions: "+we.getText());
					resultItem.setTypePurchase(we.getText());
				}
			}
		}
		return resultItem;
	}

	private List<WebElement> getElements(WebDriver driver) {
		 List<WebElement> wl = driver.findElements(By.xpath(resultListId));
		if (wl.size() < 1) {
			wl = driver.findElements(By.className(resultListClass));
			if (wl.size()<1) {
				return null;
			}
		}
		return wl;
	}
	
	private void loadAllPropertiesOfDomain(String name) throws Exception {
		domain = Constants.properties.getProperty(name + ".domain");
		if (domain==null || domain.isEmpty()) {
			throw new Exception("Propriedades incompletas! ["+name+".domain]");
		}
		inputSearchId = Constants.properties.getProperty(name + ".id.input.search");
		if (inputSearchId==null || inputSearchId.isEmpty()) {
			throw new Exception("Propriedades incompletas! ["+name+".id.input.search]");
		}
		buttonSearchId = Constants.properties.getProperty(name + ".id.button.search");
		if (buttonSearchId==null || buttonSearchId.isEmpty()) {
			throw new Exception("Propriedades incompletas! ["+name+".id.button.search]");
		}
		lowestPriceId = Constants.properties.getProperty(name + ".url.filter.lowPriceFirst");
		if (lowestPriceId==null || lowestPriceId.isEmpty()) {
			throw new Exception("Propriedades incompletas! ["+name+".url.filter.lowPriceFirst]");
		}
		resultListId = Constants.properties.getProperty(name + ".id.ul.list");
		if (resultListId==null || resultListId.isEmpty()) {
			throw new Exception("Propriedades incompletas! ["+name+".id.ul.list]");
		}
		resultListClass = Constants.properties.getProperty(name + ".class.ul.list");
		if (resultListClass==null || resultListClass.isEmpty()) {
			throw new Exception("Propriedades incompletas! ["+name+".class.ul.list]");
		}
	}

}
