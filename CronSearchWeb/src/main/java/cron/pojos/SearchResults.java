package cron.pojos;

import java.util.Date;
import java.util.List;

public class SearchResults {
	
	private Date date;
	private String KeyWord = null;
	private String webSite = null;
	private List<SearchResultItem> ListItem = null;
	private List<String> StoreList = null;
	
	
	public String getWebSite() {
		return webSite;
	}
	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getKeyWord() {
		return KeyWord;
	}
	public void setKeyWord(String keyWord) {
		KeyWord = keyWord;
	}
	public List<SearchResultItem> getListItem() {
		return ListItem;
	}
	public void setListItem(List<SearchResultItem> listItem) {
		ListItem = listItem;
	}
	public List<String> getStoreList() {
		return StoreList;
	}
	public void setStoreList(List<String> storeList) {
		StoreList = storeList;
	}
	@Override
	public String toString() {
		return "SearchResults [date=" + date + ", KeyWord=" + KeyWord + ", ListItem=" + ListItem + "]";
	}
	
	
}
