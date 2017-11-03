package rpi.lmsgrabber;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import io.ddavison.conductor.Browser;
import io.ddavison.conductor.Config;
import io.ddavison.conductor.Locomotive;
import javafx.scene.control.TreeItem;

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

