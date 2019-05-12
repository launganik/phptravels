package testLibrary;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.mail.EmailException;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.log4testng.Logger;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import baseLayer.TestBase;
import pageObjectLibrary.HomePage;
import utilityLayer.TestUtil;

public class HomePageTest extends TestBase {

	Logger log = Logger.getLogger(HomePageTest.class);
	Object data[][];
	int testDataScanner = 0;

	String userDataSheetPath = prop.getProperty("userDataSheetPath");
	String userDataSheetName = prop.getProperty("userDataSheetName");

	public HomePageTest() {
		super();
	}

	@BeforeClass
	public void setUpBeforeClass() {
	}

	@BeforeMethod
	public void setUpBeforeMethod() {
	}

	@DataProvider
	public Object[][] getTestData() {
		Object data[][] = TestUtil.getDataset(userDataSheetPath, userDataSheetName);
		return data;
	}

	@Test(dataProvider = "getTestData")
	public void makeBookingE2ETest(String info[]) throws MalformedURLException, InterruptedException {
		ExtentReports extent = TestUtil.extentReportSetup();
		ExtentTest logger = extent.startTest("Make a Booking");
		WebDriver driver = initialization();
		this.testDataScanner++;
		performTests(info, driver, extent, logger);
		// Can use STE to schedule at fixed rate instead of one by one:
		// scheduledExecutor.scheduleAtFixedRate(homePageRunnable, 0, 10,
		// TimeUnit.SECONDS);
	}

	private void performTests(String[] info, WebDriver driver, ExtentReports extent, ExtentTest logger) {

		HomePage hPage = new HomePage(driver);
		
		//Send fName, lName, email
		hPage.runBookingTest(info[0], info[1], info[2], this.testDataScanner, logger);

		extent.endTest(logger);
		extent.flush();
		extent.close();
		driver.quit();
	}

	@AfterMethod
	public void tearDownAfterMethod(ITestResult result) throws IOException, EmailException {

		if (result.getStatus() == ITestResult.FAILURE) {
			log.fatal(result.getMethod(), result.getThrowable());
		}
	}

	@AfterClass
	public void tearDownAfterClass() throws EmailException {

	}
}
