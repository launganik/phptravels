package stepDefinitions;

import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import baseLayer.TestBase;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pageObjectLibrary.HomePage;
//import junit.framework.Assert;
import utilityLayer.TestUtil;

public class LoginStepDefinition extends TestBase {

	public LoginStepDefinition() {
		super();
	}

	WebDriver driver;

	@Given("user is already on Home Page")
	public void user_is_already_on_Home_Page() throws MalformedURLException {
		driver = initialization();
	}

	@When("user enters checkin & checkout date")
	public void user_enters_checkin_checkout_date() {
		// Need to refactor
	}

	@Then("user tries to book the first hotel")
	public void user_tries_to_book_the_first_hotel() {
		ExtentReports extent = TestUtil.extentReportSetup();
		ExtentTest logger = extent.startTest("Make a Booking");
		HomePage hPage = new HomePage(driver);
		hPage.runBookingTest("Kamal", "Laungani", "launganik@yahoo.com", 1, logger);
	}

	@Then("user closes the browser")
	public void user_closes_the_browser() {
		driver.quit();
	}

}
