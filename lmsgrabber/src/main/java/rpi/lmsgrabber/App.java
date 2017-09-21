package rpi.lmsgrabber;

import javax.swing.JOptionPane;

import org.eclipse.jetty.util.Scanner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.ddavison.conductor.Browser;
import io.ddavison.conductor.Config;
import io.ddavison.conductor.Locomotive;

/**
 * Hello world!
 *
 */
@Config(
		browser=Browser.CHROME,
		url="https://lms.rpi.edu"
		)
public class App extends Locomotive
{
	
	public App()
	{
		/*
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
        */
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\Jake\\Documents\\GitHub\\LMSGrabber\\lmsgrabber\\geckodriver.exe");
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Jake\\Documents\\GitHub\\LMSGrabber\\lmsgrabber\\chromedriver.exe");

        login_lms();
        
        //driver.close();
		
	}
	
    public static void main( String[] args )
    {        
    	App a = new App();        
    }
    

    public void login_lms() {
        setText(By.name("user_id"), JOptionPane.showInputDialog("Enter LMS username"))
        .setText(By.name("password"), JOptionPane.showInputDialog("Enter LMS password"))
        .click(By.id("entry-login"));
    }
}
