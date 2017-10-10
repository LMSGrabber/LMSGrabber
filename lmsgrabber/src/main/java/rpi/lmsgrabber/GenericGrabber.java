package rpi.lmsgrabber;

import java.io.IOException;
import java.net.MalformedURLException;
import io.ddavison.conductor.Browser;
import io.ddavison.conductor.Config;
import io.ddavison.conductor.Locomotive;

@Config(browser = Browser.CHROME)
public abstract class GenericGrabber extends Locomotive {

  public UrlComparator urlComp;

  public String base_url;

  // Gets all files
  public abstract void grab() throws MalformedURLException;

  // Logs in to website
  public abstract void login(String username, String password) throws MalformedURLException;

  // Gets a list of all courses on this website
  public abstract Course[] getCourseListings() throws MalformedURLException;

  public abstract void loadSettings() throws IOException;

}
