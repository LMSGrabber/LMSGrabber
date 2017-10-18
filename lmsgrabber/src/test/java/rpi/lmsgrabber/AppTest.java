package rpi.lmsgrabber;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
  
  File settings = new File("urls\\tests.csv");
  UrlComparator urlComp = null;
  
  /**
   * Create the test case
   *
   * @param testName name of the test case
   */
  public AppTest(String testName) {
    super(testName);
    try {
      urlComp = new UrlComparator(
          CSVParser.parse(settings, java.nio.charset.StandardCharsets.UTF_8, CSVFormat.DEFAULT),
          "https://lms.rpi.edu");
    } catch (IOException e) {
      e.printStackTrace();
    }  
  }

  /**
   * @return the suite of tests being tested
   */
  public static Test suite() {
    return new TestSuite(AppTest.class);
  }

  /**
   * @throws IOException 
   */
  public void testOffsiteBlacklist() throws IOException {
  
    //Test offsite blacklists
    Iterator<String> offsite_it = urlComp.url_settings.get(UrlComparator.EXTERNAL_BLACKLIST).iterator();
    while(offsite_it.hasNext())
    {
      String next = offsite_it.next();
      System.out.println("Testing if '" + next + "' is off site blacklisted");
      assertTrue(urlComp.isBlacklistedSite(next));
    }
  
  }
  
}
