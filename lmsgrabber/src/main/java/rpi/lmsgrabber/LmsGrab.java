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

@Config(browser = Browser.CHROME, url = "https://lms.rpi.edu")
public class LmsGrab extends Locomotive {
	
	public void grab()
	{
		login_lms();

		getCourseInformation();
		// driver.close();
	}
	
	public void getCourseInformation()
	{
		//From the home page, retrieve all links to current courses
		WebElement simpleTable = waitForElement(By.xpath("//*[@id=\"_3_1termCourses_noterm\"]/ul"));
		List<WebElement> rows = simpleTable.findElements(By.tagName("a"));
		for (WebElement r : rows) {
			System.out.println(r.getText());
		}
	}

	// Get through the login page
	public void login_lms() {
		JPanel panel = new JPanel();
		JLabel pwlabel = new JLabel("Enter a password:");
		JLabel unlabel = new JLabel("Enter a username:");
		JPasswordField pass = new JPasswordField(50);
		JTextField username = new JTextField(50);

		panel.add(unlabel);
		panel.add(username);
		panel.add(pwlabel);
		panel.add(pass);
		String[] options = new String[] { "OK", "Cancel" };
		int selected = JOptionPane.showOptionDialog(null, panel, "Enter LMS credentials", JOptionPane.NO_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, username);
		// TODO submit on enter as well as "OK"
		// TODO adjust layout to have username and password fields appropriately sized
		// TODO check for login failures
		if (selected == 0) {
			setText(By.name("user_id"), username.getText()).setText(By.name("password"), new String(pass.getPassword()))
					.click(By.id("entry-login"));
		} else {
			System.err.println("Error, user canceled login. Need to handle this more gracefully.");
		}
	}

}
