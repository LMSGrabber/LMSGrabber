package rpi.lmsgrabber;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class BlackboardGrab extends GenericGrabber {

  @Override
  public void grab() {
    base_url = "https://lms.rpi.edu";
    try {
      login();
      for (CourseListing cl : getCourseListings()) {
        getCourseContent(cl);
      }
    } catch (MalformedURLException murl) {
      murl.printStackTrace();
    }
    driver.close();
  }

  public void grab(String username, String password) {
    base_url = "https://lms.rpi.edu";
    try {
      login(username, password);
      for (CourseListing cl : getCourseListings()) {
        getCourseContent(cl);
      }
    } catch (MalformedURLException murl) {
      murl.printStackTrace();
    }
    driver.close();
  }

  private void login(String username, String password) {
    driver.navigate().to(base_url);
    setText(By.name("user_id"), username).setText(By.name("password"), password)
        .click(By.id("entry-login"));
  }

  public String[] getUserCredentials() {
    JPanel panel = new JPanel();
    JLabel pwlabel = new JLabel("Enter a password:");
    JLabel unlabel = new JLabel("Enter a username:");
    JPasswordField pass = new JPasswordField(50);
    JTextField username = new JTextField(50);

    panel.add(unlabel);
    panel.add(username);
    panel.add(pwlabel);
    panel.add(pass);
    String[] options = new String[] {"OK", "Cancel"};
    int selected = JOptionPane.showOptionDialog(null, panel, "Enter LMS credentials",
        JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, username);
    // TODO submit on enter as well as "OK"
    // TODO adjust layout to have username and password fields appropriately sized
    // TODO check for login failures
    if (selected == 0) {
      return new String[] {username.getText(), new String(pass.getPassword())};
    } else {
      System.err.println("Error, user canceled login.");
      driver.close();
      System.exit(0);
      return null;
    }
  }

  @Override
  public void login() throws MalformedURLException {
    driver.navigate().to(base_url);

    // Get user credentials, retry while invalid
    String[] cred = null;

    while (cred == null) {
      cred = getUserCredentials();
    }

    setText(By.name("user_id"), cred[0]).setText(By.name("password"), cred[1])
        .click(By.id("entry-login"));
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
