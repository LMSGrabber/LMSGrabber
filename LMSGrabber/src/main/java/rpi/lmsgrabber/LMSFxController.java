/**
 * Sample Skeleton for 'ui_mockup.fxml' Controller Class
 */

package rpi.lmsgrabber;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class LMSFxController {
  
  private final ObservableList<GenericGrabber> data =
      FXCollections.observableArrayList();

  @FXML // ResourceBundle that was given to the FXMLLoader
  private ResourceBundle resources;

  @FXML // URL location of the FXML file that was given to the FXMLLoader
  private URL location;

  @FXML // fx:id="password"
  private PasswordField password; // Value injected by FXMLLoader

  @FXML // fx:id="btn_grab"
  private Button btn_grab; // Value injected by FXMLLoader

  @FXML // fx:id="url"
  private TextField url; // Value injected by FXMLLoader

  @FXML // fx:id="username"
  private TextField username; // Value injected by FXMLLoader

  @FXML // fx:id="treeoverview"
  private TreeView<GenericGrabber> treeoverview; // Value injected by FXMLLoader

  @FXML // fx:id="lmstableview"
  private TableView<GenericGrabber> lmstableview; // Value injected by FXMLLoader
  
  
  @FXML
  void on_btn_click_grab(ActionEvent event) {
    System.out.println("Trigger btn click grab");
    Thread thread = new Thread(new Runnable() {
      public void run() {
        for(GenericGrabber grabber : data)
          grabber.grab();
      }

    });

    thread.start();
  }
  
  @FXML
  void btn_click_add_new_lms(ActionEvent event) {
    System.out.println("Trigger btn click add new LMS");
    BlackboardGrab grabber = new BlackboardGrab();
    grabber.username = username.getText();
    grabber.password = password.getText();
    grabber.baseurl = url.getText();
    username.clear();
    password.clear();
    url.clear();
    data.add(grabber);
    lmstableview.setItems(data);
  }


  @FXML // This method is called by the FXMLLoader when initialization is complete
  void initialize() {
    assert password != null : "fx:id=\"password\" was not injected: check your FXML file 'ui_mockup.fxml'.";
    assert btn_grab != null : "fx:id=\"btn_grab\" was not injected: check your FXML file 'ui_mockup.fxml'.";
    assert url != null : "fx:id=\"url\" was not injected: check your FXML file 'ui_mockup.fxml'.";
    assert username != null : "fx:id=\"username\" was not injected: check your FXML file 'ui_mockup.fxml'.";
    
    lmstableview.getColumns().get(0).setCellValueFactory(
        new PropertyValueFactory<>("identifier")
    );
    lmstableview.getColumns().get(1).setCellValueFactory(
        new PropertyValueFactory<>("baseurl")
    );
    lmstableview.getColumns().get(2).setCellValueFactory(
        new PropertyValueFactory<>("username")
    );
    lmstableview.getColumns().get(3).setCellValueFactory(
        new PropertyValueFactory<>("lastupdated")
    );

  }
}
