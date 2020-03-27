package com.batch2.AutomationFrameworkFromScratch.utilities;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.net.MalformedURLException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.batch2.*;

public class BrowserUtils {
	
	public BrowserUtils() throws MalformedURLException {
		
	}
	
	public WebDriverWait wait;

	public int randomNumber = new Random().nextInt(9999);

	public static Logger logger = LogManager.getLogger(BrowserUtils.class);

	JavascriptExecutor js = (JavascriptExecutor) WebDriverConfig.getDriver();

	public void newWait(int sec) throws MalformedURLException {
		wait = new WebDriverWait(WebDriverConfig.getDriver(), sec);
	}

	public boolean waitForClickability(WebElement button) throws MalformedURLException {
		newWait(20);
		wait.until(ExpectedConditions.elementToBeClickable(button));
		return true;
	}
	
	public boolean waitForClickabilityWithTime(WebElement button, int sec) throws MalformedURLException {
		newWait(sec);
		wait.until(ExpectedConditions.elementToBeClickable(button));
		return true;
	}

	public void waitForVisibility(WebElement button, int waitSec) throws MalformedURLException {
		newWait(waitSec);
		wait.until(ExpectedConditions.visibilityOf(button));
	}

	public void waitForInvisability(WebElement element) throws MalformedURLException {
		newWait(20);
		wait.until(ExpectedConditions.invisibilityOf(element));
	}

	public void moveToElementAndClickOutside(WebElement element) throws MalformedURLException {
		new Actions(WebDriverConfig.getDriver()).moveToElement(element).moveByOffset(200, 0).click().perform();
	}

	public void moveToElementAndClick(WebElement element) throws MalformedURLException {
		new Actions(WebDriverConfig.getDriver()).moveToElement(element).click().perform();
	}

	public void checkIfCertificateAndAccept() throws InterruptedException, MalformedURLException {
		String title = WebDriverConfig.getDriver().getTitle();
		String expectedTitle = "Certificate Error";
		if (title.contains(expectedTitle)) {
			WebDriverConfig.getDriver().get("javascript:document.getElementById('overridelink').click();");
			logger.info("Capture title: " + title);
			WebDriverConfig.getDriver().get("javascript:document.getElementById('overridelink').click();");
		} else {
			logger.error("Title is not matching: " + title);
			return;
		}
	}

	public void PrivacyErrorCheck() throws MalformedURLException {
		String title = WebDriverConfig.getDriver().getTitle();
		String expectedTitle = "Privacy Error";
		if (title.contains(expectedTitle)) {
			WebDriverConfig.getDriver().get("javascript:document.getElementById('overridelink').click();");
			logger.info("Capture title: " + title);
			PrivacyErrorCheck();
		} else {
			logger.error("Title is not matching: " + title);
			return;
		}
	}

	public void goingToHomePage(String url) throws MalformedURLException {
		WebDriverConfig.getDriver().get(url);
		logger.info("Opened the url: " + url);
	}

	public static void switchToWindow(String targetTitle) throws MalformedURLException {
		String origin = WebDriverConfig.getDriver().getWindowHandle();
		for (String handle : WebDriverConfig.getDriver().getWindowHandles()) {
			WebDriverConfig.getDriver().switchTo().window(handle);
			if (WebDriverConfig.getDriver().getTitle().equals(targetTitle)) {
				return;
			}
		}
		WebDriverConfig.getDriver().switchTo().window(origin);
	}

	public final void waitForPageToLoad() throws MalformedURLException {
		JavascriptExecutor js = (JavascriptExecutor) WebDriverConfig.getDriver();
		for (int seconds = 1; seconds <= 60; seconds++) {
			try {
				if (js.executeScript("return document.readyState").equals("complete")) {
					break;
				}
			} catch (Exception e) {
			}
			waitTime(1);
		}
	}

	public static void waitTime(int Seconds) {
		try {
			Thread.sleep(Seconds * 1000);
		} catch (InterruptedException e) {
			logger.error("Error while waiting");
		}
	}

	// Method to clear Field Data
	public void clearFieldData(WebElement element) throws MalformedURLException {
		waitForVisibility(element, 20);
		element.sendKeys(Keys.CONTROL + "a");
		element.sendKeys(Keys.DELETE);
	}

