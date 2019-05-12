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

import baseLayer.TestBase;

public class LoginPage extends TestBase{
	
	//Object Repository
	@FindBy(how = How.ID, using="mainForm:login_email")
	WebElement emailid;
	
	@FindBy(how = How.ID, using="mainForm:login_pw")
	WebElement password;
	
	@FindBy(how = How.XPATH, using="//div[@class='login-btn login-do button button-green']")
	WebElement loginBtn;	
	
	By userMgmtLink = By.xpath("//div[@class='header-element blue-hover']");
	
	WebDriver driver = null;
	
	//Initializing the Page Objects:
	public LoginPage(WebDriver dr){
		driver = dr;
		PageFactory.initElements(driver, this);
	}
	
	//Actions:
	
	public void adminLogin(String un, String pwd){
		emailid.sendKeys(un);
		password.sendKeys(pwd);
		//loginBtn.click();
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();", loginBtn);
		
		WebDriverWait wait = new WebDriverWait(driver, Long.parseLong(prop.getProperty("mediumWaitMilliSeconds")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(userMgmtLink));
		
		//return new UserManagementPage();
	}	

}
