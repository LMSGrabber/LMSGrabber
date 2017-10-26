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
import edu.uci.ics.crawler4j.*;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.authentication.AuthInfo;
import edu.uci.ics.crawler4j.crawler.authentication.FormAuthInfo;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class BlackboardGrab extends GenericGrabber {

  private static final Logger logger = LogManager.getLogger();

  public BlackboardGrab() {
    identifier = "Blackboard";
  }

  @Override
  public void grab() {
    baseurl = "https://lms.rpi.edu/";
    String crawlStorageFolder = "/tmp/crawl";
    CrawlConfig config = new CrawlConfig();
    config.setCrawlStorageFolder(crawlStorageFolder);
    AuthInfo authJavaForum;
    try {
      authJavaForum = new FormAuthInfo(username, password, "https://lms.rpi.edu/webapps/login/", "user_id", "password");
    } catch (MalformedURLException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
      return;
    }
    config.addAuthInfo(authJavaForum);

    PageFetcher pageFetcher = new PageFetcher(config);
    RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
    robotstxtConfig.setEnabled(false);
    RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
    CrawlController controller;
    try {
      controller = new CrawlController(config, pageFetcher, robotstxtServer);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return;
    }

    /*
     * For each crawl, you need to add some seed urls. These are the first
     * URLs that are fetched and then the crawler starts following links
     * which are found in these pages
     */
//    controller.addSeed("http://www.ics.uci.edu/~lopes/");
//    controller.addSeed("http://www.ics.uci.edu/~welling/");
//    controller.addSeed("http://www.ics.uci.edu/");
//    controller.addSeed("https://lms.rpi.edu/");
    controller.addSeed("https://lms.rpi.edu/webapps/portal/execute/tabs/tabAction?tabId=_1_1");

    /*
     * Start the crawl. This is a blocking operation, meaning that your code
     * will reach the line after this only when crawling is finished.
     */
    controller.start(BlackboardCrawler.class, Runtime.getRuntime().availableProcessors());
//    try {
//      login();
//      for (CourseListing cl : getCourseListings()) {
//        getCourseContent(cl);
//      }
//    } catch (MalformedURLException murl) {
//      log.error("Malformed URL in grab", murl);
//    }
//    driver.close();
  }

  @Override
  public void login() throws MalformedURLException {
    return;
  }

  public void getCourseContent(CourseListing cl) throws MalformedURLException {
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
        logger.debug("Attempting to download {}", curl);
        driver.navigate().to(curl);
      }
    }
    // WGet wget = new WGet(cl.base_url, new File("test_html_jpl.html"));
    // wget.download();
  }

  @Override
  public CourseListing[] getCourseListings() throws MalformedURLException {
    // From the home page, retrieve all links to current courses
    // We also need to remove any links present in the course data block to prevent announcements
    // from being interpreted as classes
    WebElement simpleTable = waitForElement(By.xpath("//*[@id=\"_3_1termCourses_noterm\"]/ul"));
    List<WebElement> links = simpleTable.findElements(By.tagName("a"));
    List<WebElement> courseDataBlocks = simpleTable.findElements(By.className("courseDataBlock"));
    for (WebElement we : courseDataBlocks) {
      links.removeAll(we.findElements(By.tagName("a")));
    }

    CourseListing[] cls = new CourseListing[links.size()];
    for (int i = 0; i < links.size(); i++) {
      cls[i] = new CourseListing();
      cls[i].course_name = links.get(i).getText();
      cls[i].base_url = links.get(i).getAttribute("href");
    }
    return cls;
  }

}