	public void ButtonNotClicableVerification(WebElement button) {
		Assert.assertFalse(button.isEnabled());
	}

	public void goBack() throws MalformedURLException {
		newWait(20);
		waitForPageToLoad();
		WebDriverConfig.getDriver().navigate().back();
		waitForPageToLoad();
	}

	public void refresh() throws InterruptedException, MalformedURLException {
		newWait(5);
		WebDriverConfig.getDriver().navigate().refresh();
		waitForPageToLoad();
	}

	public int getRandomNumber(int boundary) {
		Random random = new Random();
		int number = random.nextInt(boundary);
		return number;
	}

	public int getRandomNumber(int lowerBoundary, int upperBoundary) {
		Random random = new Random();
		int number = random.nextInt(upperBoundary - lowerBoundary) + lowerBoundary;
		return number;
	}

	public void clickButton(List<WebElement> button) throws MalformedURLException {
		waitForVisibility(button.get(0), 20);
		if (button.size() > 0) {
			if (button.size() == 1) {
				logger.info("Capture button: " + button.get(0).getText());
				waitForClickability(button.get(0));
				button.get(0).click();
			} else {
				WebElement finalButton = button.get(new Random().nextInt(button.size() - 1));
				logger.info("Capture button " + finalButton.getText());
				waitForClickability(finalButton);
				finalButton.click();
			}
		} else
			logger.warn("No buttons are available to click");

	}

	public void enterDataInField(String data, WebElement field) throws MalformedURLException {
		clearTheField(field);
		field.sendKeys(data);
	}

	public void clearTheField(WebElement field) throws MalformedURLException {
		waitForVisibility(field, 20);
		// field.click();
		field.clear();
	}

	/**
	 * 
	 * @param data
	 *            - visible text that will be selected, String or null
	 * @param dropdown
	 * @param index
	 *            - number of the element that will be selected, index or -1
	 * @throws MalformedURLException
	 */
	public void pickDataFromDropDown(String data, WebElement dropdown, int index, List<WebElement> optionsXpath)
			throws MalformedURLException {
		waitForVisibility(dropdown, 20);
		Select select = new Select(dropdown);
		if (data != null) {
			if (data.equalsIgnoreCase("random")) {
				Random randIndex = new Random();
				select.selectByIndex(randIndex.nextInt(optionsXpath.size()));
			} else {
				select.selectByVisibleText(data);
				logger.info("Picking data by Visible Text ---->" + data);
			}
		} else if (index != -1) {
			select.selectByIndex(index);
			logger.info("Picking data by index ---->" + index);
		}
	}

	public boolean checkIfOptionIsDisplayed(String data, WebElement dropdown) throws MalformedURLException {
		waitForVisibility(dropdown, 20);
		Select select = new Select(dropdown);
		for (WebElement element : select.getOptions()) {
			if (element.getText().contains(data))
				return true;
		}
		return false;
	}

	public void scrollingToElement(WebDriver driver, WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
	}

