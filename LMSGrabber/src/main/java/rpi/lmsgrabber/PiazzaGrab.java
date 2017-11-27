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
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;

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
    //action.driver.close();
    action = null;
  }

  @Override
  public void login() throws MalformedURLException {
    action.navigateTo(baseurl);
    action.click(By.id("login_button")).setText(By.name("email"), username).setText(By.name("password"), password)
        .click(By.id("modal_login_button"));
  }

  @Override
  public void getCourseListings() throws MalformedURLException {
    // From the home page, retrieve all links to current courses
    List<WebElement> links = action.driver.findElements(By.xpath("//*[@data-pats='classes_dropdown_item']/div"));
    System.out.println(links.size());//*[@id="network_j6feqds1z771k1"]/
    //*[@id="network_j6feqds1z771k1"]/div
    CourseListing[] cls = new CourseListing[links.size()];
    for (int i = 0; i < links.size(); i++) {
      cls[i] = new CourseListing();
      String script = "return arguments[0].innerText";
      cls[i].course_name  = (String) ((JavascriptExecutor) action.driver).executeScript(script, links.get(i));
      String course_url = "";//links.get(i).getAttribute("id");
      cls[i].base_url = course_url;
      /*
      try {
        List<NameValuePair> params;
        params = URLEncodedUtils.parse(new URI(course_url), java.nio.charset.StandardCharsets.UTF_8);
        //Find course_id str
        for(NameValuePair nvp : params)
        {
          if(nvp.getName().equals("course_id"))
          {
            cls[i].course_id = nvp.getValue();
                break;
          }
        }
      } catch (URISyntaxException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      */
    }
    addSubGrabber(cls);
    }
}
