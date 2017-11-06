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
import org.apache.logging.log4j.Logger;

public class BlackboardGrab extends GenericGrabber {

  private static final Logger logger = LogManager.getLogger();
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

  public void getCourseContent(CourseListing cl) throws MalformedURLException {
    //action.navigateTo(baseurl + courseMenuString + cl.course_id);
    
    cl.to_visit.add(cl.base_url);
    // TODO Temporary lazy solution: Just say we already visited the logout page
    // TODO need to remove cycles that involve URL modifiers
    cl.previously_visited.add("https://lms.rpi.edu/webapps/login/?action=logout");
    while (!cl.to_visit.isEmpty()) {
      String current = cl.to_visit.iterator().next(); // Current URL
      URL curl = new URL(current);
      cl.to_visit.remove(current);
      cl.previously_visited.add(current);

      // Only get links off of host pages, and download the others
      if (curl.getHost().equals(cl.getURL().getHost())) {
        logger.debug("Attempting to get links on {}", curl);

        action.navigateTo(curl);
        // Get all links
        List<WebElement> links = action.driver.findElements(By.tagName("a"));
        Set<String> links_str = new HashSet<String>();
        for (WebElement we : links) {
          String href = we.getAttribute("href");
          if ((href != null) && !href.startsWith("javascript")) {
            links_str.add(href);
          }
        }
        // Remove all that have already been visited
        links_str.removeAll(cl.previously_visited);
        cl.to_visit.addAll(links_str);
      } else {
        logger.debug("Attempting to download {}", curl);
        action.navigateTo(curl);
      }
    }
    // WGet wget = new WGet(cl.base_url, new File("test_html_jpl.html"));
    // wget.download();
    
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
