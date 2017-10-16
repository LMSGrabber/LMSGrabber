package rpi.lmsgrabber;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import com.google.gson.Gson;

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

  // Essentially treat the site as a graph
  // We need to visit all nodes, and their neighbors, while avoiding cycles.
  Set<String> previously_visited = new HashSet<String>();
  Set<String> to_visit = new HashSet<String>();

  public URL getURL() {
    try {
      return new URL(base_url);
    } catch (MalformedURLException e) {
      return null;
    }
  }

  @Override
  public String toString() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }
}