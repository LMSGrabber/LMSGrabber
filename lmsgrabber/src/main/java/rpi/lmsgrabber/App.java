package rpi.lmsgrabber;

/**
 * Hello world!
 *
 */
public class App {

  public App() {
    BlackboardGrab grabber = new BlackboardGrab();
    grabber.grab();
  }

  public static void main(String[] args) {
    new App();
  }

}
