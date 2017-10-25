package rpi.lmsgrabber;

import java.net.MalformedURLException;
import io.ddavison.conductor.Browser;
import io.ddavison.conductor.Config;
import io.ddavison.conductor.Locomotive;

@Config(browser = Browser.CHROME)
public abstract class GenericGrabber extends Locomotive {
  public String baseurl;
  public String identifier;
  public String username;
  public String password;
  public String lastupdated;

  //Gets all files
  public abstract void grab();
  //Logs in to website
  public abstract void login() throws MalformedURLException;
  //Gets a list of all courses on this website
  public abstract CourseListing[] getCourseListings() throws MalformedURLException; 
  
  String getUsername()
  {
    return username;
  }
  void setUsername(String value)
  {
    this.username = value;
  }
  String getBaseurl()
  {
    return baseurl;
  }
  void setBaseurl(String value)
  {
    this.baseurl = value;
  }
  String getIdentifier()
  {
    return identifier;
  }
  void setIdentifier(String value)
  {
    this.identifier = value;
  }

}

