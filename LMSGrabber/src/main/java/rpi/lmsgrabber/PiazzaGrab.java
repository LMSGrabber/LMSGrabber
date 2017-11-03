package rpi.lmsgrabber;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PiazzaGrab extends GenericGrabber {

  private static final Logger logger = LogManager.getLogger();
  
  public PiazzaGrab() {
    identifier = "Piazza";
  }

  @Override
  public void grab() {
    action = new GrabAction();
    baseurl = "https://piazza.com";
    try {
      login();
      getCourseListings();
    } catch (MalformedURLException murl) {
      action.log.error("Malformed URL in grab", murl);
    }
    action.driver.close();
    action = null;
  }

  @Override
  public void login() throws MalformedURLException {
    action.driver.navigate().to(baseurl);
    action.click(By.id("login_button")).setText(By.name("email"), username).setText(By.name("password"), password)
        .click(By.id("modal_login_button"));
  }

  @Override
  public void getCourseListings() throws MalformedURLException {
    // TODO Auto-generated method stub
  }


}
