package cron.searchweb;

import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cron.pojos.SearchResults;
import cron.service.CronSearch;

@Controller
@EnableAutoConfiguration
public class MainController extends BasicController{
	
	private static Logger log = LoggerFactory.getLogger(MainController.class);
	
	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public String index(Model model) {
			model.addAttribute("stores", stores);
			model.addAttribute("searchResults", new SearchResults());
		return "index";
	}
	
	@RequestMapping(value = {"/search" }, method = RequestMethod.POST)
	public String search(Model model,@ModelAttribute SearchResults searchResults) {
		log.info("Pesquisa... [{}]",searchResults.toString());
			List<String> StoreList = Arrays.asList("ebay,olx");
			if ( searchResults.getListItem() != null ) {
				model.addAttribute("message", searchResults);
			}else {
				searchResults.setStoreList(StoreList);
				CronSearch task = new CronSearch(searchResults);
				SearchResults obj = task.startSearch();
				model.addAttribute("message", obj);
			}
		return "index";
	}
	
	@RequestMapping(value = {"/historic" }, method = RequestMethod.GET)
	public String historic(Model model) {
		log.info("Historico...");
			model.addAttribute("stores", stores);
			model.addAttribute("searchResults", new SearchResults());
			TreeMap<Long, SearchResults> treeMapResultsByDate = getAllResultsInFolder(new TreeMap<>());
			model.addAttribute("listResults", treeMapResultsByDate.descendingMap());
		return "historic";
	}

	
}
