package rpi.lmsgrabber;

import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import io.ddavison.conductor.Browser;
import io.ddavison.conductor.Config;
import io.ddavison.conductor.Locomotive;

public class BlackboardGrab extends GenericGrabber {
  public void grab() {
    base_url = "https://lms.rpi.edu";
    login();
    getCourseListings();
    // driver.close();
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
    WebElement simpleTable = waitForElement(By.xpath("//*[@id=\"_3_1termCourses_noterm\"]/ul"));
    List<WebElement> rows = simpleTable.findElements(By.tagName("a"));
    CourseListing[] cls = new CourseListing[rows.size()];
    for (int i = 0; i < rows.size(); i++) {
      System.out.println(rows.get(i).getText());
      cls[i] = new CourseListing();
      cls[i].course_name = rows.get(i).getText();
    }
    return cls;
  }

}