	public void scrollingToBottomOfThePage(WebDriver driver) {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	/**
	 * Use this method also when you need to scroll UP. Just pass minus integer
	 * (-250)
	 * 
	 * @param coordinate
	 * @param driver
	 */
	public void scrollingByCoordinate(int coordinate, WebDriver driver) {
		if (coordinate > 0 || coordinate < 0) {
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0," + coordinate + ")");
		} else {
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,250)");
		}
	}

	/**
	 * 
	 * @param path
	 *            - path to your file
	 * @param element
	 *            - uploading field
	 */
	public void uploadFileFromTestData(String path, WebElement element) {
		try {
			element.sendKeys(path);
		} catch (Exception e) {
			logger.error("File path is incorrect!");
		}
	}

	public void waitForInvisibility(WebElement element, String selector) {
		try {
			if (element.isDisplayed()) {
				Wait<WebDriver> wait = new FluentWait<>(WebDriverConfig.getDriver()).withTimeout(Duration.ofSeconds(20))
						.pollingEvery(Duration.ofSeconds(1)).ignoring(NoSuchElementException.class);
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(selector)));
			}
		} catch (Exception ignore) {
		}
	}

	public String pickRandomPgmNmSpaces() {
		String[] programName = new String[] { "  qwertyui", "00000dfgh  " };
		String pgmNm = programName[new Random().nextInt(programName.length)];
		return pgmNm;
	}

	public int numberVerification(WebElement elementWithNumber, String description) {
		int number = 0;
		String[] elements = elementWithNumber.getText().split(" ");
		String element = elements[0];

		if (element.equals("No")) { // elementWithNumber.getText().trim().contains("No")
			number = 0;
		} else if (NumberUtils.isParsable(element)) {
			number = Integer.valueOf(element);
		} else {
			numberVerification(elementWithNumber, description);
		}
		return number;
	}

	public static boolean isClickable(WebElement el, WebDriver driver) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 6);
			wait.until(ExpectedConditions.elementToBeClickable(el));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean ClickOnButtonTryCatch(WebElement button) throws MalformedURLException {
		boolean checkr = false;
		try {
			if (button.isDisplayed()) {
				button.click();
				logger.info("Clicked on the button" + button.getText());
				checkr = true;
			}
		} catch (Throwable e) {
			logger.error(button.getText() + " button is not clickable at the time");
			
		}
		return checkr;
	}

	// Method to scroll into view using javascript executor
	public void scrollIntoViewJS(WebElement webElement) {
		js.executeScript("arguments[0].scrollIntoView();", webElement);
	}

	// Method to click on Element using javascript executor
	public void clickOnElementJS(WebElement webElement) {
		// scrollIntoViewJS(webElement);
		js.executeScript("arguments[0].click();", webElement);
	}

	/*
	 * Method to handle credentials setup
	 */
	public static Map<String, String> getEnvironmentAndSetCredentials(String env, String role) {
		Map<String, String> credentials = new HashMap<String, String>();
		if (env.contains("test")) {
			credentials.put("userid", ConfigurationReader.getProperty("TestHARPUserID" + role));
			credentials.put("password", ConfigurationReader.getProperty("TestHARPPassword" + role));
		} else if (env.contains("sbx")) {
			credentials.put("userid", ConfigurationReader.getProperty("SbxHARPUserID" + role));
			credentials.put("password", ConfigurationReader.getProperty("SbxHARPPassword" + role));
		} else if (env.contains("local")) {
			credentials.put("userid", ConfigurationReader.getProperty("SbxHARPUserID" + role));
			credentials.put("password", ConfigurationReader.getProperty("SbxHARPPassword" + role));
		} else if (env.contains("impl")) {
			credentials.put("userid", ConfigurationReader.getProperty("ImplHARPUserID" + role));
			credentials.put("password", ConfigurationReader.getProperty("ImplHARPPassword" + role));
		} else if (env.contains("prod")) {
			credentials.put("userid", ConfigurationReader.getProperty("ProdHARPUserID" + role));
			credentials.put("password", ConfigurationReader.getProperty("ProdHARPPassword" + role));
		}
		return credentials;
	}

	public void checkIfFieldIsEmpty(WebElement field) throws MalformedURLException {
		waitForVisibility(field, 15);
		assertTrue(field.getAttribute("value").isEmpty());
	}

	public boolean checkIfDisplayed(WebElement element) {
		try {
			if (element.isDisplayed() && element.isEnabled())
				return true;
		} catch (NoSuchElementException e) {
			return false;
		}
		return false;
	}

	public static void screenshot(WebDriver driver, long ms) {
		try {
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(source, new File("./ScreenShots/" + ms + "Facebook.png"));
			logger.info("ScreenShot Taken");
		} catch (Exception e) {
			logger.warn("Exception while taking ScreenShot " + e.getMessage());
		}
	}
	
	public static boolean containsIgnoreCase(String source, String expected) {
	    final int length = expected.length();
	    if (length == 0)
	        return true; // Empty string is contained

	    final char firstLo = Character.toLowerCase(expected.charAt(0));
	    final char firstUp = Character.toUpperCase(expected.charAt(0));

	    for (int i = source.length() - length; i >= 0; i--) {
	        // Quick check before calling the more expensive regionMatches() method:
	        final char ch = source.charAt(i);
	        if (ch != firstLo && ch != firstUp)
	            continue;

	        if (source.regionMatches(true, i, expected, 0, length))
	            return true;
	    }

	    return false;
	}

}
