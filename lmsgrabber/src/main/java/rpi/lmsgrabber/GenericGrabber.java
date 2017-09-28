package rpi.lmsgrabber;

import com.google.gson.Gson;
import io.ddavison.conductor.Browser;
import io.ddavison.conductor.Config;
import io.ddavison.conductor.Locomotive;

@Config(browser = Browser.CHROME)
public abstract class GenericGrabber extends Locomotive {
  public String base_url;

  public abstract void grab(); // Gets all files

  public abstract void login(); // Logs in to website

  public abstract CourseListing[] getCourseListings(); // Gets a list of all courses on this website
}


class CourseListing {
  String base_url; // URL of page that the COURSE is located on
  String student_name; // Student taking this course
  String student_id; // Student ID of student taking this course
  String professor; // Name of professor(s) teaching this course
  String year; // Year course is taken
  String semester; // Semester course is taken
  String course_name; // General name of course
  String course_id; // ID of course
  String course_registration_id; // Registration ID of course

  @Override
  public String toString() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }
}
