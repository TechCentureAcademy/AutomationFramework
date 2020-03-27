package com.batch2.AutomationFrameworkFromScratch.utilities;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverConfig {
	
	public WebDriverConfig() {
		
	}
	
	private static WebDriver driver = null;
	public static Logger logger = LogManager.getLogger(WebDriverConfig.class);

	public static WebDriver getDriver() throws MalformedURLException {
		String browser = "";
		try {
			browser = System.getProperty(Constants.BROWSER);
		} catch (Exception e) {
			logger.info("Browser parameter was not passed. Taking browser parameter from properties file");
		}
    
		if(browser == null || browser.isEmpty()){
			browser = ConfigurationReader.getProperty("browser");
		}

		if (driver == null) {
			switch (browser) {
			case "chrome":
				ChromeOptions options = getOptionsChrome("chrome");
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver(options);
				break;
			case "chrome-headless":
				ChromeOptions optionsHeadless = getOptionsChrome("chrome-headless");
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver(optionsHeadless);
				break;
			case "ff":
				FirefoxOptions option = getOptionsFF();
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver(option);
				break;
			case "ff-headless":
				FirefoxBinary firefoxBinary = new FirefoxBinary();
				firefoxBinary.addCommandLineOptions("--headless");
				WebDriverManager.firefoxdriver().setup();
				FirefoxOptions optionFFheadless = getOptionsFF();
				optionFFheadless.setBinary(firefoxBinary);
				driver = new FirefoxDriver(optionFFheadless);
				break;
			case "ie":
				InternetExplorerOptions ieOpts = getOptionsIE();
				WebDriverManager.iedriver().setup();
				driver = new InternetExplorerDriver(ieOpts);
				break;
			case "phantom":
				DesiredCapabilities phantomCapabilities = new DesiredCapabilities();
				phantomCapabilities.setJavascriptEnabled(true);
				phantomCapabilities.setCapability("takesScreenshot", true);
				phantomCapabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS,
						new String[] { "--web-security=no", "--ignore-ssl-errors=yes" });
				WebDriverManager.phantomjs().setup();
				driver = new PhantomJSDriver(phantomCapabilities);
				break;
			case "remote-ie":
				InternetExplorerOptions IEOpts = getOptionsIE();
				WebDriverManager.iedriver().setup();
				driver = new RemoteWebDriver(new URL("http://10.137.242.215:46808/wd/hub"), IEOpts);
				break;
			default:
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
			}		
		}
		return driver;
	}

	private static ChromeOptions getOptionsChrome(String browser) {
		DesiredCapabilities capability = DesiredCapabilities.chrome();
		ChromeOptions optns = new ChromeOptions();
		if (browser.equalsIgnoreCase("chrome")) {
			capability.setBrowserName(browser);
			capability.setJavascriptEnabled(true);
			capability.setPlatform(Platform.ANY);
			optns = new ChromeOptions();
			optns.addArguments("test-type");
			optns.addArguments("--disable-extensions");
			optns.merge(capability);
		} else if (browser.equalsIgnoreCase("chrome-headless")) {
			capability.setBrowserName(browser);
			capability.setJavascriptEnabled(true);
			capability.setPlatform(Platform.ANY);
			optns = new ChromeOptions();
			optns.setHeadless(true);
			optns.addArguments("test-type");
			optns.addArguments("--disable-extensions");
			optns.merge(capability);
		} else {
			logger.error("Browser name is wrong");
			optns = null;
		}
		return optns;
	}

	private static FirefoxOptions getOptionsFF() {
		FirefoxOptions fxOptns = new FirefoxOptions();
		 fxOptns.addPreference("dom.max_chrome_script_run_time", "999");
		 fxOptns.addPreference("dom.max_script_run_time", "999");
		 fxOptns.addPreference("browser.helperApps.alwaysAsk.force", false);
		 fxOptns.addPreference("browser.download.manager.showWhenStarting",
		 false);
		 fxOptns.addPreference("plugin.disable_full_page_plugin_for_types",
		 "application/pdf");
		 fxOptns.addPreference("pdfjs.disabled", true);
		 fxOptns.addPreference("browser.helperApps.neverAsk.saveToDisk",
		 "application/pdf");
		 fxOptns.addPreference("extensions.update.enabled", false);
		 fxOptns.addPreference("app.update.enabled", false);
		 fxOptns.addPreference("app.update.auto", false);
		 fxOptns.addPreference("network.http.use-cache", false);
		 fxOptns.setCapability("marionette", true);
		
		return fxOptns;
	}

	private static InternetExplorerOptions getOptionsIE() {
		DesiredCapabilities desiredCapabilities = DesiredCapabilities.internetExplorer();
		desiredCapabilities.setBrowserName("internet explorer");
		desiredCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
				Boolean.TRUE);
		desiredCapabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, Boolean.TRUE);
		desiredCapabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
		desiredCapabilities.setJavascriptEnabled(true);
		InternetExplorerOptions ieOpts = new InternetExplorerOptions();
		ieOpts.merge(desiredCapabilities);

		return ieOpts;
	}

	public static void closeDriver() {
		if (driver != null) {
			driver.quit();
			driver = null;
		}
	}

}
