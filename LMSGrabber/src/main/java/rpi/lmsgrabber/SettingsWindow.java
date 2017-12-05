package rpi.lmsgrabber;

import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.fxml.FXMLLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("restriction")
public class SettingsWindow extends Stage {
  public SettingsWindow(Stage parent) throws IOException {
    super(StageStyle.DECORATED);
    // Load root layout from fxml file.
    FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/settings_menu.fxml"));

    loader.setController(this);

    System.out.println(parent);

    this.initModality(Modality.WINDOW_MODAL);
    this.initOwner(parent);
    this.setAlwaysOnTop(true);

    TabPane layout = loader.load();

    Scene scene2 = new Scene(layout);
    this.setScene(scene2);

    this.setTitle("LMSGrabber Settings");
  }
}