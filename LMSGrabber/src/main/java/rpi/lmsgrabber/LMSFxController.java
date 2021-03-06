/**
 * Sample Skeleton for 'ui_mockup.fxml' Controller Class
 */

package rpi.lmsgrabber;

import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.util.Callback;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.util.StringConverter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import net.bytebuddy.description.type.TypeDescription.Generic;

public class LMSFxController {

  private final ObservableList<GenericGrabber> data = FXCollections.observableArrayList();
  private final ObservableList<Class<? extends GenericGrabber>> allowed_grabbers =
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

  @FXML // fx:id="btn_add_new_lms"
  private Button btn_add_new_lms; // Value injected by FXMLLoader

  @FXML // fx:id="username"
  private TextField username; // Value injected by FXMLLoader

  @FXML // fx:id="treeoverview"
  private TreeView<GenericGrabber> treeoverview; // Value injected by FXMLLoader

  @FXML // fx:id="lmstableview"
  private TableView<GenericGrabber> lmstableview; // Value injected by FXMLLoader

  @FXML // fx:id="cmb_lms_type_selector"
  private ComboBox<Class<? extends GenericGrabber>> cmb_lms_type_selector;

  private Stage parent;

  public void setParent(Stage parent) {
    this.parent = parent;
  }

  @FXML
  void onBtnClickAbout(ActionEvent event) {
    try {
      java.awt.Desktop.getDesktop().browse(new URI("https://github.com/LMSGrabber/LMSGrabber"));
    } catch (IOException | URISyntaxException e) {
      e.printStackTrace();
    }
  }

  @FXML
  void onBtnClickSettings(ActionEvent event) {
    try {
      SettingsWindow test = new SettingsWindow(this.parent);
      test.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  @FXML
  void onBtnClickGrab(ActionEvent event) {
    for (final GenericGrabber grabber : data) {
      Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            grabber.grab();
        }

      });
      thread.start();
    }
  }

  @FXML
  void onBtnClickAddNewLMS(ActionEvent event) {
    try {
      GenericGrabber grabber =
          cmb_lms_type_selector.getSelectionModel().getSelectedItem().newInstance();
      grabber.username = username.getText();
      grabber.password = password.getText();
      grabber.baseurl = url.getText();
      username.clear();
      password.clear();
      url.clear();
      data.add(grabber);
      lmstableview.setItems(data);
      TreeItem<GenericGrabber> item = new TreeItem<GenericGrabber>(grabber);
      grabber.myTreeItem = item;
      treeoverview.getRoot().getChildren().add(item);
    } catch (InstantiationException | IllegalAccessException e) {
      e.printStackTrace();
    }
  }


  @FXML // This method is called by the FXMLLoader when initialization is complete
  void initialize() {
    // Check for loading
    assert treeoverview != null : "fx:id=\"treeoverview\" was not injected: check your FXML file 'ui_mockup.fxml'.";
    assert btn_grab != null : "fx:id=\"btn_grab\" was not injected: check your FXML file 'ui_mockup.fxml'.";
    assert lmstableview != null : "fx:id=\"lmstableview\" was not injected: check your FXML file 'ui_mockup.fxml'.";
    assert username != null : "fx:id=\"username\" was not injected: check your FXML file 'ui_mockup.fxml'.";
    assert url != null : "fx:id=\"url\" was not injected: check your FXML file 'ui_mockup.fxml'.";
    assert cmb_lms_type_selector != null : "fx:id=\"cmb_lms_type_selector\" was not injected: check your FXML file 'ui_mockup.fxml'.";
    assert btn_add_new_lms != null : "fx:id=\"btn_add_new_lms\" was not injected: check your FXML file 'ui_mockup.fxml'.";
    assert password != null : "fx:id=\"password\" was not injected: check your FXML file 'ui_mockup.fxml'.";

    // Set up combo box
    allowed_grabbers.add(BlackboardGrab.class);
    allowed_grabbers.add(PiazzaGrab.class);

    //Force it to display shortened LMS names
    cmb_lms_type_selector.setConverter(
                new StringConverter<Class<? extends GenericGrabber>>() {
                    @Override
                    public String toString(Class<? extends GenericGrabber> grabby) {
                        try {
                        if (grabby == null) {
                            return "";
                        } else {
                            return (grabby).newInstance().getIdentifier();
                        }
                      }
                      catch(Exception e)
                      {
                        return "";
                      }
                    }

                    @Override
                    public Class<? extends GenericGrabber> fromString(String s) {
                        return null;
                    }

                });

    cmb_lms_type_selector.setItems(allowed_grabbers);

    treeoverview.setCellFactory(new Callback<TreeView<GenericGrabber>, TreeCell<GenericGrabber>>() {
    @Override
    public TreeCell<GenericGrabber> call(TreeView<GenericGrabber> p) {
        return new TextFieldTreeCell<GenericGrabber>(new StringConverter<GenericGrabber>(){

            @Override
            public String toString(GenericGrabber object) {
                return object.getIdentifier();
            }

            @Override
            public GenericGrabber fromString(String string) {
                return null;
            }
        });
    }
});


    treeoverview.setRoot(new TreeItem(null));
  }
}
