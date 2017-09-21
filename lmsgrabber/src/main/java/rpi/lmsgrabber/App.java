package rpi.lmsgrabber;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        //https://github.com/mozilla/geckodriver/releases
        //http://chromedriver.storage.googleapis.com/index.html
        //TODO other OS/Browser supports
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\Jake\\Documents\\GitHub\\LMSGrabber\\lmsgrabber\\driver\\geckodriver.exe");
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Jake\\Documents\\GitHub\\LMSGrabber\\lmsgrabber\\driver\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless");	//Enable to hide the window from the user
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://lms.rpi.edu/");
        System.out.println(driver.getTitle());
        driver.close();
    }
}
