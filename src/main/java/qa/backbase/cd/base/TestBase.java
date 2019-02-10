/**
 * 
 */
package qa.backbase.cd.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import qa.backbase.cd.util.TestUtil;
import qa.backbase.cd.util.WebEventListener;

/**
 * @author Rajayalakshmi G
 * 
 *         This class is used to read the properties and initialize the browser
 */
public class TestBase {

	public static Properties prop;
	public static WebDriver driver;
	public static EventFiringWebDriver e_driver;
	public static WebEventListener eventlistener;

	public TestBase() {
		prop = new Properties();
		try {
			FileInputStream propfile = new FileInputStream(
					System.getProperty("user.dir") + "/src/main/java/qa/backbase/cd/config/config.properties");
			prop.load(propfile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void browser_Initilization() {
		String testbrowser = prop.getProperty("browser");
		if (testbrowser.equals("Chrome")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/Drivers/chromedriver.exe");
			driver = new ChromeDriver();

		} else if (testbrowser.equals("FireFox")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/Drivers/geckodriver.exe");
			driver = new FirefoxDriver();
		}
		
		e_driver=new EventFiringWebDriver(driver);
		eventlistener=new WebEventListener();
		e_driver.register(eventlistener);
		driver=e_driver;
		
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.get(prop.getProperty("testurl"));
		driver.manage().timeouts().pageLoadTimeout(TestUtil.page_load_time_out, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtil.implicit_time_out, TimeUnit.SECONDS);
	}

}
