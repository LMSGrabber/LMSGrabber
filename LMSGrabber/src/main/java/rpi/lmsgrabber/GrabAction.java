package rpi.lmsgrabber;

import org.openqa.selenium.WebDriver;
import io.ddavison.conductor.Browser;
import io.ddavison.conductor.Config;
import io.ddavison.conductor.Locomotive;

@Config(browser = Browser.CHROME)
public class GrabAction extends Locomotive {
  
  @Override
  public Locomotive navigateTo(String url) {
    System.out.println("Visited " + url);
    return super.navigateTo(url);
  }

  @Override
  public WebDriver getDriver() {
    System.err.println("Warning: Accessing webdriver directly");
    return super.getDriver();
  }
  
}
