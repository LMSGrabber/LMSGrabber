package rpi.lmsgrabber;

import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class BlackboardGrab extends GenericGrabber {
  public void grab() {
    base_url = "https://lms.rpi.edu";
    login();
    getCourseListings();
    driver.close();
  }

  public void grab(String username, String password) {
    base_url = "https://lms.rpi.edu";
    login(username, password);
    getCourseListings();
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
  public void login() {
    driver.navigate().to(base_url);

    // Get user credentials, retry while invalid
    String[] cred = null;

    while (cred == null) {
      cred = getUserCredentials();
    }

    setText(By.name("user_id"), cred[0]).setText(By.name("password"), cred[1])
        .click(By.id("entry-login"));
  }

  @Override
  public CourseListing[] getCourseListings() {
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
      System.out.println(links.get(i).getAttribute("href"));
      cls[i].base_url = links.get(i).getAttribute("href");
      System.out.println(cls[i]);
    }
    return cls;
  }

}
