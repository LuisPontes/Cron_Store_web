package cron.tools;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class SeleniumUtils {

	private WebDriver driver;

	public SeleniumUtils() {
		System.setProperty("webdriver.gecko.driver", "./geckodriver");
		System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
		System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
	}

	public HtmlUnitDriver createHtmlDriver() {
		HtmlUnitDriver driver = null;
		try{
			driver = new HtmlUnitDriver();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return driver;
	}
	
	public HtmlUnitDriver createHtmlDriverWithJS() {
		HtmlUnitDriver driver = new HtmlUnitDriver();
		driver.setJavascriptEnabled(true);
		return driver;
	}
	
	public WebDriver createDriver() {
		this.driver = new FirefoxDriver();
		return this.driver;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public List<WebElement> pullAllLinks(WebDriver d) {
		return driver.findElements(By.name("src"));
	}
}
