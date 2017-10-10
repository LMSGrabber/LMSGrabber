package rpi.lmsgrabber;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class BlackboardGrab extends GenericGrabber {

  public void grab() throws MalformedURLException {
    base_url = "https://lms.rpi.edu";
    try {
      for (Course cl : getCourseListings()) {
        getCourseContent(cl);
      }
    } catch (MalformedURLException murl) {
      murl.printStackTrace();
    }
    driver.close();
  }

  public void login(String username, String password) throws MalformedURLException {
    driver.navigate().to(base_url);
    setText(By.name("user_id"), username).setText(By.name("password"), password)
        .click(By.id("entry-login"));
  }

  public void getCourseContent(Course cl) throws MalformedURLException {
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
        System.out.println("Attempting to get links on " + curl);

        driver.navigate().to(curl);
        // Get all links
        List<WebElement> links = driver.findElements(By.tagName("a"));
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
        System.out.println("Attempting to dl " + curl);
        driver.navigate().to(curl);
      }
    }
    // WGet wget = new WGet(cl.base_url, new File("test_html_jpl.html"));
    // wget.download();
  }

  @Override
  public Course[] getCourseListings() throws MalformedURLException {
    // From the home page, retrieve all links to current courses
    // We also need to remove any links present in the course data block to prevent announcements
    // from being interpreted as classes
    WebElement simpleTable = waitForElement(By.xpath("//*[@id=\"_3_1termCourses_noterm\"]/ul"));
    List<WebElement> links = simpleTable.findElements(By.tagName("a"));
    List<WebElement> courseDataBlocks = simpleTable.findElements(By.className("courseDataBlock"));
    for (WebElement we : courseDataBlocks) {
      links.removeAll(we.findElements(By.tagName("a")));
    }

    Course[] cls = new Course[links.size()];
    for (int i = 0; i < links.size(); i++) {
      cls[i] = new Course();
      cls[i].course_name = links.get(i).getText();
      cls[i].base_url = links.get(i).getAttribute("href");
    }
    return cls;
  }

  @Override
  public void loadSettings() throws IOException {
    File settings = new File("urls\\blackboard.csv");
    urlComp = new UrlComparator(
        CSVParser.parse(settings, java.nio.charset.StandardCharsets.UTF_8, CSVFormat.DEFAULT),
        base_url);
  }

}
