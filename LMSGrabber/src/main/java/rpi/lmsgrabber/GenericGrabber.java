package rpi.lmsgrabber;

import java.net.MalformedURLException;
import io.ddavison.conductor.Browser;
import io.ddavison.conductor.Config;
import io.ddavison.conductor.Locomotive;

@Config(browser = Browser.CHROME)
public abstract class GenericGrabber extends Locomotive {
  public String base_url;

  public abstract void grab(); // Gets all files

  public abstract void login() throws MalformedURLException; // Logs in to website

  public abstract CourseListing[] getCourseListings() throws MalformedURLException; // Gets a list
                                                                                    // of all
                                                                                    // courses on
                                                                                    // this website
}

