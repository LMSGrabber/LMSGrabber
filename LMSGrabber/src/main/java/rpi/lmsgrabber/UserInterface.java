package rpi.lmsgrabber;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

@SuppressWarnings("restriction")
public class UserInterface extends Application {

  private Stage primaryStage;
  private Pane rootLayout;

  @Override
  public void start(Stage primaryStage) {
    try {
      // Load root layout from fxml file.
      FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/ui_mockup.fxml"));
      rootLayout = loader.load();

      // Show the scene containing the root layout.
      Scene scene = new Scene(rootLayout);
      primaryStage.setScene(scene);
      primaryStage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public static void main(String[] args) {
    launch(args);
  }

}
