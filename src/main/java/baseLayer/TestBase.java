package baseLayer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import utilityLayer.WebEventListener;

public class TestBase {

	public static Properties prop;

	public TestBase() {
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(System.getProperty("user.dir")
					+ "/src/main/java" + "/environmentLayer/config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public WebDriver initialization() throws MalformedURLException {
		EventFiringWebDriver e_driver;
		WebEventListener eventListener;
		WebDriver driver = null;

		String browserName = prop.getProperty("browser");

		if (browserName.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", prop.getProperty("chromeDriver"));
			driver = new ChromeDriver();

		} else if (browserName.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver", prop.getProperty("geckoDriver"));
			driver = new FirefoxDriver();
		}

		e_driver = new EventFiringWebDriver(driver);

		eventListener = new WebEventListener();
		e_driver.register(eventListener);

		driver = e_driver;

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Long.parseLong(prop.getProperty("mediumWaitSeconds")),
				TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(Long.parseLong(prop.getProperty("mediumWaitSeconds")),
				TimeUnit.SECONDS);

		try {
			driver.get(prop.getProperty("baseURL"));
		} catch (Exception e) {
			driver.quit();
		}
		return driver;
	}
}
