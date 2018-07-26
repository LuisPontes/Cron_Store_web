package cron.pojos;

import java.util.Date;

public class SearchResultItem {

	private String KeyWord = null;
	private String title = null;
	private String price = null;
	private String domain = null;
	private Date publishDate = null;
	private Date finishDate = null;
	private String endDate = null;
	private String countryFrom = null;
	private String shipping = null;
	private String link = null;
	private String typePurchase = null;
	private int totalPrice ;
	
	
	public String getTypePurchase() {
		return typePurchase;
	}
	public void setTypePurchase(String typePurchase) {
		this.typePurchase = typePurchase;
	}
	public Date getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getKeyWord() {
		return KeyWord;
	}
	public void setKeyWord(String keyWord) {
		KeyWord = keyWord;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	public String getCountryFrom() {
		return countryFrom;
	}
	public void setCountryFrom(String countryFrom) {
		this.countryFrom = countryFrom;
	}
	public String getShipping() {
		return shipping;
	}
	public void setShipping(String shipping) {
		this.shipping = shipping;
	}
	@Override
	public String toString() {
		return "SearchResultItem [KeyWord=" + KeyWord + ", title=" + title + ", price=" + price + ", domain=" + domain
				+ ", publishDate=" + publishDate + ", finishDate=" + finishDate + ", endDate=" + endDate
				+ ", countryFrom=" + countryFrom + ", shipping=" + shipping + ", link=" + link + ", totalPrice="
				+ totalPrice + "]";
	}

}
