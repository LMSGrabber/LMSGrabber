/**
 * Sample Skeleton for 'ui_mockup.fxml' Controller Class
 */

package rpi.lmsgrabber;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class LMSFxController {

  @FXML // ResourceBundle that was given to the FXMLLoader
  private ResourceBundle resources;

  @FXML // URL location of the FXML file that was given to the FXMLLoader
  private URL location;

  @FXML // fx:id="password"
  private PasswordField password; // Value injected by FXMLLoader

  @FXML // fx:id="btn_grab"
  private Button btn_grab; // Value injected by FXMLLoader

  @FXML // fx:id="web_preview"
  private WebView web_preview; // Value injected by FXMLLoader

  @FXML // fx:id="url"
  private TextField url; // Value injected by FXMLLoader

  @FXML // fx:id="username"
  private TextField username; // Value injected by FXMLLoader

  @FXML
  void login_lms(ActionEvent event) {
    WebEngine webEngine = web_preview.getEngine();
    webEngine.load("https://lms.rpi.edu");
    web_preview.setZoom(0.25f);

    Thread thread = new Thread(new Runnable() {
      public void run() {
        BlackboardGrab grabber = new BlackboardGrab();
        grabber.grab(username.getText(), password.getText());
      }

    });

    thread.start();


  }

  @FXML // This method is called by the FXMLLoader when initialization is complete
  void initialize() {
    assert password != null : "fx:id=\"password\" was not injected: check your FXML file 'ui_mockup.fxml'.";
    assert btn_grab != null : "fx:id=\"btn_grab\" was not injected: check your FXML file 'ui_mockup.fxml'.";
    assert web_preview != null : "fx:id=\"web_preview\" was not injected: check your FXML file 'ui_mockup.fxml'.";
    assert url != null : "fx:id=\"url\" was not injected: check your FXML file 'ui_mockup.fxml'.";
    assert username != null : "fx:id=\"username\" was not injected: check your FXML file 'ui_mockup.fxml'.";

  }
}
