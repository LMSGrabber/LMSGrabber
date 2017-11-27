package rpi.lmsgrabber;

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

public class BlackboardGrab extends GenericGrabber {

  private static final String courseMenuString = "/webapps/blackboard/content/courseMenu.jsp?course_id=";

  public BlackboardGrab() {
    identifier = "Blackboard";
  }

  @Override
  public void grab() {
    action = new GrabAction();
    baseurl = "https://lms.rpi.edu";
    try {
      login();
      getCourseListings();
      for (CourseListing cl : subGrabbers) {
        getCourseContent(cl);
      }
    } catch (Exception e) {
      action.log.error("Malformed URL in grab", e);
    }
    action.closeWindow();
    action = null;
  }

  @Override
  public void login() throws MalformedURLException {
    action.navigateTo(baseurl);
    action.setText(By.name("user_id"), username).setText(By.name("password"), password)
        .click(By.id("entry-login"));
  }

  @Override
  public void getCourseListings() throws MalformedURLException {
    // From the home page, retrieve all links to current courses
    // We also need to remove any links present in the course data block to prevent announcements
    // from being interpreted as classes
    WebElement simpleTable = action.waitForElement(By.xpath("//*[@id=\"_3_1termCourses_noterm\"]/ul"));
    List<WebElement> links = simpleTable.findElements(By.tagName("a"));
    List<WebElement> courseDataBlocks = simpleTable.findElements(By.className("courseDataBlock"));
    for (WebElement we : courseDataBlocks) {
      links.removeAll(we.findElements(By.tagName("a")));
    }

    CourseListing[] cls = new CourseListing[links.size()];
    for (int i = 0; i < links.size(); i++) {
      cls[i] = new CourseListing();
      cls[i].course_name = links.get(i).getText();
      String course_url = links.get(i).getAttribute("href");
      cls[i].base_url = course_url;
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
    }
    addSubGrabber(cls);
  }

}
