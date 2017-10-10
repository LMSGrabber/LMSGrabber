package rpi.lmsgrabber;

import static javafx.geometry.HPos.RIGHT;
import java.io.IOException;
import java.net.MalformedURLException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

// Based on login code from -
// http://docs.oracle.com/javase/8/javafx/get-started-tutorial/form.htm#BABDDGEE

@SuppressWarnings("restriction")
public class UserInterface extends Application {

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("LMS Grabber");

    final BlackboardGrab grabber = new BlackboardGrab();
    try {
      grabber.loadSettings();
    } catch (IOException e2) {
      e2.printStackTrace();
    }


    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(25, 25, 25, 25));

    Text scenetitle = new Text("Welcome to LMS Grabber. \nPlease log in!");
    scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
    grid.add(scenetitle, 0, 0, 2, 1);

    Label userName = new Label("User Name:");
    grid.add(userName, 0, 1);

    final TextField userTextField = new TextField();
    grid.add(userTextField, 1, 1);

    Label pw = new Label("Password:");
    grid.add(pw, 0, 2);

    final PasswordField pwBox = new PasswordField();
    grid.add(pwBox, 1, 2);

    Button btn = new Button("Sign in");
    HBox hbBtn = new HBox(10);
    hbBtn.setAlignment(Pos.BOTTOM_CENTER);
    hbBtn.getChildren().add(btn);
    grid.add(hbBtn, 1, 4);

    final Text actiontarget = new Text();
    grid.add(actiontarget, 0, 6);
    GridPane.setColumnSpan(actiontarget, 2);
    GridPane.setHalignment(actiontarget, RIGHT);
    actiontarget.setId("actiontarget");

    btn.setOnAction(new EventHandler<ActionEvent>() {

      public void handle(ActionEvent e) {
        try {
          grabber.login(userTextField.getText(), pwBox.getText());
          grabber.grab();
        } catch (MalformedURLException e1) {
          e1.printStackTrace();
        }
      }
    });

    Scene scene = new Scene(grid, 400, 400);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }

}
