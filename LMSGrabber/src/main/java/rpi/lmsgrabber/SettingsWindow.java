package rpi.lmsgrabber;

import java.io.IOException;
import java.util.prefs.Preferences;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TabPane;
import javafx.stage.Modality;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("restriction")
public class SettingsWindow extends Stage {
  private Preferences prefs = Preferences.userNodeForPackage(App.class);

  @FXML // fx:id="min_delay_slider"
  private Slider min_delay_slider; // Value injected by FXMLLoader

  @FXML // fx:id="max_delay_slider"
  private Slider max_delay_slider; // Value injected by FXMLLoader

  @FXML // fx:id="input_proxy"
  private TextField input_proxy; // Value injected by FXMLLoader

  @FXML // fx:id="settings_cancel"
  private TextField settings_cancel_btn; // Value injected by FXMLLoader

  @FXML // fx:id="multithreaded_check"
  private CheckBox multithreaded_check; // Value injected by FXMLLoader

  public SettingsWindow(Stage parent) throws IOException {
    super(StageStyle.UNDECORATED);
    prefs = Preferences.userNodeForPackage(App.class);

    // Load root layout from fxml file.
    FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/settings_menu.fxml"));

    loader.setController(this);

    this.initModality(Modality.WINDOW_MODAL);
    this.initOwner(parent);
    this.setAlwaysOnTop(true);

    TabPane layout = loader.load();

    Scene scene2 = new Scene(layout);
    this.setScene(scene2);

    this.setTitle("LMSGrabber Settings");

    min_delay_slider.setValue(prefs.getDouble("min_delay", 1.0));
    max_delay_slider.setValue(prefs.getDouble("max_delay", 3.0));
    input_proxy.setText(prefs.get("proxy", ""));
    multithreaded_check.setSelected(prefs.getBoolean("multithreaded", true));
  }

  @FXML
  void onBtnClickCancel(ActionEvent event) {
    this.close();
  }

  @FXML
  void onBtnClickApply(ActionEvent event) {
    prefs.putDouble("min_delay", min_delay_slider.getValue());
    prefs.putDouble("max_delay", max_delay_slider.getValue());
    prefs.put("proxy", input_proxy.getText());
    prefs.putBoolean("multithreaded", multithreaded_check.isSelected());

    this.close();
  }
}