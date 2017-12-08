package rpi.lmsgrabber;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.DialogPane;
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
  @FXML // fx:id="input_proxy"
  private TextField input_proxy; // Value injected by FXMLLoader

  @FXML // fx:id="settings_cancel"
  private TextField settings_cancel_btn; // Value injected by FXMLLoader

  public SettingsWindow(Stage parent) throws IOException {
    super(StageStyle.UNDECORATED);
//    super();
    // Load root layout from fxml file.
    FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/settings_menu.fxml"));

    loader.setController(this);

//    System.out.println(parent);

    this.initModality(Modality.WINDOW_MODAL);
    this.initOwner(parent);
    this.setAlwaysOnTop(true);
//    System.out.println("Doing the thing");

    TabPane layout = loader.load();

//    this.getScene().setRoot(layout);
//    layout.setScene(this.getScene());

    Scene scene2 = new Scene(layout);
    this.setScene(scene2);

    this.setTitle("LMSGrabber Settings");
  }

  @FXML
  void onBtnClickCancel(ActionEvent event) {
    this.close();
  }

  @FXML
  void onBtnClickApply(ActionEvent event) {
    System.out.println("Applying!");
  }
}