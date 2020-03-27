package com.batch2.AutomationFrameworkFromScratch.step_definitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.net.MalformedURLException;

import org.openqa.selenium.WebElement;

import com.batch2.AutomationFrameworkFromScratch.pages.Etsy_HomePage;
import com.batch2.AutomationFrameworkFromScratch.pages.MyAccount_Page;
import com.batch2.AutomationFrameworkFromScratch.utilities.BrowserUtils;
import com.batch2.AutomationFrameworkFromScratch.utilities.ConfigurationReader;
import com.batch2.AutomationFrameworkFromScratch.utilities.DataGenerator;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class etsy_StepDefinition extends BrowserUtils {
	
	public etsy_StepDefinition() throws MalformedURLException {
		
	}

	public String home_Page_url = System.getProperty("etsy_test");
	private Etsy_HomePage home_Page = new Etsy_HomePage();

	private MyAccount_Page accountPage = new MyAccount_Page();

	private DataGenerator generator = new DataGenerator();
	private String name;

	@When("User clicks on the Your Account icon")
	public void user_clicks_on_the_Your_Account_icon() throws MalformedURLException {
		waitForVisibility(homePage.myAccount, 5);
		homePage.myAccount.click();
	}

	@Then("from the list picks the View your profile tab")
	public void from_the_list_picks_the_View_your_profile_tab() throws MalformedURLException {
		waitForVisibility(accountPage.viewProfile, 5);
		accountPage.viewProfile.click();
	}

	@Then("User clicks on Edit Profile")
	public void user_clicks_on_Edit_Profile() {
		// waitForVisibility(accountPage.editProfileLink, 5);
		accountPage.editProfileLink.click();
	}

	@Then("User clicks on Change or remove hyperlink")
	public void user_clicks_on_Change_or_remove_hyperlink() throws MalformedURLException {
		waitForVisibility(accountPage.changeOrRemoveLink, 5);
		accountPage.changeOrRemoveLink.click();
	}

	@Then("User enters the First Name and the Last Name")
	public void user_enters_the_First_Name_and_the_Last_Name() throws MalformedURLException {
		waitForVisibility(accountPage.firstNameField, 5);
		// clearing the first name field and entering a first name
		accountPage.firstNameField.clear();
		accountPage.firstNameField.sendKeys(generator.getFirstName());

		// clearing and entering the last name field
		accountPage.lastNameField.clear();
		accountPage.lastNameField.sendKeys(generator.getFirstName());
	}

	@Then("User verifies that the name was changed")
	public void user_verifies_that_the_name_was_changed() {

	}

	@Then("User enters {string} into the field")
	public void user_enters_into_the_field(String name) {
		this.name = name;
		if (name.contains("first")) {
			homePage.enterDataInTheField(generator.getFirstName() + 123, accountPage.firstNameField);
		} else if (name.contains("last")) {
			homePage.enterDataInTheField(generator.getLastName() + 123, accountPage.lastNameField);
		}
	}

	@Then("User verifies the {string}")
	public void user_verifies_the(String expectedErrorMsg) {
		String actualErrorMsg = "";
		if (name.contains("first")) {
			actualErrorMsg = accountPage.firstNameErrorMsg.getText();
		} else if (name.contains("last")) {
			actualErrorMsg = accountPage.lastNameErrorMsg.getText();
		}
		assertEquals(expectedErrorMsg, actualErrorMsg);
	}

	@Then("User verifies that the Save_Button is disabled")
	public void user_verifies_that_the_Save_Button_is_disabled() throws MalformedURLException {
		waitForVisibility(accountPage.saveButton, 5);
		assertFalse(accountPage.saveButton.isEnabled());
	}

	String Home_Page_url = ConfigurationReader.getProperty("url");
	private Etsy_HomePage homePage = new Etsy_HomePage();

	@Given("User is on the home page")
	public void user_is_on_the_home_page() {
		homePage.goToPage(Home_Page_url);
	}

	@When("User clicks on the {string} button")
	public void user_clicks_on_the_button(String button) throws InterruptedException {
		WebElement clickOnButton = null;
		if (button.equals("Sign in")) {
			clickOnButton = homePage.SignInButton;
		} else if (button.equals("Sign in Login")) {
			clickOnButton = homePage.submitLoginButton;
		}
		clickOnButton.click();
		// Thread.sleep(2000);
	}

	@Then("User enters valid {string} and {string}")
	public void user_enters_valid_and(String userID, String password) throws InterruptedException {
		// entering userID to the email field
		// Thread.sleep(2000);
		homePage.enterDataInTheField(userID, homePage.email);

		// entering password to the password field
		homePage.enterDataInTheField(password, homePage.password);
	}

	@Then("User verifies that he is on the account page")
	public void user_verifies_that_he_is_on_the_account_page() throws InterruptedException {
		String expectedOutput = "You";
		Thread.sleep(2000);
		String actualOutput = homePage.myAccount.getText().trim();
		System.out.println("the actual output is: " + actualOutput);
		// assertTrue(actualOutput.contains(expectedOutput));
	}

	@Then("User enters valid username and password")
	public void user_enters_valid_username_and_password1() {
		// entering userID to the email field
		homePage.enterDataInTheField(ConfigurationReader.getProperty("userid"), homePage.email);

		// entering password to the password field
		homePage.enterDataInTheField(ConfigurationReader.getProperty("password"), homePage.password);
	}

}
