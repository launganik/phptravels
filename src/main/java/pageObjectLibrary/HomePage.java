package pageObjectLibrary;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import baseLayer.TestBase;
import utilityLayer.TestUtil;

public class HomePage extends TestBase {

	WebDriver driver = null;
	public long maxTimeOut = 0;
	int dealNumber = 1;

	public HomePage(WebDriver dr) {
		driver = dr;
		PageFactory.initElements(driver, this);
	}

	@FindBy(how = How.XPATH, using = "//div[@id='dpd1']//input[@class='form input-lg dpd1']")
	WebElement checkInField;

	@FindBy(how = How.XPATH, using = "//input[@class='form input-lg dpd2']")
	WebElement checkOutField;

	@FindBy(how = How.XPATH, using = "//div[@class='visible-lg visible-md']//input[@class='btn btn-block btn-success-small textupper loader']")
	WebElement modifyButton;

	@FindBy(how = How.XPATH, using = "//button[@class='btn btn-lg btn-block btn-primary pfb0 loader']")
	WebElement searchButton;

	@FindBy(how = How.XPATH, using = "//tr[1]//td[1]//div[3]//a[1]//button[1]")
	WebElement firstDealButton;

	@FindBy(how = How.XPATH, using = "//tr[1]//td[1]//div[2]//div[2]//div[1]//div[3]//div[1]//label[1]//div[1]")
	WebElement firstCheckBox;

	@FindBy(how = How.ID, using = "map")
	WebElement map;

	@FindBy(how = How.XPATH, using = "//button[@class='book_button btn btn-md btn-success btn-block btn-block chk mob-fs10 loader']")
	WebElement bookNowButton;

	@FindBy(how = How.XPATH, using = "//input[@placeholder='First Name']")
	WebElement firstName;

	@FindBy(how = How.XPATH, using = "//input[@placeholder='Last Name']")
	WebElement lastName;

	@FindBy(how = How.XPATH, using = "//div[@class='col-md-5 col-xs-12']//input[@placeholder='Email']")
	WebElement emailField;

	@FindBy(how = How.XPATH, using = "//input[@placeholder='Confirm Email']")
	WebElement confirmEmailField;

	@FindBy(how = How.XPATH, using = "//button[@name='guest']")
	WebElement confirmBookingButton;

	public void runBookingTest(String fName, String lName, String email, int elementNumber, ExtentTest logger) {
		// Hard coding dates for demo
		checkInField.sendKeys("14/07/2019");
		checkOutField.sendKeys("15/07/2019");
		searchButton.click();
		WebDriverWait wait = new WebDriverWait(driver, Long.parseLong(prop.getProperty("mediumWaitSeconds")));
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//tr[" + elementNumber + "]//td[1]//div[3]//a[1]//button[1]")));
		
		// This will scroll the web page till end.
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

		driver.findElement(By.xpath("//tr[" + elementNumber + "]//td[1]//div[3]//a[1]//button[1]")).click();

		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

		if (driver.findElements(By.xpath("//tr[1]//td[1]//div[2]//div[2]//div[1]//div[3]//div[1]//label[1]//div[1]"))
				.size() != 0) {
			firstCheckBox.click();
			bookNowButton.click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='First Name']")));

			firstName.sendKeys(fName);
			lastName.sendKeys(lName);
			emailField.sendKeys(email);
			confirmEmailField.sendKeys(email);
			confirmBookingButton.click();
			logger.log(LogStatus.PASS, "Pass: Hotel booking successful");
		} else {
			logger.log(LogStatus.FAIL, "Fail: The selected hotel is not bookable");
			String screenshotPath = TestUtil.getScreenshot(driver);
			logger.addScreenCapture(screenshotPath);
		}
	}

}
