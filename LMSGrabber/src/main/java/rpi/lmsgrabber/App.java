package rpi.lmsgrabber;

import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("restriction")
public class App extends Application {

  private static final Logger logger = LogManager.getLogger();

  private Stage primaryStage;
  private Pane rootLayout;

  @Override
  public void start(Stage primaryStage) {
    try {
      // Load root layout from fxml file.
      FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/ui_mockup.fxml"));

      LMSFxController controller = new LMSFxController();
      controller.setParent(primaryStage);
      loader.setController(controller);

      rootLayout = loader.load();

      primaryStage.setTitle("LMSGrabber");

      // Show the scene containing the root layout.
      Scene scene = new Scene(rootLayout);
      primaryStage.setScene(scene);
      primaryStage.show();
    } catch (IOException e) {
      logger.error("IOException", e);
    }

  }

  public static void main(String[] args) {
    launch(args);
  }

}
