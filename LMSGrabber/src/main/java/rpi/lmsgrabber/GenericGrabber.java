package rpi.lmsgrabber;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import io.ddavison.conductor.Browser;
import io.ddavison.conductor.Config;
import io.ddavison.conductor.Locomotive;
import org.apache.logging.log4j.Logger;
import javafx.scene.control.TreeItem;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import org.apache.logging.log4j.LogManager;


public abstract class GenericGrabber {
  public String baseurl;
  public String identifier;
  public String username;
  public String password;
  public String lastupdated;
  GrabAction action = null;
  TreeItem<GenericGrabber> myTreeItem;

  public List<CourseListing> subGrabbers = new ArrayList<CourseListing>();

  public void addSubGrabber(CourseListing cl)
  {
    cl.myTreeItem = new TreeItem<GenericGrabber>(cl);
    myTreeItem.getChildren().add(cl.myTreeItem);
    subGrabbers.add(cl);
  }

    public void getCourseContent(CourseListing cl) throws MalformedURLException {
      Logger logger = LogManager.getLogger();

      //action.navigateTo(baseurl + courseMenuString + cl.course_id);

      cl.to_visit.add(cl.base_url);
      // TODO Temporary lazy solution: Just say we already visited the logout page
      // TODO need to remove cycles that involve URL modifiers
      cl.previously_visited.add("https://lms.rpi.edu/webapps/login/?action=logout");
      cl.previously_visited.add("");

      Random rand = new Random();

      while (!cl.to_visit.isEmpty()) {
        try
        {
            TimeUnit.SECONDS.sleep(rand.nextInt(10) + 1);
        }
        catch(InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }

        try
        {
        String current = cl.to_visit.iterator().next(); // Current URL
        System.out.println("####### CREATING A URL FROM:" + current);
        URL curl = new URL(current);
        cl.to_visit.remove(current);
        cl.previously_visited.add(current);

        // Only get links off of host pages, and download the others
        if (curl.getHost().equals(cl.getURL().getHost())) {
          System.out.println("Attempting to get links on {}" + curl);
          System.out.flush();
          action.navigateTo(curl);
          // Get all links
          List<WebElement> links = action.driver.findElements(By.tagName("a"));
          Set<String> links_str = new HashSet<String>();
          for (WebElement we : links) {
            int attempts = 0;
            //Handle stale elements by retrying
            while(true) {
              try
              {
                String href = we.getAttribute("href");
                if ((href != null) && !href.startsWith("javascript")) {
                  links_str.add(href);
                }
                break;
              }
              catch(Exception e)
              {
                attempts++;
                if(attempts >= 5)
                  break;
              }
            }
          }
          // Remove all that have already been visited
          links_str.removeAll(cl.previously_visited);
          cl.to_visit.addAll(links_str);
        } else {
          System.out.println("Attempting to download {}" + curl);
          action.navigateTo(curl);
        }
      }
      catch(MalformedURLException me)
      {
        me.printStackTrace();
      }
      // WGet wget = new WGet(cl.base_url, new File("test_html_jpl.html"));
      // wget.download();
    }

    }

  public void addSubGrabber(CourseListing[] cl)
  {
    for(CourseListing c : cl)
      addSubGrabber(c);
  }

  // Gets all files
  public abstract void grab();

  // Logs in to website
  public abstract void login() throws MalformedURLException;

  // Gets a list of all courses on this website
  public abstract void getCourseListings() throws MalformedURLException;

  public String getUsername() {
    return username;
  }

  public void setUsername(String value) {
    this.username = value;
  }

  public String getBaseurl() {
    return baseurl;
  }

  public void setBaseurl(String value) {
    this.baseurl = value;
  }

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String value) {
    this.identifier = value;
  }

}
