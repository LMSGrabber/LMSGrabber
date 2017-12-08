package rpi.lmsgrabber;

import java.net.URI;
import java.net.URL;
import org.openqa.selenium.WebDriver;
import io.ddavison.conductor.Browser;
import io.ddavison.conductor.Config;
import io.ddavison.conductor.Locomotive;

// Note: Conductor 3.0.1 does not actually support Gecko
@Config(browser = Browser.CHROME)
public class GrabAction extends Locomotive {

  @Override
  public Locomotive navigateTo(String url) {
    System.out.println("Visited " + url);
    return super.navigateTo(url);
  }

  public void navigateTo(URL url) {
    navigateTo(url.toString());
  }

  public void navigateTo(URI uri)
  {
    navigateTo(uri.toString());
  }

/*
  public WebDriver getDriver() {
    System.err.println("Warning: Accessing webdriver directly");
    return super.getDriver();
  }
*/
}
