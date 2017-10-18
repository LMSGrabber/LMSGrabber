package rpi.lmsgrabber;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

public class UrlComparator {

  // These query strings are ignored for equivalence
  public static final String QUERY_IGNORE = "query_ignore";
  // Any URLs with this query string are blacklisted
  public static final String QUERY_BLACKLIST = "query_blacklist";
  // Any URLs on our main host site that match this path are blacklisted
  public static final String ONHOST_BLACKLIST = "onhost_blacklist";
  // Any URLs that match this are blacklisted

  public static final String EXTERNAL_BLACKLIST = "external_blacklist";
  // <Type, Set<Value>>
  public Map<String, HashSet<String>> url_settings = new HashMap<String, HashSet<String>>();
  public URI host;

  public UrlComparator(CSVParser csvParser, String host) {
    try {
      for (CSVRecord csv : csvParser.getRecords()) {
        System.out.println(csv.toString());
        String val_type = csv.get(0);
        String val = csv.get(1);

        if (!url_settings.containsKey(val_type)) {
          url_settings.put(val_type, new HashSet<String>());
        }

        url_settings.get(val_type).add(val);
      }
      this.host = new URI(host);

    } catch (IOException e) {
      e.printStackTrace();
    } catch (URISyntaxException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public boolean isBlacklistedSite(String url) {
    // TODO
    // Check that the site is not:
    // - Equivalent to anything on the external blacklist
    // - Contains any blacklisted query string
    // - Equivalent to anything on host blacklisted
    
    //Check external blacklist
    
    return false;
  }

  public boolean isValidQueryString(String query) {
    // TODO
    return false;
  }

  public boolean isOnHost(String url) {
    try {
      return isOnHost(new URI(url));
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean isOnHost(URI url) {
    return this.host.getHost().equals(url.getHost());
  }

  public boolean isOnHost(URL url) {
    try {
      return isOnHost(url.toURI());
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    return false;
  }

  public boolean isEquivalent(String a, String b) {
    // Need to check:
    // - All non-blacklisted query strings are equivalent TODO
    // - Same host
    // - Same path
    
    URI u1 = null;
    URI u2 = null;
    try {
      u1 = new URI(a);
      u2 = new URI(b);
    } catch (URISyntaxException e) {
      e.printStackTrace();
      return false;
    }
    
    //Get host equivalence
    if(u1.getHost().equals(u2.getHost()))
    {
      //Get path equivalence
      if(u1.getPath().equals(u2.getPath()))
      {
        //Get query string rough equivalence
        List<NameValuePair> q1 = URLEncodedUtils.parse(u1, java.nio.charset.StandardCharsets.UTF_8);
        List<NameValuePair> q2 = URLEncodedUtils.parse(u1, java.nio.charset.StandardCharsets.UTF_8);
        
        //TODO remove ignored query strings, then compare for list equivalence

        for (NameValuePair param : q1) {
          System.out.println(param.getName() + " : " + param.getValue());
        }          
      }
      else
      {
        return false;
      }
    }
    else
    {
      return false;
    }
    
    
    return false;
  }

}
