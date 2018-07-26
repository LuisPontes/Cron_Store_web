package cron.searchweb;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import cron.pojos.SearchResults;
import cron.tools.Constants;
import cron.tools.Utils;

public class BasicController {
	
	public static Utils ut   ;
	public static List<String> stores;
	public static String ResultsPath ;
	static{
		ResultsPath = Constants.properties.getProperty("crawler.results.save.path");
		
		ut = new Utils();
		stores = gelAllStores();//TODAS AS LOJAS
	}
	
	public static List<String> gelAllStores() {
		return Arrays.asList(Constants.properties.getProperty("crawler.target.list").split(","));
	}
	
	public TreeMap<Long, SearchResults> getAllResultsInFolder(TreeMap<Long, SearchResults> treeMapResultsByDate ) {
		try {
			File f = new File(ResultsPath);
			for(String p : f.list()) {
				String[] pp = p.split("_");
				long time = Long.parseLong(pp[0]);
				SearchResults results = ut.loadJsonModelInfoFile(ResultsPath+p);
				treeMapResultsByDate.put(time,results);
			}
			return treeMapResultsByDate;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
